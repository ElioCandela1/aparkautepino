package com.aparkautepino.aparkautepino.Model.Service.AsignacionEspacioStrategy;

import org.springframework.stereotype.Component;

import com.aparkautepino.aparkautepino.Model.Entity.CategoriaEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.Espacio;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.TipoVehiculo;
import com.aparkautepino.aparkautepino.Model.Repository.EspacioRepository;

@Component 
public class AsignacionBicicletaYScooterStrategy implements AsignacionEspacioStrategy {

    private final EspacioRepository espacioRepository;

    public AsignacionBicicletaYScooterStrategy(EspacioRepository espacioRepository) {
        this.espacioRepository = espacioRepository;
    }

    @Override
    public Espacio asignar() {
        return espacioRepository.findFirstByCategoriaEspacioAndEstado(CategoriaEspacio.PEQUENO, EstadoEspacio.DISPONIBLE).
        orElseThrow(() -> new IllegalStateException("No hay espacio disponible para Bicicletas o Scooter"));
    }    

}
