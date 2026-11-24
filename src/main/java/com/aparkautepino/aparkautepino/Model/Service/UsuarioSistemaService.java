package com.aparkautepino.aparkautepino.Model.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UsuarioSistemaService {

    private final UsuarioSistemaRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final PersonaService personaService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UsuarioSistemaService(UsuarioSistemaRepository usuarioRepository,
            PersonaRepository personaRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService,
            PersonaService personaService) {
        this.usuarioRepository = usuarioRepository;
        this.personaRepository = personaRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.personaService = personaService;
    }

    @Transactional
    public void crearUsuario(UsuarioFormDto form) {

        Persona persona = personaService.crearPersona(form);
        UsuarioSistema usuario = new UsuarioSistema();
        usuario.setPersona(persona); // ← CLAVE: asignar la Persona
        usuario.setUsername(form.getUsername());
        usuario.setPassword(passwordEncoder.encode(form.getPassword()));
        usuario.setRol(form.getRol());
        usuario.setActivo(form.getActivo() != null ? form.getActivo() : true);

        personaService.guardarPersona(persona);
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

    public Page<UsuarioSistema> listarAbodanos(int pagina) {

        Pageable pageable = PageRequest.of(pagina,
                10,
                Sort.by("id").ascending());

        return usuarioRepository.findAll(pageable);
    }

    public UsuarioSistema buscarUsuario(int id) {

        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el usuario"));
    }

    @Transactional
    public void actualizarUsuario(UsuarioFormDto form) {

        UsuarioSistema usuarioPersistido = usuarioRepository.findById(form.getIdUsuario())
                .orElseThrow(() -> new NoSuchElementException("No se encontró el usuario"));

        Persona persona = personaService.crearPersona(form);

        personaService.editarPersona(persona, form.getIdPersona());

        usuarioPersistido.setActivo(form.getActivo());
        usuarioPersistido.setRol(form.getRol());
        usuarioPersistido.setUsername(form.getUsername());
        if (!form.getPassword().isBlank() && form.getPassword() != null) {
            usuarioPersistido.setPassword(passwordEncoder.encode(form.getPassword()));
        }

    }

    public UsuarioFormDto obtenerFormulario(int id) {

        UsuarioSistema usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el usuario"));

        UsuarioFormDto dto = new UsuarioFormDto();

        dto.setActivo(usuario.getActivo());
        dto.setCorreo(usuario.getPersona().getCorreo());
        dto.setIdPersona(usuario.getPersona().getId());
        dto.setIdUsuario(usuario.getId());
        dto.setIdTipoDocumento(usuario.getPersona().getTipoDocumento().getId());
        dto.setNombres(usuario.getPersona().getNombres());
        dto.setNumeroDocumento(usuario.getPersona().getNumeroDocumento());
        dto.setPrimerApellido(usuario.getPersona().getPrimerApellido());
        dto.setSegundoApellido(usuario.getPersona().getSegundoApellido());
        dto.setTelefono(usuario.getPersona().getTelefono());
        dto.setUsername(usuario.getUsername());

        return dto;

    }
}