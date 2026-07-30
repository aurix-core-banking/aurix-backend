package com.aurix.platform.credit.financiamento.repository;

import com.aurix.platform.credit.financiamento.entity.ParcelaFinanciamento;
import com.aurix.platform.credit.financiamento.entity.StatusParcela;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelaFinanciamentoRepository extends JpaRepository<ParcelaFinanciamento, Long> {

    List<ParcelaFinanciamento> findByContratoIdOrderByNumero(Long contratoId);

    List<ParcelaFinanciamento> findByContratoIdAndStatus(Long contratoId, StatusParcela status);
}
