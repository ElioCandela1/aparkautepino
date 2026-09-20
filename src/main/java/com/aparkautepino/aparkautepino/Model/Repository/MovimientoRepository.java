package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;

@Repository 
public interface MovimientoRepository extends JpaRepository<Movimiento,Integer> {

    List<Movimiento> findByEstadoTrue();
}
