package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoAutorizadaEvent extends BaseEvent {
    private Long transacaoId;
    private Long terminalId;
    private BigDecimal valor;
    private String bandeira;
    private String codigoAutorizacao;
    private String nsu;

    public static TransacaoAutorizadaEvent autorizada(Long transacaoId, Long terminalId, BigDecimal valor, String bandeira, String codigoAutorizacao, String nsu) {
        TransacaoAutorizadaEvent e = new TransacaoAutorizadaEvent();
        e.setEventId(java.util.UUID.randomUUID().toString());
        e.setEventType("TRANSACAO_AUTORIZADA");
        e.setSource("aurix-acquirer");
        e.setTimestamp(LocalDateTime.now());
        e.setCorrelationId(java.util.UUID.randomUUID().toString());
        e.transacaoId = transacaoId;
        e.terminalId = terminalId;
        e.valor = valor;
        e.bandeira = bandeira;
        e.codigoAutorizacao = codigoAutorizacao;
        e.nsu = nsu;
        return e;
    }

    public TransacaoAutorizadaEvent() {}

    public Long getTransacaoId() { return transacaoId; }
    public void setTransacaoId(Long v) { this.transacaoId = v; }
    public Long getTerminalId() { return terminalId; }
    public void setTerminalId(Long v) { this.terminalId = v; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal v) { this.valor = v; }
    public String getBandeira() { return bandeira; }
    public void setBandeira(String v) { this.bandeira = v; }
    public String getCodigoAutorizacao() { return codigoAutorizacao; }
    public void setCodigoAutorizacao(String v) { this.codigoAutorizacao = v; }
    public String getNsu() { return nsu; }
    public void setNsu(String v) { this.nsu = v; }
}
