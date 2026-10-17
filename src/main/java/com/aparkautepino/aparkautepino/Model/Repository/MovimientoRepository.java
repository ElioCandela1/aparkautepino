package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoMovimiento;
import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento,Integer> {

    List<Movimiento> findByEstadoTrue();

    Movimiento getByAbonadoAndEstado(Abonado abonado, EstadoMovimiento EstadoMovimiento);

}
