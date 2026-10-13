package com.aparkautepino.aparkautepino.Model.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.EstadoSolicitud;
import com.aparkautepino.aparkautepino.Model.Entity.SolicitudAbonado;
import java.util.List;


public interface SolicitudAbonadoRepository extends JpaRepository<SolicitudAbonado, Integer> {

    List<SolicitudAbonado> findTop50ByEstado(EstadoSolicitud estado);

}
