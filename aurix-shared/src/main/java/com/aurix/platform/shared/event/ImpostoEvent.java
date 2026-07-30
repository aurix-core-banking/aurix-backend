package com.aurix.platform.shared.event;

import java.math.BigDecimal;

/**
 * Eventos relacionados a impostos e tributos.
 */
public class ImpostoEvent extends BaseEvent {
    /**
     * ID do imposto.
     */
    private String impostoId;
    /**
     * ID do lançamento relacionado.
     */
    private String lancamentoId;
    /**
     * Conta contábil para registro.
     */
    private String contaContabil;
    /**
     * Valor do imposto calculado.
     */
    private BigDecimal valorImposto;
    /**
     * Tipo do imposto (ex: ISS, ICMS).
     */
    private String tipoImposto;
    /**
     * Status do processamento.
     */
    private String status;
    /**
     * Período de competência.
     */
    private String periodo;

    /**
     * Evento de imposto calculado.
     *
     * @param impostoId     ID do imposto
     * @param lancamentoId  ID do lançamento
     * @param contaContabil Conta contábil
     * @param valorImposto  Valor calculado
     * @param tipoImposto   Tipo do imposto
     * @param periodo       Período
     * @return ImpostoEvent configurado
     */
    public static ImpostoEvent impostoCalculado(final String impostoId, final String lancamentoId, final String contaContabil, final BigDecimal valorImposto, final String tipoImposto, final String periodo) {
        ImpostoEvent event = new ImpostoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("IMPOSTO_CALCULADO");
        event.setSource("aurix-tax");
        event.setTimestamp(java.time.LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.impostoId = impostoId;
        event.lancamentoId = lancamentoId;
        event.contaContabil = contaContabil;
        event.valorImposto = valorImposto;
        event.tipoImposto = tipoImposto;
        event.status = "CALCULADO";
        event.periodo = periodo;
        return event;
    }

    /**
     * Evento de imposto registrado.
     *
     * @param impostoId    ID do imposto
     * @param lancamentoId ID do lançamento
     * @param valorImposto Valor do imposto
     * @param tipoImposto  Tipo do imposto
     * @return ImpostoEvent configurado
     */
    public static ImpostoEvent impostoRegistrado(final String impostoId, final String lancamentoId, final BigDecimal valorImposto, final String tipoImposto) {
        ImpostoEvent event = new ImpostoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("IMPOSTO_REGISTRADO");
        event.setSource("aurix-accounting");
        event.setTimestamp(java.time.LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.impostoId = impostoId;
        event.lancamentoId = lancamentoId;
        event.valorImposto = valorImposto;
        event.tipoImposto = tipoImposto;
        event.status = "REGISTRADO";
        return event;
    }

    @java.lang.SuppressWarnings("all")
    public ImpostoEvent() {
    }

    /**
     * ID do imposto.
     */
    @java.lang.SuppressWarnings("all")
    public String getImpostoId() {
        return this.impostoId;
    }

    /**
     * ID do lançamento relacionado.
     */
    @java.lang.SuppressWarnings("all")
    public String getLancamentoId() {
        return this.lancamentoId;
    }

    /**
     * Conta contábil para registro.
     */
    @java.lang.SuppressWarnings("all")
    public String getContaContabil() {
        return this.contaContabil;
    }

    /**
     * Valor do imposto calculado.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorImposto() {
        return this.valorImposto;
    }

    /**
     * Tipo do imposto (ex: ISS, ICMS).
     */
    @java.lang.SuppressWarnings("all")
    public String getTipoImposto() {
        return this.tipoImposto;
    }

    /**
     * Status do processamento.
     */
    @java.lang.SuppressWarnings("all")
    public String getStatus() {
        return this.status;
    }

    /**
     * Período de competência.
     */
    @java.lang.SuppressWarnings("all")
    public String getPeriodo() {
        return this.periodo;
    }

    /**
     * ID do imposto.
     */
    @java.lang.SuppressWarnings("all")
    public void setImpostoId(final String impostoId) {
        this.impostoId = impostoId;
    }

    /**
     * ID do lançamento relacionado.
     */
    @java.lang.SuppressWarnings("all")
    public void setLancamentoId(final String lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

    /**
     * Conta contábil para registro.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaContabil(final String contaContabil) {
        this.contaContabil = contaContabil;
    }

    /**
     * Valor do imposto calculado.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorImposto(final BigDecimal valorImposto) {
        this.valorImposto = valorImposto;
    }

    /**
     * Tipo do imposto (ex: ISS, ICMS).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoImposto(final String tipoImposto) {
        this.tipoImposto = tipoImposto;
    }

    /**
     * Status do processamento.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) {
        this.status = status;
    }

    /**
     * Período de competência.
     */
    @java.lang.SuppressWarnings("all")
    public void setPeriodo(final String periodo) {
        this.periodo = periodo;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ImpostoEvent(impostoId=" + this.getImpostoId() + ", lancamentoId=" + this.getLancamentoId() + ", contaContabil=" + this.getContaContabil() + ", valorImposto=" + this.getValorImposto() + ", tipoImposto=" + this.getTipoImposto() + ", status=" + this.getStatus() + ", periodo=" + this.getPeriodo() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ImpostoEvent)) return false;
        final ImpostoEvent other = (ImpostoEvent) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$impostoId = this.getImpostoId();
        final java.lang.Object other$impostoId = other.getImpostoId();
        if (this$impostoId == null ? other$impostoId != null : !this$impostoId.equals(other$impostoId)) return false;
        final java.lang.Object this$lancamentoId = this.getLancamentoId();
        final java.lang.Object other$lancamentoId = other.getLancamentoId();
        if (this$lancamentoId == null ? other$lancamentoId != null : !this$lancamentoId.equals(other$lancamentoId)) return false;
        final java.lang.Object this$contaContabil = this.getContaContabil();
        final java.lang.Object other$contaContabil = other.getContaContabil();
        if (this$contaContabil == null ? other$contaContabil != null : !this$contaContabil.equals(other$contaContabil)) return false;
        final java.lang.Object this$valorImposto = this.getValorImposto();
        final java.lang.Object other$valorImposto = other.getValorImposto();
        if (this$valorImposto == null ? other$valorImposto != null : !this$valorImposto.equals(other$valorImposto)) return false;
        final java.lang.Object this$tipoImposto = this.getTipoImposto();
        final java.lang.Object other$tipoImposto = other.getTipoImposto();
        if (this$tipoImposto == null ? other$tipoImposto != null : !this$tipoImposto.equals(other$tipoImposto)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$periodo = this.getPeriodo();
        final java.lang.Object other$periodo = other.getPeriodo();
        if (this$periodo == null ? other$periodo != null : !this$periodo.equals(other$periodo)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ImpostoEvent;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $impostoId = this.getImpostoId();
        result = result * PRIME + ($impostoId == null ? 43 : $impostoId.hashCode());
        final java.lang.Object $lancamentoId = this.getLancamentoId();
        result = result * PRIME + ($lancamentoId == null ? 43 : $lancamentoId.hashCode());
        final java.lang.Object $contaContabil = this.getContaContabil();
        result = result * PRIME + ($contaContabil == null ? 43 : $contaContabil.hashCode());
        final java.lang.Object $valorImposto = this.getValorImposto();
        result = result * PRIME + ($valorImposto == null ? 43 : $valorImposto.hashCode());
        final java.lang.Object $tipoImposto = this.getTipoImposto();
        result = result * PRIME + ($tipoImposto == null ? 43 : $tipoImposto.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $periodo = this.getPeriodo();
        result = result * PRIME + ($periodo == null ? 43 : $periodo.hashCode());
        return result;
    }
}
