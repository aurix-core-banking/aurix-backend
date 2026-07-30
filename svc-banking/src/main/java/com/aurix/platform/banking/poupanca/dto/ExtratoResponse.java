package com.aurix.platform.banking.poupanca.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ExtratoResponse {

    private Long contaId;
    private String numeroConta;
    private BigDecimal saldoAtual;
    private BigDecimal rendimentoPeriodo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private List<MovimentacaoItem> movimentacoes;

    public static class MovimentacaoItem {
        private LocalDateTime data;
        private String descricao;
        private BigDecimal valor;
        private BigDecimal saldo;

        public LocalDateTime getData() { return data; }
        public void setData(LocalDateTime data) { this.data = data; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public BigDecimal getValor() { return valor; }
        public void setValor(BigDecimal valor) { this.valor = valor; }
        public BigDecimal getSaldo() { return saldo; }
        public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    }

    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public String getNumeroConta() { return numeroConta; }
    public void setNumeroConta(String numeroConta) { this.numeroConta = numeroConta; }
    public BigDecimal getSaldoAtual() { return saldoAtual; }
    public void setSaldoAtual(BigDecimal saldoAtual) { this.saldoAtual = saldoAtual; }
    public BigDecimal getRendimentoPeriodo() { return rendimentoPeriodo; }
    public void setRendimentoPeriodo(BigDecimal rendimentoPeriodo) { this.rendimentoPeriodo = rendimentoPeriodo; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
    public List<MovimentacaoItem> getMovimentacoes() { return movimentacoes; }
    public void setMovimentacoes(List<MovimentacaoItem> movimentacoes) { this.movimentacoes = movimentacoes; }
}
