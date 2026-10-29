package com.aparkautepino.aparkautepino.Controller;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;
import com.aparkautepino.aparkautepino.Model.Service.VehiculoService;



import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VehiculosController {

    private final VehiculoService vehiculoService;

    

    public VehiculosController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    // Atributo global para este controller
    @ModelAttribute("vehiculo")
    public Vehiculo cargarVehiculo() {
        return new Vehiculo();
    }

    @GetMapping("/gestion-vehiculos")
    public String gestionVehiculos(@RequestParam(defaultValue = "0") int pagina,
                                    Model model) {
        
        Page<Vehiculo> vehiculos = vehiculoService.listarVehiculos(pagina);

        model.addAttribute("vehiculos", vehiculos);


        return "gestion-vehiculos";
    }

    @GetMapping("/crear-vehiculos")
    public String crearVehiculos() {
        return "crear-vehiculos";
    }

}
