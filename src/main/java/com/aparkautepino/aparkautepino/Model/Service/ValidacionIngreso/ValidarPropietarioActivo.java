package com.aparkautepino.aparkautepino.Model.Service.ValidacionIngreso;

import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;

public class ValidarPropietarioActivo implements ValidacionIngreso{

    @Override
    public void validar(Vehiculo vehiculo) {
        if(!vehiculo.getPropietario().getActivo()){
            throw new IllegalStateException("El propietario de este vehiculo se encuentra inhabilitado");
        }
    }

}
