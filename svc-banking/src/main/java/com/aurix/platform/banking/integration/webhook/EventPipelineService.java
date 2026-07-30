package com.aurix.platform.banking.integration.webhook;

import com.aurix.platform.banking.integration.webhook.WebhookEvent.WebhookEventStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class EventPipelineService {

    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventPipelineService.class);
    private static final int INITIAL_RETRY_MINUTES = 1;
    private static final int MAX_RETRY_MINUTES = 60;

    private final WebhookEventRepository repository;
    private final WebhookEndpoint webhookEndpoint;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public EventPipelineService(final WebhookEventRepository repository,
                                 final WebhookEndpoint webhookEndpoint,
                                 final RestTemplate restTemplate,
                                 final ObjectMapper objectMapper) {
        this.repository = repository;
        this.webhookEndpoint = webhookEndpoint;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public void dispatch(String eventType, String source, Object payload) {
        try {
            String payloadJson = objectMapper.writeValueAsString(payload);
            List<WebhookEndpoint.Endpoint> endpoints = webhookEndpoint.getEndpoints();
            if (endpoints == null) return;

            for (WebhookEndpoint.Endpoint ep : endpoints) {
                if (!ep.isEnabled()) continue;
                if (ep.getEventos() != null && !ep.getEventos().isEmpty()
                    && !ep.getEventos().contains(eventType)) continue;

                WebhookEvent event = new WebhookEvent();
                event.setEventType(eventType);
                event.setSource(source);
                event.setPayload(payloadJson);
                event.setTargetUrl(ep.getUrl());
                event.setTargetApiKey(ep.getApiKey());
                event.setStatus(WebhookEventStatus.PENDING);
                event.setAttempts(0);
                event.setNextRetryAt(LocalDateTime.now());
                repository.save(event);

                send(event);
            }
        } catch (Exception e) {
            log.error("Failed to dispatch event {}: {}", eventType, e.getMessage());
        }
    }

    public void send(WebhookEvent event) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("User-Agent", "Aurix-Webhook/1.0");
            if (event.getTargetApiKey() != null) {
                headers.set("Authorization", "Bearer " + event.getTargetApiKey());
            }

            HttpEntity<String> entity = new HttpEntity<>(event.getPayload(), headers);
            ResponseEntity<String> response = restTemplate.exchange(
                event.getTargetUrl(), HttpMethod.POST, entity, String.class);

            event.setStatus(WebhookEventStatus.DELIVERED);
            event.setResponseCode(response.getStatusCode().value());
            event.setResponseBody(response.getBody());
            event.setDispatchedAt(LocalDateTime.now());
            event.setLastAttemptAt(LocalDateTime.now());
            repository.save(event);
            log.info("Webhook delivered: id={}, url={}, status={}",
                event.getId(), event.getTargetUrl(), response.getStatusCode());
        } catch (Exception e) {
            event.setAttempts(event.getAttempts() + 1);
            event.setLastAttemptAt(LocalDateTime.now());
            event.setErrorMessage(e.getMessage());

            if (event.getAttempts() >= event.getMaxAttempts()) {
                event.setStatus(WebhookEventStatus.EXHAUSTED);
                log.warn("Webhook exhausted: id={}, url={}, attempts={}",
                    event.getId(), event.getTargetUrl(), event.getAttempts());
            } else {
                event.setStatus(WebhookEventStatus.FAILED);
                int delayMinutes = Math.min(
                    INITIAL_RETRY_MINUTES * (int) Math.pow(2, event.getAttempts() - 1),
                    MAX_RETRY_MINUTES);
                event.setNextRetryAt(LocalDateTime.now().plusMinutes(delayMinutes));
                log.warn("Webhook failed: id={}, url={}, attempt={}/{}, retry in {}min",
                    event.getId(), event.getTargetUrl(),
                    event.getAttempts(), event.getMaxAttempts(), delayMinutes);
            }
            repository.save(event);
        }
    }

    public int retryFailed() {
        List<WebhookEvent> failed = repository
            .findByStatusAndNextRetryAtBeforeOrderByNextRetryAtAsc(
                WebhookEventStatus.FAILED, LocalDateTime.now());
        for (WebhookEvent event : failed) {
            event.setStatus(WebhookEventStatus.PENDING);
            repository.save(event);
            send(event);
        }
        if (!failed.isEmpty()) {
            log.info("Retried {} failed webhook events", failed.size());
        }
        return failed.size();
    }

    public List<WebhookEvent> listByEventType(String eventType) {
        return repository.findByEventTypeOrderByDataCriacaoDesc(eventType);
    }

    public long countPending() {
        return repository.countByStatus(WebhookEventStatus.PENDING);
    }

    public long countFailed() {
        return repository.countByStatus(WebhookEventStatus.FAILED);
    }
}
