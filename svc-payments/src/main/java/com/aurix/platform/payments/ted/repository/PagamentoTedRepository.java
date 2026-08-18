package com.aurix.platform.payments.ted.repository;

import com.aurix.platform.shared.entity.PagamentoTed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoTedRepository extends JpaRepository<PagamentoTed, Long> {

    Optional<PagamentoTed> findByCodigoTed(String codigoTed);

    List<PagamentoTed> findByContaOrigemId(Long contaOrigemId);

    List<PagamentoTed> findByStatus(PagamentoTed.StatusTed status);

    List<PagamentoTed> findByTipoPagamento(PagamentoTed.TipoPagamento tipoPagamento);

    @Query("SELECT t FROM PagamentoTed t WHERE t.status = 'PENDENTE' AND t.dataAgendamento <= :agora")
    List<PagamentoTed> findPendentesParaProcessar(@Param("agora") LocalDateTime agora);

    @Query("SELECT t FROM PagamentoTed t WHERE t.contaOrigem.id = :contaId AND t.dataCriacao BETWEEN :inicio AND :fim ORDER BY t.dataCriacao DESC")
    List<PagamentoTed> findByContaAndPeriodo(@Param("contaId") Long contaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT t FROM PagamentoTed t WHERE t.dataCriacao BETWEEN :inicio AND :fim ORDER BY t.dataCriacao DESC")
    List<PagamentoTed> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    boolean existsByCodigoTed(String codigoTed);
}
