package com.aparkautepino.aparkautepino.Controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aparkautepino.aparkautepino.Model.Dto.UsuarioFormDto;
import com.aparkautepino.aparkautepino.Model.Entity.UsuarioSistema;
import com.aparkautepino.aparkautepino.Model.Repository.TipoDocumentoRepository;
import com.aparkautepino.aparkautepino.Model.Repository.UsuarioSistemaRepository;
import com.aparkautepino.aparkautepino.Model.Service.UsuarioSistemaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UsuarioController {

    // Atributo global para este controller
    @ModelAttribute("usuarioForm")
    public UsuarioFormDto cargarUsuarioSistema() {
        return new UsuarioFormDto();
    }

    private final UsuarioSistemaService usuarioService;
    private final UsuarioSistemaRepository usuarioRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;

    public UsuarioController(UsuarioSistemaService usuarioService,
            UsuarioSistemaRepository usuarioRepository,
            TipoDocumentoRepository tipoDocumentoRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
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
        usuarioService.crearUsuario(form);
        redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
        redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado con éxito");
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
            return "reset-password-error";  // vista de error
        }

        model.addAttribute("token", token);
        return "reset-password";  // formulario
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
     

}
