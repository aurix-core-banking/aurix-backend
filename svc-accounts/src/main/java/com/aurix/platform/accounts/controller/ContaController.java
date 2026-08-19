package com.aurix.platform.accounts.controller;

import com.aurix.platform.accounts.dto.ContaRequest;
import com.aurix.platform.accounts.dto.ContaResponse;
import com.aurix.platform.accounts.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contas")
@Tag(name = "Conta", description = "CRUD de contas, saldo e extrato")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping
    @Operation(summary = "Criar conta")
    public ResponseEntity<ContaResponse> criarConta(@Valid @RequestBody ContaRequest request) {
        ContaResponse response = contaService.criarConta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta por ID")
    public ResponseEntity<ContaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.buscarPorId(id));
    }

    @GetMapping("/numero/{numeroConta}")
    @Operation(summary = "Buscar conta por numero")
    public ResponseEntity<ContaResponse> buscarPorNumero(@PathVariable String numeroConta) {
        return ResponseEntity.ok(contaService.buscarPorNumero(numeroConta));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar contas por cliente")
    public ResponseEntity<List<ContaResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(contaService.listarPorCliente(clienteId));
    }

    @GetMapping
    @Operation(summary = "Listar todas as contas")
    public ResponseEntity<List<ContaResponse>> listarTodas() {
        return ResponseEntity.ok(contaService.listarTodas());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar conta")
    public ResponseEntity<ContaResponse> atualizarConta(
            @PathVariable Long id,
            @Valid @RequestBody ContaRequest request) {
        return ResponseEntity.ok(contaService.atualizarConta(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Fechar conta")
    public ResponseEntity<Void> fecharConta(@PathVariable Long id) {
        contaService.fecharConta(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/saldo")
    @Operation(summary = "Consultar saldo da conta")
    public ResponseEntity<BigDecimal> consultarSaldo(@PathVariable Long id) {
        return ResponseEntity.ok(contaService.consultarSaldo(id));
    }
}
