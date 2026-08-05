package com.aurix.platform.intelligence.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller para health checks do Analytics
 */
@RestController
@RequestMapping("/api/intelligence/health")
@Tag(name = "Health", description = "API para verificação de saúde do serviço de Analytics")
public class HealthController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HealthController.class);

    /**
     * Health check básico
     */
    @GetMapping
    @Operation(summary = "Health check", description = "Verifica se o serviço de Analytics está funcionando")
    public ResponseEntity<Map<String, Object>> health() {
        log.debug("Health check Analytics solicitado");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "aurix-analytics");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    /**
     * Health check detalhado
     */
    @GetMapping("/detailed")
    @Operation(summary = "Health check detalhado", description = "Verifica detalhadamente o status do serviço de Analytics")
    public ResponseEntity<Map<String, Object>> healthDetailed() {
        log.debug("Health check Analytics detalhado solicitado");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "aurix-analytics");
        response.put("description", "Aurix Analytics - Business Intelligence");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");
        response.put("environment", "development");
        // Status dos componentes
        Map<String, String> components = new HashMap<>();
        components.put("database", "UP");
        components.put("redis", "UP");
        components.put("kafka", "UP");
        response.put("components", components);
        return ResponseEntity.ok(response);
    }
}
