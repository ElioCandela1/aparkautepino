package com.aparkautepino.aparkautepino.Model.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aparkautepino.aparkautepino.Model.Entity.TipoDocumento;

@Repository 
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Integer> {
    Optional<TipoDocumento> findByCodigo(String codigo);

    Optional<TipoDocumento> findById(Long idTipoDocumento);

}
