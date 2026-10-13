package com.aparkautepino.aparkautepino.Model.Service;

import com.aparkautepino.aparkautepino.Model.Repository.SolicitudAbonadoRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Dto.PersonaDto;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoSolicitud;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.SolicitudAbonado;
import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;

@Service
public class SolicitudAbonadoService {

    private final SolicitudAbonadoRepository solicitudAbonadoRepository;
    private final PersonaService personaService;
    private final VehiculoService vehiculoService;

    public SolicitudAbonadoService(SolicitudAbonadoRepository solicitudAbonadoRepository, PersonaService personaService,
            VehiculoService vehiculoService) {
        this.solicitudAbonadoRepository = solicitudAbonadoRepository;
        this.personaService = personaService;
        this.vehiculoService = vehiculoService;
    }

    // En un nuevo SolicitudCompletaService
    @Transactional
    public void registrarSolicitudCompleta(PersonaDto personaDto, SolicitudAbonado solicitud) {

        Persona persona;

        try {
            persona = personaService.buscarpersonaPorNumeroDocumento(personaDto.getNumeroDocumento());
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
        SolicitudAbonado solicitudAbonado = solicitudAbonadoRepository.findById(id).orElseThrow(()-> new RuntimeException("No se encontró solicitud"));
        solicitudAbonado.setEstado(EstadoSolicitud.APROBADA);
        solicitudAbonado.setFechaRespuesta(LocalDateTime.now());
        solicitudAbonadoRepository.save(solicitudAbonado);

        Vehiculo vehiculo = Vehiculo.builder().tipo(solicitudAbonado.getTipoVehiculo())
                .placa(solicitudAbonado.getPlaca())
                .marca(solicitudAbonado.getMarca())
                .modelo(solicitudAbonado.getModelo())
                .color(solicitudAbonado.getColor())
                .activo(true)
                .propietario(solicitudAbonado.getPersona())
                .activo(true)
                .build();
        vehiculoService.guardarVehiculo(vehiculo);

    }

    public void rechazarSolicitud(int id, String motivo) {

        SolicitudAbonado solicitudAbonado = solicitudAbonadoRepository.findById(id).orElseThrow(()-> new RuntimeException("No se encontró solicitud"));
        solicitudAbonado.setEstado(EstadoSolicitud.RECHAZADA);
        solicitudAbonado.setFechaRespuesta(LocalDateTime.now());
        solicitudAbonado.setMotivoRechazo(motivo);

        solicitudAbonadoRepository.save(solicitudAbonado);
    }

    public List<SolicitudAbonado> listarSolicitudesPedientes(){
        
        List<SolicitudAbonado> solicitudes =  solicitudAbonadoRepository.findTop50ByEstado(EstadoSolicitud.PENDIENTE);
        return solicitudes;
        
    }

}
