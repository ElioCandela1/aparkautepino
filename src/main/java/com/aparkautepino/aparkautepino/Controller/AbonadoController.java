package com.aparkautepino.aparkautepino.Controller;

import java.util.NoSuchElementException;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Repository.TipoDocumentoRepository;
import com.aparkautepino.aparkautepino.Model.Service.AbonadoService;
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

    public AbonadoController(AbonadoService abonadoService, TipoDocumentoRepository tipoDocumentoRepository) {
        this.abonadoService = abonadoService;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
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

}
