package com.aparkautepino.aparkautepino.Model.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Dto.UsuarioFormDto;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.TipoDocumento;
import com.aparkautepino.aparkautepino.Model.Entity.UsuarioSistema;
import com.aparkautepino.aparkautepino.Model.Repository.PersonaRepository;
import com.aparkautepino.aparkautepino.Model.Repository.TipoDocumentoRepository;
import com.aparkautepino.aparkautepino.Model.Repository.UsuarioSistemaRepository;

import jakarta.validation.Valid;

@Service
public class UsuarioSistemaService {

    private final UsuarioSistemaRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioSistemaService(UsuarioSistemaRepository usuarioRepository,
                                 PersonaRepository personaRepository,
                                 TipoDocumentoRepository tipoDocumentoRepository,
                                 PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void crearUsuario(UsuarioFormDto form) {

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
        personaRepository.save(persona);   // ← CLAVE: guardar la Persona

        // 2. Usuario
        UsuarioSistema usuario = new UsuarioSistema();
        usuario.setPersona(persona);       // ← CLAVE: asignar la Persona
        usuario.setUsername(form.getUsername());
        usuario.setPassword(passwordEncoder.encode(form.getPassword()));
        usuario.setRol(form.getRol());
        usuario.setActivo(form.getActivo() != null ? form.getActivo() : true);

        usuarioRepository.save(usuario);
    }
}