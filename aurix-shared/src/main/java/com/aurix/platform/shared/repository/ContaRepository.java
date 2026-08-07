package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByTenantIdAndId(String tenantId, Long id);

    List<Conta> findByTenantId(String tenantId);

    Optional<Conta> findByTenantIdAndNumeroConta(String tenantId, String numeroConta);

    boolean existsByTenantIdAndNumeroConta(String tenantId, String numeroConta);

    Optional<Conta> findByNumeroConta(String numeroConta);

    List<Conta> findByClienteId(Long clienteId);

    List<Conta> findByTenantIdAndClienteId(String tenantId, Long clienteId);

    @Query("SELECT c FROM Conta c WHERE c.cliente.id = :clienteId AND c.status = 'ATIVA'")
    List<Conta> findContasAtivasByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT c FROM Conta c WHERE c.tenantId = :tenantId AND c.cliente.id = :clienteId AND c.status = 'ATIVA'")
    List<Conta> findContasAtivasByTenantIdAndClienteId(@Param("tenantId") String tenantId, @Param("clienteId") Long clienteId);

    List<Conta> findByTipoConta(Conta.TipoConta tipoConta);

    List<Conta> findByStatus(Conta.StatusConta status);

    boolean existsByNumeroConta(String numeroConta);

    @Query("SELECT c FROM Conta c WHERE c.saldo >= :valor")
    List<Conta> findContasComSaldoMaiorQue(@Param("valor") BigDecimal valor);

    @Query("SELECT c FROM Conta c WHERE (c.limiteCredito - c.limiteUtilizado) >= :valor")
    List<Conta> findContasComLimiteDisponivelMaiorQue(@Param("valor") BigDecimal valor);

    @Query("SELECT SUM(c.saldo) FROM Conta c WHERE c.cliente.id = :clienteId AND c.status = 'ATIVA'")
    BigDecimal somarSaldosPorCliente(@Param("clienteId") Long clienteId);

    long countByStatus(Conta.StatusConta status);

    long countByTipoConta(Conta.TipoConta tipoConta);

    @Modifying
    @Query("UPDATE Conta c SET c.saldo = c.saldo - :valor, c.versao = c.versao + 1 "
            + "WHERE c.id = :id AND c.tenantId = :tenantId AND c.saldo >= :valor")
    int debitarSaldoAtomico(@Param("tenantId") String tenantId, @Param("id") Long id,
            @Param("valor") BigDecimal valor);

    @Modifying
    @Query("UPDATE Conta c SET c.saldo = c.saldo + :valor, c.versao = c.versao + 1 "
            + "WHERE c.id = :id AND c.tenantId = :tenantId")
    int creditarSaldoAtomico(@Param("tenantId") String tenantId, @Param("id") Long id,
            @Param("valor") BigDecimal valor);

    @Modifying
    @Query("UPDATE Conta c SET c.limiteUtilizado = c.limiteUtilizado + :valor, c.versao = c.versao + 1 "
            + "WHERE c.id = :id AND c.tenantId = :tenantId "
            + "AND (c.limiteCredito - c.limiteUtilizado) >= :valor")
    int utilizarLimiteAtomico(@Param("tenantId") String tenantId, @Param("id") Long id,
            @Param("valor") BigDecimal valor);

    @Modifying
    @Query("UPDATE Conta c SET c.limiteUtilizado = c.limiteUtilizado - :valor, c.versao = c.versao + 1 "
            + "WHERE c.id = :id AND c.tenantId = :tenantId")
    int liberarLimiteAtomico(@Param("tenantId") String tenantId, @Param("id") Long id,
            @Param("valor") BigDecimal valor);
}
