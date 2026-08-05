package com.aurix.platform.customer.onboarding.controller;

import com.aurix.platform.customer.onboarding.dto.ParticipanteRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoPJRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoPJResponse;
import com.aurix.platform.customer.onboarding.entity.Participante;
import com.aurix.platform.customer.onboarding.entity.StatusOnboarding;
import com.aurix.platform.customer.onboarding.service.OnboardingPJService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contas/pj")
@Tag(name = "Onboarding PJ", description = "Abertura de conta PJ")
public class ControllerPJ {
    private final OnboardingPJService onboardingPJService;

    @PostMapping
    @Operation(summary = "Iniciar onboarding PJ")
    public ResponseEntity<SolicitacaoPJResponse> iniciarOnboarding(@Valid @RequestBody SolicitacaoPJRequest request) {
        return ResponseEntity.ok(onboardingPJService.iniciarOnboarding(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar status da solicitacao PJ")
    public ResponseEntity<SolicitacaoPJResponse> consultarStatus(@PathVariable Long id) {
        return onboardingPJService.buscarStatus(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Listar solicitacoes PJ (back office)")
    public ResponseEntity<List<SolicitacaoPJResponse>> listar(@RequestParam(required = false) List<StatusOnboarding> status) {
        return ResponseEntity.ok(onboardingPJService.listar(status));
    }

    @PostMapping("/{id}/socios")
    @Operation(summary = "Adicionar socio à solicitacao PJ")
    public ResponseEntity<Void> adicionarSocio(@PathVariable Long id, @Valid @RequestBody ParticipanteRequest request) {
        onboardingPJService.adicionarParticipante(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/socios/{participanteId}")
    @Operation(summary = "Remover socio da solicitacao PJ")
    public ResponseEntity<Void> removerSocio(@PathVariable Long id, @PathVariable Long participanteId) {
        onboardingPJService.removerParticipante(id, participanteId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/documentos")
    @Operation(summary = "Adicionar documento à solicitacao PJ")
    public ResponseEntity<Void> adicionarDocumento(@PathVariable Long id, @RequestBody Map<String, String> body) {
        onboardingPJService.adicionarDocumento(id, body.getOrDefault("tipoDocumento", "OUTRO"), body.getOrDefault("nomeArquivo", "documento"), body.get("urlStorage"));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/validar-cnpj")
    @Operation(summary = "Consultar CNPJ na Receita Federal")
    public ResponseEntity<SolicitacaoPJResponse> validarCNPJ(@PathVariable Long id) {
        return ResponseEntity.ok(onboardingPJService.consultarCNPJ(id));
    }

    @PostMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar solicitacao PJ (analista)")
    public ResponseEntity<SolicitacaoPJResponse> aprovar(@PathVariable Long id, @RequestParam(required = false) String usuarioAnalista, @RequestParam(required = false) String observacao) {
        return ResponseEntity.ok(onboardingPJService.aprovar(id, usuarioAnalista, observacao));
    }

    @PostMapping("/{id}/status")
    @Operation(summary = "Avancar status da solicitacao PJ")
    public ResponseEntity<SolicitacaoPJResponse> avancarStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(onboardingPJService.avancarStatus(id,
            body.get("novoStatus"),
            body.getOrDefault("usuarioAnalista", null),
            body.getOrDefault("observacao", null)));
    }

    @PostMapping("/{id}/aml-aprovar")
    @Operation(summary = "Aprovar AML da solicitacao PJ")
    public ResponseEntity<SolicitacaoPJResponse> aprovarAML(@PathVariable Long id) {
        return ResponseEntity.ok(onboardingPJService.aprovarAML(id));
    }

    @PostMapping("/{id}/compliance-aprovar")
    @Operation(summary = "Aprovar Compliance da solicitacao PJ")
    public ResponseEntity<SolicitacaoPJResponse> aprovarCompliance(@PathVariable Long id) {
        return ResponseEntity.ok(onboardingPJService.aprovarCompliance(id));
    }

    @PostMapping("/{id}/assinatura-solicitar")
    @Operation(summary = "Solicitar assinatura da solicitacao PJ")
    public ResponseEntity<SolicitacaoPJResponse> solicitarAssinatura(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(onboardingPJService.solicitarAssinatura(id, body.getOrDefault("tipoAssinatura", "ELETRONICA")));
    }

    @PostMapping("/{id}/assinatura-confirmar")
    @Operation(summary = "Confirmar assinatura da solicitacao PJ")
    public ResponseEntity<SolicitacaoPJResponse> confirmarAssinatura(@PathVariable Long id) {
        return ResponseEntity.ok(onboardingPJService.confirmarAssinatura(id));
    }

    @PostMapping("/{id}/rejeitar")
    @Operation(summary = "Rejeitar solicitacao PJ (analista)")
    public ResponseEntity<SolicitacaoPJResponse> rejeitar(@PathVariable Long id, @RequestParam(required = false) String usuarioAnalista, @RequestParam(required = false) String observacao) {
        return ResponseEntity.ok(onboardingPJService.rejeitar(id, usuarioAnalista, observacao));
    }

    @GetMapping("/{id}/socios")
    @Operation(summary = "Listar socios da solicitacao PJ")
    public ResponseEntity<List<Participante>> listarSocios(@PathVariable Long id) {
        return ResponseEntity.ok(onboardingPJService.listarSocios(id));
    }

    @PostMapping("/{id}/documentos/{documentoId}/validar")
    @Operation(summary = "Validar ou rejeitar um documento da solicitacao PJ")
    public ResponseEntity<Void> validarDocumento(@PathVariable Long id, @PathVariable Long documentoId, @RequestBody Map<String, Object> body) {
        boolean validado = Boolean.TRUE.equals(body.get("validado"));
        String observacao = (String) body.getOrDefault("observacao", null);
        onboardingPJService.validarDocumento(id, documentoId, validado, observacao);
        return ResponseEntity.noContent().build();
    }

    @java.lang.SuppressWarnings("all")
    public ControllerPJ(final OnboardingPJService onboardingPJService) {
        this.onboardingPJService = onboardingPJService;
    }
}
