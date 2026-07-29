package com.aurix.platform.customer.onboarding.controller;

import com.aurix.platform.customer.onboarding.dto.SolicitacaoContaRequest;
import com.aurix.platform.customer.onboarding.dto.SolicitacaoContaResponse;
import com.aurix.platform.customer.onboarding.entity.Pep;
import com.aurix.platform.customer.onboarding.entity.StatusOnboarding;
import com.aurix.platform.customer.onboarding.service.KycProviderService;
import com.aurix.platform.customer.onboarding.service.OnboardingPFService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/contas/pf")
@Tag(name = "Onboarding PF", description = "Abertura de conta PF e KYC")
public class ControllerPF {
    private final OnboardingPFService onboardingPFService;

    @PostMapping("/solicitacoes")
    @Operation(summary = "Solicitar abertura de conta PF")
    public ResponseEntity<SolicitacaoContaResponse> solicitarAberturaConta(@Valid @RequestBody SolicitacaoContaRequest request) {
        return ResponseEntity.ok(onboardingPFService.solicitarAberturaConta(request));
    }

    @GetMapping("/solicitacoes/{id}")
    @Operation(summary = "Consultar status da solicitacao PF")
    public ResponseEntity<SolicitacaoContaResponse> consultarStatus(@PathVariable Long id) {
        return onboardingPFService.buscarStatus(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/solicitacoes")
    @Operation(summary = "Listar solicitacoes PF (back office)")
    public ResponseEntity<List<SolicitacaoContaResponse>> listar(@RequestParam(required = false) List<StatusOnboarding> status) {
        return ResponseEntity.ok(onboardingPFService.listarParaBackOffice(status));
    }

    @PostMapping("/solicitacoes/{id}/aprovar")
    @Operation(summary = "Aprovar solicitacao PF (analista)")
    public ResponseEntity<SolicitacaoContaResponse> aprovar(@PathVariable Long id, @RequestParam(required = false) String usuarioAnalista, @RequestParam(required = false) String observacao) {
        return ResponseEntity.ok(onboardingPFService.aprovar(id, usuarioAnalista, observacao));
    }

    @PostMapping("/solicitacoes/{id}/rejeitar")
    @Operation(summary = "Rejeitar solicitacao PF (analista)")
    public ResponseEntity<SolicitacaoContaResponse> rejeitar(@PathVariable Long id, @RequestParam(required = false) String usuarioAnalista, @RequestParam(required = false) String observacao) {
        return ResponseEntity.ok(onboardingPFService.rejeitar(id, usuarioAnalista, observacao));
    }

    @PostMapping("/solicitacoes/{id}/kyc")
    @Operation(summary = "Enviar documentos para validacao KYC PF")
    public ResponseEntity<SolicitacaoContaResponse> enviarKyc(@PathVariable Long id, @RequestBody KycRequest body) {
        return ResponseEntity.ok(onboardingPFService.enviarParaKyc(id, body.getDocumentos(), body.getSelfieBase64()));
    }

    @PostMapping("/solicitacoes/{id}/documentos")
    @Operation(summary = "Adicionar documento à solicitacao PF")
    public ResponseEntity<Void> adicionarDocumento(@PathVariable Long id, @RequestBody Map<String, String> body) {
        onboardingPFService.adicionarDocumento(id, body.getOrDefault("tipoDocumento", "OUTRO"), body.getOrDefault("nomeArquivo", "documento"), body.get("urlStorage"));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pep/{cpf}")
    @Operation(summary = "Consultar se CPF e PEP")
    public ResponseEntity<Map<String, Boolean>> consultarPep(@PathVariable String cpf) {
        return ResponseEntity.ok(Map.of("pep", onboardingPFService.consultarPep(cpf)));
    }

    @PostMapping("/pep")
    @Operation(summary = "Registrar PEP")
    public ResponseEntity<Pep> registrarPep(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(onboardingPFService.registrarPep(body.get("cpf"), body.get("nome"), body.get("cargoOuVinculo")));
    }

    @PostMapping("/solicitacoes/{id}/documentos/{documentoId}/validar")
    @Operation(summary = "Validar ou rejeitar um documento da solicitacao PF")
    public ResponseEntity<Void> validarDocumento(@PathVariable Long id, @PathVariable Long documentoId, @RequestBody Map<String, Object> body) {
        boolean validado = Boolean.TRUE.equals(body.get("validado"));
        String observacao = (String) body.getOrDefault("observacao", null);
        onboardingPFService.validarDocumento(id, documentoId, validado, observacao);
        return ResponseEntity.noContent().build();
    }

    public static class KycRequest {
        private List<KycProviderService.DocumentoInfo> documentos;
        private String selfieBase64;

        @java.lang.SuppressWarnings("all")
        public KycRequest() {
        }

        @java.lang.SuppressWarnings("all")
        public List<KycProviderService.DocumentoInfo> getDocumentos() {
            return this.documentos;
        }

        @java.lang.SuppressWarnings("all")
        public String getSelfieBase64() {
            return this.selfieBase64;
        }

        @java.lang.SuppressWarnings("all")
        public void setDocumentos(final List<KycProviderService.DocumentoInfo> documentos) {
            this.documentos = documentos;
        }

        @java.lang.SuppressWarnings("all")
        public void setSelfieBase64(final String selfieBase64) {
            this.selfieBase64 = selfieBase64;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public boolean equals(final java.lang.Object o) {
            if (o == this) return true;
            if (!(o instanceof ControllerPF.KycRequest)) return false;
            final ControllerPF.KycRequest other = (ControllerPF.KycRequest) o;
            if (!other.canEqual((java.lang.Object) this)) return false;
            final java.lang.Object this$documentos = this.getDocumentos();
            final java.lang.Object other$documentos = other.getDocumentos();
            if (this$documentos == null ? other$documentos != null : !this$documentos.equals(other$documentos)) return false;
            final java.lang.Object this$selfieBase64 = this.getSelfieBase64();
            final java.lang.Object other$selfieBase64 = other.getSelfieBase64();
            if (this$selfieBase64 == null ? other$selfieBase64 != null : !this$selfieBase64.equals(other$selfieBase64)) return false;
            return true;
        }

        @java.lang.SuppressWarnings("all")
        protected boolean canEqual(final java.lang.Object other) {
            return other instanceof ControllerPF.KycRequest;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public int hashCode() {
            final int PRIME = 59;
            int result = 1;
            final java.lang.Object $documentos = this.getDocumentos();
            result = result * PRIME + ($documentos == null ? 43 : $documentos.hashCode());
            final java.lang.Object $selfieBase64 = this.getSelfieBase64();
            result = result * PRIME + ($selfieBase64 == null ? 43 : $selfieBase64.hashCode());
            return result;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "ControllerPF.KycRequest(documentos=" + this.getDocumentos() + ", selfieBase64=" + this.getSelfieBase64() + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public ControllerPF(final OnboardingPFService onboardingPFService) {
        this.onboardingPFService = onboardingPFService;
    }
}
