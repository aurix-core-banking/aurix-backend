package com.aurix.platform.payments.dda.controller;

import com.aurix.platform.payments.dda.service.DdaService;
import com.aurix.platform.shared.dto.DdaAutorizacaoDTO;
import com.aurix.platform.shared.entity.DdaDebito;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller para gestão de DDA — Débito Direto Autorizado.
 */
@RestController
@RequestMapping("/api/dda")
@Tag(name = "DDA — Débito Direto Autorizado", description = "API para autorizações, revogação e débitos agendados DDA do Aurix")
public class DdaController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DdaController.class);
    private final DdaService ddaService;

    /**
     * Cria uma nova autorização DDA.
     */
    @PostMapping("/autorizacoes")
    @Operation(summary = "Criar autorização DDA", description = "Cria uma nova autorização de débito direto com consentimento do titular")
    public ResponseEntity<DdaAutorizacaoDTO> criarAutorizacao(@Valid @RequestBody DdaAutorizacaoDTO dto) {
        log.info("Recebida solicitação para criar autorização DDA para conta: {}", dto.getContaDebitadaId());
        DdaAutorizacaoDTO autorizacaoCriada = ddaService.criarAutorizacao(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorizacaoCriada);
    }

    /**
     * Revoga uma autorização DDA.
     */
    @DeleteMapping("/autorizacoes/{id}")
    @Operation(summary = "Revogar autorização DDA", description = "Revoga uma autorização de débito direto existente")
    public ResponseEntity<Void> revogarAutorizacao(
            @Parameter(description = "ID da autorização") @PathVariable Long id) {
        log.info("Recebida solicitação para revogar autorização DDA ID: {}", id);
        ddaService.revogarAutorizacao(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista débitos agendados para uma conta.
     */
    @GetMapping("/debitos")
    @Operation(summary = "Listar débitos DDA agendados", description = "Lista débitos diretos agendados por conta")
    public ResponseEntity<List<DdaDebito>> listarDebitosAgendados(
            @Parameter(description = "ID da conta debitada") @RequestParam Long contaId) {
        log.info("Recebida solicitação para listar débitos DDA da conta: {}", contaId);
        List<DdaDebito> debitos = ddaService.listarDebitosAgendados(contaId);
        return ResponseEntity.ok(debitos);
    }

    /**
     * Busca autorização por ID.
     */
    @GetMapping("/autorizacoes/{id}")
    @Operation(summary = "Buscar autorização DDA", description = "Busca uma autorização DDA pelo ID")
    public ResponseEntity<DdaAutorizacaoDTO> buscarAutorizacao(
            @Parameter(description = "ID da autorização") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar autorização DDA ID: {}", id);
        DdaAutorizacaoDTO autorizacao = ddaService.buscarAutorizacaoPorId(id);
        return ResponseEntity.ok(autorizacao);
    }

    /**
     * Lista autorizações por conta.
     */
    @GetMapping("/autorizacoes/conta/{contaId}")
    @Operation(summary = "Listar autorizações por conta", description = "Lista todas as autorizações DDA de uma conta")
    public ResponseEntity<List<DdaAutorizacaoDTO>> listarAutorizacoesPorConta(
            @Parameter(description = "ID da conta") @PathVariable Long contaId) {
        log.info("Recebida solicitação para listar autorizações DDA da conta: {}", contaId);
        List<DdaAutorizacaoDTO> autorizacoes = ddaService.listarAutorizacoesPorConta(contaId);
        return ResponseEntity.ok(autorizacoes);
    }

    @java.lang.SuppressWarnings("all")
    public DdaController(final DdaService ddaService) {
        this.ddaService = ddaService;
    }
}
