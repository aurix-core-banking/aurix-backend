package com.aurix.platform.compliance.lgpd.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lgpd_bases_legais", schema = "aurix")
public class LgpdBaseLegal extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigoBaseLegal;

    @Column(nullable = false, length = 255)
    private String nomeBaseLegal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoBaseLegal tipoBaseLegal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusBaseLegal status = StatusBaseLegal.ATIVA;

    @Column(columnDefinition = "TEXT")
    private String descricaoBaseLegal;

    @Column(length = 500)
    private String referenciaLegal;

    @Column(name = "cliente_id")
    private Long clienteId;

    @Column(nullable = false)
    private LocalDateTime dataInicioVigencia;

    @Column
    private LocalDateTime dataFimVigencia;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public enum TipoBaseLegal {
        CONSENTIMENTO, OBRIGACAO_LEGAL, LEGITIMO_INTERESSE,
        PROTECAO_VIDA, PROCEDIMENTO_CONTRATUAL, EXERCICIO_DIREITOS,
        PESQUISA_CIENTIFICA, PROTECAO_SAUDE, OPERACAO_FINANCEIRA
    }

    public enum StatusBaseLegal {
        ATIVA, INATIVA, SUSPENSA
    }

    @SuppressWarnings("all")
    public LgpdBaseLegal() {
    }

    public String getCodigoBaseLegal() {
        return this.codigoBaseLegal;
    }

    public String getNomeBaseLegal() {
        return this.nomeBaseLegal;
    }

    public TipoBaseLegal getTipoBaseLegal() {
        return this.tipoBaseLegal;
    }

    public StatusBaseLegal getStatus() {
        return this.status;
    }

    public String getDescricaoBaseLegal() {
        return this.descricaoBaseLegal;
    }

    public String getReferenciaLegal() {
        return this.referenciaLegal;
    }

    public Long getClienteId() {
        return this.clienteId;
    }

    public LocalDateTime getDataInicioVigencia() {
        return this.dataInicioVigencia;
    }

    public LocalDateTime getDataFimVigencia() {
        return this.dataFimVigencia;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public void setCodigoBaseLegal(final String codigoBaseLegal) {
        this.codigoBaseLegal = codigoBaseLegal;
    }

    public void setNomeBaseLegal(final String nomeBaseLegal) {
        this.nomeBaseLegal = nomeBaseLegal;
    }

    public void setTipoBaseLegal(final TipoBaseLegal tipoBaseLegal) {
        this.tipoBaseLegal = tipoBaseLegal;
    }

    public void setStatus(final StatusBaseLegal status) {
        this.status = status;
    }

    public void setDescricaoBaseLegal(final String descricaoBaseLegal) {
        this.descricaoBaseLegal = descricaoBaseLegal;
    }

    public void setReferenciaLegal(final String referenciaLegal) {
        this.referenciaLegal = referenciaLegal;
    }

    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setDataInicioVigencia(final LocalDateTime dataInicioVigencia) {
        this.dataInicioVigencia = dataInicioVigencia;
    }

    public void setDataFimVigencia(final LocalDateTime dataFimVigencia) {
        this.dataFimVigencia = dataFimVigencia;
    }

    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }
}
