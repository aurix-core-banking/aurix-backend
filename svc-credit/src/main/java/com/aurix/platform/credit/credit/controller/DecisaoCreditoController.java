package com.aurix.platform.credit.credit.controller;

import com.aurix.platform.credit.credit.service.DecisaoCreditoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/credit/decisao")
@Tag(name = "Decisao de Credito", description = "API de decisao approve/decline/refer e score")
public class DecisaoCreditoController {
    private final DecisaoCreditoService decisaoCreditoService;

    @PostMapping("/{solicitacaoId}")
    @Operation(summary = "Obter decisao para solicitacao")
    public ResponseEntity<DecisaoCreditoService.DecisaoResponse> obterDecisao(@PathVariable Long solicitacaoId) {
        return ResponseEntity.ok(decisaoCreditoService.obterDecisao(solicitacaoId));
    }

    @java.lang.SuppressWarnings("all")
    public DecisaoCreditoController(final DecisaoCreditoService decisaoCreditoService) {
        this.decisaoCreditoService = decisaoCreditoService;
    }
}
