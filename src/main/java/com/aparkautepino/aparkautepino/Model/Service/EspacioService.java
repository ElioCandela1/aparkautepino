package com.aparkautepino.aparkautepino.Model.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.CategoriaEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.Espacio;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;
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

    public int contarCapacidadTotal(CategoriaEspacio categoriaEspacio){
        return espacioRepository.countByCategoriaEspacio(categoriaEspacio);
    }

    public int contarEspaciosDisponibles(CategoriaEspacio categoriaEspacio){
        return espacioRepository.countByCategoriaEspacioAndEstado(categoriaEspacio, EstadoEspacio.DISPONIBLE);
    }

    public int contarEspaciosOcupados(CategoriaEspacio categoriaEspacio){
        return espacioRepository.countByCategoriaEspacioAndEstado(categoriaEspacio, EstadoEspacio.OCUPADO);
    }

    public List<Espacio> listarEspaciosPorSector(String sector){

        return espacioRepository.findByCategoriaEspacioAndNumeroStartingWithOrderById(CategoriaEspacio.GRANDE, sector);
    }
}
