package com.aurix.platform.banking.core.repository;

import com.aurix.platform.shared.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para Conta
 */
@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByTenantIdAndId(String tenantId, Long id);

    List<Conta> findByTenantId(String tenantId);

    Optional<Conta> findByTenantIdAndNumeroConta(String tenantId, String numeroConta);

    boolean existsByTenantIdAndNumeroConta(String tenantId, String numeroConta);

    /**
     * Busca conta por número
     */
    Optional<Conta> findByNumeroConta(String numeroConta);

    /**
     * Busca contas por cliente
     */
    List<Conta> findByClienteId(Long clienteId);

    List<Conta> findByTenantIdAndClienteId(String tenantId, Long clienteId);
    
    /**
     * Busca contas ativas por cliente
     */
    @Query("SELECT c FROM Conta c WHERE c.cliente.id = :clienteId AND c.status = 'ATIVA'")
    List<Conta> findContasAtivasByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT c FROM Conta c WHERE c.tenantId = :tenantId AND c.cliente.id = :clienteId AND c.status = 'ATIVA'")
    List<Conta> findContasAtivasByTenantIdAndClienteId(@Param("tenantId") String tenantId, @Param("clienteId") Long clienteId);
    
    /**
     * Busca contas por tipo
     */
    List<Conta> findByTipoConta(Conta.TipoConta tipoConta);
    
    /**
     * Busca contas por status
     */
    List<Conta> findByStatus(Conta.StatusConta status);
    
    /**
     * Verifica se existe conta com número
     */
    boolean existsByNumeroConta(String numeroConta);
    
    /**
     * Busca contas com saldo maior que valor
     */
    @Query("SELECT c FROM Conta c WHERE c.saldo >= :valor")
    List<Conta> findContasComSaldoMaiorQue(@Param("valor") BigDecimal valor);
    
    /**
     * Busca contas com limite disponível maior que valor
     */
    @Query("SELECT c FROM Conta c WHERE (c.limiteCredito - c.limiteUtilizado) >= :valor")
    List<Conta> findContasComLimiteDisponivelMaiorQue(@Param("valor") BigDecimal valor);
    
    /**
     * Soma total de saldos por cliente
     */
    @Query("SELECT SUM(c.saldo) FROM Conta c WHERE c.cliente.id = :clienteId AND c.status = 'ATIVA'")
    BigDecimal somarSaldosPorCliente(@Param("clienteId") Long clienteId);
    
    /**
     * Conta contas por status
     */
    long countByStatus(Conta.StatusConta status);
    
    /**
     * Conta contas por tipo
     */
    long countByTipoConta(Conta.TipoConta tipoConta);

    /**
     * Debita o valor do saldo de forma atômica, condicionada a saldo suficiente.
     * A condição e a escrita ocorrem na mesma instrução SQL (sem read-modify-write),
     * eliminando a janela de corrida descrita no ADR-0002. Retorna 1 se debitou,
     * 0 se a conta não existe, é de outro tenant, ou o saldo era insuficiente.
     */
    @Modifying
    @Query("UPDATE Conta c SET c.saldo = c.saldo - :valor, c.versao = c.versao + 1 "
            + "WHERE c.id = :id AND c.tenantId = :tenantId AND c.saldo >= :valor")
    int debitarSaldoAtomico(@Param("tenantId") String tenantId, @Param("id") Long id,
            @Param("valor") BigDecimal valor);

    /**
     * Credita o valor no saldo de forma atômica. Sempre afeta 1 linha se a conta existir
     * (crédito não tem condição de saldo mínimo).
     */
    @Modifying
    @Query("UPDATE Conta c SET c.saldo = c.saldo + :valor, c.versao = c.versao + 1 "
            + "WHERE c.id = :id AND c.tenantId = :tenantId")
    int creditarSaldoAtomico(@Param("tenantId") String tenantId, @Param("id") Long id,
            @Param("valor") BigDecimal valor);

    /**
     * Aumenta o limite utilizado de forma atômica, condicionado a limite disponível suficiente.
     */
    @Modifying
    @Query("UPDATE Conta c SET c.limiteUtilizado = c.limiteUtilizado + :valor, c.versao = c.versao + 1 "
            + "WHERE c.id = :id AND c.tenantId = :tenantId "
            + "AND (c.limiteCredito - c.limiteUtilizado) >= :valor")
    int utilizarLimiteAtomico(@Param("tenantId") String tenantId, @Param("id") Long id,
            @Param("valor") BigDecimal valor);

    /**
     * Reduz o limite utilizado de forma atômica.
     */
    @Modifying
    @Query("UPDATE Conta c SET c.limiteUtilizado = c.limiteUtilizado - :valor, c.versao = c.versao + 1 "
            + "WHERE c.id = :id AND c.tenantId = :tenantId")
    int liberarLimiteAtomico(@Param("tenantId") String tenantId, @Param("id") Long id,
            @Param("valor") BigDecimal valor);
}

