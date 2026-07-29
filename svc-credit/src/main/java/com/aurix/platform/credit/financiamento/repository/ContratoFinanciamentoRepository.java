package com.aurix.platform.credit.financiamento.repository;

import com.aurix.platform.credit.financiamento.entity.ContratoFinanciamento;
import com.aurix.platform.credit.financiamento.entity.StatusContrato;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoFinanciamentoRepository extends JpaRepository<ContratoFinanciamento, Long> {

    List<ContratoFinanciamento> findByClienteId(Long clienteId);

    List<ContratoFinanciamento> findByTenantIdAndStatus(String tenantId, StatusContrato status);
}
