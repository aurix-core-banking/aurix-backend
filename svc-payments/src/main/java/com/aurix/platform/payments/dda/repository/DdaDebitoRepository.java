package com.aurix.platform.payments.dda.repository;

import com.aurix.platform.shared.entity.DdaDebito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DdaDebitoRepository extends JpaRepository<DdaDebito, Long> {

    Optional<DdaDebito> findByCodigoDebito(String codigoDebito);

    List<DdaDebito> findByContaDebitadaId(Long contaDebitadaId);

    List<DdaDebito> findByAutorizacaoId(Long autorizacaoId);

    List<DdaDebito> findByStatus(DdaDebito.StatusDebito status);

    @Query("SELECT d FROM DdaDebito d WHERE d.status = 'AGENDADO' AND d.dataVencimento <= :agora")
    List<DdaDebito> findDebitosParaProcessar(@Param("agora") LocalDateTime agora);

    @Query("SELECT d FROM DdaDebito d WHERE d.status = 'AGENDADO' AND d.dataVencimento BETWEEN :inicio AND :fim ORDER BY d.dataVencimento ASC")
    List<DdaDebito> findDebitosNoPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT d FROM DdaDebito d WHERE d.contaDebitada.id = :contaId AND d.dataCriacao BETWEEN :inicio AND :fim ORDER BY d.dataCriacao DESC")
    List<DdaDebito> findByContaAndPeriodo(@Param("contaId") Long contaId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    boolean existsByCodigoDebito(String codigoDebito);
}
