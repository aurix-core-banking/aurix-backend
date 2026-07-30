package com.aurix.platform.finance.repository;

import com.aurix.platform.finance.entity.Imposto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para impostos
 */
@Repository
public interface ImpostoRepository extends JpaRepository<Imposto, Long> {
    
    /**
     * Busca imposto por código
     */
    Optional<Imposto> findByCodigoImposto(String codigoImposto);
    
    /**
     * Busca impostos por tipo
     */
    List<Imposto> findByTipoImposto(Imposto.TipoImposto tipoImposto);
    
    /**
     * Busca impostos por categoria
     */
    List<Imposto> findByCategoria(Imposto.CategoriaImposto categoria);
    
    /**
     * Busca impostos por status
     */
    List<Imposto> findByStatus(Imposto.StatusImposto status);
    
    /**
     * Busca impostos por competência
     */
    List<Imposto> findByCompetencia(String competencia);
    
    /**
     * Busca impostos por período
     */
    @Query("SELECT i FROM Imposto i WHERE i.dataReferencia BETWEEN :dataInicio AND :dataFim")
    List<Imposto> findByPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca impostos vencidos
     */
    @Query("SELECT i FROM Imposto i WHERE i.dataVencimento < :dataAtual AND i.status IN ('CALCULADO', 'APURADO')")
    List<Imposto> findImpostosVencidos(@Param("dataAtual") LocalDate dataAtual);
    
    /**
     * Busca impostos próximos do vencimento
     */
    @Query("SELECT i FROM Imposto i WHERE i.dataVencimento BETWEEN :dataInicio AND :dataFim AND i.status IN ('CALCULADO', 'APURADO')")
    List<Imposto> findImpostosProximosVencimento(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Busca impostos pagos
     */
    @Query("SELECT i FROM Imposto i WHERE i.status = 'PAGO'")
    List<Imposto> findImpostosPagos();
    
    /**
     * Soma valor total por tipo
     */
    @Query("SELECT SUM(i.valorTotal) FROM Imposto i WHERE i.tipoImposto = :tipoImposto")
    BigDecimal somaValorPorTipo(@Param("tipoImposto") Imposto.TipoImposto tipoImposto);
    
    /**
     * Soma valor total por categoria
     */
    @Query("SELECT SUM(i.valorTotal) FROM Imposto i WHERE i.categoria = :categoria")
    BigDecimal somaValorPorCategoria(@Param("categoria") Imposto.CategoriaImposto categoria);
    
    /**
     * Soma valor total por competência
     */
    @Query("SELECT SUM(i.valorTotal) FROM Imposto i WHERE i.competencia = :competencia")
    BigDecimal somaValorPorCompetencia(@Param("competencia") String competencia);
    
    /**
     * Soma valor total por período
     */
    @Query("SELECT SUM(i.valorTotal) FROM Imposto i WHERE i.dataReferencia BETWEEN :dataInicio AND :dataFim")
    BigDecimal somaValorPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Conta impostos por status
     */
    long countByStatus(Imposto.StatusImposto status);
    
    /**
     * Conta impostos por tipo
     */
    long countByTipoImposto(Imposto.TipoImposto tipoImposto);
    
    /**
     * Conta impostos por competência
     */
    long countByCompetencia(String competencia);

    /**
     * Conta impostos por competência e status
     */
    long countByCompetenciaAndStatus(String competencia, Imposto.StatusImposto status);
    
    /**
     * Busca impostos por faixa de valor
     */
    @Query("SELECT i FROM Imposto i WHERE i.valorTotal BETWEEN :valorMin AND :valorMax")
    List<Imposto> findByFaixaValor(@Param("valorMin") BigDecimal valorMin, @Param("valorMax") BigDecimal valorMax);
    
    /**
     * Busca impostos por número DARF
     */
    Optional<Imposto> findByNumeroDarf(String numeroDarf);
    
    /**
     * Busca impostos com multa
     */
    @Query("SELECT i FROM Imposto i WHERE i.valorMulta > 0")
    List<Imposto> findImpostosComMulta();
    
    /**
     * Busca impostos com juros
     */
    @Query("SELECT i FROM Imposto i WHERE i.valorJuros > 0")
    List<Imposto> findImpostosComJuros();
    
    /**
     * Soma multas por período
     */
    @Query("SELECT SUM(i.valorMulta) FROM Imposto i WHERE i.dataReferencia BETWEEN :dataInicio AND :dataFim")
    BigDecimal somaMultasPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Soma juros por período
     */
    @Query("SELECT SUM(i.valorJuros) FROM Imposto i WHERE i.dataReferencia BETWEEN :dataInicio AND :dataFim")
    BigDecimal somaJurosPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
