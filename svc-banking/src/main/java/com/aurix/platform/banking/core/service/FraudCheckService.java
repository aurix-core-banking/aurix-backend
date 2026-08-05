package com.aurix.platform.banking.core.service;

import com.aurix.platform.shared.dto.ml.FraudAnalysisRequestDTO;
import com.aurix.platform.shared.dto.ml.FraudAnalysisResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class FraudCheckService {

    private static final Logger log = LoggerFactory.getLogger(FraudCheckService.class);

    private final RestTemplate restTemplate;
    private final String fraudEndpoint;

    public FraudCheckService(RestTemplate restTemplate,
                             @Value("${aurix.services.ai.url:http://localhost:8082}") String aiUrl) {
        this.restTemplate = restTemplate;
        this.fraudEndpoint = aiUrl + "/api/ai/ml/fraud/analyze";
    }

    public Optional<FraudAnalysisResponseDTO> analyze(FraudAnalysisRequestDTO request) {
        try {
            FraudAnalysisResponseDTO response = restTemplate.postForObject(
                    fraudEndpoint, request, FraudAnalysisResponseDTO.class);
            log.info("Fraud analysis result: riskLevel={}, fraudScore={}, blocked={}",
                    response != null ? response.getRiskLevel() : "UNKNOWN",
                    response != null ? response.getFraudScore() : -1,
                    response != null ? response.isBlockTransaction() : false);
            return Optional.ofNullable(response);
        } catch (Exception e) {
            log.warn("Fraud analysis call failed (proceeding without blocking): {}", e.getMessage());
            return Optional.empty();
        }
    }

    public boolean isBlocked(FraudAnalysisRequestDTO request) {
        Optional<FraudAnalysisResponseDTO> result = analyze(request);
        return result.isPresent()
                && result.get().isBlockTransaction();
    }
}
