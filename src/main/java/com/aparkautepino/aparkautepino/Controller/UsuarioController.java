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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aparkautepino.aparkautepino.Model.Dto.UsuarioFormDto;
import com.aparkautepino.aparkautepino.Model.Entity.Abonado;
import com.aparkautepino.aparkautepino.Model.Entity.UsuarioSistema;
import com.aparkautepino.aparkautepino.Model.Repository.TipoDocumentoRepository;
import com.aparkautepino.aparkautepino.Model.Repository.UsuarioSistemaRepository;
import com.aparkautepino.aparkautepino.Model.Service.BusquedaService;
import com.aparkautepino.aparkautepino.Model.Service.UsuarioSistemaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsuarioController {

    // Atributo global para este controller
    @ModelAttribute("usuarioForm")
    public UsuarioFormDto cargarUsuarioSistema() {
        return new UsuarioFormDto();
    }

    private final UsuarioSistemaService usuarioService;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final BusquedaService busquedaService;

    public UsuarioController(UsuarioSistemaService usuarioService,
            TipoDocumentoRepository tipoDocumentoRepository,
            BusquedaService busquedaService) {
        this.usuarioService = usuarioService;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.busquedaService = busquedaService;
    }

    // Mostrar ventana login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/crearUsuario")
    public String mostrarCrearUsuario(Model model) {
        model.addAttribute("tiposDocumento", tipoDocumentoRepository.findAll());
        return "crear-usuario";
    }

    @GetMapping("/actualizar-usuario/{id}")
    public String formularioEditarAbonado(@PathVariable int id,
            Model model) {
        UsuarioFormDto usuarioForm = usuarioService.obtenerFormulario(id);

        model.addAttribute("usuarioForm", usuarioForm);
        model.addAttribute("tiposDocumento", tipoDocumentoRepository.findAll());
        return "crear-usuario";
    }

    @PostMapping("/guardarUsuario")
    public String crearUsuario(@Valid @ModelAttribute("usuarioForm") UsuarioFormDto form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        if (bindingResult.hasErrors()) {
            String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            redirectAttributes.addFlashAttribute("usuarioForm", form);
            return "redirect:/crearUsuario";
        }

        try {

            boolean esEdicion = form.getIdUsuario() != null;

            if (esEdicion) {
                usuarioService.actualizarUsuario(form);
            } else {
                usuarioService.crearUsuario(form);
            }

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje",
                    esEdicion ? "Usuario actualizado con éxito" : "Usuario guardado con éxito");
            return "redirect:/crearUsuario";

        } catch (NoSuchElementException e) {

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/crearUsuario";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());
            return "redirect:/crearUsuario";
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    @GetMapping("/reset-password")
    public String mostrarFormulario(@RequestParam("token") String token, Model model) {

        if (!usuarioService.tokenValido(token)) {
            model.addAttribute("error", "El enlace es inválido o ha expirado.");
            return "reset-password-error"; // vista de error
        }

        model.addAttribute("token", token);
        return "reset-password"; // formulario
    }

    @PostMapping("/reset-password")
    public String cambiarPassword(@RequestParam("token") String token,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas no coinciden.");
            return "redirect:/reset-password?token=" + token;
        }

        try {
            usuarioService.resetearPasswordConToken(token, password);
            redirectAttributes.addFlashAttribute("mensaje",
                    "Contraseña actualizada. Ya puedes iniciar sesión.");
            return "redirect:/login";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/reset-password?token=" + token;
        }
    }

    @GetMapping("/filtrar-usuarios")
public String filtrar(
        @RequestParam(required = false) String documentoIdentidad,
        @RequestParam(defaultValue = "0") int pagina,
        Model model) {

    Page<UsuarioSistema> usuarios;

    if (documentoIdentidad == null || documentoIdentidad.isBlank()) {
        // Sin DNI: página vacía, pero con la estructura de Page intacta
        System.out.println("Mostrando pagina vacia");
        usuarios = Page.empty(PageRequest.of(pagina, 10));
    } else {
        UsuarioSistema usuarioimpreso = busquedaService.bucarUsuarioporDocumentoIdentidad(documentoIdentidad).orElse(null);
        System.out.println("Usuario conseguido: " + usuarioimpreso);
        
        usuarios = busquedaService.bucarUsuarioporDocumentoIdentidad(documentoIdentidad)
                .map(u -> (Page<UsuarioSistema>) new PageImpl<>(List.of(u)))
                .orElseGet(() -> {
                    model.addAttribute("tipoModal", "notificacion");
                    model.addAttribute("mensaje", "No se encontró usuario con DNI " + documentoIdentidad);
                    return Page.empty();
                });
    }

    model.addAttribute("documentoIdentidad", documentoIdentidad);
    System.out.println("Ususario: " + usuarios.getContent().toString());
    model.addAttribute("usuarios", usuarios); // ← ahora es un Page
    return "gestion-usuarios";
}

}
