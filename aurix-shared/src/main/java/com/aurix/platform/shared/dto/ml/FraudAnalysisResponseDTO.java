package com.aurix.platform.shared.dto.ml;

import java.util.List;

public class FraudAnalysisResponseDTO {

    private double fraudScore;
    private double anomalyScore;
    private double supervisedScore;
    private String riskLevel;
    private List<String> redFlags;
    private boolean blockTransaction;
    private String recommendation;
    private String decisionId;
    private long processingTimeMs;

    public double getFraudScore() { return fraudScore; }
    public void setFraudScore(double fraudScore) { this.fraudScore = fraudScore; }

    public double getAnomalyScore() { return anomalyScore; }
    public void setAnomalyScore(double anomalyScore) { this.anomalyScore = anomalyScore; }

    public double getSupervisedScore() { return supervisedScore; }
    public void setSupervisedScore(double supervisedScore) { this.supervisedScore = supervisedScore; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public List<String> getRedFlags() { return redFlags; }
    public void setRedFlags(List<String> redFlags) { this.redFlags = redFlags; }

    public boolean isBlockTransaction() { return blockTransaction; }
    public void setBlockTransaction(boolean blockTransaction) { this.blockTransaction = blockTransaction; }

    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }

    public String getDecisionId() { return decisionId; }
    public void setDecisionId(String decisionId) { this.decisionId = decisionId; }

    public long getProcessingTimeMs() { return processingTimeMs; }
    public void setProcessingTimeMs(long processingTimeMs) { this.processingTimeMs = processingTimeMs; }
}
