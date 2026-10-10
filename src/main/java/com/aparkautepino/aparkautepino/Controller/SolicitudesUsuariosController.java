package com.aparkautepino.aparkautepino.Controller;

import com.aparkautepino.aparkautepino.Model.Service.PersonaService;
import com.aparkautepino.aparkautepino.Model.Service.SolicitudAbonadoService;

import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aparkautepino.aparkautepino.Model.Dto.PersonaDto;
import com.aparkautepino.aparkautepino.Model.Dto.SolicitudesPayloadDTO;
import com.aparkautepino.aparkautepino.Model.Entity.Persona;
import com.aparkautepino.aparkautepino.Model.Entity.SolicitudAbonado;
import com.aparkautepino.aparkautepino.Model.Entity.TipoAbonado;
import com.aparkautepino.aparkautepino.Model.Entity.TipoVehiculo;
import com.aparkautepino.aparkautepino.Model.Repository.TipoDocumentoRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Transactional 
public class SolicitudesUsuariosController {

    private final SolicitudAbonadoService solicitudAbonadoService;
    private final TipoDocumentoRepository tipoDocumentoRepository;

    public SolicitudesUsuariosController(TipoDocumentoRepository tipoDocumentoRepository,
            SolicitudAbonadoService solicitudAbonadoService) {
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        
        this.solicitudAbonadoService = solicitudAbonadoService;
    }

    @PostMapping("/solicitudes-usuarios")
    public ResponseEntity<String> recibirSolicitud(@RequestBody SolicitudesPayloadDTO solicitud) {
        System.out.println("Fila recibida: " + solicitud.getFila());
        System.out.println("Timestamp: " + solicitud.getTimestamp());
        System.out.println("Datos: " + solicitud.getDatos());

        List<Object> datos = solicitud.getDatos();

        if (datos == null || datos.size() < 13) {
            return ResponseEntity.badRequest().body("Datos incompletos: se esperaban 13 campos");
        }

        // Acceso por posición (array plano)
        String correo = datos.get(0) != null ? datos.get(0).toString() : null;
        String nombres = datos.get(1) != null ? datos.get(1).toString() : null;
        String primerApellido = datos.get(2) != null ? datos.get(2).toString() : null;
        String segundoApellido = datos.get(3) != null ? datos.get(3).toString() : null;
        String tipoDocumento = datos.get(4) != null ? datos.get(4).toString() : null;
        String numeroDocumento = datos.get(5) != null ? datos.get(5).toString() : null;
        String telefono = datos.get(6) != null ? datos.get(6).toString() : null;
        String tipoAbonado = datos.get(7) != null ? datos.get(7).toString() : null;
        String tipoVehiculo = datos.get(8) != null ? datos.get(8).toString() : null;
        String placa = datos.get(9) != null ? datos.get(9).toString() : null;
        String marca = datos.get(10) != null ? datos.get(10).toString() : null;
        String color = datos.get(11) != null ? datos.get(11).toString() : null;
        String modelo = datos.get(12) != null ? datos.get(12).toString() : null;

        try {
            // Crear el DTO localmente (¡no como campo de clase!)
            PersonaDto personaDto = new PersonaDto();
            personaDto.setNombres(nombres);
            personaDto.setPrimerApellido(primerApellido);
            personaDto.setSegundoApellido(segundoApellido);
            personaDto.setIdTipoDocumento(tipoDocumentoRepository.findByCodigo(tipoDocumento)
                    .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado")).getId());
            personaDto.setNumeroDocumento(numeroDocumento);
            personaDto.setCorreo(correo);
            personaDto.setTelefono(telefono);

            // Crear solicitud
            SolicitudAbonado solicitudAbonado = new SolicitudAbonado();
            String timestampStr = solicitud.getTimestamp();
            Instant instant = Instant.parse(timestampStr);
            LocalDateTime fechaSolicitud = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
            solicitudAbonado.setTipoAbonado(TipoAbonado.fromString(tipoAbonado));
            solicitudAbonado.setTipoVehiculo(TipoVehiculo.fromString(tipoVehiculo));
            solicitudAbonado.setPlaca(placa);
            solicitudAbonado.setMarca(marca);
            solicitudAbonado.setModelo(modelo);
            solicitudAbonado.setColor(color);
            solicitudAbonado.setFechaSolicitud(fechaSolicitud);
            solicitudAbonadoService.registrarSolicitudCompleta(personaDto, solicitudAbonado);

            return ResponseEntity.ok("Recibido");
        } catch (DateTimeParseException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body("Formato de fecha inválido: " + e.getMessage());
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.badRequest().body("Error de validacion: " + e.getMessage());
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }

}
