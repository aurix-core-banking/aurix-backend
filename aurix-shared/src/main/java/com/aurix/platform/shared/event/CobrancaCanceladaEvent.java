package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class CobrancaCanceladaEvent extends BaseEvent {
    private Long cobrancaId;
    private String motivo;

    public static CobrancaCanceladaEvent cancelada(Long cobrancaId, String motivo) {
        CobrancaCanceladaEvent e = new CobrancaCanceladaEvent();
        e.setEventId(java.util.UUID.randomUUID().toString());
        e.setEventType("COBRANCA_CANCELADA");
        e.setSource("aurix-collections");
        e.setTimestamp(LocalDateTime.now());
        e.setCorrelationId(java.util.UUID.randomUUID().toString());
        e.cobrancaId = cobrancaId;
        e.motivo = motivo;
        return e;
    }

    public CobrancaCanceladaEvent() {}

    public Long getCobrancaId() { return cobrancaId; }
    public void setCobrancaId(Long v) { this.cobrancaId = v; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String v) { this.motivo = v; }
}
