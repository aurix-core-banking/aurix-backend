package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.AlertaRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlertaRiscoRepository extends JpaRepository<AlertaRisco, Long> {
    
    Optional<AlertaRisco> findByCodigoAlerta(String codigoAlerta);
    
    List<AlertaRisco> findByAvaliacaoRiscoId(Long avaliacaoRiscoId);
    
    List<AlertaRisco> findByRegraRiscoId(Long regraRiscoId);
    
    List<AlertaRisco> findByContaId(Long contaId);
    
    List<AlertaRisco> findByTransacaoId(Long transacaoId);
    
    List<AlertaRisco> findByTipoAlerta(AlertaRisco.TipoAlerta tipoAlerta);
    
    List<AlertaRisco> findByNivelAlerta(AlertaRisco.NivelAlerta nivelAlerta);
    
    List<AlertaRisco> findByStatus(AlertaRisco.StatusAlerta status);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.conta.id = :contaId AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasPorContaEPeriodo(@Param("contaId") Long contaId,
                                                  @Param("dataInicio") LocalDateTime dataInicio,
                                                  @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.tipoAlerta = :tipoAlerta AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasPorTipoEPeriodo(@Param("tipoAlerta") AlertaRisco.TipoAlerta tipoAlerta,
                                                 @Param("dataInicio") LocalDateTime dataInicio,
                                                 @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.nivelAlerta = :nivelAlerta AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasPorNivelEPeriodo(@Param("nivelAlerta") AlertaRisco.NivelAlerta nivelAlerta,
                                                  @Param("dataInicio") LocalDateTime dataInicio,
                                                  @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.status = :status AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasPorStatusEPeriodo(@Param("status") AlertaRisco.StatusAlerta status,
                                                   @Param("dataInicio") LocalDateTime dataInicio,
                                                   @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.critico = :critico AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasCriticos(@Param("critico") Boolean critico,
                                          @Param("dataInicio") LocalDateTime dataInicio,
                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.urgente = :urgente AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasUrgentes(@Param("urgente") Boolean urgente,
                                          @Param("dataInicio") LocalDateTime dataInicio,
                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.requerAcao = :requerAcao AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasQueRequeremAcao(@Param("requerAcao") Boolean requerAcao,
                                                 @Param("dataInicio") LocalDateTime dataInicio,
                                                 @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.requerNotificacao = :requerNotificacao AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasQueRequeremNotificacao(@Param("requerNotificacao") Boolean requerNotificacao,
                                                        @Param("dataInicio") LocalDateTime dataInicio,
                                                        @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.requerEscalacao = :requerEscalacao AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasQueRequeremEscalacao(@Param("requerEscalacao") Boolean requerEscalacao,
                                                      @Param("dataInicio") LocalDateTime dataInicio,
                                                      @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.requerAuditoria = :requerAuditoria AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasQueRequeremAuditoria(@Param("requerAuditoria") Boolean requerAuditoria,
                                                      @Param("dataInicio") LocalDateTime dataInicio,
                                                      @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.scoreRisco >= :scoreMinimo AND a.scoreRisco <= :scoreMaximo AND a.dataAlerta BETWEEN :dataInicio AND :dataFim")
    List<AlertaRisco> findAlertasPorScore(@Param("scoreMinimo") Integer scoreMinimo,
                                          @Param("scoreMaximo") Integer scoreMaximo,
                                          @Param("dataInicio") LocalDateTime dataInicio,
                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.codigoTransacao = :codigoTransacao")
    Optional<AlertaRisco> findByCodigoTransacao(@Param("codigoTransacao") String codigoTransacao);
    
    @Query("SELECT a FROM AlertaRisco a WHERE a.codigoLiquidacao = :codigoLiquidacao")
    Optional<AlertaRisco> findByCodigoLiquidacao(@Param("codigoLiquidacao") String codigoLiquidacao);
}
