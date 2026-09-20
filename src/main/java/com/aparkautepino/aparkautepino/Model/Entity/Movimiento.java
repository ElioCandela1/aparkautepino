package com.aparkautepino.aparkautepino.Model.Entity;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimientos")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fecha-ingreso", nullable = false)
    private LocalDateTime fechaIngreso;

    @Column(name = "fecha-salida", nullable = true)
    private LocalDateTime fechaSalida;

    @Column(nullable = false)
    private boolean estado;

    public Duration duracion() {
        return Duration.between(this.fechaIngreso, this.fechaSalida);
    }

    public Movimiento() {
    }

    public Movimiento(LocalDateTime fechaIngreso, boolean estado) {
        this.fechaIngreso = fechaIngreso;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

}
