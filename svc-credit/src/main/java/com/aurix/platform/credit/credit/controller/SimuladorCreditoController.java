package com.aurix.platform.credit.credit.controller;

import com.aurix.platform.credit.credit.service.SimuladorCreditoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/credit/simulador")
@Tag(name = "Simulador de Credito", description = "Simular parcelas e custo total")
public class SimuladorCreditoController {
    private final SimuladorCreditoService simuladorCreditoService;

    @GetMapping
    @Operation(summary = "Simular credito")
    public ResponseEntity<SimuladorCreditoService.SimulacaoResponse> simular(@RequestParam BigDecimal valor, @RequestParam Integer prazoMeses, @RequestParam BigDecimal taxaJurosAoMes) {
        return ResponseEntity.ok(simuladorCreditoService.simular(valor, prazoMeses, taxaJurosAoMes));
    }

    @java.lang.SuppressWarnings("all")
    public SimuladorCreditoController(final SimuladorCreditoService simuladorCreditoService) {
        this.simuladorCreditoService = simuladorCreditoService;
    }
}
