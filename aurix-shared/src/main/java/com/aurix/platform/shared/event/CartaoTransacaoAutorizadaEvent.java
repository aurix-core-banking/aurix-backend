package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CartaoTransacaoAutorizadaEvent extends BaseEvent {
    private String codigoTransacao;
    private Long cartaoId;
    private BigDecimal valor;
    private String estabelecimento;
    private String autorizacao;
    private String status;
    private Long tenantId;

    public static CartaoTransacaoAutorizadaEvent autorizada(String codigoTransacao, Long cartaoId, BigDecimal valor, String estabelecimento, String autorizacao, String status, Long tenantId) {
        CartaoTransacaoAutorizadaEvent event = new CartaoTransacaoAutorizadaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CARTAO_TRANSACAO_AUTORIZADA");
        event.setSource("aurix-cartoes");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.codigoTransacao = codigoTransacao;
        event.cartaoId = cartaoId;
        event.valor = valor;
        event.estabelecimento = estabelecimento;
        event.autorizacao = autorizacao;
        event.status = status;
        event.tenantId = tenantId;
        return event;
    }

    public CartaoTransacaoAutorizadaEvent() {}

    public String getCodigoTransacao() { return codigoTransacao; }
    public void setCodigoTransacao(String codigoTransacao) { this.codigoTransacao = codigoTransacao; }
    public Long getCartaoId() { return cartaoId; }
    public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getEstabelecimento() { return estabelecimento; }
    public void setEstabelecimento(String estabelecimento) { this.estabelecimento = estabelecimento; }
    public String getAutorizacao() { return autorizacao; }
    public void setAutorizacao(String autorizacao) { this.autorizacao = autorizacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
}
