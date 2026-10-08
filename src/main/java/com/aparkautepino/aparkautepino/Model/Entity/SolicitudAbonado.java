package com.aparkautepino.aparkautepino.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "solicitud_abonado")
@Builder
public class SolicitudAbonado extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_persona", nullable = false)
    private Persona persona;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_abonado", nullable = false, length = 20)
    private TipoAbonado tipoAbonado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoSolicitud estado;

    // Datos del vehículo solicitado

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vehiculo", nullable = false, length = 20)
    private TipoVehiculo tipoVehiculo;

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

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_respuesta")
    private LocalDateTime fechaRespuesta;

    @Size(max = 255)
    @Column(name = "motivo_rechazo", length = 255)
    private String motivoRechazo;
}
