package com.aurix.platform.finance.repository;

import com.aurix.platform.finance.entity.InstrumentoFinanceiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para instrumentos financeiros conforme IFRS 9
 */
@Repository
public interface InstrumentoFinanceiroRepository extends JpaRepository<InstrumentoFinanceiro, Long> {
    
    /**
     * Busca instrumento por código
     */
    Optional<InstrumentoFinanceiro> findByCodigoInstrumento(String codigoInstrumento);
    
    /**
     * Busca instrumentos por tipo
     */
    List<InstrumentoFinanceiro> findByTipoInstrumento(InstrumentoFinanceiro.TipoInstrumento tipoInstrumento);
    
    /**
     * Busca instrumentos por categoria IFRS 9
     */
    List<InstrumentoFinanceiro> findByCategoriaIFRS9(InstrumentoFinanceiro.CategoriaIFRS9 categoriaIFRS9);
    
    /**
     * Busca instrumentos por modelo de mensuração
     */
    List<InstrumentoFinanceiro> findByModeloMensuracao(InstrumentoFinanceiro.ModeloMensuracao modeloMensuracao);
    
    /**
     * Busca instrumentos por estágio de deterioração
     */
    List<InstrumentoFinanceiro> findByEstagioDeterioracao(InstrumentoFinanceiro.EstagioDeterioracao estagioDeterioracao);
    
    /**
     * Busca instrumentos por status
     */
    List<InstrumentoFinanceiro> findByStatus(InstrumentoFinanceiro.StatusInstrumento status);
    
    /**
     * Busca instrumentos por cliente
     */
    List<InstrumentoFinanceiro> findByClienteId(Long clienteId);
    
    /**
     * Busca instrumentos por conta contábil
     */
    List<InstrumentoFinanceiro> findByContaContabilId(Long contaContabilId);
    
    /**
     * Busca instrumentos por moeda
     */
    List<InstrumentoFinanceiro> findByMoeda(String moeda);
    
    /**
     * Busca instrumentos ativos
     */
    @Query("SELECT i FROM InstrumentoFinanceiro i WHERE i.status = 'ATIVO'")
    List<InstrumentoFinanceiro> findInstrumentosAtivos();
    
    /**
     * Busca instrumentos vencidos
     */
    @Query("SELECT i FROM InstrumentoFinanceiro i WHERE i.dataVencimento < :dataAtual AND i.status = 'ATIVO'")
    List<InstrumentoFinanceiro> findInstrumentosVencidos(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca instrumentos com impairment
     */
    @Query("SELECT i FROM InstrumentoFinanceiro i WHERE i.provisaoImparment > 0")
    List<InstrumentoFinanceiro> findInstrumentosComImpairment();
    
    /**
     * Busca instrumentos por faixa de valor
     */
    @Query("SELECT i FROM InstrumentoFinanceiro i WHERE i.valorNominal BETWEEN :valorMin AND :valorMax")
    List<InstrumentoFinanceiro> findByFaixaValor(@Param("valorMin") BigDecimal valorMin, @Param("valorMax") BigDecimal valorMax);
    
    /**
     * Busca instrumentos por período de emissão
     */
    @Query("SELECT i FROM InstrumentoFinanceiro i WHERE i.dataEmissao BETWEEN :dataInicio AND :dataFim")
    List<InstrumentoFinanceiro> findByPeriodoEmissao(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca instrumentos por período de vencimento
     */
    @Query("SELECT i FROM InstrumentoFinanceiro i WHERE i.dataVencimento BETWEEN :dataInicio AND :dataFim")
    List<InstrumentoFinanceiro> findByPeriodoVencimento(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca instrumentos reclassificados em um período
     */
    @Query("SELECT i FROM InstrumentoFinanceiro i WHERE i.dataReclassificacao BETWEEN :dataInicio AND :dataFim")
    List<InstrumentoFinanceiro> findReclassificadosPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Conta instrumentos por categoria IFRS 9
     */
    long countByCategoriaIFRS9(InstrumentoFinanceiro.CategoriaIFRS9 categoriaIFRS9);
    
    /**
     * Conta instrumentos por estágio
     */
    long countByEstagioDeterioracao(InstrumentoFinanceiro.EstagioDeterioracao estagioDeterioracao);
    
    /**
     * Soma valor total por categoria
     */
    @Query("SELECT SUM(i.valorNominal) FROM InstrumentoFinanceiro i WHERE i.categoriaIFRS9 = :categoria AND i.status = 'ATIVO'")
    BigDecimal somaValorPorCategoria(@Param("categoria") InstrumentoFinanceiro.CategoriaIFRS9 categoria);
    
    /**
     * Soma valor total por estágio
     */
    @Query("SELECT SUM(i.valorNominal) FROM InstrumentoFinanceiro i WHERE i.estagioDeterioracao = :estagio AND i.status = 'ATIVO'")
    BigDecimal somaValorPorEstagio(@Param("estagio") InstrumentoFinanceiro.EstagioDeterioracao estagio);
    
    /**
     * Soma provisionamento total
     */
    @Query("SELECT SUM(i.provisaoImparment) FROM InstrumentoFinanceiro i WHERE i.status = 'ATIVO'")
    BigDecimal somaProvisionamentoTotal();
    
    /**
     * Busca instrumentos para reclassificação
     */
    @Query("SELECT i FROM InstrumentoFinanceiro i WHERE i.status = 'ATIVO' AND (i.dataReclassificacao IS NULL OR i.dataReclassificacao < :dataLimite)")
    List<InstrumentoFinanceiro> findParaReclassificacao(@Param("dataLimite") LocalDateTime dataLimite);
    
    /**
     * Busca instrumentos por rating
     */
    @Query(value = "SELECT * FROM instrumentos_financeiros WHERE metadata LIKE %:rating%", nativeQuery = true)
    List<InstrumentoFinanceiro> findByRating(@Param("rating") String rating);
    
    /**
     * Busca instrumentos com garantias
     */
    @Query(value = "SELECT * FROM instrumentos_financeiros WHERE metadata LIKE %:garantia%", nativeQuery = true)
    List<InstrumentoFinanceiro> findComGarantias(@Param("garantia") String garantia);
    
    /**
     * Busca instrumentos por score de crédito
     */
    @Query(value = "SELECT * FROM instrumentos_financeiros WHERE metadata LIKE %:score%", nativeQuery = true)
    List<InstrumentoFinanceiro> findByScoreCredito(@Param("score") String score);
}
