package com.aurix.platform.credit.seguro.controller;

import com.aurix.platform.credit.seguro.dto.request.AbrirSinistroRequest;
import com.aurix.platform.credit.seguro.dto.request.ContratarSeguroRequest;
import com.aurix.platform.credit.seguro.dto.response.CoberturaResponse;
import com.aurix.platform.credit.seguro.dto.response.SeguroPrestamistaResponse;
import com.aurix.platform.credit.seguro.dto.response.SinistroResponse;
import com.aurix.platform.credit.seguro.service.SeguroPrestamistaService;
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
@RequestMapping("/api/credito/seguro")
@Tag(name = "Seguro Prestamista", description = "Seguro prestamista vinculado a contratos de crédito")
public class SeguroPrestamistaController {

    private final SeguroPrestamistaService seguroService;

    public SeguroPrestamistaController(SeguroPrestamistaService seguroService) {
        this.seguroService = seguroService;
    }

    @PostMapping
    @Operation(summary = "Contratar seguro prestamista")
    public ResponseEntity<SeguroPrestamistaResponse> contratar(
            @Valid @RequestBody ContratarSeguroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(seguroService.contratar(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar seguro por ID")
    public ResponseEntity<SeguroPrestamistaResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(seguroService.buscarPorId(id));
    }

    @GetMapping("/contrato/{contratoId}")
    @Operation(summary = "Listar seguros do contrato")
    public ResponseEntity<List<SeguroPrestamistaResponse>> listarPorContrato(
            @PathVariable Long contratoId) {
        return ResponseEntity.ok(seguroService.listarPorContrato(contratoId));
    }

    @PostMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar seguro prestamista")
    public ResponseEntity<SeguroPrestamistaResponse> cancelar(
            @PathVariable Long id,
            @RequestParam(required = false) String motivo) {
        return ResponseEntity.ok(seguroService.cancelar(id, motivo));
    }

    @GetMapping("/contrato/{contratoId}/cobertura")
    @Operation(summary = "Verificar cobertura do seguro")
    public ResponseEntity<CoberturaResponse> verificarCobertura(@PathVariable Long contratoId) {
        return ResponseEntity.ok(seguroService.verificarCobertura(contratoId));
    }

    @PostMapping("/{seguroId}/sinistro")
    @Operation(summary = "Abrir sinistro do seguro")
    public ResponseEntity<SinistroResponse> abrirSinistro(
            @PathVariable Long seguroId,
            @Valid @RequestBody AbrirSinistroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            seguroService.abrirSinistro(seguroId, request));
    }

    @GetMapping("/{seguroId}/sinistros")
    @Operation(summary = "Listar sinistros do seguro")
    public ResponseEntity<List<SinistroResponse>> listarSinistros(@PathVariable Long seguroId) {
        return ResponseEntity.ok(seguroService.listarSinistros(seguroId));
    }
}
