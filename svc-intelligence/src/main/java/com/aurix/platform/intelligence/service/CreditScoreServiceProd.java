package com.aurix.platform.intelligence.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Profile("prod")
public class CreditScoreServiceProd implements CreditScoreService {

    @Override
    public Map<String, Object> obterScore(String clienteId) {
        return Map.of(
            "clienteId", clienteId,
            "score", 500,
            "faixa", "MEDIO_RISCO",
            "modelo", "prod-placeholder"
        );
    }
}
