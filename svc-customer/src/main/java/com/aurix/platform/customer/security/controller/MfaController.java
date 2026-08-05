package com.aurix.platform.customer.security.controller;

import com.aurix.platform.customer.security.entity.MfaConfig;
import com.aurix.platform.customer.security.entity.MfaToken;
import com.aurix.platform.customer.security.service.MfaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mfa")
@Tag(name = "MFA", description = "APIs para Autenticação Multifator")
@CrossOrigin(origins = "*")
public class MfaController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MfaController.class);
    private final MfaService mfaService;

    @PostMapping("/configurar")
    @Operation(summary = "Configurar MFA para usuário")
    public ResponseEntity<MfaConfig> configurarMfa(@RequestParam Long usuarioId, @RequestParam MfaConfig.TipoMfa tipoMfa, @RequestParam String valorConfigurado) {
        return ResponseEntity.ok(mfaService.configurarMfa(usuarioId, tipoMfa, valorConfigurado));
    }

    @PostMapping("/gerar-token")
    @Operation(summary = "Gerar token MFA")
    public ResponseEntity<MfaToken> gerarTokenMfa(@RequestParam Long usuarioId, @RequestParam String sessaoId, @RequestParam MfaToken.TipoMfa tipoMfa) {
        return ResponseEntity.ok(mfaService.gerarTokenMfa(usuarioId, sessaoId, tipoMfa));
    }

    @PostMapping("/validar-token")
    @Operation(summary = "Validar token MFA")
    public ResponseEntity<Map<String, Object>> validarTokenMfa(@RequestParam String codigoToken, @RequestParam String codigoInformado) {
        boolean valido = mfaService.validarTokenMfa(codigoToken, codigoInformado);
        return ResponseEntity.ok(Map.of("valido", valido));
    }

    @PostMapping("/validar-biometria")
    @Operation(summary = "Validar biometria")
    public ResponseEntity<Map<String, Object>> validarBiometria(@RequestParam Long usuarioId, @RequestParam String dadosBiometricos) {
        boolean valido = mfaService.validarBiometria(usuarioId, dadosBiometricos);
        return ResponseEntity.ok(Map.of("valido", valido));
    }

    @GetMapping("/configuracoes/{usuarioId}")
    @Operation(summary = "Listar configurações MFA do usuário")
    public ResponseEntity<List<MfaConfig>> listarConfiguracoes(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(mfaService.listarConfiguracoesMfa(usuarioId));
    }

    @PostMapping("/desativar")
    @Operation(summary = "Desativar MFA")
    public ResponseEntity<Map<String, String>> desativarMfa(@RequestParam Long usuarioId, @RequestParam MfaConfig.TipoMfa tipoMfa) {
        mfaService.desativarMfa(usuarioId, tipoMfa);
        return ResponseEntity.ok(Map.of("mensagem", "MFA desativado com sucesso"));
    }

    @java.lang.SuppressWarnings("all")
    public MfaController(final MfaService mfaService) {
        this.mfaService = mfaService;
    }
}
