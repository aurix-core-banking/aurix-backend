package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.settlement.service.BatchJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/batch")
@Tag(name = "Batch Job", description = "APIs para trigger manual e monitoramento de jobs batch")
public class BatchJobController {

    private final BatchJobService batchJobService;

    public BatchJobController(BatchJobService batchJobService) {
        this.batchJobService = batchJobService;
    }

    @PostMapping("/reprocessar-pendentes")
    @Operation(summary = "Reprocessar liquidações pendentes", description = "Trigger manual para processar todas as liquidações pendentes")
    public ResponseEntity<Map<String, Object>> reprocessarPendentes() {
        int processados = batchJobService.reprocessarPendentes();
        return ResponseEntity.ok(Map.of(
            "acao", "REPROCESSAR_PENDENTES",
            "processados", processados
        ));
    }

    @PostMapping("/reprocessar-falhas")
    @Operation(summary = "Reprocessar liquidações com falha", description = "Trigger manual para reprocessar liquidações com status FALHA")
    public ResponseEntity<Map<String, Object>> reprocessarFalhas() {
        int processados = batchJobService.reprocessarFalhas();
        return ResponseEntity.ok(Map.of(
            "acao", "REPROCESSAR_FALHAS",
            "processados", processados
        ));
    }

    @GetMapping("/status")
    @Operation(summary = "Status dos jobs", description = "Retorna contagem de liquidações pendentes e com falha")
    public ResponseEntity<Map<String, Object>> status() {
        return ResponseEntity.ok(Map.of(
            "pendentes", batchJobService.contarPendentes(),
            "falhas", batchJobService.contarFalhas()
        ));
    }
}
