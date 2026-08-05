package com.aurix.platform.ai.controller.ml;

import com.aurix.platform.ai.client.MlGrpcClient;
import com.aurix.platform.ml.v1.FraudAnalysisRequest;
import com.aurix.platform.ml.v1.FraudAnalysisResponse;
import com.aurix.platform.shared.dto.ml.FraudAnalysisRequestDTO;
import com.aurix.platform.shared.dto.ml.FraudAnalysisResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai/ml/fraud")
@Tag(name = "ML Fraud Detection")
public class FraudController {

    private final MlGrpcClient mlClient;

    public FraudController(MlGrpcClient mlClient) {
        this.mlClient = mlClient;
    }

    @PostMapping("/analyze")
    @Operation(summary = "Analyze transaction for fraud")
    public FraudAnalysisResponseDTO analyze(@RequestBody FraudAnalysisRequestDTO request) {
        FraudAnalysisRequest.Builder builder = FraudAnalysisRequest.newBuilder();
        if (request.getTransactionId() != null) builder.setTransactionId(request.getTransactionId());
        if (request.getAccountId() != null) builder.setAccountId(request.getAccountId());
        if (request.getTransactionType() != null) builder.setTransactionType(request.getTransactionType());
        if (request.getAmount() != null) builder.setAmount(request.getAmount().doubleValue());
        if (request.getCurrency() != null) builder.setCurrency(request.getCurrency());
        if (request.getChannel() != null) builder.setChannel(request.getChannel());
        if (request.getDeviceId() != null) builder.setDeviceId(request.getDeviceId());
        if (request.getIpAddress() != null) builder.setIpAddress(request.getIpAddress());
        if (request.getUserAgent() != null) builder.setUserAgent(request.getUserAgent());
        if (request.getCity() != null) builder.setCity(request.getCity());
        if (request.getState() != null) builder.setState(request.getState());
        if (request.getTimestamp() != null) builder.setTimestamp(request.getTimestamp());
        if (request.getMetadata() != null) builder.putAllMetadata(request.getMetadata());

        FraudAnalysisResponse protoResponse = mlClient.analyzeTransaction(builder.build());
        return toDto(protoResponse);
    }

    @PostMapping("/analyze/transaction")
    @Operation(summary = "Analyze transaction from raw data")
    public FraudAnalysisResponseDTO analyzeFromData(@RequestBody FraudAnalysisRequestDTO request) {
        return analyze(request);
    }

    private FraudAnalysisResponseDTO toDto(FraudAnalysisResponse proto) {
        FraudAnalysisResponseDTO dto = new FraudAnalysisResponseDTO();
        dto.setFraudScore(proto.getFraudScore());
        dto.setAnomalyScore(proto.getAnomalyScore());
        dto.setSupervisedScore(proto.getSupervisedScore());
        dto.setRiskLevel(proto.getRiskLevel());
        dto.setRedFlags(List.copyOf(proto.getRedFlagsList()));
        dto.setBlockTransaction(proto.getBlockTransaction());
        dto.setRecommendation(proto.getRecommendation());
        dto.setDecisionId(proto.getDecisionId());
        dto.setProcessingTimeMs(proto.getProcessingTimeMs());
        return dto;
    }
}
