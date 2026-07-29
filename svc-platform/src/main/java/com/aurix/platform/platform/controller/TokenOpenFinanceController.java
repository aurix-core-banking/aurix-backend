package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.dto.TokenOpenFinanceDTO;
import com.aurix.platform.platform.service.TokenOpenFinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller para gestão de tokens do Open Finance
 * 
 * Implementa as APIs para geração, validação e renovação
 * de tokens de acesso do Open Finance.
 */
@RestController
@RequestMapping("/api/platform/openfinance/tokens")
@Tag(name = "Tokens Open Finance", description = "API para gestão de tokens do Open Finance")
public class TokenOpenFinanceController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TokenOpenFinanceController.class);
    private final TokenOpenFinanceService tokenService;

    /**
     * Valida token de acesso
     */
    @PostMapping("/validate")
    @Operation(summary = "Validar token", description = "Valida um token de acesso")
    @PreAuthorize("hasRole(\'CLIENT\')")
    public ResponseEntity<TokenOpenFinanceDTO> validarToken(@Parameter(description = "Token de acesso") @RequestParam String accessToken) {
        log.debug("Recebida solicitação para validar token");
        TokenOpenFinanceDTO token = tokenService.validarToken(accessToken);
        return ResponseEntity.ok(token);
    }

    /**
     * Renova token usando refresh token
     */
    @PostMapping("/refresh")
    @Operation(summary = "Renovar token", description = "Renova um token usando refresh token")
    @PreAuthorize("hasRole(\'CLIENT\')")
    public ResponseEntity<TokenOpenFinanceDTO> renovarToken(@Parameter(description = "Refresh token") @RequestParam String refreshToken) {
        log.info("Recebida solicitação para renovar token");
        TokenOpenFinanceDTO token = tokenService.renovarToken(refreshToken);
        return ResponseEntity.ok(token);
    }

    /**
     * Revoga token
     */
    @PostMapping("/revoke")
    @Operation(summary = "Revogar token", description = "Revoga um token de acesso")
    @PreAuthorize("hasRole(\'CLIENT\')")
    public ResponseEntity<Void> revogarToken(@Parameter(description = "Token de acesso") @RequestParam String accessToken, @Parameter(description = "Motivo da revogação") @RequestParam(required = false) String motivo) {
        log.info("Recebida solicitação para revogar token - Motivo: {}", motivo);
        tokenService.revogarToken(accessToken, motivo);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista tokens por usuário
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar tokens por usuário", description = "Lista todos os tokens de um usuário")
    @PreAuthorize("hasRole(\'USER\')")
    public ResponseEntity<List<TokenOpenFinanceDTO>> listarTokensPorUsuario(@Parameter(description = "ID do usuário") @PathVariable Long userId) {
        log.info("Recebida solicitação para listar tokens do usuário: {}", userId);
        List<TokenOpenFinanceDTO> tokens = tokenService.listarTokensPorUsuario(userId);
        return ResponseEntity.ok(tokens);
    }

    /**
     * Lista tokens por cliente
     */
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Listar tokens por cliente", description = "Lista todos os tokens de um cliente")
    @PreAuthorize("hasRole(\'CLIENT\')")
    public ResponseEntity<List<TokenOpenFinanceDTO>> listarTokensPorCliente(@Parameter(description = "ID do cliente") @PathVariable String clientId) {
        log.info("Recebida solicitação para listar tokens do cliente: {}", clientId);
        List<TokenOpenFinanceDTO> tokens = tokenService.listarTokensPorCliente(clientId);
        return ResponseEntity.ok(tokens);
    }

    /**
     * Verifica rate limit
     */
    @GetMapping("/rate-limit")
    @Operation(summary = "Verificar rate limit", description = "Verifica se token tem rate limit disponível")
    @PreAuthorize("hasRole(\'CLIENT\')")
    public ResponseEntity<Boolean> verificarRateLimit(@Parameter(description = "Token de acesso") @RequestParam String accessToken) {
        log.debug("Recebida solicitação para verificar rate limit");
        boolean temRateLimit = tokenService.verificarRateLimit(accessToken);
        return ResponseEntity.ok(temRateLimit);
    }

    /**
     * Processa tokens expirados (endpoint administrativo)
     */
    @PostMapping("/process-expired")
    @Operation(summary = "Processar tokens expirados", description = "Processa tokens que expiraram")
    @PreAuthorize("hasRole(\'ADMIN\')")
    public ResponseEntity<Void> processarTokensExpirados() {
        log.info("Recebida solicitação para processar tokens expirados");
        tokenService.processarTokensExpirados();
        return ResponseEntity.noContent().build();
    }

    @java.lang.SuppressWarnings("all")
    public TokenOpenFinanceController(final TokenOpenFinanceService tokenService) {
        this.tokenService = tokenService;
    }
}
