package com.aparkautepino.aparkautepino.Model.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;

import jakarta.persistence.criteria.Predicate;

public class MovimientoSpec {

    public static Specification<Movimiento> conFiltros(
        LocalDate desde, LocalDate hasta, String placa, String dni){
            return (root, query, cb) ->{
                List<Predicate> ps = new ArrayList<>();

                // Rango de fechas (opcional cada extremo)
            if (desde != null)
                ps.add(cb.greaterThanOrEqualTo(root.get("fechaIngreso"), desde.atStartOfDay()));
            if (hasta != null)
                ps.add(cb.lessThanOrEqualTo(root.get("fechaIngreso"), hasta.atTime(23, 59, 59)));

             // Placa (opcional)
            if (placa != null && !placa.isBlank())
                ps.add(cb.like(cb.upper(root.get("placa")), "%" + placa.toUpperCase() + "%"));

            // DNI (opcional) → navega la relación persona
            if (dni != null && !dni.isBlank())
                ps.add(cb.equal(root.get("abonado").get("persona").get("numeroDocumento"), dni));

            return cb.and(ps.toArray(new Predicate[0]));
            };
        }
    
}
