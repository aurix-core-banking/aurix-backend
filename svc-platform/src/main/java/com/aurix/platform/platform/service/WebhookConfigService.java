package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.WebhookConfig;
import com.aurix.platform.platform.repository.WebhookConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@SuppressWarnings({"PMD.SimplifiedTernary"})
public class WebhookConfigService {
    private final WebhookConfigRepository configRepository;

    @Transactional(readOnly = true)
    public WebhookConfig buscarPorTenant(String tenantId) {
        return configRepository.findByTenantId(tenantId).orElse(null);
    }

    @Transactional
    public WebhookConfig salvar(String tenantId, String url, List<String> eventos, Boolean ativo, String secret) {
        WebhookConfig c = configRepository.findByTenantId(tenantId).orElse(WebhookConfig.builder().tenantId(tenantId).build());
        c.setUrl(url);
        c.setEventos(eventos);
        c.setAtivo(ativo != null ? ativo : true);
        c.setSecret(secret);
        return configRepository.save(c);
    }

    @java.lang.SuppressWarnings("all")
    public WebhookConfigService(final WebhookConfigRepository configRepository) {
        this.configRepository = configRepository;
    }
}
