package com.aurix.platform.banking.salario.repository;

import com.aurix.platform.banking.salario.entity.SolicitacaoPortabilidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SolicitacaoPortabilidadeRepository extends JpaRepository<SolicitacaoPortabilidade, Long> {
    Optional<SolicitacaoPortabilidade> findByTenantIdAndId(String tenantId, Long id);
    List<SolicitacaoPortabilidade> findByContaSalarioId(Long contaSalarioId);
    List<SolicitacaoPortabilidade> findByContaSalarioIdAndStatus(
        Long contaSalarioId, SolicitacaoPortabilidade.StatusPortabilidade status);
}
