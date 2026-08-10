package com.aurix.platform.contracts.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "contratos_versoes", schema = "aurix")
public class ContratoVersao extends BaseEntity {

    @Column(name = "contrato_id", nullable = false)
    private Long contratoId;

    @Column(name = "numero_versao", nullable = false)
    private Integer numeroVersao;

    @Column(name = "motivo_alteracao", length = 300)
    private String motivoAlteracao;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_json", columnDefinition = "JSONB")
    private String dadosJson;

    @Column(name = "data_versao", nullable = false)
    private LocalDateTime dataVersao = LocalDateTime.now();

    public Long getContratoId() {
        return contratoId;
    }

    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }

    public Integer getNumeroVersao() {
        return numeroVersao;
    }

    public void setNumeroVersao(Integer numeroVersao) {
        this.numeroVersao = numeroVersao;
    }

    public String getMotivoAlteracao() {
        return motivoAlteracao;
    }

    public void setMotivoAlteracao(String motivoAlteracao) {
        this.motivoAlteracao = motivoAlteracao;
    }

    public String getDadosJson() {
        return dadosJson;
    }

    public void setDadosJson(String dadosJson) {
        this.dadosJson = dadosJson;
    }

    public LocalDateTime getDataVersao() {
        return dataVersao;
    }

    public void setDataVersao(LocalDateTime dataVersao) {
        this.dataVersao = dataVersao;
    }
}
