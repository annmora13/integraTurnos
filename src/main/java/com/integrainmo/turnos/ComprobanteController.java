package com.integrainmo.turnos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ComprobanteController {
    private final EmpresaRepository empresaRepository;
    private final ComprobanteRepository comprobanteRepository;
    private final TrabajadorRepository trabajadorRepository;

    @GetMapping("/empresas/{id}/comprobantes")
    public String listarComprobantes(@PathVariable Long id, Model model) {

        Empresa empresa = empresaRepository.findById(id).orElseThrow();

        List<Comprobante> comprobantes = comprobanteRepository.findByEmpresaId(id);

        model.addAttribute("empresa", empresa);
        model.addAttribute("empresaID", empresa.getId());
        model.addAttribute("trabajadores", empresa.getTrabajadores());
        model.addAttribute("comprobantes", comprobantes);

        return "lista-comprobantes";
    }

    @GetMapping("/empresas/{id}/comprobantes/nuevo")
    public String nuevoComprobante(@PathVariable Long id, Model model) {

        Empresa empresa = empresaRepository.findById(id).orElseThrow();

        model.addAttribute("empresaId", id);
        model.addAttribute("trabajadores", empresa.getTrabajadores());

        return "crear-comprobante";
    }

    @PostMapping("/empresas/{id}/comprobantes/guardar")
    public String guardarComprobante(
            @PathVariable Long id,
            @RequestParam(required = false) Long trabajadorId,
            @RequestParam(required = false) String nuevoTrabajadorNombre,
            @RequestParam(required = false) Cargo nuevoTrabajadorCargo,
            @RequestParam int mes,
            @RequestParam int anio,
            @RequestParam int quincena,
            @RequestParam List<String> fechas,
            @RequestParam List<Double> montos) {

        Empresa empresa = empresaRepository.findById(id).orElseThrow();

        Trabajador trabajador;

        if (trabajadorId != null) {
            trabajador = trabajadorRepository.findById(trabajadorId).orElseThrow();
        } else {
            trabajador = new Trabajador();
            trabajador.setNombre(nuevoTrabajadorNombre);
            trabajador.setCargo(nuevoTrabajadorCargo);
        }

        // asegurar relación many-to-many
        trabajador.getEmpresas().add(empresa);

        // guardamos (si es nuevo lo crea, si existe actualiza relación)
        trabajadorRepository.save(trabajador);

        Comprobante comprobante = new Comprobante();
        comprobante.setEmpresa(empresa);
        comprobante.setTrabajador(trabajador);
        comprobante.setMes(mes);
        comprobante.setAnio(anio);
        comprobante.setQuincena(quincena);

        List<Turno> turnos = new ArrayList<>();

        for (int i = 0; i < fechas.size(); i++) {
            Turno turno = new Turno();
            turno.setFecha(LocalDate.parse(fechas.get(i)));
            turno.setMonto(montos.get(i));
            turno.setComprobante(comprobante);
            turnos.add(turno);
        }

        comprobante.setTurnos(turnos);
        comprobanteRepository.save(comprobante);

        return "redirect:/empresas/" + id + "/comprobantes";
    }

    private final ComprobantePdfService comprobantePdfService;

    @GetMapping("/comprobantes/{id}/pdf")
    public ResponseEntity<byte[]> descargarPdf(@PathVariable Long id) {

        Comprobante comprobante = comprobanteRepository
                .findById(id)
                .orElseThrow();

        byte[] pdf = comprobantePdfService.generarPdf(comprobante);

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=comprobante.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    public ComprobanteController(
            ComprobanteRepository comprobanteRepository,
            EmpresaRepository empresaRepository,
            TrabajadorRepository trabajadorRepository,
            ComprobantePdfService comprobantePdfService) {

        this.comprobanteRepository = comprobanteRepository;
        this.empresaRepository = empresaRepository;
        this.trabajadorRepository = trabajadorRepository;
        this.comprobantePdfService = comprobantePdfService;
    }
}