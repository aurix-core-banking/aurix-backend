package com.aurix.platform.platform.repository;

import com.aurix.platform.shared.entity.Investimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para Investimento
 */
@Repository
public interface InvestimentoRepository extends JpaRepository<Investimento, Long> {
    
    /**
     * Busca investimentos por conta
     */
    List<Investimento> findByContaId(Long contaId);
    
    /**
     * Busca investimentos por tipo
     */
    List<Investimento> findByTipoInvestimento(Investimento.TipoInvestimento tipoInvestimento);
    
    /**
     * Busca investimentos por status
     */
    List<Investimento> findByStatus(Investimento.StatusInvestimento status);
    
    /**
     * Busca investimentos ativos por conta
     */
    @Query("SELECT i FROM Investimento i WHERE i.conta.id = :contaId AND i.status = 'ATIVO'")
    List<Investimento> findInvestimentosAtivosByContaId(@Param("contaId") Long contaId);
    
    /**
     * Busca investimentos vencidos
     */
    @Query("SELECT i FROM Investimento i WHERE i.dataVencimento < :dataAtual AND i.status = 'ATIVO'")
    List<Investimento> findInvestimentosVencidos(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca investimentos por período
     */
    @Query("SELECT i FROM Investimento i WHERE i.dataAplicacao BETWEEN :inicio AND :fim")
    List<Investimento> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca investimentos por conta e período
     */
    @Query("SELECT i FROM Investimento i WHERE i.conta.id = :contaId AND i.dataAplicacao BETWEEN :inicio AND :fim")
    List<Investimento> findByContaAndPeriodo(@Param("contaId") Long contaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Soma valor total dos investimentos por conta
     */
    @Query("SELECT SUM(i.valorInvestido + i.rendimentoAtual) FROM Investimento i WHERE i.conta.id = :contaId AND i.status = 'ATIVO'")
    BigDecimal somarValorTotalPorConta(@Param("contaId") Long contaId);
    
    /**
     * Soma valor investido por conta
     */
    @Query("SELECT SUM(i.valorInvestido) FROM Investimento i WHERE i.conta.id = :contaId AND i.status = 'ATIVO'")
    BigDecimal somarValorInvestidoPorConta(@Param("contaId") Long contaId);
    
    /**
     * Soma rendimento atual por conta
     */
    @Query("SELECT SUM(i.rendimentoAtual) FROM Investimento i WHERE i.conta.id = :contaId AND i.status = 'ATIVO'")
    BigDecimal somarRendimentoAtualPorConta(@Param("contaId") Long contaId);
    
    /**
     * Conta investimentos por status
     */
    long countByStatus(Investimento.StatusInvestimento status);
    
    /**
     * Conta investimentos por tipo
     */
    long countByTipoInvestimento(Investimento.TipoInvestimento tipoInvestimento);
    
    /**
     * Conta investimentos por conta
     */
    long countByContaId(Long contaId);
    
    /**
     * Busca investimentos próximos do vencimento
     */
    @Query("SELECT i FROM Investimento i WHERE i.dataVencimento BETWEEN :inicio AND :fim AND i.status = 'ATIVO'")
    List<Investimento> findInvestimentosProximosVencimento(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}
