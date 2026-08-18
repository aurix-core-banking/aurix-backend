package com.aurix.platform.banking.boleto.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "boletos_registrados", schema = "aurix")
public class BoletoRegistrado extends BaseEntity {

    @NotBlank
    @Column(name = "codigo_barras", nullable = false, length = 44)
    private String codigoBarras;

    @NotBlank
    @Column(name = "linha_digitavel", nullable = false, length = 54)
    private String linhaDigitavel;

    @NotNull
    @Column(name = "conta_id", nullable = false)
    private Long contaId;

    @Column(name = "conta_numero", length = 20)
    private String contaNumero;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusBoleto status = StatusBoleto.PENDENTE;

    @Column(name = "multa_percentual", precision = 5, scale = 2)
    private BigDecimal multaPercentual = new BigDecimal("2.00");

    @Column(name = "juros_percentual_mes", precision = 5, scale = 2)
    private BigDecimal jurosPercentualMes = new BigDecimal("1.00");

    @Column(name = "valor_multa", precision = 15, scale = 2)
    private BigDecimal valorMulta;

    @Column(name = "valor_juros", precision = 15, scale = 2)
    private BigDecimal valorJuros;

    @Column(name = "valor_total_pago", precision = 15, scale = 2)
    private BigDecimal valorTotalPago;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "data_baixa")
    private LocalDateTime dataBaixa;

    @Column(name = "data_protesto")
    private LocalDateTime dataProtesto;

    @Column(name = "nosso_numero", length = 20)
    private String nossoNumero;

    @Column(name = "beneficiario_nome", length = 200)
    private String beneficiarioNome;

    @Column(name = "beneficiario_documento", length = 20)
    private String beneficiarioDocumento;

    @Column(name = "pagador_nome", length = 200)
    private String pagadorNome;

    @Column(name = "pagador_documento", length = 20)
    private String pagadorDocumento;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "qtde_dias_protesto")
    private Integer qtdeDiasProtesto;

    @Column(name = "aceite")
    private Boolean aceite = false;

    public enum StatusBoleto {
        PENDENTE, PAGO, VENCIDO, PROTESTADO, BAIXADO, CANCELADO
    }

    public BoletoRegistrado() {}

    public BoletoRegistrado(String codigoBarras, String linhaDigitavel, Long contaId,
                            BigDecimal valor, LocalDate dataVencimento) {
        this.codigoBarras = codigoBarras;
        this.linhaDigitavel = linhaDigitavel;
        this.contaId = contaId;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
    }

    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public String getContaNumero() { return contaNumero; }
    public void setContaNumero(String contaNumero) { this.contaNumero = contaNumero; }
    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
    public String getLinhaDigitavel() { return linhaDigitavel; }
    public void setLinhaDigitavel(String linhaDigitavel) { this.linhaDigitavel = linhaDigitavel; }
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
    public LocalDateTime getDataProtesto() { return dataProtesto; }
    public void setDataProtesto(LocalDateTime dataProtesto) { this.dataProtesto = dataProtesto; }
    public String getNossoNumero() { return nossoNumero; }
    public void setNossoNumero(String nossoNumero) { this.nossoNumero = nossoNumero; }
    public String getBeneficiarioNome() { return beneficiarioNome; }
    public void setBeneficiarioNome(String beneficiarioNome) { this.beneficiarioNome = beneficiarioNome; }
    public String getBeneficiarioDocumento() { return beneficiarioDocumento; }
    public void setBeneficiarioDocumento(String beneficiarioDocumento) { this.beneficiarioDocumento = beneficiarioDocumento; }
    public String getPagadorNome() { return pagadorNome; }
    public void setPagadorNome(String pagadorNome) { this.pagadorNome = pagadorNome; }
    public String getPagadorDocumento() { return pagadorDocumento; }
    public void setPagadorDocumento(String pagadorDocumento) { this.pagadorDocumento = pagadorDocumento; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Integer getQtdeDiasProtesto() { return qtdeDiasProtesto; }
    public void setQtdeDiasProtesto(Integer qtdeDiasProtesto) { this.qtdeDiasProtesto = qtdeDiasProtesto; }
    public Boolean getAceite() { return aceite; }
    public void setAceite(Boolean aceite) { this.aceite = aceite; }
}
