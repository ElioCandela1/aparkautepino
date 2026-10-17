package com.aparkautepino.aparkautepino.Model.Service;

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

}
