package com.aparkautepino.aparkautepino.Model.Service.AsignacionEspacioStrategy;

import org.springframework.stereotype.Component;

import com.aparkautepino.aparkautepino.Model.Entity.CategoriaEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.Espacio;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.TipoVehiculo;
import com.aparkautepino.aparkautepino.Model.Repository.EspacioRepository;

@Component 
public class AsignacionAutoStrategy implements AsignacionEspacioStrategy{

    private final EspacioRepository espacioRepository;

    public AsignacionAutoStrategy(EspacioRepository espacioRepository) {
        this.espacioRepository = espacioRepository;
    }
    @Override
    public Espacio asignar() {
        Espacio espacio = espacioRepository.findFirstByCategoriaEspacioAndEstado(CategoriaEspacio.GRANDE, EstadoEspacio.DISPONIBLE).
        orElseThrow(() -> new IllegalStateException("No hay espacios disponibles para autos"));
        espacio.setEstado(EstadoEspacio.OCUPADO);
        return espacio;
    }

}
