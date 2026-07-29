package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.EventoRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRiscoRepository extends JpaRepository<EventoRisco, Long> {
    
    Optional<EventoRisco> findByCodigoEvento(String codigoEvento);
    
    List<EventoRisco> findByContaId(Long contaId);
    
    List<EventoRisco> findByTransacaoId(Long transacaoId);
    
    List<EventoRisco> findByLiquidacaoId(Long liquidacaoId);
    
    List<EventoRisco> findByTipoEvento(EventoRisco.TipoEvento tipoEvento);
    
    List<EventoRisco> findByCategoriaEvento(EventoRisco.CategoriaEvento categoriaEvento);
    
    List<EventoRisco> findByNivelRisco(EventoRisco.NivelRisco nivelRisco);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.conta.id = :contaId AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosPorContaEPeriodo(@Param("contaId") Long contaId,
                                                  @Param("dataInicio") LocalDateTime dataInicio,
                                                  @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.tipoEvento = :tipoEvento AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosPorTipoEPeriodo(@Param("tipoEvento") EventoRisco.TipoEvento tipoEvento,
                                                 @Param("dataInicio") LocalDateTime dataInicio,
                                                 @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.categoriaEvento = :categoriaEvento AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosPorCategoriaEPeriodo(@Param("categoriaEvento") EventoRisco.CategoriaEvento categoriaEvento,
                                                      @Param("dataInicio") LocalDateTime dataInicio,
                                                      @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.nivelRisco = :nivelRisco AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosPorNivelRiscoEPeriodo(@Param("nivelRisco") EventoRisco.NivelRisco nivelRisco,
                                                       @Param("dataInicio") LocalDateTime dataInicio,
                                                       @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.critico = :critico AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosCriticos(@Param("critico") Boolean critico,
                                          @Param("dataInicio") LocalDateTime dataInicio,
                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.urgente = :urgente AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosUrgentes(@Param("urgente") Boolean urgente,
                                          @Param("dataInicio") LocalDateTime dataInicio,
                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.resolvido = :resolvido AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosResolvidos(@Param("resolvido") Boolean resolvido,
                                            @Param("dataInicio") LocalDateTime dataInicio,
                                            @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.requerAcao = :requerAcao AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosQueRequeremAcao(@Param("requerAcao") Boolean requerAcao,
                                                 @Param("dataInicio") LocalDateTime dataInicio,
                                                 @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.requerNotificacao = :requerNotificacao AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosQueRequeremNotificacao(@Param("requerNotificacao") Boolean requerNotificacao,
                                                        @Param("dataInicio") LocalDateTime dataInicio,
                                                        @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.requerEscalacao = :requerEscalacao AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosQueRequeremEscalacao(@Param("requerEscalacao") Boolean requerEscalacao,
                                                      @Param("dataInicio") LocalDateTime dataInicio,
                                                      @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.requerAuditoria = :requerAuditoria AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosQueRequeremAuditoria(@Param("requerAuditoria") Boolean requerAuditoria,
                                                      @Param("dataInicio") LocalDateTime dataInicio,
                                                      @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.scoreRisco >= :scoreMinimo AND e.scoreRisco <= :scoreMaximo AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosPorScore(@Param("scoreMinimo") Integer scoreMinimo,
                                          @Param("scoreMaximo") Integer scoreMaximo,
                                          @Param("dataInicio") LocalDateTime dataInicio,
                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.valorEnvolvido >= :valorMinimo AND e.valorEnvolvido <= :valorMaximo AND e.dataEvento BETWEEN :dataInicio AND :dataFim")
    List<EventoRisco> findEventosPorValor(@Param("valorMinimo") Double valorMinimo,
                                          @Param("valorMaximo") Double valorMaximo,
                                          @Param("dataInicio") LocalDateTime dataInicio,
                                          @Param("dataFim") LocalDateTime dataFim);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.codigoTransacao = :codigoTransacao")
    Optional<EventoRisco> findByCodigoTransacao(@Param("codigoTransacao") String codigoTransacao);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.codigoLiquidacao = :codigoLiquidacao")
    Optional<EventoRisco> findByCodigoLiquidacao(@Param("codigoLiquidacao") String codigoLiquidacao);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.codigoBacen = :codigoBacen")
    Optional<EventoRisco> findByCodigoBacen(@Param("codigoBacen") String codigoBacen);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.codigoSPI = :codigoSPI")
    Optional<EventoRisco> findByCodigoSPI(@Param("codigoSPI") String codigoSPI);
    
    @Query("SELECT e FROM EventoRisco e WHERE e.codigoSTR = :codigoSTR")
    Optional<EventoRisco> findByCodigoSTR(@Param("codigoSTR") String codigoSTR);
}
