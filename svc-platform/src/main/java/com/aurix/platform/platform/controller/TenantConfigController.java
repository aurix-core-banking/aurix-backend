package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.dto.TenantConfigDTO;
import com.aurix.platform.platform.service.TenantConfigService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/platform/tenants/{tenantId}/config")
public class TenantConfigController {
    private final TenantConfigService tenantConfigService;

    @GetMapping
    public ResponseEntity<TenantConfigDTO> buscar(@PathVariable String tenantId) {
        TenantConfigDTO dto = tenantConfigService.buscarPorTenantId(tenantId);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<TenantConfigDTO> salvar(@PathVariable String tenantId, @Valid @RequestBody TenantConfigDTO dto) {
        dto.setTenantId(tenantId);
        return ResponseEntity.ok(tenantConfigService.salvar(tenantId, dto));
    }

    @java.lang.SuppressWarnings("all")
    public TenantConfigController(final TenantConfigService tenantConfigService) {
        this.tenantConfigService = tenantConfigService;
    }
}
