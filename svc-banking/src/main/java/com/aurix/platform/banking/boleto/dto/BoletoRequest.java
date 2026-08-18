package com.aurix.platform.banking.boleto.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class BoletoRequest {

    @NotNull(message = "ID da conta e obrigatorio")
    private Long contaId;

    @NotNull(message = "Valor e obrigatorio")
    @DecimalMin(value = "0.01", message = "Valor minimo e R$ 0,01")
    private BigDecimal valor;

    @NotNull(message = "Data de vencimento e obrigatoria")
    private LocalDate dataVencimento;

    private String beneficiarioNome;
    private String beneficiarioDocumento;
    private String pagadorNome;
    private String pagadorDocumento;
    private String descricao;
    private BigDecimal multaPercentual;
    private BigDecimal jurosPercentualMes;
    private Integer qtdeDiasProtesto;
    private Boolean aceite;

    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
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
    public BigDecimal getMultaPercentual() { return multaPercentual; }
    public void setMultaPercentual(BigDecimal multaPercentual) { this.multaPercentual = multaPercentual; }
    public BigDecimal getJurosPercentualMes() { return jurosPercentualMes; }
    public void setJurosPercentualMes(BigDecimal jurosPercentualMes) { this.jurosPercentualMes = jurosPercentualMes; }
    public Integer getQtdeDiasProtesto() { return qtdeDiasProtesto; }
    public void setQtdeDiasProtesto(Integer qtdeDiasProtesto) { this.qtdeDiasProtesto = qtdeDiasProtesto; }
    public Boolean getAceite() { return aceite; }
    public void setAceite(Boolean aceite) { this.aceite = aceite; }
}
