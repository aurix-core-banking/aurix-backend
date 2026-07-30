package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class ScoreAlteradoEvent extends BaseEvent {
    private Long clienteId;
    private String transacaoRef;
    private Integer score;
    private String risco;

    public static ScoreAlteradoEvent alterado(Long clienteId, String transacaoRef, Integer score, String risco) {
        ScoreAlteradoEvent event = new ScoreAlteradoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("SCORE_ALTERADO");
        event.setSource("aurix-fraud");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.clienteId = clienteId;
        event.transacaoRef = transacaoRef;
        event.score = score;
        event.risco = risco;
        return event;
    }

    public ScoreAlteradoEvent() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getTransacaoRef() { return transacaoRef; }
    public void setTransacaoRef(String transacaoRef) { this.transacaoRef = transacaoRef; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public String getRisco() { return risco; }
    public void setRisco(String risco) { this.risco = risco; }
}
