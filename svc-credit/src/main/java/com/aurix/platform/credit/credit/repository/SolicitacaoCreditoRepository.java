package com.aurix.platform.credit.credit.repository;

import com.aurix.platform.shared.entity.SolicitacaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para Solicitação de Crédito
 */
@Repository
public interface SolicitacaoCreditoRepository extends JpaRepository<SolicitacaoCredito, Long> {
    
    /**
     * Busca solicitações por cliente
     */
    List<SolicitacaoCredito> findByClienteId(Long clienteId);
    
    /**
     * Busca solicitações por status
     */
    List<SolicitacaoCredito> findByStatus(SolicitacaoCredito.StatusSolicitacao status);
    
    /**
     * Busca solicitações por período
     */
    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.dataSolicitacao BETWEEN :inicio AND :fim")
    List<SolicitacaoCredito> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca solicitações por cliente e período
     */
    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.cliente.id = :clienteId AND s.dataSolicitacao BETWEEN :inicio AND :fim")
    List<SolicitacaoCredito> findByClienteAndPeriodo(@Param("clienteId") Long clienteId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca solicitações pendentes
     */
    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.status = 'PENDENTE' OR s.status = 'EM_ANALISE'")
    List<SolicitacaoCredito> findSolicitacoesPendentes();
    
    /**
     * Busca solicitações aprovadas
     */
    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.status = 'APROVADA'")
    List<SolicitacaoCredito> findSolicitacoesAprovadas();
    
    /**
     * Soma valor das solicitações aprovadas por cliente
     */
    @Query("SELECT SUM(s.valorAprovado) FROM SolicitacaoCredito s WHERE s.cliente.id = :clienteId AND s.status = 'APROVADA'")
    BigDecimal somarValorAprovadoPorCliente(@Param("clienteId") Long clienteId);
    
    /**
     * Conta solicitações por status
     */
    long countByStatus(SolicitacaoCredito.StatusSolicitacao status);
    
    /**
     * Conta solicitações por cliente
     */
    long countByClienteId(Long clienteId);
    
    /**
     * Busca solicitações expiradas
     */
    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.status = 'PENDENTE' AND s.dataSolicitacao < :limite")
    List<SolicitacaoCredito> findSolicitacoesExpiradas(@Param("limite") LocalDateTime limite);

    @Query("SELECT s FROM SolicitacaoCredito s WHERE s.status = 'REFER' ORDER BY s.dataSolicitacao")
    List<SolicitacaoCredito> findSolicitacoesRefer();
}

