package com.aparkautepino.aparkautepino.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.aparkautepino.aparkautepino.Model.Entity.Vehiculo;
import com.aparkautepino.aparkautepino.Model.Service.BusquedaService;
import com.aparkautepino.aparkautepino.Model.Service.VehiculoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VehiculosController {

    private final VehiculoService vehiculoService;
    private final BusquedaService busquedaService;

    public VehiculosController(VehiculoService vehiculoService,
            BusquedaService busquedaService) {
        this.vehiculoService = vehiculoService;
        this.busquedaService = busquedaService;
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

    @GetMapping("/actualizar-vehiculo/{id}")
    public String formularioActualizarVehiculo(@PathVariable int id,
            Model model) {
        Vehiculo vehiculo = vehiculoService.buscarVehiculo(id);
        model.addAttribute("vehiculo", vehiculo);
        return "crear-vehiculos";
    }

    @PostMapping("/guardar-vehiculo")
    public String crearUsuario(@Valid @ModelAttribute("vehiculo") Vehiculo vehiculo,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            redirectAttributes.addFlashAttribute("vehiculo", vehiculo);
            return "redirect:/crear-vehiculos";
        }

        try {

            Integer idVehiculo = vehiculo.getId();
            System.out.println(idVehiculo);

            boolean esEdicion = idVehiculo != null && idVehiculo != 0;

            if (esEdicion) {
                System.out.println("Voy a editar");
                vehiculoService.actualizarVehiculo(vehiculo);
            } else {
                System.out.println("Voy a crear uno nuevo");
                vehiculoService.crearVehiculo(vehiculo);
            }

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje",
                    esEdicion ? "Vehiculo actualizado con éxito" : "Vehiculo guardado con éxito");
            return "redirect:/crear-vehiculos";

        } catch (NoSuchElementException e) {

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/crearUsuario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/crear-vehiculos";
        }
    }

    @GetMapping("/filtrar-vehiculos")
    public String filtrar(
            @RequestParam(required = false) String parametro,
            @RequestParam(defaultValue = "0") int pagina,
            Model model) {

        Page<Vehiculo> vehiculos;

        if (parametro == null || parametro.isBlank()) {

            vehiculos = Page.empty(PageRequest.of(pagina, 10));
        } else {
            List<Vehiculo> encontrados = busquedaService.buscar(parametro);

            if (encontrados != null || !encontrados.isEmpty()) {
                vehiculos = new PageImpl<>(encontrados);
            } else {
                model.addAttribute("tipoModal", "notificacion");
                model.addAttribute("mensaje", "No se encontraron vehiculos");
                vehiculos = Page.empty();
            }
        }

        model.addAttribute("parametro", parametro);
        model.addAttribute("vehiculos", vehiculos); // ← ahora es un Page

        return  "gestion-vehiculos";
    }

}
