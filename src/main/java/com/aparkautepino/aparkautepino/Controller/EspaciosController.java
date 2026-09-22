package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller 
public class EspaciosController {

    @GetMapping("/espacios")
    public String mostrarEspacios() {
        return "espacios";
    }
    
}
