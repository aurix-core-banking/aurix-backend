package com.aurix.platform.payments.ted.controller;

import com.aurix.platform.payments.ted.service.TedPagamentoService;
import com.aurix.platform.shared.dto.PagamentoTedDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller para gestão de pagamentos TED e DOC.
 */
@RestController
@RequestMapping("/api/pagamentos/ted")
@Tag(name = "TED/DOC Pagamentos", description = "API para gestão de pagamentos TED e DOC do Aurix")
public class TedPagamentoController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TedPagamentoController.class);
    private final TedPagamentoService tedPagamentoService;

    /**
     * Cria um novo pagamento TED ou DOC.
     * TED: valor até R$ 4.999,99. DOC: valor >= R$ 5.000,00 (agendamento D+1).
     */
    @PostMapping
    @Operation(summary = "Criar pagamento TED/DOC", description = "Cria um novo pagamento TED ou DOC conforme o valor informado")
    public ResponseEntity<PagamentoTedDTO> criarPagamento(@Valid @RequestBody PagamentoTedDTO dto) {
        log.info("Recebida solicitação para criar pagamento TED/DOC para conta: {}", dto.getContaOrigemId());
        PagamentoTedDTO pagamentoCriado = tedPagamentoService.criarPagamento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoCriado);
    }

    /**
     * Busca pagamento TED/DOC por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento TED/DOC por ID", description = "Busca um pagamento TED ou DOC pelo ID")
    public ResponseEntity<PagamentoTedDTO> buscarPagamentoPorId(
            @Parameter(description = "ID do pagamento") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar pagamento TED/DOC ID: {}", id);
        PagamentoTedDTO pagamento = tedPagamentoService.buscarPorId(id);
        return ResponseEntity.ok(pagamento);
    }

    /**
     * Cancela pagamento TED/DOC pendente ou agendado.
     */
    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pagamento TED/DOC", description = "Cancela um pagamento TED ou DOC pendente/agendado")
    public ResponseEntity<Void> cancelarPagamento(
            @Parameter(description = "ID do pagamento") @PathVariable Long id) {
        log.info("Recebida solicitação para cancelar pagamento TED/DOC ID: {}", id);
        tedPagamentoService.cancelarPagamento(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista pagamentos por conta.
     */
    @GetMapping("/conta/{contaId}")
    @Operation(summary = "Listar pagamentos por conta", description = "Lista todos os pagamentos TED/DOC de uma conta")
    public ResponseEntity<List<PagamentoTedDTO>> listarPorConta(
            @Parameter(description = "ID da conta") @PathVariable Long contaId) {
        log.info("Recebida solicitação para listar pagamentos da conta ID: {}", contaId);
        List<PagamentoTedDTO> pagamentos = tedPagamentoService.listarPorConta(contaId);
        return ResponseEntity.ok(pagamentos);
    }

    /**
     * Lista pagamentos por período.
     */
    @GetMapping("/periodo")
    @Operation(summary = "Listar pagamentos por período", description = "Lista pagamentos TED/DOC por período")
    public ResponseEntity<List<PagamentoTedDTO>> listarPorPeriodo(
            @Parameter(description = "Data de início") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @Parameter(description = "Data de fim") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        log.info("Recebida solicitação para listar pagamentos do período: {} a {}", inicio, fim);
        List<PagamentoTedDTO> pagamentos = tedPagamentoService.listarPorPeriodo(inicio, fim);
        return ResponseEntity.ok(pagamentos);
    }

    @java.lang.SuppressWarnings("all")
    public TedPagamentoController(final TedPagamentoService tedPagamentoService) {
        this.tedPagamentoService = tedPagamentoService;
    }
}
