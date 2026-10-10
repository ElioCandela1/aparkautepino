package com.aparkautepino.aparkautepino.Model.Service;

import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;
import com.aparkautepino.aparkautepino.Model.Repository.VehiculoRepository;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public void guardarVehiculo(Vehiculo vehiculo) {

        vehiculoRepository.save(vehiculo);

    }

}
