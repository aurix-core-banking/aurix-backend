package com.aurix.platform.finance.controller;

import com.aurix.platform.finance.entity.PerfilFinanceiroCliente;
import com.aurix.platform.finance.service.PerfilFinanceiroClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/finance/perfil")
public class PerfilFinanceiroClienteController {
    private final PerfilFinanceiroClienteService service;

    @PostMapping("/{clienteId}")
    public ResponseEntity<PerfilFinanceiroCliente> criarPerfil(
            @PathVariable Long clienteId, @RequestParam String codigoCliente) {
        PerfilFinanceiroCliente perfil = service.criarPerfil(clienteId, codigoCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(perfil);
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<PerfilFinanceiroCliente> buscarPerfil(@PathVariable Long clienteId) {
        Optional<PerfilFinanceiroCliente> perfil = service.buscarPorClienteId(clienteId);
        return perfil.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{clienteId}/limite-credito")
    public ResponseEntity<PerfilFinanceiroCliente> atualizarLimiteCredito(
            @PathVariable Long clienteId, @RequestParam BigDecimal limiteCredito) {
        PerfilFinanceiroCliente perfil = service.atualizarLimiteCredito(clienteId, limiteCredito);
        return ResponseEntity.ok(perfil);
    }

    @PutMapping("/{clienteId}/score")
    public ResponseEntity<PerfilFinanceiroCliente> atualizarScore(
            @PathVariable Long clienteId, @RequestParam Integer scoreCredito) {
        PerfilFinanceiroCliente perfil = service.atualizarScore(clienteId, scoreCredito);
        return ResponseEntity.ok(perfil);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> removerPerfil(@PathVariable Long clienteId) {
        service.removerPorClienteId(clienteId);
        return ResponseEntity.noContent().build();
    }

    public PerfilFinanceiroClienteController(PerfilFinanceiroClienteService service) {
        this.service = service;
    }
}
