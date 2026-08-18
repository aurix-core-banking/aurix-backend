package com.aurix.platform.payments.chargeback.repository;

import com.aurix.platform.shared.entity.Chargeback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChargebackRepository extends JpaRepository<Chargeback, Long> {

    Optional<Chargeback> findByCodigoChargeback(String codigoChargeback);

    List<Chargeback> findByContaId(Long contaId);

    List<Chargeback> findByStatus(Chargeback.StatusChargeback status);

    List<Chargeback> findByTransacaoOrigemId(Long transacaoOrigemId);

    @Query("SELECT c FROM Chargeback c WHERE c.status IN ('ABERTO', 'EM_ANALISE', 'EM_CONTESTACAO') AND c.prazoLimite < :agora")
    List<Chargeback> findExpirados(@Param("agora") LocalDateTime agora);

    @Query("SELECT c FROM Chargeback c WHERE c.conta.id = :contaId AND c.dataSolicitacao BETWEEN :inicio AND :fim ORDER BY c.dataSolicitacao DESC")
    List<Chargeback> findByContaAndPeriodo(@Param("contaId") Long contaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT c FROM Chargeback c WHERE c.dataSolicitacao BETWEEN :inicio AND :fim ORDER BY c.dataSolicitacao DESC")
    List<Chargeback> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    boolean existsByCodigoChargeback(String codigoChargeback);
}
