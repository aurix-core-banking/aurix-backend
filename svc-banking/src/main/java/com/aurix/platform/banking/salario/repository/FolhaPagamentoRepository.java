package com.aurix.platform.banking.salario.repository;

import com.aurix.platform.banking.salario.entity.FolhaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaPagamento, Long> {
    List<FolhaPagamento> findByTenantId(String tenantId);
    List<FolhaPagamento> findByTenantIdAndStatus(
        String tenantId, FolhaPagamento.StatusFolha status);
    List<FolhaPagamento> findByTenantIdAndEmpresaId(
        String tenantId, Long empresaId);
    Optional<FolhaPagamento> findByTenantIdAndId(String tenantId, Long id);
}
