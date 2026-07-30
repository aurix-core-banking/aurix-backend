package com.aurix.platform.credit.financiamento.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ParcelaResponse {

    private Long id;
    private Long contratoId;
    private int numero;
    private LocalDate dataVencimento;
    private BigDecimal valorParcela;
    private BigDecimal valorAmortizacao;
    private BigDecimal valorJuros;
    private BigDecimal valorSaldoDevolver;
    private LocalDate dataPagamento;
    private String status;

    public ParcelaResponse() {}

    public ParcelaResponse(Long id, Long contratoId, int numero, LocalDate dataVencimento, BigDecimal valorParcela, BigDecimal valorAmortizacao, BigDecimal valorJuros, BigDecimal valorSaldoDevolver, LocalDate dataPagamento, String status) {
        this.id = id;
        this.contratoId = contratoId;
        this.numero = numero;
        this.dataVencimento = dataVencimento;
        this.valorParcela = valorParcela;
        this.valorAmortizacao = valorAmortizacao;
        this.valorJuros = valorJuros;
        this.valorSaldoDevolver = valorSaldoDevolver;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public BigDecimal getValorParcela() { return valorParcela; }
    public void setValorParcela(BigDecimal valorParcela) { this.valorParcela = valorParcela; }
    public BigDecimal getValorAmortizacao() { return valorAmortizacao; }
    public void setValorAmortizacao(BigDecimal valorAmortizacao) { this.valorAmortizacao = valorAmortizacao; }
    public BigDecimal getValorJuros() { return valorJuros; }
    public void setValorJuros(BigDecimal valorJuros) { this.valorJuros = valorJuros; }
    public BigDecimal getValorSaldoDevolver() { return valorSaldoDevolver; }
    public void setValorSaldoDevolver(BigDecimal valorSaldoDevolver) { this.valorSaldoDevolver = valorSaldoDevolver; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
