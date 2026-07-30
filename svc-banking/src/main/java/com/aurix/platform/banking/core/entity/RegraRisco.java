package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "regras_risco", schema = "aurix")
public class RegraRisco extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoRegra;
    @Column(nullable = false)
    private String nomeRegra;
    @Column(length = 1000)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRegra tipoRegra;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaRegra categoriaRegra;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelPrioridade nivelPrioridade;
    @Column(nullable = false)
    private String expressaoRegra;
    @Column(length = 1000)
    private String condicoes;
    @Column(length = 1000)
    private String acoes;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorLimite;
    @Column(precision = 5, scale = 4)
    private BigDecimal percentualLimite;
    @Column
    private Integer quantidadeLimite;
    @Column
    private Integer tempoLimiteMinutos;
    @Column
    private Boolean ativa = true;
    @Column
    private Boolean critica = false;
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
    private Boolean requerNotificacao = true;
    @Column
    private Boolean requerBloqueio = false;
    @Column
    private Boolean requerAuditoria = true;
    @Column
    private Integer pesoRegra = 1;
    @Column
    private Integer scoreMinimo = 0;
    @Column
    private Integer scoreMaximo = 100;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "parametros_regra", columnDefinition = "JSONB")
    private String parametrosRegra;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracoes_especiais", columnDefinition = "JSONB")
    private String configuracoesEspeciais;
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


    public enum TipoRegra {
        VALOR, QUANTIDADE, FREQUENCIA, HORARIO, LOCALIZACAO, DISPOSITIVO, COMPORTAMENTO, HISTORICO, RELACIONAMENTO, COMPLIANCE, FRAUDE, LAVAGEM_DINHEIRO, TERRORISMO, OUTROS;
    }


    public enum CategoriaRegra {
        PREVENTIVA, DETECTIVA, CORRETIVA, MONITORAMENTO, ALERTA, BLOQUEIO, APROVACAO, AUDITORIA, COMPLIANCE, FRAUDE;
    }


    public enum NivelPrioridade {
        BAIXA, MEDIA, ALTA, CRITICA;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoRegra() {
        return this.codigoRegra;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeRegra() {
        return this.nomeRegra;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoRegra getTipoRegra() {
        return this.tipoRegra;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaRegra getCategoriaRegra() {
        return this.categoriaRegra;
    }

    @java.lang.SuppressWarnings("all")
    public NivelPrioridade getNivelPrioridade() {
        return this.nivelPrioridade;
    }

    @java.lang.SuppressWarnings("all")
    public String getExpressaoRegra() {
        return this.expressaoRegra;
    }

    @java.lang.SuppressWarnings("all")
    public String getCondicoes() {
        return this.condicoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getAcoes() {
        return this.acoes;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorLimite() {
        return this.valorLimite;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualLimite() {
        return this.percentualLimite;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getQuantidadeLimite() {
        return this.quantidadeLimite;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTempoLimiteMinutos() {
        return this.tempoLimiteMinutos;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtiva() {
        return this.ativa;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getCritica() {
        return this.critica;
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
    public Boolean getRequerNotificacao() {
        return this.requerNotificacao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerBloqueio() {
        return this.requerBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerAuditoria() {
        return this.requerAuditoria;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getPesoRegra() {
        return this.pesoRegra;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getScoreMinimo() {
        return this.scoreMinimo;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getScoreMaximo() {
        return this.scoreMaximo;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getParametrosRegra() {
        return this.parametrosRegra;
    }

    @java.lang.SuppressWarnings("all")
    public String getConfiguracoesEspeciais() {
        return this.configuracoesEspeciais;
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
    public void setCodigoRegra(final String codigoRegra) {
        this.codigoRegra = codigoRegra;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeRegra(final String nomeRegra) {
        this.nomeRegra = nomeRegra;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoRegra(final TipoRegra tipoRegra) {
        this.tipoRegra = tipoRegra;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoriaRegra(final CategoriaRegra categoriaRegra) {
        this.categoriaRegra = categoriaRegra;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelPrioridade(final NivelPrioridade nivelPrioridade) {
        this.nivelPrioridade = nivelPrioridade;
    }

    @java.lang.SuppressWarnings("all")
    public void setExpressaoRegra(final String expressaoRegra) {
        this.expressaoRegra = expressaoRegra;
    }

    @java.lang.SuppressWarnings("all")
    public void setCondicoes(final String condicoes) {
        this.condicoes = condicoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setAcoes(final String acoes) {
        this.acoes = acoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorLimite(final BigDecimal valorLimite) {
        this.valorLimite = valorLimite;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualLimite(final BigDecimal percentualLimite) {
        this.percentualLimite = percentualLimite;
    }

    @java.lang.SuppressWarnings("all")
    public void setQuantidadeLimite(final Integer quantidadeLimite) {
        this.quantidadeLimite = quantidadeLimite;
    }

    @java.lang.SuppressWarnings("all")
    public void setTempoLimiteMinutos(final Integer tempoLimiteMinutos) {
        this.tempoLimiteMinutos = tempoLimiteMinutos;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtiva(final Boolean ativa) {
        this.ativa = ativa;
    }

    @java.lang.SuppressWarnings("all")
    public void setCritica(final Boolean critica) {
        this.critica = critica;
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
    public void setRequerNotificacao(final Boolean requerNotificacao) {
        this.requerNotificacao = requerNotificacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerBloqueio(final Boolean requerBloqueio) {
        this.requerBloqueio = requerBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerAuditoria(final Boolean requerAuditoria) {
        this.requerAuditoria = requerAuditoria;
    }

    @java.lang.SuppressWarnings("all")
    public void setPesoRegra(final Integer pesoRegra) {
        this.pesoRegra = pesoRegra;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreMinimo(final Integer scoreMinimo) {
        this.scoreMinimo = scoreMinimo;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreMaximo(final Integer scoreMaximo) {
        this.scoreMaximo = scoreMaximo;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setParametrosRegra(final String parametrosRegra) {
        this.parametrosRegra = parametrosRegra;
    }

    @java.lang.SuppressWarnings("all")
    public void setConfiguracoesEspeciais(final String configuracoesEspeciais) {
        this.configuracoesEspeciais = configuracoesEspeciais;
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
        return "RegraRisco(id=" + this.getId() + ", codigoRegra=" + this.getCodigoRegra() + ", nomeRegra=" + this.getNomeRegra() + ", descricao=" + this.getDescricao() + ", tipoRegra=" + this.getTipoRegra() + ", categoriaRegra=" + this.getCategoriaRegra() + ", nivelPrioridade=" + this.getNivelPrioridade() + ", expressaoRegra=" + this.getExpressaoRegra() + ", condicoes=" + this.getCondicoes() + ", acoes=" + this.getAcoes() + ", valorLimite=" + this.getValorLimite() + ", percentualLimite=" + this.getPercentualLimite() + ", quantidadeLimite=" + this.getQuantidadeLimite() + ", tempoLimiteMinutos=" + this.getTempoLimiteMinutos() + ", ativa=" + this.getAtiva() + ", critica=" + this.getCritica() + ", requerAprovacao=" + this.getRequerAprovacao() + ", requerDocumentacao=" + this.getRequerDocumentacao() + ", requerBiometria=" + this.getRequerBiometria() + ", requerToken=" + this.getRequerToken() + ", requerAssinaturaDigital=" + this.getRequerAssinaturaDigital() + ", requerNotificacao=" + this.getRequerNotificacao() + ", requerBloqueio=" + this.getRequerBloqueio() + ", requerAuditoria=" + this.getRequerAuditoria() + ", pesoRegra=" + this.getPesoRegra() + ", scoreMinimo=" + this.getScoreMinimo() + ", scoreMaximo=" + this.getScoreMaximo() + ", observacoes=" + this.getObservacoes() + ", parametrosRegra=" + this.getParametrosRegra() + ", configuracoesEspeciais=" + this.getConfiguracoesEspeciais() + ", dataInicioVigencia=" + this.getDataInicioVigencia() + ", dataFimVigencia=" + this.getDataFimVigencia() + ", usuarioCriacao=" + this.getUsuarioCriacao() + ", usuarioAprovacao=" + this.getUsuarioAprovacao() + ", dataAprovacao=" + this.getDataAprovacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public RegraRisco() {
    }

    @java.lang.SuppressWarnings("all")
    public RegraRisco(final Long id, final String codigoRegra, final String nomeRegra, final String descricao, final TipoRegra tipoRegra, final CategoriaRegra categoriaRegra, final NivelPrioridade nivelPrioridade, final String expressaoRegra, final String condicoes, final String acoes, final BigDecimal valorLimite, final BigDecimal percentualLimite, final Integer quantidadeLimite, final Integer tempoLimiteMinutos, final Boolean ativa, final Boolean critica, final Boolean requerAprovacao, final Boolean requerDocumentacao, final Boolean requerBiometria, final Boolean requerToken, final Boolean requerAssinaturaDigital, final Boolean requerNotificacao, final Boolean requerBloqueio, final Boolean requerAuditoria, final Integer pesoRegra, final Integer scoreMinimo, final Integer scoreMaximo, final String observacoes, final String parametrosRegra, final String configuracoesEspeciais, final LocalDateTime dataInicioVigencia, final LocalDateTime dataFimVigencia, final String usuarioCriacao, final String usuarioAprovacao, final LocalDateTime dataAprovacao) {
        this.setId(id);
        this.codigoRegra = codigoRegra;
        this.nomeRegra = nomeRegra;
        this.descricao = descricao;
        this.tipoRegra = tipoRegra;
        this.categoriaRegra = categoriaRegra;
        this.nivelPrioridade = nivelPrioridade;
        this.expressaoRegra = expressaoRegra;
        this.condicoes = condicoes;
        this.acoes = acoes;
        this.valorLimite = valorLimite;
        this.percentualLimite = percentualLimite;
        this.quantidadeLimite = quantidadeLimite;
        this.tempoLimiteMinutos = tempoLimiteMinutos;
        this.ativa = ativa;
        this.critica = critica;
        this.requerAprovacao = requerAprovacao;
        this.requerDocumentacao = requerDocumentacao;
        this.requerBiometria = requerBiometria;
        this.requerToken = requerToken;
        this.requerAssinaturaDigital = requerAssinaturaDigital;
        this.requerNotificacao = requerNotificacao;
        this.requerBloqueio = requerBloqueio;
        this.requerAuditoria = requerAuditoria;
        this.pesoRegra = pesoRegra;
        this.scoreMinimo = scoreMinimo;
        this.scoreMaximo = scoreMaximo;
        this.observacoes = observacoes;
        this.parametrosRegra = parametrosRegra;
        this.configuracoesEspeciais = configuracoesEspeciais;
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
        if (!(o instanceof RegraRisco)) return false;
        final RegraRisco other = (RegraRisco) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$quantidadeLimite = this.getQuantidadeLimite();
        final java.lang.Object other$quantidadeLimite = other.getQuantidadeLimite();
        if (this$quantidadeLimite == null ? other$quantidadeLimite != null : !this$quantidadeLimite.equals(other$quantidadeLimite)) return false;
        final java.lang.Object this$tempoLimiteMinutos = this.getTempoLimiteMinutos();
        final java.lang.Object other$tempoLimiteMinutos = other.getTempoLimiteMinutos();
        if (this$tempoLimiteMinutos == null ? other$tempoLimiteMinutos != null : !this$tempoLimiteMinutos.equals(other$tempoLimiteMinutos)) return false;
        final java.lang.Object this$ativa = this.getAtiva();
        final java.lang.Object other$ativa = other.getAtiva();
        if (this$ativa == null ? other$ativa != null : !this$ativa.equals(other$ativa)) return false;
        final java.lang.Object this$critica = this.getCritica();
        final java.lang.Object other$critica = other.getCritica();
        if (this$critica == null ? other$critica != null : !this$critica.equals(other$critica)) return false;
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
        final java.lang.Object this$requerNotificacao = this.getRequerNotificacao();
        final java.lang.Object other$requerNotificacao = other.getRequerNotificacao();
        if (this$requerNotificacao == null ? other$requerNotificacao != null : !this$requerNotificacao.equals(other$requerNotificacao)) return false;
        final java.lang.Object this$requerBloqueio = this.getRequerBloqueio();
        final java.lang.Object other$requerBloqueio = other.getRequerBloqueio();
        if (this$requerBloqueio == null ? other$requerBloqueio != null : !this$requerBloqueio.equals(other$requerBloqueio)) return false;
        final java.lang.Object this$requerAuditoria = this.getRequerAuditoria();
        final java.lang.Object other$requerAuditoria = other.getRequerAuditoria();
        if (this$requerAuditoria == null ? other$requerAuditoria != null : !this$requerAuditoria.equals(other$requerAuditoria)) return false;
        final java.lang.Object this$pesoRegra = this.getPesoRegra();
        final java.lang.Object other$pesoRegra = other.getPesoRegra();
        if (this$pesoRegra == null ? other$pesoRegra != null : !this$pesoRegra.equals(other$pesoRegra)) return false;
        final java.lang.Object this$scoreMinimo = this.getScoreMinimo();
        final java.lang.Object other$scoreMinimo = other.getScoreMinimo();
        if (this$scoreMinimo == null ? other$scoreMinimo != null : !this$scoreMinimo.equals(other$scoreMinimo)) return false;
        final java.lang.Object this$scoreMaximo = this.getScoreMaximo();
        final java.lang.Object other$scoreMaximo = other.getScoreMaximo();
        if (this$scoreMaximo == null ? other$scoreMaximo != null : !this$scoreMaximo.equals(other$scoreMaximo)) return false;
        final java.lang.Object this$codigoRegra = this.getCodigoRegra();
        final java.lang.Object other$codigoRegra = other.getCodigoRegra();
        if (this$codigoRegra == null ? other$codigoRegra != null : !this$codigoRegra.equals(other$codigoRegra)) return false;
        final java.lang.Object this$nomeRegra = this.getNomeRegra();
        final java.lang.Object other$nomeRegra = other.getNomeRegra();
        if (this$nomeRegra == null ? other$nomeRegra != null : !this$nomeRegra.equals(other$nomeRegra)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoRegra = this.getTipoRegra();
        final java.lang.Object other$tipoRegra = other.getTipoRegra();
        if (this$tipoRegra == null ? other$tipoRegra != null : !this$tipoRegra.equals(other$tipoRegra)) return false;
        final java.lang.Object this$categoriaRegra = this.getCategoriaRegra();
        final java.lang.Object other$categoriaRegra = other.getCategoriaRegra();
        if (this$categoriaRegra == null ? other$categoriaRegra != null : !this$categoriaRegra.equals(other$categoriaRegra)) return false;
        final java.lang.Object this$nivelPrioridade = this.getNivelPrioridade();
        final java.lang.Object other$nivelPrioridade = other.getNivelPrioridade();
        if (this$nivelPrioridade == null ? other$nivelPrioridade != null : !this$nivelPrioridade.equals(other$nivelPrioridade)) return false;
        final java.lang.Object this$expressaoRegra = this.getExpressaoRegra();
        final java.lang.Object other$expressaoRegra = other.getExpressaoRegra();
        if (this$expressaoRegra == null ? other$expressaoRegra != null : !this$expressaoRegra.equals(other$expressaoRegra)) return false;
        final java.lang.Object this$condicoes = this.getCondicoes();
        final java.lang.Object other$condicoes = other.getCondicoes();
        if (this$condicoes == null ? other$condicoes != null : !this$condicoes.equals(other$condicoes)) return false;
        final java.lang.Object this$acoes = this.getAcoes();
        final java.lang.Object other$acoes = other.getAcoes();
        if (this$acoes == null ? other$acoes != null : !this$acoes.equals(other$acoes)) return false;
        final java.lang.Object this$valorLimite = this.getValorLimite();
        final java.lang.Object other$valorLimite = other.getValorLimite();
        if (this$valorLimite == null ? other$valorLimite != null : !this$valorLimite.equals(other$valorLimite)) return false;
        final java.lang.Object this$percentualLimite = this.getPercentualLimite();
        final java.lang.Object other$percentualLimite = other.getPercentualLimite();
        if (this$percentualLimite == null ? other$percentualLimite != null : !this$percentualLimite.equals(other$percentualLimite)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$parametrosRegra = this.getParametrosRegra();
        final java.lang.Object other$parametrosRegra = other.getParametrosRegra();
        if (this$parametrosRegra == null ? other$parametrosRegra != null : !this$parametrosRegra.equals(other$parametrosRegra)) return false;
        final java.lang.Object this$configuracoesEspeciais = this.getConfiguracoesEspeciais();
        final java.lang.Object other$configuracoesEspeciais = other.getConfiguracoesEspeciais();
        if (this$configuracoesEspeciais == null ? other$configuracoesEspeciais != null : !this$configuracoesEspeciais.equals(other$configuracoesEspeciais)) return false;
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
        return other instanceof RegraRisco;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $quantidadeLimite = this.getQuantidadeLimite();
        result = result * PRIME + ($quantidadeLimite == null ? 43 : $quantidadeLimite.hashCode());
        final java.lang.Object $tempoLimiteMinutos = this.getTempoLimiteMinutos();
        result = result * PRIME + ($tempoLimiteMinutos == null ? 43 : $tempoLimiteMinutos.hashCode());
        final java.lang.Object $ativa = this.getAtiva();
        result = result * PRIME + ($ativa == null ? 43 : $ativa.hashCode());
        final java.lang.Object $critica = this.getCritica();
        result = result * PRIME + ($critica == null ? 43 : $critica.hashCode());
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
        final java.lang.Object $requerNotificacao = this.getRequerNotificacao();
        result = result * PRIME + ($requerNotificacao == null ? 43 : $requerNotificacao.hashCode());
        final java.lang.Object $requerBloqueio = this.getRequerBloqueio();
        result = result * PRIME + ($requerBloqueio == null ? 43 : $requerBloqueio.hashCode());
        final java.lang.Object $requerAuditoria = this.getRequerAuditoria();
        result = result * PRIME + ($requerAuditoria == null ? 43 : $requerAuditoria.hashCode());
        final java.lang.Object $pesoRegra = this.getPesoRegra();
        result = result * PRIME + ($pesoRegra == null ? 43 : $pesoRegra.hashCode());
        final java.lang.Object $scoreMinimo = this.getScoreMinimo();
        result = result * PRIME + ($scoreMinimo == null ? 43 : $scoreMinimo.hashCode());
        final java.lang.Object $scoreMaximo = this.getScoreMaximo();
        result = result * PRIME + ($scoreMaximo == null ? 43 : $scoreMaximo.hashCode());
        final java.lang.Object $codigoRegra = this.getCodigoRegra();
        result = result * PRIME + ($codigoRegra == null ? 43 : $codigoRegra.hashCode());
        final java.lang.Object $nomeRegra = this.getNomeRegra();
        result = result * PRIME + ($nomeRegra == null ? 43 : $nomeRegra.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoRegra = this.getTipoRegra();
        result = result * PRIME + ($tipoRegra == null ? 43 : $tipoRegra.hashCode());
        final java.lang.Object $categoriaRegra = this.getCategoriaRegra();
        result = result * PRIME + ($categoriaRegra == null ? 43 : $categoriaRegra.hashCode());
        final java.lang.Object $nivelPrioridade = this.getNivelPrioridade();
        result = result * PRIME + ($nivelPrioridade == null ? 43 : $nivelPrioridade.hashCode());
        final java.lang.Object $expressaoRegra = this.getExpressaoRegra();
        result = result * PRIME + ($expressaoRegra == null ? 43 : $expressaoRegra.hashCode());
        final java.lang.Object $condicoes = this.getCondicoes();
        result = result * PRIME + ($condicoes == null ? 43 : $condicoes.hashCode());
        final java.lang.Object $acoes = this.getAcoes();
        result = result * PRIME + ($acoes == null ? 43 : $acoes.hashCode());
        final java.lang.Object $valorLimite = this.getValorLimite();
        result = result * PRIME + ($valorLimite == null ? 43 : $valorLimite.hashCode());
        final java.lang.Object $percentualLimite = this.getPercentualLimite();
        result = result * PRIME + ($percentualLimite == null ? 43 : $percentualLimite.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $parametrosRegra = this.getParametrosRegra();
        result = result * PRIME + ($parametrosRegra == null ? 43 : $parametrosRegra.hashCode());
        final java.lang.Object $configuracoesEspeciais = this.getConfiguracoesEspeciais();
        result = result * PRIME + ($configuracoesEspeciais == null ? 43 : $configuracoesEspeciais.hashCode());
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
