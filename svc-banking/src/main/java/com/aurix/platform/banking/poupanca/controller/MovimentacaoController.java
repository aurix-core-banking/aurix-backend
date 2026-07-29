package com.aurix.platform.banking.poupanca.controller;

import com.aurix.platform.banking.poupanca.dto.DepositoRequest;
import com.aurix.platform.banking.poupanca.dto.ExtratoResponse;
import com.aurix.platform.banking.poupanca.dto.SaqueRequest;
import com.aurix.platform.banking.poupanca.service.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/poupanca/movimentacoes")
@Tag(name = "Movimentacoes Poupanca", description = "Depositos, saques e extratos")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    public MovimentacaoController(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }

    @PostMapping("/deposito")
    @Operation(summary = "Depositar em conta poupanca")
    public ResponseEntity<Void> depositar(@Valid @RequestBody DepositoRequest request) {
        movimentacaoService.depositar(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/saque")
    @Operation(summary = "Sacar de conta poupanca")
    public ResponseEntity<Void> sacar(@Valid @RequestBody SaqueRequest request) {
        movimentacaoService.sacar(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/conta/{contaId}")
    @Operation(summary = "Extrato completo da conta poupanca")
    public ResponseEntity<ExtratoResponse> extratoCompleto(@PathVariable Long contaId) {
        LocalDateTime fim = LocalDateTime.now();
        LocalDateTime inicio = fim.minusMonths(12);
        return ResponseEntity.ok(movimentacaoService.gerarExtrato(contaId, inicio, fim));
    }

    @GetMapping("/conta/{contaId}/periodo")
    @Operation(summary = "Extrato por periodo")
    public ResponseEntity<ExtratoResponse> extratoPorPeriodo(
            @PathVariable Long contaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(movimentacaoService.gerarExtrato(contaId, inicio, fim));
    }
}
