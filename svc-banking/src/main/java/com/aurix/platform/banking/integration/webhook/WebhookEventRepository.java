package com.aurix.platform.banking.integration.webhook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WebhookEventRepository extends JpaRepository<WebhookEvent, Long> {

    List<WebhookEvent> findByStatusOrderByDataCriacaoAsc(WebhookEvent.WebhookEventStatus status);

    List<WebhookEvent> findByStatusAndNextRetryAtBeforeOrderByNextRetryAtAsc(
        WebhookEvent.WebhookEventStatus status, LocalDateTime now);

    List<WebhookEvent> findByEventTypeOrderByDataCriacaoDesc(String eventType);

    long countByStatus(WebhookEvent.WebhookEventStatus status);
}
