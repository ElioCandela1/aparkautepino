package com.aparkautepino.aparkautepino.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;
import com.aparkautepino.aparkautepino.Model.Service.BusquedaService;
import com.aparkautepino.aparkautepino.Model.Service.MovimientoService;
import com.aparkautepino.aparkautepino.Model.Service.AsignacionEspacioStrategy.AsignarEspacioService;

import jakarta.validation.Valid;
import tools.jackson.databind.ObjectMapper;

@Controller
public class SalidaVehiculoController {

    private final MovimientoService movimientoService;
    private final BusquedaService busquedaService;
    private final AsignarEspacioService asignarEspacioService;

    public SalidaVehiculoController(BusquedaService busquedaService, MovimientoService movimientoService,
            AsignarEspacioService asignarEspacioService) {
        this.movimientoService = movimientoService;
        this.busquedaService = busquedaService;
        this.asignarEspacioService = asignarEspacioService;
    }

    // Atributo global para este controller
    @ModelAttribute
    public Movimiento cargarMovimiento() {
        return new Movimiento();
    }

    @GetMapping("/salida-vehiculo")
    public String mostrarSalidaVehiculo() {
        return "salida-vehiculo";
    }

    @PostMapping("/salidas/manual")
    public String registrarSalidaVehiculo(@Valid @ModelAttribute("movimiento") Movimiento movimiento,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            return "redirect:/ingresos/manual";
        }

         ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(movimiento));

        try {
            movimientoService.registarSalidaVehiculo(movimiento.getId());
        } catch (Exception e) {
            System.out.println(e);
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
        }

        return "salida-vehiculo";
    }

    @GetMapping("/salidas/automatico/{numeroDocumento}")
    @Transactional
    @ResponseBody
    public void registrarSalidaVehiculo(@PathVariable String numeroDocumento,
            RedirectAttributes redirectAttributes) {
        try {
            movimientoService.registarSalidaVehiculo(numeroDocumento);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            throw e;
        }

    }

    @GetMapping("/salidas/buscar-vehiculos")
    public String buscarVehiculos(@RequestParam(name = "criterio", required = false) String criterio,
            @ModelAttribute("movimiento") Movimiento movimiento,
            Model model) {

        Movimiento encontrado = busquedaService.buscarMovimientoActivo(criterio);


        System.out.println(encontrado.getAbonado().getPersona().getNombreCompleto());

        movimiento.setId(encontrado.getId());
        movimiento.setAbonado(encontrado.getAbonado());
        movimiento.setEspacio(encontrado.getEspacio());
        movimiento.setFechaIngreso(encontrado.getFechaIngreso());
        movimiento.setVehiculo(encontrado.getVehiculo());

        model.addAttribute("fechaActual", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy' 'HH:mm")));

        return "salida-vehiculo";
    }
}
