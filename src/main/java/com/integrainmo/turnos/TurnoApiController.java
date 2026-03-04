package com.integrainmo.turnos;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TurnoApiController {

    private final TurnoRepository turnoRepository;

    public TurnoApiController(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    @GetMapping("/empresas/{id}/turnos/resumen")
    public List<TurnoDiaResumen> resumenTurnosPorDia(
            @PathVariable Long id,
            @RequestParam int anio,
            @RequestParam int mes) {

        return turnoRepository.resumenPorDia(id, anio, mes);
    }
}