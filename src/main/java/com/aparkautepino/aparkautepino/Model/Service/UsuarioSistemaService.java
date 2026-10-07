package com.aparkautepino.aparkautepino.Model.Service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aparkautepino.aparkautepino.Model.Dto.UsuarioFormDto;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.TipoDocumento;
import com.aparkautepino.aparkautepino.Model.Entity.UsuarioSistema;
import com.aparkautepino.aparkautepino.Model.Repository.PersonaRepository;
import com.aparkautepino.aparkautepino.Model.Repository.TipoDocumentoRepository;
import com.aparkautepino.aparkautepino.Model.Repository.UsuarioSistemaRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioSistemaService {

    private final UsuarioSistemaRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final PasswordEncoder passwordEncoder;
     private final EmailService emailService;

    public UsuarioSistemaService(UsuarioSistemaRepository usuarioRepository,
                                 PersonaRepository personaRepository,
                                 TipoDocumentoRepository tipoDocumentoRepository,
                                 PasswordEncoder passwordEncoder,
                                EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
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

    @Transactional
    public void registrarIntentoFallido(String username) {
        UsuarioSistema usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getBloqueado()) {
            return; // Ya está bloqueado
        }

        int intentos = usuario.getIntentosFallidos() + 1;
        usuario.setIntentosFallidos(intentos);

        if (intentos >= 3) {
            usuario.setBloqueado(true);
            String token = UUID.randomUUID().toString();
            usuario.setTokenRecuperacion(token);
            usuario.setFechaExpiracionToken(LocalDateTime.now().plusHours(1)); // 1 hora de validez
            emailService.enviarCorreoDesbloqueo(usuario.getPersona().getCorreo(), token);
        }

        usuarioRepository.save(usuario);
    }

    public void resetearIntentos(String username) {
        usuarioRepository.findByUsername(username).ifPresent(u -> {
            u.setIntentosFallidos(0);
            usuarioRepository.save(u);
        });
    }

    public boolean tokenValido(String token) {
    return usuarioRepository.findByTokenRecuperacion(token)
            .map(u -> u.getFechaExpiracionToken() != null
                   && u.getFechaExpiracionToken().isAfter(LocalDateTime.now()))
            .orElse(false);
}

@Transactional
public void resetearPasswordConToken(String token, String nuevaPassword) {

    UsuarioSistema usuario = usuarioRepository.findByTokenRecuperacion(token)
            .orElseThrow(() -> new RuntimeException("Token inválido"));

    if (usuario.getFechaExpiracionToken() == null
            || usuario.getFechaExpiracionToken().isBefore(LocalDateTime.now())) {
        throw new RuntimeException("El enlace ha expirado");
    }

    // Actualizar contraseña y desbloquear la cuenta
    usuario.setPassword(passwordEncoder.encode(nuevaPassword));
    usuario.setBloqueado(false);
    usuario.setIntentosFallidos(0);
    usuario.setTokenRecuperacion(null);
    usuario.setFechaExpiracionToken(null);

    usuarioRepository.save(usuario);
}
}