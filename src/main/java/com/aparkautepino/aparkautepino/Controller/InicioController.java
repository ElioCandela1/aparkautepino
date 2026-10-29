package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aparkautepino.aparkautepino.Model.Entity.CategoriaEspacio;
import com.aparkautepino.aparkautepino.Model.Service.EspacioService;


@Controller 
public class InicioController {

    private final EspacioService espacioService;

    

    public InicioController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }

    @GetMapping("/inicio")
    public String mostrarInicio(Model model) {
        

        model.addAttribute("capacidadVehiculosPesado", espacioService.contarCapacidadTotal(CategoriaEspacio.GRANDE));
        model.addAttribute("disponiblesVehiculosPesados", espacioService.contarEspaciosDisponibles(CategoriaEspacio.GRANDE));
        model.addAttribute("ocupadosVehiculosPesados", espacioService.contarEspaciosOcupados(CategoriaEspacio.GRANDE));
        
        model.addAttribute("capacidadVehiculosLigeros", espacioService.contarCapacidadTotal(CategoriaEspacio.LIGERO));
        model.addAttribute("disponiblesVehiculosLigeros", espacioService.contarEspaciosDisponibles(CategoriaEspacio.LIGERO));
        model.addAttribute("ocupadosVehiculosLigeros", espacioService.contarEspaciosOcupados(CategoriaEspacio.LIGERO));
        
        return "inicio";
    }

    @GetMapping("/")
    public String mostrarVentanaRoot() {
        return "login";
    }
    

}
