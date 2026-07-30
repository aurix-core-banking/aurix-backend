package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "avaliacoes_risco", schema = "aurix")
public class AvaliacaoRisco extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoAvaliacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id")
    private Conta conta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_id")
    private Transacao transacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_risco_id")
    private PerfilRisco perfilRisco;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAvaliacao tipoAvaliacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAvaliacao status = StatusAvaliacao.PENDENTE;
    @Column(nullable = false)
    private Integer scoreRisco;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelRisco nivelRisco;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorTransacao;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorLimite;
    @Column(precision = 5, scale = 4)
    private BigDecimal percentualRisco;
    @Column
    private Boolean aprovada = false;
    @Column
    private Boolean rejeitada = false;
    @Column
    private Boolean requerAprovacao = false;
    @Column
    private Boolean requerDocumentacao = false;
    @Column
    private Boolean requerBiometria = false;
    @Column
    private Boolean requerToken = false;
    @Column
    private Boolean requerAssinaturaDigital = false;
    @Column
    private LocalDateTime dataAvaliacao;
    @Column
    private LocalDateTime dataAprovacao;
    @Column
    private LocalDateTime dataRejeicao;
    @Column(length = 1000)
    private String observacoes;
    @Column(length = 1000)
    private String justificativa;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_avaliacao", columnDefinition = "JSONB")
    private String detalhesAvaliacao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_aplicadas", columnDefinition = "JSONB")
    private String regrasAplicadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "alertas_gerados", columnDefinition = "JSONB")
    private String alertasGerados;
    @Column
    private String usuarioAvaliador;
    @Column
    private String usuarioAprovador;
    @Column
    private String usuarioRejeitador;
    @Column
    private String sistemaOrigem;
    @Column
    private String codigoTransacao;
    @Column
    private String codigoLiquidacao;


    public enum TipoAvaliacao {
        TRANSACAO, CONTA, CLIENTE, PERIODICA, EVENTO, COMPLIANCE, FRAUDE, LAVAGEM_DINHEIRO, TERRORISMO, OUTROS;
    }


    public enum StatusAvaliacao {
        PENDENTE, PROCESSANDO, APROVADA, REJEITADA, SUSPENSA, CANCELADA, AGUARDANDO_APROVACAO, AGUARDANDO_DOCUMENTACAO, AGUARDANDO_BIOMETRIA, AGUARDANDO_TOKEN, AGUARDANDO_ASSINATURA;
    }


    public enum NivelRisco {
        BAIXO, MEDIO, ALTO, CRITICO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoAvaliacao() {
        return this.codigoAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public Transacao getTransacao() {
        return this.transacao;
    }

    @java.lang.SuppressWarnings("all")
    public PerfilRisco getPerfilRisco() {
        return this.perfilRisco;
    }

    @java.lang.SuppressWarnings("all")
    public TipoAvaliacao getTipoAvaliacao() {
        return this.tipoAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusAvaliacao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getScoreRisco() {
        return this.scoreRisco;
    }

    @java.lang.SuppressWarnings("all")
    public NivelRisco getNivelRisco() {
        return this.nivelRisco;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTransacao() {
        return this.valorTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorLimite() {
        return this.valorLimite;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualRisco() {
        return this.percentualRisco;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAprovada() {
        return this.aprovada;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRejeitada() {
        return this.rejeitada;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerAprovacao() {
        return this.requerAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerDocumentacao() {
        return this.requerDocumentacao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerBiometria() {
        return this.requerBiometria;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerToken() {
        return this.requerToken;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerAssinaturaDigital() {
        return this.requerAssinaturaDigital;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAvaliacao() {
        return this.dataAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAprovacao() {
        return this.dataAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataRejeicao() {
        return this.dataRejeicao;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getJustificativa() {
        return this.justificativa;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesAvaliacao() {
        return this.detalhesAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasAplicadas() {
        return this.regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getAlertasGerados() {
        return this.alertasGerados;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioAvaliador() {
        return this.usuarioAvaliador;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioAprovador() {
        return this.usuarioAprovador;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioRejeitador() {
        return this.usuarioRejeitador;
    }

    @java.lang.SuppressWarnings("all")
    public String getSistemaOrigem() {
        return this.sistemaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoTransacao() {
        return this.codigoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoLiquidacao() {
        return this.codigoLiquidacao;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoAvaliacao(final String codigoAvaliacao) {
        this.codigoAvaliacao = codigoAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacao(final Transacao transacao) {
        this.transacao = transacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPerfilRisco(final PerfilRisco perfilRisco) {
        this.perfilRisco = perfilRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoAvaliacao(final TipoAvaliacao tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusAvaliacao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreRisco(final Integer scoreRisco) {
        this.scoreRisco = scoreRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelRisco(final NivelRisco nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTransacao(final BigDecimal valorTransacao) {
        this.valorTransacao = valorTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorLimite(final BigDecimal valorLimite) {
        this.valorLimite = valorLimite;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualRisco(final BigDecimal percentualRisco) {
        this.percentualRisco = percentualRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setAprovada(final Boolean aprovada) {
        this.aprovada = aprovada;
    }

    @java.lang.SuppressWarnings("all")
    public void setRejeitada(final Boolean rejeitada) {
        this.rejeitada = rejeitada;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerAprovacao(final Boolean requerAprovacao) {
        this.requerAprovacao = requerAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerDocumentacao(final Boolean requerDocumentacao) {
        this.requerDocumentacao = requerDocumentacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerBiometria(final Boolean requerBiometria) {
        this.requerBiometria = requerBiometria;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerToken(final Boolean requerToken) {
        this.requerToken = requerToken;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerAssinaturaDigital(final Boolean requerAssinaturaDigital) {
        this.requerAssinaturaDigital = requerAssinaturaDigital;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAvaliacao(final LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAprovacao(final LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataRejeicao(final LocalDateTime dataRejeicao) {
        this.dataRejeicao = dataRejeicao;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setJustificativa(final String justificativa) {
        this.justificativa = justificativa;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesAvaliacao(final String detalhesAvaliacao) {
        this.detalhesAvaliacao = detalhesAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasAplicadas(final String regrasAplicadas) {
        this.regrasAplicadas = regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setAlertasGerados(final String alertasGerados) {
        this.alertasGerados = alertasGerados;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioAvaliador(final String usuarioAvaliador) {
        this.usuarioAvaliador = usuarioAvaliador;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioAprovador(final String usuarioAprovador) {
        this.usuarioAprovador = usuarioAprovador;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioRejeitador(final String usuarioRejeitador) {
        this.usuarioRejeitador = usuarioRejeitador;
    }

    @java.lang.SuppressWarnings("all")
    public void setSistemaOrigem(final String sistemaOrigem) {
        this.sistemaOrigem = sistemaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoTransacao(final String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoLiquidacao(final String codigoLiquidacao) {
        this.codigoLiquidacao = codigoLiquidacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "AvaliacaoRisco(id=" + this.getId() + ", codigoAvaliacao=" + this.getCodigoAvaliacao() + ", conta=" + this.getConta() + ", transacao=" + this.getTransacao() + ", perfilRisco=" + this.getPerfilRisco() + ", tipoAvaliacao=" + this.getTipoAvaliacao() + ", status=" + this.getStatus() + ", scoreRisco=" + this.getScoreRisco() + ", nivelRisco=" + this.getNivelRisco() + ", valorTransacao=" + this.getValorTransacao() + ", valorLimite=" + this.getValorLimite() + ", percentualRisco=" + this.getPercentualRisco() + ", aprovada=" + this.getAprovada() + ", rejeitada=" + this.getRejeitada() + ", requerAprovacao=" + this.getRequerAprovacao() + ", requerDocumentacao=" + this.getRequerDocumentacao() + ", requerBiometria=" + this.getRequerBiometria() + ", requerToken=" + this.getRequerToken() + ", requerAssinaturaDigital=" + this.getRequerAssinaturaDigital() + ", dataAvaliacao=" + this.getDataAvaliacao() + ", dataAprovacao=" + this.getDataAprovacao() + ", dataRejeicao=" + this.getDataRejeicao() + ", observacoes=" + this.getObservacoes() + ", justificativa=" + this.getJustificativa() + ", detalhesAvaliacao=" + this.getDetalhesAvaliacao() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", alertasGerados=" + this.getAlertasGerados() + ", usuarioAvaliador=" + this.getUsuarioAvaliador() + ", usuarioAprovador=" + this.getUsuarioAprovador() + ", usuarioRejeitador=" + this.getUsuarioRejeitador() + ", sistemaOrigem=" + this.getSistemaOrigem() + ", codigoTransacao=" + this.getCodigoTransacao() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public AvaliacaoRisco() {
    }

    @java.lang.SuppressWarnings("all")
    public AvaliacaoRisco(final Long id, final String codigoAvaliacao, final Conta conta, final Transacao transacao, final PerfilRisco perfilRisco, final TipoAvaliacao tipoAvaliacao, final StatusAvaliacao status, final Integer scoreRisco, final NivelRisco nivelRisco, final BigDecimal valorTransacao, final BigDecimal valorLimite, final BigDecimal percentualRisco, final Boolean aprovada, final Boolean rejeitada, final Boolean requerAprovacao, final Boolean requerDocumentacao, final Boolean requerBiometria, final Boolean requerToken, final Boolean requerAssinaturaDigital, final LocalDateTime dataAvaliacao, final LocalDateTime dataAprovacao, final LocalDateTime dataRejeicao, final String observacoes, final String justificativa, final String detalhesAvaliacao, final String regrasAplicadas, final String alertasGerados, final String usuarioAvaliador, final String usuarioAprovador, final String usuarioRejeitador, final String sistemaOrigem, final String codigoTransacao, final String codigoLiquidacao) {
        this.setId(id);
        this.codigoAvaliacao = codigoAvaliacao;
        this.conta = conta;
        this.transacao = transacao;
        this.perfilRisco = perfilRisco;
        this.tipoAvaliacao = tipoAvaliacao;
        this.status = status;
        this.scoreRisco = scoreRisco;
        this.nivelRisco = nivelRisco;
        this.valorTransacao = valorTransacao;
        this.valorLimite = valorLimite;
        this.percentualRisco = percentualRisco;
        this.aprovada = aprovada;
        this.rejeitada = rejeitada;
        this.requerAprovacao = requerAprovacao;
        this.requerDocumentacao = requerDocumentacao;
        this.requerBiometria = requerBiometria;
        this.requerToken = requerToken;
        this.requerAssinaturaDigital = requerAssinaturaDigital;
        this.dataAvaliacao = dataAvaliacao;
        this.dataAprovacao = dataAprovacao;
        this.dataRejeicao = dataRejeicao;
        this.observacoes = observacoes;
        this.justificativa = justificativa;
        this.detalhesAvaliacao = detalhesAvaliacao;
        this.regrasAplicadas = regrasAplicadas;
        this.alertasGerados = alertasGerados;
        this.usuarioAvaliador = usuarioAvaliador;
        this.usuarioAprovador = usuarioAprovador;
        this.usuarioRejeitador = usuarioRejeitador;
        this.sistemaOrigem = sistemaOrigem;
        this.codigoTransacao = codigoTransacao;
        this.codigoLiquidacao = codigoLiquidacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AvaliacaoRisco)) return false;
        final AvaliacaoRisco other = (AvaliacaoRisco) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$scoreRisco = this.getScoreRisco();
        final java.lang.Object other$scoreRisco = other.getScoreRisco();
        if (this$scoreRisco == null ? other$scoreRisco != null : !this$scoreRisco.equals(other$scoreRisco)) return false;
        final java.lang.Object this$aprovada = this.getAprovada();
        final java.lang.Object other$aprovada = other.getAprovada();
        if (this$aprovada == null ? other$aprovada != null : !this$aprovada.equals(other$aprovada)) return false;
        final java.lang.Object this$rejeitada = this.getRejeitada();
        final java.lang.Object other$rejeitada = other.getRejeitada();
        if (this$rejeitada == null ? other$rejeitada != null : !this$rejeitada.equals(other$rejeitada)) return false;
        final java.lang.Object this$requerAprovacao = this.getRequerAprovacao();
        final java.lang.Object other$requerAprovacao = other.getRequerAprovacao();
        if (this$requerAprovacao == null ? other$requerAprovacao != null : !this$requerAprovacao.equals(other$requerAprovacao)) return false;
        final java.lang.Object this$requerDocumentacao = this.getRequerDocumentacao();
        final java.lang.Object other$requerDocumentacao = other.getRequerDocumentacao();
        if (this$requerDocumentacao == null ? other$requerDocumentacao != null : !this$requerDocumentacao.equals(other$requerDocumentacao)) return false;
        final java.lang.Object this$requerBiometria = this.getRequerBiometria();
        final java.lang.Object other$requerBiometria = other.getRequerBiometria();
        if (this$requerBiometria == null ? other$requerBiometria != null : !this$requerBiometria.equals(other$requerBiometria)) return false;
        final java.lang.Object this$requerToken = this.getRequerToken();
        final java.lang.Object other$requerToken = other.getRequerToken();
        if (this$requerToken == null ? other$requerToken != null : !this$requerToken.equals(other$requerToken)) return false;
        final java.lang.Object this$requerAssinaturaDigital = this.getRequerAssinaturaDigital();
        final java.lang.Object other$requerAssinaturaDigital = other.getRequerAssinaturaDigital();
        if (this$requerAssinaturaDigital == null ? other$requerAssinaturaDigital != null : !this$requerAssinaturaDigital.equals(other$requerAssinaturaDigital)) return false;
        final java.lang.Object this$codigoAvaliacao = this.getCodigoAvaliacao();
        final java.lang.Object other$codigoAvaliacao = other.getCodigoAvaliacao();
        if (this$codigoAvaliacao == null ? other$codigoAvaliacao != null : !this$codigoAvaliacao.equals(other$codigoAvaliacao)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$transacao = this.getTransacao();
        final java.lang.Object other$transacao = other.getTransacao();
        if (this$transacao == null ? other$transacao != null : !this$transacao.equals(other$transacao)) return false;
        final java.lang.Object this$perfilRisco = this.getPerfilRisco();
        final java.lang.Object other$perfilRisco = other.getPerfilRisco();
        if (this$perfilRisco == null ? other$perfilRisco != null : !this$perfilRisco.equals(other$perfilRisco)) return false;
        final java.lang.Object this$tipoAvaliacao = this.getTipoAvaliacao();
        final java.lang.Object other$tipoAvaliacao = other.getTipoAvaliacao();
        if (this$tipoAvaliacao == null ? other$tipoAvaliacao != null : !this$tipoAvaliacao.equals(other$tipoAvaliacao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$nivelRisco = this.getNivelRisco();
        final java.lang.Object other$nivelRisco = other.getNivelRisco();
        if (this$nivelRisco == null ? other$nivelRisco != null : !this$nivelRisco.equals(other$nivelRisco)) return false;
        final java.lang.Object this$valorTransacao = this.getValorTransacao();
        final java.lang.Object other$valorTransacao = other.getValorTransacao();
        if (this$valorTransacao == null ? other$valorTransacao != null : !this$valorTransacao.equals(other$valorTransacao)) return false;
        final java.lang.Object this$valorLimite = this.getValorLimite();
        final java.lang.Object other$valorLimite = other.getValorLimite();
        if (this$valorLimite == null ? other$valorLimite != null : !this$valorLimite.equals(other$valorLimite)) return false;
        final java.lang.Object this$percentualRisco = this.getPercentualRisco();
        final java.lang.Object other$percentualRisco = other.getPercentualRisco();
        if (this$percentualRisco == null ? other$percentualRisco != null : !this$percentualRisco.equals(other$percentualRisco)) return false;
        final java.lang.Object this$dataAvaliacao = this.getDataAvaliacao();
        final java.lang.Object other$dataAvaliacao = other.getDataAvaliacao();
        if (this$dataAvaliacao == null ? other$dataAvaliacao != null : !this$dataAvaliacao.equals(other$dataAvaliacao)) return false;
        final java.lang.Object this$dataAprovacao = this.getDataAprovacao();
        final java.lang.Object other$dataAprovacao = other.getDataAprovacao();
        if (this$dataAprovacao == null ? other$dataAprovacao != null : !this$dataAprovacao.equals(other$dataAprovacao)) return false;
        final java.lang.Object this$dataRejeicao = this.getDataRejeicao();
        final java.lang.Object other$dataRejeicao = other.getDataRejeicao();
        if (this$dataRejeicao == null ? other$dataRejeicao != null : !this$dataRejeicao.equals(other$dataRejeicao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$justificativa = this.getJustificativa();
        final java.lang.Object other$justificativa = other.getJustificativa();
        if (this$justificativa == null ? other$justificativa != null : !this$justificativa.equals(other$justificativa)) return false;
        final java.lang.Object this$detalhesAvaliacao = this.getDetalhesAvaliacao();
        final java.lang.Object other$detalhesAvaliacao = other.getDetalhesAvaliacao();
        if (this$detalhesAvaliacao == null ? other$detalhesAvaliacao != null : !this$detalhesAvaliacao.equals(other$detalhesAvaliacao)) return false;
        final java.lang.Object this$regrasAplicadas = this.getRegrasAplicadas();
        final java.lang.Object other$regrasAplicadas = other.getRegrasAplicadas();
        if (this$regrasAplicadas == null ? other$regrasAplicadas != null : !this$regrasAplicadas.equals(other$regrasAplicadas)) return false;
        final java.lang.Object this$alertasGerados = this.getAlertasGerados();
        final java.lang.Object other$alertasGerados = other.getAlertasGerados();
        if (this$alertasGerados == null ? other$alertasGerados != null : !this$alertasGerados.equals(other$alertasGerados)) return false;
        final java.lang.Object this$usuarioAvaliador = this.getUsuarioAvaliador();
        final java.lang.Object other$usuarioAvaliador = other.getUsuarioAvaliador();
        if (this$usuarioAvaliador == null ? other$usuarioAvaliador != null : !this$usuarioAvaliador.equals(other$usuarioAvaliador)) return false;
        final java.lang.Object this$usuarioAprovador = this.getUsuarioAprovador();
        final java.lang.Object other$usuarioAprovador = other.getUsuarioAprovador();
        if (this$usuarioAprovador == null ? other$usuarioAprovador != null : !this$usuarioAprovador.equals(other$usuarioAprovador)) return false;
        final java.lang.Object this$usuarioRejeitador = this.getUsuarioRejeitador();
        final java.lang.Object other$usuarioRejeitador = other.getUsuarioRejeitador();
        if (this$usuarioRejeitador == null ? other$usuarioRejeitador != null : !this$usuarioRejeitador.equals(other$usuarioRejeitador)) return false;
        final java.lang.Object this$sistemaOrigem = this.getSistemaOrigem();
        final java.lang.Object other$sistemaOrigem = other.getSistemaOrigem();
        if (this$sistemaOrigem == null ? other$sistemaOrigem != null : !this$sistemaOrigem.equals(other$sistemaOrigem)) return false;
        final java.lang.Object this$codigoTransacao = this.getCodigoTransacao();
        final java.lang.Object other$codigoTransacao = other.getCodigoTransacao();
        if (this$codigoTransacao == null ? other$codigoTransacao != null : !this$codigoTransacao.equals(other$codigoTransacao)) return false;
        final java.lang.Object this$codigoLiquidacao = this.getCodigoLiquidacao();
        final java.lang.Object other$codigoLiquidacao = other.getCodigoLiquidacao();
        if (this$codigoLiquidacao == null ? other$codigoLiquidacao != null : !this$codigoLiquidacao.equals(other$codigoLiquidacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof AvaliacaoRisco;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $scoreRisco = this.getScoreRisco();
        result = result * PRIME + ($scoreRisco == null ? 43 : $scoreRisco.hashCode());
        final java.lang.Object $aprovada = this.getAprovada();
        result = result * PRIME + ($aprovada == null ? 43 : $aprovada.hashCode());
        final java.lang.Object $rejeitada = this.getRejeitada();
        result = result * PRIME + ($rejeitada == null ? 43 : $rejeitada.hashCode());
        final java.lang.Object $requerAprovacao = this.getRequerAprovacao();
        result = result * PRIME + ($requerAprovacao == null ? 43 : $requerAprovacao.hashCode());
        final java.lang.Object $requerDocumentacao = this.getRequerDocumentacao();
        result = result * PRIME + ($requerDocumentacao == null ? 43 : $requerDocumentacao.hashCode());
        final java.lang.Object $requerBiometria = this.getRequerBiometria();
        result = result * PRIME + ($requerBiometria == null ? 43 : $requerBiometria.hashCode());
        final java.lang.Object $requerToken = this.getRequerToken();
        result = result * PRIME + ($requerToken == null ? 43 : $requerToken.hashCode());
        final java.lang.Object $requerAssinaturaDigital = this.getRequerAssinaturaDigital();
        result = result * PRIME + ($requerAssinaturaDigital == null ? 43 : $requerAssinaturaDigital.hashCode());
        final java.lang.Object $codigoAvaliacao = this.getCodigoAvaliacao();
        result = result * PRIME + ($codigoAvaliacao == null ? 43 : $codigoAvaliacao.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $transacao = this.getTransacao();
        result = result * PRIME + ($transacao == null ? 43 : $transacao.hashCode());
        final java.lang.Object $perfilRisco = this.getPerfilRisco();
        result = result * PRIME + ($perfilRisco == null ? 43 : $perfilRisco.hashCode());
        final java.lang.Object $tipoAvaliacao = this.getTipoAvaliacao();
        result = result * PRIME + ($tipoAvaliacao == null ? 43 : $tipoAvaliacao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $nivelRisco = this.getNivelRisco();
        result = result * PRIME + ($nivelRisco == null ? 43 : $nivelRisco.hashCode());
        final java.lang.Object $valorTransacao = this.getValorTransacao();
        result = result * PRIME + ($valorTransacao == null ? 43 : $valorTransacao.hashCode());
        final java.lang.Object $valorLimite = this.getValorLimite();
        result = result * PRIME + ($valorLimite == null ? 43 : $valorLimite.hashCode());
        final java.lang.Object $percentualRisco = this.getPercentualRisco();
        result = result * PRIME + ($percentualRisco == null ? 43 : $percentualRisco.hashCode());
        final java.lang.Object $dataAvaliacao = this.getDataAvaliacao();
        result = result * PRIME + ($dataAvaliacao == null ? 43 : $dataAvaliacao.hashCode());
        final java.lang.Object $dataAprovacao = this.getDataAprovacao();
        result = result * PRIME + ($dataAprovacao == null ? 43 : $dataAprovacao.hashCode());
        final java.lang.Object $dataRejeicao = this.getDataRejeicao();
        result = result * PRIME + ($dataRejeicao == null ? 43 : $dataRejeicao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $justificativa = this.getJustificativa();
        result = result * PRIME + ($justificativa == null ? 43 : $justificativa.hashCode());
        final java.lang.Object $detalhesAvaliacao = this.getDetalhesAvaliacao();
        result = result * PRIME + ($detalhesAvaliacao == null ? 43 : $detalhesAvaliacao.hashCode());
        final java.lang.Object $regrasAplicadas = this.getRegrasAplicadas();
        result = result * PRIME + ($regrasAplicadas == null ? 43 : $regrasAplicadas.hashCode());
        final java.lang.Object $alertasGerados = this.getAlertasGerados();
        result = result * PRIME + ($alertasGerados == null ? 43 : $alertasGerados.hashCode());
        final java.lang.Object $usuarioAvaliador = this.getUsuarioAvaliador();
        result = result * PRIME + ($usuarioAvaliador == null ? 43 : $usuarioAvaliador.hashCode());
        final java.lang.Object $usuarioAprovador = this.getUsuarioAprovador();
        result = result * PRIME + ($usuarioAprovador == null ? 43 : $usuarioAprovador.hashCode());
        final java.lang.Object $usuarioRejeitador = this.getUsuarioRejeitador();
        result = result * PRIME + ($usuarioRejeitador == null ? 43 : $usuarioRejeitador.hashCode());
        final java.lang.Object $sistemaOrigem = this.getSistemaOrigem();
        result = result * PRIME + ($sistemaOrigem == null ? 43 : $sistemaOrigem.hashCode());
        final java.lang.Object $codigoTransacao = this.getCodigoTransacao();
        result = result * PRIME + ($codigoTransacao == null ? 43 : $codigoTransacao.hashCode());
        final java.lang.Object $codigoLiquidacao = this.getCodigoLiquidacao();
        result = result * PRIME + ($codigoLiquidacao == null ? 43 : $codigoLiquidacao.hashCode());
        return result;
    }
}
