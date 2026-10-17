package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;
import com.aparkautepino.aparkautepino.Model.Service.MovimientoService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping("/ingresos/manual")
    @Transactional
    public String ingresarVehiculo(@Valid @ModelAttribute("movimiento") Movimiento movimiento,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            return "redirect:/ingresos/manual";
        }

        try {
            movimientoService.ingresarVehiculo(movimiento);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        return "redirect:/ingresos/manual";
    }

    @GetMapping("/ingresos/automatico/{numeroDocumento}")
    @Transactional
    @ResponseBody  
    public String ingresarVehiculo(@PathVariable String numeroDocumento,
        RedirectAttributes redirectAttributes
    ) {
        try {
            movimientoService.ingresarVehiculo(numeroDocumento);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "Ok";
    }

    @PostMapping("/salidas/manual")
    @Transactional
    public String registrarSalidaVehiculo(@Valid @ModelAttribute("movimiento") Movimiento movimiento,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            return "redirect:/ingresos/manual";
        }

        try {
            movimientoService.registarSalidaVehiculo(movimiento);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        return "redirect:/ingresos/manual";
    }
    
    @GetMapping("/salidas/automatico/{numeroDocumento}")
    @Transactional
    @ResponseBody  
    public void registrarSalidaVehiculo(@PathVariable String numeroDocumento,
        RedirectAttributes redirectAttributes
    ) {
        try {
            movimientoService.registarSalidaVehiculo(numeroDocumento);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            throw e;
        }
        
    }

}
