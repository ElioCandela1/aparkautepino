package com.aparkautepino.aparkautepino.Model.Service;

import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.Espacio;
import com.aparkautepino.aparkautepino.Model.Repository.EspacioRepository;

@Service 
public class EspacioService {

    private final EspacioRepository espacioRepository;

    public EspacioService(EspacioRepository espacioRepository) {
        this.espacioRepository = espacioRepository;
    }

    public Espacio buscarEspacio(Integer id){
        return espacioRepository.findById(id).orElseThrow(() -> new RuntimeException("No se encontró es espacio seleccionado"));
    }
}
