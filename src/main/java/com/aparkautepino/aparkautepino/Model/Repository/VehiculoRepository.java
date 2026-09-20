package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;

@Repository 
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer>{

    Optional<Vehiculo> findById(int id);

    Optional<Vehiculo> findByPlaca(String placa);

}
