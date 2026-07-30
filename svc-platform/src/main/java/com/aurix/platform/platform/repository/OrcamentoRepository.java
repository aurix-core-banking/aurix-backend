package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para orçamentos
 */
@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
    
    /**
     * Busca orçamento por código
     */
    Optional<Orcamento> findByCodigoOrcamento(String codigoOrcamento);
    
    /**
     * Busca orçamentos por ano
     */
    List<Orcamento> findByAno(Integer ano);
    
    /**
     * Busca orçamentos por tipo
     */
    List<Orcamento> findByTipoOrcamento(Orcamento.TipoOrcamento tipoOrcamento);
    
    /**
     * Busca orçamentos por status
     */
    List<Orcamento> findByStatus(Orcamento.StatusOrcamento status);
    
    /**
     * Busca orçamentos por centro de custo
     */
    List<Orcamento> findByCentroCusto(String centroCusto);
    
    /**
     * Busca orçamentos por responsável
     */
    List<Orcamento> findByResponsavel(String responsavel);
    
    /**
     * Busca orçamentos por período
     */
    @Query("SELECT o FROM Orcamento o WHERE o.dataInicio BETWEEN :dataInicio AND :dataFim")
    List<Orcamento> findByPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca orçamentos aprovados
     */
    @Query("SELECT o FROM Orcamento o WHERE o.status = 'APROVADO'")
    List<Orcamento> findOrcamentosAprovados();
    
    /**
     * Busca orçamentos em execução
     */
    @Query("SELECT o FROM Orcamento o WHERE o.status = 'EXECUTANDO' AND o.dataFim >= :dataAtual")
    List<Orcamento> findOrcamentosEmExecucao(@Param("dataAtual") LocalDate dataAtual);
    
    /**
     * Busca orçamentos vencidos
     */
    @Query("SELECT o FROM Orcamento o WHERE o.status = 'EXECUTANDO' AND o.dataFim < :dataAtual")
    List<Orcamento> findOrcamentosVencidos(@Param("dataAtual") LocalDate dataAtual);
    
    /**
     * Soma valor orçado por ano
     */
    @Query("SELECT SUM(o.valorTotalOrcado) FROM Orcamento o WHERE o.ano = :ano AND o.status = 'APROVADO'")
    BigDecimal somaValorOrcadoPorAno(@Param("ano") Integer ano);
    
    /**
     * Soma valor realizado por ano
     */
    @Query("SELECT SUM(o.valorTotalRealizado) FROM Orcamento o WHERE o.ano = :ano AND o.status = 'EXECUTANDO'")
    BigDecimal somaValorRealizadoPorAno(@Param("ano") Integer ano);
    
    /**
     * Soma valor orçado por centro de custo
     */
    @Query("SELECT SUM(o.valorTotalOrcado) FROM Orcamento o WHERE o.centroCusto = :centroCusto AND o.status = 'APROVADO'")
    BigDecimal somaValorOrcadoPorCentroCusto(@Param("centroCusto") String centroCusto);
    
    /**
     * Soma valor realizado por centro de custo
     */
    @Query("SELECT SUM(o.valorTotalRealizado) FROM Orcamento o WHERE o.centroCusto = :centroCusto AND o.status = 'EXECUTANDO'")
    BigDecimal somaValorRealizadoPorCentroCusto(@Param("centroCusto") String centroCusto);
    
    /**
     * Conta orçamentos por status
     */
    long countByStatus(Orcamento.StatusOrcamento status);
    
    /**
     * Conta orçamentos por ano
     */
    long countByAno(Integer ano);
    
    /**
     * Busca orçamentos com variação acima do limite
     */
    @Query("SELECT o FROM Orcamento o WHERE ABS(o.percentualVariacao) > :limiteVariacao AND o.status = 'EXECUTANDO'")
    List<Orcamento> findOrcamentosComVariacaoAcimaLimite(@Param("limiteVariacao") BigDecimal limiteVariacao);
    
    /**
     * Busca orçamentos por faixa de variação
     */
    @Query("SELECT o FROM Orcamento o WHERE o.percentualVariacao BETWEEN :variacaoMin AND :variacaoMax")
    List<Orcamento> findByFaixaVariacao(@Param("variacaoMin") BigDecimal variacaoMin, @Param("variacaoMax") BigDecimal variacaoMax);
}
