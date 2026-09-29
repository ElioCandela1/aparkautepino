package com.aparkautepino.aparkautepino.Model.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Entity
@Table(
    name = "alumno",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_alumno_codigo", columnNames = "codigo_alumno")
    }
)
@Builder
public class Alumno extends AuditableEntity {

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

    @NotBlank
    @Size(max = 30)
    @Column(name = "codigo_alumno", nullable = false, length = 30)
    private String codigoAlumno;

    @Column(nullable = false)
    private Boolean activo = true;
}