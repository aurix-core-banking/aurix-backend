package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoEstornadaEvent extends BaseEvent {
    private Long transacaoId;
    private BigDecimal valor;
    private String motivo;

    public static TransacaoEstornadaEvent estornada(Long transacaoId, BigDecimal valor, String motivo) {
        TransacaoEstornadaEvent e = new TransacaoEstornadaEvent();
        e.setEventId(java.util.UUID.randomUUID().toString());
        e.setEventType("TRANSACAO_ESTORNADA");
        e.setSource("aurix-acquirer");
        e.setTimestamp(LocalDateTime.now());
        e.setCorrelationId(java.util.UUID.randomUUID().toString());
        e.transacaoId = transacaoId;
        e.valor = valor;
        e.motivo = motivo;
        return e;
    }

    public TransacaoEstornadaEvent() {}

    public Long getTransacaoId() { return transacaoId; }
    public void setTransacaoId(Long v) { this.transacaoId = v; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal v) { this.valor = v; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String v) { this.motivo = v; }
}
