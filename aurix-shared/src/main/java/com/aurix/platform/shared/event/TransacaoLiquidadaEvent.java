package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoLiquidadaEvent extends BaseEvent {
    private Long liquidacaoId;
    private Long transacaoId;
    private BigDecimal valorLiquido;
    private BigDecimal valorRepasse;

    public static TransacaoLiquidadaEvent liquidada(Long liquidacaoId, Long transacaoId, BigDecimal valorLiquido, BigDecimal valorRepasse) {
        TransacaoLiquidadaEvent e = new TransacaoLiquidadaEvent();
        e.setEventId(java.util.UUID.randomUUID().toString());
        e.setEventType("TRANSACAO_LIQUIDADA");
        e.setSource("aurix-acquirer");
        e.setTimestamp(LocalDateTime.now());
        e.setCorrelationId(java.util.UUID.randomUUID().toString());
        e.liquidacaoId = liquidacaoId;
        e.transacaoId = transacaoId;
        e.valorLiquido = valorLiquido;
        e.valorRepasse = valorRepasse;
        return e;
    }

    public TransacaoLiquidadaEvent() {}

    public Long getLiquidacaoId() { return liquidacaoId; }
    public void setLiquidacaoId(Long v) { this.liquidacaoId = v; }
    public Long getTransacaoId() { return transacaoId; }
    public void setTransacaoId(Long v) { this.transacaoId = v; }
    public BigDecimal getValorLiquido() { return valorLiquido; }
    public void setValorLiquido(BigDecimal v) { this.valorLiquido = v; }
    public BigDecimal getValorRepasse() { return valorRepasse; }
    public void setValorRepasse(BigDecimal v) { this.valorRepasse = v; }
}
