package com.aparkautepino.aparkautepino.Model.Service;

import com.aparkautepino.aparkautepino.Model.Service.EspacioService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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

    public MovimientoService(VehiculoService vehiculoService,
            MovimientoRepository movimientoRepository, AbonadoService abonadoService,
            AsignarEspacioService asignarEspacioService, PersonaService personaService, EspacioService espacioService) {
        this.vehiculoService = vehiculoService;
        this.movimientoRepository = movimientoRepository;
        this.abonadoService = abonadoService;
        this.asignarEspacioService = asignarEspacioService;
        this.personaService = personaService;
        this.espacioService = espacioService;
    }

    @Transactional
    public void ingresarVehiculo(int idVehiculo, Integer idEspacio) {

        try {

            Vehiculo vehiculo = vehiculoService.buscarVehiculo(idVehiculo);

            Abonado abonado = abonadoService.buscarAbonado(vehiculo.getPropietario());
            // Verificar que el vehiculo este registrado y activo

            if (!vehiculo.getActivo()) {
                throw new IllegalStateException("Vehículo inhabilitado");
            }

            // Asignar espacio

            Espacio espacio;
            if (idEspacio == null) {
                espacio = asignarEspacioService.asignarEspacio(vehiculo.getTipo());
            }else{
                espacio = espacioService.buscarEspacio(idEspacio);
            }

            // Crear y registrar movimiento;

            Movimiento movimiento = crearMovimiento(vehiculo, espacio, abonado);

            movimiento.setEspacio(espacio);
            espacio.setEstado(EstadoEspacio.OCUPADO);
            movimientoRepository.save(movimiento);

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("No se pudo registrar el ingreso del vehiculo");
        }

    }

    @Transactional
    public void registarSalidaVehiculo(int id) {

        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        Espacio espacio = movimiento.getEspacio();
        espacio.setEstado(EstadoEspacio.DISPONIBLE);
        movimiento.setEstado(EstadoMovimiento.FINALIZADO);
        movimiento.setFechaSalida(LocalDateTime.now());
        movimientoRepository.save(movimiento);
    }

    @Transactional
    public void ingresarVehiculo(String numeroDocumento) {

        // Verificar que la persona existe y es un abonado
        Persona persona = personaService.buscarpersonaPorNumeroDocumento(numeroDocumento);
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

        Persona persona = personaService.buscarpersonaPorNumeroDocumento(documento);
        Abonado abonado = abonadoService.buscarAbonado(persona);
        Movimiento movimiento = movimientoRepository.getByAbonadoAndEstado(abonado, EstadoMovimiento.ACTIVO);
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
        return movimientoRepository.findByAbonadoAndEstado(abonado, EstadoMovimiento.ACTIVO).orElseThrow(()-> new RuntimeException("No se encontró movimiento para el abonado: " + abonado.getPersona().getNombreCompleto()));

        }
        public Movimiento buscarMovimientoActivo(Vehiculo vehiculo) {
        return movimientoRepository.findByVehiculoAndEstado(vehiculo, EstadoMovimiento.ACTIVO).orElseThrow(() -> new RuntimeException("No se encontró movimiento para el vehiculo: " + vehiculo.getId()));
    }
}
