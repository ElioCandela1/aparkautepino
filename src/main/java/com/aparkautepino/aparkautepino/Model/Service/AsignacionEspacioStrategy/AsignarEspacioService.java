package com.aparkautepino.aparkautepino.Model.Service.AsignacionEspacioStrategy;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.Espacio;
import com.aparkautepino.aparkautepino.Model.Entity.TipoVehiculo;

/**
 * EspacioService
 */
@Service 
public class AsignarEspacioService {

    private final Map<TipoVehiculo, AsignacionEspacioStrategy> estrategias;

public AsignarEspacioService(
            AsignacionAutoStrategy autoStrategy,
            AsignacionMotoStrategy motoStrategy,
            AsignacionBicicletaYScooterStrategy bicicletaYScooterStrategy) {

        estrategias = Map.of(
            TipoVehiculo.AUTOMOVIL, autoStrategy,
            TipoVehiculo.MOTO, motoStrategy,
            TipoVehiculo.BICICLETA, bicicletaYScooterStrategy,
            TipoVehiculo.SCOOTER, bicicletaYScooterStrategy
        );
        }


    public Espacio asignarEspacio(TipoVehiculo tipo) {
        AsignacionEspacioStrategy strategy = estrategias.get(tipo);

        if (strategy == null) {
            throw new IllegalArgumentException("Tipo de Vehiculo no soportado");
            
        }
        return strategy.asignar();
    }

}
