package com.aurix.platform.intelligence.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Profile("prod")
public class MlFraudServiceProd implements FraudService {

    @Override
    public Map<String, Object> avaliarFraude(Map<String, Object> transacao) {
        return Map.of(
            "riscoFraude", 0.0,
            "aprovado", true,
            "modelo", "prod-placeholder"
        );
    }
}
