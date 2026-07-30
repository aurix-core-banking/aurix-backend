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
@Table(name = "eventos_risco", schema = "aurix")
public class EventoRisco extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoEvento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id")
    private Conta conta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_id")
    private Transacao transacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquidacao_id")
    private Liquidacao liquidacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEvento tipoEvento;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaEvento categoriaEvento;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelRisco nivelRisco;
    @Column(nullable = false)
    private String titulo;
    @Column(length = 2000)
    private String descricao;
    @Column(length = 2000)
    private String detalhes;
    @Column
    private LocalDateTime dataEvento;
    @Column
    private LocalDateTime dataDetecao;
    @Column
    private LocalDateTime dataResolucao;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorEnvolvido;
    @Column
    private Integer scoreRisco;
    @Column
    private Boolean critico = false;
    @Column
    private Boolean urgente = false;
    @Column
    private Boolean resolvido = false;
    @Column
    private Boolean requerAcao = true;
    @Column
    private Boolean requerNotificacao = true;
    @Column
    private Boolean requerEscalacao = false;
    @Column
    private Boolean requerAuditoria = true;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_evento", columnDefinition = "JSONB")
    private String detalhesEvento;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "evidencias", columnDefinition = "JSONB")
    private String evidencias;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "acoes_tomadas", columnDefinition = "JSONB")
    private String acoesTomadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "notificacoes_enviadas", columnDefinition = "JSONB")
    private String notificacoesEnviadas;
    @Column
    private String usuarioDetecao;
    @Column
    private String usuarioResolucao;
    @Column
    private String sistemaOrigem;
    @Column
    private String codigoTransacao;
    @Column
    private String codigoLiquidacao;
    @Column
    private String codigoBacen;
    @Column
    private String codigoSPI;
    @Column
    private String codigoSTR;


    public enum TipoEvento {
        FRAUDE, LAVAGEM_DINHEIRO, TERRORISMO, COMPLIANCE, SEGURANCA, OPERACIONAL, CREDITO, LIQUIDEZ, MERCADO, CONCENTRACAO, SUSPEITA, ANOMALIA, VIOLACAO, INCIDENTE, OUTROS;
    }


    public enum CategoriaEvento {
        PREVENTIVA, DETECTIVA, CORRETIVA, MONITORAMENTO, ALERTA, BLOQUEIO, APROVACAO, AUDITORIA, COMPLIANCE, FRAUDE;
    }


    public enum NivelRisco {
        BAIXO, MEDIO, ALTO, CRITICO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoEvento() {
        return this.codigoEvento;
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
    public Liquidacao getLiquidacao() {
        return this.liquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoEvento getTipoEvento() {
        return this.tipoEvento;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaEvento getCategoriaEvento() {
        return this.categoriaEvento;
    }

    @java.lang.SuppressWarnings("all")
    public NivelRisco getNivelRisco() {
        return this.nivelRisco;
    }

    @java.lang.SuppressWarnings("all")
    public String getTitulo() {
        return this.titulo;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhes() {
        return this.detalhes;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataEvento() {
        return this.dataEvento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataDetecao() {
        return this.dataDetecao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataResolucao() {
        return this.dataResolucao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorEnvolvido() {
        return this.valorEnvolvido;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getScoreRisco() {
        return this.scoreRisco;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getCritico() {
        return this.critico;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getUrgente() {
        return this.urgente;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getResolvido() {
        return this.resolvido;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerAcao() {
        return this.requerAcao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerNotificacao() {
        return this.requerNotificacao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerEscalacao() {
        return this.requerEscalacao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerAuditoria() {
        return this.requerAuditoria;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesEvento() {
        return this.detalhesEvento;
    }

    @java.lang.SuppressWarnings("all")
    public String getEvidencias() {
        return this.evidencias;
    }

    @java.lang.SuppressWarnings("all")
    public String getAcoesTomadas() {
        return this.acoesTomadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getNotificacoesEnviadas() {
        return this.notificacoesEnviadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioDetecao() {
        return this.usuarioDetecao;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioResolucao() {
        return this.usuarioResolucao;
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
    public String getCodigoBacen() {
        return this.codigoBacen;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoSPI() {
        return this.codigoSPI;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoSTR() {
        return this.codigoSTR;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoEvento(final String codigoEvento) {
        this.codigoEvento = codigoEvento;
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
    public void setLiquidacao(final Liquidacao liquidacao) {
        this.liquidacao = liquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoEvento(final TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoriaEvento(final CategoriaEvento categoriaEvento) {
        this.categoriaEvento = categoriaEvento;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelRisco(final NivelRisco nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhes(final String detalhes) {
        this.detalhes = detalhes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEvento(final LocalDateTime dataEvento) {
        this.dataEvento = dataEvento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataDetecao(final LocalDateTime dataDetecao) {
        this.dataDetecao = dataDetecao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataResolucao(final LocalDateTime dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorEnvolvido(final BigDecimal valorEnvolvido) {
        this.valorEnvolvido = valorEnvolvido;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreRisco(final Integer scoreRisco) {
        this.scoreRisco = scoreRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setCritico(final Boolean critico) {
        this.critico = critico;
    }

    @java.lang.SuppressWarnings("all")
    public void setUrgente(final Boolean urgente) {
        this.urgente = urgente;
    }

    @java.lang.SuppressWarnings("all")
    public void setResolvido(final Boolean resolvido) {
        this.resolvido = resolvido;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerAcao(final Boolean requerAcao) {
        this.requerAcao = requerAcao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerNotificacao(final Boolean requerNotificacao) {
        this.requerNotificacao = requerNotificacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerEscalacao(final Boolean requerEscalacao) {
        this.requerEscalacao = requerEscalacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerAuditoria(final Boolean requerAuditoria) {
        this.requerAuditoria = requerAuditoria;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesEvento(final String detalhesEvento) {
        this.detalhesEvento = detalhesEvento;
    }

    @java.lang.SuppressWarnings("all")
    public void setEvidencias(final String evidencias) {
        this.evidencias = evidencias;
    }

    @java.lang.SuppressWarnings("all")
    public void setAcoesTomadas(final String acoesTomadas) {
        this.acoesTomadas = acoesTomadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setNotificacoesEnviadas(final String notificacoesEnviadas) {
        this.notificacoesEnviadas = notificacoesEnviadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioDetecao(final String usuarioDetecao) {
        this.usuarioDetecao = usuarioDetecao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioResolucao(final String usuarioResolucao) {
        this.usuarioResolucao = usuarioResolucao;
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

    @java.lang.SuppressWarnings("all")
    public void setCodigoBacen(final String codigoBacen) {
        this.codigoBacen = codigoBacen;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoSPI(final String codigoSPI) {
        this.codigoSPI = codigoSPI;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoSTR(final String codigoSTR) {
        this.codigoSTR = codigoSTR;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "EventoRisco(id=" + this.getId() + ", codigoEvento=" + this.getCodigoEvento() + ", conta=" + this.getConta() + ", transacao=" + this.getTransacao() + ", liquidacao=" + this.getLiquidacao() + ", tipoEvento=" + this.getTipoEvento() + ", categoriaEvento=" + this.getCategoriaEvento() + ", nivelRisco=" + this.getNivelRisco() + ", titulo=" + this.getTitulo() + ", descricao=" + this.getDescricao() + ", detalhes=" + this.getDetalhes() + ", dataEvento=" + this.getDataEvento() + ", dataDetecao=" + this.getDataDetecao() + ", dataResolucao=" + this.getDataResolucao() + ", valorEnvolvido=" + this.getValorEnvolvido() + ", scoreRisco=" + this.getScoreRisco() + ", critico=" + this.getCritico() + ", urgente=" + this.getUrgente() + ", resolvido=" + this.getResolvido() + ", requerAcao=" + this.getRequerAcao() + ", requerNotificacao=" + this.getRequerNotificacao() + ", requerEscalacao=" + this.getRequerEscalacao() + ", requerAuditoria=" + this.getRequerAuditoria() + ", observacoes=" + this.getObservacoes() + ", detalhesEvento=" + this.getDetalhesEvento() + ", evidencias=" + this.getEvidencias() + ", acoesTomadas=" + this.getAcoesTomadas() + ", notificacoesEnviadas=" + this.getNotificacoesEnviadas() + ", usuarioDetecao=" + this.getUsuarioDetecao() + ", usuarioResolucao=" + this.getUsuarioResolucao() + ", sistemaOrigem=" + this.getSistemaOrigem() + ", codigoTransacao=" + this.getCodigoTransacao() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ", codigoBacen=" + this.getCodigoBacen() + ", codigoSPI=" + this.getCodigoSPI() + ", codigoSTR=" + this.getCodigoSTR() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public EventoRisco() {
    }

    @java.lang.SuppressWarnings("all")
    public EventoRisco(final Long id, final String codigoEvento, final Conta conta, final Transacao transacao, final Liquidacao liquidacao, final TipoEvento tipoEvento, final CategoriaEvento categoriaEvento, final NivelRisco nivelRisco, final String titulo, final String descricao, final String detalhes, final LocalDateTime dataEvento, final LocalDateTime dataDetecao, final LocalDateTime dataResolucao, final BigDecimal valorEnvolvido, final Integer scoreRisco, final Boolean critico, final Boolean urgente, final Boolean resolvido, final Boolean requerAcao, final Boolean requerNotificacao, final Boolean requerEscalacao, final Boolean requerAuditoria, final String observacoes, final String detalhesEvento, final String evidencias, final String acoesTomadas, final String notificacoesEnviadas, final String usuarioDetecao, final String usuarioResolucao, final String sistemaOrigem, final String codigoTransacao, final String codigoLiquidacao, final String codigoBacen, final String codigoSPI, final String codigoSTR) {
        this.setId(id);
        this.codigoEvento = codigoEvento;
        this.conta = conta;
        this.transacao = transacao;
        this.liquidacao = liquidacao;
        this.tipoEvento = tipoEvento;
        this.categoriaEvento = categoriaEvento;
        this.nivelRisco = nivelRisco;
        this.titulo = titulo;
        this.descricao = descricao;
        this.detalhes = detalhes;
        this.dataEvento = dataEvento;
        this.dataDetecao = dataDetecao;
        this.dataResolucao = dataResolucao;
        this.valorEnvolvido = valorEnvolvido;
        this.scoreRisco = scoreRisco;
        this.critico = critico;
        this.urgente = urgente;
        this.resolvido = resolvido;
        this.requerAcao = requerAcao;
        this.requerNotificacao = requerNotificacao;
        this.requerEscalacao = requerEscalacao;
        this.requerAuditoria = requerAuditoria;
        this.observacoes = observacoes;
        this.detalhesEvento = detalhesEvento;
        this.evidencias = evidencias;
        this.acoesTomadas = acoesTomadas;
        this.notificacoesEnviadas = notificacoesEnviadas;
        this.usuarioDetecao = usuarioDetecao;
        this.usuarioResolucao = usuarioResolucao;
        this.sistemaOrigem = sistemaOrigem;
        this.codigoTransacao = codigoTransacao;
        this.codigoLiquidacao = codigoLiquidacao;
        this.codigoBacen = codigoBacen;
        this.codigoSPI = codigoSPI;
        this.codigoSTR = codigoSTR;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof EventoRisco)) return false;
        final EventoRisco other = (EventoRisco) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$scoreRisco = this.getScoreRisco();
        final java.lang.Object other$scoreRisco = other.getScoreRisco();
        if (this$scoreRisco == null ? other$scoreRisco != null : !this$scoreRisco.equals(other$scoreRisco)) return false;
        final java.lang.Object this$critico = this.getCritico();
        final java.lang.Object other$critico = other.getCritico();
        if (this$critico == null ? other$critico != null : !this$critico.equals(other$critico)) return false;
        final java.lang.Object this$urgente = this.getUrgente();
        final java.lang.Object other$urgente = other.getUrgente();
        if (this$urgente == null ? other$urgente != null : !this$urgente.equals(other$urgente)) return false;
        final java.lang.Object this$resolvido = this.getResolvido();
        final java.lang.Object other$resolvido = other.getResolvido();
        if (this$resolvido == null ? other$resolvido != null : !this$resolvido.equals(other$resolvido)) return false;
        final java.lang.Object this$requerAcao = this.getRequerAcao();
        final java.lang.Object other$requerAcao = other.getRequerAcao();
        if (this$requerAcao == null ? other$requerAcao != null : !this$requerAcao.equals(other$requerAcao)) return false;
        final java.lang.Object this$requerNotificacao = this.getRequerNotificacao();
        final java.lang.Object other$requerNotificacao = other.getRequerNotificacao();
        if (this$requerNotificacao == null ? other$requerNotificacao != null : !this$requerNotificacao.equals(other$requerNotificacao)) return false;
        final java.lang.Object this$requerEscalacao = this.getRequerEscalacao();
        final java.lang.Object other$requerEscalacao = other.getRequerEscalacao();
        if (this$requerEscalacao == null ? other$requerEscalacao != null : !this$requerEscalacao.equals(other$requerEscalacao)) return false;
        final java.lang.Object this$requerAuditoria = this.getRequerAuditoria();
        final java.lang.Object other$requerAuditoria = other.getRequerAuditoria();
        if (this$requerAuditoria == null ? other$requerAuditoria != null : !this$requerAuditoria.equals(other$requerAuditoria)) return false;
        final java.lang.Object this$codigoEvento = this.getCodigoEvento();
        final java.lang.Object other$codigoEvento = other.getCodigoEvento();
        if (this$codigoEvento == null ? other$codigoEvento != null : !this$codigoEvento.equals(other$codigoEvento)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$transacao = this.getTransacao();
        final java.lang.Object other$transacao = other.getTransacao();
        if (this$transacao == null ? other$transacao != null : !this$transacao.equals(other$transacao)) return false;
        final java.lang.Object this$liquidacao = this.getLiquidacao();
        final java.lang.Object other$liquidacao = other.getLiquidacao();
        if (this$liquidacao == null ? other$liquidacao != null : !this$liquidacao.equals(other$liquidacao)) return false;
        final java.lang.Object this$tipoEvento = this.getTipoEvento();
        final java.lang.Object other$tipoEvento = other.getTipoEvento();
        if (this$tipoEvento == null ? other$tipoEvento != null : !this$tipoEvento.equals(other$tipoEvento)) return false;
        final java.lang.Object this$categoriaEvento = this.getCategoriaEvento();
        final java.lang.Object other$categoriaEvento = other.getCategoriaEvento();
        if (this$categoriaEvento == null ? other$categoriaEvento != null : !this$categoriaEvento.equals(other$categoriaEvento)) return false;
        final java.lang.Object this$nivelRisco = this.getNivelRisco();
        final java.lang.Object other$nivelRisco = other.getNivelRisco();
        if (this$nivelRisco == null ? other$nivelRisco != null : !this$nivelRisco.equals(other$nivelRisco)) return false;
        final java.lang.Object this$titulo = this.getTitulo();
        final java.lang.Object other$titulo = other.getTitulo();
        if (this$titulo == null ? other$titulo != null : !this$titulo.equals(other$titulo)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$detalhes = this.getDetalhes();
        final java.lang.Object other$detalhes = other.getDetalhes();
        if (this$detalhes == null ? other$detalhes != null : !this$detalhes.equals(other$detalhes)) return false;
        final java.lang.Object this$dataEvento = this.getDataEvento();
        final java.lang.Object other$dataEvento = other.getDataEvento();
        if (this$dataEvento == null ? other$dataEvento != null : !this$dataEvento.equals(other$dataEvento)) return false;
        final java.lang.Object this$dataDetecao = this.getDataDetecao();
        final java.lang.Object other$dataDetecao = other.getDataDetecao();
        if (this$dataDetecao == null ? other$dataDetecao != null : !this$dataDetecao.equals(other$dataDetecao)) return false;
        final java.lang.Object this$dataResolucao = this.getDataResolucao();
        final java.lang.Object other$dataResolucao = other.getDataResolucao();
        if (this$dataResolucao == null ? other$dataResolucao != null : !this$dataResolucao.equals(other$dataResolucao)) return false;
        final java.lang.Object this$valorEnvolvido = this.getValorEnvolvido();
        final java.lang.Object other$valorEnvolvido = other.getValorEnvolvido();
        if (this$valorEnvolvido == null ? other$valorEnvolvido != null : !this$valorEnvolvido.equals(other$valorEnvolvido)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesEvento = this.getDetalhesEvento();
        final java.lang.Object other$detalhesEvento = other.getDetalhesEvento();
        if (this$detalhesEvento == null ? other$detalhesEvento != null : !this$detalhesEvento.equals(other$detalhesEvento)) return false;
        final java.lang.Object this$evidencias = this.getEvidencias();
        final java.lang.Object other$evidencias = other.getEvidencias();
        if (this$evidencias == null ? other$evidencias != null : !this$evidencias.equals(other$evidencias)) return false;
        final java.lang.Object this$acoesTomadas = this.getAcoesTomadas();
        final java.lang.Object other$acoesTomadas = other.getAcoesTomadas();
        if (this$acoesTomadas == null ? other$acoesTomadas != null : !this$acoesTomadas.equals(other$acoesTomadas)) return false;
        final java.lang.Object this$notificacoesEnviadas = this.getNotificacoesEnviadas();
        final java.lang.Object other$notificacoesEnviadas = other.getNotificacoesEnviadas();
        if (this$notificacoesEnviadas == null ? other$notificacoesEnviadas != null : !this$notificacoesEnviadas.equals(other$notificacoesEnviadas)) return false;
        final java.lang.Object this$usuarioDetecao = this.getUsuarioDetecao();
        final java.lang.Object other$usuarioDetecao = other.getUsuarioDetecao();
        if (this$usuarioDetecao == null ? other$usuarioDetecao != null : !this$usuarioDetecao.equals(other$usuarioDetecao)) return false;
        final java.lang.Object this$usuarioResolucao = this.getUsuarioResolucao();
        final java.lang.Object other$usuarioResolucao = other.getUsuarioResolucao();
        if (this$usuarioResolucao == null ? other$usuarioResolucao != null : !this$usuarioResolucao.equals(other$usuarioResolucao)) return false;
        final java.lang.Object this$sistemaOrigem = this.getSistemaOrigem();
        final java.lang.Object other$sistemaOrigem = other.getSistemaOrigem();
        if (this$sistemaOrigem == null ? other$sistemaOrigem != null : !this$sistemaOrigem.equals(other$sistemaOrigem)) return false;
        final java.lang.Object this$codigoTransacao = this.getCodigoTransacao();
        final java.lang.Object other$codigoTransacao = other.getCodigoTransacao();
        if (this$codigoTransacao == null ? other$codigoTransacao != null : !this$codigoTransacao.equals(other$codigoTransacao)) return false;
        final java.lang.Object this$codigoLiquidacao = this.getCodigoLiquidacao();
        final java.lang.Object other$codigoLiquidacao = other.getCodigoLiquidacao();
        if (this$codigoLiquidacao == null ? other$codigoLiquidacao != null : !this$codigoLiquidacao.equals(other$codigoLiquidacao)) return false;
        final java.lang.Object this$codigoBacen = this.getCodigoBacen();
        final java.lang.Object other$codigoBacen = other.getCodigoBacen();
        if (this$codigoBacen == null ? other$codigoBacen != null : !this$codigoBacen.equals(other$codigoBacen)) return false;
        final java.lang.Object this$codigoSPI = this.getCodigoSPI();
        final java.lang.Object other$codigoSPI = other.getCodigoSPI();
        if (this$codigoSPI == null ? other$codigoSPI != null : !this$codigoSPI.equals(other$codigoSPI)) return false;
        final java.lang.Object this$codigoSTR = this.getCodigoSTR();
        final java.lang.Object other$codigoSTR = other.getCodigoSTR();
        if (this$codigoSTR == null ? other$codigoSTR != null : !this$codigoSTR.equals(other$codigoSTR)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof EventoRisco;
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
        final java.lang.Object $critico = this.getCritico();
        result = result * PRIME + ($critico == null ? 43 : $critico.hashCode());
        final java.lang.Object $urgente = this.getUrgente();
        result = result * PRIME + ($urgente == null ? 43 : $urgente.hashCode());
        final java.lang.Object $resolvido = this.getResolvido();
        result = result * PRIME + ($resolvido == null ? 43 : $resolvido.hashCode());
        final java.lang.Object $requerAcao = this.getRequerAcao();
        result = result * PRIME + ($requerAcao == null ? 43 : $requerAcao.hashCode());
        final java.lang.Object $requerNotificacao = this.getRequerNotificacao();
        result = result * PRIME + ($requerNotificacao == null ? 43 : $requerNotificacao.hashCode());
        final java.lang.Object $requerEscalacao = this.getRequerEscalacao();
        result = result * PRIME + ($requerEscalacao == null ? 43 : $requerEscalacao.hashCode());
        final java.lang.Object $requerAuditoria = this.getRequerAuditoria();
        result = result * PRIME + ($requerAuditoria == null ? 43 : $requerAuditoria.hashCode());
        final java.lang.Object $codigoEvento = this.getCodigoEvento();
        result = result * PRIME + ($codigoEvento == null ? 43 : $codigoEvento.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $transacao = this.getTransacao();
        result = result * PRIME + ($transacao == null ? 43 : $transacao.hashCode());
        final java.lang.Object $liquidacao = this.getLiquidacao();
        result = result * PRIME + ($liquidacao == null ? 43 : $liquidacao.hashCode());
        final java.lang.Object $tipoEvento = this.getTipoEvento();
        result = result * PRIME + ($tipoEvento == null ? 43 : $tipoEvento.hashCode());
        final java.lang.Object $categoriaEvento = this.getCategoriaEvento();
        result = result * PRIME + ($categoriaEvento == null ? 43 : $categoriaEvento.hashCode());
        final java.lang.Object $nivelRisco = this.getNivelRisco();
        result = result * PRIME + ($nivelRisco == null ? 43 : $nivelRisco.hashCode());
        final java.lang.Object $titulo = this.getTitulo();
        result = result * PRIME + ($titulo == null ? 43 : $titulo.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $detalhes = this.getDetalhes();
        result = result * PRIME + ($detalhes == null ? 43 : $detalhes.hashCode());
        final java.lang.Object $dataEvento = this.getDataEvento();
        result = result * PRIME + ($dataEvento == null ? 43 : $dataEvento.hashCode());
        final java.lang.Object $dataDetecao = this.getDataDetecao();
        result = result * PRIME + ($dataDetecao == null ? 43 : $dataDetecao.hashCode());
        final java.lang.Object $dataResolucao = this.getDataResolucao();
        result = result * PRIME + ($dataResolucao == null ? 43 : $dataResolucao.hashCode());
        final java.lang.Object $valorEnvolvido = this.getValorEnvolvido();
        result = result * PRIME + ($valorEnvolvido == null ? 43 : $valorEnvolvido.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesEvento = this.getDetalhesEvento();
        result = result * PRIME + ($detalhesEvento == null ? 43 : $detalhesEvento.hashCode());
        final java.lang.Object $evidencias = this.getEvidencias();
        result = result * PRIME + ($evidencias == null ? 43 : $evidencias.hashCode());
        final java.lang.Object $acoesTomadas = this.getAcoesTomadas();
        result = result * PRIME + ($acoesTomadas == null ? 43 : $acoesTomadas.hashCode());
        final java.lang.Object $notificacoesEnviadas = this.getNotificacoesEnviadas();
        result = result * PRIME + ($notificacoesEnviadas == null ? 43 : $notificacoesEnviadas.hashCode());
        final java.lang.Object $usuarioDetecao = this.getUsuarioDetecao();
        result = result * PRIME + ($usuarioDetecao == null ? 43 : $usuarioDetecao.hashCode());
        final java.lang.Object $usuarioResolucao = this.getUsuarioResolucao();
        result = result * PRIME + ($usuarioResolucao == null ? 43 : $usuarioResolucao.hashCode());
        final java.lang.Object $sistemaOrigem = this.getSistemaOrigem();
        result = result * PRIME + ($sistemaOrigem == null ? 43 : $sistemaOrigem.hashCode());
        final java.lang.Object $codigoTransacao = this.getCodigoTransacao();
        result = result * PRIME + ($codigoTransacao == null ? 43 : $codigoTransacao.hashCode());
        final java.lang.Object $codigoLiquidacao = this.getCodigoLiquidacao();
        result = result * PRIME + ($codigoLiquidacao == null ? 43 : $codigoLiquidacao.hashCode());
        final java.lang.Object $codigoBacen = this.getCodigoBacen();
        result = result * PRIME + ($codigoBacen == null ? 43 : $codigoBacen.hashCode());
        final java.lang.Object $codigoSPI = this.getCodigoSPI();
        result = result * PRIME + ($codigoSPI == null ? 43 : $codigoSPI.hashCode());
        final java.lang.Object $codigoSTR = this.getCodigoSTR();
        result = result * PRIME + ($codigoSTR == null ? 43 : $codigoSTR.hashCode());
        return result;
    }
}
