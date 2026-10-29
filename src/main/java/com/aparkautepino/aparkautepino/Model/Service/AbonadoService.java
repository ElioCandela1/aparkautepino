package com.aparkautepino.aparkautepino.Model.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.TipoAbonado;
import com.aparkautepino.aparkautepino.Model.Repository.AbonadoRepository;;

/**
 * AbonadoService
 */
@Service
public class AbonadoService {

    private final AbonadoRepository abonadoRepository;

    public AbonadoService(AbonadoRepository abonadoRepository) {
        this.abonadoRepository = abonadoRepository;
    }

    public Abonado buscarAbonado(Persona persona) {

        return abonadoRepository.findByPersona(persona)
                .orElseThrow(() -> new RuntimeException("Usuario no registrado"));
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
}
