package com.aurix.platform.finance.repository;

import com.aurix.platform.finance.entity.Custo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para custos
 */
@Repository
public interface CustoRepository extends JpaRepository<Custo, Long> {
    
    /**
     * Busca custo por código
     */
    Optional<Custo> findByCodigoCusto(String codigoCusto);
    
    /**
     * Busca custos por tipo
     */
    List<Custo> findByTipoCusto(Custo.TipoCusto tipoCusto);
    
    /**
     * Busca custos por categoria
     */
    List<Custo> findByCategoria(Custo.CategoriaCusto categoria);
    
    /**
     * Busca custos por status
     */
    List<Custo> findByStatus(Custo.StatusCusto status);
    
    /**
     * Busca custos por competência
     */
    List<Custo> findByCompetencia(String competencia);
    
    /**
     * Busca custos por produto
     */
    List<Custo> findByProdutoId(Long produtoId);
    
    /**
     * Busca custos por cliente
     */
    List<Custo> findByClienteId(Long clienteId);
    
    /**
     * Busca custos por canal
     */
    List<Custo> findByCanalId(Long canalId);
    
    /**
     * Busca custos por atividade
     */
    List<Custo> findByAtividadeId(Long atividadeId);
    
    /**
     * Busca custos por centro de custo
     */
    List<Custo> findByCentroCusto(String centroCusto);
    
    /**
     * Busca custos por período
     */
    @Query("SELECT c FROM Custo c WHERE c.dataReferencia BETWEEN :dataInicio AND :dataFim")
    List<Custo> findByPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Soma valor total por tipo
     */
    @Query("SELECT SUM(c.valor) FROM Custo c WHERE c.tipoCusto = :tipoCusto")
    BigDecimal somaValorPorTipo(@Param("tipoCusto") Custo.TipoCusto tipoCusto);
    
    /**
     * Soma valor total por categoria
     */
    @Query("SELECT SUM(c.valor) FROM Custo c WHERE c.categoria = :categoria")
    BigDecimal somaValorPorCategoria(@Param("categoria") Custo.CategoriaCusto categoria);
    
    /**
     * Soma valor total por produto
     */
    @Query("SELECT SUM(c.valor) FROM Custo c WHERE c.produtoId = :produtoId")
    BigDecimal somaValorPorProduto(@Param("produtoId") Long produtoId);
    
    /**
     * Soma valor total por cliente
     */
    @Query("SELECT SUM(c.valor) FROM Custo c WHERE c.clienteId = :clienteId")
    BigDecimal somaValorPorCliente(@Param("clienteId") Long clienteId);
    
    /**
     * Soma valor total por canal
     */
    @Query("SELECT SUM(c.valor) FROM Custo c WHERE c.canalId = :canalId")
    BigDecimal somaValorPorCanal(@Param("canalId") Long canalId);
    
    /**
     * Soma valor total por centro de custo
     */
    @Query("SELECT SUM(c.valor) FROM Custo c WHERE c.centroCusto = :centroCusto")
    BigDecimal somaValorPorCentroCusto(@Param("centroCusto") String centroCusto);
    
    /**
     * Soma valor total por competência
     */
    @Query("SELECT SUM(c.valor) FROM Custo c WHERE c.competencia = :competencia")
    BigDecimal somaValorPorCompetencia(@Param("competencia") String competencia);
    
    /**
     * Soma valor total por período
     */
    @Query("SELECT SUM(c.valor) FROM Custo c WHERE c.dataReferencia BETWEEN :dataInicio AND :dataFim")
    BigDecimal somaValorPorPeriodo(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    
    /**
     * Soma valor rateado por produto
     */
    @Query("SELECT SUM(c.valorRateado) FROM Custo c WHERE c.produtoId = :produtoId")
    BigDecimal somaValorRateadoPorProduto(@Param("produtoId") Long produtoId);
    
    /**
     * Soma valor rateado por cliente
     */
    @Query("SELECT SUM(c.valorRateado) FROM Custo c WHERE c.clienteId = :clienteId")
    BigDecimal somaValorRateadoPorCliente(@Param("clienteId") Long clienteId);
    
    /**
     * Conta custos por status
     */
    long countByStatus(Custo.StatusCusto status);
    
    /**
     * Conta custos por tipo
     */
    long countByTipoCusto(Custo.TipoCusto tipoCusto);
    
    /**
     * Conta custos por categoria
     */
    long countByCategoria(Custo.CategoriaCusto categoria);
    
    /**
     * Conta custos por competência
     */
    long countByCompetencia(String competencia);

    long countByProdutoId(Long produtoId);

    long countByClienteId(Long clienteId);

    long countByDataReferenciaBetween(LocalDate dataInicio, LocalDate dataFim);
    
    /**
     * Busca custos por faixa de valor
     */
    @Query("SELECT c FROM Custo c WHERE c.valor BETWEEN :valorMin AND :valorMax")
    List<Custo> findByFaixaValor(@Param("valorMin") BigDecimal valorMin, @Param("valorMax") BigDecimal valorMax);
    
    /**
     * Busca custos com variação significativa
     */
    @Query("SELECT c FROM Custo c WHERE ABS(c.percentualRateio - :percentualEsperado) > :tolerancia")
    List<Custo> findCustosComVariacaoSignificativa(@Param("percentualEsperado") BigDecimal percentualEsperado, 
                                                   @Param("tolerancia") BigDecimal tolerancia);
    
    /**
     * Busca top custos por valor
     */
    @Query("SELECT c FROM Custo c ORDER BY c.valor DESC")
    List<Custo> findTopCustosPorValor();
    
    /**
     * Busca custos não rateados
     */
    @Query("SELECT c FROM Custo c WHERE c.valorRateado IS NULL OR c.valorRateado = 0")
    List<Custo> findCustosNaoRateados();
    
    /**
     * Busca custos por método de cálculo
     */
    List<Custo> findByMetodoCalculo(String metodoCalculo);
    
    /**
     * Soma valor por método de cálculo
     */
    @Query("SELECT SUM(c.valor) FROM Custo c WHERE c.metodoCalculo = :metodoCalculo")
    BigDecimal somaValorPorMetodoCalculo(@Param("metodoCalculo") String metodoCalculo);
}
