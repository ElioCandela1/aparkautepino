package com.aparkautepino.aparkautepino.Controller;

import java.lang.ProcessBuilder.Redirect;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aparkautepino.aparkautepino.Model.Entity.Usuario;
import com.aparkautepino.aparkautepino.Model.Service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UsuarioController {

    // Atributo global para este controller
    @ModelAttribute("usuario")
    public Usuario cargarUsuario() {
        return new Usuario();
    }

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // mostrar ventana login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/crearUsuario")
    public String mostrarCrearUsuario() {
        return "crear-usuario";
    }

    @PostMapping("/guardarUsuario")
    public String crearUsuario(@Valid @ModelAttribute Usuario usuario,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
        Model model) {

        // validacion
        if (bindingResult.hasErrors()) {
            System.out.println("Entré en bindinResult");
            String mensaje = bindingResult.getAllErrors().get(0).getDefaultMessage();
        

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", mensaje);
            redirectAttributes.addFlashAttribute("usuario",usuario);

            return "redirect:/crearUsuario";
        }

        // Guardar datos

        try {
            System.out.println("Entré en try");
            usuarioService.crearUsuario(usuario);

            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", "Usuario guardado con exito");

            return "redirect:/crearUsuario"; 

        } catch (Exception e) {

            System.out.println("Entré en catch");

            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("tipoModal", "notificacion");
            redirectAttributes.addFlashAttribute("mensaje", e.getMessage());

            return "redirect:/crearUsuario";
        }  
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }

    

}
