package com.aurix.platform.credit.financiamento.repository;

import com.aurix.platform.credit.financiamento.entity.BemFinanciado;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BemFinanciadoRepository extends JpaRepository<BemFinanciado, Long> {

    List<BemFinanciado> findByContratoId(Long contratoId);
}
