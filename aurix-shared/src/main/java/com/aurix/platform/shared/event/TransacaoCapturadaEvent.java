package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoCapturadaEvent extends BaseEvent {
    private Long transacaoId;
    private BigDecimal valor;
    private Integer parcelas;
    private String bandeira;

    public static TransacaoCapturadaEvent capturada(Long transacaoId, BigDecimal valor, Integer parcelas, String bandeira) {
        TransacaoCapturadaEvent e = new TransacaoCapturadaEvent();
        e.setEventId(java.util.UUID.randomUUID().toString());
        e.setEventType("TRANSACAO_CAPTURADA");
        e.setSource("aurix-acquirer");
        e.setTimestamp(LocalDateTime.now());
        e.setCorrelationId(java.util.UUID.randomUUID().toString());
        e.transacaoId = transacaoId;
        e.valor = valor;
        e.parcelas = parcelas;
        e.bandeira = bandeira;
        return e;
    }

    public TransacaoCapturadaEvent() {}

    public Long getTransacaoId() { return transacaoId; }
    public void setTransacaoId(Long v) { this.transacaoId = v; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal v) { this.valor = v; }
    public Integer getParcelas() { return parcelas; }
    public void setParcelas(Integer v) { this.parcelas = v; }
    public String getBandeira() { return bandeira; }
    public void setBandeira(String v) { this.bandeira = v; }
}
