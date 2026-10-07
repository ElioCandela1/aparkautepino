package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.UsuarioSistema;

public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Integer> {
    Optional<UsuarioSistema> findByUsername(String username);

    boolean existsByUsername(String string);

    Page<UsuarioSistema> findAll(Pageable pageable);

    Optional<UsuarioSistema> findByTokenRecuperacion(String token);

}
