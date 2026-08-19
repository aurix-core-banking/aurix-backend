package com.aurix.platform.cards.token.controller;

import com.aurix.platform.cards.token.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cartoes")
@Tag(name = "Tokenização de Cartões", description = "APIs para tokenização segura de cartões")
@CrossOrigin(origins = "*")
public class TokenController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TokenController.class);
    private final TokenService tokenService;

    @java.lang.SuppressWarnings("all")
    public TokenController(final TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/tokenizar")
    @Operation(summary = "Criar token para cartão vinculado a um merchant")
    public ResponseEntity<Map<String, Object>> criarToken(
            @RequestParam Long cartaoId,
            @RequestParam String merchantId,
            @RequestParam(defaultValue = "") String descricao) {
        return ResponseEntity.ok(tokenService.criarToken(cartaoId, merchantId, descricao));
    }

    @GetMapping("/tokens")
    @Operation(summary = "Listar tokens de um cartão")
    public ResponseEntity<List<Map<String, Object>>> listarTokens(@RequestParam Long cartaoId) {
        return ResponseEntity.ok(tokenService.listarTokens(cartaoId));
    }

    @DeleteMapping("/tokens/{id}")
    @Operation(summary = "Revogar token")
    public ResponseEntity<Map<String, Object>> revogarToken(@PathVariable Long id) {
        return ResponseEntity.ok(tokenService.revogarToken(id));
    }
}
