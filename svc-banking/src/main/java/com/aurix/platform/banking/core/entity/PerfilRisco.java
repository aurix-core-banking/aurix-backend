package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "perfis_risco", schema = "aurix")
public class PerfilRisco extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoPerfil;
    @Column(nullable = false)
    private String nomePerfil;
    @Column(length = 1000)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelRisco nivelRisco;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorLimiteDiario;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorLimiteMensal;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorLimiteAnual;
    @Column
    private Integer quantidadeTransacoesDiarias;
    @Column
    private Integer quantidadeTransacoesMensais;
    @Column
    private Integer quantidadeTransacoesAnuais;
    @Column(precision = 5, scale = 4)
    private BigDecimal percentualLimiteCredito;
    @Column(precision = 5, scale = 4)
    private BigDecimal percentualLimiteInvestimento;
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
    private Integer tempoSessaoMinutos = 30;
    @Column
    private Integer tentativasLoginMaximas = 3;
    @Column
    private Integer tempoBloqueioMinutos = 30;
    @Column
    private Boolean ativo = true;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_especiais", columnDefinition = "JSONB")
    private String regrasEspeciais;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracoes_avancadas", columnDefinition = "JSONB")
    private String configuracoesAvancadas;
    @Column
    private LocalDateTime dataInicioVigencia;
    @Column
    private LocalDateTime dataFimVigencia;
    @Column
    private String usuarioCriacao;
    @Column
    private String usuarioAprovacao;
    @Column
    private LocalDateTime dataAprovacao;


    public enum NivelRisco {
        BAIXO, MEDIO, ALTO, CRITICO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoPerfil() {
        return this.codigoPerfil;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomePerfil() {
        return this.nomePerfil;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public NivelRisco getNivelRisco() {
        return this.nivelRisco;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorLimiteDiario() {
        return this.valorLimiteDiario;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorLimiteMensal() {
        return this.valorLimiteMensal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorLimiteAnual() {
        return this.valorLimiteAnual;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeTransacoesDiarias() {
        return this.quantidadeTransacoesDiarias;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeTransacoesMensais() {
        return this.quantidadeTransacoesMensais;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeTransacoesAnuais() {
        return this.quantidadeTransacoesAnuais;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualLimiteCredito() {
        return this.percentualLimiteCredito;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualLimiteInvestimento() {
        return this.percentualLimiteInvestimento;
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
    public Integer getTempoSessaoMinutos() {
        return this.tempoSessaoMinutos;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTentativasLoginMaximas() {
        return this.tentativasLoginMaximas;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTempoBloqueioMinutos() {
        return this.tempoBloqueioMinutos;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasEspeciais() {
        return this.regrasEspeciais;
    }

    @java.lang.SuppressWarnings("all")
    public String getConfiguracoesAvancadas() {
        return this.configuracoesAvancadas;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInicioVigencia() {
        return this.dataInicioVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFimVigencia() {
        return this.dataFimVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioCriacao() {
        return this.usuarioCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioAprovacao() {
        return this.usuarioAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAprovacao() {
        return this.dataAprovacao;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoPerfil(final String codigoPerfil) {
        this.codigoPerfil = codigoPerfil;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomePerfil(final String nomePerfil) {
        this.nomePerfil = nomePerfil;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelRisco(final NivelRisco nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorLimiteDiario(final BigDecimal valorLimiteDiario) {
        this.valorLimiteDiario = valorLimiteDiario;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorLimiteMensal(final BigDecimal valorLimiteMensal) {
        this.valorLimiteMensal = valorLimiteMensal;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorLimiteAnual(final BigDecimal valorLimiteAnual) {
        this.valorLimiteAnual = valorLimiteAnual;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeTransacoesDiarias(final Integer quantidadeTransacoesDiarias) {
        this.quantidadeTransacoesDiarias = quantidadeTransacoesDiarias;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeTransacoesMensais(final Integer quantidadeTransacoesMensais) {
        this.quantidadeTransacoesMensais = quantidadeTransacoesMensais;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeTransacoesAnuais(final Integer quantidadeTransacoesAnuais) {
        this.quantidadeTransacoesAnuais = quantidadeTransacoesAnuais;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualLimiteCredito(final BigDecimal percentualLimiteCredito) {
        this.percentualLimiteCredito = percentualLimiteCredito;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualLimiteInvestimento(final BigDecimal percentualLimiteInvestimento) {
        this.percentualLimiteInvestimento = percentualLimiteInvestimento;
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
    public void setTempoSessaoMinutos(final Integer tempoSessaoMinutos) {
        this.tempoSessaoMinutos = tempoSessaoMinutos;
    }

    @java.lang.SuppressWarnings("all")
    public void setTentativasLoginMaximas(final Integer tentativasLoginMaximas) {
        this.tentativasLoginMaximas = tentativasLoginMaximas;
    }

    @java.lang.SuppressWarnings("all")
    public void setTempoBloqueioMinutos(final Integer tempoBloqueioMinutos) {
        this.tempoBloqueioMinutos = tempoBloqueioMinutos;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasEspeciais(final String regrasEspeciais) {
        this.regrasEspeciais = regrasEspeciais;
    }

    @java.lang.SuppressWarnings("all")
    public void setConfiguracoesAvancadas(final String configuracoesAvancadas) {
        this.configuracoesAvancadas = configuracoesAvancadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataInicioVigencia(final LocalDateTime dataInicioVigencia) {
        this.dataInicioVigencia = dataInicioVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataFimVigencia(final LocalDateTime dataFimVigencia) {
        this.dataFimVigencia = dataFimVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioCriacao(final String usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioAprovacao(final String usuarioAprovacao) {
        this.usuarioAprovacao = usuarioAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAprovacao(final LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PerfilRisco(id=" + this.getId() + ", codigoPerfil=" + this.getCodigoPerfil() + ", nomePerfil=" + this.getNomePerfil() + ", descricao=" + this.getDescricao() + ", nivelRisco=" + this.getNivelRisco() + ", valorLimiteDiario=" + this.getValorLimiteDiario() + ", valorLimiteMensal=" + this.getValorLimiteMensal() + ", valorLimiteAnual=" + this.getValorLimiteAnual() + ", quantidadeTransacoesDiarias=" + this.getQuantidadeTransacoesDiarias() + ", quantidadeTransacoesMensais=" + this.getQuantidadeTransacoesMensais() + ", quantidadeTransacoesAnuais=" + this.getQuantidadeTransacoesAnuais() + ", percentualLimiteCredito=" + this.getPercentualLimiteCredito() + ", percentualLimiteInvestimento=" + this.getPercentualLimiteInvestimento() + ", requerAprovacao=" + this.getRequerAprovacao() + ", requerDocumentacao=" + this.getRequerDocumentacao() + ", requerBiometria=" + this.getRequerBiometria() + ", requerToken=" + this.getRequerToken() + ", requerAssinaturaDigital=" + this.getRequerAssinaturaDigital() + ", tempoSessaoMinutos=" + this.getTempoSessaoMinutos() + ", tentativasLoginMaximas=" + this.getTentativasLoginMaximas() + ", tempoBloqueioMinutos=" + this.getTempoBloqueioMinutos() + ", ativo=" + this.getAtivo() + ", observacoes=" + this.getObservacoes() + ", regrasEspeciais=" + this.getRegrasEspeciais() + ", configuracoesAvancadas=" + this.getConfiguracoesAvancadas() + ", dataInicioVigencia=" + this.getDataInicioVigencia() + ", dataFimVigencia=" + this.getDataFimVigencia() + ", usuarioCriacao=" + this.getUsuarioCriacao() + ", usuarioAprovacao=" + this.getUsuarioAprovacao() + ", dataAprovacao=" + this.getDataAprovacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PerfilRisco() {
    }

    @java.lang.SuppressWarnings("all")
    public PerfilRisco(final Long id, final String codigoPerfil, final String nomePerfil, final String descricao, final NivelRisco nivelRisco, final BigDecimal valorLimiteDiario, final BigDecimal valorLimiteMensal, final BigDecimal valorLimiteAnual, final Integer quantidadeTransacoesDiarias, final Integer quantidadeTransacoesMensais, final Integer quantidadeTransacoesAnuais, final BigDecimal percentualLimiteCredito, final BigDecimal percentualLimiteInvestimento, final Boolean requerAprovacao, final Boolean requerDocumentacao, final Boolean requerBiometria, final Boolean requerToken, final Boolean requerAssinaturaDigital, final Integer tempoSessaoMinutos, final Integer tentativasLoginMaximas, final Integer tempoBloqueioMinutos, final Boolean ativo, final String observacoes, final String regrasEspeciais, final String configuracoesAvancadas, final LocalDateTime dataInicioVigencia, final LocalDateTime dataFimVigencia, final String usuarioCriacao, final String usuarioAprovacao, final LocalDateTime dataAprovacao) {
        this.setId(id);
        this.codigoPerfil = codigoPerfil;
        this.nomePerfil = nomePerfil;
        this.descricao = descricao;
        this.nivelRisco = nivelRisco;
        this.valorLimiteDiario = valorLimiteDiario;
        this.valorLimiteMensal = valorLimiteMensal;
        this.valorLimiteAnual = valorLimiteAnual;
        this.quantidadeTransacoesDiarias = quantidadeTransacoesDiarias;
        this.quantidadeTransacoesMensais = quantidadeTransacoesMensais;
        this.quantidadeTransacoesAnuais = quantidadeTransacoesAnuais;
        this.percentualLimiteCredito = percentualLimiteCredito;
        this.percentualLimiteInvestimento = percentualLimiteInvestimento;
        this.requerAprovacao = requerAprovacao;
        this.requerDocumentacao = requerDocumentacao;
        this.requerBiometria = requerBiometria;
        this.requerToken = requerToken;
        this.requerAssinaturaDigital = requerAssinaturaDigital;
        this.tempoSessaoMinutos = tempoSessaoMinutos;
        this.tentativasLoginMaximas = tentativasLoginMaximas;
        this.tempoBloqueioMinutos = tempoBloqueioMinutos;
        this.ativo = ativo;
        this.observacoes = observacoes;
        this.regrasEspeciais = regrasEspeciais;
        this.configuracoesAvancadas = configuracoesAvancadas;
        this.dataInicioVigencia = dataInicioVigencia;
        this.dataFimVigencia = dataFimVigencia;
        this.usuarioCriacao = usuarioCriacao;
        this.usuarioAprovacao = usuarioAprovacao;
        this.dataAprovacao = dataAprovacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PerfilRisco)) return false;
        final PerfilRisco other = (PerfilRisco) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$quantidadeTransacoesDiarias = this.getQuantidadeTransacoesDiarias();
        final java.lang.Object other$quantidadeTransacoesDiarias = other.getQuantidadeTransacoesDiarias();
        if (this$quantidadeTransacoesDiarias == null ? other$quantidadeTransacoesDiarias != null : !this$quantidadeTransacoesDiarias.equals(other$quantidadeTransacoesDiarias)) return false;
        final java.lang.Object this$quantidadeTransacoesMensais = this.getQuantidadeTransacoesMensais();
        final java.lang.Object other$quantidadeTransacoesMensais = other.getQuantidadeTransacoesMensais();
        if (this$quantidadeTransacoesMensais == null ? other$quantidadeTransacoesMensais != null : !this$quantidadeTransacoesMensais.equals(other$quantidadeTransacoesMensais)) return false;
        final java.lang.Object this$quantidadeTransacoesAnuais = this.getQuantidadeTransacoesAnuais();
        final java.lang.Object other$quantidadeTransacoesAnuais = other.getQuantidadeTransacoesAnuais();
        if (this$quantidadeTransacoesAnuais == null ? other$quantidadeTransacoesAnuais != null : !this$quantidadeTransacoesAnuais.equals(other$quantidadeTransacoesAnuais)) return false;
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
        final java.lang.Object this$tempoSessaoMinutos = this.getTempoSessaoMinutos();
        final java.lang.Object other$tempoSessaoMinutos = other.getTempoSessaoMinutos();
        if (this$tempoSessaoMinutos == null ? other$tempoSessaoMinutos != null : !this$tempoSessaoMinutos.equals(other$tempoSessaoMinutos)) return false;
        final java.lang.Object this$tentativasLoginMaximas = this.getTentativasLoginMaximas();
        final java.lang.Object other$tentativasLoginMaximas = other.getTentativasLoginMaximas();
        if (this$tentativasLoginMaximas == null ? other$tentativasLoginMaximas != null : !this$tentativasLoginMaximas.equals(other$tentativasLoginMaximas)) return false;
        final java.lang.Object this$tempoBloqueioMinutos = this.getTempoBloqueioMinutos();
        final java.lang.Object other$tempoBloqueioMinutos = other.getTempoBloqueioMinutos();
        if (this$tempoBloqueioMinutos == null ? other$tempoBloqueioMinutos != null : !this$tempoBloqueioMinutos.equals(other$tempoBloqueioMinutos)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
        final java.lang.Object this$codigoPerfil = this.getCodigoPerfil();
        final java.lang.Object other$codigoPerfil = other.getCodigoPerfil();
        if (this$codigoPerfil == null ? other$codigoPerfil != null : !this$codigoPerfil.equals(other$codigoPerfil)) return false;
        final java.lang.Object this$nomePerfil = this.getNomePerfil();
        final java.lang.Object other$nomePerfil = other.getNomePerfil();
        if (this$nomePerfil == null ? other$nomePerfil != null : !this$nomePerfil.equals(other$nomePerfil)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$nivelRisco = this.getNivelRisco();
        final java.lang.Object other$nivelRisco = other.getNivelRisco();
        if (this$nivelRisco == null ? other$nivelRisco != null : !this$nivelRisco.equals(other$nivelRisco)) return false;
        final java.lang.Object this$valorLimiteDiario = this.getValorLimiteDiario();
        final java.lang.Object other$valorLimiteDiario = other.getValorLimiteDiario();
        if (this$valorLimiteDiario == null ? other$valorLimiteDiario != null : !this$valorLimiteDiario.equals(other$valorLimiteDiario)) return false;
        final java.lang.Object this$valorLimiteMensal = this.getValorLimiteMensal();
        final java.lang.Object other$valorLimiteMensal = other.getValorLimiteMensal();
        if (this$valorLimiteMensal == null ? other$valorLimiteMensal != null : !this$valorLimiteMensal.equals(other$valorLimiteMensal)) return false;
        final java.lang.Object this$valorLimiteAnual = this.getValorLimiteAnual();
        final java.lang.Object other$valorLimiteAnual = other.getValorLimiteAnual();
        if (this$valorLimiteAnual == null ? other$valorLimiteAnual != null : !this$valorLimiteAnual.equals(other$valorLimiteAnual)) return false;
        final java.lang.Object this$percentualLimiteCredito = this.getPercentualLimiteCredito();
        final java.lang.Object other$percentualLimiteCredito = other.getPercentualLimiteCredito();
        if (this$percentualLimiteCredito == null ? other$percentualLimiteCredito != null : !this$percentualLimiteCredito.equals(other$percentualLimiteCredito)) return false;
        final java.lang.Object this$percentualLimiteInvestimento = this.getPercentualLimiteInvestimento();
        final java.lang.Object other$percentualLimiteInvestimento = other.getPercentualLimiteInvestimento();
        if (this$percentualLimiteInvestimento == null ? other$percentualLimiteInvestimento != null : !this$percentualLimiteInvestimento.equals(other$percentualLimiteInvestimento)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$regrasEspeciais = this.getRegrasEspeciais();
        final java.lang.Object other$regrasEspeciais = other.getRegrasEspeciais();
        if (this$regrasEspeciais == null ? other$regrasEspeciais != null : !this$regrasEspeciais.equals(other$regrasEspeciais)) return false;
        final java.lang.Object this$configuracoesAvancadas = this.getConfiguracoesAvancadas();
        final java.lang.Object other$configuracoesAvancadas = other.getConfiguracoesAvancadas();
        if (this$configuracoesAvancadas == null ? other$configuracoesAvancadas != null : !this$configuracoesAvancadas.equals(other$configuracoesAvancadas)) return false;
        final java.lang.Object this$dataInicioVigencia = this.getDataInicioVigencia();
        final java.lang.Object other$dataInicioVigencia = other.getDataInicioVigencia();
        if (this$dataInicioVigencia == null ? other$dataInicioVigencia != null : !this$dataInicioVigencia.equals(other$dataInicioVigencia)) return false;
        final java.lang.Object this$dataFimVigencia = this.getDataFimVigencia();
        final java.lang.Object other$dataFimVigencia = other.getDataFimVigencia();
        if (this$dataFimVigencia == null ? other$dataFimVigencia != null : !this$dataFimVigencia.equals(other$dataFimVigencia)) return false;
        final java.lang.Object this$usuarioCriacao = this.getUsuarioCriacao();
        final java.lang.Object other$usuarioCriacao = other.getUsuarioCriacao();
        if (this$usuarioCriacao == null ? other$usuarioCriacao != null : !this$usuarioCriacao.equals(other$usuarioCriacao)) return false;
        final java.lang.Object this$usuarioAprovacao = this.getUsuarioAprovacao();
        final java.lang.Object other$usuarioAprovacao = other.getUsuarioAprovacao();
        if (this$usuarioAprovacao == null ? other$usuarioAprovacao != null : !this$usuarioAprovacao.equals(other$usuarioAprovacao)) return false;
        final java.lang.Object this$dataAprovacao = this.getDataAprovacao();
        final java.lang.Object other$dataAprovacao = other.getDataAprovacao();
        if (this$dataAprovacao == null ? other$dataAprovacao != null : !this$dataAprovacao.equals(other$dataAprovacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PerfilRisco;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $quantidadeTransacoesDiarias = this.getQuantidadeTransacoesDiarias();
        result = result * PRIME + ($quantidadeTransacoesDiarias == null ? 43 : $quantidadeTransacoesDiarias.hashCode());
        final java.lang.Object $quantidadeTransacoesMensais = this.getQuantidadeTransacoesMensais();
        result = result * PRIME + ($quantidadeTransacoesMensais == null ? 43 : $quantidadeTransacoesMensais.hashCode());
        final java.lang.Object $quantidadeTransacoesAnuais = this.getQuantidadeTransacoesAnuais();
        result = result * PRIME + ($quantidadeTransacoesAnuais == null ? 43 : $quantidadeTransacoesAnuais.hashCode());
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
        final java.lang.Object $tempoSessaoMinutos = this.getTempoSessaoMinutos();
        result = result * PRIME + ($tempoSessaoMinutos == null ? 43 : $tempoSessaoMinutos.hashCode());
        final java.lang.Object $tentativasLoginMaximas = this.getTentativasLoginMaximas();
        result = result * PRIME + ($tentativasLoginMaximas == null ? 43 : $tentativasLoginMaximas.hashCode());
        final java.lang.Object $tempoBloqueioMinutos = this.getTempoBloqueioMinutos();
        result = result * PRIME + ($tempoBloqueioMinutos == null ? 43 : $tempoBloqueioMinutos.hashCode());
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
        final java.lang.Object $codigoPerfil = this.getCodigoPerfil();
        result = result * PRIME + ($codigoPerfil == null ? 43 : $codigoPerfil.hashCode());
        final java.lang.Object $nomePerfil = this.getNomePerfil();
        result = result * PRIME + ($nomePerfil == null ? 43 : $nomePerfil.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $nivelRisco = this.getNivelRisco();
        result = result * PRIME + ($nivelRisco == null ? 43 : $nivelRisco.hashCode());
        final java.lang.Object $valorLimiteDiario = this.getValorLimiteDiario();
        result = result * PRIME + ($valorLimiteDiario == null ? 43 : $valorLimiteDiario.hashCode());
        final java.lang.Object $valorLimiteMensal = this.getValorLimiteMensal();
        result = result * PRIME + ($valorLimiteMensal == null ? 43 : $valorLimiteMensal.hashCode());
        final java.lang.Object $valorLimiteAnual = this.getValorLimiteAnual();
        result = result * PRIME + ($valorLimiteAnual == null ? 43 : $valorLimiteAnual.hashCode());
        final java.lang.Object $percentualLimiteCredito = this.getPercentualLimiteCredito();
        result = result * PRIME + ($percentualLimiteCredito == null ? 43 : $percentualLimiteCredito.hashCode());
        final java.lang.Object $percentualLimiteInvestimento = this.getPercentualLimiteInvestimento();
        result = result * PRIME + ($percentualLimiteInvestimento == null ? 43 : $percentualLimiteInvestimento.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $regrasEspeciais = this.getRegrasEspeciais();
        result = result * PRIME + ($regrasEspeciais == null ? 43 : $regrasEspeciais.hashCode());
        final java.lang.Object $configuracoesAvancadas = this.getConfiguracoesAvancadas();
        result = result * PRIME + ($configuracoesAvancadas == null ? 43 : $configuracoesAvancadas.hashCode());
        final java.lang.Object $dataInicioVigencia = this.getDataInicioVigencia();
        result = result * PRIME + ($dataInicioVigencia == null ? 43 : $dataInicioVigencia.hashCode());
        final java.lang.Object $dataFimVigencia = this.getDataFimVigencia();
        result = result * PRIME + ($dataFimVigencia == null ? 43 : $dataFimVigencia.hashCode());
        final java.lang.Object $usuarioCriacao = this.getUsuarioCriacao();
        result = result * PRIME + ($usuarioCriacao == null ? 43 : $usuarioCriacao.hashCode());
        final java.lang.Object $usuarioAprovacao = this.getUsuarioAprovacao();
        result = result * PRIME + ($usuarioAprovacao == null ? 43 : $usuarioAprovacao.hashCode());
        final java.lang.Object $dataAprovacao = this.getDataAprovacao();
        result = result * PRIME + ($dataAprovacao == null ? 43 : $dataAprovacao.hashCode());
        return result;
    }
}
