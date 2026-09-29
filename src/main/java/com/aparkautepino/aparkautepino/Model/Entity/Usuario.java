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
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Debe ingresar un nombre")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotBlank(message = "Debe ingresar un apellido")
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido", nullable = true)
    private String segundoApellido;

    @NotBlank(message = "Debe ingresar un nombre de usuario")
    @Column(name = "nombre_usuario", nullable = false)
    private String username;

    @NotBlank (message = "Debe ingresar una contraseña")
    @Column (nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Se debe ingresar un rol")
    @Column(name = "rol", nullable = false)
    private Rol rol;

    @Column(name = "estado", nullable = false)
    private Boolean activo;

    @NotBlank(message = "Se debe ingresar un documento de identidad")
    @Size(min = 8, max = 9, message = "El dni debe tener exactamente 8 caracteres")
    @Column(name = "documento_identidad", nullable = false)
    private String documentoIdentidad;

    @Column(name = "tipo_documento_identidad", nullable = false)
    private String tipoDocumentoIdentidad;

    public Usuario() {
    }

    public Usuario(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.primerApellido = builder.primerApellido;
        this.segundoApellido = builder.segundoApellido; // Opcional
        this.rol = builder.rol;
        this.activo = builder.activo;
        this.username = builder.username;
        this.password = builder.password;
        this.documentoIdentidad = builder.documentoIdentidad;
        this.tipoDocumentoIdentidad = builder.tipoDocumentoIdentidad;
    }

    public static class Builder {
        private int id;
        private String nombre;
        private String primerApellido;
        private String segundoApellido;
        private String username;
        private String password;
        private Rol rol;
        private Boolean activo;
        private String documentoIdentidad;
        private String tipoDocumentoIdentidad;

        
        public Builder() {
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder primerApellido(String primerApellido) {
            this.primerApellido = primerApellido;
            return this;
        }

        public Builder segundoApellido(String segundoApellido) {
            this.segundoApellido = segundoApellido;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder rol(Rol rol) {
            this.rol = rol;
            return this;
        }

        public Builder activo(boolean activo) {
            this.activo = activo;
            return this;
        }

        public Builder documentoIdentidad(String documentoIdentidad) {
            this.documentoIdentidad = documentoIdentidad;
            return this;
        }

        public Builder tipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
            this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }

    }

    // Getters y Setters
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

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIndetidad) {
        this.documentoIdentidad = documentoIndetidad;
    }

    public String getTipoDocumentoIdentidad() {
        return tipoDocumentoIdentidad;
    }

    public void setTipoDocumentoIdentidad(String tipoDocumentoIdentidad) {
        this.tipoDocumentoIdentidad = tipoDocumentoIdentidad;
    }

}
