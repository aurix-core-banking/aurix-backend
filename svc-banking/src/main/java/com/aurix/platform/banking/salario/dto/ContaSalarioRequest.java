package com.aurix.platform.banking.salario.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ContaSalarioRequest {
    @NotNull
    private Long contaCorrenteId;
    @NotNull
    private Long empresaId;
    @NotBlank
    private String matriculaFuncionario;
    @NotBlank
    private String cpfFuncionario;
    @NotNull
    private LocalDate dataAdmissao;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valorSalarioBruto;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valorSalarioLiquido;
    @NotNull
    @Min(1)
    @Max(31)
    private Integer diaPagamento;

    public ContaSalarioRequest() {}
    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long v) { this.contaCorrenteId = v; }
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long v) { this.empresaId = v; }
    public String getMatriculaFuncionario() { return matriculaFuncionario; }
    public void setMatriculaFuncionario(String v) { this.matriculaFuncionario = v; }
    public String getCpfFuncionario() { return cpfFuncionario; }
    public void setCpfFuncionario(String v) { this.cpfFuncionario = v; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate v) { this.dataAdmissao = v; }
    public BigDecimal getValorSalarioBruto() { return valorSalarioBruto; }
    public void setValorSalarioBruto(BigDecimal v) { this.valorSalarioBruto = v; }
    public BigDecimal getValorSalarioLiquido() { return valorSalarioLiquido; }
    public void setValorSalarioLiquido(BigDecimal v) { this.valorSalarioLiquido = v; }
    public Integer getDiaPagamento() { return diaPagamento; }
    public void setDiaPagamento(Integer v) { this.diaPagamento = v; }
}
