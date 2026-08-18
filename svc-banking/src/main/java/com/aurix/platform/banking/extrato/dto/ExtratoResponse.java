package com.aurix.platform.banking.extrato.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ExtratoResponse {
    private Long id;
    private Long contaId;
    private String contaNumero;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoFinal;
    private BigDecimal totalCreditos;
    private BigDecimal totalDebitos;
    private Integer quantidadeMovimentacoes;
    private LocalDateTime dataGeracao;
    private List<MovimentacaoItem> movimentacoes;

    public ExtratoResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public String getContaNumero() { return contaNumero; }
    public void setContaNumero(String contaNumero) { this.contaNumero = contaNumero; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public BigDecimal getSaldoAnterior() { return saldoAnterior; }
    public void setSaldoAnterior(BigDecimal saldoAnterior) { this.saldoAnterior = saldoAnterior; }
    public BigDecimal getSaldoFinal() { return saldoFinal; }
    public void setSaldoFinal(BigDecimal saldoFinal) { this.saldoFinal = saldoFinal; }
    public BigDecimal getTotalCreditos() { return totalCreditos; }
    public void setTotalCreditos(BigDecimal totalCreditos) { this.totalCreditos = totalCreditos; }
    public BigDecimal getTotalDebitos() { return totalDebitos; }
    public void setTotalDebitos(BigDecimal totalDebitos) { this.totalDebitos = totalDebitos; }
    public Integer getQuantidadeMovimentacoes() { return quantidadeMovimentacoes; }
    public void setQuantidadeMovimentacoes(Integer quantidadeMovimentacoes) { this.quantidadeMovimentacoes = quantidadeMovimentacoes; }
    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDateTime dataGeracao) { this.dataGeracao = dataGeracao; }
    public List<MovimentacaoItem> getMovimentacoes() { return movimentacoes; }
    public void setMovimentacoes(List<MovimentacaoItem> movimentacoes) { this.movimentacoes = movimentacoes; }

    public static class MovimentacaoItem {
        private Long id;
        private LocalDateTime data;
        private String tipo;
        private String descricao;
        private BigDecimal valor;
        private BigDecimal saldo;

        public MovimentacaoItem() {}

        public MovimentacaoItem(Long id, LocalDateTime data, String tipo, String descricao,
                                BigDecimal valor, BigDecimal saldo) {
            this.id = id;
            this.data = data;
            this.tipo = tipo;
            this.descricao = descricao;
            this.valor = valor;
            this.saldo = saldo;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public LocalDateTime getData() { return data; }
        public void setData(LocalDateTime data) { this.data = data; }
        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public BigDecimal getValor() { return valor; }
        public void setValor(BigDecimal valor) { this.valor = valor; }
        public BigDecimal getSaldo() { return saldo; }
        public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    }
}
