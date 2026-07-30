package com.aurix.platform.shared.dto.ml;

import java.util.Map;

public class FraudAnalysisResponseDTO {

    public enum FraudAction { ALLOW, BLOCK, REVIEW }

    private boolean fraud;
    private double riskScore;
    private FraudAction action;
    private String reason;
    private Map<String, String> details;

    public boolean isFraud() { return fraud; }
    public void setFraud(boolean fraud) { this.fraud = fraud; }

    public double getRiskScore() { return riskScore; }
    public void setRiskScore(double riskScore) { this.riskScore = riskScore; }

    public FraudAction getAction() { return action; }
    public void setAction(FraudAction action) { this.action = action; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public Map<String, String> getDetails() { return details; }
    public void setDetails(Map<String, String> details) { this.details = details; }
}
