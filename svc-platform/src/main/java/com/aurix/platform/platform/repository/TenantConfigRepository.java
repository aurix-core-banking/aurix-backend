package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.TenantConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TenantConfigRepository extends JpaRepository<TenantConfig, Long> {

    Optional<TenantConfig> findByTenantId(String tenantId);

    boolean existsByTenantId(String tenantId);
}
