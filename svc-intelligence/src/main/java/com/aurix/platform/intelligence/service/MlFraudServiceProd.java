package com.aurix.platform.intelligence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@Profile("prod")
public class MlFraudServiceProd implements FraudService {

    private static final Logger log = LoggerFactory.getLogger(MlFraudServiceProd.class);

    private final RestClient mlRestClient;

    public MlFraudServiceProd(RestClient mlRestClient) {
        this.mlRestClient = mlRestClient;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> avaliarFraude(Map<String, Object> transacao) {
        try {
            Map<String, Object> resposta = mlRestClient.post()
                    .uri("/predict")
                    .body(Map.of("transactions", List.of(transacao)))
                    .retrieve()
                    .body(Map.class);

            List<Integer> predictions = (List<Integer>) resposta.get("predictions");
            List<Double> scores = (List<Double>) resposta.get("scores");

            boolean aprovado = predictions == null || predictions.isEmpty() || predictions.get(0) == 0;
            double riscoFraude = (scores == null || scores.isEmpty()) ? 0.0 : scores.get(0);

            return Map.of(
                    "riscoFraude", riscoFraude,
                    "aprovado", aprovado,
                    "modelo", resposta.getOrDefault("model_version", "ml-service")
            );
        } catch (Exception e) {
            log.warn("Falha ao comunicar com ML service para deteccao de fraude: {}", e.getMessage());
            return Map.of(
                    "riscoFraude", 0.0,
                    "aprovado", true,
                    "modelo", "indisponivel"
            );
        }
    }
}
