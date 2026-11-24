package com.aparkautepino.aparkautepino.Model.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaDto {

    private Integer idPersona;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private int idTipoDocumento; // se elige por id
    private String numeroDocumento;
    private String correo;
    private String telefono;
}
