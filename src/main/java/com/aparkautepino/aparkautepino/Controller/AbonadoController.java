package com.aparkautepino.aparkautepino.Controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.UsuarioSistema;
import com.aparkautepino.aparkautepino.Model.Repository.TipoDocumentoRepository;
import com.aparkautepino.aparkautepino.Model.Service.AbonadoService;
import com.aparkautepino.aparkautepino.Model.Service.BusquedaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AbonadoController {

    @ModelAttribute("usuarioForm")
    public Abonado cargarUsuarioSistema() {
        return new Abonado();
    }

    private final AbonadoService abonadoService;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final BusquedaService busquedaService;

    public AbonadoController(AbonadoService abonadoService, TipoDocumentoRepository tipoDocumentoRepository,
            BusquedaService busquedaService) {
        this.abonadoService = abonadoService;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.busquedaService = busquedaService;
    }

    @GetMapping("/gestion-abonados")
    public String mostrarModuloAbonados(@RequestParam(defaultValue = "0") int pagina,
            Model model) {

        Page<Abonado> abonados = abonadoService.listarAbodanos(pagina);
        model.addAttribute("abonados", abonados);

        return "gestion-abonados";
    }

    @GetMapping("/actualizar-abonado/{id}")
    public String formularioAbonado(@PathVariable int id,
            Model model) {
        Abonado abonado = abonadoService.buscarAbonado(id);
        model.addAttribute("abonado", abonado);
        model.addAttribute("tiposDocumento", tipoDocumentoRepository.findAll());
        return "crear-abonado";
    }

    @PostMapping("guardar-abonado/{id}")
    public String actualizarAbonado(@PathVariable int id,
            @ModelAttribute Abonado abonado,
            RedirectAttributes redirectAttributes) {

        try {
            abonadoService.actualizarAbonado(abonado, id);
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            redirectAttributes.addFlashAttribute("abonado", abonado);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar abonado");
            redirectAttributes.addFlashAttribute("abonado", abonado);
        }

        return "redirect:/actualizar-abonado/" + id;
    }

    @GetMapping("/filtrar-abonados")
    public String filtrar(
            @RequestParam(required = false) String documentoIdentidad,
            @RequestParam(defaultValue = "0") int pagina,
            Model model) {

        Page<Abonado> abonados;

        if (documentoIdentidad == null || documentoIdentidad.isBlank()) {
            // Sin DNI: página vacía, pero con la estructura de Page intacta
            abonados = Page.empty(PageRequest.of(pagina, 10));
        } else {
            Abonado encontrado = busquedaService.bucarporDocumentoIdentidad(documentoIdentidad);

            if (encontrado != null) {
                abonados = new PageImpl<>(List.of(encontrado));
            } else {
                model.addAttribute("tipoModal", "notificacion");
                model.addAttribute("mensaje", "No se encontró abonado con DNI " + documentoIdentidad);
                abonados = Page.empty();
            }
        }

        model.addAttribute("documentoIdentidad", documentoIdentidad);
        model.addAttribute("abonados", abonados); // ← ahora es un Page
        return "gestion-abonados";
    }

}
