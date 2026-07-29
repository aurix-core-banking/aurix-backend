package com.aurix.platform.banking.core.repository;

import com.aurix.platform.shared.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para Cliente
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByTenantIdAndId(String tenantId, Long id);

    List<Cliente> findByTenantId(String tenantId);

    Optional<Cliente> findByTenantIdAndCpf(String tenantId, String cpf);

    boolean existsByTenantIdAndCpf(String tenantId, String cpf);

    Optional<Cliente> findByTenantIdAndEmail(String tenantId, String email);

    boolean existsByTenantIdAndEmail(String tenantId, String email);

    /**
     * Busca cliente por CPF
     */
    Optional<Cliente> findByCpf(String cpf);
    
    /**
     * Busca cliente por email
     */
    Optional<Cliente> findByEmail(String email);
    
    /**
     * Busca cliente por CNPJ (tenant-scoped)
     */
    Optional<Cliente> findByTenantIdAndCnpj(String tenantId, String cnpj);
    
    /**
     * Verifica se existe cliente com CNPJ (tenant-scoped)
     */
    boolean existsByTenantIdAndCnpj(String tenantId, String cnpj);
    
    /**
     * Busca cliente por CNPJ
     */
    Optional<Cliente> findByCnpj(String cnpj);
    
    /**
     * Verifica se existe cliente com CNPJ
     */
    boolean existsByCnpj(String cnpj);
    
    /**
     * Verifica se existe cliente com CPF
     */
    boolean existsByCpf(String cpf);
    
    /**
     * Verifica se existe cliente com email
     */
    boolean existsByEmail(String email);
    
    /**
     * Busca clientes por status
     */
    List<Cliente> findByStatus(Cliente.StatusCliente status);
    
    /**
     * Busca clientes por nome (case insensitive)
     */
    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Cliente> findByNomeContainingIgnoreCase(@Param("nome") String nome);
    
    /**
     * Busca clientes ativos
     */
    @Query("SELECT c FROM Cliente c WHERE c.status = 'ATIVO'")
    List<Cliente> findClientesAtivos();

    List<Cliente> findByTenantIdAndStatus(String tenantId, Cliente.StatusCliente status);
    
    /**
     * Conta clientes por status
     */
    long countByStatus(Cliente.StatusCliente status);
}

