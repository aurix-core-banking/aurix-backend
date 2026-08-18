package com.aurix.platform.intelligence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@Profile("prod")
public class CreditScoreServiceProd implements CreditScoreService {

    private static final Logger log = LoggerFactory.getLogger(CreditScoreServiceProd.class);

    private final RestClient mlRestClient;

    public CreditScoreServiceProd(RestClient mlRestClient) {
        this.mlRestClient = mlRestClient;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> obterScore(String clienteId) {
        try {
            Map<String, Object> resposta = mlRestClient.post()
                    .uri("/score")
                    .body(Map.of("cliente", Map.of("id_cliente", clienteId)))
                    .retrieve()
                    .body(Map.class);

            return Map.of(
                    "clienteId", clienteId,
                    "score", resposta.get("score"),
                    "faixa", mapRiskLevel((String) resposta.get("risk_level")),
                    "modelo", resposta.getOrDefault("model_version", "ml-service")
            );
        } catch (Exception e) {
            log.warn("Falha ao comunicar com ML service para score do cliente {}: {}", clienteId, e.getMessage());
            return Map.of(
                    "clienteId", clienteId,
                    "score", 500,
                    "faixa", "INDISPONIVEL",
                    "modelo", "fallback"
            );
        }
    }

    private String mapRiskLevel(String riskLevel) {
        if (riskLevel == null) return "INDISPONIVEL";
        return switch (riskLevel) {
            case "LOW" -> "BAIXO_RISCO";
            case "MEDIUM" -> "MEDIO_RISCO";
            case "HIGH" -> "ALTO_RISCO";
            default -> riskLevel;
        };
    }
}
