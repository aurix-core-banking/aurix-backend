package com.aurix.platform.banking.core.controller;

import com.aurix.platform.banking.core.entity.ConciliacaoBancaria;
import com.aurix.platform.banking.core.entity.Reconciliacao;
import com.aurix.platform.banking.core.service.ReconciliationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reconciliation")
@Tag(name = "Reconciliation", description = "Conciliação bancária e reconciliação de liquidações")
@CrossOrigin(origins = "*")
public class ReconciliationController {

    private final ReconciliationService service;

    public ReconciliationController(final ReconciliationService service) {
        this.service = service;
    }

    @PostMapping("/conciliacao")
    @Operation(summary = "Criar conciliação bancária")
    public ResponseEntity<ConciliacaoBancaria> criarConciliacao(@RequestParam Long contaId,
                                                                 @RequestParam String tipo,
                                                                 @RequestParam(required = false) String extratoRef) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.criarConciliacao(contaId, tipo, extratoRef));
    }

    @PostMapping("/conciliacao/{id}/processar")
    @Operation(summary = "Processar conciliação")
    public ResponseEntity<ConciliacaoBancaria> processarConciliacao(@PathVariable Long id) {
        return ResponseEntity.ok(service.processarConciliacao(id));
    }

    @GetMapping("/conciliacao/pendentes")
    @Operation(summary = "Listar conciliações pendentes")
    public ResponseEntity<List<ConciliacaoBancaria>> listarPendentes() {
        return ResponseEntity.ok(service.listarConciliacoesPendentes());
    }

    @GetMapping("/conciliacao/divergencias")
    @Operation(summary = "Listar conciliações com divergência")
    public ResponseEntity<List<ConciliacaoBancaria>> listarDivergencias() {
        return ResponseEntity.ok(service.listarConciliacoesComDivergencia());
    }

    @GetMapping("/conciliacao/{id}")
    @Operation(summary = "Buscar conciliação por ID")
    public ResponseEntity<ConciliacaoBancaria> buscarConciliacao(@PathVariable Long id) {
        return service.buscarConciliacaoPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/reconciliacao")
    @Operation(summary = "Criar reconciliação geral")
    public ResponseEntity<Reconciliacao> criarReconciliacao(@RequestParam String tipo) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(service.criarReconciliacaoGeral(tipo));
    }

    @PostMapping("/reconciliacao/{id}/processar")
    @Operation(summary = "Processar reconciliação geral")
    public ResponseEntity<Reconciliacao> processarReconciliacao(@PathVariable Long id) {
        return ResponseEntity.ok(service.processarReconciliacaoGeral(id));
    }

    @GetMapping("/reconciliacao/{id}")
    @Operation(summary = "Buscar reconciliação por ID")
    public ResponseEntity<Reconciliacao> buscarReconciliacao(@PathVariable Long id) {
        return service.buscarReconciliacaoPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
