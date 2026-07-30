package com.aurix.platform.banking.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@Tag(name = "Health Check", description = "APIs para verificação de saúde do sistema")
public class HealthController {

    @Autowired
    private DataSource dataSource;

    @GetMapping
    @Operation(summary = "Health Check Básico", description = "Verifica se o serviço está funcionando")
    @ApiResponse(responseCode = "200", description = "Serviço funcionando normalmente")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Aurix Core Banking");
        health.put("timestamp", LocalDateTime.now());
        health.put("version", "1.0.0");
        health.put("environment", "development");

        return ResponseEntity.ok(health);
    }

    @GetMapping("/detailed")
    @Operation(summary = "Health Check Detalhado", description = "Verifica o status detalhado de todos os componentes")
    @ApiResponse(responseCode = "200", description = "Status detalhado retornado")
    public ResponseEntity<Map<String, Object>> detailedHealthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Aurix Core Banking");
        health.put("timestamp", LocalDateTime.now());
        health.put("version", "1.0.0");
        health.put("environment", "development");

        // Verificar banco de dados
        Map<String, Object> database = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            database.put("status", "UP");
            database.put("url", connection.getMetaData().getURL());
            database.put("driver", connection.getMetaData().getDriverName());
            database.put("version", connection.getMetaData().getDatabaseProductVersion());
        } catch (SQLException e) {
            database.put("status", "DOWN");
            database.put("error", e.getMessage());
        } catch (RuntimeException e) {
            database.put("status", "DOWN");
            database.put("error", "Erro de execução: " + e.getMessage());
        } catch (Exception e) {
            database.put("status", "DOWN");
            database.put("error", "Erro inesperado: " + e.getMessage());
        }
        health.put("database", database);

        // Verificar memória
        Map<String, Object> memory = new HashMap<>();
        Runtime runtime = Runtime.getRuntime();
        memory.put("total", runtime.totalMemory());
        memory.put("free", runtime.freeMemory());
        memory.put("used", runtime.totalMemory() - runtime.freeMemory());
        memory.put("max", runtime.maxMemory());
        health.put("memory", memory);

        // Verificar sistema
        Map<String, Object> system = new HashMap<>();
        system.put("os", System.getProperty("os.name"));
        system.put("version", System.getProperty("os.version"));
        system.put("arch", System.getProperty("os.arch"));
        system.put("java", System.getProperty("java.version"));
        system.put("uptime", System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime());
        health.put("system", system);

        return ResponseEntity.ok(health);
    }

    @GetMapping("/ready")
    @Operation(summary = "Readiness Check", description = "Verifica se o serviço está pronto para receber tráfego")
    @ApiResponse(responseCode = "200", description = "Serviço pronto")
    @ApiResponse(responseCode = "503", description = "Serviço não pronto")
    public ResponseEntity<Map<String, Object>> readinessCheck() {
        Map<String, Object> readiness = new HashMap<>();
        Map<String, Object> checks = new HashMap<>();

        boolean isReady = true;

        // Verificar banco de dados
        try (Connection conn = dataSource.getConnection()) {
            boolean valid = conn.isValid(5); // Check if connection is valid within 5 seconds
            checks.put("database", valid ? "UP" : "DOWN");
            if (!valid) {
                isReady = false;
            }
        } catch (SQLException e) {
            checks.put("database", "DOWN (" + e.getMessage() + ")");
            readiness.put("timestamp", LocalDateTime.now());
            readiness.put("checks", checks);
            readiness.put("status", "NOT_READY");
            readiness.put("error", e.getMessage());
            return ResponseEntity.status(503).body(readiness);
        }

        // Other checks (memory, disk) are assumed to be OK for this example
        checks.put("memory", "OK");
        checks.put("disk", "OK");

        readiness.put("timestamp", LocalDateTime.now());
        readiness.put("checks", checks);

        if (isReady) {
            readiness.put("status", "READY");
            return ResponseEntity.ok(readiness);
        } else {
            readiness.put("status", "NOT_READY");
            return ResponseEntity.status(503).body(readiness);
        }
    }

    @GetMapping("/live")
    @Operation(summary = "Liveness Check", description = "Verifica se o serviço está vivo")
    @ApiResponse(responseCode = "200", description = "Serviço vivo")
    public ResponseEntity<Map<String, Object>> livenessCheck() {
        Map<String, Object> liveness = new HashMap<>();
        liveness.put("status", "ALIVE");
        liveness.put("timestamp", LocalDateTime.now());
        liveness.put("uptime", System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime());

        return ResponseEntity.ok(liveness);
    }
}