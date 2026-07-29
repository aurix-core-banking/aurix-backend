package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BoletoEmitidoEvent extends BaseEvent {
    private Long cobrancaId;
    private String nossoNumero;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private Long clienteId;

    public static BoletoEmitidoEvent emitido(Long cobrancaId, String nossoNumero, BigDecimal valor, LocalDate dataVencimento, Long clienteId) {
        BoletoEmitidoEvent e = new BoletoEmitidoEvent();
        e.setEventId(java.util.UUID.randomUUID().toString());
        e.setEventType("BOLETO_EMITIDO");
        e.setSource("aurix-collections");
        e.setTimestamp(LocalDateTime.now());
        e.setCorrelationId(java.util.UUID.randomUUID().toString());
        e.cobrancaId = cobrancaId;
        e.nossoNumero = nossoNumero;
        e.valor = valor;
        e.dataVencimento = dataVencimento;
        e.clienteId = clienteId;
        return e;
    }

    public BoletoEmitidoEvent() {}

    public Long getCobrancaId() { return cobrancaId; }
    public void setCobrancaId(Long v) { this.cobrancaId = v; }
    public String getNossoNumero() { return nossoNumero; }
    public void setNossoNumero(String v) { this.nossoNumero = v; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal v) { this.valor = v; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate v) { this.dataVencimento = v; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long v) { this.clienteId = v; }
}
