package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.service.TokenOpenFinanceService;
import com.aurix.platform.platform.service.LogAcessoOpenFinanceService;
import com.aurix.platform.platform.service.OpenFinanceDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Controller principal para APIs padronizadas do Open Finance
 * 
 * Implementa as APIs padronizadas definidas pelo BACEN para
 * compartilhamento de dados financeiros entre instituições.
 */
@RestController
@RequestMapping("/api/platform/openfinance")
@Tag(name = "Open Finance APIs", description = "APIs padronizadas do Open Finance brasileiro")
public class OpenFinanceController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OpenFinanceController.class);
    private final TokenOpenFinanceService tokenService;
    private final LogAcessoOpenFinanceService logService;
    private final OpenFinanceDataService openFinanceDataService;

    /**
     * API de contas - Lista contas do usuário
     */
    @GetMapping("/accounts")
    @Operation(summary = "Listar contas", description = "Lista contas do usuário autorizadas")
    public ResponseEntity<Map<String, Object>> listarContas(@Parameter(description = "Token de acesso") @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        log.info("Recebida solicitação para listar contas");
        // Extrair token
        String accessToken = extrairToken(authorization);
        // Validar token
        var token = tokenService.validarToken(accessToken);
        // Verificar rate limit
        if (!tokenService.verificarRateLimit(accessToken)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        // Registrar acesso
        logService.registrarAcesso(token.getConsentId(), token.getClientId(), token.getUserId(), "/open-finance/accounts", "GET", 200, System.currentTimeMillis(), obterIpAddress(request), request.getHeader("User-Agent"), request.getHeader("X-Device-Id"), request.getHeader("X-Geolocation"), "{\"dados\": \"contas\"}");
        List<Map<String, Object>> accounts = openFinanceDataService.listarContasPorToken(token);
        Map<String, Object> response = Map.of("data", Map.of("accounts", accounts), "links", Map.of("self", "/open-finance/accounts"), "meta", Map.of("totalRecords", accounts.size(), "totalPages", accounts.isEmpty() ? 0 : 1));
        return ResponseEntity.ok(response);
    }

    /**
     * API de transações - Lista transações das contas
     */
    @GetMapping("/accounts/{accountId}/transactions")
    @Operation(summary = "Listar transações", description = "Lista transações de uma conta específica")
    public ResponseEntity<Map<String, Object>> listarTransacoes(@Parameter(description = "ID da conta") @PathVariable String accountId, @Parameter(description = "Token de acesso") @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        log.info("Recebida solicitação para listar transações da conta: {}", accountId);
        // Extrair token
        String accessToken = extrairToken(authorization);
        // Validar token
        var token = tokenService.validarToken(accessToken);
        // Verificar rate limit
        if (!tokenService.verificarRateLimit(accessToken)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        // Registrar acesso
        logService.registrarAcesso(token.getConsentId(), token.getClientId(), token.getUserId(), "/open-finance/accounts/" + accountId + "/transactions", "GET", 200, System.currentTimeMillis(), obterIpAddress(request), request.getHeader("User-Agent"), request.getHeader("X-Device-Id"), request.getHeader("X-Geolocation"), "{\"dados\": \"transacoes\"}");
        List<Map<String, Object>> transactions = openFinanceDataService.listarTransacoesPorConta(accountId, token);
        Map<String, Object> response = Map.of("data", Map.of("transactions", transactions), "links", Map.of("self", "/open-finance/accounts/" + accountId + "/transactions"), "meta", Map.of("totalRecords", transactions.size(), "totalPages", transactions.isEmpty() ? 0 : 1));
        return ResponseEntity.ok(response);
    }

    /**
     * API de cartões de crédito - Lista cartões do usuário
     */
    @GetMapping("/credit-cards-accounts")
    @Operation(summary = "Listar cartões de crédito", description = "Lista cartões de crédito do usuário")
    public ResponseEntity<Map<String, Object>> listarCartoesCredito(@Parameter(description = "Token de acesso") @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        log.info("Recebida solicitação para listar cartões de crédito");
        // Extrair token
        String accessToken = extrairToken(authorization);
        // Validar token
        var token = tokenService.validarToken(accessToken);
        // Verificar rate limit
        if (!tokenService.verificarRateLimit(accessToken)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        // Registrar acesso
        logService.registrarAcesso(token.getConsentId(), token.getClientId(), token.getUserId(), "/open-finance/credit-cards-accounts", "GET", 200, System.currentTimeMillis(), obterIpAddress(request), request.getHeader("User-Agent"), request.getHeader("X-Device-Id"), request.getHeader("X-Geolocation"), "{\"dados\": \"cartoes_credito\"}");
        List<Map<String, Object>> creditCards = openFinanceDataService.listarCartoesCreditoPorToken(token.toEntity());
        Map<String, Object> response = Map.of("data", Map.of("creditCards", creditCards), "links", Map.of("self", "/open-finance/credit-cards-accounts"), "meta", Map.of("totalRecords", creditCards.size(), "totalPages", creditCards.isEmpty() ? 0 : 1));
        return ResponseEntity.ok(response);
    }

    /**
     * API de dados pessoais - Lista dados pessoais do usuário
     */
    @GetMapping("/customers/personal/identifications")
    @Operation(summary = "Listar identificações pessoais", description = "Lista dados de identificação pessoal do usuário")
    public ResponseEntity<Map<String, Object>> listarIdentificacoesPessoais(@Parameter(description = "Token de acesso") @RequestHeader("Authorization") String authorization, HttpServletRequest request) {
        log.info("Recebida solicitação para listar identificações pessoais");
        // Extrair token
        String accessToken = extrairToken(authorization);
        // Validar token
        var token = tokenService.validarToken(accessToken);
        // Verificar rate limit
        if (!tokenService.verificarRateLimit(accessToken)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        // Registrar acesso
        logService.registrarAcesso(token.getConsentId(), token.getClientId(), token.getUserId(), "/open-finance/customers/personal/identifications", "GET", 200, System.currentTimeMillis(), obterIpAddress(request), request.getHeader("User-Agent"), request.getHeader("X-Device-Id"), request.getHeader("X-Geolocation"), "{\"dados\": \"identificacoes_pessoais\"}");
        List<Map<String, Object>> identifications = openFinanceDataService.listarIdentificacoesPessoaisPorToken(token.toEntity());
        Map<String, Object> response = Map.of("data", Map.of("personalIdentifications", identifications), "links", Map.of("self", "/open-finance/customers/personal/identifications"), "meta", Map.of("totalRecords", identifications.size(), "totalPages", identifications.isEmpty() ? 0 : 1));
        return ResponseEntity.ok(response);
    }

    /**
     * API de recursos - Lista recursos disponíveis
     */
    @GetMapping("/resources")
    @Operation(summary = "Listar recursos"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        