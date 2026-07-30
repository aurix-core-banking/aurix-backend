package com.aurix.platform.cards.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "transacoes_cartao", schema = "aurix")
public class TransacaoCartao extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoTransacao;
    @Column(name = "cartao_id", nullable = false)
    private Long cartaoId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipoTransacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTransacao status = StatusTransacao.PENDENTE;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valor;
    @Column(nullable = false)
    private LocalDateTime dataTransacao;
    @Column(length = 500)
    private String estabelecimento;
    @Column(length = 100)
    private String nsu;
    @Column(length = 100)
    private String autorizacao;
    @Column
    private Integer numeroParcelas = 1;
    @Column
    private Integer parcelaAtual = 1;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_transacao", columnDefinition = "JSONB")
    private String dadosTransacao;
    @Column(length = 1000)
    private String observacoes;
    @Column
    private Long faturaId;
    @Enumerated(EnumType.STRING)
    @Column
    private ModoTransacao modo;
    @Column(length = 3)
    private String moeda = "BRL";


    public enum TipoTransacao {
        COMPRA_CREDITO, COMPRA_DEBITO, SAQUE, PARCELAMENTO, ESTORNO, CANCELAMENTO, TARIFA, ANUIDADE, JUROS;
    }


    public enum StatusTransacao {
        PENDENTE, AUTORIZADA, NEGADA, CANCELADA, ESTORNADA, CONFIRMADA;
    }

    public enum ModoTransacao {
        CREDITO, DEBITO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoTransacao() {
        return this.codigoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getCartaoId() {
        return this.cartaoId;
    }

    @java.lang.SuppressWarnings("all")
    public TipoTransacao getTipoTransacao() {
        return this.tipoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusTransacao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataTransacao() {
        return this.dataTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getEstabelecimento() {
        return this.estabelecimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getNsu() {
        return this.nsu;
    }

    @java.lang.SuppressWarnings("all")
    public String getAutorizacao() {
        return this.autorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getNumeroParcelas() {
        return this.numeroParcelas;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getParcelaAtual() {
        return this.parcelaAtual;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosTransacao() {
        return this.dadosTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoTransacao(final String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setCartaoId(final Long cartaoId) {
        this.cartaoId = cartaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoTransacao(final TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusTransacao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataTransacao(final LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setEstabelecimento(final String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setNsu(final String nsu) {
        this.nsu = nsu;
    }

    @java.lang.SuppressWarnings("all")
    public void setAutorizacao(final String autorizacao) {
        this.autorizacao = autorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setNumeroParcelas(final Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }

    @java.lang.SuppressWarnings("all")
    public void setParcelaAtual(final Integer parcelaAtual) {
        this.parcelaAtual = parcelaAtual;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosTransacao(final String dadosTransacao) {
        this.dadosTransacao = dadosTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public Long getFaturaId() {
        return this.faturaId;
    }

    @java.lang.SuppressWarnings("all")
    public ModoTransacao getModo() {
        return this.modo;
    }

    @java.lang.SuppressWarnings("all")
    public String getMoeda() {
        return this.moeda;
    }

    @java.lang.SuppressWarnings("all")
    public void setFaturaId(final Long faturaId) {
        this.faturaId = faturaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setModo(final ModoTransacao modo) {
        this.modo = modo;
    }

    @java.lang.SuppressWarnings("all")
    public void setMoeda(final String moeda) {
        this.moeda = moeda;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "TransacaoCartao(id=" + this.getId() + ", codigoTransacao=" + this.getCodigoTransacao() + ", cartaoId=" + this.getCartaoId() + ", tipoTransacao=" + this.getTipoTransacao() + ", status=" + this.getStatus() + ", valor=" + this.getValor() + ", dataTransacao=" + this.getDataTransacao() + ", estabelecimento=" + this.getEstabelecimento() + ", nsu=" + this.getNsu() + ", autorizacao=" + this.getAutorizacao() + ", numeroParcelas=" + this.getNumeroParcelas() + ", parcelaAtual=" + this.getParcelaAtual() + ", dadosTransacao=" + this.getDadosTransacao() + ", observacoes=" + this.getObservacoes() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public TransacaoCartao() {
    }

    @java.lang.SuppressWarnings("all")
    public TransacaoCartao(final Long id, final String codigoTransacao, final Long cartaoId, final TipoTransacao tipoTransacao, final StatusTransacao status, final BigDecimal valor, final LocalDateTime dataTransacao, final String estabelecimento, final String nsu, final String autorizacao, final Integer numeroParcelas, final Integer parcelaAtual, final String dadosTransacao, final String observacoes) {
        this.setId(id);
        this.codigoTransacao = codigoTransacao;
        this.cartaoId = cartaoId;
        this.tipoTransacao = tipoTransacao;
        this.status = status;
        this.valor = valor;
        this.dataTransacao = dataTransacao;
        this.estabelecimento = estabelecimento;
        this.nsu = nsu;
        this.autorizacao = autorizacao;
        this.numeroParcelas = numeroParcelas;
        this.parcelaAtual = parcelaAtual;
        this.dadosTransacao = dadosTransacao;
        this.observacoes = observacoes;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof TransacaoCartao)) return false;
        final TransacaoCartao other = (TransacaoCartao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$cartaoId = this.getCartaoId();
        final java.lang.Object other$cartaoId = other.getCartaoId();
        if (this$cartaoId == null ? other$cartaoId != null : !this$cartaoId.equals(other$cartaoId)) return false;
        final java.lang.Object this$numeroParcelas = this.getNumeroParcelas();
        final java.lang.Object other$numeroParcelas = other.getNumeroParcelas();
        if (this$numeroParcelas == null ? other$numeroParcelas != null : !this$numeroParcelas.equals(other$numeroParcelas)) return false;
        final java.lang.Object this$parcelaAtual = this.getParcelaAtual();
        final java.lang.Object other$parcelaAtual = other.getParcelaAtual();
        if (this$parcelaAtual == null ? other$parcelaAtual != null : !this$parcelaAtual.equals(other$parcelaAtual)) return false;
        final java.lang.Object this$codigoTransacao = this.getCodigoTransacao();
        final java.lang.Object other$codigoTransacao = other.getCodigoTransacao();
        if (this$codigoTransacao == null ? other$codigoTransacao != null : !this$codigoTransacao.equals(other$codigoTransacao)) return false;
        final java.lang.Object this$tipoTransacao = this.getTipoTransacao();
        final java.lang.Object other$tipoTransacao = other.getTipoTransacao();
        if (this$tipoTransacao == null ? other$tipoTransacao != null : !this$tipoTransacao.equals(other$tipoTransacao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$dataTransacao = this.getDataTransacao();
        final java.lang.Object other$dataTransacao = other.getDataTransacao();
        if (this$dataTransacao == null ? other$dataTransacao != null : !this$dataTransacao.equals(other$dataTransacao)) return false;
        final java.lang.Object this$estabelecimento = this.getEstabelecimento();
        final java.lang.Object other$estabelecimento = other.getEstabelecimento();
        if (this$estabelecimento == null ? other$estabelecimento != null : !this$estabelecimento.equals(other$estabelecimento)) return false;
        final java.lang.Object this$nsu = this.getNsu();
        final java.lang.Object other$nsu = other.getNsu();
        if (this$nsu == null ? other$nsu != null : !this$nsu.equals(other$nsu)) return false;
        final java.lang.Object this$autorizacao = this.getAutorizacao();
        final java.lang.Object other$autorizacao = other.getAutorizacao();
        if (this$autorizacao == null ? other$autorizacao != null : !this$autorizacao.equals(other$autorizacao)) return false;
        final java.lang.Object this$dadosTransacao = this.getDadosTransacao();
        final java.lang.Object other$dadosTransacao = other.getDadosTransacao();
        if (this$dadosTransacao == null ? other$dadosTransacao != null : !this$dadosTransacao.equals(other$dadosTransacao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof TransacaoCartao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $cartaoId = this.getCartaoId();
        result = result * PRIME + ($cartaoId == null ? 43 : $cartaoId.hashCode());
        final java.lang.Object $numeroParcelas = this.getNumeroParcelas();
        result = result * PRIME + ($numeroParcelas == null ? 43 : $numeroParcelas.hashCode());
        final java.lang.Object $parcelaAtual = this.getParcelaAtual();
        result = result * PRIME + ($parcelaAtual == null ? 43 : $parcelaAtual.hashCode());
        final java.lang.Object $codigoTransacao = this.getCodigoTransacao();
        result = result * PRIME + ($codigoTransacao == null ? 43 : $codigoTransacao.hashCode());
        final java.lang.Object $tipoTransacao = this.getTipoTransacao();
        result = result * PRIME + ($tipoTransacao == null ? 43 : $tipoTransacao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $dataTransacao = this.getDataTransacao();
        result = result * PRIME + ($dataTransacao == null ? 43 : $dataTransacao.hashCode());
        final java.lang.Object $estabelecimento = this.getEstabelecimento();
        result = result * PRIME + ($estabelecimento == null ? 43 : $estabelecimento.hashCode());
        final java.lang.Object $nsu = this.getNsu();
        result = result * PRIME + ($nsu == null ? 43 : $nsu.hashCode());
        final java.lang.Object $autorizacao = this.getAutorizacao();
        result = result * PRIME + ($autorizacao == null ? 43 : $autorizacao.hashCode());
        final java.lang.Object $dadosTransacao = this.getDadosTransacao();
        result = result * PRIME + ($dadosTransacao == null ? 43 : $dadosTransacao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        return result;
    }
}
