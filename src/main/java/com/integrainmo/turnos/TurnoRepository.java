package com.integrainmo.turnos;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TurnoRepository extends JpaRepository<Turno, Long> {

    @Query(value = """
        SELECT 
          t.fecha AS fecha,
          SUM(CASE WHEN tr.cargo = 'AUXILIAR_ASEO' THEN 1 ELSE 0 END) AS aseo,
          SUM(CASE WHEN tr.cargo = 'CONSERJE' THEN 1 ELSE 0 END) AS conserje
        FROM turno t
        JOIN comprobante c ON c.id = t.comprobante_id
        JOIN trabajador tr ON tr.id = c.trabajador_id
        WHERE c.empresa_id = :empresaId
          AND EXTRACT(YEAR FROM t.fecha) = :anio
          AND EXTRACT(MONTH FROM t.fecha) = :mes
        GROUP BY t.fecha
        ORDER BY t.fecha
        """, nativeQuery = true)
    List<TurnoDiaResumen> resumenPorDia(@Param("empresaId") Long empresaId,
                                        @Param("anio") int anio,
                                        @Param("mes") int mes);
}