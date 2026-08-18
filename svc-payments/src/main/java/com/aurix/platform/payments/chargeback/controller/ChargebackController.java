package com.aurix.platform.payments.chargeback.controller;

import com.aurix.platform.payments.chargeback.service.ChargebackService;
import com.aurix.platform.shared.dto.ChargebackDTO;
import com.aurix.platform.shared.entity.Chargeback;
import com.aurix.platform.shared.entity.ChargebackEvidencia;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller para gestão de chargebacks (estornos).
 */
@RestController
@RequestMapping("/api/chargebacks")
@Tag(name = "Chargeback", description = "API para solicitação e gestão de chargebacks do Aurix")
public class ChargebackController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChargebackController.class);
    private final ChargebackService chargebackService;

    /**
     * Solicita um novo chargeback.
     */
    @PostMapping
    @Operation(summary = "Solicitar chargeback", description = "Abre uma solicitação de chargeback com prazo de até 120 dias")
    public ResponseEntity<ChargebackDTO> solicitarChargeback(@Valid @RequestBody ChargebackDTO dto) {
        log.info("Recebida solicitação de chargeback para conta: {}", dto.getContaId());
        ChargebackDTO chargebackCriado = chargebackService.solicitarChargeback(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(chargebackCriado);
    }

    /**
     * Busca chargeback por ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar chargeback por ID", description = "Busca um chargeback pelo ID")
    public ResponseEntity<ChargebackDTO> buscarPorId(
            @Parameter(description = "ID do chargeback") @PathVariable Long id) {
        log.info("Recebida solicitação para buscar chargeback ID: {}", id);
        ChargebackDTO chargeback = chargebackService.buscarPorId(id);
        return ResponseEntity.ok(chargeback);
    }

    /**
     * Adiciona evidência a um chargeback.
     */
    @PostMapping("/{id}/evidencia")
    @Operation(summary = "Adicionar evidência ao chargeback", description = "Anexa evidência documental ao processo de chargeback")
    public ResponseEntity<ChargebackEvidencia> adicionarEvidencia(
            @Parameter(description = "ID do chargeback") @PathVariable Long id,
            @Valid @RequestBody ChargebackEvidencia evidencia) {
        log.info("Recebida solicitação para adicionar evidência ao chargeback ID: {}", id);
        ChargebackEvidencia evidenciaSalva = chargebackService.adicionarEvidencia(id, evidencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(evidenciaSalva);
    }

    /**
     * Move chargeback para "Em Análise".
     */
    @PostMapping("/{id}/iniciar-analise")
    @Operation(summary = "Iniciar análise do chargeback", description = "Move o chargeback para fase de análise")
    public ResponseEntity<Void> iniciarAnalise(
            @Parameter(description = "ID do chargeback") @PathVariable Long id) {
        log.info("Recebida solicitação para iniciar análise do chargeback ID: {}", id);
        chargebackService.iniciarAnalise(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Move chargeback para "Em Contestação".
     */
    @PostMapping("/{id}/contestar")
    @Operation(summary = "Contestar chargeback", description = "Move o chargeback para fase de contestação junto à instituição")
    public ResponseEntity<Void> contestar(
            @Parameter(description = "ID do chargeback") @PathVariable Long id) {
        log.info("Recebida solicitação para contestar chargeback ID: {}", id);
        chargebackService.contestar(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Resolve o chargeback com resultado final.
     */
    @PostMapping("/{id}/resolver")
    @Operation(summary = "Resolver chargeback", description = "Resolve o chargeback com resultado: DEFERIDO, INDEFERIDO ou PARCIAL")
    public ResponseEntity<Void> resolver(
            @Parameter(description = "ID do chargeback") @PathVariable Long id,
            @RequestParam @Parameter(description = "Resultado") Chargeback.ResultadoChargeback resultado,
            @RequestParam(required = false) @Parameter(description = "Justificativa") String justificativa) {
        log.info("Recebida solicitação para resolver chargeback ID: {} com resultado: {}", id, resultado);
        chargebackService.resolver(id, resultado, justificativa);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista chargebacks por conta.
     */
    @GetMapping("/conta/{contaId}")
    @Operation(summary = "Listar chargebacks por conta", description = "Lista todos os chargebacks de uma conta")
    public ResponseEntity<List<ChargebackDTO>> listarPorConta(
            @Parameter(description = "ID da conta") @PathVariable Long contaId) {
        log.info("Recebida solicitação para listar chargebacks da conta: {}", contaId);
        List<ChargebackDTO> chargebacks = chargebackService.listarPorConta(contaId);
        return ResponseEntity.ok(chargebacks);
    }

    /**
     * Lista chargebacks por status.
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Listar chargebacks por status", description = "Lista chargebacks filtrados por status")
    public ResponseEntity<List<ChargebackDTO>> listarPorStatus(
            @Parameter(description = "Status") @PathVariable Chargeback.StatusChargeback status) {
        log.info("Recebida solicitação para listar chargebacks com status: {}", status);
        List<ChargebackDTO> chargebacks = chargebackService.listarPorStatus(status);
        return ResponseEntity.ok(chargebacks);
    }

    @java.lang.SuppressWarnings("all")
    public ChargebackController(final ChargebackService chargebackService) {
        this.chargebackService = chargebackService;
    }
}
