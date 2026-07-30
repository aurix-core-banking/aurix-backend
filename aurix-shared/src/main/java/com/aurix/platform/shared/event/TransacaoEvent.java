package com.aurix.platform.shared.event;

import java.math.BigDecimal;

/**
 * Eventos relacionados a transações bancárias.
 */
public class TransacaoEvent extends BaseEvent {
    /**
     * ID da transação.
     */
    private String transacaoId;
    /**
     * ID da conta.
     */
    private String contaId;
    /**
     * ID do cliente.
     */
    private String clienteId;
    /**
     * Valor da transação.
     */
    private BigDecimal valor;
    /**
     * Tipo da transação.
     */
    private String tipoTransacao;
    /**
     * Status atual.
     */
    private String status;
    /**
     * Descrição amigável.
     */
    private String descricao;

    /**
     * Evento de transação realizada.
     *
     * @param transacaoId   ID da transação
     * @param contaId       ID da conta
     * @param clienteId     ID do cliente
     * @param valor         Valor transacionado
     * @param tipoTransacao Tipo da transação
     * @param descricao     Descrição
     * @return TransacaoEvent configurado
     */
    public static TransacaoEvent transacaoRealizada(final String transacaoId, final String contaId, final String clienteId, final BigDecimal valor, final String tipoTransacao, final String descricao) {
        TransacaoEvent event = new TransacaoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("TRANSACAO_REALIZADA");
        event.setSource("aurix-core");
        event.setTimestamp(java.time.LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.transacaoId = transacaoId;
        event.contaId = contaId;
        event.clienteId = clienteId;
        event.valor = valor;
        event.tipoTransacao = tipoTransacao;
        event.status = "PROCESSADA";
        event.descricao = descricao;
        return event;
    }

    /**
     * Evento de transação liquidada.
     *
     * @param transacaoId   ID da transação
     * @param contaId       ID da conta
     * @param valor         Valor liquidado
     * @param tipoTransacao Tipo da transação
     * @return TransacaoEvent configurado
     */
    public static TransacaoEvent transacaoLiquidada(final String transacaoId, final String contaId, final BigDecimal valor, final String tipoTransacao) {
        TransacaoEvent event = new TransacaoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("TRANSACAO_LIQUIDADA");
        event.setSource("aurix-settlement");
        event.setTimestamp(java.time.LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.transacaoId = transacaoId;
        event.contaId = contaId;
        event.valor = valor;
        event.tipoTransacao = tipoTransacao;
        event.status = "LIQUIDADA";
        return event;
    }

    /**
     * Evento de transação conciliada.
     *
     * @param transacaoId ID da transação
     * @param contaId     ID da conta
     * @param valor       Valor conciliado
     * @return TransacaoEvent configurado
     */
    public static TransacaoEvent transacaoConciliada(final String transacaoId, final String contaId, final BigDecimal valor) {
        TransacaoEvent event = new TransacaoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("TRANSACAO_CONCILIADA");
        event.setSource("aurix-settlement");
        event.setTimestamp(java.time.LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.transacaoId = transacaoId;
        event.contaId = contaId;
        event.valor = valor;
        event.status = "CONCILIADA";
        return event;
    }

    @java.lang.SuppressWarnings("all")
    public TransacaoEvent() {
    }

    /**
     * ID da transação.
     */
    @java.lang.SuppressWarnings("all")
    public String getTransacaoId() {
        return this.transacaoId;
    }

    /**
     * ID da conta.
     */
    @java.lang.SuppressWarnings("all")
    public String getContaId() {
        return this.contaId;
    }

    /**
     * ID do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public String getClienteId() {
        return this.clienteId;
    }

    /**
     * Valor da transação.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    /**
     * Tipo da transação.
     */
    @java.lang.SuppressWarnings("all")
    public String getTipoTransacao() {
        return this.tipoTransacao;
    }

    /**
     * Status atual.
     */
    @java.lang.SuppressWarnings("all")
    public String getStatus() {
        return this.status;
    }

    /**
     * Descrição amigável.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * ID da transação.
     */
    @java.lang.SuppressWarnings("all")
    public void setTransacaoId(final String transacaoId) {
        this.transacaoId = transacaoId;
    }

    /**
     * ID da conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaId(final String contaId) {
        this.contaId = contaId;
    }

    /**
     * ID do cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteId(final String clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * Valor da transação.
     */
    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Tipo da transação.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoTransacao(final String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    /**
     * Status atual.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Descrição amigável.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "TransacaoEvent(transacaoId=" + this.getTransacaoId() + ", contaId=" + this.getContaId() + ", clienteId=" + this.getClienteId() + ", valor=" + this.getValor() + ", tipoTransacao=" + this.getTipoTransacao() + ", status=" + this.getStatus() + ", descricao=" + this.getDescricao() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof TransacaoEvent)) return false;
        final TransacaoEvent other = (TransacaoEvent) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$transacaoId = this.getTransacaoId();
        final java.lang.Object other$transacaoId = other.getTransacaoId();
        if (this$transacaoId == null ? other$transacaoId != null : !this$transacaoId.equals(other$transacaoId)) return false;
        final java.lang.Object this$contaId = this.getContaId();
        final java.lang.Object other$contaId = other.getContaId();
        if (this$contaId == null ? other$contaId != null : !this$contaId.equals(other$contaId)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$tipoTransacao = this.getTipoTransacao();
        final java.lang.Object other$tipoTransacao = other.getTipoTransacao();
        if (this$tipoTransacao == null ? other$tipoTransacao != null : !this$tipoTransacao.equals(other$tipoTransacao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof TransacaoEvent;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $transacaoId = this.getTransacaoId();
        result = result * PRIME + ($transacaoId == null ? 43 : $transacaoId.hashCode());
        final java.lang.Object $contaId = this.getContaId();
        result = result * PRIME + ($contaId == null ? 43 : $contaId.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $tipoTransacao = this.getTipoTransacao();
        result = result * PRIME + ($tipoTransacao == null ? 43 : $tipoTransacao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        return result;
    }
}
