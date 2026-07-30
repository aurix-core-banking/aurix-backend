package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ConsignadoContratoAssinadoEvent extends BaseEvent {
    private Long contratoId;
    private Long clienteId;
    private BigDecimal valorTotal;
    private Integer prazoMeses;
    private BigDecimal valorParcela;
    private Long tenantId;

    public static ConsignadoContratoAssinadoEvent assinado(Long contratoId, Long clienteId, BigDecimal valorTotal, Integer prazoMeses, BigDecimal valorParcela, Long tenantId) {
        ConsignadoContratoAssinadoEvent event = new ConsignadoContratoAssinadoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CONSIGNADO_CONTRATO_ASSINADO");
        event.setSource("aurix-consignado");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.contratoId = contratoId;
        event.clienteId = clienteId;
        event.valorTotal = valorTotal;
        event.prazoMeses = prazoMeses;
        event.valorParcela = valorParcela;
        event.tenantId = tenantId;
        return event;
    }

    public ConsignadoContratoAssinadoEvent() {}

    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }
    public BigDecimal getValorParcela() { return valorParcela; }
    public void setValorParcela(BigDecimal valorParcela) { this.valorParcela = valorParcela; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
}
