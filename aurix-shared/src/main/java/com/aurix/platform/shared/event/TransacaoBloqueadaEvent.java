package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoBloqueadaEvent extends BaseEvent {
    private Long clienteId;
    private String transacaoRef;
    private Integer score;
    private String risco;
    private Long bloqueioId;

    public static TransacaoBloqueadaEvent bloqueada(Long clienteId, String transacaoRef, Integer score, String risco, Long bloqueioId) {
        TransacaoBloqueadaEvent event = new TransacaoBloqueadaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("TRANSACAO_BLOQUEADA");
        event.setSource("aurix-fraud");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.clienteId = clienteId;
        event.transacaoRef = transacaoRef;
        event.score = score;
        event.risco = risco;
        event.bloqueioId = bloqueioId;
        return event;
    }

    public TransacaoBloqueadaEvent() {}

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getTransacaoRef() { return transacaoRef; }
    public void setTransacaoRef(String transacaoRef) { this.transacaoRef = transacaoRef; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public String getRisco() { return risco; }
    public void setRisco(String risco) { this.risco = risco; }
    public Long getBloqueioId() { return bloqueioId; }
    public void setBloqueioId(Long bloqueioId) { this.bloqueioId = bloqueioId; }
}
