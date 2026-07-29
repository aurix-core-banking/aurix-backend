package com.aurix.platform.credit.financiamento.repository;

import com.aurix.platform.credit.financiamento.entity.Garantia;
import com.aurix.platform.credit.financiamento.entity.StatusGarantia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GarantiaRepository extends JpaRepository<Garantia, Long> {

    List<Garantia> findByContratoId(Long contratoId);

    List<Garantia> findByStatus(StatusGarantia status);
}
