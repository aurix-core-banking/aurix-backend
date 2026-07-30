package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoFaturaPagaEvent extends BaseEvent {
    private Long faturaId;
    private Long cartaoId;
    private BigDecimal valorPago;
    private Long tenantId;

    public static CartaoFaturaPagaEvent paga(Long faturaId, Long cartaoId, BigDecimal valorPago, Long tenantId) {
        CartaoFaturaPagaEvent event = new CartaoFaturaPagaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CARTAO_FATURA_PAGA");
        event.setSource("aurix-cartoes");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.faturaId = faturaId;
        event.cartaoId = cartaoId;
        event.valorPago = valorPago;
        event.tenantId = tenantId;
        return event;
    }

    public CartaoFaturaPagaEvent() {}

    public Long getFaturaId() { return faturaId; }
    public void setFaturaId(Long faturaId) { this.faturaId = faturaId; }
    public Long getCartaoId() { return cartaoId; }
    public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }
    public BigDecimal getValorPago() { return valorPago; }
    public void setValorPago(BigDecimal valorPago) { this.valorPago = valorPago; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
}
