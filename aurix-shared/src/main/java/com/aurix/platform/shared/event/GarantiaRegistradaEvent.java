package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GarantiaRegistradaEvent extends BaseEvent {
    private Long garantiaId;
    private Long contratoId;
    private Long clienteId;
    private String tipo;
    private BigDecimal valor;

    public static GarantiaRegistradaEvent registrada(Long garantiaId, Long contratoId, Long clienteId, String tipo, BigDecimal valor) {
        GarantiaRegistradaEvent e = new GarantiaRegistradaEvent();
        e.setEventId(java.util.UUID.randomUUID().toString());
        e.setEventType("GARANTIA_REGISTRADA");
        e.setSource("aurix-guarantee");
        e.setTimestamp(LocalDateTime.now());
        e.setCorrelationId(java.util.UUID.randomUUID().toString());
        e.garantiaId = garantiaId;
        e.contratoId = contratoId;
        e.clienteId = clienteId;
        e.tipo = tipo;
        e.valor = valor;
        return e;
    }

    public GarantiaRegistradaEvent() {}

    public Long getGarantiaId() { return garantiaId; }
    public void setGarantiaId(Long v) { this.garantiaId = v; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long v) { this.contratoId = v; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long v) { this.clienteId = v; }
    public String getTipo() { return tipo; }
    public void setTipo(String v) { this.tipo = v; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal v) { this.valor = v; }
}
