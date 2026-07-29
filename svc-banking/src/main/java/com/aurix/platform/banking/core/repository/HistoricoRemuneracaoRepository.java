package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.HistoricoRemuneracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoricoRemuneracaoRepository extends JpaRepository<HistoricoRemuneracao, Long> {
    List<HistoricoRemuneracao> findByContaId(Long contaId);
    List<HistoricoRemuneracao> findByAplicacaoFinanceiraId(Long aplicacaoFinanceiraId);
    List<HistoricoRemuneracao> findByDataEventoBetween(LocalDateTime inicio, LocalDateTime fim);
}
