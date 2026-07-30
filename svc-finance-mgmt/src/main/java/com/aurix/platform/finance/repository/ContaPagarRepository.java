package com.aurix.platform.finance.repository;

import com.aurix.platform.finance.entity.ContaPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para contas a pagar
 */
@Repository
public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
    
    /**
     * Busca conta por número do documento
     */
    Optional<ContaPagar> findByNumeroDocumento(String numeroDocumento);
    
    /**
     * Busca contas por status
     */
    List<ContaPagar> findByStatus(ContaPagar.StatusConta status);
    
    /**
     * Busca contas por fornecedor
     */
    List<ContaPagar> findByFornecedorId(Long fornecedorId);
    
    /**
     * Busca contas por tipo
     */
    List<ContaPagar> findByTipoConta(ContaPagar.TipoConta tipoConta);
    
    /**
     * Busca contas por categoria
     */
    List<ContaPagar> findByCategoria(String categoria);
    
    /**
     * Busca contas por centro de custo
     */
    List<ContaPagar> findByCentroCusto(String centroCusto);
    
    /**
     * Busca contas por período de vencimento
     */
    @Query("SELECT c FROM ContaPagar c WHERE c.dataVencimento BETWEEN :dataInicio AND :dataFim")
    List<ContaPagar> findByPeriodoVencimento(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca contas vencidas
     */
    @Query("SELECT c FROM ContaPagar c WHERE c.dataVencimento < :dataAtual AND c.status IN ('PENDENTE', 'APROVADA')")
    List<ContaPagar> findContasVencidas(@Param("dataAtual") LocalDate dataAtual);
    
    /**
     * Busca contas próximas do vencimento
     */
    @Query("SELECT c FROM ContaPagar c WHERE c.dataVencimento BETWEEN :dataInicio AND :dataFim AND c.status IN ('PENDENTE', 'APROVADA')")
    List<ContaPagar> findContasProximasVencimento(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Soma valor total por status
     */
    @Query("SELECT SUM(c.valorTotal) FROM ContaPagar c WHERE c.status = :status")
    BigDecimal somaValorPorStatus(@Param("status") ContaPagar.StatusConta status);
    
    /**
     * Soma valor total por período
     */
    @Query("SELECT SUM(c.valorTotal) FROM ContaPagar c WHERE c.dataVencimento BETWEEN :dataInicio AND :dataFim")
    BigDecimal somaValorPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Conta registros por status
     */
    long countByStatus(ContaPagar.StatusConta status);
    
    /**
     * Busca contas por faixa de valor
     */
    @Query("SELECT c FROM ContaPagar c WHERE c.valorTotal BETWEEN :valorMin AND :valorMax")
    List<ContaPagar> findByFaixaValor(@Param("valorMin") BigDecimal valorMin, @Param("valorMax") BigDecimal valorMax);
    
    /**
     * Busca contas por usuário de criação
     */
    List<ContaPagar> findByUsuarioCriacao(String usuarioCriacao);
    
    /**
     * Busca contas por período de criação
     */
    @Query("SELECT c FROM ContaPagar c WHERE c.dataCriacao BETWEEN :dataInicio AND :dataFim")
    List<ContaPagar> findByPeriodoCriacao(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
