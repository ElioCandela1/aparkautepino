package com.aparkautepino.aparkautepino.Model.Entity;

public enum TipoAbonado {
    ESTUDIANTE,
    DOCENTE;

    public static TipoAbonado fromString(String valor) {
        try {
            return TipoAbonado.valueOf(valor.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("TipoAbonado inválido: " + valor);
        }
    }
}
