package com.aparkautepino.aparkautepino.Controller;

import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;
import com.aparkautepino.aparkautepino.Model.Service.MovimientoService;
import com.aparkautepino.aparkautepino.Model.Service.ReportePdfService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovimientosController {

    private final MovimientoService movimientoService;
    private final ReportePdfService reportePdfService;

    MovimientosController(MovimientoService movimientoService,
            ReportePdfService reportePdfService) {
        this.movimientoService = movimientoService;
        this.reportePdfService = reportePdfService;
    }

    @GetMapping("/historial")
    public String historialMovimientos(@PageableDefault(size = 10) Pageable pageable,
            Model model) {

        model.addAttribute("movimientos", movimientoService.buscar(null, null, null, null, pageable));

        return "historial";
    }

    @GetMapping("/filtrar")
    public String listar(
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String dni,
            @PageableDefault(size = 10) Pageable pageable,
            Model model) {

        Page<Movimiento> page = movimientoService.buscar(desde, hasta, placa, dni, pageable);

        model.addAttribute("movimientos", page);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);
        model.addAttribute("placa", placa);
        model.addAttribute("dni", dni);
        return "historial";
    }

    @GetMapping("/reportes/movimientos/pdf")
    public ResponseEntity<byte[]> descargarReporteIngresos(@RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate hasta,
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String dni,
            @PageableDefault(size = 10) Pageable pageable,
            Model model)
            throws IOException {

                List<Movimiento> movimientos = movimientoService.buscar(desde, hasta, placa, dni, pageable).toList();

                System.out.println("Voy a listar los movimientos");
                for (Movimiento movimiento : movimientos) {
                    System.out.println("Lista de movimientos: ");
                    System.out.println(movimiento.getVehiculo().getPlaca());
                }

        // 2. Generar el PDF
        byte[] pdfBytes = reportePdfService.generarReporteIngresos(movimientos);

        // 3. Configurar la respuesta HTTP para la descarga
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "reporte-ingresos.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

}
