package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.ParceiroCustodia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParceiroCustodiaRepository extends JpaRepository<ParceiroCustodia, Long> {

    Optional<ParceiroCustodia> findByTenantIdAndClientId(String tenantId, String clientId);
}
