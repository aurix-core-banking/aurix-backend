package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.dto.ConsentimentoOpenFinanceDTO;
import com.aurix.platform.platform.service.ConsentimentoOpenFinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller para gestão de consentimentos do Open Finance
 * 
 * Implementa as APIs padronizadas do Open Finance brasileiro
 * para gestão de consentimentos de compartilhamento de dados.
 */
@RestController
@RequestMapping("/api/platform/openfinance/consentimentos")
@Tag(name = "Consentimentos Open Finance", description = "API para gestão de consentimentos do Open Finance")
public class ConsentimentoOpenFinanceController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ConsentimentoOpenFinanceController.class);
    private final ConsentimentoOpenFinanceService consentimentoService;

    /**
     * Cria um novo consentimento
     */
    @PostMapping
    @Operation(summary = "Criar consentimento", description = "Cria um novo consentimento para compartilhamento de dados")
    @PreAuthorize("hasRole(\'USER\')")
    public ResponseEntity<ConsentimentoOpenFinanceDTO> criarConsentimento(@Valid @RequestBody ConsentimentoOpenFinanceDTO consentimentoDTO) {
        log.info("Recebida solicitação para criar consentimento do cliente: {}", consentimentoDTO.getClientId());
        ConsentimentoOpenFinanceDTO consentimentoCriado = consentimentoService.criarConsentimento(consentimentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(consentimentoCriado);
    }

    /**
     * Busca consentimento por ID
     */
    @GetMapping("/{consentId}")
    @Operation(summary = "Buscar consentimento", description = "Busca um consentimento pelo ID")
    @PreAuthorize("hasRole(\'USER\')")
    public ResponseEntity<ConsentimentoOpenFinanceDTO> buscarConsentimento(@Parameter(description = "ID do consentimento") @PathVariable String consentId) {
        log.info("Recebida solicitação para buscar consentimento: {}", consentId);
        ConsentimentoOpenFinanceDTO consentimento = consentimentoService.buscarConsentimento(consentId);
        return ResponseEntity.ok(consentimento);
    }

    /**
     * Aprova um consentimento
     */
    @PostMapping("/{consentId}/approve")
    @Operation(summary = "Aprovar consentimento", description = "Aprova um consentimento pendente")
    @PreAuthorize("hasRole(\'USER\')")
    public ResponseEntity<ConsentimentoOpenFinanceDTO> aprovarConsentimento(@Parameter(description = "ID do consentimento") @PathVariable String consentId, @Parameter(description = "ID do usuário") @RequestParam Long userId) {
        log.info("Recebida solicitação para aprovar consentimento: {} pelo usuário: {}", consentId, userId);
        ConsentimentoOpenFinanceDTO consentimento = consentimentoService.aprovarConsentimento(consentId, userId);
        return ResponseEntity.ok(consentimento);
    }

    /**
     * Rejeita um consentimento
     */
    @PostMapping("/{consentId}/reject")
    @Operation(summary = "Rejeitar consentimento", description = "Rejeita um consentimento pendente")
    @PreAuthorize("hasRole(\'USER\')")
    public ResponseEntity<ConsentimentoOpenFinanceDTO> rejeitarConsentimento(@Parameter(description = "ID do consentimento") @PathVariable String consentId, @Parameter(description = "Motivo da rejeição") @RequestParam String motivo) {
        log.info("Recebida solicitação para rejeitar consentimento: {} - Motivo: {}", consentId, motivo);
        ConsentimentoOpenFinanceDTO consentimento = consentimentoService.rejeitarConsentimento(consentId, motivo);
        return ResponseEntity.ok(consentimento);
    }

    /**
     * Revoga um consentimento
     */
    @PostMapping("/{consentId}/revoke")
    @Operation(summary = "Revogar consentimento", description = "Revoga um consentimento aprovado")
    @PreAuthorize("hasRole(\'USER\')")
    public ResponseEntity<ConsentimentoOpenFinanceDTO> revogarConsentimento(@Parameter(description = "ID do consentimento") @PathVariable String consentId, @Parameter(description = "Motivo da revogação") @RequestParam String motivo) {
        log.info("Recebida solicitação para revogar consentimento: {} - Motivo: {}", consentId, motivo);
        ConsentimentoOpenFinanceDTO consentimento = consentimentoService.revogarConsentimento(consentId, motivo);
        return ResponseEntity.ok(consentimento);
    }

    /**
     * Lista consentimentos por usuário
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Listar consentimentos por usuário", description = "Lista todos os consentimentos de um usuário")
    @PreAuthorize("hasRole(\'USER\')")
    public ResponseEntity<List<ConsentimentoOpenFinanceDTO>> listarConsentimentosPorUsuario(@Parameter(description = "ID do usuário") @PathVariable Long userId) {
        log.info("Recebida solicitação para listar consentimentos do usuário: {}", userId);
        List<ConsentimentoOpenFinanceDTO> consentimentos = consentimentoService.listarConsentimentosPorUsuario(userId);
        return ResponseEntity.ok(consentimentos);
    }

    /**
     * Lista consentimentos por cliente
     */
    @GetMapping("/client/{clientId}")
    @Operation(summary = "Listar consentimentos por cliente", description = "Lista todos os consentimentos de um cliente")
    @PreAuthorize("hasRole(\'CLIENT\')")
    public ResponseEntity<List<ConsentimentoOpenFinanceDTO>> listarConsentimentosPorCliente(@Parameter(description = "ID do cliente") @PathVariable String clientId) {
        log.info("Recebida solicitação para listar consentimentos do cliente: {}", clientId);
        List<ConsentimentoOpenFinanceDTO> consentimentos = consentimentoService.listarConsentimentosPorCliente(clientId);
        return ResponseEntity.ok(consentimentos);
    }

    /**
     * Lista consentimentos ativos por usuário
     */
    @GetMapping("/user/{userId}/active")
    @Operation(summary = "Listar consentimentos ativos", description = "Lista consentimentos ativos de um usuário")
    @PreAuthorize("hasRole(\'USER\')")
    public ResponseEntity<List<ConsentimentoOpenFinanceDTO>> listarConsentimentosAtivosPorUsuario(@Parameter(description = "ID do usuário") @PathVariable Long userId) {
        log.info("Recebida solicitação para listar consentimentos ativos do usuário: {}", userId);
        List<ConsentimentoOpenFinanceDTO> consentimentos = consentimentoService.listarConsentimentosAtivosPorUsuario(userId);
        return ResponseEntity.ok(consentimentos);
    }

    /**
     * Verifica se usuário tem consentimento ativo para cliente
     */
    @GetMapping("/check")
    @Operation(summary = "Verificar consentimento ativo", description = "Verifica se usuário tem consentimento ativo para cliente")
    @PreAuthorize("hasRole(\'USER\')")
    public ResponseEntity<Boolean> verificarConsentimentoAtivo(@Parameter(description = "ID do cliente") @RequestParam String clientId, @Parameter(description = "ID do usuário") @RequestParam Long userId) {
        log.info("Recebida solicitação para verificar consentimento ativo - Cliente: {} - Usuário: {}", clientId, userId);
        boolean temConsentimento = consentimentoService.verificarConsentimentoAtivo(clientId, userId);
        return ResponseEntity.ok(temConsentimento);
    }

    /**
     * Processa consentimentos expirados (endpoint administrativo)
     */
    @PostMapping("/process-expired")
    @Operation(summary = "Processar consentimentos expirados", description = "Processa consentimentos que expiraram")
    @PreAuthorize("hasRole(\'ADMIN\')")
    public ResponseEntity<Void> processarConsentimentosExpirados() {
        log.info("Recebida solicitação para processar consentimentos expirados");
        consentimentoService.processarConsentimentosExpirados();
        return ResponseEntity.noContent().build();
    }

    @java.lang.SuppressWarnings("all")
    public ConsentimentoOpenFinanceController(final ConsentimentoOpenFinanceService consentimentoService) {
        this.consentimentoService = consentimentoService;
    }
}
