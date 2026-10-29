package com.aparkautepino.aparkautepino.Controller;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Service.AbonadoService;
import com.aparkautepino.aparkautepino.Model.Service.SolicitudAbonadoService;

@Controller
public class GestionUsuariosController {

    private final SolicitudAbonadoService solicitudAbonadoService;
    private final AbonadoService abonadoService;

    public GestionUsuariosController(SolicitudAbonadoService solicitudAbonadoService, AbonadoService abonadoService) {
        this.solicitudAbonadoService = solicitudAbonadoService;
        this.abonadoService = abonadoService;
    }

    @GetMapping("/gestion-usuarios")
    public String mostrarVista(@RequestParam(defaultValue = "0") int pagina,
            Model model) {

        Page<Abonado> abonados = abonadoService.listarAbodanos(pagina);

        model.addAttribute("abonados", abonados);

        return "gestion-usuarios";
    }

}
