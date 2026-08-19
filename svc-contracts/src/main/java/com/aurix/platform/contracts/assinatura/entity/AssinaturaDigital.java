package com.aurix.platform.contracts.assinatura.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "assinaturas_digitais")
public class AssinaturaDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long contratoId;

    @NotBlank
    @Column(nullable = false, length = 14)
    private String clienteDocumento;

    @Column(length = 200)
    private String clienteNome;

    @Column(name = "hash_documento", length = 128)
    private String hashDocumento;

    @Column(name = "hash_documento_sha256", length = 128)
    private String hashDocumentoSha256;

    @Column(name = "caminho_documento", length = 500)
    private String caminhoDocumento;

    @Column(columnDefinition = "TEXT")
    private String conteudoDocumento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusAssinaturaDigital status;

    @Column(nullable = false)
    private boolean otpEnviado = false;

    @Column(name = "otp_codigo", length = 10)
    private String otpCodigo;

    @Column(name = "otp_expiracao")
    private LocalDateTime otpExpiracao;

    @Column(nullable = false)
    private boolean biometriaConfirmada = false;

    @Column(name = "biometria_tipo", length = 20)
    private String biometriaTipo;

    @Column(name = "timestamp_digital")
    private String timestampDigital;

    @Column(name = "certificado_icp_brasil", length = 500)
    private String certificadoIcpBrasil;

    @Column(name = "valida_juridicamente", nullable = false)
    private boolean validaJuridicamente = false;

    @Column(length = 45)
    private String ip;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "data_assinatura")
    private LocalDateTime dataAssinatura;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    public AssinaturaDigital() {}

    public AssinaturaDigital(Long contratoId, String clienteDocumento, String clienteNome,
                             StatusAssinaturaDigital status) {
        this.contratoId = contratoId;
        this.clienteDocumento = clienteDocumento;
        this.clienteNome = clienteNome;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public String getClienteDocumento() { return clienteDocumento; }
    public void setClienteDocumento(String clienteDocumento) { this.clienteDocumento = clienteDocumento; }
    public String getClienteNome() { return clienteNome; }
    public void setClienteNome(String clienteNome) { this.clienteNome = clienteNome; }
    public String getHashDocumento() { return hashDocumento; }
    public void setHashDocumento(String hashDocumento) { this.hashDocumento = hashDocumento; }
    public String getHashDocumentoSha256() { return hashDocumentoSha256; }
    public void setHashDocumentoSha256(String hashDocumentoSha256) { this.hashDocumentoSha256 = hashDocumentoSha256; }
    public String getCaminhoDocumento() { return caminhoDocumento; }
    public void setCaminhoDocumento(String caminhoDocumento) { this.caminhoDocumento = caminhoDocumento; }
    public String getConteudoDocumento() { return conteudoDocumento; }
    public void setConteudoDocumento(String conteudoDocumento) { this.conteudoDocumento = conteudoDocumento; }
    public StatusAssinaturaDigital getStatus() { return status; }
    public void setStatus(StatusAssinaturaDigital status) { this.status = status; }
    public boolean isOtpEnviado() { return otpEnviado; }
    public void setOtpEnviado(boolean otpEnviado) { this.otpEnviado = otpEnviado; }
    public String getOtpCodigo() { return otpCodigo; }
    public void setOtpCodigo(String otpCodigo) { this.otpCodigo = otpCodigo; }
    public LocalDateTime getOtpExpiracao() { return otpExpiracao; }
    public void setOtpExpiracao(LocalDateTime otpExpiracao) { this.otpExpiracao = otpExpiracao; }
    public boolean isBiometriaConfirmada() { return biometriaConfirmada; }
    public void setBiometriaConfirmada(boolean biometriaConfirmada) { this.biometriaConfirmada = biometriaConfirmada; }
    public String getBiometriaTipo() { return biometriaTipo; }
    public void setBiometriaTipo(String biometriaTipo) { this.biometriaTipo = biometriaTipo; }
    public String getTimestampDigital() { return timestampDigital; }
    public void setTimestampDigital(String timestampDigital) { this.timestampDigital = timestampDigital; }
    public String getCertificadoIcpBrasil() { return certificadoIcpBrasil; }
    public void setCertificadoIcpBrasil(String certificadoIcpBrasil) { this.certificadoIcpBrasil = certificadoIcpBrasil; }
    public boolean isValidaJuridicamente() { return validaJuridicamente; }
    public void setValidaJuridicamente(boolean validaJuridicamente) { this.validaJuridicamente = validaJuridicamente; }
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public LocalDateTime getDataAssinatura() { return dataAssinatura; }
    public void setDataAssinatura(LocalDateTime dataAssinatura) { this.dataAssinatura = dataAssinatura; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
