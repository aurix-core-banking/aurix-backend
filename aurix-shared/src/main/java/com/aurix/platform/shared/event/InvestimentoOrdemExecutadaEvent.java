package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InvestimentoOrdemExecutadaEvent extends BaseEvent {
    private Long id;
    private Long contaId;
    private Long produtoId;
    private BigDecimal valor;
    private Integer quantidade;
    private Long tenantId;

    public static InvestimentoOrdemExecutadaEvent executada(Long id, Long contaId, Long produtoId, BigDecimal valor, Integer quantidade, Long tenantId) {
        InvestimentoOrdemExecutadaEvent event = new InvestimentoOrdemExecutadaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("INVESTIMENTO_ORDEM_EXECUTADA");
        event.setSource("aurix-investimento");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.id = id;
        event.contaId = contaId;
        event.produtoId = produtoId;
        event.valor = valor;
        event.quantidade = quantidade;
        event.tenantId = tenantId;
        return event;
    }

    public InvestimentoOrdemExecutadaEvent() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
}
