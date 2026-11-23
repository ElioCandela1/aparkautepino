package com.aparkautepino.aparkautepino.Model.Service.ValidacionIngreso;

import org.springframework.stereotype.Component;

import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;

@Component 
public class ValidarVehiculoAutorizado implements ValidacionIngreso {

    @Override
    public void validar(Vehiculo vehiculo) {
        System.out.println("Validando que el vehiculo este autorizado");
       if(!vehiculo.getActivo()){
        throw new IllegalStateException("Vehiculo Inhabilitado");
       }
    }

}
