package com.aurix.platform.cards.fidelidade.controller;

import com.aurix.platform.cards.fidelidade.service.FidelidadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cartoes/fidelidade")
@Tag(name = "Fidelidade", description = "Programa de pontos e resgate de fidelidade")
@CrossOrigin(origins = "*")
public class FidelidadeController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FidelidadeController.class);
    private final FidelidadeService fidelidadeService;

    @java.lang.SuppressWarnings("all")
    public FidelidadeController(final FidelidadeService fidelidadeService) {
        this.fidelidadeService = fidelidadeService;
    }

    @PostMapping("/pontos")
    @Operation(summary = "Consultar saldo de pontos")
    public ResponseEntity<Map<String, Object>> consultarPontos(@RequestParam Long contaId) {
        return ResponseEntity.ok(fidelidadeService.consultarPontos(contaId));
    }

    @PostMapping("/resgatar")
    @Operation(summary = "Resgatar pontos por milhas, cashback ou produtos")
    public ResponseEntity<Map<String, Object>> resgatarPontos(
            @RequestParam Long contaId,
            @RequestParam Integer pontos,
            @RequestParam String tipoResgate,
            @RequestParam(required = false) String parceiro) {
        return ResponseEntity.ok(fidelidadeService.resgatar(contaId, pontos, tipoResgate, parceiro));
    }

    @GetMapping("/historico")
    @Operation(summary = "Histórico de movimentação de pontos")
    public ResponseEntity<List<Map<String, Object>>> historicoPontos(
            @RequestParam Long contaId,
            @RequestParam(defaultValue = "0") Integer pagina,
            @RequestParam(defaultValue = "20") Integer tamanhoPagina) {
        return ResponseEntity.ok(fidelidadeService.historico(contaId, pagina, tamanhoPagina));
    }
}
