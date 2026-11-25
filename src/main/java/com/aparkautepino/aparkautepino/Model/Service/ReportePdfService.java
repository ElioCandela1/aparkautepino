package com.aparkautepino.aparkautepino.Model.Service;

import com.aparkautepino.aparkautepino.Model.Entity.Movimiento;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.TemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportePdfService {

    private final TemplateEngine templateEngine; // Inyecta el bean 'pdfTemplateEngine'

    public ReportePdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generarReporteIngresos(List<Movimiento> listaMovimientos) throws IOException {
        // 1. Preparar el contexto de Thymeleaf con los datos
        Context context = new Context();
        context.setVariable("listaIngresos", listaMovimientos);
        context.setVariable("fechaGeneracion", new java.util.Date());

        // 2. Renderizar la plantilla a un String HTML
        String htmlContent = templateEngine.process("movimientos-pdf", context);

        // 3. Convertir el HTML a PDF usando OpenHTMLToPDF
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(htmlContent, null);
            builder.toStream(os);
            builder.run();
            return os.toByteArray();
        }
    }
}