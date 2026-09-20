package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class GestionUsuariosController {

    @GetMapping("/gestion-usuarios")
    public String mostrarVista() {
        return "gestion-usuarios";
    }
    

}
