package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByTenantIdAndId(String tenantId, Long id);

    List<Cliente> findByTenantId(String tenantId);

    Optional<Cliente> findByTenantIdAndCpf(String tenantId, String cpf);

    boolean existsByTenantIdAndCpf(String tenantId, String cpf);

    Optional<Cliente> findByTenantIdAndEmail(String tenantId, String email);

    boolean existsByTenantIdAndEmail(String tenantId, String email);

    Optional<Cliente> findByCpf(String cpf);

    Optional<Cliente> findByEmail(String email);

    Optional<Cliente> findByTenantIdAndCnpj(String tenantId, String cnpj);

    boolean existsByTenantIdAndCnpj(String tenantId, String cnpj);

    Optional<Cliente> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    List<Cliente> findByStatus(Cliente.StatusCliente status);

    @Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Cliente> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    @Query("SELECT c FROM Cliente c WHERE c.status = 'ATIVO'")
    List<Cliente> findClientesAtivos();

    List<Cliente> findByTenantIdAndStatus(String tenantId, Cliente.StatusCliente status);

    long countByStatus(Cliente.StatusCliente status);
}
