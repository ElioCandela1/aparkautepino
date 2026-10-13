package com.aparkautepino.aparkautepino.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.aparkautepino.aparkautepino.Model.Service.SolicitudAbonadoService;

@Controller
public class GestionUsuariosController {

     private final SolicitudAbonadoService solicitudAbonadoService; 

    public GestionUsuariosController(SolicitudAbonadoService solicitudAbonadoService) {
        this.solicitudAbonadoService = solicitudAbonadoService;
    }

    @GetMapping("/gestion-usuarios")
    public String mostrarVista() {
        return "gestion-usuarios";
    }

    
    
    

}
