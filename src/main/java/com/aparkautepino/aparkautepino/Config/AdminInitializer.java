package com.aparkautepino.aparkautepino.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class AdminInitializer {
    
    @Bean
CommandLineRunner generarHash(PasswordEncoder encoder) {
    return args -> System.out.println("HASH: " + encoder.encode("123"));
}
    
    /* 

    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner crearAdmin(UsuarioSistemaRepository usuarioRepository,
                                 TipoDocumentoRepository tipoDocumentoRepository) {
        return args -> {

            if (!usuarioRepository.existsByUsername("SYSTEM")) {

                // 1. Buscar (o crear) el TipoDocumento DNI
                TipoDocumento dni = tipoDocumentoRepository
                        .findByCodigo("DNI")
                        .orElseGet(() -> tipoDocumentoRepository.save(
                                TipoDocumento.builder()
                                        .codigo("DNI")
                                        .nombre("Documento Nacional de Identidad")
                                        .activo(true)
                                        .build()
                        ));

                // 2. Crear la Persona
                Persona persona = Persona.builder()
                        .nombres("SYSTEM")
                        .primerApellido("-")
                        .segundoApellido("-")
                        .tipoDocumento(dni)
                        .numeroDocumento("00000000")
                        .activo(true)
                        .build();
                

                // 3. Crear el UsuarioSistema
                UsuarioSistema system = UsuarioSistema.builder()
                        .persona(persona)
                        .username("SYSTEM")
                        .password(passwordEncoder.encode("123"))
                        .rol(Rol.ADMIN)
                        .activo(true)
                        .build();

                usuarioRepository.save(system);
                System.out.println("Se generó un administrador inicial");
            }
        };
    }*/
}