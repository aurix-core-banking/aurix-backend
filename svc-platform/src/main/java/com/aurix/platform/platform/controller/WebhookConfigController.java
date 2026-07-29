package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.entity.WebhookConfig;
import com.aurix.platform.platform.repository.WebhookLogRepository;
import com.aurix.platform.platform.service.WebhookConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/platform/webhooks/config")
@SuppressWarnings({"PMD.SimplifiedTernary"})
public class WebhookConfigController {
    private final WebhookConfigService configService;
    private final WebhookLogRepository logRepository;

    @GetMapping("/{tenantId}")
    public ResponseEntity<WebhookConfig> buscar(@PathVariable String tenantId) {
        WebhookConfig c = configService.buscarPorTenant(tenantId);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{tenantId}")
    public ResponseEntity<WebhookConfig> salvar(@PathVariable String tenantId, @RequestBody Map<String, Object> body) {
        String url = (String) body.get("url");
        @SuppressWarnings("unchecked")
        List<String> eventos = body.get("eventos") != null ? (List<String>) body.get("eventos") : null;
        Boolean ativo = body.get("ativo") != null ? (Boolean) body.get("ativo") : true;
        String secret = (String) body.get("secret");
        if (url == null || url.isBlank()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(configService.salvar(tenantId, url, eventos, ativo, secret));
    }

    @GetMapping("/{tenantId}/logs")
    public List<Map<String, Object>> logs(@PathVariable String tenantId, @RequestParam(defaultValue = "50") int limit) {
        return logRepository.findByTenantIdOrderByDataCriacaoDesc(tenantId, org.springframework.data.domain.PageRequest.of(0, Math.min(limit, 100))).stream().map(e -> Map.<String, Object>of("id", e.getId(), "evento", e.getEvento(), "status", e.getStatus().name(), "tentativas", e.getTentativas() != null ? e.getTentativas() : 0, "responseCode", e.getResponseCode() != null ? e.getResponseCode() : 0, "dataCriacao", e.getDataCriacao().toString())).collect(Collectors.toList());
    }

    @java.lang.SuppressWarnings("all")
    public WebhookConfigController(final WebhookConfigService configService, final WebhookLogRepository logRepository) {
        this.configService = configService;
        this.logRepository = logRepository;
    }
}
