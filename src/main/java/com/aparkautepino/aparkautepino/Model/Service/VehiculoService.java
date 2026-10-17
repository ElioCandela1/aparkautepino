package com.aparkautepino.aparkautepino.Model.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.Persona;
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

    public Vehiculo buscarPorPlaca(String placa) {
      
        return vehiculoRepository.findByPlaca(placa).orElseThrow(() -> new RuntimeException("No se encontró el vehiculo con placa " + placa));
    }

    public List<Vehiculo> bucarPorPropietario(Persona persona) {
       return vehiculoRepository.findAllByPropietario(persona);
    }

}
