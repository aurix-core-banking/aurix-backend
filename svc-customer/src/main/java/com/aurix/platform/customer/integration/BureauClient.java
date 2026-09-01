package com.aurix.platform.customer.integration;

import com.aurix.platform.customer.onboarding.service.BureauProvider;
import org.springframework.stereotype.Component;

/**
 * Cliente de consulta a bureau de crédito (Serasa/SPC/Quod).
 * <p>
 * Delega para o {@link BureauProvider} ativo (stub em dev/test; provedor real
 * planejado na Fase 2.2). Retorna o score como String para o workflow de onboarding.
 */
@Component
public class BureauClient {

    private final BureauProvider bureauProvider;

    public BureauClient(BureauProvider bureauProvider) {
        this.bureauProvider = bureauProvider;
    }

    public String consultarScore(String cpf) {
        var resultado = bureauProvider.consultar(cpf);
        return resultado.score() != null ? resultado.score().toString() : null;
    }
}