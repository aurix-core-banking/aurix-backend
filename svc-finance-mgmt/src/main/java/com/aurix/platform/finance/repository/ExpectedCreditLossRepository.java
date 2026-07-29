package com.aurix.platform.finance.repository;

import com.aurix.platform.finance.entity.ExpectedCreditLoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para Expected Credit Loss (ECL) conforme IFRS 9
 */
@Repository
public interface ExpectedCreditLossRepository extends JpaRepository<ExpectedCreditLoss, Long> {
    
    /**
     * Busca ECL por instrumento
     */
    List<ExpectedCreditLoss> findByInstrumentoId(Long instrumentoId);
    
    /**
     * Busca ECL por data de cálculo
     */
    List<ExpectedCreditLoss> findByDataCalculo(LocalDate dataCalculo);
    
    /**
     * Busca ECL por estágio
     */
    List<ExpectedCreditLoss> findByEstagio(ExpectedCreditLoss.EstagioDeterioracao estagio);
    
    /**
     * Busca ECL por período
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.dataCalculo BETWEEN :dataInicio AND :dataFim")
    List<ExpectedCreditLoss> findByPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca ECL mais recente por instrumento
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.instrumento.id = :instrumentoId ORDER BY e.dataCalculo DESC")
    Optional<ExpectedCreditLoss> findECLRecentePorInstrumento(@Param("instrumentoId") Long instrumentoId);
    
    /**
     * Busca ECL por faixa de PD
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.probabilityDefault BETWEEN :pdMin AND :pdMax")
    List<ExpectedCreditLoss> findByFaixaPD(@Param("pdMin") BigDecimal pdMin, @Param("pdMax") BigDecimal pdMax);
    
    /**
     * Busca ECL por faixa de LGD
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.lossGivenDefault BETWEEN :lgdMin AND :lgdMax")
    List<ExpectedCreditLoss> findByFaixaLGD(@Param("lgdMin") BigDecimal lgdMin, @Param("lgdMax") BigDecimal lgdMax);
    
    /**
     * Busca ECL por faixa de EAD
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.exposureAtDefault BETWEEN :eadMin AND :eadMax")
    List<ExpectedCreditLoss> findByFaixaEAD(@Param("eadMin") BigDecimal eadMin, @Param("eadMax") BigDecimal eadMax);
    
    /**
     * Busca ECL por faixa de ECL
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.expectedCreditLoss BETWEEN :eclMin AND :eclMax")
    List<ExpectedCreditLoss> findByFaixaECL(@Param("eclMin") BigDecimal eclMin, @Param("eclMax") BigDecimal eclMax);
    
    /**
     * Busca ECL por metodologia
     */
    List<ExpectedCreditLoss> findByMetodologiaCalculo(String metodologiaCalculo);
    
    /**
     * Busca ECL por modelo utilizado
     */
    List<ExpectedCreditLoss> findByModeloUtilizado(String modeloUtilizado);
    
    /**
     * Busca ECL por cenário
     */
    List<ExpectedCreditLoss> findByCenarioBase(String cenarioBase);
    
    /**
     * Busca ECL por score de crédito
     */
    List<ExpectedCreditLoss> findByScoreCredito(Integer scoreCredito);
    
    /**
     * Busca ECL por rating interno
     */
    List<ExpectedCreditLoss> findByRatingInterno(String ratingInterno);
    
    /**
     * Busca ECL por rating externo
     */
    List<ExpectedCreditLoss> findByRatingExterno(String ratingExterno);
    
    /**
     * Busca ECL por faixa de score
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.scoreCredito BETWEEN :scoreMin AND :scoreMax")
    List<ExpectedCreditLoss> findByFaixaScore(@Param("scoreMin") Integer scoreMin, @Param("scoreMax") Integer scoreMax);
    
    /**
     * Busca ECL com garantias
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.garantiasValor > 0")
    List<ExpectedCreditLoss> findECLComGarantias();
    
    /**
     * Busca ECL sem garantias
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.garantiasValor IS NULL OR e.garantiasValor = 0")
    List<ExpectedCreditLoss> findECLSemGarantias();
    
    /**
     * Busca ECL por tipo de garantia
     */
    List<ExpectedCreditLoss> findByGarantiasTipo(String garantiasTipo);
    
    /**
     * Busca ECL por usuário de cálculo
     */
    List<ExpectedCreditLoss> findByUsuarioCalculo(String usuarioCalculo);
    
    /**
     * Conta ECL por estágio
     */
    long countByEstagio(ExpectedCreditLoss.EstagioDeterioracao estagio);
    
    /**
     * Conta ECL por data
     */
    long countByDataCalculo(LocalDate dataCalculo);
    
    /**
     * Soma ECL total por estágio
     */
    @Query("SELECT SUM(e.expectedCreditLoss) FROM ExpectedCreditLoss e WHERE e.estagio = :estagio")
    BigDecimal somaECLPorEstagio(@Param("estagio") ExpectedCreditLoss.EstagioDeterioracao estagio);
    
    /**
     * Soma ECL total por data
     */
    @Query("SELECT SUM(e.expectedCreditLoss) FROM ExpectedCreditLoss e WHERE e.dataCalculo = :dataCalculo")
    BigDecimal somaECLPorData(@Param("dataCalculo") LocalDate dataCalculo);
    
    /**
     * Soma provisão total por estágio
     */
    @Query("SELECT SUM(e.provisaoTotal) FROM ExpectedCreditLoss e WHERE e.estagio = :estagio")
    BigDecimal somaProvisaoPorEstagio(@Param("estagio") ExpectedCreditLoss.EstagioDeterioracao estagio);
    
    /**
     * Soma provisão total por data
     */
    @Query("SELECT SUM(e.provisaoTotal) FROM ExpectedCreditLoss e WHERE e.dataCalculo = :dataCalculo")
    BigDecimal somaProvisaoPorData(@Param("dataCalculo") LocalDate dataCalculo);
    
    /**
     * Média de PD por estágio
     */
    @Query("SELECT AVG(e.probabilityDefault) FROM ExpectedCreditLoss e WHERE e.estagio = :estagio")
    BigDecimal mediaPDPorEstagio(@Param("estagio") ExpectedCreditLoss.EstagioDeterioracao estagio);
    
    /**
     * Média de LGD por estágio
     */
    @Query("SELECT AVG(e.lossGivenDefault) FROM ExpectedCreditLoss e WHERE e.estagio = :estagio")
    BigDecimal mediaLGDPorEstagio(@Param("estagio") ExpectedCreditLoss.EstagioDeterioracao estagio);
    
    /**
     * Busca ECL por instrumento e data
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.instrumento.id = :instrumentoId AND e.dataCalculo = :dataCalculo")
    Optional<ExpectedCreditLoss> findByInstrumentoEData(@Param("instrumentoId") Long instrumentoId, @Param("dataCalculo") LocalDate dataCalculo);
    
    /**
     * Busca ECL por instrumento e estágio
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.instrumento.id = :instrumentoId AND e.estagio = :estagio ORDER BY e.dataCalculo DESC")
    List<ExpectedCreditLoss> findByInstrumentoEEstagio(@Param("instrumentoId") Long instrumentoId, @Param("estagio") ExpectedCreditLoss.EstagioDeterioracao estagio);
    
    /**
     * Busca ECL por período e estágio
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.dataCalculo BETWEEN :dataInicio AND :dataFim AND e.estagio = :estagio")
    List<ExpectedCreditLoss> findByPeriodoEEstagio(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim, @Param("estagio") ExpectedCreditLoss.EstagioDeterioracao estagio);
    
    /**
     * Busca ECL por cenário e período
     */
    @Query("SELECT e FROM ExpectedCreditLoss e WHERE e.dataCalculo BETWEEN :dataInicio AND :dataFim AND e.cenarioBase = :cenario")
    List<ExpectedCreditLoss> findByPeriodoECenario(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim, @Param("cenario") String cenario);
}
