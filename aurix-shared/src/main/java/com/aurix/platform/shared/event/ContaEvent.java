package com.aurix.platform.shared.event;

import java.math.BigDecimal;

/**
 * Eventos relacionados a contas bancárias.
 */
public class ContaEvent extends BaseEvent {
    /**
     * ID da conta.
     */
    private String contaId;
    /**
     * ID do cliente.
     */
    private String clienteId;
    /**
     * Saldo atual.
     */
    private BigDecimal saldo;
    /**
     * Tipo da conta.
     */
    private String tipoConta;
    /**
     * Status da conta.
     */
    private String status;

    /**
     * Evento de conta criada.
     *
     * @param contaId      ID da conta
     * @param clienteId    ID do cliente
     * @param saldoInicial Saldo inicial
     * @param tipoConta    Tipo da conta
     * @return ContaEvent configurado
     */
    public static ContaEvent contaCriada(final String contaId, final String clienteId, final BigDecimal saldoInicial, final String tipoConta) {
        ContaEvent event = new ContaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CONTA_CRIADA");
        event.setSource("aurix-core");
        event.setTimestamp(java.time.LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.contaId = contaId;
        event.clienteId = clienteId;
        event.saldo = saldoInicial;
        event.tipoConta = tipoConta;
        event.status = "ATIVA";
        return event;
    }

    /**
     * Evento de conta atualizada.
     *
     * @param contaId   ID da conta
     * @param clienteId ID do cliente
     * @param novoSaldo Novo saldo
     * @param status    Novo status
     * @return ContaEvent configurado
     */
    public static ContaEvent contaAtualizada(final String contaId, final String clienteId, final BigDecimal novoSaldo, final String status) {
        ContaEvent event = new ContaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CONTA_ATUALIZADA");
        event.setSource("aurix-core");
        event.setTimestamp(java.time.LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.contaId = contaId;
        event.clienteId = clienteId;
        event.saldo = novoSaldo;
        event.status = status;
        return event;
    }

    /**
     * Evento de conta bloqueada.
     *
     * @param contaId   ID da conta
     * @param clienteId ID do cliente
     * @param motivo    Motivo do bloqueio
     * @return ContaEvent configurado
     */
    public static ContaEvent contaBloqueada(final String contaId, final String clienteId, final String motivo) {
        ContaEvent event = new ContaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CONTA_BLOQUEADA");
        event.setSource("aurix-core");
        event.setTimestamp(java.time.LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.contaId = contaId;
        event.clienteId = clienteId;
        event.status = "BLOQUEADA";
        return event;
    }

    @java.lang.SuppressWarnings("all")
    public ContaEvent() {
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
     * Saldo atual.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldo() {
        return this.saldo;
    }

    /**
     * Tipo da conta.
     */
    @java.lang.SuppressWarnings("all")
    public String getTipoConta() {
        return this.tipoConta;
    }

    /**
     * Status da conta.
     */
    @java.lang.SuppressWarnings("all")
    public String getStatus() {
        return this.status;
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
     * Saldo atual.
     */
    @java.lang.SuppressWarnings("all")
    public void setSaldo(final BigDecimal saldo) {
        this.saldo = saldo;
    }

    /**
     * Tipo da conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoConta(final String tipoConta) {
        this.tipoConta = tipoConta;
    }

    /**
     * Status da conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) {
        this.status = status;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ContaEvent(contaId=" + this.getContaId() + ", clienteId=" + this.getClienteId() + ", saldo=" + this.getSaldo() + ", tipoConta=" + this.getTipoConta() + ", status=" + this.getStatus() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ContaEvent)) return false;
        final ContaEvent other = (ContaEvent) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$contaId = this.getContaId();
        final java.lang.Object other$contaId = other.getContaId();
        if (this$contaId == null ? other$contaId != null : !this$contaId.equals(other$contaId)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$saldo = this.getSaldo();
        final java.lang.Object other$saldo = other.getSaldo();
        if (this$saldo == null ? other$saldo != null : !this$saldo.equals(other$saldo)) return false;
        final java.lang.Object this$tipoConta = this.getTipoConta();
        final java.lang.Object other$tipoConta = other.getTipoConta();
        if (this$tipoConta == null ? other$tipoConta != null : !this$tipoConta.equals(other$tipoConta)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ContaEvent;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $contaId = this.getContaId();
        result = result * PRIME + ($contaId == null ? 43 : $contaId.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $saldo = this.getSaldo();
        result = result * PRIME + ($saldo == null ? 43 : $saldo.hashCode());
        final java.lang.Object $tipoConta = this.getTipoConta();
        result = result * PRIME + ($tipoConta == null ? 43 : $tipoConta.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        return result;
    }
}
