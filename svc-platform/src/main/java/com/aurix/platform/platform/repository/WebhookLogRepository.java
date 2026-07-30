package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.WebhookLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface WebhookLogRepository extends JpaRepository<WebhookLog, Long> {

    List<WebhookLog> findByStatusAndProximaTentativaBefore(WebhookLog.StatusEnvio status, LocalDateTime until);

    List<WebhookLog> findByTenantIdOrderByDataCriacaoDesc(String tenantId, org.springframework.data.domain.Pageable pageable);
}
