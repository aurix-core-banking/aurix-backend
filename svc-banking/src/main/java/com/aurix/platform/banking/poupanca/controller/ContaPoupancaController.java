package com.aurix.platform.banking.poupanca.controller;

import com.aurix.platform.banking.poupanca.dto.ContaPoupancaResponse;
import com.aurix.platform.banking.poupanca.dto.CriarContaRequest;
import com.aurix.platform.banking.poupanca.service.ContaPoupancaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "Conta Poupanca", description = "Gerenciamento de contas poupanca")
public class ContaPoupancaController {

    private final ContaPoupancaService contaPoupancaService;

    public ContaPoupancaController(ContaPoupancaService contaPoupancaService) {
        this.contaPoupancaService = contaPoupancaService;
    }

    @PostMapping
    @Operation(summary = "Criar conta poupanca")
    public ResponseEntity<ContaPoupancaResponse> criarConta(@Valid @RequestBody CriarContaRequest request) {
        ContaPoupancaResponse response = contaPoupancaService.criarConta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta poupanca por ID")
    public ResponseEntity<ContaPoupancaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contaPoupancaService.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar contas poupanca de um cliente")
    public ResponseEntity<List<ContaPoupancaResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(contaPoupancaService.listarPorCliente(clienteId));
    }

    @PatchMapping("/{id}/bloquear")
    @Operation(summary = "Bloquear conta poupanca")
    public ResponseEntity<Void> bloquear(@PathVariable Long id) {
        contaPoupancaService.bloquear(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/encerrar")
    @Operation(summary = "Encerrar conta poupanca")
    public ResponseEntity<Void> encerrar(@PathVariable Long id) {
        contaPoupancaService.encerrar(id);
        return ResponseEntity.noContent().build();
    }
}
