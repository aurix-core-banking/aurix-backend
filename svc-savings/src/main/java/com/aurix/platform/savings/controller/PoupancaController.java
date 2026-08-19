package com.aurix.platform.savings.controller;

import com.aurix.platform.savings.dto.ContaPoupancaRequest;
import com.aurix.platform.savings.dto.ContaPoupancaResponse;
import com.aurix.platform.savings.dto.MovimentacaoRequest;
import com.aurix.platform.savings.dto.MovimentacaoResponse;
import com.aurix.platform.savings.service.PoupancaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/poupanca/contas")
@Tag(name = "Conta Poupanca", description = "Criar poupanca, render e resgatar")
public class PoupancaController {

    private final PoupancaService poupancaService;

    public PoupancaController(PoupancaService poupancaService) {
        this.poupancaService = poupancaService;
    }

    @PostMapping
    @Operation(summary = "Criar conta poupanca")
    public ResponseEntity<ContaPoupancaResponse> criarConta(
            @Valid @RequestBody ContaPoupancaRequest request) {
        ContaPoupancaResponse response = poupancaService.criarConta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta poupanca por ID")
    public ResponseEntity<ContaPoupancaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(poupancaService.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar contas poupanca por cliente")
    public ResponseEntity<List<ContaPoupancaResponse>> listarPorCliente(
            @PathVariable Long clienteId) {
        return ResponseEntity.ok(poupancaService.listarPorCliente(clienteId));
    }

    @PatchMapping("/{id}/bloquear")
    @Operation(summary = "Bloquear conta poupanca")
    public ResponseEntity<Void> bloquear(@PathVariable Long id) {
        poupancaService.bloquear(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/encerrar")
    @Operation(summary = "Encerrar conta poupanca")
    public ResponseEntity<Void> encerrar(@PathVariable Long id) {
        poupancaService.encerrar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/deposito")
    @Operation(summary = "Depositar na poupanca")
    public ResponseEntity<MovimentacaoResponse> depositar(
            @PathVariable Long id,
            @Valid @RequestBody MovimentacaoRequest request) {
        MovimentacaoResponse response = poupancaService.depositar(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/resgate")
    @Operation(summary = "Resgatar da poupanca")
    public ResponseEntity<MovimentacaoResponse> resgatar(
            @PathVariable Long id,
            @Valid @RequestBody MovimentacaoRequest request) {
        MovimentacaoResponse response = poupancaService.resgatar(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{id}/render")
    @Operation(summary = "Render poupanca (correcao monetaria)")
    public ResponseEntity<Void> render(@PathVariable Long id) {
        poupancaService.render(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/saldo")
    @Operation(summary = "Consultar saldo da poupanca")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable Long id) {
        return ResponseEntity.ok(poupancaService.consultarSaldo(id));
    }
}
