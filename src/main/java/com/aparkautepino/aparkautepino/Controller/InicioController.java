package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller 
public class InicioController {

    @GetMapping("/inicio")
    public String mostrarInicio(Model model) {
        //model.addAttribute(null, model);
        return "inicio";
    }

    @GetMapping("/")
    public String mostrarVentanaRoot() {
        return "login";
    }
    

}
