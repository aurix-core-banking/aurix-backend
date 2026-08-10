package com.aurix.platform.contracts.controller;

import com.aurix.platform.contracts.dto.AssinanteRequest;
import com.aurix.platform.contracts.dto.AssinaturaRequest;
import com.aurix.platform.contracts.dto.AssinaturaResponse;
import com.aurix.platform.contracts.dto.ContratoRequest;
import com.aurix.platform.contracts.dto.ContratoResponse;
import com.aurix.platform.contracts.dto.ContratoVersaoResponse;
import com.aurix.platform.contracts.entity.AssinaturaContrato;
import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.service.AssinaturaService;
import com.aurix.platform.contracts.service.ContratoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@Tag(name = "contracts", description = "Gestão de contratos (svc-contracts)")
public class ContratoController {

    private final ContratoService contratoService;
    private final AssinaturaService assinaturaService;

    public ContratoController(ContratoService contratoService, AssinaturaService assinaturaService) {
        this.contratoService = contratoService;
        this.assinaturaService = assinaturaService;
    }

    @PostMapping("/contratos")
    @Operation(summary = "Criar contrato")
    public ResponseEntity<ContratoResponse> criar(@Valid @RequestBody ContratoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ContratoResponse.de(contratoService.criar(request)));
    }

    @GetMapping("/contratos")
    @Operation(summary = "Listar contratos")
    public ResponseEntity<List<ContratoResponse>> listar(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) Contrato.StatusContrato status) {
        return ResponseEntity.ok(contratoService.listar(clienteId, status).stream()
            .map(ContratoResponse::de)
            .toList());
    }

    @GetMapping("/contratos/{id}")
    @Operation(summary = "Buscar contrato por id")
    public ResponseEntity<ContratoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ContratoResponse.de(contratoService.buscarEntidade(id)));
    }

    @GetMapping("/contratos/numero/{numero}")
    @Operation(summary = "Buscar contrato por número")
    public ResponseEntity<ContratoResponse> buscarPorNumero(@PathVariable String numero) {
        return ResponseEntity.ok(ContratoResponse.de(contratoService.buscarPorNumero(numero)));
    }

    @GetMapping("/contratos/cliente/{clienteId}")
    @Operation(summary = "Listar contratos do cliente")
    public ResponseEntity<List<ContratoResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(contratoService.listar(clienteId, null).stream()
            .map(ContratoResponse::de)
            .toList());
    }

    @PutMapping("/contratos/{id}")
    @Operation(summary = "Atualizar contrato criando nova versão")
    public ResponseEntity<ContratoResponse> atualizar(@PathVariable Long id,
                                                      @Valid @RequestBody ContratoRequest request,
                                                      @RequestParam(required = false) String motivo) {
        return ResponseEntity.ok(ContratoResponse.de(
            contratoService.atualizar(id, request, motivo == null ? "Atualização do contrato" : motivo)));
    }

    @GetMapping("/contratos/{id}/versoes")
    @Operation(summary = "Listar histórico de versões do contrato")
    public ResponseEntity<List<ContratoVersaoResponse>> versoes(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.listarVersoes(id));
    }

    @PostMapping("/contratos/{id}/assinaturas/enviar")
    @Operation(summary = "Iniciar fluxo de assinatura digital")
    public ResponseEntity<List<AssinaturaResponse>> iniciarAssinatura(@PathVariable Long id,
                                                                      @Valid @RequestBody List<AssinanteRequest> assinantes) {
        List<AssinaturaContrato> assinaturas = assinaturaService.iniciarFluxo(id, assinantes);
        return ResponseEntity.ok(assinaturas.stream().map(AssinaturaResponse::de).toList());
    }

    @GetMapping("/contratos/{id}/assinaturas")
    @Operation(summary = "Listar assinaturas do contrato")
    public ResponseEntity<List<AssinaturaResponse>> listarAssinaturas(@PathVariable Long id) {
        return ResponseEntity.ok(assinaturaService.listarAssinaturas(id));
    }

    @PostMapping("/contratos/{id}/assinaturas/{documento}/registrar")
    @Operation(summary = "Registrar assinatura digital do assinante")
    public ResponseEntity<AssinaturaResponse> registrarAssinatura(@PathVariable Long id,
                                                                  @PathVariable String documento,
                                                                  @RequestBody AssinaturaRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(AssinaturaResponse.de(
            assinaturaService.registrarAssinatura(id, documento, request)));
    }

    @PatchMapping("/contratos/{id}/liquidar")
    @Operation(summary = "Liquidar contrato")
    public ResponseEntity<ContratoResponse> liquidar(@PathVariable Long id) {
        return ResponseEntity.ok(ContratoResponse.de(contratoService.liquidar(id)));
    }

    @PatchMapping("/contratos/{id}/cancelar")
    @Operation(summary = "Cancelar contrato")
    public ResponseEntity<ContratoResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(ContratoResponse.de(contratoService.cancelar(id)));
    }
}
