package com.aurix.platform.credit.financiamento.controller;

import com.aurix.platform.credit.financiamento.dto.request.SimulacaoRequest;
import com.aurix.platform.credit.financiamento.dto.response.SimulacaoResponse;
import com.aurix.platform.credit.financiamento.service.SimulacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/financiamento/simulacoes")
@Tag(name = "Simulação")
public class SimulacaoController {

    private final SimulacaoService simulacaoService;

    public SimulacaoController(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @PostMapping
    public ResponseEntity<SimulacaoResponse> simular(@Valid @RequestBody SimulacaoRequest request) {
        var response = simulacaoService.simular(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimulacaoResponse> obterSimulacao(@PathVariable Long id) {
        return ResponseEntity.ok(simulacaoService.buscarPorId(id));
    }
}
