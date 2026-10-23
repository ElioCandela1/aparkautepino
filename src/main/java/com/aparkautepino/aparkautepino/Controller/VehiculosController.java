package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;

import ch.qos.logback.core.model.Model;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VehiculosController {

    // Atributo global para este controller
    @ModelAttribute("vehiculo")
    public Vehiculo cargarUsuarioSistema() {
        return new Vehiculo();
    }

    @GetMapping("/gestion-vehiculos")
    public String gestionVehiculos() {
        return "gestion-vehiculos";
    }

    @GetMapping("/crear-vehiculos")
    public String crearVehiculos() {
        return "crear-vehiculos";
    }

    

}
