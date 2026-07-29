package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class NotificacaoEnviadaEvent extends BaseEvent {
    private Long notificacaoId;
    private Long clienteId;
    private String canal;
    private String templateCodigo;
    private String status;

    public static NotificacaoEnviadaEvent enviada(Long notificacaoId, Long clienteId, String canal, String templateCodigo, String status) {
        NotificacaoEnviadaEvent event = new NotificacaoEnviadaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("NOTIFICACAO_ENVIADA");
        event.setSource("aurix-notification");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.notificacaoId = notificacaoId;
        event.clienteId = clienteId;
        event.canal = canal;
        event.templateCodigo = templateCodigo;
        event.status = status;
        return event;
    }

    public NotificacaoEnviadaEvent() {}

    public Long getNotificacaoId() { return notificacaoId; }
    public void setNotificacaoId(Long notificacaoId) { this.notificacaoId = notificacaoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getCanal() { return canal; }
    public void setCanal(String canal) { this.canal = canal; }
    public String getTemplateCodigo() { return templateCodigo; }
    public void setTemplateCodigo(String templateCodigo) { this.templateCodigo = templateCodigo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
