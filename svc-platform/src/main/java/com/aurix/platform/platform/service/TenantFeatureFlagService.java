package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.TenantFeatureFlag;
import com.aurix.platform.platform.repository.TenantFeatureFlagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TenantFeatureFlagService {
    private final TenantFeatureFlagRepository repository;

    @Transactional(readOnly = true)
    public boolean isEnabled(String tenantId, String featureKey) {
        return repository.findByTenantIdAndFeatureKey(tenantId, featureKey).map(TenantFeatureFlag::getEnabled).orElse(false);
    }

    @Transactional(readOnly = true)
    public List<TenantFeatureFlag> listByTenant(String tenantId) {
        return repository.findByTenantId(tenantId);
    }

    @Transactional(readOnly = true)
    public Optional<TenantFeatureFlag> get(String tenantId, String featureKey) {
        return repository.findByTenantIdAndFeatureKey(tenantId, featureKey);
    }

    @Transactional
    public TenantFeatureFlag set(String tenantId, String featureKey, boolean enabled, String descricao) {
        TenantFeatureFlag flag = repository.findByTenantIdAndFeatureKey(tenantId, featureKey).orElse(TenantFeatureFlag.builder().tenantId(tenantId).featureKey(featureKey).enabled(enabled).descricao(descricao).build());
        flag.setEnabled(enabled);
        if (descricao != null) {
            flag.setDescricao(descricao);
        }
        return repository.save(flag);
    }

    @Transactional
    public void delete(String tenantId, String featureKey) {
        repository.findByTenantIdAndFeatureKey(tenantId, featureKey).ifPresent(repository::delete);
    }

    @java.lang.SuppressWarnings("all")
    public TenantFeatureFlagService(final TenantFeatureFlagRepository repository) {
        this.repository = repository;
    }
}
