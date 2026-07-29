package com.aurix.platform.finance.controller;

import com.aurix.platform.finance.service.SyncService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/finance/sincronizar")
@Tag(name = "Sync", description = "Sincronizacao Core -> Finance")
public class SyncController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SyncController.class);
    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

    @PostMapping("/contas")
    @Operation(summary = "Sincronizar conta do Core")
    public ResponseEntity<Map<String, Object>> sincronizarConta(@RequestBody Map<String, Object> payload) {
        try {
            String contaId = (String) payload.get("contaId");
            String clienteId = (String) payload.get("clienteId");
            BigDecimal saldoInicial = new BigDecimal(payload.get("saldoInicial").toString());
            LocalDateTime dataCriacao = payload.get("dataCriacao") != null
                ? LocalDateTime.parse(payload.get("dataCriacao").toString().substring(0, 19))
                : LocalDateTime.now();
            var result = syncService.sincronizarConta(contaId, clienteId, saldoInicial, dataCriacao);
            return ResponseEntity.ok(Map.of(
                "id", result.getId(), "contaId", result.getContaId(),
                "status", result.getStatus().name(),
                "dataSincronizacao", result.getDataSincronizacao().toString()
            ));
        } catch (Exception e) {
            log.error("Erro ao sincronizar conta: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/contas/{contaId}")
    @Operation(summary = "Desincronizar conta (compensation)")
    public ResponseEntity<Map<String, Object>> desyncConta(@PathVariable String contaId) {
        try {
            syncService.desyncConta(contaId);
            return ResponseEntity.ok(Map.of("status", "compensated", "contaId", contaId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/transacoes")
    @Operation(summary = "Sincronizar transacao do Core")
    public ResponseEntity<Map<String, Object>> sincronizarTransacao(@RequestBody Map<String, Object> payload) {
        try {
            String transacaoId = (String) payload.get("transacaoId");
            String contaId = (String) payload.get("contaId");
            BigDecimal valor = new BigDecimal(payload.get("valor").toString());
            String tipo = (String) payload.get("tipo");
            LocalDateTime dataTransacao = payload.get("dataTransacao") != null
                ? LocalDateTime.parse(payload.get("dataTransacao").toString().substring(0, 19))
                : LocalDateTime.now();
            var result = syncService.sincronizarTransacao(transacaoId, contaId, valor, tipo, dataTransacao);
            return ResponseEntity.ok(Map.of(
                "id", result.getId(), "transacaoId", result.getTransacaoId(),
                "status", result.getStatus().name(),
                "dataSincronizacao", result.getDataSincronizacao().toString()
            ));
        } catch (Exception e) {
            log.error("Erro ao sincronizar transacao: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/transacoes/{transacaoId}")
    @Operation(summary = "Desincronizar transacao (compensation)")
    public ResponseEntity<Map<String, Object>> desyncTransacao(@PathVariable String transacaoId) {
        try {
            syncService.desyncTransacao(transacaoId);
            return ResponseEntity.ok(Map.of("status", "compensated", "transacaoId", transacaoId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("erro", e.getMessage()));
        }
    }
}
