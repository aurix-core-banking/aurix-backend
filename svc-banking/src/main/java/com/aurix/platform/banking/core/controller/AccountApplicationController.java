package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.dto.AccountApplicationDTO;
import com.aurix.platform.banking.core.entity.AccountApplication;
import com.aurix.platform.banking.core.service.AccountApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account-applications")
@Tag(name = "Account Applications", description = "Pipeline de abertura de contas")
@CrossOrigin(origins = "*")
public class AccountApplicationController {

    private final AccountApplicationService service;

    public AccountApplicationController(final AccountApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar solicitação de abertura de conta")
    public ResponseEntity<AccountApplicationDTO> criar(@Valid @RequestBody AccountApplicationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar solicitação por ID")
    public ResponseEntity<AccountApplicationDTO> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar solicitações por cliente")
    public ResponseEntity<List<AccountApplicationDTO>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar solicitações por status")
    public ResponseEntity<List<AccountApplicationDTO>> listarPorStatus(@PathVariable String status) {
        AccountApplication.AccountApplicationStatus appStatus =
            AccountApplication.AccountApplicationStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(service.listarPorStatus(appStatus));
    }

    @PostMapping("/{id}/submit")
    @Operation(summary = "Submeter solicitação para análise")
    public ResponseEntity<AccountApplicationDTO> submit(@PathVariable Long id) {
        return ResponseEntity.ok(service.submit(id));
    }

    @PostMapping("/{id}/request-documents")
    @Operation(summary = "Solicitar documentos adicionais")
    public ResponseEntity<AccountApplicationDTO> requestDocuments(@PathVariable Long id,
                                                                    @RequestParam String notes) {
        return ResponseEntity.ok(service.requestDocuments(id, notes));
    }

    @PostMapping("/{id}/review")
    @Operation(summary = "Iniciar revisão")
    public ResponseEntity<AccountApplicationDTO> startReview(@PathVariable Long id,
                                                               @RequestParam Long reviewerId) {
        return ResponseEntity.ok(service.startReview(id, reviewerId));
    }

    @PostMapping("/{id}/approve")
    @Operation(summary = "Aprovar e criar conta")
    public ResponseEntity<AccountApplicationDTO> approve(@PathVariable Long id,
                                                           @RequestParam String notes,
                                                           @RequestParam Long reviewerId) {
        return ResponseEntity.ok(service.approve(id, notes, reviewerId));
    }

    @PostMapping("/{id}/reject")
    @Operation(summary = "Rejeitar solicitação")
    public ResponseEntity<AccountApplicationDTO> reject(@PathVariable Long id,
                                                          @RequestParam String reason,
                                                          @RequestParam Long reviewerId) {
        return ResponseEntity.ok(service.reject(id, reason, reviewerId));
    }
}
