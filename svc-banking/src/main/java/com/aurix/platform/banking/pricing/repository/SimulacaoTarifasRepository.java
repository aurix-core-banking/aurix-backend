package com.aurix.platform.banking.pricing.repository;

import com.aurix.platform.banking.pricing.entity.SimulacaoTarifas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para simulações de tarifas
 */
@Repository
public interface SimulacaoTarifasRepository extends JpaRepository<SimulacaoTarifas, Long> {
    
    /**
     * Busca simulação por número
     */
    Optional<SimulacaoTarifas> findByNumeroSimulacao(String numeroSimulacao);
    
    /**
     * Busca simulações por cliente
     */
    List<SimulacaoTarifas> findByClienteId(String clienteId);
    
    /**
     * Busca simulações por tipo
     */
    List<SimulacaoTarifas> findByTipoSimulacao(SimulacaoTarifas.TipoSimulacao tipoSimulacao);
    
    /**
     * Busca simulações por produto
     */
    List<SimulacaoTarifas> findByProduto(String produto);
    
    /**
     * Busca simulações por segmento de cliente
     */
    List<SimulacaoTarifas> findBySegmentoCliente(String segmentoCliente);
    
    /**
     * Busca simulações por canal
     */
    List<SimulacaoTarifas> findByCanal(String canal);
    
    /**
     * Busca simulações por região
     */
    List<SimulacaoTarifas> findByRegiao(String regiao);
    
    /**
     * Busca simulações por status
     */
    List<SimulacaoTarifas> findByStatusSimulacao(String statusSimulacao);
    
    /**
     * Busca simulações por usuário
     */
    List<SimulacaoTarifas> findByUsuarioSimulacao(String usuarioSimulacao);
    
    /**
     * Busca simulações por período
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.dataSimulacao BETWEEN :dataInicio AND :dataFim")
    List<SimulacaoTarifas> findByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca simulações ativas (não expiradas)
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.dataExpiracao > :dataAtual")
    List<SimulacaoTarifas> findSimulacoesAtivas(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca simulações expiradas
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.dataExpiracao <= :dataAtual")
    List<SimulacaoTarifas> findSimulacoesExpiradas(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca simulações por faixa de economia
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.economiaTotal BETWEEN :economiaMinima AND :economiaMaxima")
    List<SimulacaoTarifas> findByFaixaEconomia(@Param("economiaMinima") BigDecimal economiaMinima, @Param("economiaMaxima") BigDecimal economiaMaxima);
    
    /**
     * Busca simulações por faixa de percentual de economia
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.economiaPercentual BETWEEN :percentualMinimo AND :percentualMaximo")
    List<SimulacaoTarifas> findByFaixaEconomiaPercentual(@Param("percentualMinimo") BigDecimal percentualMinimo, @Param("percentualMaximo") BigDecimal percentualMaximo);
    
    /**
     * Busca simulações com economia positiva
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.economiaTotal > 0")
    List<SimulacaoTarifas> findSimulacoesComEconomia();
    
    /**
     * Busca simulações por volume de operações
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.volumeOperacoes BETWEEN :volumeMinimo AND :volumeMaximo")
    List<SimulacaoTarifas> findByFaixaVolume(@Param("volumeMinimo") Integer volumeMinimo, @Param("volumeMaximo") Integer volumeMaximo);
    
    /**
     * Busca simulações por valor total de operações
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.valorTotalOperacoes BETWEEN :valorMinimo AND :valorMaximo")
    List<SimulacaoTarifas> findByFaixaValorTotal(@Param("valorMinimo") BigDecimal valorMinimo, @Param("valorMaximo") BigDecimal valorMaximo);
    
    /**
     * Busca simulações por período de simulação
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.periodoSimulacao BETWEEN :periodoMinimo AND :periodoMaximo")
    List<SimulacaoTarifas> findByFaixaPeriodo(@Param("periodoMinimo") Integer periodoMinimo, @Param("periodoMaximo") Integer periodoMaximo);
    
    /**
     * Busca simulações mais recentes por cliente
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.clienteId = :clienteId ORDER BY s.dataSimulacao DESC")
    List<SimulacaoTarifas> findSimulacoesRecentesPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Conta simulações por status
     */
    long countByStatusSimulacao(String statusSimulacao);
    
    /**
     * Conta simulações por tipo
     */
    long countByTipoSimulacao(SimulacaoTarifas.TipoSimulacao tipoSimulacao);
    
    /**
     * Conta simulações por produto
     */
    long countByProduto(String produto);
    
    /**
     * Conta simulações por cliente
     */
    long countByClienteId(String clienteId);
    
    /**
     * Soma economia total por cliente
     */
    @Query("SELECT SUM(s.economiaTotal) FROM SimulacaoTarifas s WHERE s.clienteId = :clienteId AND s.economiaTotal > 0")
    BigDecimal somaEconomiaPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Soma economia total por produto
     */
    @Query("SELECT SUM(s.economiaTotal) FROM SimulacaoTarifas s WHERE s.produto = :produto AND s.economiaTotal > 0")
    BigDecimal somaEconomiaPorProduto(@Param("produto") String produto);
    
    /**
     * Busca simulações concluídas
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.statusSimulacao = 'CONCLUIDA'")
    List<SimulacaoTarifas> findSimulacoesConcluidas();
    
    /**
     * Busca simulações em processamento
     */
    @Query("SELECT s FROM SimulacaoTarifas s WHERE s.statusSimulacao = 'PROCESSANDO'")
    List<SimulacaoTarifas> findSimulacoesEmProcessamento();
}
