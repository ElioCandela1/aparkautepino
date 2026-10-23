package com.aparkautepino.aparkautepino.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Entity
@Table(name = "visitante")
@Builder
public class Visitante extends AuditableEntity {

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

    @Size(max = 255)
    @Column(name = "motivo_visita", length = 255)
    private String motivoVisita;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo=true;
}