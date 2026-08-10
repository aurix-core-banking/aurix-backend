package com.aurix.platform.contracts.controller;

import com.aurix.platform.contracts.dto.TemplateRequest;
import com.aurix.platform.contracts.dto.TemplateResponse;
import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.entity.TemplateContrato;
import com.aurix.platform.contracts.service.TemplateContratoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/contracts/templates")
@Tag(name = "contracts", description = "Gestão de contratos (svc-contracts)")
public class TemplateContratoController {

    private final TemplateContratoService templateService;

    public TemplateContratoController(TemplateContratoService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    @Operation(summary = "Criar template de contrato")
    public ResponseEntity<TemplateResponse> criar(@Valid @RequestBody TemplateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(TemplateResponse.de(templateService.criar(request)));
    }

    @GetMapping
    @Operation(summary = "Listar templates de contrato")
    public ResponseEntity<List<TemplateResponse>> listar(
            @RequestParam(required = false) Contrato.TipoContrato tipo,
            @RequestParam(required = false) TemplateContrato.StatusTemplate status) {
        return ResponseEntity.ok(templateService.listar(tipo, status).stream()
            .map(TemplateResponse::de)
            .toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar template por id")
    public ResponseEntity<TemplateResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(TemplateResponse.de(templateService.buscarEntidade(id)));
    }

    @GetMapping("/codigo/{codigo}")
    @Operation(summary = "Buscar template por código")
    public ResponseEntity<TemplateResponse> buscarPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(TemplateResponse.de(templateService.buscarPorCodigo(codigo)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar template de contrato")
    public ResponseEntity<TemplateResponse> atualizar(@PathVariable Long id,
                                                      @Valid @RequestBody TemplateRequest request) {
        return ResponseEntity.ok(TemplateResponse.de(templateService.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inativar template de contrato")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        templateService.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/gerar")
    @Operation(summary = "Gerar documento do contrato a partir do template")
    public ResponseEntity<String> gerarDocumento(@PathVariable Long id, @RequestParam Long contratoId) {
        return ResponseEntity.ok(templateService.gerarDocumento(id, contratoId));
    }
}
