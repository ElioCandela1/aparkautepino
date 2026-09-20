package com.aparkautepino.aparkautepino.Model.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UsuarioController {

    //mostrar ventana login
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    

}
