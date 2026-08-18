package com.aurix.platform.banking.ted.repository;

import com.aurix.platform.banking.ted.entity.TransferenciaTed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransferenciaTedRepository extends JpaRepository<TransferenciaTed, Long> {
    List<TransferenciaTed> findByContaOrigemId(Long contaOrigemId);
    List<TransferenciaTed> findByContaOrigemIdAndStatus(Long contaOrigemId, TransferenciaTed.StatusTed status);
    Optional<TransferenciaTed> findByTenantIdAndId(String tenantId, Long id);
    List<TransferenciaTed> findByTenantId(String tenantId);
    List<TransferenciaTed> findByTenantIdAndContaOrigemId(String tenantId, Long contaOrigemId);
}
