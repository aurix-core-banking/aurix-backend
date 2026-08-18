package com.aurix.platform.compliance.aml.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "aml_alertas", schema = "aurix")
public class AmlAlerta extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String codigoAlerta;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "cpf_cnpj", nullable = false, length = 14)
    private String cpfCnpj;

    @Column(name = "nome_cliente", length = 255)
    private String nomeCliente;

    @Column(name = "regra_id")
    private Long regraId;

    @Column(nullable = false, length = 255)
    private String nomeRegra;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAlertaAml tipoAlerta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAlertaAml status = StatusAlertaAml.DETECTADO;

    @Column(nullable = false)
    private Integer scoreRisco = 0;

    @Column(nullable = false)
    private LocalDateTime dataDeteccao;

    @Column
    private LocalDateTime dataInvestigacao;

    @Column
    private LocalDateTime dataResolucao;

    @Column(precision = 18, scale = 2)
    private BigDecimal valorTransacao;

    @Column(columnDefinition = "TEXT")
    private String descricaoAlerta;

    @Column(columnDefinition = "TEXT")
    private String detalhesDeteccao;

    @Column(length = 100)
    private String investigadorResponsavel;

    @Column(columnDefinition = "TEXT")
    private String parecerInvestigacao;

    @Column(length = 100)
    private String motivoResolucao;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB")
    private String evidencias;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    public enum TipoAlertaAml {
        TRANSACAO_VALOR_ALTO, FREQUENCIA_EXCESSIVA, PARAISO_FISCAL,
        PAD_DEPOSITO, STRUCTURING, PAIS_ALTO_RISCO, MOVIMENTACAO_INCOMPATIVEL,
        LISTA_PEP, ANALISE_REDE, ALERTA_MANUAL, CONCENTRACAO_PIX,
        TRANSACAO_NOTURNA, CONTA_REATIVADA, TERCEIRO_SUSPEITO
    }

    public enum StatusAlertaAml {
        DETECTADO, EM_INVESTIGACAO, REPORTADO, RESOLVIDO, ARQUIVADO
    }

    @SuppressWarnings("all")
    public AmlAlerta() {
    }

    public String getCodigoAlerta() {
        return this.codigoAlerta;
    }

    public Long getClienteId() {
        return this.clienteId;
    }

    public String getCpfCnpj() {
        return this.cpfCnpj;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public Long getRegraId() {
        return this.regraId;
    }

    public String getNomeRegra() {
        return this.nomeRegra;
    }

    public TipoAlertaAml getTipoAlerta() {
        return this.tipoAlerta;
    }

    public StatusAlertaAml getStatus() {
        return this.status;
    }

    public Integer getScoreRisco() {
        return this.scoreRisco;
    }

    public LocalDateTime getDataDeteccao() {
        return this.dataDeteccao;
    }

    public LocalDateTime getDataInvestigacao() {
        return this.dataInvestigacao;
    }

    public LocalDateTime getDataResolucao() {
        return this.dataResolucao;
    }

    public BigDecimal getValorTransacao() {
        return this.valorTransacao;
    }

    public String getDescricaoAlerta() {
        return this.descricaoAlerta;
    }

    public String getDetalhesDeteccao() {
        return this.detalhesDeteccao;
    }

    public String getInvestigadorResponsavel() {
        return this.investigadorResponsavel;
    }

    public String getParecerInvestigacao() {
        return this.parecerInvestigacao;
    }

    public String getMotivoResolucao() {
        return this.motivoResolucao;
    }

    public String getEvidencias() {
        return this.evidencias;
    }

    public String getObservacoes() {
        return this.observacoes;
    }

    public void setCodigoAlerta(final String codigoAlerta) {
        this.codigoAlerta = codigoAlerta;
    }

    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    public void setCpfCnpj(final String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public void setNomeCliente(final String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setRegraId(final Long regraId) {
        this.regraId = regraId;
    }

    public void setNomeRegra(final String nomeRegra) {
        this.nomeRegra = nomeRegra;
    }

    public void setTipoAlerta(final TipoAlertaAml tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public void setStatus(final StatusAlertaAml status) {
        this.status = status;
    }

    public void setScoreRisco(final Integer scoreRisco) {
        this.scoreRisco = scoreRisco;
    }

    public void setDataDeteccao(final LocalDateTime dataDeteccao) {
        this.dataDeteccao = dataDeteccao;
    }

    public void setDataInvestigacao(final LocalDateTime dataInvestigacao) {
        this.dataInvestigacao = dataInvestigacao;
    }

    public void setDataResolucao(final LocalDateTime dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    public void setValorTransacao(final BigDecimal valorTransacao) {
        this.valorTransacao = valorTransacao;
    }

    public void setDescricaoAlerta(final String descricaoAlerta) {
        this.descricaoAlerta = descricaoAlerta;
    }

    public void setDetalhesDeteccao(final String detalhesDeteccao) {
        this.detalhesDeteccao = detalhesDeteccao;
    }

    public void setInvestigadorResponsavel(final String investigadorResponsavel) {
        this.investigadorResponsavel = investigadorResponsavel;
    }

    public void setParecerInvestigacao(final String parecerInvestigacao) {
        this.parecerInvestigacao = parecerInvestigacao;
    }

    public void setMotivoResolucao(final String motivoResolucao) {
        this.motivoResolucao = motivoResolucao;
    }

    public void setEvidencias(final String evidencias) {
        this.evidencias = evidencias;
    }

    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }
}
