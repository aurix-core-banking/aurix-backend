package com.aurix.platform.intelligence.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Profile("!prod")
public class CreditScoreStubService implements CreditScoreService {

    @Override
    public Map<String, Object> obterScore(String clienteId) {
        return Map.of(
            "clienteId", clienteId,
            "score", 750,
            "faixa", "BAIXO_RISCO",
            "modelo", "stub"
        );
    }
}
