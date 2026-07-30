package com.aurix.platform.customer.onboarding.service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BureauGateway implements BureauService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BureauGateway.class);

    private final List<BureauProvider> providers;

    public BureauGateway(List<BureauProvider> providers) {
        this.providers = providers;
    }

    @Override
    public ResultadoBureau consultar(String cpf) {
        for (BureauProvider provider : providers) {
            try {
                log.debug("Tentando provider {}", provider.getClass().getSimpleName());
                ResultadoBureau result = provider.consultar(cpf);
                if (result != null) {
                    log.debug("Provider {} retornou score={}", provider.getClass().getSimpleName(), result.score());
                    return result;
                }
            } catch (Exception e) {
                log.warn("Provider {} falhou: {}", provider.getClass().getSimpleName(), e.getMessage());
            }
        }
        throw new IllegalStateException("Todos os provedores de consulta de CPF falharam");
    }
}
