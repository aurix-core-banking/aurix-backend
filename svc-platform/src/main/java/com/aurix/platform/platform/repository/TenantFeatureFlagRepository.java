package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.TenantFeatureFlag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TenantFeatureFlagRepository extends JpaRepository<TenantFeatureFlag, Long> {

    List<TenantFeatureFlag> findByTenantId(String tenantId);

    Optional<TenantFeatureFlag> findByTenantIdAndFeatureKey(String tenantId, String featureKey);

    boolean existsByTenantIdAndFeatureKey(String tenantId, String featureKey);
}
