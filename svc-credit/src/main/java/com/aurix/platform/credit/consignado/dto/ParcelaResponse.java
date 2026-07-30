package com.aurix.platform.credit.consignado.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ParcelaResponse {

    private Long id;
    private Long contratoId;
    private int numero;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private String status;

    public ParcelaResponse() {}

    public ParcelaResponse(Long id, Long contratoId, int numero, BigDecimal valor, LocalDate dataVencimento, LocalDate dataPagamento, String status) {
        this.id = id;
        this.contratoId = contratoId;
        this.numero = numero;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
