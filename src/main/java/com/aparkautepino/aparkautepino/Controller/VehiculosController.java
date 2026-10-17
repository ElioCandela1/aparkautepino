package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;

import jakarta.transaction.Transactional;

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
