package com.aparkautepino.aparkautepino.Model.Entity;

public enum TipoVehiculo {
    AUTOMOVIL,
    MOTO,
    BICICLETA,
    SCOOTER;

    public static TipoVehiculo fromString(String valor) {
        try {
            valor.replace(" ", "");
            return TipoVehiculo.valueOf(valor.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("TipoVehiculo inválido: " + valor);
        }
    }
}
