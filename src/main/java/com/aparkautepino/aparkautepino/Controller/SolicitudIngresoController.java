package com.aparkautepino.aparkautepino.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aparkautepino.aparkautepino.Model.Entity.SolicitudAbonado;
import com.aparkautepino.aparkautepino.Model.Service.SolicitudAbonadoService;

import jakarta.transaction.Transactional;
@Controller
public class SolicitudIngresoController {

    private final SolicitudAbonadoService solicitudAbonadoService;

    public SolicitudIngresoController(SolicitudAbonadoService solicitudAbonadoService) {
        this.solicitudAbonadoService = solicitudAbonadoService;
    }

    @ModelAttribute("solicitudes")
    public List<SolicitudAbonado> cargarSolicitudes() {
        return solicitudAbonadoService.listarSolicitudesPedientes();
    }

    @PostMapping("/solicitudes/{id}/aprobar")
    @Transactional
    public String aprobarSolicitud(
            @PathVariable int id,
            RedirectAttributes redirectAttributes) {
        try {
            solicitudAbonadoService.aceptarSolicitud(id);
            redirectAttributes.addFlashAttribute("abrirModal", "solicitudes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        return "redirect:/solicitudes-usuarios";
    }

    @PostMapping("/solicitudes/{id}/rechazar")
    public String desaprobarSolicitud(@PathVariable int id,
            RedirectAttributes redirectAttributes,
             @RequestParam("motivoRechazo") String motivoRechazo) {

        try {
            solicitudAbonadoService.rechazarSolicitud(id, motivoRechazo);
            redirectAttributes.addFlashAttribute("abrirModal", "solicitudes");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }
        return "redirect:/solicitudes-usuarios";
    }

    
    @GetMapping("/solicitudes-usuarios")
    public String mostrarSolicitudesUsuarios(Model model) {

        model.addAttribute("abrirModal", "solicitudes");
        return "gestion-usuarios";
    }
}
