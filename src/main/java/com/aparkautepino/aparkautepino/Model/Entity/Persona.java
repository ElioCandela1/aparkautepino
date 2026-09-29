package com.aparkautepino.aparkautepino.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "persona",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_persona_documento",
            columnNames = {"id_tipo_documento", "numero_documento"}
        )
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Persona extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombres;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String primerApellido;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = true, length = 100)
    private String segundoApellido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento;

    @NotBlank
    @Size(max = 20)
    @Column(name = "numero_documento", nullable = false, length = 20)
    private String numeroDocumento;

    @Email
    @Size(max = 150)
    @Column(length = 150)
    private String correo;

    @Size(max = 20)
    @Column(length = 20)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;
}