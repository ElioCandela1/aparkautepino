package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;

public interface AbonadoRepository extends JpaRepository<Abonado, Integer> {

    Optional<Abonado> findByPersona(Persona persona);

}
