package com.aparkautepino.aparkautepino.Model.Dto;

import java.util.List;

import lombok.Data;

@Data
public class SolicitudesPayloadDTO {

    private int fila;
    private String timestamp;
    private  List<Object> datos;
}
