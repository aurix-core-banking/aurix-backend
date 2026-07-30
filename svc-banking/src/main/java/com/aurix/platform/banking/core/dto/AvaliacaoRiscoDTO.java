package com.aurix.platform.banking.core.dto;

import com.aurix.platform.banking.core.entity.AvaliacaoRisco;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AvaliacaoRiscoDTO {
    private Long id;
    private String codigoAvaliacao;
    private Long contaId;
    private Long transacaoId;
    private Long perfilRiscoId;
    private String tipoAvaliacao;
    private String status;
    private Integer scoreRisco;
    private String nivelRisco;
    private BigDecimal valorTransacao;
    private BigDecimal valorLimite;
    private BigDecimal percentualRisco;
    private Boolean aprovada;
    private Boolean rejeitada;
    private Boolean requerAprovacao;
    private Boolean requerDocumentacao;
    private Boolean requerBiometria;
    private Boolean requerToken;
    private Boolean requerAssinaturaDigital;
    private LocalDateTime dataAvaliacao;
    private LocalDateTime dataAprovacao;
    private LocalDateTime dataRejeicao;
    private String observacoes;
    private String justificativa;
    private String detalhesAvaliacao;
    private String regrasAplicadas;
    private String alertasGerados;
    private String usuarioAvaliador;
    private String usuarioAprovador;
    private String usuarioRejeitador;
    private String sistemaOrigem;
    private String codigoTransacao;
    private String codigoLiquidacao;

    public static AvaliacaoRiscoDTO fromEntity(AvaliacaoRisco avaliacao) {
        AvaliacaoRiscoDTO dto = new AvaliacaoRiscoDTO();
        dto.setId(avaliacao.getId());
        dto.setCodigoAvaliacao(avaliacao.getCodigoAvaliacao());
        dto.setContaId(avaliacao.getConta() != null ? avaliacao.getConta().getId() : null);
        dto.setTransacaoId(avaliacao.getTransacao() != null ? avaliacao.getTransacao().getId() : null);
        dto.setPerfilRiscoId(avaliacao.getPerfilRisco() != null ? avaliacao.getPerfilRisco().getId() : null);
        dto.setTipoAvaliacao(avaliacao.getTipoAvaliacao().name());
        dto.setStatus(avaliacao.getStatus().name());
        dto.setScoreRisco(avaliacao.getScoreRisco());
        dto.setNivelRisco(avaliacao.getNivelRisco().name());
        dto.setValorTransacao(avaliacao.getValorTransacao());
        dto.setValorLimite(avaliacao.getValorLimite());
        dto.setPercentualRisco(avaliacao.getPercentualRisco());
        dto.setAprovada(avaliacao.getAprovada());
        dto.setRejeitada(avaliacao.getRejeitada());
        dto.setRequerAprovacao(avaliacao.getRequerAprovacao());
        dto.setRequerDocumentacao(avaliacao.getRequerDocumentacao());
        dto.setRequerBiometria(avaliacao.getRequerBiometria());
        dto.setRequerToken(avaliacao.getRequerToken());
        dto.setRequerAssinaturaDigital(avaliacao.getRequerAssinaturaDigital());
        dto.setDataAvaliacao(avaliacao.getDataAvaliacao());
        dto.setDataAprovacao(avaliacao.getDataAprovacao());
        dto.setDataRejeicao(avaliacao.getDataRejeicao());
        dto.setObservacoes(avaliacao.getObservacoes());
        dto.setJustificativa(avaliacao.getJustificativa());
        dto.setDetalhesAvaliacao(avaliacao.getDetalhesAvaliacao());
        dto.setRegrasAplicadas(avaliacao.getRegrasAplicadas());
        dto.setAlertasGerados(avaliacao.getAlertasGerados());
        dto.setUsuarioAvaliador(avaliacao.getUsuarioAvaliador());
        dto.setUsuarioAprovador(avaliacao.getUsuarioAprovador());
        dto.setUsuarioRejeitador(avaliacao.getUsuarioRejeitador());
        dto.setSistemaOrigem(avaliacao.getSistemaOrigem());
        dto.setCodigoTransacao(avaliacao.getCodigoTransacao());
        dto.setCodigoLiquidacao(avaliacao.getCodigoLiquidacao());
        return dto;
    }

    public AvaliacaoRisco toEntity() {
        AvaliacaoRisco avaliacao = new AvaliacaoRisco();
        avaliacao.setId(this.id);
        avaliacao.setCodigoAvaliacao(this.codigoAvaliacao);
        avaliacao.setTipoAvaliacao(AvaliacaoRisco.TipoAvaliacao.valueOf(this.tipoAvaliacao));
        avaliacao.setStatus(AvaliacaoRisco.StatusAvaliacao.valueOf(this.status));
        avaliacao.setScoreRisco(this.scoreRisco);
        avaliacao.setNivelRisco(AvaliacaoRisco.NivelRisco.valueOf(this.nivelRisco));
        avaliacao.setValorTransacao(this.valorTransacao);
        avaliacao.setValorLimite(this.valorLimite);
        avaliacao.setPercentualRisco(this.percentualRisco);
        avaliacao.setAprovada(this.aprovada);
        avaliacao.setRejeitada(this.rejeitada);
        avaliacao.setRequerAprovacao(this.requerAprovacao);
        avaliacao.setRequerDocumentacao(this.requerDocumentacao);
        avaliacao.setRequerBiometria(this.requerBiometria);
        avaliacao.setRequerToken(this.requerToken);
        avaliacao.setRequerAssinaturaDigital(this.requerAssinaturaDigital);
        avaliacao.setDataAvaliacao(this.dataAvaliacao);
        avaliacao.setDataAprovacao(this.dataAprovacao);
        avaliacao.setDataRejeicao(this.dataRejeicao);
        avaliacao.setObservacoes(this.observacoes);
        avaliacao.setJustificativa(this.justificativa);
        avaliacao.setDetalhesAvaliacao(this.detalhesAvaliacao);
        avaliacao.setRegrasAplicadas(this.regrasAplicadas);
        avaliacao.setAlertasGerados(this.alertasGerados);
        avaliacao.setUsuarioAvaliador(this.usuarioAvaliador);
        avaliacao.setUsuarioAprovador(this.usuarioAprovador);
        avaliacao.setUsuarioRejeitador(this.usuarioRejeitador);
        avaliacao.setSistemaOrigem(this.sistemaOrigem);
        avaliacao.setCodigoTransacao(this.codigoTransacao);
        avaliacao.setCodigoLiquidacao(this.codigoLiquidacao);
        return avaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoAvaliacao() {
        return this.codigoAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTransacaoId() {
        return this.transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getPerfilRiscoId() {
        return this.perfilRiscoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getTipoAvaliacao() {
        return this.tipoAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getScoreRisco() {
        return this.scoreRisco;
    }

    @java.lang.SuppressWarnings("all")
    public String getNivelRisco() {
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
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoAvaliacao(final String codigoAvaliacao) {
        this.codigoAvaliacao = codigoAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacaoId(final Long transacaoId) {
        this.transacaoId = transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setPerfilRiscoId(final Long perfilRiscoId) {
        this.perfilRiscoId = perfilRiscoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoAvaliacao(final String tipoAvaliacao) {
        this.tipoAvaliacao = tipoAvaliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreRisco(final Integer scoreRisco) {
        this.scoreRisco = scoreRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelRisco(final String nivelRisco) {
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
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AvaliacaoRiscoDTO)) return false;
        final AvaliacaoRiscoDTO other = (AvaliacaoRiscoDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$contaId = this.getContaId();
        final java.lang.Object other$contaId = other.getContaId();
        if (this$contaId == null ? other$contaId != null : !this$contaId.equals(other$contaId)) return false;
        final java.lang.Object this$transacaoId = this.getTransacaoId();
        final java.lang.Object other$transacaoId = other.getTransacaoId();
        if (this$transacaoId == null ? other$transacaoId != null : !this$transacaoId.equals(other$transacaoId)) return false;
        final java.lang.Object this$perfilRiscoId = this.getPerfilRiscoId();
        final java.lang.Object other$perfilRiscoId = other.getPerfilRiscoId();
        if (this$perfilRiscoId == null ? other$perfilRiscoId != null : !this$perfilRiscoId.equals(other$perfilRiscoId)) return false;
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
        return other instanceof AvaliacaoRiscoDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $contaId = this.getContaId();
        result = result * PRIME + ($contaId == null ? 43 : $contaId.hashCode());
        final java.lang.Object $transacaoId = this.getTransacaoId();
        result = result * PRIME + ($transacaoId == null ? 43 : $transacaoId.hashCode());
        final java.lang.Object $perfilRiscoId = this.getPerfilRiscoId();
        result = result * PRIME + ($perfilRiscoId == null ? 43 : $perfilRiscoId.hashCode());
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

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "AvaliacaoRiscoDTO(id=" + this.getId() + ", codigoAvaliacao=" + this.getCodigoAvaliacao() + ", contaId=" + this.getContaId() + ", transacaoId=" + this.getTransacaoId() + ", perfilRiscoId=" + this.getPerfilRiscoId() + ", tipoAvaliacao=" + this.getTipoAvaliacao() + ", status=" + this.getStatus() + ", scoreRisco=" + this.getScoreRisco() + ", nivelRisco=" + this.getNivelRisco() + ", valorTransacao=" + this.getValorTransacao() + ", valorLimite=" + this.getValorLimite() + ", percentualRisco=" + this.getPercentualRisco() + ", aprovada=" + this.getAprovada() + ", rejeitada=" + this.getRejeitada() + ", requerAprovacao=" + this.getRequerAprovacao() + ", requerDocumentacao=" + this.getRequerDocumentacao() + ", requerBiometria=" + this.getRequerBiometria() + ", requerToken=" + this.getRequerToken() + ", requerAssinaturaDigital=" + this.getRequerAssinaturaDigital() + ", dataAvaliacao=" + this.getDataAvaliacao() + ", dataAprovacao=" + this.getDataAprovacao() + ", dataRejeicao=" + this.getDataRejeicao() + ", observacoes=" + this.getObservacoes() + ", justificativa=" + this.getJustificativa() + ", detalhesAvaliacao=" + this.getDetalhesAvaliacao() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", alertasGerados=" + this.getAlertasGerados() + ", usuarioAvaliador=" + this.getUsuarioAvaliador() + ", usuarioAprovador=" + this.getUsuarioAprovador() + ", usuarioRejeitador=" + this.getUsuarioRejeitador() + ", sistemaOrigem=" + this.getSistemaOrigem() + ", codigoTransacao=" + this.getCodigoTransacao() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public AvaliacaoRiscoDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public AvaliacaoRiscoDTO(final Long id, final String codigoAvaliacao, final Long contaId, final Long transacaoId, final Long perfilRiscoId, final String tipoAvaliacao, final String status, final Integer scoreRisco, final String nivelRisco, final BigDecimal valorTransacao, final BigDecimal valorLimite, final BigDecimal percentualRisco, final Boolean aprovada, final Boolean rejeitada, final Boolean requerAprovacao, final Boolean requerDocumentacao, final Boolean requerBiometria, final Boolean requerToken, final Boolean requerAssinaturaDigital, final LocalDateTime dataAvaliacao, final LocalDateTime dataAprovacao, final LocalDateTime dataRejeicao, final String observacoes, final String justificativa, final String detalhesAvaliacao, final String regrasAplicadas, final String alertasGerados, final String usuarioAvaliador, final String usuarioAprovador, final String usuarioRejeitador, final String sistemaOrigem, final String codigoTransacao, final String codigoLiquidacao) {
        this.id = id;
        this.codigoAvaliacao = codigoAvaliacao;
        this.contaId = contaId;
        this.transacaoId = transacaoId;
        this.perfilRiscoId = perfilRiscoId;
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
}
