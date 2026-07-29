package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.dto.AutorizarTransacaoRequest;
import com.aurix.platform.cards.dto.TransacaoResponse;
import com.aurix.platform.cards.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards/transacoes")
@Tag(name = "Transacoes Cartao", description = "Autorizacao, captura e estorno de transacoes")
public class TransacaoController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransacaoController.class);

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping("/autorizar")
    @Operation(summary = "Autorizar transacao")
    public ResponseEntity<TransacaoResponse> autorizar(@Valid @RequestBody AutorizarTransacaoRequest request) {
        return ResponseEntity.ok(transacaoService.autorizar(request));
    }

    @PostMapping("/{id}/capturar")
    @Operation(summary = "Capturar transacao")
    public ResponseEntity<TransacaoResponse> capturar(@PathVariable Long id) {
        return ResponseEntity.ok(transacaoService.capturar(id));
    }

    @PostMapping("/{id}/estornar")
    @Operation(summary = "Estornar transacao")
    public ResponseEntity<TransacaoResponse> estornar(@PathVariable Long id) {
        return ResponseEntity.ok(transacaoService.estornar(id));
    }
}
