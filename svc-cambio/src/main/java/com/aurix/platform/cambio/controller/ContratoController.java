package com.aurix.platform.cambio.controller;

import com.aurix.platform.cambio.dto.ContratoCambioResponse;
import com.aurix.platform.cambio.dto.FecharContratoRequest;
import com.aurix.platform.cambio.dto.LiquidarContratoRequest;
import com.aurix.platform.cambio.service.ContratoCambioService;
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
@RequestMapping("/api/cambio/contratos")
@Tag(name = "Contratos Cambio")
public class ContratoController {

    private final ContratoCambioService contratoService;

    public ContratoController(ContratoCambioService contratoService) {
        this.contratoService = contratoService;
    }

    @PostMapping
    public ResponseEntity<ContratoCambioResponse> fecharContrato(@Valid @RequestBody FecharContratoRequest request) {
        var response = contratoService.fecharContrato(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoCambioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(contratoService.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ContratoCambioResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(contratoService.listarPorCliente(clienteId));
    }

    @PatchMapping("/{id}/liquidar")
    public ResponseEntity<ContratoCambioResponse> liquidar(@PathVariable Long id,
                                                           @RequestBody(required = false) LiquidarContratoRequest request) {
        return ResponseEntity.ok(contratoService.liquidar(id, request));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        contratoService.cancelar(id);
        return ResponseEntity.ok().build();
    }
}
