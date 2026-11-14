package com.aparkautepino.aparkautepino.Model.Service.ValidacionIngreso;

import java.util.NoSuchElementException;

import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;
import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;
import com.aparkautepino.aparkautepino.Model.Service.MovimientoService;
import com.aparkautepino.aparkautepino.Model.Service.VehiculoService;

public class ValidarIngresoActivo implements ValidacionIngreso{

    private final MovimientoService movimientoService;

    



    public ValidarIngresoActivo(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @Override
    public void validar(Vehiculo vehiculo) {

        Movimiento movimiento = movimientoService.buscarMovimientoActivo(vehiculo).orElse(null);

        if(movimiento != null){
            throw new IllegalStateException("El vehiculo cuenta con un ingreso activo");
        }        
    }



}
