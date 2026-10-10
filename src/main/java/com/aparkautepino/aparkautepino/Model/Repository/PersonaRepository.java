package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import java.util.List;


public interface PersonaRepository extends JpaRepository<Persona,Integer>{

    Optional<Persona> findByNumeroDocumento(String numeroDocumento);

}
