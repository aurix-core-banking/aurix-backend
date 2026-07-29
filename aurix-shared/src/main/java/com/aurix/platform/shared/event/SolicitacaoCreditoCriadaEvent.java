package com.aurix.platform.shared.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SolicitacaoCreditoCriadaEvent extends BaseEvent {
    private Long solicitacaoId;
    private Long clienteId;
    private BigDecimal valor;
    private String tipoCredito;
    private LocalDateTime dataSolicitacao;

    public static SolicitacaoCreditoCriadaEvent criada(Long solicitacaoId, Long clienteId, BigDecimal valor, String tipoCredito) {
        SolicitacaoCreditoCriadaEvent event = new SolicitacaoCreditoCriadaEvent();
        event.setEventId(java.util.UUID.randomUUID().toString());
        event.setEventType("SOLICITACAO_CREDITO_CRIADA");
        event.setSource("aurix-credit");
        event.setTimestamp(LocalDateTime.now());
        event.setCorrelationId(java.util.UUID.randomUUID().toString());
        event.solicitacaoId = solicitacaoId;
        event.clienteId = clienteId;
        event.valor = valor;
        event.tipoCredito = tipoCredito;
        event.dataSolicitacao = LocalDateTime.now();
        return event;
    }

    public SolicitacaoCreditoCriadaEvent() {}

    public Long getSolicitacaoId() { return solicitacaoId; }
    public void setSolicitacaoId(Long solicitacaoId) { this.solicitacaoId = solicitacaoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getTipoCredito() { return tipoCredito; }
    public void setTipoCredito(String tipoCredito) { this.tipoCredito = tipoCredito; }
    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
}
