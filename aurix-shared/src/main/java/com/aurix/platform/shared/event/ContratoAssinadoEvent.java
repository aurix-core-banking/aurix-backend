package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class ContratoAssinadoEvent extends BaseEvent {
    private Long contratoId;
    private String numeroContrato;
    private String assinanteDocumento;
    private LocalDateTime dataAssinatura;

    public static ContratoAssinadoEvent assinado(Long contratoId, String numeroContrato, String assinanteDocumento, LocalDateTime dataAssinatura) {
        ContratoAssinadoEvent event = new ContratoAssinadoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CONTRATO_ASSINADO");
        event.setSource("aurix-contracts");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.contratoId = contratoId;
        event.numeroContrato = numeroContrato;
        event.assinanteDocumento = assinanteDocumento;
        event.dataAssinatura = dataAssinatura;
        return event;
    }

    public ContratoAssinadoEvent() {
    }

    public Long getContratoId() {
        return contratoId;
    }

    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }

    public String getNumeroContrato() {
        return numeroContrato;
    }

    public void setNumeroContrato(String numeroContrato) {
        this.numeroContrato = numeroContrato;
    }

    public String getAssinanteDocumento() {
        return assinanteDocumento;
    }

    public void setAssinanteDocumento(String assinanteDocumento) {
        this.assinanteDocumento = assinanteDocumento;
    }

    public LocalDateTime getDataAssinatura() {
        return dataAssinatura;
    }

    public void setDataAssinatura(LocalDateTime dataAssinatura) {
        this.dataAssinatura = dataAssinatura;
    }
}
