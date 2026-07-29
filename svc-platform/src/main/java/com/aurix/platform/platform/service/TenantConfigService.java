package com.aurix.platform.platform.service;

import com.aurix.platform.platform.dto.TenantConfigDTO;
import com.aurix.platform.platform.entity.TenantConfig;
import com.aurix.platform.platform.repository.TenantConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantConfigService {
    private final TenantConfigRepository tenantConfigRepository;

    @Transactional(readOnly = true)
    public TenantConfigDTO buscarPorTenantId(String tenantId) {
        return tenantConfigRepository.findByTenantId(tenantId).map(TenantConfigDTO::from).orElse(null);
    }

    @Transactional
    public TenantConfigDTO salvar(String tenantId, TenantConfigDTO dto) {
        TenantConfig e = tenantConfigRepository.findByTenantId(tenantId).orElse(TenantConfig.builder().tenantId(tenantId).build());
        e.setLogoUrl(dto.getLogoUrl());
        e.setCorPrimaria(dto.getCorPrimaria());
        e.setCorSecundaria(dto.getCorSecundaria());
        e.setTermosUsoUrl(dto.getTermosUsoUrl());
        e.setLimites(dto.getLimites());
        e.setProdutosHabilitados(dto.getProdutosHabilitados());
        return TenantConfigDTO.from(tenantConfigRepository.save(e));
    }

    @java.lang.SuppressWarnings("all")
    public TenantConfigService(final TenantConfigRepository tenantConfigRepository) {
        this.tenantConfigRepository = tenantConfigRepository;
    }
}
