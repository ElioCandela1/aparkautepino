package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.Espacio;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoEspacio;
import com.aparkautepino.aparkautepino.Model.Entity.TipoVehiculo;

public interface EpacioRepository extends JpaRepository<Espacio, Integer> {

    List<Espacio> findByEstadoAndTipoPermitido(EstadoEspacio estado, TipoVehiculo tipoPermitido);

    
}
