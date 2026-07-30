package com.aurix.platform.credit.financiamento.repository;

import com.aurix.platform.credit.financiamento.entity.SimulacaoFinanciamento;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulacaoFinanciamentoRepository extends JpaRepository<SimulacaoFinanciamento, Long> {

    List<SimulacaoFinanciamento> findByClienteIdOrderByDataSimulacaoDesc(Long clienteId);
}
