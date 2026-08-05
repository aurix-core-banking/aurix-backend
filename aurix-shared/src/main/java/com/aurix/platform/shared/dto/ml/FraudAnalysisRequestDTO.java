package com.aurix.platform.shared.dto.ml;

import java.math.BigDecimal;
import java.util.Map;

public class FraudAnalysisRequestDTO {

    private String transactionId;
    private String accountId;
    private String transactionType;
    private BigDecimal amount;
    private String currency;
    private String channel;
    private String deviceId;
    private String ipAddress;
    private String userAgent;
    private String city;
    private String state;
    private String timestamp;
    private Map<String, String> metadata;

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getChannel() { return channel; }
    public void setChannel(String channel) { this.channel = channel; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }
}
