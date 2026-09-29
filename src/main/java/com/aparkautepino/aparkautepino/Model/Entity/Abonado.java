package com.aparkautepino.aparkautepino.Model.Entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "abonado",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_abonado_persona", columnNames = "id_persona")
    }
)
@Builder
public class Abonado extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "id_persona",
        nullable = false,
        unique = true
    )
    private Persona persona;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoAbonado tipo;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_aprobacion", nullable = false)
    private LocalDateTime fechaAprobacion;
}