package com.integrainmo.turnos;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class ComprobantePdfService {

    public byte[] generarPdf(Comprobante comprobante) {

        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, baos);

            document.open();

            Font titulo = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 12);

            document.add(new Paragraph("COMPROBANTE DE TURNOS", titulo));
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Empresa: " + comprobante.getEmpresa().getNombre(), normal));
            document.add(new Paragraph("Trabajador: " + comprobante.getTrabajador().getNombre(), normal));
            document.add(new Paragraph("Periodo: " + comprobante.getMes() + "/" + comprobante.getAnio(), normal));

            document.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(2);
            tabla.addCell("Fecha");
            tabla.addCell("Monto");

            double total = 0;

            for (Turno turno : comprobante.getTurnos()) {

                tabla.addCell(turno.getFecha().toString());
                tabla.addCell("$" + turno.getMonto());

                total += turno.getMonto();
            }

            document.add(tabla);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("TOTAL: $" + total, titulo));

            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }
}
