package com.aparkautepino.aparkautepino.Model.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;
import com.aparkautepino.aparkautepino.Model.Repository.VehiculoRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final PersonaService personaService;

    public VehiculoService(VehiculoRepository vehiculoRepository, PersonaService personaService) {
        this.vehiculoRepository = vehiculoRepository;
        this.personaService = personaService;
    }

    public void guardarVehiculo(Vehiculo vehiculo) {

        vehiculoRepository.save(vehiculo);

    }

    public Vehiculo buscarVehiculo(int id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se ha podido encontrar el vehiculo"));
    }

    public Vehiculo buscarPorPlaca(String placa) {

        return vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el vehiculo con placa " + placa));
    }

    public Optional<Vehiculo> buscarPorPlacaOptional(String placa) {

        return vehiculoRepository.findByPlaca(placa);
    }

    public List<Vehiculo> buscarPorPropietario(Persona persona) {

        return vehiculoRepository.findAllByPropietario(persona);
    }

    public Page<Vehiculo> listarVehiculos(int numeroPagina) {
        Pageable pageable = PageRequest.of(numeroPagina,
                10,
                Sort.by("id").ascending());

        return vehiculoRepository.findAll(pageable);
    }

    @Transactional
    public void actualizarVehiculo(Vehiculo vehiculo) {

        Vehiculo vehiculoPersistido = vehiculoRepository.findById(vehiculo.getId())
                .orElseThrow(() -> new NoSuchElementException("No se encontró el vehiculo"));

        Persona propietario = personaService
                .buscarpersonaPorNumeroDocumento(vehiculo.getPropietario().getNumeroDocumento())
                .orElseThrow(() -> new NoSuchElementException("No se encontró el propietario"));
        vehiculoPersistido.setActivo(vehiculo.getActivo());
        vehiculoPersistido.setColor(vehiculo.getColor());
        vehiculoPersistido.setMarca(vehiculo.getMarca());
        vehiculoPersistido.setModelo(vehiculo.getModelo());
        vehiculoPersistido.setPlaca(vehiculo.getPlaca());
        vehiculoPersistido.setPropietario(propietario);
        vehiculoPersistido.setTipo(vehiculo.getTipo());
    }

    @Transactional
    public void crearVehiculo(Vehiculo vehiculo) {
        System.out.println("Encontré el propietario");
        Persona propietario = personaService
                .buscarpersonaPorNumeroDocumento(vehiculo.getPropietario().getNumeroDocumento())
                .orElseThrow(() -> new RuntimeException("No se encontró el propietario"));

        vehiculo.setPropietario(propietario);
        vehiculoRepository.save(vehiculo);

    }

}
