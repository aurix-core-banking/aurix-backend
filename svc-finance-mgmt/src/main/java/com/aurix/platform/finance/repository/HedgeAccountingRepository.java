package com.aurix.platform.finance.repository;

import com.aurix.platform.finance.entity.HedgeAccounting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HedgeAccountingRepository extends JpaRepository<HedgeAccounting, Long> {
    List<HedgeAccounting> findByInstrumentoHedgeadoId(Long instrumentoHedgeadoId);
    List<HedgeAccounting> findByInstrumentoHedgeId(Long instrumentoHedgeId);
}
