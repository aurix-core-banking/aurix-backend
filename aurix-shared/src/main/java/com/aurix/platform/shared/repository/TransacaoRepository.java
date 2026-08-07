package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.Transacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    Optional<Transacao> findByTenantIdAndId(String tenantId, Long id);

    List<Transacao> findByTenantId(String tenantId);

    Optional<Transacao> findByCodigoTransacao(String codigoTransacao);

    Optional<Transacao> findByTenantIdAndCodigoTransacao(String tenantId, String codigoTransacao);

    List<Transacao> findByContaOrigemId(Long contaOrigemId);

    List<Transacao> findByContaDestinoId(Long contaDestinoId);

    List<Transacao> findByStatus(Transacao.StatusTransacao status);

    List<Transacao> findByTipoTransacao(Transacao.TipoTransacao tipoTransacao);

    @Query("SELECT t FROM Transacao t WHERE t.contaOrigem.id = :contaId OR t.contaDestino.id = :contaId ORDER BY t.dataTransacao DESC")
    List<Transacao> findByContaId(@Param("contaId") Long contaId);

    @Query("SELECT t FROM Transacao t WHERE t.tenantId = :tenantId AND (t.contaOrigem.id = :contaId OR t.contaDestino.id = :contaId) ORDER BY t.dataTransacao DESC")
    Page<Transacao> findByTenantIdAndContaIdOrderByDataTransacaoDesc(
            @Param("tenantId") String tenantId, @Param("contaId") Long contaId, Pageable pageable);

    @Query("SELECT t FROM Transacao t WHERE t.tenantId = :tenantId AND (t.contaOrigem.id = :contaId OR t.contaDestino.id = :contaId) AND t.dataTransacao BETWEEN :inicio AND :fim ORDER BY t.dataTransacao DESC")
    Page<Transacao> findByTenantIdAndContaIdEPeriodo(
            @Param("tenantId") String tenantId, @Param("contaId") Long contaId,
            @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, Pageable pageable);

    @Query("SELECT t FROM Transacao t WHERE (t.contaOrigem.id = :contaId OR t.contaDestino.id = :contaId) AND t.dataTransacao BETWEEN :inicio AND :fim ORDER BY t.dataTransacao DESC")
    List<Transacao> findByContaIdEPeriodo(@Param("contaId") Long contaId,
                                          @Param("inicio") LocalDateTime inicio,
                                          @Param("fim") LocalDateTime fim);

    @Query("SELECT COALESCE(SUM(t.valor), 0) FROM Transacao t "
           + "WHERE (t.contaOrigem.id = :contaId) "
           + "AND t.dataTransacao >= :startOfDay "
           + "AND t.status NOT IN ('CANCELADA', 'FALHADA', 'REVERTIDA')")
    BigDecimal sumDailyDebitsByContaOrigem(@Param("contaId") Long contaId,
                                           @Param("startOfDay") LocalDateTime startOfDay);

    @Query("SELECT t FROM Transacao t WHERE t.status = 'PENDENTE' ORDER BY t.dataTransacao")
    List<Transacao> findTransacoesPendentes();

    @Query("SELECT t FROM Transacao t WHERE t.tenantId = :tenantId AND t.status = 'PENDENTE' ORDER BY t.dataTransacao")
    Page<Transacao> findTransacoesPendentesByTenantId(@Param("tenantId") String tenantId, Pageable pageable);
}
