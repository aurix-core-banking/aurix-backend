package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SegurosApoliceEmitidaEvent extends BaseEvent {
    private Long apoliceId;
    private String numero;
    private Long clienteId;
    private Long produtoId;
    private BigDecimal premioTotal;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Long tenantId;

    public static SegurosApoliceEmitidaEvent emitida(Long apoliceId, String numero, Long clienteId, Long produtoId, BigDecimal premioTotal, LocalDate dataInicio, LocalDate dataFim, Long tenantId) {
        SegurosApoliceEmitidaEvent event = new SegurosApoliceEmitidaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("SEGUROS_APOLICE_EMITIDA");
        event.setSource("aurix-seguros");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.apoliceId = apoliceId;
        event.numero = numero;
        event.clienteId = clienteId;
        event.produtoId = produtoId;
        event.premioTotal = premioTotal;
        event.dataInicio = dataInicio;
        event.dataFim = dataFim;
        event.tenantId = tenantId;
        return event;
    }

    public SegurosApoliceEmitidaEvent() {}

    public Long getApoliceId() { return apoliceId; }
    public void setApoliceId(Long apoliceId) { this.apoliceId = apoliceId; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public BigDecimal getPremioTotal() { return premioTotal; }
    public void setPremioTotal(BigDecimal premioTotal) { this.premioTotal = premioTotal; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
}
