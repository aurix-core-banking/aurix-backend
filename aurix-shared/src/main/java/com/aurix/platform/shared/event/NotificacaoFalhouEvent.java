package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class NotificacaoFalhouEvent extends BaseEvent {
    private Long notificacaoId;
    private Long clienteId;
    private String canal;
    private String motivo;

    public static NotificacaoFalhouEvent falhou(Long notificacaoId, Long clienteId, String canal, String motivo) {
        NotificacaoFalhouEvent event = new NotificacaoFalhouEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("NOTIFICACAO_FALHOU");
        event.setSource("aurix-notification");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.notificacaoId = notificacaoId;
        event.clienteId = clienteId;
        event.canal = canal;
        event.motivo = motivo;
        return event;
    }

    public NotificacaoFalhouEvent() {}

    public Long getNotificacaoId() { return notificacaoId; }
    public void setNotificacaoId(Long notificacaoId) { this.notificacaoId = notificacaoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
