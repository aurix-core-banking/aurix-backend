package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.DeploymentProfile;
import com.aurix.platform.platform.entity.Instituicao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "aurix.provisioning.provider", havingValue = "stub", matchIfMissing = true)
public class ProvisioningServiceStub implements ProvisioningService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ProvisioningServiceStub.class);

    @Override
    public ProvisioningResult provisionar(Instituicao instituicao) {
        log.info("Provisioning stub: tenant={}, tenancy={}, cloud={}, topology={}", instituicao.getTenantId(), instituicao.getDeploymentProfile() != null ? instituicao.getDeploymentProfile().getTenancy() : null, instituicao.getDeploymentProfile() != null ? instituicao.getDeploymentProfile().getCloud() : null, instituicao.getDeploymentProfile() != null ? instituicao.getDeploymentProfile().getTopology() : null);
        if (instituicao.getDeploymentProfile() == null) {
            return new ProvisioningResult(false, "Deployment profile obrigatorio", null);
        }
        if (instituicao.getDeploymentProfile().getTenancy() == DeploymentProfile.TenancyType.MULTI_TENANT) {
            String dbUrl = "jdbc:postgresql://localhost:5432/aurix_tenant_" + instituicao.getTenantId().replaceAll("[^a-zA-Z0-9_]", "_");
            return new ProvisioningResult(true, "Stub: banco por tenant nao criado; configurar provider real para criar DB", dbUrl);
        }
        return new ProvisioningResult(true, "Stub: self-hosted usa banco unico; nenhuma acao", null);
    }
}
