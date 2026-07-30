package com.aurix.platform.ai.controller.ml;

import com.aurix.platform.ai.client.MlGrpcClient;
import com.aurix.platform.ml.v1.FraudAnalysisRequest;
import com.aurix.platform.ml.v1.FraudAnalysisResponse;
import com.aurix.platform.shared.dto.ml.FraudAnalysisRequestDTO;
import com.aurix.platform.shared.dto.ml.FraudAnalysisResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
        if (request.getIpAddress() != null) builder.setIpAddress(request.getIpAddress());
        if (request.getDeviceId() != null) builder.setDeviceId(request.getDeviceId());
        if (request.getLocation() != null) builder.setLocation(request.getLocation());
        if (request.getMetadata() != null) builder.putAllMetadata(request.getMetadata());

        FraudAnalysisResponse protoResponse = mlClient.analyzeTransaction(builder.build());
        return toDto(protoResponse);
    }

    @PostMapping("/analyze/transaction")
    @Operation(summary = "Analyze transaction from raw data")
    public FraudAnalysisResponseDTO analyzeFromData(@RequestBody Map<String, Object> data) {
        FraudAnalysisRequest.Builder builder = FraudAnalysisRequest.newBuilder();
        if (data.containsKey("transactionId")) builder.setTransactionId((String) data.get("transactionId"));
        if (data.containsKey("accountId")) builder.setAccountId((String) data.get("accountId"));
        if (data.containsKey("transactionType")) builder.setTransactionType((String) data.get("transactionType"));
        if (data.containsKey("amount")) builder.setAmount(((Number) data.get("amount")).doubleValue());
        if (data.containsKey("channel")) builder.setChannel((String) data.get("channel"));
        if (data.containsKey("ipAddress")) builder.setIpAddress((String) data.get("ipAddress"));
        FraudAnalysisResponse protoResponse = mlClient.analyzeTransaction(builder.build());
        return toDto(protoResponse);
    }

    private FraudAnalysisResponseDTO toDto(FraudAnalysisResponse proto) {
        FraudAnalysisResponseDTO dto = new FraudAnalysisResponseDTO();
        dto.setFraud(proto.getIsFraud());
        dto.setRiskScore(proto.getRiskScore());
        if (proto.getAction() != null) {
            dto.setAction(FraudAnalysisResponseDTO.FraudAction.valueOf(proto.getAction().name()));
        }
        if (proto.getReason() != null && !proto.getReason().isEmpty()) {
            dto.setReason(proto.getReason());
        }
        Map<String, String> details = new HashMap<>();
        details.putAll(proto.getDetailsMap());
        dto.setDetails(details);
        return dto;
    }
}
