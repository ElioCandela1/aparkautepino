package com.aparkautepino.aparkautepino.Model.Service.ValidacionIngreso;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Component;

import com.aparkautepino.aparkautepino.Model.Entity.EstadoMovimiento;
import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;
import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;
import com.aparkautepino.aparkautepino.Model.Repository.MovimientoRepository;
import com.aparkautepino.aparkautepino.Model.Service.MovimientoService;
import com.aparkautepino.aparkautepino.Model.Service.VehiculoService;

import lombok.AllArgsConstructor;

@Component 
@AllArgsConstructor 
public class ValidarIngresoActivo implements ValidacionIngreso{

    private final MovimientoRepository movimientoRepository;

    @Override
    public void validar(Vehiculo vehiculo) {
        System.out.println("Validando que no haya ingresos activos");

        Movimiento movimiento = movimientoRepository.findByVehiculoAndEstado(vehiculo, EstadoMovimiento.ACTIVO).orElse(null);

        if(movimiento != null){
            throw new IllegalStateException("El vehiculo cuenta con un ingreso activo");
        }        
    }



}
