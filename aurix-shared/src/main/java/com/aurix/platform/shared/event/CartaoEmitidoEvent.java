package com.aurix.platform.shared.event;

import java.time.LocalDateTime;

public class CartaoEmitidoEvent extends BaseEvent {
    private Long cartaoId;
    private Long contaId;
    private String nomePortador;
    private String bandeira;
    private String tipo;
    private Long tenantId;

    public static CartaoEmitidoEvent emitido(Long cartaoId, Long contaId, String nomePortador, String bandeira, String tipo, Long tenantId) {
        CartaoEmitidoEvent event = new CartaoEmitidoEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("CARTAO_EMITIDO");
        event.setSource("aurix-cartoes");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.cartaoId = cartaoId;
        event.contaId = contaId;
        event.nomePortador = nomePortador;
        event.bandeira = bandeira;
        event.tipo = tipo;
        event.tenantId = tenantId;
        return event;
    }

    public CartaoEmitidoEvent() {}

    public Long getCartaoId() { return cartaoId; }
    public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }
    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public String getNomePortador() { return nomePortador; }
    public void setNomePortador(String nomePortador) { this.nomePortador = nomePortador; }
    public String getBandeira() { return bandeira; }
    public void setBandeira(String bandeira) { this.bandeira = bandeira; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Long getTenantId() { return tenantId; }
    public void setTenantId(Long tenantId) { this.tenantId = tenantId; }
}
