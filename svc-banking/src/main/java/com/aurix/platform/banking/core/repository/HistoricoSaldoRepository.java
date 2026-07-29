package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.HistoricoSaldo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoricoSaldoRepository extends JpaRepository<HistoricoSaldo, Long> {
    
    List<HistoricoSaldo> findByContaId(Long contaId);
    
    List<HistoricoSaldo> findByContaIdOrderByDataReferenciaDesc(Long contaId);
    
    @Query("SELECT h FROM HistoricoSaldo h WHERE h.conta.id = :contaId AND h.dataReferencia BETWEEN :dataInicio AND :dataFim ORDER BY h.dataReferencia DESC")
    List<HistoricoSaldo> findHistoricoPorContaEPeriodo(@Param("contaId") Long contaId,
                                                       @Param("dataInicio") LocalDateTime dataInicio,
                                                       @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT h FROM HistoricoSaldo h WHERE h.conta.id = :contaId AND h.versaoSaldo = :versaoSaldo")
    Optional<HistoricoSaldo> findByContaIdEVersao(@Param("contaId") Long contaId,
                                                  @Param("versaoSaldo") Integer versaoSaldo);
    
    @Query("SELECT h FROM HistoricoSaldo h WHERE h.conta.id = :contaId AND h.versaoSaldo = (SELECT MAX(h2.versaoSaldo) FROM HistoricoSaldo h2 WHERE h2.conta.id = :contaId)")
    Optional<HistoricoSaldo> findUltimaVersaoPorConta(@Param("contaId") Long contaId);
    
    @Query("SELECT h FROM HistoricoSaldo h WHERE h.saldoConsistente = false")
    List<HistoricoSaldo> findHistoricosComSaldoInconsistente();
    
    @Query("SELECT h FROM HistoricoSaldo h WHERE h.tipoAtualizacao = :tipoAtualizacao AND h.dataReferencia BETWEEN :dataInicio AND :dataFim")
    List<HistoricoSaldo> findHistoricosPorTipoEPeriodo(@Param("tipoAtualizacao") HistoricoSaldo.TipoAtualizacao tipoAtualizacao,
                                                       @Param("dataInicio") LocalDateTime dataInicio,
                                                       @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT h FROM HistoricoSaldo h WHERE h.conta.id = :contaId AND h.dataReferencia = :dataReferencia")
    Optional<HistoricoSaldo> findByContaIdEDataReferencia(@Param("contaId") Long contaId,
                                                          @Param("dataReferencia") LocalDateTime dataReferencia);
    
    @Query("SELECT h FROM HistoricoSaldo h WHERE h.conta.id = :contaId AND h.dataReferencia <= :dataReferencia ORDER BY h.dataReferencia DESC LIMIT 1")
    Optional<HistoricoSaldo> findUltimoHistoricoAntesDaData(@Param("contaId") Long contaId,
                                                            @Param("dataReferencia") LocalDateTime dataReferencia);
    
    @Query("SELECT h FROM HistoricoSaldo h WHERE h.codigoTransacao = :codigoTransacao")
    Optional<HistoricoSaldo> findByCodigoTransacao(@Param("codigoTransacao") String codigoTransacao);
    
    @Query("SELECT h FROM HistoricoSaldo h WHERE h.codigoLiquidacao = :codigoLiquidacao")
    Optional<HistoricoSaldo> findByCodigoLiquidacao(@Param("codigoLiquidacao") String codigoLiquidacao);
    
    @Query("SELECT h FROM HistoricoSaldo h WHERE h.codigoMovimento = :codigoMovimento")
    Optional<HistoricoSaldo> findByCodigoMovimento(@Param("codigoMovimento") String codigoMovimento);
}
