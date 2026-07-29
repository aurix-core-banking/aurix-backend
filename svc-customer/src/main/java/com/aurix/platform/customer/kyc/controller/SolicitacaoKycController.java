package com.aurix.platform.customer.kyc.controller;

import com.aurix.platform.customer.kyc.entity.DocumentoKYC;
import com.aurix.platform.customer.kyc.entity.ScoreKYC;
import com.aurix.platform.customer.kyc.entity.SolicitacaoKYC;
import com.aurix.platform.customer.kyc.service.SolicitacaoKycService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/solicitacoes")
@Tag(name = "KYC", description = "Validacao documental e compliance")
public class SolicitacaoKycController {
    private final SolicitacaoKycService service;

    public SolicitacaoKycController(SolicitacaoKycService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Iniciar KYC para cliente")
    public ResponseEntity<SolicitacaoKYC> criar(@RequestParam Long clienteId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criarSolicitacao(clienteId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Status da solicitacao")
    public ResponseEntity<SolicitacaoKYC> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Historico KYC do cliente")
    public ResponseEntity<List<SolicitacaoKYC>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.listarPorCliente(clienteId));
    }

    @PostMapping("/documentos")
    @Operation(summary = "Anexar documento")
    public ResponseEntity<DocumentoKYC> anexarDocumento(@RequestParam Long solicitacaoId,
                                                         @Valid @RequestBody DocumentoKYC documento) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.anexarDocumento(solicitacaoId, documento));
    }

    @PostMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar KYC")
    public ResponseEntity<SolicitacaoKYC> aprovar(@PathVariable Long id) {
        return ResponseEntity.ok(service.aprovar(id));
    }

    @PostMapping("/{id}/rejeitar")
    @Operation(summary = "Rejeitar KYC")
    public ResponseEntity<SolicitacaoKYC> rejeitar(@PathVariable Long id, @RequestParam String motivo) {
        return ResponseEntity.ok(service.rejeitar(id, motivo));
    }

    @GetMapping("/score/{clienteId}")
    @Operation(summary = "Consultar score KYC")
    public ResponseEntity<ScoreKYC> consultarScore(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.consultarScore(clienteId));
    }
}
