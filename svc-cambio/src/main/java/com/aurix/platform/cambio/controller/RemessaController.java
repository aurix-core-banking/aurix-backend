package com.aurix.platform.cambio.controller;

import com.aurix.platform.cambio.dto.RemessaRequest;
import com.aurix.platform.cambio.dto.RemessaResponse;
import com.aurix.platform.cambio.service.RemessaService;
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
@RequestMapping("/api/cambio/remessas")
@Tag(name = "Remessas Cambio")
public class RemessaController {

    private final RemessaService remessaService;

    public RemessaController(RemessaService remessaService) {
        this.remessaService = remessaService;
    }

    @PostMapping
    public ResponseEntity<RemessaResponse> solicitarRemessa(@Valid @RequestBody RemessaRequest request) {
        var response = remessaService.solicitarRemessa(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RemessaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(remessaService.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<RemessaResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(remessaService.listarPorCliente(clienteId));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        remessaService.cancelar(id);
        return ResponseEntity.ok().build();
    }
}
