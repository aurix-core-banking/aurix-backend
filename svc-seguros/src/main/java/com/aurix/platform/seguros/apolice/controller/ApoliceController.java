package com.aurix.platform.seguros.apolice.controller;

import com.aurix.platform.seguros.apolice.dto.ApoliceRequest;
import com.aurix.platform.seguros.apolice.dto.ApoliceResponse;
import com.aurix.platform.seguros.apolice.service.ApoliceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seguros/apolices")
@Tag(name = "Apólice")
public class ApoliceController {

    private final ApoliceService apoliceService;

    public ApoliceController(ApoliceService apoliceService) {
        this.apoliceService = apoliceService;
    }

    @PostMapping
    public ResponseEntity<ApoliceResponse> contratar(@Valid @RequestBody ApoliceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(apoliceService.contratar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApoliceResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(apoliceService.buscarPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ApoliceResponse>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(apoliceService.listarPorCliente(clienteId));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ApoliceResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(apoliceService.cancelar(id));
    }
}
