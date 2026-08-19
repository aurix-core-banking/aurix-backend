package com.aurix.platform.salary.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ContaSalarioResponse {

    private Long id;
    private Long contaCorrenteId;
    private Long empresaId;
    private String matriculaFuncionario;
    private String cpfFuncionario;
    private LocalDate dataAdmissao;
    private LocalDate dataRescisao;
    private BigDecimal valorSalarioBruto;
    private BigDecimal valorSalarioLiquido;
    private Integer diaPagamento;
    private Boolean portabilidadeAtiva;
    private String status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContaCorrenteId() {
        return contaCorrenteId;
    }

    public void setContaCorrenteId(Long contaCorrenteId) {
        this.contaCorrenteId = contaCorrenteId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
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

    public LocalDate getDataRescisao() {
        return dataRescisao;
    }

    public void setDataRescisao(LocalDate dataRescisao) {
        this.dataRescisao = dataRescisao;
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

    public Boolean getPortabilidadeAtiva() {
        return portabilidadeAtiva;
    }

    public void setPortabilidadeAtiva(Boolean portabilidadeAtiva) {
        this.portabilidadeAtiva = portabilidadeAtiva;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
