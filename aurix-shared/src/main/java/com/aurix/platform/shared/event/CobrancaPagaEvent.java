package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CobrancaPagaEvent extends BaseEvent {
    private Long cobrancaId;
    private BigDecimal valorPago;
    private LocalDate dataPagamento;

    public static CobrancaPagaEvent paga(Long cobrancaId, BigDecimal valorPago, LocalDate dataPagamento) {
        CobrancaPagaEvent e = new CobrancaPagaEvent();
        e.setEventId(java.util.UUID.randomUUID().toString());
        e.setEventType("COBRANCA_PAGA");
        e.setSource("aurix-collections");
        e.setTimestamp(LocalDateTime.now());
        e.setCorrelationId(java.util.UUID.randomUUID().toString());
        e.cobrancaId = cobrancaId;
        e.valorPago = valorPago;
        e.dataPagamento = dataPagamento;
        return e;
    }

    public CobrancaPagaEvent() {}

    public Long getCobrancaId() { return cobrancaId; }
    public void setCobrancaId(Long v) { this.cobrancaId = v; }
    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal v) { this.valorPago = v; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate v) { this.dataPagamento = v; }
}
