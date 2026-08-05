package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.dto.InstituicaoDTO;
import com.aurix.platform.platform.service.InstituicaoService;
import com.aurix.platform.platform.service.ProvisioningService;
import com.aurix.platform.platform.entity.Instituicao;
import com.aurix.platform.platform.repository.InstituicaoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/platform/instituicoes")
public class InstituicaoController {
    private final InstituicaoService instituicaoService;
    private final InstituicaoRepository instituicaoRepository;
    private final ProvisioningService provisioningService;

    @GetMapping
    public List<InstituicaoDTO> listar() {
        return instituicaoService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstituicaoDTO> buscarPorId(@PathVariable Long id) {
        InstituicaoDTO dto = instituicaoService.buscarPorId(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<InstituicaoDTO> buscarPorTenantId(@PathVariable String tenantId) {
        InstituicaoDTO dto = instituicaoService.buscarPorTenantId(tenantId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<InstituicaoDTO> criar(@Valid @RequestBody InstituicaoDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(instituicaoService.criar(dto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstituicaoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody InstituicaoDTO dto) {
        try {
            InstituicaoDTO updated = instituicaoService.atualizar(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return e.getMessage().contains("nao encontrada") ? ResponseEntity.notFound().build() : ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/provisionar")
    public ResponseEntity<ProvisioningService.ProvisioningResult> provisionar(@PathVariable Long id) {
        Instituicao instituicao = instituicaoRepository.findById(id).orElse(null);
        if (instituicao == null) return ResponseEntity.notFound().build();
        ProvisioningService.ProvisioningResult result = provisioningService.provisionar(instituicao);
        return ResponseEntity.ok(result);
    }

    @java.lang.SuppressWarnings("all")
    public InstituicaoController(final InstituicaoService instituicaoService, final InstituicaoRepository instituicaoRepository, final ProvisioningService provisioningService) {
        this.instituicaoService = instituicaoService;
        this.instituicaoRepository = instituicaoRepository;
        this.provisioningService = provisioningService;
    }
}
