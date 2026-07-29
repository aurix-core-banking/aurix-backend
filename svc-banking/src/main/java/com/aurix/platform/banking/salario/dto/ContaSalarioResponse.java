package com.aurix.platform.banking.salario.dto;

import com.aurix.platform.banking.salario.entity.ContaSalario.StatusContaSalario;
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
    private StatusContaSalario status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public ContaSalarioResponse() {}
    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
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
    public LocalDate getDataRescisao() { return dataRescisao; }
    public void setDataRescisao(LocalDate v) { this.dataRescisao = v; }
    public BigDecimal getValorSalarioBruto() { return valorSalarioBruto; }
    public void setValorSalarioBruto(BigDecimal v) { this.valorSalarioBruto = v; }
    public BigDecimal getValorSalarioLiquido() { return valorSalarioLiquido; }
    public void setValorSalarioLiquido(BigDecimal v) { this.valorSalarioLiquido = v; }
    public Integer getDiaPagamento() { return diaPagamento; }
    public void setDiaPagamento(Integer v) { this.diaPagamento = v; }
    public Boolean getPortabilidadeAtiva() { return portabilidadeAtiva; }
    public void setPortabilidadeAtiva(Boolean v) { this.portabilidadeAtiva = v; }
    public StatusContaSalario getStatus() { return status; }
    public void setStatus(StatusContaSalario v) { this.status = v; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime v) { this.dataCriacao = v; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime v) { this.dataAtualizacao = v; }
}
