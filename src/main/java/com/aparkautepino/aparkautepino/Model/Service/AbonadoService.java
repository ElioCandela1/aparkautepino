package com.aparkautepino.aparkautepino.Model.Service;


import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.TipoAbonado;
import com.aparkautepino.aparkautepino.Model.Repository.AbonadoRepository;

import jakarta.persistence.EntityManager;;

/**
 * AbonadoService
 */
@Service
public class AbonadoService {

    private final AbonadoRepository abonadoRepository;
    private final PersonaService personaService;

    public AbonadoService(AbonadoRepository abonadoRepository, PersonaService personaService) {
        this.abonadoRepository = abonadoRepository;
        this.personaService = personaService;
    }

    public Abonado buscarAbonado(Persona persona) {

        return abonadoRepository.findByPersona(persona)
                .orElseThrow(() -> new RuntimeException("Usuario no registrado"));
    }

    public Abonado buscarAbonado(int id) {

        return abonadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Abonado no encontrado"));
    }

    public void registarAbonado(Abonado abonado) {

        abonadoRepository.save(abonado);

    }

    public Page<Abonado> listarAbodanos(int numeroPagina){

        Pageable pageable = PageRequest.of(numeroPagina,
            10, 
            Sort.by("id").ascending()
        );

        return abonadoRepository.findAll(pageable);
    }

    @Transactional 
    public void actualizarAbonado(Abonado abonado, int id){

        Abonado abonadoPersistido = abonadoRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontró el abonado"));

        // Actualizar datos de persona
        personaService.editarPersona(abonado.getPersona(), abonado.getPersona().getId());

        abonadoPersistido.setActivo(abonado.getActivo());
        abonadoPersistido.setTipo(abonado.getTipo());
    }

}
