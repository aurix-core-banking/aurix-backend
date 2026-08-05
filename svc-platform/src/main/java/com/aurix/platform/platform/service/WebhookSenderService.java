package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.WebhookConfig;
import com.aurix.platform.platform.entity.WebhookLog;
import com.aurix.platform.platform.repository.WebhookConfigRepository;
import com.aurix.platform.platform.repository.WebhookLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings({"PMD.UnusedFormalParameter"})
public class WebhookSenderService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WebhookSenderService.class);
    private final WebhookConfigRepository configRepository;
    private final WebhookLogRepository logRepository;
    private final WebClient.Builder webClientBuilder;
    @Value("${aurix.webhooks.max-retries:5}")
    private int maxRetries;
    @Value("${aurix.webhooks.retry-interval-minutes:5}")
    private int retryIntervalMinutes;

    @Transactional
    public void dispatch(String tenantId, String evento, Object payload) {
        WebhookConfig config = configRepository.findByTenantId(tenantId).orElse(null);
        if (config == null || !Boolean.TRUE.equals(config.getAtivo())) return;
        if (config.getEventos() != null && !config.getEventos().isEmpty() && !config.getEventos().contains(evento)) return;
        String payloadStr = payload instanceof String ? (String) payload : toJsonMap(payload);
        WebhookLog entry = WebhookLog.builder().tenantId(tenantId).evento(evento).payload(payloadStr).status(WebhookLog.StatusEnvio.PENDENTE).tentativas(0).proximaTentativa(LocalDateTime.now()).build();
        logRepository.save(entry);
        enviar(entry.getId());
    }

    void enviar(Long logId) {
        WebhookLog entry = logRepository.findById(logId).orElse(null);
        if (entry == null || entry.getStatus() != WebhookLog.StatusEnvio.PENDENTE) return;
        WebhookConfig config = configRepository.findByTenantId(entry.getTenantId()).orElse(null);
        if (config == null) return;
        int tentativas = entry.getTentativas() != null ? entry.getTentativas() : 0;
        try {
            String body = buildBody(entry.getEvento(), entry.getPayload(), config.getSecret());
            var response = webClientBuilder.build().post().uri(config.getUrl()).contentType(MediaType.APPLICATION_JSON).bodyValue(body).retrieve().toBodilessEntity().block();
            int code = response != null && response.getStatusCode().is2xxSuccessful() ? 200 : response != null ? response.getStatusCode().value() : 0;
            entry.setStatus(code >= 200 && code < 300 ? WebhookLog.StatusEnvio.ENVIADO : WebhookLog.StatusEnvio.FALHA);
            entry.setTentativas(tentativas + 1);
            entry.setResponseCode(code);
            if (entry.getStatus() == WebhookLog.StatusEnvio.FALHA && tentativas + 1 < maxRetries) {
                entry.setStatus(WebhookLog.StatusEnvio.PENDENTE);
                entry.setProximaTentativa(LocalDateTime.now().plusMinutes(retryIntervalMinutes));
            }
            logRepository.save(entry);
        } catch (Exception e) {
            log.warn("Webhook falhou tenant={} evento={}: {}", entry.getTenantId(), entry.getEvento(), e.getMessage());
            entry.setTentativas(tentativas + 1);
            entry.setResponseBody(e.getMessage() != null ? e.getMessage().substring(0, Math.min(2000, e.getMessage().length())) : null);
            if (tentativas + 1 >= maxRetries) {
                entry.setStatus(WebhookLog.StatusEnvio.EXCEDIDO);
            } else {
                entry.setStatus(WebhookLog.StatusEnvio.PENDENTE);
                entry.setProximaTentativa(LocalDateTime.now().plusMinutes(retryIntervalMinutes));
            }
            logRepository.save(entry);
        }
    }

    @Transactional
    public void retryPendentes() {
        List<WebhookLog> pendentes = logRepository.findByStatusAndProximaTentativaBefore(WebhookLog.StatusEnvio.PENDENTE, LocalDateTime.now());
        for (WebhookLog e : pendentes) {
            enviar(e.getId());
        }
    }

    private String buildBody(String evento, String payload, String secret) {
        String p = (payload != null && payload.startsWith("{")) ? payload : "{}";
        return "{\"evento\":\"" + evento + "\",\"payload\":" + p + "}";
    }

    private String toJsonMap(Object payload) {
        if (payload == null) return "{}";
        if (payload instanceof Map) {
            try {
                return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(payload);
            } catch (Exception e) {
                return "{}";
            }
        }
        return "{\"value\":\"" + payload.toString().replace("\"", "\\\"") + "\"}";
    }

    @java.lang.SuppressWarnings("all")
    public WebhookSenderService(final WebhookConfigRepository configRepository, final WebhookLogRepository logRepository, final WebClient.Builder webClientBuilder) {
        this.configRepository = configRepository;
        this.logRepository = logRepository;
        this.webClientBuilder = webClientBuilder;
    }
}
