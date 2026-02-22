package com.integrainmo.turnos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TrabajadorController {

    private final TrabajadorRepository trabajadorRepository;

    @GetMapping("/trabajadores/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("trabajador", new Trabajador());
        return "crear-trabajador";
    }

    @PostMapping("/trabajadores/guardar")
    public String guardarTrabajador(@ModelAttribute Trabajador trabajador) {
        trabajadorRepository.save(trabajador);
        return "redirect:/trabajadores/nuevo";
    }
}