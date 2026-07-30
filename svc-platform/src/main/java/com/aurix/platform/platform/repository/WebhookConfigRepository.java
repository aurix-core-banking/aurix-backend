package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.WebhookConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WebhookConfigRepository extends JpaRepository<WebhookConfig, Long> {

    Optional<WebhookConfig> findByTenantId(String tenantId);
}
