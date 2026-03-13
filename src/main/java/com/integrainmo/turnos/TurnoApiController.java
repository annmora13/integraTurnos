package com.integrainmo.turnos;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TurnoApiController {

    private final TurnoRepository turnoRepository;

    public TurnoApiController(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    @GetMapping("/empresas/{id}/turnos")
    public List<Turno> turnosPorRango(
            @PathVariable Long id,
            @RequestParam String start,
            @RequestParam String end) {
        LocalDate ini = LocalDate.parse(start);
        LocalDate fin = LocalDate.parse(end);
        return turnoRepository.findByEmpresaYFechaEntre(id, ini, fin);
    }

    @GetMapping("/empresas/{id}/turnos/resumen")
    public List<TurnoDiaResumen> resumenTurnosPorDia(
            @PathVariable Long id,
            @RequestParam int anio,
            @RequestParam int mes) {

        return turnoRepository.resumenPorDia(id, anio, mes);
    }
}