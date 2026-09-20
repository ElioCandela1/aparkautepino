package com.aparkautepino.aparkautepino.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity 
@Table(name="espacios")
public class Espacio {

@Id 
@GeneratedValue (strategy = GenerationType.IDENTITY)
private int id; 

@Column (name = "nombre", nullable = false)
@NotBlank (message = "El espacio de estacionamiento debe tener un nombre identificador")
private String nombre;

@Enumerated (EnumType.STRING)
@Column (nullable = false)
@NotBlank (message = "El espacio de estacionamiento debe tener un estado asignado")
private EstadoEspacio estado; 

@Enumerated (EnumType.STRING)
@Column (name = "tipo-permitido", nullable = true)
private TipoVehiculo tipoPermitido;

public Espacio() {
}

public void ocupar(){
    this.estado = EstadoEspacio.OCUPADO;
}

public void liberar(){
    this.estado = EstadoEspacio.DISPONIBLE;
}

public boolean admite(TipoVehiculo tipo){
    return (tipo == tipoPermitido) ? true: false;
}

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}

public EstadoEspacio getEstado() {
    return estado;
}

public void setEstado(EstadoEspacio estado) {
    this.estado = estado;
}

public TipoVehiculo getTipoPermitido() {
    return tipoPermitido;
}

public void setTipoPermitido(TipoVehiculo tipoPermitido) {
    this.tipoPermitido = tipoPermitido;
}

// getters y setters

}
