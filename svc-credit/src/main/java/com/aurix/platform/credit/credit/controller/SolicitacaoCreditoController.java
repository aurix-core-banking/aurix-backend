package com.aurix.platform.credit.credit.controller;

import com.aurix.platform.credit.credit.service.SolicitacaoCreditoService;
import com.aurix.platform.shared.dto.SolicitacaoCreditoDTO;
import com.aurix.platform.shared.entity.SolicitacaoCredito;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Controller para gestão de solicitações de crédito
 */
@RestController
@RequestMapping("/api/credit/solicitacoes")
@Tag(name = "Solicitações de Crédito", description = "API para gestão de solicitações de crédito do Aurix")
public class SolicitacaoCreditoController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SolicitacaoCreditoController.class);
    private final SolicitacaoCreditoService solicitacaoCreditoService;

    /**
     * Cria uma nova solicitação de crédito
     */
    @PostMapping
    @Operation(summary = "Criar solicitação de crédito", description = "Cria uma nova solicitação de crédito")
    public ResponseEntity<SolicitacaoCreditoDTO> criarSolicitacaoCredito(@Valid @RequestBody SolicitacaoCreditoDTO solicitacaoCreditoDTO) {
        log.info("Recebida solicitação para criar solicitação de crédito para cliente ID: {}", solicitacaoCreditoDTO.getClienteId());
        SolicitacaoCreditoDTO solicitacaoCriada = solicitacaoCreditoService.criarSolicitacaoCredito(solicitacaoCreditoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(solicitacaoCriada);
    }

    /**
     * Busca solicitação por ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar solicitação por ID", description = "Busca uma solicitação de crédito pelo ID")
    public ResponseEntity<SolicitacaoCreditoDTO> buscarSolicitacaoPorId(@Parameter(description = "ID da solicitação") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar solicitação de crédito ID: {}", id);
        SolicitacaoCreditoDTO solicitacao = solicitacaoCreditoService.buscarSolicitacaoPorId(id);
        return ResponseEntity.ok(solicitacao);
    }

    /**
     * Lista solicitações por cliente
     */
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar solicitações por cliente", description = "Lista todas as solicitações de crédito de um cliente")
    public ResponseEntity<List<SolicitacaoCreditoDTO>> listarSolicitacoesPorCliente(@Parameter(description = "ID do cliente") @PathVariable Long clienteId) {
        log.info("Recebida solicitação para listar solicitações do cliente ID: {}", clienteId);
        List<SolicitacaoCreditoDTO> solicitacoes = solicitacaoCreditoService.listarSolicitacoesPorCliente(clienteId);
        return ResponseEntity.ok(solicitacoes);
    }

    /**
     * Lista solicitações por status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Listar solicitações por status", description = "Lista solicitações de crédito por status")
    public ResponseEntity<List<SolicitacaoCreditoDTO>> listarSolicitacoesPorStatus(@Parameter(description = "Status da solicitação") @PathVariable SolicitacaoCredito.StatusSolicitacao status) {
        log.info("Recebida solicitação para listar solicitações com status: {}", status);
        List<SolicitacaoCreditoDTO> solicitacoes = solicitacaoCreditoService.listarSolicitacoesPorStatus(status);
        return ResponseEntity.ok(solicitacoes);
    }

    /**
     * Lista solicitações pendentes
     */
    @GetMapping("/pendentes")
    @Operation(summary = "Listar solicitações pendentes", description = "Lista todas as solicitações de crédito pendentes")
    public ResponseEntity<List<SolicitacaoCreditoDTO>> listarSolicitacoesPendentes() {
        log.info("Recebida solicitação para listar solicitações pendentes");
        List<SolicitacaoCreditoDTO> solicitacoes = solicitacaoCreditoService.listarSolicitacoesPendentes();
        return ResponseEntity.ok(solicitacoes);
    }

    /**
     * Lista solicitações aprovadas
     */
    @GetMapping("/aprovadas")
    @Operation(summary = "Listar solicitações aprovadas", description = "Lista todas as solicitações de crédito aprovadas")
    public ResponseEntity<List<SolicitacaoCreditoDTO>> listarSolicitacoesAprovadas() {
        log.info("Recebida solicitação para listar solicitações aprovadas");
        List<SolicitacaoCreditoDTO> solicitacoes = solicitacaoCreditoService.listarSolicitacoesAprovadas();
        return ResponseEntity.ok(solicitacoes);
    }

    /**
     * Aprova solicitação de crédito
     */
    @PutMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar solicitação", description = "Aprova uma solicitação de crédito")
    public ResponseEntity<Void> aprovarSolicitacao(@Parameter(description = "ID da solicitação") @PathVariable Long id, @Parameter(description = "Valor aprovado") @RequestParam BigDecimal valorAprovado, @Parameter(description = "Prazo aprovado em meses") @RequestParam Integer prazoAprovado, @Parameter(description = "Taxa aprovada") @RequestParam BigDecimal taxaAprovada) {
        log.info("Recebida solicitação para aprovar solicitação de crédito ID: {}", id);
        solicitacaoCreditoService.aprovarSolicitacao(id, valorAprovado, prazoAprovado, taxaAprovada);
        return ResponseEntity.noContent().build();
    }

    /**
     * Rejeita solicitação de crédito
     */
    @PutMapping("/{id}/rejeitar")
    @Operation(summary = "Rejeitar solicitação", description = "Rejeita uma solicitação de crédito")
    public ResponseEntity<Void> rejeitarSolicitacao(@Parameter(description = "ID da solicitação") @PathVariable Long id, @Parameter(description = "Observações") @RequestParam String observacoes) {
        log.info("Recebida solicitação para rejeitar solicitação de crédito ID: {}", id);
        solicitacaoCreditoService.rejeitarSolicitacao(id, observacoes);
        return ResponseEntity.noContent().build();
    }

    /**
     * Cancela solicitação de crédito
     */
    @PutMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar solicitação", description = "Cancela uma solicitação de crédito")
    public ResponseEntity<Void> cancelarSolicitacao(@Parameter(description = "ID da solicitação") @PathVariable Long id) {
        solicitacaoCreditoService.cancelarSolicitacao(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/refer")
    @Operation(summary = "Listar refer (analise manual)")
    public ResponseEntity<List<SolicitacaoCreditoDTO>> listarRefer() {
        return ResponseEntity.ok(solicitacaoCreditoService.listarSolicitacoesRefer());
    }

    @PutMapping("/{id}/emitir-oferta")
    @Operation(summary = "Emitir oferta (apos aprovacao)")
    public ResponseEntity<SolicitacaoCreditoDTO> emitirOferta(@PathVariable Long id, @RequestParam BigDecimal valorAprovado, @RequestParam Integer prazoAprovado, @RequestParam BigDecimal taxaAprovada) {
        return ResponseEntity.ok(solicitacaoCreditoService.emitirOferta(id, valorAprovado, prazoAprovado, taxaAprovada));
    }

    @PutMapping("/{id}/aceitar-oferta")
    @Operation(summary = "Cliente aceita oferta")
    public ResponseEntity<SolicitacaoCreditoDTO> aceitarOferta(@PathVariable Long id) {
        return ResponseEntity.ok(solicitacaoCreditoService.aceitarOferta(id));
    }

    @PutMapping("/{id}/registrar-contrato")
    @Operation(summary = "Registrar contrato assinado (URL ou ID)")
    public ResponseEntity<SolicitacaoCreditoDTO> registrarContrato(@PathVariable Long id, @RequestParam String contratoUrl) {
        return ResponseEntity.ok(solicitacaoCreditoService.registrarContrato(id, contratoUrl));
    }

    @PutMapping("/{id}/liberar")
    @Operation(summary = "Liberar credito")
    public ResponseEntity<SolicitacaoCreditoDTO> liberar(@PathVariable Long id) {
        return ResponseEntity.ok(solicitacaoCreditoService.liberar(id));
    }

    @java.lang.SuppressWarnings("all")
    public SolicitacaoCreditoController(final SolicitacaoCreditoService solicitacaoCreditoService) {
        this.solicitacaoCreditoService = solicitacaoCreditoService;
    }
}
