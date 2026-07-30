package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FinanciamentoContratoAssinadoEvent extends BaseEvent {
    private Long id;
    private Long clienteId;
    private Long contaCorrenteId;
    private String tipo;
    private BigDecimal valorFinanciado;
    private Integer prazoMeses;
    private BigDecimal taxaJuros;
    private Long tenantId;

    public static FinanciamentoContratoAssinadoEvent assinado(Long id, Long clienteId, Long contaCorrenteId, String tipo, BigDecimal valorFinanciado, Integer prazoMeses, BigDecimal taxaJuros, Long tenantId) {
        FinanciamentoContratoAssinadoEvent event = new FinanciamentoContratoAssinadoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("FINANCIAMENTO_CONTRATO_ASSINADO");
        event.setSource("aurix-financiamento");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.id = id;
        event.clienteId = clienteId;
        event.contaCorrenteId = contaCorrenteId;
        event.tipo = tipo;
        event.valorFinanciado = valorFinanciado;
        event.prazoMeses = prazoMeses;
        event.taxaJuros = taxaJuros;
        event.tenantId = tenantId;
        return event;
    }

    public FinanciamentoContratoAssinadoEvent() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getValorFinanciado() { return valorFinanciado; }
    public void setValorFinanciado(BigDecimal valorFinanciado) { this.valorFinanciado = valorFinanciado; }
    public Integer getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(Integer prazoMeses) { this.prazoMeses = prazoMeses; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
}
