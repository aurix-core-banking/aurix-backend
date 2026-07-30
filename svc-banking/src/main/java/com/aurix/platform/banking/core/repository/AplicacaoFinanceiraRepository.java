package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.AplicacaoFinanceira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AplicacaoFinanceiraRepository extends JpaRepository<AplicacaoFinanceira, Long> {
    
    Optional<AplicacaoFinanceira> findByCodigoAplicacao(String codigoAplicacao);
    
    List<AplicacaoFinanceira> findByContaId(Long contaId);
    
    List<AplicacaoFinanceira> findByProdutoFinanceiroId(Long produtoFinanceiroId);
    
    List<AplicacaoFinanceira> findByStatus(AplicacaoFinanceira.StatusAplicacao status);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.conta.id = :contaId AND a.dataAplicacao BETWEEN :dataInicio AND :dataFim")
    List<AplicacaoFinanceira> findAplicacoesPorContaEPeriodo(@Param("contaId") Long contaId,
                                                             @Param("dataInicio") LocalDateTime dataInicio,
                                                             @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.produtoFinanceiro.id = :produtoFinanceiroId AND a.dataAplicacao BETWEEN :dataInicio AND :dataFim")
    List<AplicacaoFinanceira> findAplicacoesPorProdutoEPeriodo(@Param("produtoFinanceiroId") Long produtoFinanceiroId,
                                                               @Param("dataInicio") LocalDateTime dataInicio,
                                                               @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.status = :status AND a.dataAplicacao BETWEEN :dataInicio AND :dataFim")
    List<AplicacaoFinanceira> findAplicacoesPorStatusEPeriodo(@Param("status") AplicacaoFinanceira.StatusAplicacao status,
                                                              @Param("dataInicio") LocalDateTime dataInicio,
                                                              @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.permiteResgateAntecipado = :permiteResgateAntecipado AND a.status = 'ATIVA'")
    List<AplicacaoFinanceira> findAplicacoesQuePermitemResgateAntecipado(@Param("permiteResgateAntecipado") Boolean permiteResgateAntecipado);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.renovacaoAutomatica = :renovacaoAutomatica AND a.status = 'ATIVA'")
    List<AplicacaoFinanceira> findAplicacoesComRenovacaoAutomatica(@Param("renovacaoAutomatica") Boolean renovacaoAutomatica);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.reaplicacaoAutomatica = :reaplicacaoAutomatica AND a.status = 'ATIVA'")
    List<AplicacaoFinanceira> findAplicacoesComReaplicacaoAutomatica(@Param("reaplicacaoAutomatica") Boolean reaplicacaoAutomatica);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.dataVencimento <= :dataAtual AND a.status = 'ATIVA'")
    List<AplicacaoFinanceira> findAplicacoesVencidas(@Param("dataAtual") LocalDateTime dataAtual);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.dataProximaRemuneracao <= :dataAtual AND a.status = 'ATIVA'")
    List<AplicacaoFinanceira> findAplicacoesParaRemuneracao(@Param("dataAtual") LocalDateTime dataAtual);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.dataUltimaRemuneracao IS NULL AND a.status = 'ATIVA'")
    List<AplicacaoFinanceira> findAplicacoesSemRemuneracao();
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.dataUltimaRemuneracao < :dataLimite AND a.status = 'ATIVA'")
    List<AplicacaoFinanceira> findAplicacoesComRemuneracaoAtrasada(@Param("dataLimite") LocalDateTime dataLimite);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.valorAplicacao >= :valorMinimo AND a.valorAplicacao <= :valorMaximo")
    List<AplicacaoFinanceira> findAplicacoesPorFaixaValor(@Param("valorMinimo") Double valorMinimo, @Param("valorMaximo") Double valorMaximo);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.taxaRemuneracao >= :taxaMinima AND a.taxaRemuneracao <= :taxaMaxima")
    List<AplicacaoFinanceira> findAplicacoesPorFaixaTaxa(@Param("taxaMinima") Double taxaMinima, @Param("taxaMaxima") Double taxaMaxima);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.prazoDias >= :prazoMinimo AND a.prazoDias <= :prazoMaximo")
    List<AplicacaoFinanceira> findAplicacoesPorFaixaPrazo(@Param("prazoMinimo") Integer prazoMinimo, @Param("prazoMaximo") Integer prazoMaximo);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.codigoTransacao = :codigoTransacao")
    Optional<AplicacaoFinanceira> findByCodigoTransacao(@Param("codigoTransacao") String codigoTransacao);
    
    @Query("SELECT a FROM AplicacaoFinanceira a WHERE a.codigoLiquidacao = :codigoLiquidacao")
    Optional<AplicacaoFinanceira> findByCodigoLiquidacao(@Param("codigoLiquidacao") String codigoLiquidacao);
}
