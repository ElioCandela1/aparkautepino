package com.aparkautepino.aparkautepino.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity 
@Table 
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vehiculo {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = true)
    private String marca;
    
    @Column(nullable = true)
    private String modelo;
    
    @Column(nullable = false)
    private String color;

    @Column(name = "estado_vehiculo", nullable = false)
    private boolean activo;

    @Enumerated (EnumType.STRING)
    @Column (name = "tipo_vehiculo",nullable = false)
    private TipoVehiculo tipoVehiculo;

    public Vehiculo() {
    }

    
    public Vehiculo(String color,  TipoVehiculo tipoVehiculo) {
        this.color = color;
        this.activo = true;
        this.tipoVehiculo = tipoVehiculo;
    }


    public boolean requierePlaca(){
        return this.tipoVehiculo == TipoVehiculo.AUTOMOVIL || this.tipoVehiculo == TipoVehiculo.MOTO;
    }

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public TipoVehiculo getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    

}
