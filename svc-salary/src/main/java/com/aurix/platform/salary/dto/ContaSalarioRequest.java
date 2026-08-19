package com.aurix.platform.salary.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ContaSalarioRequest {

    @NotNull(message = "ID da empresa e obrigatorio")
    private Long empresaId;

    @NotNull(message = "ID da conta corrente e obrigatorio")
    private Long contaCorrenteId;

    private String matriculaFuncionario;
    private String cpfFuncionario;
    private LocalDate dataAdmissao;
    private BigDecimal valorSalarioBruto;
    private BigDecimal valorSalarioLiquido;
    private Integer diaPagamento;

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Long getContaCorrenteId() {
        return contaCorrenteId;
    }

    public void setContaCorrenteId(Long contaCorrenteId) {
        this.contaCorrenteId = contaCorrenteId;
    }

    public String getMatriculaFuncionario() {
        return matriculaFuncionario;
    }

    public void setMatriculaFuncionario(String matriculaFuncionario) {
        this.matriculaFuncionario = matriculaFuncionario;
    }

    public String getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(String cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public BigDecimal getValorSalarioBruto() {
        return valorSalarioBruto;
    }

    public void setValorSalarioBruto(BigDecimal valorSalarioBruto) {
        this.valorSalarioBruto = valorSalarioBruto;
    }

    public BigDecimal getValorSalarioLiquido() {
        return valorSalarioLiquido;
    }

    public void setValorSalarioLiquido(BigDecimal valorSalarioLiquido) {
        this.valorSalarioLiquido = valorSalarioLiquido;
    }

    public Integer getDiaPagamento() {
        return diaPagamento;
    }

    public void setDiaPagamento(Integer diaPagamento) {
        this.diaPagamento = diaPagamento;
    }
}
