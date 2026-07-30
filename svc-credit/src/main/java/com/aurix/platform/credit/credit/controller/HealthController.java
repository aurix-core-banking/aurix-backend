package com.aurix.platform.credit.credit.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/credit/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "svc-credit",
            "timestamp", LocalDateTime.now().toString()
        ));
    }

    @GetMapping("/detailed")
    public ResponseEntity<Map<String, Object>> healthDetailed() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "svc-credit",
            "modules", new String[]{"credit", "consignado", "financiamento", "guarantee"},
            "timestamp", LocalDateTime.now().toString()
        ));
    }
}
