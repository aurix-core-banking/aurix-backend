package com.aurix.platform.shared.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GarantiaLiberadaEvent extends BaseEvent {
    private Long garantiaId;
    private Long contratoId;
    private LocalDate dataBaixa;

    public static GarantiaLiberadaEvent liberada(Long garantiaId, Long contratoId, LocalDate dataBaixa) {
        GarantiaLiberadaEvent e = new GarantiaLiberadaEvent();
        e.setEventId(java.util.UUID.randomUUID().toString());
        e.setEventType("GARANTIA_LIBERADA");
        e.setSource("aurix-guarantee");
        e.setTimestamp(LocalDateTime.now());
        e.setCorrelationId(java.util.UUID.randomUUID().toString());
        e.garantiaId = garantiaId;
        e.contratoId = contratoId;
        e.dataBaixa = dataBaixa;
        return e;
    }

    public GarantiaLiberadaEvent() {}

    public Long getGarantiaId() { return garantiaId; }
    public void setGarantiaId(Long v) { this.garantiaId = v; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long v) { this.contratoId = v; }
    public LocalDate getDataBaixa() { return dataBaixa; }
    public void setDataBaixa(LocalDate v) { this.dataBaixa = v; }
}
