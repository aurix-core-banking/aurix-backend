package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Transacao;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "itens_conciliacao", schema = "aurix")
public class ItemConciliacao extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conciliacao_id", nullable = false)
    private ConciliacaoBancaria conciliacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movimento_id")
    private MovimentoConta movimento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_id")
    private Transacao transacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquidacao_id")
    private Liquidacao liquidacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrigemItem origemItem;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConciliacao status = StatusConciliacao.PENDENTE;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorItem;
    @Column(nullable = false)
    private LocalDateTime dataItem;
    @Column(length = 1000)
    private String descricaoItem;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_item", columnDefinition = "JSONB")
    private String detalhesItem;
    @Column
    private String codigoReferencia;
    @Column
    private String codigoBacen;
    @Column
    private String codigoSPI;
    @Column
    private String codigoSTR;
    @Column
    private String codigoContraparte;
    @Column
    private Boolean processado = false;
    @Column
    private LocalDateTime dataProcessamento;
    @Column
    private String usuarioProcessamento;


    public enum OrigemItem {
        SISTEMA, EXTRATO, BACEN, SPI, STR, MANUAL;
    }


    public enum StatusConciliacao {
        PENDENTE, CONCILIADO, DIVERGENTE, NAO_CONCILIADO, PROCESSADO, REJEITADO;
    }

@java.lang.SuppressWarnings("all")
    public ConciliacaoBancaria getConciliacao() {
        return this.conciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public MovimentoConta getMovimento() {
        return this.movimento;
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
    public OrigemItem getOrigemItem() {
        return this.origemItem;
    }

    @java.lang.SuppressWarnings("all")
    public StatusConciliacao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorItem() {
        return this.valorItem;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataItem() {
        return this.dataItem;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricaoItem() {
        return this.descricaoItem;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesItem() {
        return this.detalhesItem;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoReferencia() {
        return this.codigoReferencia;
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
    public String getCodigoContraparte() {
        return this.codigoContraparte;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getProcessado() {
        return this.processado;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioProcessamento() {
        return this.usuarioProcessamento;
    }

@java.lang.SuppressWarnings("all")
    public void setConciliacao(final ConciliacaoBancaria conciliacao) {
        this.conciliacao = conciliacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setMovimento(final MovimentoConta movimento) {
        this.movimento = movimento;
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
    public void setOrigemItem(final OrigemItem origemItem) {
        this.origemItem = origemItem;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusConciliacao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorItem(final BigDecimal valorItem) {
        this.valorItem = valorItem;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataItem(final LocalDateTime dataItem) {
        this.dataItem = dataItem;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricaoItem(final String descricaoItem) {
        this.descricaoItem = descricaoItem;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesItem(final String detalhesItem) {
        this.detalhesItem = detalhesItem;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoReferencia(final String codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
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

    @java.lang.SuppressWarnings("all")
    public void setCodigoContraparte(final String codigoContraparte) {
        this.codigoContraparte = codigoContraparte;
    }

    @java.lang.SuppressWarnings("all")
    public void setProcessado(final Boolean processado) {
        this.processado = processado;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioProcessamento(final String usuarioProcessamento) {
        this.usuarioProcessamento = usuarioProcessamento;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ItemConciliacao(id=" + this.getId() + ", conciliacao=" + this.getConciliacao() + ", movimento=" + this.getMovimento() + ", transacao=" + this.getTransacao() + ", liquidacao=" + this.getLiquidacao() + ", origemItem=" + this.getOrigemItem() + ", status=" + this.getStatus() + ", valorItem=" + this.getValorItem() + ", dataItem=" + this.getDataItem() + ", descricaoItem=" + this.getDescricaoItem() + ", observacoes=" + this.getObservacoes() + ", detalhesItem=" + this.getDetalhesItem() + ", codigoReferencia=" + this.getCodigoReferencia() + ", codigoBacen=" + this.getCodigoBacen() + ", codigoSPI=" + this.getCodigoSPI() + ", codigoSTR=" + this.getCodigoSTR() + ", codigoContraparte=" + this.getCodigoContraparte() + ", processado=" + this.getProcessado() + ", dataProcessamento=" + this.getDataProcessamento() + ", usuarioProcessamento=" + this.getUsuarioProcessamento() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ItemConciliacao() {
    }

    @java.lang.SuppressWarnings("all")
    public ItemConciliacao(final Long id, final ConciliacaoBancaria conciliacao, final MovimentoConta movimento, final Transacao transacao, final Liquidacao liquidacao, final OrigemItem origemItem, final StatusConciliacao status, final BigDecimal valorItem, final LocalDateTime dataItem, final String descricaoItem, final String observacoes, final String detalhesItem, final String codigoReferencia, final String codigoBacen, final String codigoSPI, final String codigoSTR, final String codigoContraparte, final Boolean processado, final LocalDateTime dataProcessamento, final String usuarioProcessamento) {
        this.setId(id);
        this.conciliacao = conciliacao;
        this.movimento = movimento;
        this.transacao = transacao;
        this.liquidacao = liquidacao;
        this.origemItem = origemItem;
        this.status = status;
        this.valorItem = valorItem;
        this.dataItem = dataItem;
        this.descricaoItem = descricaoItem;
        this.observacoes = observacoes;
        this.detalhesItem = detalhesItem;
        this.codigoReferencia = codigoReferencia;
        this.codigoBacen = codigoBacen;
        this.codigoSPI = codigoSPI;
        this.codigoSTR = codigoSTR;
        this.codigoContraparte = codigoContraparte;
        this.processado = processado;
        this.dataProcessamento = dataProcessamento;
        this.usuarioProcessamento = usuarioProcessamento;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ItemConciliacao)) return false;
        final ItemConciliacao other = (ItemConciliacao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$processado = this.getProcessado();
        final java.lang.Object other$processado = other.getProcessado();
        if (this$processado == null ? other$processado != null : !this$processado.equals(other$processado)) return false;
        final java.lang.Object this$conciliacao = this.getConciliacao();
        final java.lang.Object other$conciliacao = other.getConciliacao();
        if (this$conciliacao == null ? other$conciliacao != null : !this$conciliacao.equals(other$conciliacao)) return false;
        final java.lang.Object this$movimento = this.getMovimento();
        final java.lang.Object other$movimento = other.getMovimento();
        if (this$movimento == null ? other$movimento != null : !this$movimento.equals(other$movimento)) return false;
        final java.lang.Object this$transacao = this.getTransacao();
        final java.lang.Object other$transacao = other.getTransacao();
        if (this$transacao == null ? other$transacao != null : !this$transacao.equals(other$transacao)) return false;
        final java.lang.Object this$liquidacao = this.getLiquidacao();
        final java.lang.Object other$liquidacao = other.getLiquidacao();
        if (this$liquidacao == null ? other$liquidacao != null : !this$liquidacao.equals(other$liquidacao)) return false;
        final java.lang.Object this$origemItem = this.getOrigemItem();
        final java.lang.Object other$origemItem = other.getOrigemItem();
        if (this$origemItem == null ? other$origemItem != null : !this$origemItem.equals(other$origemItem)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$valorItem = this.getValorItem();
        final java.lang.Object other$valorItem = other.getValorItem();
        if (this$valorItem == null ? other$valorItem != null : !this$valorItem.equals(other$valorItem)) return false;
        final java.lang.Object this$dataItem = this.getDataItem();
        final java.lang.Object other$dataItem = other.getDataItem();
        if (this$dataItem == null ? other$dataItem != null : !this$dataItem.equals(other$dataItem)) return false;
        final java.lang.Object this$descricaoItem = this.getDescricaoItem();
        final java.lang.Object other$descricaoItem = other.getDescricaoItem();
        if (this$descricaoItem == null ? other$descricaoItem != null : !this$descricaoItem.equals(other$descricaoItem)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesItem = this.getDetalhesItem();
        final java.lang.Object other$detalhesItem = other.getDetalhesItem();
        if (this$detalhesItem == null ? other$detalhesItem != null : !this$detalhesItem.equals(other$detalhesItem)) return false;
        final java.lang.Object this$codigoReferencia = this.getCodigoReferencia();
        final java.lang.Object other$codigoReferencia = other.getCodigoReferencia();
        if (this$codigoReferencia == null ? other$codigoReferencia != null : !this$codigoReferencia.equals(other$codigoReferencia)) return false;
        final java.lang.Object this$codigoBacen = this.getCodigoBacen();
        final java.lang.Object other$codigoBacen = other.getCodigoBacen();
        if (this$codigoBacen == null ? other$codigoBacen != null : !this$codigoBacen.equals(other$codigoBacen)) return false;
        final java.lang.Object this$codigoSPI = this.getCodigoSPI();
        final java.lang.Object other$codigoSPI = other.getCodigoSPI();
        if (this$codigoSPI == null ? other$codigoSPI != null : !this$codigoSPI.equals(other$codigoSPI)) return false;
        final java.lang.Object this$codigoSTR = this.getCodigoSTR();
        final java.lang.Object other$codigoSTR = other.getCodigoSTR();
        if (this$codigoSTR == null ? other$codigoSTR != null : !this$codigoSTR.equals(other$codigoSTR)) return false;
        final java.lang.Object this$codigoContraparte = this.getCodigoContraparte();
        final java.lang.Object other$codigoContraparte = other.getCodigoContraparte();
        if (this$codigoContraparte == null ? other$codigoContraparte != null : !this$codigoContraparte.equals(other$codigoContraparte)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$usuarioProcessamento = this.getUsuarioProcessamento();
        final java.lang.Object other$usuarioProcessamento = other.getUsuarioProcessamento();
        if (this$usuarioProcessamento == null ? other$usuarioProcessamento != null : !this$usuarioProcessamento.equals(other$usuarioProcessamento)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ItemConciliacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $processado = this.getProcessado();
        result = result * PRIME + ($processado == null ? 43 : $processado.hashCode());
        final java.lang.Object $conciliacao = this.getConciliacao();
        result = result * PRIME + ($conciliacao == null ? 43 : $conciliacao.hashCode());
        final java.lang.Object $movimento = this.getMovimento();
        result = result * PRIME + ($movimento == null ? 43 : $movimento.hashCode());
        final java.lang.Object $transacao = this.getTransacao();
        result = result * PRIME + ($transacao == null ? 43 : $transacao.hashCode());
        final java.lang.Object $liquidacao = this.getLiquidacao();
        result = result * PRIME + ($liquidacao == null ? 43 : $liquidacao.hashCode());
        final java.lang.Object $origemItem = this.getOrigemItem();
        result = result * PRIME + ($origemItem == null ? 43 : $origemItem.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $valorItem = this.getValorItem();
        result = result * PRIME + ($valorItem == null ? 43 : $valorItem.hashCode());
        final java.lang.Object $dataItem = this.getDataItem();
        result = result * PRIME + ($dataItem == null ? 43 : $dataItem.hashCode());
        final java.lang.Object $descricaoItem = this.getDescricaoItem();
        result = result * PRIME + ($descricaoItem == null ? 43 : $descricaoItem.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesItem = this.getDetalhesItem();
        result = result * PRIME + ($detalhesItem == null ? 43 : $detalhesItem.hashCode());
        final java.lang.Object $codigoReferencia = this.getCodigoReferencia();
        result = result * PRIME + ($codigoReferencia == null ? 43 : $codigoReferencia.hashCode());
        final java.lang.Object $codigoBacen = this.getCodigoBacen();
        result = result * PRIME + ($codigoBacen == null ? 43 : $codigoBacen.hashCode());
        final java.lang.Object $codigoSPI = this.getCodigoSPI();
        result = result * PRIME + ($codigoSPI == null ? 43 : $codigoSPI.hashCode());
        final java.lang.Object $codigoSTR = this.getCodigoSTR();
        result = result * PRIME + ($codigoSTR == null ? 43 : $codigoSTR.hashCode());
        final java.lang.Object $codigoContraparte = this.getCodigoContraparte();
        result = result * PRIME + ($codigoContraparte == null ? 43 : $codigoContraparte.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $usuarioProcessamento = this.getUsuarioProcessamento();
        result = result * PRIME + ($usuarioProcessamento == null ? 43 : $usuarioProcessamento.hashCode());
        return result;
    }
}
