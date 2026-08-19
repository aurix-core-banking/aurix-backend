package com.aurix.platform.contracts.assinatura.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ConfirmarAssinaturaRequest {

    @NotBlank(message = "OTP é obrigatório")
    private String otpCodigo;

    private String biometriaTipo;

    private String biometriaHash;

    private String ip;

    private String userAgent;

    public ConfirmarAssinaturaRequest() {}

    public ConfirmarAssinaturaRequest(String otpCodigo, String biometriaTipo, String biometriaHash,
                                      String ip, String userAgent) {
        this.otpCodigo = otpCodigo;
        this.biometriaTipo = biometriaTipo;
        this.biometriaHash = biometriaHash;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public String getOtpCodigo() { return otpCodigo; }
    public void setOtpCodigo(String otpCodigo) { this.otpCodigo = otpCodigo; }
    public String getBiometriaTipo() { return biometriaTipo; }
    public void setBiometriaTipo(String biometriaTipo) { this.biometriaTipo = biometriaTipo; }
    public String getBiometriaHash() { return biometriaHash; }
    public void setBiometriaHash(String biometriaHash) { this.biometriaHash = biometriaHash; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
}
