package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.CalculoRemuneracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalculoRemuneracaoRepository extends JpaRepository<CalculoRemuneracao, Long> {
    
    Optional<CalculoRemuneracao> findByCodigoCalculo(String codigoCalculo);
    
    List<CalculoRemuneracao> findByAplicacaoFinanceiraId(Long aplicacaoFinanceiraId);
    
    List<CalculoRemuneracao> findByProdutoFinanceiroId(Long produtoFinanceiroId);
    
    List<CalculoRemuneracao> findByTipoCalculo(CalculoRemuneracao.TipoCalculo tipoCalculo);
    
    List<CalculoRemuneracao> findByStatus(CalculoRemuneracao.StatusCalculo status);
    
    @Query("SELECT c FROM CalculoRemuneracao c WHERE c.aplicacaoFinanceira.id = :aplicacaoFinanceiraId AND c.dataCalculo BETWEEN :dataInicio AND :dataFim")
    List<CalculoRemuneracao> findCalculosPorAplicacaoEPeriodo(@Param("aplicacaoFinanceiraId") Long aplicacaoFinanceiraId,
                                                              @Param("dataInicio") LocalDateTime dataInicio,
                                                              @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM CalculoRemuneracao c WHERE c.tipoCalculo = :tipoCalculo AND c.dataCalculo BETWEEN :dataInicio AND :dataFim")
    List<CalculoRemuneracao> findCalculosPorTipoEPeriodo(@Param("tipoCalculo") CalculoRemuneracao.TipoCalculo tipoCalculo,
                                                         @Param("dataInicio") LocalDateTime dataInicio,
                                                         @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM CalculoRemuneracao c WHERE c.status = :status AND c.dataCalculo BETWEEN :dataInicio AND :dataFim")
    List<CalculoRemuneracao> findCalculosPorStatusEPeriodo(@Param("status") CalculoRemuneracao.StatusCalculo status,
                                                           @Param("dataInicio") LocalDateTime dataInicio,
                                                           @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM CalculoRemuneracao c WHERE c.calculoAutomatico = :calculoAutomatico AND c.dataCalculo BETWEEN :dataInicio AND :dataFim")
    List<CalculoRemuneracao> findCalculosAutomaticos(@Param("calculoAutomatico") Boolean calculoAutomatico,
                                                     @Param("dataInicio") LocalDateTime dataInicio,
                                                     @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM CalculoRemuneracao c WHERE c.calculoCorreto = :calculoCorreto AND c.dataCalculo BETWEEN :dataInicio AND :dataFim")
    List<CalculoRemuneracao> findCalculosCorretos(@Param("calculoCorreto") Boolean calculoCorreto,
                                                  @Param("dataInicio") LocalDateTime dataInicio,
                                                  @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT c FROM CalculoRemuneracao c WHERE c.valorRemuneracao >= :valorMinimo AND c.valorRemuneracao <= :valorMaximo")
    List<CalculoRemuneracao> findCalculosPorFaixaValor(@Param("valorMinimo") Double valorMinimo, @Param("valorMaximo") Double valorMaximo);
    
    @Query("SELECT c FROM CalculoRemuneracao c WHERE c.taxaAplicada >= :taxaMinima AND c.taxaAplicada <= :taxaMaxima")
    List<CalculoRemuneracao> findCalculosPorFaixaTaxa(@Param("taxaMinima") Double taxaMinima, @Param("taxaMaxima") Double taxaMaxima);
    
    @Query("SELECT c FROM CalculoRemuneracao c WHERE c.diasCalculo >= :diasMinimos AND c.diasCalculo <= :diasMaximos")
    List<CalculoRemuneracao> findCalculosPorFaixaDias(@Param("diasMinimos") Integer diasMinimos, @Param("diasMaximos") Integer diasMaximos);
    
    @Query("SELECT c FROM CalculoRemuneracao c WHERE c.codigoTransacao = :codigoTransacao")
    Optional<CalculoRemuneracao> findByCodigoTransacao(@Param("codigoTransacao") String codigoTransacao);
    
    @Query("SELECT c FROM CalculoRemuneracao c WHERE c.codigoLiquidacao = :codigoLiquidacao")
    Optional<CalculoRemuneracao> findByCodigoLiquidacao(@Param("codigoLiquidacao") String codigoLiquidacao);
}
