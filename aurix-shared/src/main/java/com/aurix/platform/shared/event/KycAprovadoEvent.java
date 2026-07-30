package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class KycAprovadoEvent extends BaseEvent {
    private Long clienteId;
    private Long solicitacaoId;
    private Integer scoreRisco;

    public static KycAprovadoEvent aprovado(Long clienteId, Long solicitacaoId, Integer scoreRisco) {
        KycAprovadoEvent event = new KycAprovadoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("KYC_APROVADO");
        event.setSource("aurix-kyc");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.clienteId = clienteId;
        event.solicitacaoId = solicitacaoId;
        event.scoreRisco = scoreRisco;
        return event;
    }

    public KycAprovadoEvent() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getSolicitacaoId() { return solicitacaoId; }
    public void setSolicitacaoId(Long solicitacaoId) { this.solicitacaoId = solicitacaoId; }
    public Integer getScoreRisco() { return scoreRisco; }
    public void setScoreRisco(Integer scoreRisco) { this.scoreRisco = scoreRisco; }
}
