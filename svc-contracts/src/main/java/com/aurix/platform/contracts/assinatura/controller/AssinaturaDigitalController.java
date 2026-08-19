package com.aurix.platform.contracts.assinatura.controller;

import com.aurix.platform.contracts.assinatura.dto.request.ConfirmarAssinaturaRequest;
import com.aurix.platform.contracts.assinatura.dto.request.GerarDocumentoRequest;
import com.aurix.platform.contracts.assinatura.dto.response.AssinaturaDigitalResponse;
import com.aurix.platform.contracts.assinatura.service.AssinaturaDigitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contratos/{contratoId}/assinatura")
@Tag(name = "Assinatura Digital", description = "Assinatura digital de contratos com ICP-Brasil")
public class AssinaturaDigitalController {

    private final AssinaturaDigitalService assinaturaDigitalService;

    public AssinaturaDigitalController(AssinaturaDigitalService assinaturaDigitalService) {
        this.assinaturaDigitalService = assinaturaDigitalService;
    }

    @PostMapping
    @Operation(summary = "Gerar documento para assinatura digital")
    public ResponseEntity<AssinaturaDigitalResponse> gerarDocumento(
            @PathVariable Long contratoId,
            @Valid @RequestBody GerarDocumentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            assinaturaDigitalService.gerarDocumento(contratoId, request));
    }

    @PostMapping("/{assinaturaId}/otp")
    @Operation(summary = "Enviar OTP para verificação (SMS/email)")
    public ResponseEntity<AssinaturaDigitalResponse> enviarOtp(@PathVariable Long contratoId,
                                                               @PathVariable Long assinaturaId) {
        return ResponseEntity.ok(assinaturaDigitalService.enviarOtp(assinaturaId));
    }

    @PostMapping("/{assinaturaId}/confirmar")
    @Operation(summary = "Confirmar assinatura (OTP + biometria)")
    public ResponseEntity<AssinaturaDigitalResponse> confirmarAssinatura(
            @PathVariable Long contratoId,
            @PathVariable Long assinaturaId,
            @Valid @RequestBody ConfirmarAssinaturaRequest request) {
        return ResponseEntity.ok(assinaturaDigitalService.confirmarAssinatura(assinaturaId, request));
    }

    @PostMapping("/{assinaturaId}/biometria")
    @Operation(summary = "Confirmar biometria separadamente")
    public ResponseEntity<AssinaturaDigitalResponse> confirmarBiometria(
            @PathVariable Long contratoId,
            @PathVariable Long assinaturaId,
            @RequestParam String tipo,
            @RequestParam String hash) {
        return ResponseEntity.ok(assinaturaDigitalService.confirmarBiometria(assinaturaId, tipo, hash));
    }

    @GetMapping
    @Operation(summary = "Consultar status da assinatura digital")
    public ResponseEntity<AssinaturaDigitalResponse> buscar(@PathVariable Long contratoId) {
        return ResponseEntity.ok(assinaturaDigitalService.buscarPorContrato(contratoId));
    }

    @GetMapping("/historico")
    @Operation(summary = "Listar histórico de assinaturas do contrato")
    public ResponseEntity<List<AssinaturaDigitalResponse>> listar(@PathVariable Long contratoId) {
        return ResponseEntity.ok(assinaturaDigitalService.listarPorContrato(contratoId));
    }
}
