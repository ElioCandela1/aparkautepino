package com.aparkautepino.aparkautepino.Model.Service;

import com.aparkautepino.aparkautepino.Model.Repository.AbonadoRepository;
import com.aparkautepino.aparkautepino.Model.Repository.SolicitudAbonadoRepository;

import com.aparkautepino.aparkautepino.Model.Service.AbonadoService;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Dto.PersonaDto;
import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoSolicitud;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.SolicitudAbonado;
import com.aparkautepino.aparkautepino.Model.Entity.TipoVehiculo;
import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;

@Service
public class SolicitudAbonadoService {

    private final AbonadoService abonadoService;
    private final SolicitudAbonadoRepository solicitudAbonadoRepository;
    private final PersonaService personaService;
    private final VehiculoService vehiculoService;

    public SolicitudAbonadoService(SolicitudAbonadoRepository solicitudAbonadoRepository, PersonaService personaService,
            VehiculoService vehiculoService, AbonadoService abonadoService) {
        this.solicitudAbonadoRepository = solicitudAbonadoRepository;
        this.personaService = personaService;
        this.vehiculoService = vehiculoService;
        this.abonadoService = abonadoService;
    }

    // En un nuevo SolicitudCompletaService
    @Transactional
    public void registrarSolicitudCompleta(PersonaDto personaDto, SolicitudAbonado solicitud) {

        Persona persona;

        try {
            persona = personaService.buscarpersonaPorNumeroDocumento(personaDto.getNumeroDocumento())
                    .orElseThrow(() -> new NoSuchElementException("Persona no encontrada"));
        } catch (RuntimeException e) {
            persona = personaService.crearPersona(personaDto);
        }

        solicitud.setPersona(persona);
        personaService.guardarPersona(persona);
        guardarSolicitudAbonado(solicitud);
    }

    public void guardarSolicitudAbonado(SolicitudAbonado solicitudAbonado) {
        solicitudAbonadoRepository.save(solicitudAbonado);
    }

    @Transactional
    public void aceptarSolicitud(int id) {
        SolicitudAbonado solicitudAbonado = solicitudAbonadoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró solicitud"));

        // 1. Idempotencia: no reprocesar si ya está resuelta
        if (solicitudAbonado.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new IllegalStateException(
                    "La solicitud " + id + " ya fue procesada (estado: " + solicitudAbonado.getEstado() + ")");
        }

        if (solicitudAbonado.getTipoVehiculo().equals(TipoVehiculo.AUTOMOVIL)
                || solicitudAbonado.getTipoVehiculo().equals(TipoVehiculo.MOTO)) {

            Optional<Vehiculo> existente = vehiculoService.buscarPorPlacaOptional(solicitudAbonado.getPlaca());
            if (existente.isPresent()) {
                throw new IllegalStateException("Ya existe un vehículo con placa " + solicitudAbonado.getPlaca());
            }
        }

        solicitudAbonado.setEstado(EstadoSolicitud.APROBADA);
        solicitudAbonado.setFechaRespuesta(LocalDateTime.now());
        solicitudAbonadoRepository.save(solicitudAbonado);

        // 5. Crear o actualizar abonado
        Persona persona = solicitudAbonado.getPersona();
        Abonado abonado = abonadoService.buscarAbonadoOptional(persona)
                .map(existente -> {
                    existente.setTipo(solicitudAbonado.getTipoAbonado());
                    existente.setFechaAprobacion(solicitudAbonado.getFechaRespuesta());
                    return existente;
                })
                .orElseGet(() -> Abonado.builder()
                        .persona(persona)
                        .tipo(solicitudAbonado.getTipoAbonado())
                        .fechaAprobacion(solicitudAbonado.getFechaRespuesta())
                        .build());

        abonadoService.registarAbonado(abonado);

        Vehiculo vehiculo = Vehiculo.builder()
                .tipo(solicitudAbonado.getTipoVehiculo())
                .placa(solicitudAbonado.getPlaca())
                .marca(solicitudAbonado.getMarca())
                .modelo(solicitudAbonado.getModelo())
                .color(solicitudAbonado.getColor())
                .propietario(persona)
                .activo(true)
                .build();
        try {
            vehiculoService.guardarVehiculo(vehiculo);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Ya existe un vehículo con placa " + solicitudAbonado.getPlaca(), e);
        }

    }

    public void rechazarSolicitud(int id, String motivo) {

        SolicitudAbonado solicitudAbonado = solicitudAbonadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró solicitud"));
        solicitudAbonado.setEstado(EstadoSolicitud.RECHAZADA);
        solicitudAbonado.setFechaRespuesta(LocalDateTime.now());
        solicitudAbonado.setMotivoRechazo(motivo);

        solicitudAbonadoRepository.save(solicitudAbonado);
    }

    public List<SolicitudAbonado> listarSolicitudesPedientes() {

        List<SolicitudAbonado> solicitudes = solicitudAbonadoRepository.findTop50ByEstado(EstadoSolicitud.PENDIENTE);
        return solicitudes;

    }

}
