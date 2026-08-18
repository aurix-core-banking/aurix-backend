package com.aurix.platform.banking.extrato.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ExtratoRequest {

    @NotNull(message = "ID da conta e obrigatorio")
    private Long contaId;

    @NotNull(message = "Data inicio e obrigatoria")
    private LocalDate dataInicio;

    @NotNull(message = "Data fim e obrigatoria")
    private LocalDate dataFim;

    private String tipoMovimentacao;
    private String descricao;
    private java.math.BigDecimal valorMinimo;
    private java.math.BigDecimal valorMaximo;

    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public String getTipoMovimentacao() { return tipoMovimentacao; }
    public void setTipoMovimentacao(String tipoMovimentacao) { this.tipoMovimentacao = tipoMovimentacao; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public java.math.BigDecimal getValorMinimo() { return valorMinimo; }
    public void setValorMinimo(java.math.BigDecimal valorMinimo) { this.valorMinimo = valorMinimo; }
    public java.math.BigDecimal getValorMaximo() { return valorMaximo; }
    public void setValorMaximo(java.math.BigDecimal valorMaximo) { this.valorMaximo = valorMaximo; }
}
