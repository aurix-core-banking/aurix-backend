package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoTransacaoEstornadaEvent extends BaseEvent {
    private String codigoTransacao;
    private Long cartaoId;
    private BigDecimal valor;
    private Long tenantId;

    public static CartaoTransacaoEstornadaEvent estornada(String codigoTransacao, Long cartaoId, BigDecimal valor, Long tenantId) {
        CartaoTransacaoEstornadaEvent event = new CartaoTransacaoEstornadaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CARTAO_TRANSACAO_ESTORNADA");
        event.setSource("aurix-cartoes");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.codigoTransacao = codigoTransacao;
        event.cartaoId = cartaoId;
        event.valor = valor;
        event.tenantId = tenantId;
        return event;
    }

    public CartaoTransacaoEstornadaEvent() {}

    public String getCodigoTransacao() { return codigoTransacao; }
    public void setCodigoTransacao(String codigoTransacao) { this.codigoTransacao = codigoTransacao; }
    public Long getCartaoId() { return cartaoId; }
    public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
}
