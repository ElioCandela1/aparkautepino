package com.aparkautepino.aparkautepino.Model.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario_sistema", uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuario_username", columnNames = "username")
})

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSistema extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_persona", nullable = false, unique = true)
    private Persona persona;

    @NotBlank
    @Size(min = 4, max = 50)
    @Column(nullable = false, length = 50)
    private String username;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(nullable = false)
    private Integer intentosFallidos = 0;

    @Column(nullable = false)
    private Boolean bloqueado = false;

    // Campo para el token de recuperación
    @Column(length = 100)
    private String tokenRecuperacion;

    @Column
    private LocalDateTime fechaExpiracionToken;
}