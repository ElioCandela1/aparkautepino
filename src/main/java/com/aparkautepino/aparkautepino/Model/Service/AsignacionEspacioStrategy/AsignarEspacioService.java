package com.aparkautepino.aparkautepino.Model.Service.AsignacionEspacioStrategy;

import com.aparkautepino.aparkautepino.Model.Repository.EspacioRepository;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.CategoriaEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.Espacio;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.TipoVehiculo;

/**
 * EspacioService
 */
@Service 
public class AsignarEspacioService {

    private final EspacioRepository espacioRepository;
    private final Map<TipoVehiculo, AsignacionEspacioStrategy> estrategias;

public AsignarEspacioService(
            AsignacionAutoStrategy autoStrategy,
            AsignacionMotoStrategy motoStrategy,
            AsignacionBicicletaYScooterStrategy bicicletaYScooterStrategy, EspacioRepository espacioRepository) {

        estrategias = Map.of(
            TipoVehiculo.AUTOMOVIL, autoStrategy,
            TipoVehiculo.MOTO, motoStrategy,
            TipoVehiculo.BICICLETA, bicicletaYScooterStrategy,
            TipoVehiculo.SCOOTER, bicicletaYScooterStrategy
        );
        this.espacioRepository = espacioRepository;
        }

        

    public Espacio asignarEspacio(TipoVehiculo tipo) {
        AsignacionEspacioStrategy strategy = estrategias.get(tipo);

        if (strategy == null) {
            throw new IllegalArgumentException("Tipo de Vehiculo no soportado");
            
        }
        return strategy.asignar();
    }

    public List<Espacio> listarEspaciosDisponibles(CategoriaEspacio categoriaEspacio, EstadoEspacio estado){
        return espacioRepository.findByCategoriaEspacioAndEstado(categoriaEspacio, estado);
        
    }
}
