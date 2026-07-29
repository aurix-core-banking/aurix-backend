package com.aurix.platform.compliance.repository;

import com.aurix.platform.shared.entity.Regulacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para Regulacao
 */
@Repository
public interface RegulacaoRepository extends JpaRepository<Regulacao, Long> {
    
    /**
     * Busca regulamentações por órgão regulador
     */
    List<Regulacao> findByOrgaoRegulador(String orgaoRegulador);
    
    /**
     * Busca regulamentações por tipo
     */
    List<Regulacao> findByTipoRegulacao(Regulacao.TipoRegulacao tipoRegulacao);
    
    /**
     * Busca regulamentações por status
     */
    List<Regulacao> findByStatus(Regulacao.StatusRegulacao status);
    
    /**
     * Busca regulamentações ativas
     */
    @Query("SELECT r FROM Regulacao r WHERE r.status = 'ATIVA' AND r.dataVigencia <= :dataAtual AND (r.dataVencimento IS NULL OR r.dataVencimento > :dataAtual)")
    List<Regulacao> findRegulacoesAtivas(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca regulamentações vencidas
     */
    @Query("SELECT r FROM Regulacao r WHERE r.dataVencimento < :dataAtual")
    List<Regulacao> findRegulacoesVencidas(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca regulamentações próximas do vencimento
     */
    @Query("SELECT r FROM Regulacao r WHERE r.dataVencimento BETWEEN :inicio AND :fim")
    List<Regulacao> findRegulacoesProximasVencimento(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca regulamentações por período
     */
    @Query("SELECT r FROM Regulacao r WHERE r.dataVigencia BETWEEN :inicio AND :fim")
    List<Regulacao> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Conta regulamentações por status
     */
    long countByStatus(Regulacao.StatusRegulacao status);
    
    /**
     * Conta regulamentações por tipo
     */
    long countByTipoRegulacao(Regulacao.TipoRegulacao tipoRegulacao);
    
    /**
     * Conta regulamentações por órgão
     */
    long countByOrgaoRegulador(String orgaoRegulador);
}

