package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.Optional;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.Usuario;

public interface UsuarioRepository extends  JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String string);

    Page<Usuario> findAll(Pageable pageable);

}
