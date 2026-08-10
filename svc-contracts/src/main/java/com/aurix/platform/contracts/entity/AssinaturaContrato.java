package com.aurix.platform.contracts.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "contratos_assinaturas", schema = "aurix")
public class AssinaturaContrato extends BaseEntity {

    public enum AssinanteTipo {
        CLIENTE, GARANTE, AVALISTA, CORRETOR, INSTITUICAO
    }

    @Column(name = "contrato_id", nullable = false)
    private Long contratoId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assinante_tipo", nullable = false, length = 20)
    private AssinanteTipo assinanteTipo;

    @Column(name = "assinante_documento", nullable = false, length = 14)
    private String assinanteDocumento;

    @Column(name = "assinante_nome", length = 200)
    private String assinanteNome;

    @Column(name = "hash_documento", length = 128)
    private String hashDocumento;

    @Column(length = 45)
    private String ip;

    @Column(name = "user_agent", length = 500)
    private String userAgent;

    @Column(name = "data_assinatura")
    private LocalDateTime dataAssinatura;

    @Column(nullable = false)
    private Boolean assinada = Boolean.FALSE;

    @Column(nullable = false)
    private Boolean valida = Boolean.FALSE;

    public Long getContratoId() {
        return contratoId;
    }

    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }

    public AssinanteTipo getAssinanteTipo() {
        return assinanteTipo;
    }

    public void setAssinanteTipo(AssinanteTipo assinanteTipo) {
        this.assinanteTipo = assinanteTipo;
    }

    public String getAssinanteDocumento() {
        return assinanteDocumento;
    }

    public void setAssinanteDocumento(String assinanteDocumento) {
        this.assinanteDocumento = assinanteDocumento;
    }

    public String getAssinanteNome() {
        return assinanteNome;
    }

    public void setAssinanteNome(String assinanteNome) {
        this.assinanteNome = assinanteNome;
    }

    public String getHashDocumento() {
        return hashDocumento;
    }

    public void setHashDocumento(String hashDocumento) {
        this.hashDocumento = hashDocumento;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public LocalDateTime getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(LocalDateTime dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }

    public Boolean getAssinada() {
        return assinada;
    }

    public void setAssinada(Boolean assinada) {
        this.assinada = assinada;
    }

    public Boolean getValida() {
        return valida;
    }

    public void setValida(Boolean valida) {
        this.valida = valida;
    }
}
