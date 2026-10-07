
package com.aparkautepino.aparkautepino.Model.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarCorreoDesbloqueo(String destinatario, String token) {
        String url = "http://localhost:8090/reset-password?token=" + token;
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject("Cuenta bloqueada - Restablecer contraseña");
        mensaje.setText("Tu cuenta ha sido bloqueada por 3 intentos fallidos.\n" +
                        "Para desbloquearla, restablece tu contraseña en el siguiente enlace:\n" + url);
        mailSender.send(mensaje);
    }
}