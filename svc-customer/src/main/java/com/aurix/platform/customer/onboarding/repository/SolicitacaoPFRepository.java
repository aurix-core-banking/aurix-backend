package com.aurix.platform.customer.onboarding.repository;

import com.aurix.platform.customer.onboarding.entity.SolicitacaoPF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitacaoPFRepository extends JpaRepository<SolicitacaoPF, Long> {

    Optional<SolicitacaoPF> findBySolicitacaoId(Long solicitacaoId);

    List<SolicitacaoPF> findByTenantIdAndCpf(String tenantId, String cpf);

    Optional<SolicitacaoPF> findByTenantIdAndSolicitacaoId(String tenantId, Long solicitacaoId);
}
