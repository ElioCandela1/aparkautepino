package com.aparkautepino.aparkautepino.Controller;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.UsuarioSistema;
import com.aparkautepino.aparkautepino.Model.Service.AbonadoService;
import com.aparkautepino.aparkautepino.Model.Service.SolicitudAbonadoService;
import com.aparkautepino.aparkautepino.Model.Service.UsuarioSistemaService;

@Controller
public class GestionUsuariosController {

    UsuarioSistemaService usuarioSistemaService;

    public GestionUsuariosController(UsuarioSistemaService usuarioSistemaService) {
        this.usuarioSistemaService = usuarioSistemaService;
    }



    @GetMapping("/gestion-usuarios")
    public String mostrarVista(@RequestParam(defaultValue = "0") int pagina,
            Model model) {

        Page<UsuarioSistema> usuariosSistema = usuarioSistemaService.listarAbodanos(pagina);

        model.addAttribute("usuarios", usuariosSistema);

        return "gestion-usuarios";
    }


}
