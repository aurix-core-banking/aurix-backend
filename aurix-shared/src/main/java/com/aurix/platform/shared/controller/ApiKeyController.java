package com.aurix.platform.shared.controller;

import com.aurix.platform.shared.entity.ApiKey;
import com.aurix.platform.shared.service.ApiKeyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/api-keys")
public class ApiKeyController {

    private final ApiKeyService service;

    public ApiKeyController(ApiKeyService service) {
        this.service = service;
    }

    /**
     * Criar nova API key.
     * A key plaintext é retornada APENAS nesta resposta.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> criar(@RequestBody CriarApiKeyRequest request) {
        Map<String, Object> resultado = service.criar(
            request.nome(), request.tenantId(), request.plano(), request.diasExpiracao());
        return ResponseEntity.ok(resultado);
    }

    /**
     * Listar todas as API keys (sem expor as keys).
     */
    @GetMapping
    public ResponseEntity<List<ApiKey>> listar() {
        return ResponseEntity.ok(service.listarTodas());
    }

    /**
     * Rotacionar API key — gera nova e invalida a antiga.
     */
    @PostMapping("/{id}/rotacionar")
    public ResponseEntity<Map<String, String>> rotacionar(@PathVariable Long id) {
        return ResponseEntity.ok(service.rotacionar(id));
    }

    /**
     * Revogar API key.
     */
    @PostMapping("/{id}/revogar")
    public ResponseEntity<Void> revogar(@PathVariable Long id) {
        service.revogar(id);
        return ResponseEntity.noContent().build();
    }

    record CriarApiKeyRequest(
        String nome,
        String tenantId,
        String plano,
        Integer diasExpiracao
    ) {}
}
