package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller 
public class EspaciosController {

    @GetMapping("/espacios")
    public String mostrarEspacios() {
        return "espacios";
    }
    
}
