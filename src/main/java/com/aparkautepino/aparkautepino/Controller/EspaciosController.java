package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aparkautepino.aparkautepino.Model.Entity.CategoriaEspacio;
import com.aparkautepino.aparkautepino.Model.Service.EspacioService;


@Controller 
public class EspaciosController {

    private final EspacioService espacioService;

    

    public EspaciosController(EspacioService espacioService) {
        this.espacioService = espacioService;
    }



    @GetMapping("/espacios")
    public String mostrarEspacios(Model model) {

        model.addAttribute("espaciosSectorA", espacioService.listarEspaciosPorSector("A"));
        model.addAttribute("espaciosSectorB", espacioService.listarEspaciosPorSector("B"));
        model.addAttribute("capacidadTotalMotos", espacioService.contarCapacidadTotal(CategoriaEspacio.LIGERO));
        model.addAttribute("espaciosDisponiblesMotos", espacioService.contarEspaciosDisponibles(CategoriaEspacio.LIGERO));
        model.addAttribute("espaciosOcupadosMotos", espacioService.contarEspaciosOcupados(CategoriaEspacio.LIGERO));
        model.addAttribute("capacidadTotalVehiculosPequenos", espacioService.contarCapacidadTotal(CategoriaEspacio.PEQUENO));
        model.addAttribute("espaciosDisponiblesVehiculosPequenos", espacioService.contarEspaciosDisponibles(CategoriaEspacio.PEQUENO));
        model.addAttribute("espaciosOcupadosVehiculosPequenos", espacioService.contarEspaciosOcupados(CategoriaEspacio.PEQUENO));
                
        
        return "espacios";
    }
    
}
