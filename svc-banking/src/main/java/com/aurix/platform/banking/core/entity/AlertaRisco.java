package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "alertas_risco", schema = "aurix")
public class AlertaRisco extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoAlerta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliacao_risco_id")
    private AvaliacaoRisco avaliacaoRisco;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regra_risco_id")
    private RegraRisco regraRisco;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id")
    private Conta conta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_id")
    private Transacao transacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAlerta tipoAlerta;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelAlerta nivelAlerta;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAlerta status = StatusAlerta.ATIVO;
    @Column(nullable = false)
    private String titulo;
    @Column(length = 2000)
    private String descricao;
    @Column(length = 2000)
    private String detalhes;
    @Column
    private LocalDateTime dataAlerta;
    @Column
    private LocalDateTime dataAck;
    @Column
    private LocalDateTime dataResolucao;
    @Column
    private Boolean critico = false;
    @Column
    private Boolean urgente = false;
    @Column
    private Boolean requerAcao = true;
    @Column
    private Boolean requerNotificacao = true;
    @Column
    private Boolean requerEscalacao = false;
    @Column
    private Boolean requerAuditoria = true;
    @Column
    private Integer scoreRisco;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_alerta", columnDefinition = "JSONB")
    private String detalhesAlerta;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "acoes_tomadas", columnDefinition = "JSONB")
    private String acoesTomadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "notificacoes_enviadas", columnDefinition = "JSONB")
    private String notificacoesEnviadas;
    @Column
    private String usuarioAck;
    @Column
    private String usuarioResolucao;
    @Column
    private String sistemaOrigem;
    @Column
    private String codigoTransacao;
    @Column
    private String codigoLiquidacao;


    public enum TipoAlerta {
        FRAUDE, LAVAGEM_DINHEIRO, TERRORISMO, COMPLIANCE, SEGURANCA, OPERACIONAL, CREDITO, LIQUIDEZ, MERCADO, CONCENTRACAO, OUTROS;
    }


    public enum NivelAlerta {
        BAIXO, MEDIO, ALTO, CRITICO;
    }


    public enum StatusAlerta {
        ATIVO, ACK, RESOLVIDO, CANCELADO, ESCALADO, SUSPENSO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoAlerta() {
        return this.codigoAlerta;
    }

    @java.lang.SuppressWarnings("all")
    public AvaliacaoRisco getAvaliacaoRisco() {
        return this.avaliacaoRisco;
    }

    @java.lang.SuppressWarnings("all")
    public RegraRisco getRegraRisco() {
        return this.regraRisco;
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
    public TipoAlerta getTipoAlerta() {
        return this.tipoAlerta;
    }

    @java.lang.SuppressWarnings("all")
    public NivelAlerta getNivelAlerta() {
        return this.nivelAlerta;
    }

    @java.lang.SuppressWarnings("all")
    public StatusAlerta getStatus() {
        return this.status;
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
    public LocalDateTime getDataAlerta() {
        return this.dataAlerta;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAck() {
        return this.dataAck;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataResolucao() {
        return this.dataResolucao;
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
    public Integer getScoreRisco() {
        return this.scoreRisco;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesAlerta() {
        return this.detalhesAlerta;
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
    public String getUsuarioAck() {
        return this.usuarioAck;
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
    public void setCodigoAlerta(final String codigoAlerta) {
        this.codigoAlerta = codigoAlerta;
    }

    @java.lang.SuppressWarnings("all")
    public void setAvaliacaoRisco(final AvaliacaoRisco avaliacaoRisco) {
        this.avaliacaoRisco = avaliacaoRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegraRisco(final RegraRisco regraRisco) {
        this.regraRisco = regraRisco;
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
    public void setTipoAlerta(final TipoAlerta tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelAlerta(final NivelAlerta nivelAlerta) {
        this.nivelAlerta = nivelAlerta;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusAlerta status) {
        this.status = status;
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
    public void setDataAlerta(final LocalDateTime dataAlerta) {
        this.dataAlerta = dataAlerta;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAck(final LocalDateTime dataAck) {
        this.dataAck = dataAck;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataResolucao(final LocalDateTime dataResolucao) {
        this.dataResolucao = dataResolucao;
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
    public void setScoreRisco(final Integer scoreRisco) {
        this.scoreRisco = scoreRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesAlerta(final String detalhesAlerta) {
        this.detalhesAlerta = detalhesAlerta;
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
    public void setUsuarioAck(final String usuarioAck) {
        this.usuarioAck = usuarioAck;
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

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "AlertaRisco(id=" + this.getId() + ", codigoAlerta=" + this.getCodigoAlerta() + ", avaliacaoRisco=" + this.getAvaliacaoRisco() + ", regraRisco=" + this.getRegraRisco() + ", conta=" + this.getConta() + ", transacao=" + this.getTransacao() + ", tipoAlerta=" + this.getTipoAlerta() + ", nivelAlerta=" + this.getNivelAlerta() + ", status=" + this.getStatus() + ", titulo=" + this.getTitulo() + ", descricao=" + this.getDescricao() + ", detalhes=" + this.getDetalhes() + ", dataAlerta=" + this.getDataAlerta() + ", dataAck=" + this.getDataAck() + ", dataResolucao=" + this.getDataResolucao() + ", critico=" + this.getCritico() + ", urgente=" + this.getUrgente() + ", requerAcao=" + this.getRequerAcao() + ", requerNotificacao=" + this.getRequerNotificacao() + ", requerEscalacao=" + this.getRequerEscalacao() + ", requerAuditoria=" + this.getRequerAuditoria() + ", scoreRisco=" + this.getScoreRisco() + ", observacoes=" + this.getObservacoes() + ", detalhesAlerta=" + this.getDetalhesAlerta() + ", acoesTomadas=" + this.getAcoesTomadas() + ", notificacoesEnviadas=" + this.getNotificacoesEnviadas() + ", usuarioAck=" + this.getUsuarioAck() + ", usuarioResolucao=" + this.getUsuarioResolucao() + ", sistemaOrigem=" + this.getSistemaOrigem() + ", codigoTransacao=" + this.getCodigoTransacao() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public AlertaRisco() {
    }

    @java.lang.SuppressWarnings("all")
    public AlertaRisco(final Long id, final String codigoAlerta, final AvaliacaoRisco avaliacaoRisco, final RegraRisco regraRisco, final Conta conta, final Transacao transacao, final TipoAlerta tipoAlerta, final NivelAlerta nivelAlerta, final StatusAlerta status, final String titulo, final String descricao, final String detalhes, final LocalDateTime dataAlerta, final LocalDateTime dataAck, final LocalDateTime dataResolucao, final Boolean critico, final Boolean urgente, final Boolean requerAcao, final Boolean requerNotificacao, final Boolean requerEscalacao, final Boolean requerAuditoria, final Integer scoreRisco, final String observacoes, final String detalhesAlerta, final String acoesTomadas, final String notificacoesEnviadas, final String usuarioAck, final String usuarioResolucao, final String sistemaOrigem, final String codigoTransacao, final String codigoLiquidacao) {
        this.setId(id);
        this.codigoAlerta = codigoAlerta;
        this.avaliacaoRisco = avaliacaoRisco;
        this.regraRisco = regraRisco;
        this.conta = conta;
        this.transacao = transacao;
        this.tipoAlerta = tipoAlerta;
        this.nivelAlerta = nivelAlerta;
        this.status = status;
        this.titulo = titulo;
        this.descricao = descricao;
        this.detalhes = detalhes;
        this.dataAlerta = dataAlerta;
        this.dataAck = dataAck;
        this.dataResolucao = dataResolucao;
        this.critico = critico;
        this.urgente = urgente;
        this.requerAcao = requerAcao;
        this.requerNotificacao = requerNotificacao;
        this.requerEscalacao = requerEscalacao;
        this.requerAuditoria = requerAuditoria;
        this.scoreRisco = scoreRisco;
        this.observacoes = observacoes;
        this.detalhesAlerta = detalhesAlerta;
        this.acoesTomadas = acoesTomadas;
        this.notificacoesEnviadas = notificacoesEnviadas;
        this.usuarioAck = usuarioAck;
        this.usuarioResolucao = usuarioResolucao;
        this.sistemaOrigem = sistemaOrigem;
        this.codigoTransacao = codigoTransacao;
        this.codigoLiquidacao = codigoLiquidacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AlertaRisco)) return false;
        final AlertaRisco other = (AlertaRisco) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$critico = this.getCritico();
        final java.lang.Object other$critico = other.getCritico();
        if (this$critico == null ? other$critico != null : !this$critico.equals(other$critico)) return false;
        final java.lang.Object this$urgente = this.getUrgente();
        final java.lang.Object other$urgente = other.getUrgente();
        if (this$urgente == null ? other$urgente != null : !this$urgente.equals(other$urgente)) return false;
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
        final java.lang.Object this$scoreRisco = this.getScoreRisco();
        final java.lang.Object other$scoreRisco = other.getScoreRisco();
        if (this$scoreRisco == null ? other$scoreRisco != null : !this$scoreRisco.equals(other$scoreRisco)) return false;
        final java.lang.Object this$codigoAlerta = this.getCodigoAlerta();
        final java.lang.Object other$codigoAlerta = other.getCodigoAlerta();
        if (this$codigoAlerta == null ? other$codigoAlerta != null : !this$codigoAlerta.equals(other$codigoAlerta)) return false;
        final java.lang.Object this$avaliacaoRisco = this.getAvaliacaoRisco();
        final java.lang.Object other$avaliacaoRisco = other.getAvaliacaoRisco();
        if (this$avaliacaoRisco == null ? other$avaliacaoRisco != null : !this$avaliacaoRisco.equals(other$avaliacaoRisco)) return false;
        final java.lang.Object this$regraRisco = this.getRegraRisco();
        final java.lang.Object other$regraRisco = other.getRegraRisco();
        if (this$regraRisco == null ? other$regraRisco != null : !this$regraRisco.equals(other$regraRisco)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$transacao = this.getTransacao();
        final java.lang.Object other$transacao = other.getTransacao();
        if (this$transacao == null ? other$transacao != null : !this$transacao.equals(other$transacao)) return false;
        final java.lang.Object this$tipoAlerta = this.getTipoAlerta();
        final java.lang.Object other$tipoAlerta = other.getTipoAlerta();
        if (this$tipoAlerta == null ? other$tipoAlerta != null : !this$tipoAlerta.equals(other$tipoAlerta)) return false;
        final java.lang.Object this$nivelAlerta = this.getNivelAlerta();
        final java.lang.Object other$nivelAlerta = other.getNivelAlerta();
        if (this$nivelAlerta == null ? other$nivelAlerta != null : !this$nivelAlerta.equals(other$nivelAlerta)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$titulo = this.getTitulo();
        final java.lang.Object other$titulo = other.getTitulo();
        if (this$titulo == null ? other$titulo != null : !this$titulo.equals(other$titulo)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$detalhes = this.getDetalhes();
        final java.lang.Object other$detalhes = other.getDetalhes();
        if (this$detalhes == null ? other$detalhes != null : !this$detalhes.equals(other$detalhes)) return false;
        final java.lang.Object this$dataAlerta = this.getDataAlerta();
        final java.lang.Object other$dataAlerta = other.getDataAlerta();
        if (this$dataAlerta == null ? other$dataAlerta != null : !this$dataAlerta.equals(other$dataAlerta)) return false;
        final java.lang.Object this$dataAck = this.getDataAck();
        final java.lang.Object other$dataAck = other.getDataAck();
        if (this$dataAck == null ? other$dataAck != null : !this$dataAck.equals(other$dataAck)) return false;
        final java.lang.Object this$dataResolucao = this.getDataResolucao();
        final java.lang.Object other$dataResolucao = other.getDataResolucao();
        if (this$dataResolucao == null ? other$dataResolucao != null : !this$dataResolucao.equals(other$dataResolucao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesAlerta = this.getDetalhesAlerta();
        final java.lang.Object other$detalhesAlerta = other.getDetalhesAlerta();
        if (this$detalhesAlerta == null ? other$detalhesAlerta != null : !this$detalhesAlerta.equals(other$detalhesAlerta)) return false;
        final java.lang.Object this$acoesTomadas = this.getAcoesTomadas();
        final java.lang.Object other$acoesTomadas = other.getAcoesTomadas();
        if (this$acoesTomadas == null ? other$acoesTomadas != null : !this$acoesTomadas.equals(other$acoesTomadas)) return false;
        final java.lang.Object this$notificacoesEnviadas = this.getNotificacoesEnviadas();
        final java.lang.Object other$notificacoesEnviadas = other.getNotificacoesEnviadas();
        if (this$notificacoesEnviadas == null ? other$notificacoesEnviadas != null : !this$notificacoesEnviadas.equals(other$notificacoesEnviadas)) return false;
        final java.lang.Object this$usuarioAck = this.getUsuarioAck();
        final java.lang.Object other$usuarioAck = other.getUsuarioAck();
        if (this$usuarioAck == null ? other$usuarioAck != null : !this$usuarioAck.equals(other$usuarioAck)) return false;
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
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof AlertaRisco;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $critico = this.getCritico();
        result = result * PRIME + ($critico == null ? 43 : $critico.hashCode());
        final java.lang.Object $urgente = this.getUrgente();
        result = result * PRIME + ($urgente == null ? 43 : $urgente.hashCode());
        final java.lang.Object $requerAcao = this.getRequerAcao();
        result = result * PRIME + ($requerAcao == null ? 43 : $requerAcao.hashCode());
        final java.lang.Object $requerNotificacao = this.getRequerNotificacao();
        result = result * PRIME + ($requerNotificacao == null ? 43 : $requerNotificacao.hashCode());
        final java.lang.Object $requerEscalacao = this.getRequerEscalacao();
        result = result * PRIME + ($requerEscalacao == null ? 43 : $requerEscalacao.hashCode());
        final java.lang.Object $requerAuditoria = this.getRequerAuditoria();
        result = result * PRIME + ($requerAuditoria == null ? 43 : $requerAuditoria.hashCode());
        final java.lang.Object $scoreRisco = this.getScoreRisco();
        result = result * PRIME + ($scoreRisco == null ? 43 : $scoreRisco.hashCode());
        final java.lang.Object $codigoAlerta = this.getCodigoAlerta();
        result = result * PRIME + ($codigoAlerta == null ? 43 : $codigoAlerta.hashCode());
        final java.lang.Object $avaliacaoRisco = this.getAvaliacaoRisco();
        result = result * PRIME + ($avaliacaoRisco == null ? 43 : $avaliacaoRisco.hashCode());
        final java.lang.Object $regraRisco = this.getRegraRisco();
        result = result * PRIME + ($regraRisco == null ? 43 : $regraRisco.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $transacao = this.getTransacao();
        result = result * PRIME + ($transacao == null ? 43 : $transacao.hashCode());
        final java.lang.Object $tipoAlerta = this.getTipoAlerta();
        result = result * PRIME + ($tipoAlerta == null ? 43 : $tipoAlerta.hashCode());
        final java.lang.Object $nivelAlerta = this.getNivelAlerta();
        result = result * PRIME + ($nivelAlerta == null ? 43 : $nivelAlerta.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $titulo = this.getTitulo();
        result = result * PRIME + ($titulo == null ? 43 : $titulo.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $detalhes = this.getDetalhes();
        result = result * PRIME + ($detalhes == null ? 43 : $detalhes.hashCode());
        final java.lang.Object $dataAlerta = this.getDataAlerta();
        result = result * PRIME + ($dataAlerta == null ? 43 : $dataAlerta.hashCode());
        final java.lang.Object $dataAck = this.getDataAck();
        result = result * PRIME + ($dataAck == null ? 43 : $dataAck.hashCode());
        final java.lang.Object $dataResolucao = this.getDataResolucao();
        result = result * PRIME + ($dataResolucao == null ? 43 : $dataResolucao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesAlerta = this.getDetalhesAlerta();
        result = result * PRIME + ($detalhesAlerta == null ? 43 : $detalhesAlerta.hashCode());
        final java.lang.Object $acoesTomadas = this.getAcoesTomadas();
        result = result * PRIME + ($acoesTomadas == null ? 43 : $acoesTomadas.hashCode());
        final java.lang.Object $notificacoesEnviadas = this.getNotificacoesEnviadas();
        result = result * PRIME + ($notificacoesEnviadas == null ? 43 : $notificacoesEnviadas.hashCode());
        final java.lang.Object $usuarioAck = this.getUsuarioAck();
        result = result * PRIME + ($usuarioAck == null ? 43 : $usuarioAck.hashCode());
        final java.lang.Object $usuarioResolucao = this.getUsuarioResolucao();
        result = result * PRIME + ($usuarioResolucao == null ? 43 : $usuarioResolucao.hashCode());
        final java.lang.Object $sistemaOrigem = this.getSistemaOrigem();
        result = result * PRIME + ($sistemaOrigem == null ? 43 : $sistemaOrigem.hashCode());
        final java.lang.Object $codigoTransacao = this.getCodigoTransacao();
        result = result * PRIME + ($codigoTransacao == null ? 43 : $codigoTransacao.hashCode());
        final java.lang.Object $codigoLiquidacao = this.getCodigoLiquidacao();
        result = result * PRIME + ($codigoLiquidacao == null ? 43 : $codigoLiquidacao.hashCode());
        return result;
    }
}
