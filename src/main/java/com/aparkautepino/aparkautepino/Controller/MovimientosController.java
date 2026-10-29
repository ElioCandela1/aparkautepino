package com.aparkautepino.aparkautepino.Controller;

import com.aparkautepino.aparkautepino.Model.Service.MovimientoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller 
public class MovimientosController {

    private final MovimientoService movimientoService;

    MovimientosController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @GetMapping("/historial")
    public String historialMovimientos(@RequestParam(defaultValue = "0") int pagina,
    Model model) {

        model.addAttribute("movimientos", movimientoService.listarMovimientos(pagina));

        return "historial";
    }
    
}
