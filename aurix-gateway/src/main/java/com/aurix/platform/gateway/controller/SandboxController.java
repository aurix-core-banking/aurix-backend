package com.aurix.platform.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/gateway/sandbox")
@Tag(name = "Sandbox", description = "Ambiente de testes para API Banking")
public class SandboxController {

    @Value("${aurix.gateway.sandbox.enabled:true}")
    private boolean sandboxEnabled;

    @Value("${server.port:8080}")
    private String port;

    @GetMapping
    @Operation(summary = "Status e configuração do Sandbox")
    public ResponseEntity<Map<String, Object>> sandbox() {
        return ResponseEntity.ok(Map.of(
            "sandbox", true,
            "enabled", sandboxEnabled,
            "baseUrl", "http://localhost:" + port,
            "documentacao", "/swagger-ui.html",
            "headerApiKey", "X-API-Key"
        ));
    }
}
