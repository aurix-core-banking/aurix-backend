package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class OcorrenciaFraudEvent extends BaseEvent {
    private Long clienteId;
    private Long ocorrenciaId;
    private String tipo;
    private String status;

    public static OcorrenciaFraudEvent criada(Long clienteId, Long ocorrenciaId, String tipo, String status) {
        OcorrenciaFraudEvent event = new OcorrenciaFraudEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("OCORRENCIA_FRAUD");
        event.setSource("aurix-fraud");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.clienteId = clienteId;
        event.ocorrenciaId = ocorrenciaId;
        event.tipo = tipo;
        event.status = status;
        return event;
    }

    public OcorrenciaFraudEvent() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getOcorrenciaId() { return ocorrenciaId; }
    public void setOcorrenciaId(Long ocorrenciaId) { this.ocorrenciaId = ocorrenciaId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
