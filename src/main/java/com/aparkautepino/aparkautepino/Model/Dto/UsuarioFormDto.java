package com.aparkautepino.aparkautepino.Model.Dto;

import com.aparkautepino.aparkautepino.Model.Entity.Rol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class UsuarioFormDto extends PersonaDto{

  // --- Campos de UsuarioSistema ---
    private String username;
    private String password;
    private Rol rol;
    private Boolean activo = true;
}
