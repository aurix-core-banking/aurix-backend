package com.aurix.platform.shared.event;

import java.math.BigDecimal;

/**
 * Eventos relacionados a liquidação de transações (ADR-0001).
 */
public class LiquidezEvent extends BaseEvent {
    private String liquidezId;
    private String contaOrigem;
    private String contaDestino;
    private BigDecimal valor;
    private String tipoOperacao;
    private String status;
    private String motivo;

    public static LiquidezEvent liquidezProcessada(final String liquidezId, final String contaOrigem, final String contaDestino, final BigDecimal valor, final String tipoOperacao) {
        LiquidezEvent event = new LiquidezEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("LIQUIDEZ_PROCESSADA");
        event.setSource("aurix-settlement");
        event.setTimestamp(java.time.LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.liquidezId = liquidezId;
        event.contaOrigem = contaOrigem;
        event.contaDestino = contaDestino;
        event.valor = valor;
        event.tipoOperacao = tipoOperacao;
        event.status = "PROCESSADA";
        return event;
    }

    public static LiquidezEvent liquidezRejeitada(final String liquidezId, final String contaOrigem, final String contaDestino, final BigDecimal valor, final String tipoOperacao, final String motivo) {
        LiquidezEvent event = new LiquidezEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("LIQUIDEZ_REJEITADA");
        event.setSource("aurix-settlement");
        event.setTimestamp(java.time.LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.liquidezId = liquidezId;
        event.contaOrigem = contaOrigem;
        event.contaDestino = contaDestino;
        event.valor = valor;
        event.tipoOperacao = tipoOperacao;
        event.status = "REJEITADA";
        event.motivo = motivo;
        return event;
    }

    public String getLiquidezId() {
        return this.liquidezId;
    }

    public String getContaOrigem() {
        return this.contaOrigem;
    }

    public String getContaDestino() {
        return this.contaDestino;
    }

    public BigDecimal getValor() {
        return this.valor;
    }

    public String getTipoOperacao() {
        return this.tipoOperacao;
    }

    public String getStatus() {
        return this.status;
    }

    public String getMotivo() {
        return this.motivo;
    }
}
