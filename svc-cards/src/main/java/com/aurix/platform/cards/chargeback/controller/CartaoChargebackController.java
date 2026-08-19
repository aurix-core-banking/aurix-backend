package com.aurix.platform.cards.chargeback.controller;

import com.aurix.platform.cards.chargeback.service.CartaoChargebackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/cartoes/chargeback")
@Tag(name = "Chargeback de Cartões", description = "APIs para disputa e chargeback de transações")
@CrossOrigin(origins = "*")
public class CartaoChargebackController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CartaoChargebackController.class);
    private final CartaoChargebackService cartaoChargebackService;

    @java.lang.SuppressWarnings("all")
    public CartaoChargebackController(final CartaoChargebackService cartaoChargebackService) {
        this.cartaoChargebackService = cartaoChargebackService;
    }

    @PostMapping
    @Operation(summary = "Solicitar chargeback para uma transação")
    public ResponseEntity<Map<String, Object>> solicitarChargeback(
            @RequestParam Long transacaoId,
            @RequestParam Long cartaoId,
            @RequestParam String motivo,
            @RequestParam(required = false) String descricao) {
        return ResponseEntity.ok(cartaoChargebackService.solicitar(transacaoId, cartaoId, motivo, descricao));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar status do chargeback")
    public ResponseEntity<Map<String, Object>> consultarChargeback(@PathVariable Long id) {
        return ResponseEntity.ok(cartaoChargebackService.consultar(id));
    }

    @PostMapping("/{id}/evidencia")
    @Operation(summary = "Adicionar evidência ao chargeback")
    public ResponseEntity<Map<String, Object>> adicionarEvidencia(
            @PathVariable Long id,
            @RequestParam String descricao,
            @RequestParam(required = false) MultipartFile arquivo) {
        return ResponseEntity.ok(cartaoChargebackService.adicionarEvidencia(id, descricao, arquivo));
    }
}
