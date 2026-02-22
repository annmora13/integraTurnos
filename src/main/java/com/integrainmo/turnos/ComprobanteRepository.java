package com.integrainmo.turnos;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ComprobanteRepository extends JpaRepository<Comprobante, Long> {
    List<Comprobante> findByEmpresaId(Long empresaId);
}

