package com.aurix.platform.transactions.controller;

import com.aurix.platform.transactions.dto.TransacaoRequest;
import com.aurix.platform.transactions.dto.TransacaoResponse;
import com.aurix.platform.transactions.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transacoes")
@Tag(name = "Transacao", description = "Criar, processar e cancelar transacoes financeiras")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    @Operation(summary = "Criar transacao")
    public ResponseEntity<TransacaoResponse> criar(@Valid @RequestBody TransacaoRequest request) {
        TransacaoResponse response = transacaoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transacao por ID")
    public ResponseEntity<TransacaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(transacaoService.buscarPorId(id));
    }

    @GetMapping("/codigo/{codigoTransacao}")
    @Operation(summary = "Buscar transacao por codigo")
    public ResponseEntity<TransacaoResponse> buscarPorCodigo(@PathVariable String codigoTransacao) {
        return ResponseEntity.ok(transacaoService.buscarPorCodigo(codigoTransacao));
    }

    @GetMapping("/conta/{contaId}")
    @Operation(summary = "Listar transacoes por conta")
    public ResponseEntity<List<TransacaoResponse>> listarPorConta(@PathVariable Long contaId) {
        return ResponseEntity.ok(transacaoService.listarPorConta(contaId));
    }

    @GetMapping("/conta/{contaId}/paginado")
    @Operation(summary = "Listar transacoes por conta (paginado)")
    public ResponseEntity<Page<TransacaoResponse>> listarPorContaPaginado(
            @PathVariable Long contaId, Pageable pageable) {
        return ResponseEntity.ok(transacaoService.listarPorContaPaginado(contaId, pageable));
    }

    @GetMapping("/pendentes")
    @Operation(summary = "Listar transacoes pendentes")
    public ResponseEntity<Page<TransacaoResponse>> listarPendentes(Pageable pageable) {
        return ResponseEntity.ok(transacaoService.listarPendentes(pageable));
    }

    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar transacao")
    public ResponseEntity<TransacaoResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(transacaoService.cancelar(id));
    }

    @PutMapping("/{id}/processar")
    @Operation(summary = "Processar transacao")
    public ResponseEntity<TransacaoResponse> processar(@PathVariable Long id) {
        return ResponseEntity.ok(transacaoService.processar(id));
    }
}
