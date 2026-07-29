package com.aurix.platform.platform.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/platform/health")
@Tag(name = "Health", description = "API para verificacao de saude do servico Notification")
public class HealthController {
    @GetMapping
    @Operation(summary = "Health check", description = "Verifica se o servico Notification esta funcionando")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "aurix-notification",
            "timestamp", LocalDateTime.now().toString(),
            "version", "1.0.0"
        ));
    }
}
