package com.aurix.platform.customer.onboarding.repository;

import com.aurix.platform.customer.onboarding.entity.SolicitacaoPJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolicitacaoPJRepository extends JpaRepository<SolicitacaoPJ, Long> {

    Optional<SolicitacaoPJ> findBySolicitacaoId(Long solicitacaoId);

    Optional<SolicitacaoPJ> findByCnpj(String cnpj);
}
