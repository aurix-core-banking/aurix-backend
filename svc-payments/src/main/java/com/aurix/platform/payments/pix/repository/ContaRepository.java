package com.aurix.platform.payments.pix.repository;

import com.aurix.platform.shared.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Repositório para Conta (Visão PIX)
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    /**
     * Busca conta por número
     */
    Optional<Conta> findByNumeroConta(String numeroConta);

    /**
     * Debita o valor do saldo de forma atômica, condicionada a saldo suficiente
     * (ver ADR-0002 — sem read-modify-write). Retorna 1 se debitou, 0 se a conta
     * não existe ou o saldo era insuficiente.
     */
    @Modifying
    @Query("UPDATE Conta c SET c.saldo = c.saldo - :valor, c.versao = c.versao + 1 "
            + "WHERE c.id = :id AND c.tenantId = :tenantId AND c.saldo >= :valor")
    int debitarSaldoAtomico(@Param("tenantId") String tenantId, @Param("id") Long id,
            @Param("valor") BigDecimal valor);

    /**
     * Credita o valor no saldo de forma atômica.
     */
    @Modifying
    @Query("UPDATE Conta c SET c.saldo = c.saldo + :valor, c.versao = c.versao + 1 "
            + "WHERE c.id = :id AND c.tenantId = :tenantId")
    int creditarSaldoAtomico(@Param("tenantId") String tenantId, @Param("id") Long id,
            @Param("valor") BigDecimal valor);
}
