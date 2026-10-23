package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.EstadoMovimiento;
import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;
import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;

public interface MovimientoRepository extends JpaRepository<Movimiento,Integer> {

    List<Movimiento> findByEstadoTrue();

    Movimiento getByAbonadoAndEstado(Abonado abonado, EstadoMovimiento EstadoMovimiento);

   Optional<Movimiento> findByAbonadoAndEstado(Abonado abonado, EstadoMovimiento estadoMovimiento);

   Optional<Movimiento> findByVehiculoAndEstado(Vehiculo vehiculo, EstadoMovimiento estadoMovimiento);
}
