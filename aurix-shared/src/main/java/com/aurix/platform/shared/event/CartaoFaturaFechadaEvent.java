package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoFaturaFechadaEvent extends BaseEvent {
    private Long faturaId;
    private Long cartaoId;
    private Integer mesReferencia;
    private Integer anoReferencia;
    private BigDecimal valor;
    private Long tenantId;

    public static CartaoFaturaFechadaEvent fechada(Long faturaId, Long cartaoId, Integer mesReferencia, Integer anoReferencia, BigDecimal valor, Long tenantId) {
        CartaoFaturaFechadaEvent event = new CartaoFaturaFechadaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CARTAO_FATURA_FECHADA");
        event.setSource("aurix-cartoes");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.faturaId = faturaId;
        event.cartaoId = cartaoId;
        event.mesReferencia = mesReferencia;
        event.anoReferencia = anoReferencia;
        event.valor = valor;
        event.tenantId = tenantId;
        return event;
    }

    public CartaoFaturaFechadaEvent() {}

    public Long getFaturaId() { return faturaId; }
    public void setFaturaId(Long faturaId) { this.faturaId = faturaId; }
    public Long getCartaoId() { return cartaoId; }
    public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }
    public Integer getMesReferencia() { return mesReferencia; }
    public void setMesReferencia(Integer mesReferencia) { this.mesReferencia = mesReferencia; }
    public Integer getAnoReferencia() { return anoReferencia; }
    public void setAnoReferencia(Integer anoReferencia) { this.anoReferencia = anoReferencia; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
}
