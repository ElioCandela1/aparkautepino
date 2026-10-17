package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer>{

    Optional<Vehiculo> findById(int id);

    Optional<Vehiculo> findByPlaca(String placa);

    List<Vehiculo> findAllByPropietario(Persona persona);

}
