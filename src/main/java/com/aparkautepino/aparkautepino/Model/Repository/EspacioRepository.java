package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.CategoriaEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.Espacio;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.TipoVehiculo;

public interface EspacioRepository extends JpaRepository<Espacio, Integer> {

    //List<Espacio> findByEstadoAndTipoPermitido(EstadoEspacio estado, TipoVehiculo tipoPermitido);

    Optional<Espacio> findFirstByCategoriaEspacioAndEstado(CategoriaEspacio categoriaEspacio, EstadoEspacio estadoEspacio);

    List<Espacio> findByCategoriaEspacioAndEstado(CategoriaEspacio categoriaEspacio, EstadoEspacio estadoEspacio);

    
}
