package com.aurix.platform.cambio.repository;

import com.aurix.platform.cambio.entity.TaxaSelic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para taxa SELIC
 */
@Repository
public interface TaxaSelicRepository extends JpaRepository<TaxaSelic, Long> {
    
    /**
     * Busca taxa SELIC por data
     */
    Optional<TaxaSelic> findByDataReferencia(LocalDate dataReferencia);
    
    /**
     * Busca taxa SELIC por tipo
     */
    List<TaxaSelic> findByTipoTaxa(TaxaSelic.TipoTaxa tipoTaxa);
    
    /**
     * Busca taxa SELIC mais recente
     */
    @Query("SELECT t FROM TaxaSelic t ORDER BY t.dataReferencia DESC")
    Optional<TaxaSelic> findTaxaMaisRecente();
    
    /**
     * Busca taxa SELIC por período
     */
    @Query("SELECT t FROM TaxaSelic t WHERE t.dataReferencia BETWEEN :dataInicio AND :dataFim ORDER BY t.dataReferencia DESC")
    List<TaxaSelic> findByPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca taxa SELIC atual
     */
    @Query("SELECT t FROM TaxaSelic t WHERE t.dataReferencia <= :dataAtual ORDER BY t.dataReferencia DESC")
    Optional<TaxaSelic> findTaxaAtual(@Param("dataAtual") LocalDate dataAtual);
    
    /**
     * Busca variação da taxa SELIC
     */
    @Query("SELECT t FROM TaxaSelic t WHERE ABS(t.valorTaxa - :taxaReferencia) >= :variacaoMinima ORDER BY t.dataReferencia DESC")
    List<TaxaSelic> findVariacoesSignificativas(@Param("taxaReferencia") BigDecimal taxaReferencia, @Param("variacaoMinima") BigDecimal variacaoMinima);
    
    /**
     * Busca histórico de taxas por ano
     */
    @Query("SELECT t FROM TaxaSelic t WHERE YEAR(t.dataReferencia) = :ano ORDER BY t.dataReferencia DESC")
    List<TaxaSelic> findByAno(@Param("ano") Integer ano);
    
    /**
     * Busca média de taxas por período
     */
    @Query("SELECT AVG(t.valorTaxa) FROM TaxaSelic t WHERE t.dataReferencia BETWEEN :dataInicio AND :dataFim")
    BigDecimal calcularMediaPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca maior taxa do período
     */
    @Query("SELECT MAX(t.valorTaxa) FROM TaxaSelic t WHERE t.dataReferencia BETWEEN :dataInicio AND :dataFim")
    BigDecimal buscarMaiorTaxaPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca menor taxa do período
     */
    @Query("SELECT MIN(t.valorTaxa) FROM TaxaSelic t WHERE t.dataReferencia BETWEEN :dataInicio AND :dataFim")
    BigDecimal buscarMenorTaxaPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca tendência da taxa
     */
    @Query("SELECT t FROM TaxaSelic t ORDER BY t.dataReferencia DESC")
    List<TaxaSelic> findTendenciaTaxa();
    
    /**
     * Conta registros por ano
     */
    @Query("SELECT COUNT(t) FROM TaxaSelic t WHERE YEAR(t.dataReferencia) = :ano")
    long countByAno(@Param("ano") Integer ano);
    
    /**
     * Busca última atualização
     */
    @Query("SELECT MAX(t.dataAtualizacaoBacen) FROM TaxaSelic t")
    Optional<java.time.LocalDateTime> findUltimaAtualizacao();
}
