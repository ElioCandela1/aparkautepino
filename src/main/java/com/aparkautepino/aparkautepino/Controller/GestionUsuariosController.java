package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GestionUsuariosController {

    @GetMapping("/gestion-usuarios")
    public String mostrarVista() {
        return "gestion-usuarios";
    }
    

}
