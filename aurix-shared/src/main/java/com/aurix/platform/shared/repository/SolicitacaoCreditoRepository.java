package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.SolicitacaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SolicitacaoCreditoRepository extends JpaRepository<SolicitacaoCredito, Long> {

    List<SolicitacaoCredito> findByClienteId(Long clienteId);

    List<SolicitacaoCredito> findByStatus(SolicitacaoCredito.StatusSolicitacao status);

    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.dataSolicitacao BETWEEN :inicio AND :fim")
    List<SolicitacaoCredito> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.cliente.id = :clienteId AND s.dataSolicitacao BETWEEN :inicio AND :fim")
    List<SolicitacaoCredito> findByClienteAndPeriodo(@Param("clienteId") Long clienteId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.status = 'PENDENTE' OR s.status = 'EM_ANALISE'")
    List<SolicitacaoCredito> findSolicitacoesPendentes();

    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.status = 'APROVADA'")
    List<SolicitacaoCredito> findSolicitacoesAprovadas();

    @Query("SELECT SUM(s.valorAprovado) FROM SolicitacaoCredito s WHERE s.cliente.id = :clienteId AND s.status = 'APROVADA'")
    BigDecimal somarValorAprovadoPorCliente(@Param("clienteId") Long clienteId);

    long countByStatus(SolicitacaoCredito.StatusSolicitacao status);

    long countByClienteId(Long clienteId);

    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.status = 'PENDENTE' AND s.dataSolicitacao < :limite")
    List<SolicitacaoCredito> findSolicitacoesExpiradas(@Param("limite") LocalDateTime limite);

    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.status = 'REFER' ORDER BY s.dataSolicitacao")
    List<SolicitacaoCredito> findSolicitacoesRefer();
}
