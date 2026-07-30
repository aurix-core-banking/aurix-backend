package com.aurix.platform.customer.onboarding.repository;

import com.aurix.platform.customer.onboarding.entity.HistoricoAprovacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoAprovacaoRepository extends JpaRepository<HistoricoAprovacao, Long> {

    List<HistoricoAprovacao> findBySolicitacaoIdOrderByDataAcaoDesc(Long solicitacaoId);
}
