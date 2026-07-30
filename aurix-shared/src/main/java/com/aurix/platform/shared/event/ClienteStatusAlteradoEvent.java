package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class ClienteStatusAlteradoEvent extends BaseEvent {
    private Long clienteId;
    private String statusAnterior;
    private String statusAtual;

    public static ClienteStatusAlteradoEvent alterado(Long clienteId, String statusAnterior, String statusAtual) {
        ClienteStatusAlteradoEvent event = new ClienteStatusAlteradoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CLIENTE_STATUS_ALTERADO");
        event.setSource("aurix-customer");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.clienteId = clienteId;
        event.statusAnterior = statusAnterior;
        event.statusAtual = statusAtual;
        return event;
    }

    public ClienteStatusAlteradoEvent() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getStatusAnterior() { return statusAnterior; }
    public void setStatusAnterior(String statusAnterior) { this.statusAnterior = statusAnterior; }
    public String getStatusAtual() { return statusAtual; }
    public void setStatusAtual(String statusAtual) { this.statusAtual = statusAtual; }
}
