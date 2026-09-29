package com.aparkautepino.aparkautepino.Model.Entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.LocalDate;

@Entity
@Table(
    name = "matricula",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_matricula_alumno_ciclo",
            columnNames = {"id_alumno", "id_ciclo"}
        )
    }
)
@Builder
public class Matricula extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciclo", nullable = false)
    private CicloAcademico ciclo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoMatricula estado;

    @NotNull
    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;
}