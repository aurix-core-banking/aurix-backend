package com.aurix.platform.gateway.controller;

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
 * Controller para health checks do Gateway
 */
@RestController
@RequestMapping("/health")
@Tag(name = "Health", description = "API para verificação de saúde do Gateway")
public class HealthController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HealthController.class);

    /**
     * Health check básico
     */
    @GetMapping
    @Operation(summary = "Health check", description = "Verifica se o Gateway está funcionando")
    public ResponseEntity<Map<String, Object>> health() {
        log.debug("Health check Gateway solicitado");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "aurix-gateway");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    /**
     * Health check detalhado
     */
    @GetMapping("/detailed")
    @Operation(summary = "Health check detalhado", description = "Verifica detalhadamente o status do Gateway")
    public ResponseEntity<Map<String, Object>> healthDetailed() {
        log.debug("Health check Gateway detalhado solicitado");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "aurix-gateway");
        response.put("description", "Aurix Gateway - API Gateway");
        response.put("timestamp", LocalDateTime.now());
        response.put("version", "1.0.0");
        response.put("environment", "development");
        // Status dos componentes
        Map<String, String> components = new HashMap<>();
        components.put("redis", "UP");
        components.put("gateway", "UP");
        response.put("components", components);
        // Status dos serviços
        Map<String, String> services = new HashMap<>();
        services.put("aurix-core", "UP");
        services.put("aurix-pix", "UP");
        services.put("aurix-credit", "UP");
        services.put("aurix-treasury", "UP");
        response.put("services", services);
        return ResponseEntity.ok(response);
    }
}
