package com.aparkautepino.aparkautepino.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "vehiculo",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_vehiculo_placa", columnNames = "placa")
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona propietario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoVehiculo tipo;

    @Size(max = 15)
    @Column(length = 15)
    private String placa;

    @Size(max = 50)
    @Column(length = 50)
    private String marca;

    @Size(max = 50)
    @Column(length = 50)
    private String modelo;

    @Size(max = 30)
    @Column(length = 30)
    private String color;

    @Column(nullable = false)
    private Boolean activo = true;
}