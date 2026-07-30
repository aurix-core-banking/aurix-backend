package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.dto.AjustarLimiteRequest;
import com.aurix.platform.cards.dto.LimiteCartaoResponse;
import com.aurix.platform.cards.service.LimiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards/limites")
@Tag(name = "Limites Cartao", description = "Gerenciamento de limites de cartoes")
public class LimiteController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LimiteController.class);

    private final LimiteService limiteService;

    public LimiteController(LimiteService limiteService) {
        this.limiteService = limiteService;
    }

    @PutMapping("/{cartaoId}")
    @Operation(summary = "Ajustar limite do cartao")
    public ResponseEntity<LimiteCartaoResponse> ajustarLimite(@PathVariable Long cartaoId, @Valid @RequestBody AjustarLimiteRequest request) {
        return ResponseEntity.ok(limiteService.ajustarLimite(cartaoId, request));
    }

    @GetMapping("/{cartaoId}")
    @Operation(summary = "Consultar limite do cartao")
    public ResponseEntity<LimiteCartaoResponse> consultarLimite(@PathVariable Long cartaoId) {
        return ResponseEntity.ok(limiteService.consultarLimite(cartaoId));
    }

    @PostMapping("/{cartaoId}/bloquear")
    @Operation(summary = "Bloquear limite do cartao")
    public ResponseEntity<Void> bloquearLimite(@PathVariable Long cartaoId) {
        limiteService.bloquearLimite(cartaoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cartaoId}/desbloquear")
    @Operation(summary = "Desbloquear limite do cartao")
    public ResponseEntity<Void> desbloquearLimite(@PathVariable Long cartaoId) {
        limiteService.desbloquearLimite(cartaoId);
        return ResponseEntity.noContent().build();
    }
}
