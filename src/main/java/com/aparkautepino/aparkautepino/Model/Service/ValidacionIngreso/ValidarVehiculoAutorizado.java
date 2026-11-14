package com.aparkautepino.aparkautepino.Model.Service.ValidacionIngreso;

import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;

public class ValidarVehiculoAutorizado implements ValidacionIngreso {

    @Override
    public void validar(Vehiculo vehiculo) {
       if(!vehiculo.getActivo()){
        throw new IllegalStateException("Vehiculo Inhabilitado");
       }
    }

}
