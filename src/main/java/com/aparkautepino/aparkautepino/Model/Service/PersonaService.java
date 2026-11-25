package com.aparkautepino.aparkautepino.Model.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aparkautepino.aparkautepino.Model.Dto.PersonaDto;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.TipoDocumento;
import com.aparkautepino.aparkautepino.Model.Repository.PersonaRepository;
import com.aparkautepino.aparkautepino.Model.Repository.TipoDocumentoRepository;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;

    public PersonaService (PersonaRepository personaRepository, TipoDocumentoRepository tipoDocumentoRepository){
        
        this.personaRepository = personaRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
    }

    public Persona crearPersona(PersonaDto form){

        TipoDocumento tipo = tipoDocumentoRepository.findById(form.getIdTipoDocumento())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de documento inválido"));

        // 1. Persona
        Persona persona = new Persona();
        persona.setNombres(form.getNombres());
        persona.setPrimerApellido(form.getPrimerApellido());
        persona.setSegundoApellido(form.getSegundoApellido());
        persona.setTipoDocumento(tipo);
        persona.setNumeroDocumento(form.getNumeroDocumento());
        persona.setCorreo(form.getCorreo());
        persona.setTelefono(form.getTelefono());
        persona.setActivo(true);
        return persona;
    }

    public void guardarPersona(Persona persona){
        personaRepository.save(persona); // ← CLAVE: guardar la Persona
    }

    public Optional<Persona> buscarpersonaPorNumeroDocumento(String numeroDocumento) {
        return personaRepository.findByNumeroDocumento(numeroDocumento);
        
    }

        @Transactional
    public void editarPersona(Persona persona, int id){

        Persona personaPersistida = personaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No se encontró la persona"));
    
        personaPersistida.setActivo(persona.getActivo());
        personaPersistida.setCorreo(persona.getCorreo());
        personaPersistida.setNombres(persona.getNombres());
        personaPersistida.setNumeroDocumento(persona.getNumeroDocumento());
        personaPersistida.setPrimerApellido(persona.getPrimerApellido());
        personaPersistida.setSegundoApellido(persona.getSegundoApellido());
        personaPersistida.setTelefono(persona.getTelefono());
        personaPersistida.setTipoDocumento(persona.getTipoDocumento());
    }

        public Optional<Persona> buscarPersona(int id) {
            return personaRepository.findById(id);
        }
}
