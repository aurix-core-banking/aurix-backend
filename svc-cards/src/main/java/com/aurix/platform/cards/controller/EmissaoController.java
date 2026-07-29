package com.aurix.platform.cards.controller;

import com.aurix.platform.cards.dto.BloquearCartaoRequest;
import com.aurix.platform.cards.dto.EmitirCartaoRequest;
import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.service.EmissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards/emissao")
@Tag(name = "Emissao Cartao", description = "Emissao, ativacao, bloqueio e cancelamento de cartoes")
public class EmissaoController {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EmissaoController.class);

    private final EmissaoService emissaoService;

    public EmissaoController(EmissaoService emissaoService) {
        this.emissaoService = emissaoService;
    }

    @PostMapping
    @Operation(summary = "Emitir novo cartao")
    public ResponseEntity<Cartao> emitir(@Valid @RequestBody EmitirCartaoRequest request) {
        Cartao cartao = emissaoService.emitir(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartao);
    }

    @PostMapping("/{id}/bloquear")
    @Operation(summary = "Bloquear cartao")
    public ResponseEntity<Cartao> bloquear(@PathVariable Long id, @Valid @RequestBody BloquearCartaoRequest request) {
        return ResponseEntity.ok(emissaoService.bloquear(id, request));
    }

    @PostMapping("/{id}/ativar")
    @Operation(summary = "Ativar cartao")
    public ResponseEntity<Cartao> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(emissaoService.ativar(id));
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar cartao")
    public ResponseEntity<Cartao> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(emissaoService.cancelar(id));
    }
}
