package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aparkautepino.aparkautepino.Model.Entity.TipoDocumento;

public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
    Optional<TipoDocumento> findByCodigo(String codigo);

    Optional<TipoDocumento> findById(Long idTipoDocumento);

}
