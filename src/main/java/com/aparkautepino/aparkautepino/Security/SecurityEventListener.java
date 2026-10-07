package com.aparkautepino.aparkautepino.Security;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.aparkautepino.aparkautepino.Model.Service.UsuarioSistemaService;

@Component
public class SecurityEventListener {
    private final UsuarioSistemaService usuarioService;


    public SecurityEventListener(UsuarioSistemaService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @EventListener
    public void onFailure(AuthenticationFailureBadCredentialsEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        usuarioService.registrarIntentoFallido(username);
    }

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        usuarioService.resetearIntentos(username);
    }
}
