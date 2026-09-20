package com.aparkautepino.aparkautepino.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "auto_moviles")
public class AutoMovil extends Vehiculo {

    @NotBlank(message = "El automovil debe tener un numero de placa")
    @Column(nullable = false)
    private String placa;

    public AutoMovil() {
    }

    public AutoMovil(String color, TipoVehiculo tipoVehiculo, String placa) {
        super(color, tipoVehiculo);
        this.placa = placa;
        this.setActivo(true);
    }

    // Getters y Setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

}
