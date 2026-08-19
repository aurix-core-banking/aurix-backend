package com.aurix.platform.credit.renegociacao.controller;

import com.aurix.platform.credit.renegociacao.dto.request.CriarRenegociacaoRequest;
import com.aurix.platform.credit.renegociacao.dto.response.RenegociacaoResponse;
import com.aurix.platform.credit.renegociacao.service.RenegociacaoService;
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
@RequestMapping("/api/credito/renegociacao")
@Tag(name = "Renegociação", description = "Renegociação de contratos de crédito")
public class RenegociacaoController {

    private final RenegociacaoService renegociacaoService;

    public RenegociacaoController(RenegociacaoService renegociacaoService) {
        this.renegociacaoService = renegociacaoService;
    }

    @PostMapping
    @Operation(summary = "Criar renegociação de crédito")
    public ResponseEntity<RenegociacaoResponse> criar(@Valid @RequestBody CriarRenegociacaoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(renegociacaoService.criar(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar renegociação por ID")
    public ResponseEntity<RenegociacaoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(renegociacaoService.buscarPorId(id));
    }

    @GetMapping
    @Operation(summary = "Listar renegociações do cliente")
    public ResponseEntity<List<RenegociacaoResponse>> listar(
            @RequestParam Long clienteId) {
        return ResponseEntity.ok(renegociacaoService.listarPorCliente(clienteId));
    }

    @PostMapping("/{id}/aprovar")
    @Operation(summary = "Aprovar renegociação")
    public ResponseEntity<RenegociacaoResponse> aprovar(@PathVariable Long id) {
        return ResponseEntity.ok(renegociacaoService.aprovar(id));
    }

    @PostMapping("/{id}/contratar")
    @Operation(summary = "Contratar renegociação aprovada")
    public ResponseEntity<RenegociacaoResponse> contratar(@PathVariable Long id) {
        return ResponseEntity.ok(renegociacaoService.contratar(id));
    }
}
