package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class GestionVehiculosController {

    @GetMapping("/gestion-vehiculos")
    public String gestionVehiculos() {
        return "gestion-vehiculos";
    }
    
    @GetMapping("/crear-vehiculos")
    public String crearVehiculos() {
        return "crear-vehiculos";
    }
    
}
