package com.aparkautepino.aparkautepino.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "abonado",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_abonado_persona", columnNames = "id_persona")
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Builder.Default
    private Boolean activo=true;

    @Column(name = "fecha_aprobacion", nullable = false)
    private LocalDateTime fechaAprobacion;
}