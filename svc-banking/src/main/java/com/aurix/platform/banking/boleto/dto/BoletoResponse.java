package com.aurix.platform.banking.boleto.dto;

import com.aurix.platform.banking.boleto.entity.BoletoRegistrado.StatusBoleto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BoletoResponse {
    private Long id;
    private String codigoBarras;
    private String linhaDigitavel;
    private Long contaId;
    private String contaNumero;
    private BigDecimal valor;
    private LocalDate dataVencimento;
    private StatusBoleto status;
    private BigDecimal multaPercentual;
    private BigDecimal jurosPercentualMes;
    private BigDecimal valorMulta;
    private BigDecimal valorJuros;
    private BigDecimal valorTotalPago;
    private LocalDateTime dataPagamento;
    private LocalDateTime dataBaixa;
    private String nossoNumero;
    private String beneficiarioNome;
    private String pagadorNome;
    private String descricao;
    private LocalDateTime dataCriacao;

    public BoletoResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    public String getLinhaDigitavel() { return linhaDigitavel; }
    public void setLinhaDigitavel(String linhaDigitavel) { this.linhaDigitavel = linhaDigitavel; }
    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public String getContaNumero() { return contaNumero; }
    public void setContaNumero(String contaNumero) { this.contaNumero = contaNumero; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public StatusBoleto getStatus() { return status; }
    public void setStatus(StatusBoleto status) { this.status = status; }
    public BigDecimal getMultaPercentual() { return multaPercentual; }
    public void setMultaPercentual(BigDecimal multaPercentual) { this.multaPercentual = multaPercentual; }
    public BigDecimal getJurosPercentualMes() { return jurosPercentualMes; }
    public void setJurosPercentualMes(BigDecimal jurosPercentualMes) { this.jurosPercentualMes = jurosPercentualMes; }
    public BigDecimal getValorMulta() { return valorMulta; }
    public void setValorMulta(BigDecimal valorMulta) { this.valorMulta = valorMulta; }
    public BigDecimal getValorJuros() { return valorJuros; }
    public void setValorJuros(BigDecimal valorJuros) { this.valorJuros = valorJuros; }
    public BigDecimal getValorTotalPago() { return valorTotalPago; }
    public void setValorTotalPago(BigDecimal valorTotalPago) { this.valorTotalPago = valorTotalPago; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }
    public LocalDateTime getDataBaixa() { return dataBaixa; }
    public void setDataBaixa(LocalDateTime dataBaixa) { this.dataBaixa = dataBaixa; }
    public String getNossoNumero() { return nossoNumero; }
    public void setNossoNumero(String nossoNumero) { this.nossoNumero = nossoNumero; }
    public String getBeneficiarioNome() { return beneficiarioNome; }
    public void setBeneficiarioNome(String beneficiarioNome) { this.beneficiarioNome = beneficiarioNome; }
    public String getPagadorNome() { return pagadorNome; }
    public void setPagadorNome(String pagadorNome) { this.pagadorNome = pagadorNome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
}
