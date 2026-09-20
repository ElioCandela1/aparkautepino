package com.aparkautepino.aparkautepino.Config;

import java.beans.BeanProperty;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.aparkautepino.aparkautepino.Model.Entity.Rol;
import com.aparkautepino.aparkautepino.Model.Entity.Usuario;
import com.aparkautepino.aparkautepino.Model.Repository.UsuarioRepository;

@Configuration 
public class AdminInitialaizer {

    private final PasswordEncoder passwordEncoder;

    public AdminInitialaizer(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner crearAdmin(UsuarioRepository usuarioRepository){
        return args -> {

            // Crear usuario SYSTEM
            if(!usuarioRepository.existsByUsername("SYSTEM")){
                Usuario system = new Usuario.Builder()
                .nombre("SYSTEM")
                .primerApellido("null")
                .rol(Rol.ADMIN)
                .activo(true)
                .documentoIdentidad("00000000")
                .password(passwordEncoder.encode("123"))
                .tipoDocumentoIdentidad("DNI")
                .username("SYSTEM")
                .build();

                usuarioRepository.save(system);
                System.out.println("Se genero un administrador inicial");
            }
        };
    }
}