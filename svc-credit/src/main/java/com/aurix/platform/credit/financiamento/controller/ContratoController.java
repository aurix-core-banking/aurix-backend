package com.aurix.platform.credit.financiamento.controller;

import com.aurix.platform.credit.financiamento.dto.request.CriarContratoRequest;
import com.aurix.platform.credit.financiamento.dto.request.RenegociarRequest;
import com.aurix.platform.credit.financiamento.dto.response.ContratoResponse;
import com.aurix.platform.credit.financiamento.dto.response.ContratoResumoResponse;
import com.aurix.platform.credit.financiamento.service.ContratoFinanciamentoService;
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
@RequestMapping("/api/financiamento/contratos")
@Tag(name = "Contrato")
public class ContratoController {

    private final ContratoFinanciamentoService contratoService;

    public ContratoController(ContratoFinanciamentoService contratoService) {
        this.contratoService = contratoService;
    }

    @PostMapping
    public ResponseEntity<ContratoResponse> contratar(@Valid @RequestBody CriarContratoRequest request) {
        var response = contratoService.contratar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ContratoResumoResponse>> listar(@PathVariable Long clienteId) {
        return ResponseEntity.ok(contratoService.listarPorCliente(clienteId));
    }

    @PatchMapping("/{id}/liquidar")
    public ResponseEntity<Void> liquidar(@PathVariable Long id) {
        contratoService.liquidar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/renegociar")
    public ResponseEntity<ContratoResponse> renegociar(@PathVariable Long id,
                                                       @Valid @RequestBody RenegociarRequest request) {
        return ResponseEntity.ok(contratoService.renegociar(id, request));
    }
}
