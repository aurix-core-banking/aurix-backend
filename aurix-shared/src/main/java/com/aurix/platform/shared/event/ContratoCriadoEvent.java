package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContratoCriadoEvent extends BaseEvent {
    private Long contratoId;
    private String numeroContrato;
    private Long clienteId;
    private String tipoContrato;
    private BigDecimal valor;
    private Integer prazoMeses;

    public static ContratoCriadoEvent criado(Long contratoId, String numeroContrato, Long clienteId, String tipoContrato, BigDecimal valor, Integer prazoMeses) {
        ContratoCriadoEvent event = new ContratoCriadoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CONTRATO_CRIADO");
        event.setSource("aurix-contracts");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.contratoId = contratoId;
        event.numeroContrato = numeroContrato;
        event.clienteId = clienteId;
        event.tipoContrato = tipoContrato;
        event.valor = valor;
        event.prazoMeses = prazoMeses;
        return event;
    }

    public ContratoCriadoEvent() {
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

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Integer getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }
}
