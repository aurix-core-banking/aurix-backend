package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.entity.ParceiroCustodia;
import com.aurix.platform.platform.repository.ParceiroCustodiaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/platform/parceiros")
public class ParceiroController {
    private final ParceiroCustodiaRepository parceiroRepository;

    @PostMapping
    public ResponseEntity<ParceiroCustodia> criar(@RequestHeader("X-Tenant-Id") String tenantId, @RequestBody Map<String, String> body) {
        String clientId = body.get("clientId");
        String nome = body.get("nome");
        if (clientId == null || nome == null) return ResponseEntity.badRequest().build();
        if (parceiroRepository.findByTenantIdAndClientId(tenantId, clientId).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        ParceiroCustodia p = parceiroRepository.save(ParceiroCustodia.builder().tenantId(tenantId).clientId(clientId).nome(nome).ativo(true).build());
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    @GetMapping
    public List<ParceiroCustodia> listar(@RequestHeader("X-Tenant-Id") String tenantId) {
        return parceiroRepository.findAll().stream().filter(p -> tenantId.equals(p.getTenantId())).toList();
    }

    @java.lang.SuppressWarnings("all")
    public ParceiroController(final ParceiroCustodiaRepository parceiroRepository) {
        this.parceiroRepository = parceiroRepository;
    }
}
