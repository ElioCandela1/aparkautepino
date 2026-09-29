package com.aparkautepino.aparkautepino.Model.Dto;

import com.aparkautepino.aparkautepino.Model.Entity.Rol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class UsuarioFormDto {
  // --- Campos de Persona ---
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private Long idTipoDocumento;      // se elige por id
    private String numeroDocumento;
    private String correo;
    private String telefono;

    // --- Campos de UsuarioSistema ---
    private String username;
    private String password;
    private Rol rol;
    private Boolean activo = true;
}
