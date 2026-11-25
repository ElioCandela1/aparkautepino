package com.aparkautepino.aparkautepino.Model.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.Espacio;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoMovimiento;
import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.TipoAcceso;
import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;
import com.aparkautepino.aparkautepino.Model.Repository.MovimientoRepository;
import com.aparkautepino.aparkautepino.Model.Service.AsignacionEspacioStrategy.AsignarEspacioService;
import com.aparkautepino.aparkautepino.Model.Service.ValidacionIngreso.ValidacionIngreso;

import jakarta.transaction.Transactional;

/**
 * MovimientoService
 */
@Service
public class MovimientoService {

    private final EspacioService espacioService;
    private final VehiculoService vehiculoService;
    private final MovimientoRepository movimientoRepository;
    private final AbonadoService abonadoService;
    private final AsignarEspacioService asignarEspacioService;
    private final PersonaService personaService;
    private final List<ValidacionIngreso> validacionIngresos;

    public MovimientoService(VehiculoService vehiculoService,
            MovimientoRepository movimientoRepository,
            AbonadoService abonadoService,
            AsignarEspacioService asignarEspacioService, 
            PersonaService personaService, 
            EspacioService espacioService,
            List<ValidacionIngreso> validacionIngresos) {
        this.vehiculoService = vehiculoService;
        this.movimientoRepository = movimientoRepository;
        this.abonadoService = abonadoService;
        this.asignarEspacioService = asignarEspacioService;
        this.personaService = personaService;
        this.espacioService = espacioService;
        this.validacionIngresos = validacionIngresos;
    }

    @Transactional
    public void ingresarVehiculo(Integer idVehiculo, Integer idEspacio) {

        Vehiculo vehiculo = vehiculoService.buscarVehiculo(idVehiculo);

        Abonado abonado = abonadoService.buscarAbonado(vehiculo.getPropietario());

        for(ValidacionIngreso validacion : validacionIngresos){
            System.out.println("Entre a validar");
            validacion.validar(vehiculo);
        }

        System.out.println("Sali de validar");

        // Asignar espacio

        Espacio espacio;
        if (idEspacio == null) {
            System.out.println("El id del espacio es nulo");
            espacio = asignarEspacioService.asignarEspacio(vehiculo.getTipo());
        } else {
            espacio = espacioService.buscarEspacio(idEspacio);
        }

        // Crear y registrar movimiento;

        Movimiento movimiento = crearMovimiento(vehiculo, espacio, abonado);

        movimiento.setEspacio(espacio);
        espacio.setEstado(EstadoEspacio.OCUPADO);
        movimientoRepository.save(movimiento);

    }

    @Transactional
    public void registarSalidaVehiculo(int id) {

        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        Espacio espacio = movimiento.getEspacio();
        espacio.setEstado(EstadoEspacio.DISPONIBLE);
        movimiento.setEstado(EstadoMovimiento.FINALIZADO);
        movimiento.setFechaSalida(LocalDateTime.now());
        movimientoRepository.save(movimiento);
    }

    @Transactional
    public void ingresarVehiculo(String numeroDocumento) {

        // Verificar que la persona existe y es un abonado
        Persona persona = personaService.buscarpersonaPorNumeroDocumento(numeroDocumento).orElseThrow(() -> new NoSuchElementException("Persona no registrada"));
        Abonado abonado = abonadoService.buscarAbonado(persona);

        if (!abonado.getActivo()) {
            throw new IllegalStateException("Usuario inhabilitado");
        }

        List<Vehiculo> vehiculos = vehiculoService.buscarPorPropietario(persona);
        if (vehiculos.size() > 1) {
            throw new UnsupportedOperationException(
                    "El usuario cuenta con mas de un vehiculo realice un ingreso manual");
        }

        Vehiculo vehiculo = vehiculos.get(0);

        if (!vehiculo.getActivo()) {
            throw new IllegalStateException("Vehiculo inhabilitado");
        }

        Espacio espacio = asignarEspacioService.asignarEspacio(vehiculo.getTipo());

        // Crear y registrar el movimiento
        movimientoRepository.save(
                Movimiento.builder()
                        .vehiculo(vehiculo)
                        .espacio(espacio)
                        .abonado(abonado)
                        .tipoAcceso(TipoAcceso.ABONADO)
                        .fechaIngreso(LocalDateTime.now())
                        .estado(EstadoMovimiento.ACTIVO)
                        .build());

        // Marcar el espacio como ocupado
    }

    @Transactional
    public void registarSalidaVehiculo(String documento) {

        Persona persona = personaService.buscarpersonaPorNumeroDocumento(documento).orElseThrow(() -> new NoSuchElementException("Persona no registrada"));
        Abonado abonado = abonadoService.buscarAbonado(persona);
        Movimiento movimiento = movimientoRepository.findByAbonadoAndEstado(abonado, EstadoMovimiento.ACTIVO)
                .orElseThrow(() -> new RuntimeException("No se encontró el abonado"));
        Espacio espacio = movimiento.getEspacio();
        espacio.setEstado(EstadoEspacio.DISPONIBLE);
        movimiento.setFechaSalida(LocalDateTime.now());
        movimiento.setEstado(EstadoMovimiento.FINALIZADO);
        movimientoRepository.save(movimiento);
    }

    public Movimiento crearMovimiento(Vehiculo vehiculo, Espacio espacio, Abonado abonado) {

        return Movimiento.builder()
                .vehiculo(vehiculo)
                .espacio(espacio)
                .abonado(abonado)
                .tipoAcceso(TipoAcceso.ABONADO)
                .fechaIngreso(LocalDateTime.now())
                .estado(EstadoMovimiento.ACTIVO)
                .build();

    }

    public Movimiento buscarMovimientoActivo(Abonado abonado) {
        return movimientoRepository.findByAbonadoAndEstado(abonado, EstadoMovimiento.ACTIVO)
                .orElseThrow(() -> new RuntimeException(
                        "No se encontró movimiento para el abonado: " + abonado.getPersona().getNombreCompleto()));

    }

    public Optional<Movimiento> buscarMovimientoActivo(Vehiculo vehiculo) {
        return movimientoRepository.findByVehiculoAndEstado(vehiculo, EstadoMovimiento.ACTIVO);
    }

    public Page<Movimiento> listarMovimientos(int numeroPagina) {

        Pageable pageable = PageRequest.of(numeroPagina,
                10,
                Sort.by("id").ascending());

        return movimientoRepository.findAll(pageable);
    }

    public Page<Movimiento> buscar(LocalDate desde, LocalDate hasta,
                               String placa, String dni, Pageable pageable) {
    return movimientoRepository.findAll(
            MovimientoSpec.conFiltros(desde, hasta, placa, dni),
            pageable);
}


}
