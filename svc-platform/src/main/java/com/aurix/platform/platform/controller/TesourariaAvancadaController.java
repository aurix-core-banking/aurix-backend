package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.service.TesourariaAvancadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/platform/tesouraria-avancada")
@Tag(name = "Tesouraria Avançada", description = "Liquidez, VaR e stress testing")
public class TesourariaAvancadaController {
    private final TesourariaAvancadaService service;

    @GetMapping("/liquidez")
    @Operation(summary = "Posição de liquidez")
    public ResponseEntity<Map<String, BigDecimal>> liquidez() {
        return ResponseEntity.ok(service.posicaoLiquidez());
    }

    @GetMapping("/var")
    @Operation(summary = "Value at Risk")
    public ResponseEntity<Map<String, BigDecimal>> var(@RequestParam(defaultValue = "1") int dias, @RequestParam(defaultValue = "95") String nivelConfianca) {
        return ResponseEntity.ok(service.calcularVar(dias, nivelConfianca));
    }

    @PostMapping("/stress")
    @Operation(summary = "Executar cenário de stress")
    public ResponseEntity<Map<String, Object>> stress(@RequestParam String cenario) {
        return ResponseEntity.ok(service.executarStressCenario(cenario));
    }

    @java.lang.SuppressWarnings("all")
    public TesourariaAvancadaController(final TesourariaAvancadaService service) {
        this.service = service;
    }
}
