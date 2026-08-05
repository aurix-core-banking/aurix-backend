package com.aurix.platform.finance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/finance/health")
@Tag(name = "Health", description = "APIs de monitoramento de saúde")
public class HealthController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HealthController.class);

    @GetMapping
    @Operation(summary = "Health check", description = "Verifica a saúde do módulo")
    public ResponseEntity<Map<String, Object>> health() {
        log.debug("Health check solicitado");
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("module", "svc-finance-mgmt");
        health.put("version", "1.0.0");
        health.put("timestamp", LocalDateTime.now());
        health.put("description", "Aurix Finance Management - Gestão de Contas a Pagar e Receber");
        return ResponseEntity.ok(health);
    }

    @GetMapping("/detailed")
    @Operation(summary = "Health check detalhado", description = "Verifica a saúde detalhada do módulo")
    public ResponseEntity<Map<String, Object>> healthDetailed() {
        log.debug("Health check detalhado solicitado");
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("module", "svc-finance-mgmt");
        health.put("version", "1.0.0");
        health.put("timestamp", LocalDateTime.now());
        health.put("description", "Aurix Finance Management - Gestão de Contas a Pagar e Receber");
        Map<String, Object> system = new HashMap<>();
        system.put("java.version", System.getProperty("java.version"));
        system.put("java.vendor", System.getProperty("java.vendor"));
        system.put("os.name", System.getProperty("os.name"));
        system.put("os.version", System.getProperty("os.version"));
        health.put("system", system);
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> memory = new HashMap<>();
        memory.put("total", runtime.totalMemory());
        memory.put("free", runtime.freeMemory());
        memory.put("used", runtime.totalMemory() - runtime.freeMemory());
        memory.put("max", runtime.maxMemory());
        health.put("memory", memory);
        return ResponseEntity.ok(health);
    }

    @java.lang.SuppressWarnings("all")
    public HealthController() {
    }
}
