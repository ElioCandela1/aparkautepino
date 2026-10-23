package com.aparkautepino.aparkautepino.Model.Service;

import com.aparkautepino.aparkautepino.Model.Service.MovimientoService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;
import com.aparkautepino.aparkautepino.Model.Repository.AbonadoRepository;

@Service 
public class BusquedaService {

    private final MovimientoService movimientoService;
    private final PersonaService personaService;
    private final AbonadoService abonadoService;
    private final VehiculoService vehiculoService;
    private static final Pattern PLACA = Pattern.compile("^[A-Z]{3}-?\\d{3,4}$");
    private static final Pattern DOCIDENTIDAD = Pattern.compile("^\\d{8}$");

    

    public BusquedaService(PersonaService personaService, AbonadoService abonadoService,
            VehiculoService vehiculoService,MovimientoService movimientoService) {
        this.personaService = personaService;
        this.abonadoService = abonadoService;
        this.vehiculoService = vehiculoService;
        this.movimientoService = movimientoService;
    }



    public Abonado bucarporDocumentoIdentidad(String documento){

        Persona persona = personaService.buscarpersonaPorNumeroDocumento(documento);

        return abonadoService.buscarAbonado(persona);
    }

    public  Vehiculo buscarVehiculoPorPlaca(String placa){

        return  vehiculoService.buscarPorPlaca(placa);
    }

    public List<Vehiculo> buscarVehiculoPorPropietario(Persona persona){
        return vehiculoService.buscarPorPropietario(persona);
    }

    public List<Vehiculo> buscar(String criterio){

         if (criterio == null || criterio.isBlank()) {
        return Collections.emptyList();
    }
        String limpio = criterio.trim().toUpperCase();

        if(DOCIDENTIDAD.matcher(limpio).matches()){
            Persona persona = personaService.buscarpersonaPorNumeroDocumento(limpio);
            return vehiculoService.buscarPorPropietario(persona);
        }

        if(PLACA.matcher(limpio).matches()){
             
            Vehiculo vehiculo = vehiculoService.buscarPorPlaca(limpio);
            return List.of(vehiculo);
        }

        return Collections.emptyList();
    }



    public Movimiento buscarMovimientoActivo(String criterio) {
        if (criterio == null || criterio.isBlank()) {
        return null;
    }
        String limpio = criterio.trim().toUpperCase();

        if(DOCIDENTIDAD.matcher(limpio).matches()){
            Persona persona = personaService.buscarpersonaPorNumeroDocumento(limpio);

            return movimientoService.buscarMovimientoActivo(abonadoService.buscarAbonado(persona));
        }

        if(PLACA.matcher(limpio).matches()){
             
            Vehiculo vehiculo = vehiculoService.buscarPorPlaca(limpio);
            return movimientoService.buscarMovimientoActivo(vehiculo);
        }

        return null;
    }
}
