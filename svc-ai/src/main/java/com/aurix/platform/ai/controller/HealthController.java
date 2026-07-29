package com.aurix.platform.ai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/health")
@Tag(name = "Health", description = "API para verificação de saúde do serviço de AI")
public class HealthController {

    @GetMapping
    @Operation(summary = "Health check", description = "Verifica se o serviço de AI está funcionando")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "aurix-ai",
            "timestamp", LocalDateTime.now(),
            "version", "1.0.0"
        ));
    }
}
