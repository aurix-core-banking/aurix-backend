package com.aurix.platform.customer.kyc.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "scores_kyc", schema = "aurix")
public class ScoreKYC extends BaseEntity {
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "score_geral")
    private Integer scoreGeral;

    @Column(name = "score_documento")
    private Integer scoreDocumento;

    @Column(name = "score_biometria")
    private Integer scoreBiometria;

    @Column(name = "score_pep")
    private Integer scorePep;

    @Column(name = "score_origem_fundos")
    private Integer scoreOrigemFundos;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Integer getScoreGeral() { return scoreGeral; }
    public void setScoreGeral(Integer scoreGeral) { this.scoreGeral = scoreGeral; }
    public Integer getScoreDocumento() { return scoreDocumento; }
    public void setScoreDocumento(Integer scoreDocumento) { this.scoreDocumento = scoreDocumento; }
    public Integer getScoreBiometria() { return scoreBiometria; }
    public void setScoreBiometria(Integer scoreBiometria) { this.scoreBiometria = scoreBiometria; }
    public Integer getScorePep() { return scorePep; }
    public void setScorePep(Integer scorePep) { this.scorePep = scorePep; }
    public Integer getScoreOrigemFundos() { return scoreOrigemFundos; }
    public void setScoreOrigemFundos(Integer scoreOrigemFundos) { this.scoreOrigemFundos = scoreOrigemFundos; }
}
