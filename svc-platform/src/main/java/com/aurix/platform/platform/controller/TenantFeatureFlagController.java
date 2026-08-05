package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.dto.TenantFeatureFlagDTO;
import com.aurix.platform.platform.entity.TenantFeatureFlag;
import com.aurix.platform.platform.service.TenantFeatureFlagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/platform/tenants/{tenantId}/features")
@SuppressWarnings({"PMD.SimplifiedTernary"})
public class TenantFeatureFlagController {
    private final TenantFeatureFlagService service;

    @GetMapping
    public List<TenantFeatureFlagDTO> list(@PathVariable String tenantId) {
        return service.listByTenant(tenantId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{featureKey}")
    public ResponseEntity<TenantFeatureFlagDTO> get(@PathVariable String tenantId, @PathVariable String featureKey) {
        return service.get(tenantId, featureKey).map(this::toDto).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{featureKey}/enabled")
    public ResponseEntity<Boolean> isEnabled(@PathVariable String tenantId, @PathVariable String featureKey) {
        return ResponseEntity.ok(service.isEnabled(tenantId, featureKey));
    }

    @PutMapping("/{featureKey}")
    public TenantFeatureFlagDTO set(@PathVariable String tenantId, @PathVariable String featureKey, @RequestBody TenantFeatureFlagDTO dto) {
        boolean enabled = dto.getEnabled() != null ? dto.getEnabled() : true;
        TenantFeatureFlag flag = service.set(tenantId, featureKey, enabled, dto.getDescricao());
        return toDto(flag);
    }

    @DeleteMapping("/{featureKey}")
    public ResponseEntity<Void> delete(@PathVariable String tenantId, @PathVariable String featureKey) {
        service.delete(tenantId, featureKey);
        return ResponseEntity.noContent().build();
    }

    private TenantFeatureFlagDTO toDto(TenantFeatureFlag f) {
        return TenantFeatureFlagDTO.builder().tenantId(f.getTenantId()).featureKey(f.getFeatureKey()).enabled(f.getEnabled()).descricao(f.getDescricao()).build();
    }

    @java.lang.SuppressWarnings("all")
    public TenantFeatureFlagController(final TenantFeatureFlagService service) {
        this.service = service;
    }
}
