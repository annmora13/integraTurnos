package com.integrainmo.turnos;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmpresaViewController {

    private final EmpresaRepository empresaRepository;

    public EmpresaViewController(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @GetMapping("/empresas")
    public String verEmpresas(Model model) {
        model.addAttribute("empresas", empresaRepository.findAll());
        return "empresas";
    }
}
