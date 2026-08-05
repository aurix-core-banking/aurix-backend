package com.aurix.platform.intelligence.controller;

import com.aurix.platform.intelligence.service.CreditScoreService;
import com.aurix.platform.intelligence.service.FraudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/intelligence/ml")
@Tag(name = "ML", description = "Detecção de fraude e score de crédito")
public class MlController {
    private final FraudService fraudService;
    private final CreditScoreService creditScoreService;

    public MlController(FraudService fraudService, CreditScoreService creditScoreService) {
        this.fraudService = fraudService;
        this.creditScoreService = creditScoreService;
    }

    @PostMapping("/fraude/avaliar")
    @Operation(summary = "Avaliar transação para fraude")
    public ResponseEntity<Map<String, Object>> avaliarFraude(@RequestBody Map<String, Object> transacao) {
        return ResponseEntity.ok(fraudService.avaliarFraude(transacao));
    }

    @GetMapping("/credito/score")
    @Operation(summary = "Score de crédito do cliente")
    public ResponseEntity<Map<String, Object>> scoreCredito(@RequestParam String clienteId) {
        return ResponseEntity.ok(creditScoreService.obterScore(clienteId));
    }
}
