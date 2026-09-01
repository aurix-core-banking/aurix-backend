package com.aurix.platform.customer.integration;

import com.aurix.platform.customer.onboarding.service.FraudService;
import org.springframework.stereotype.Component;

/**
 * Cliente de análise de fraude (ClearSale).
 * <p>
 * Delega para o {@link FraudService} ativo (stub em dev/test; integração real
 * planejada na Fase 2.2). Retorna o score de risco como inteiro (0–100).
 */
@Component
public class FraudClient {

    private final FraudService fraudService;

    public FraudClient(FraudService fraudService) {
        this.fraudService = fraudService;
    }

    public int analisarRisco(String cpf, String nome, String ip, String dispositivo) {
        var resultado = fraudService.analisar(cpf, nome, null, null);
        return resultado.risco();
    }
}