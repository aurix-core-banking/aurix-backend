package com.aurix.platform.cambio.repository;

import com.aurix.platform.cambio.entity.ContratoCambio;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoCambioRepository extends JpaRepository<ContratoCambio, Long> {

    List<ContratoCambio> findByClienteId(Long clienteId);

    List<ContratoCambio> findByStatus(String status);

    List<ContratoCambio> findByDataContratacao(LocalDate dataContratacao);
}
