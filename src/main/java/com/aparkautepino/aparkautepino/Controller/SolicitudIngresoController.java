package com.aparkautepino.aparkautepino.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aparkautepino.aparkautepino.Model.Entity.SolicitudAbonado;
import com.aparkautepino.aparkautepino.Model.Service.SolicitudAbonadoService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/SolicitudIngreso")
public class SolicitudIngresoController {

    private final SolicitudAbonadoService solicitudAbonadoService;

    public SolicitudIngresoController(SolicitudAbonadoService solicitudAbonadoService) {
        this.solicitudAbonadoService = solicitudAbonadoService;
    }

    @PostMapping("/aprobar")
    @Transactional
    public String aprobarSolicitud(
            @Valid @ModelAttribute("solicitudAbonado") SolicitudAbonado solicitudAbonado,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            return "redirect:/gestion-usuarios";
        }

        try {
            solicitudAbonadoService.aceptarSolicitud(solicitudAbonado);
            redirectAttributes.addFlashAttribute("abrirModal", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        return "redirect:/gestion-usuarios";
    }

    @PostMapping("/desaprobar")
    public String desaprobarSolicitud(@ModelAttribute("solicitudAbonado") SolicitudAbonado solicitudAbonado,
            RedirectAttributes redirectAttributes) {

        try {
            solicitudAbonadoService.rechazarSolicitud(solicitudAbonado);
            redirectAttributes.addFlashAttribute("abrirModal", true);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/gestion-usuarios";
    }

}
