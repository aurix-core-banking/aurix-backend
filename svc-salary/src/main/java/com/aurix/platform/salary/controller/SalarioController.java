package com.aurix.platform.salary.controller;

import com.aurix.platform.salary.dto.ContaSalarioRequest;
import com.aurix.platform.salary.dto.ContaSalarioResponse;
import com.aurix.platform.salary.dto.PortabilidadeRequest;
import com.aurix.platform.salary.dto.PortabilidadeResponse;
import com.aurix.platform.salary.service.SalarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/salario/contas")
@Tag(name = "Conta Salario", description = "Conta salario, pagamento e portabilidade")
public class SalarioController {

    private final SalarioService salarioService;

    public SalarioController(SalarioService salarioService) {
        this.salarioService = salarioService;
    }

    @PostMapping
    @Operation(summary = "Criar conta salario")
    public ResponseEntity<ContaSalarioResponse> criarConta(
            @Valid @RequestBody ContaSalarioRequest request) {
        ContaSalarioResponse response = salarioService.criarConta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta salario por ID")
    public ResponseEntity<ContaSalarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(salarioService.buscarPorId(id));
    }

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Listar contas salario por empresa")
    public ResponseEntity<List<ContaSalarioResponse>> listarPorEmpresa(
            @PathVariable Long empresaId) {
        return ResponseEntity.ok(salarioService.listarPorEmpresa(empresaId));
    }

    @PatchMapping("/{id}/bloquear")
    @Operation(summary = "Bloquear conta salario")
    public ResponseEntity<Void> bloquear(@PathVariable Long id) {
        salarioService.bloquearConta(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/rescindir")
    @Operation(summary = "Rescindir conta salario")
    public ResponseEntity<Void> rescindir(@PathVariable Long id) {
        salarioService.rescindirConta(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/portabilidade")
    @Operation(summary = "Solicitar portabilidade de salario")
    public ResponseEntity<PortabilidadeResponse> solicitarPortabilidade(
            @PathVariable Long id,
            @Valid @RequestBody PortabilidadeRequest request) {
        PortabilidadeResponse response = salarioService.solicitarPortabilidade(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/portabilidade/{portabilidadeId}/aprovar")
    @Operation(summary = "Aprovar portabilidade")
    public ResponseEntity<Void> aprovarPortabilidade(
            @PathVariable Long id,
            @PathVariable Long portabilidadeId) {
        salarioService.aprovarPortabilidade(id, portabilidadeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/portabilidade/{portabilidadeId}/cancelar")
    @Operation(summary = "Cancelar portabilidade")
    public ResponseEntity<Void> cancelarPortabilidade(
            @PathVariable Long id,
            @PathVariable Long portabilidadeId) {
        salarioService.cancelarPortabilidade(id, portabilidadeId);
        return ResponseEntity.ok().build();
    }
}
