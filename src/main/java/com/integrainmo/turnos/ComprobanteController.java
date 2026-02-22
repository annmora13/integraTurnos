package com.integrainmo.turnos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ComprobanteController {
    private final EmpresaRepository empresaRepository;
    private final ComprobanteRepository comprobanteRepository;
    private final TrabajadorRepository trabajadorRepository;

    @GetMapping("/empresas/{id}/comprobantes")
    public String listarComprobantes(@PathVariable Long id, Model model) {

        List<Comprobante> comprobantes = comprobanteRepository.findByEmpresaId(id);

        model.addAttribute("comprobantes", comprobantes);
        model.addAttribute("empresaId", id);

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
        @RequestParam Long trabajadorId,
        @RequestParam int mes,
        @RequestParam int anio,
        @RequestParam List<String> fechas,
        @RequestParam List<Double> montos) {

    Empresa empresa = empresaRepository.findById(id).orElseThrow();
    Trabajador trabajador = trabajadorRepository.findById(trabajadorId).orElseThrow();

    Comprobante comprobante = new Comprobante();
    comprobante.setEmpresa(empresa);
    comprobante.setTrabajador(trabajador);
    comprobante.setMes(mes);
    comprobante.setAnio(anio);

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
    public ComprobanteController(
            ComprobanteRepository comprobanteRepository,
            EmpresaRepository empresaRepository,
            TrabajadorRepository trabajadorRepository) {

        this.comprobanteRepository = comprobanteRepository;
        this.empresaRepository = empresaRepository;
        this.trabajadorRepository = trabajadorRepository;
    }
    
}