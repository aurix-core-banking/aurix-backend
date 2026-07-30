package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class KycRejeitadoEvent extends BaseEvent {
    private Long clienteId;
    private Long solicitacaoId;
    private String motivo;

    public static KycRejeitadoEvent rejeitado(Long clienteId, Long solicitacaoId, String motivo) {
        KycRejeitadoEvent event = new KycRejeitadoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("KYC_REJEITADO");
        event.setSource("aurix-kyc");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.clienteId = clienteId;
        event.solicitacaoId = solicitacaoId;
        event.motivo = motivo;
        return event;
    }

    public KycRejeitadoEvent() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getSolicitacaoId() { return solicitacaoId; }
    public void setSolicitacaoId(Long solicitacaoId) { this.solicitacaoId = solicitacaoId; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
