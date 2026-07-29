package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class ClienteAtualizadoEvent extends BaseEvent {
    private Long clienteId;
    private String documento;
    private String status;

    public static ClienteAtualizadoEvent atualizado(Long clienteId, String documento, String status) {
        ClienteAtualizadoEvent event = new ClienteAtualizadoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CLIENTE_ATUALIZADO");
        event.setSource("aurix-customer");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.clienteId = clienteId;
        event.documento = documento;
        event.status = status;
        return event;
    }

    public ClienteAtualizadoEvent() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
