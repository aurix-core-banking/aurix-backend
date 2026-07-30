package com.aurix.platform.banking.salario.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contas_salario", schema = "aurix",
       uniqueConstraints = @UniqueConstraint(columnNames = {"empresa_id", "matricula_funcionario"}))
public class ContaSalario extends BaseEntity {

    @NotNull
    @Column(name = "conta_corrente_id", nullable = false)
    private Long contaCorrenteId;

    @NotNull
    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

        @NotBlank
        @Column(name = "matricula_funcionario", nullable = false, length = 50)
        private String matriculaFuncionario;

        @NotBlank
        @Column(name = "cpf_funcionario", nullable = false, length = 11)
        private String cpfFuncionario;

    @NotNull
    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    @Column(name = "data_rescisao")
    private LocalDate dataRescisao;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "valor_salario_bruto", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorSalarioBruto;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "valor_salario_liquido", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorSalarioLiquido;

    @NotNull
    @Min(1)
    @Max(31)
    @Column(name = "dia_pagamento", nullable = false)
    private Integer diaPagamento;

    @Column(name = "portabilidade_ativa", nullable = false)
    private Boolean portabilidadeAtiva = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusContaSalario status = StatusContaSalario.ATIVA;

    public enum StatusContaSalario {
        ATIVA, RESCINDIDA, BLOQUEADA
    }

    public ContaSalario() {}

    public ContaSalario(Long contaCorrenteId, Long empresaId, String matriculaFuncionario,
                        String cpfFuncionario, LocalDate dataAdmissao,
                        BigDecimal valorSalarioBruto,
                        BigDecimal valorSalarioLiquido, Integer diaPagamento) {
        this.contaCorrenteId = contaCorrenteId;
        this.empresaId = empresaId;
        this.matriculaFuncionario = matriculaFuncionario;
        this.cpfFuncionario = cpfFuncionario;
        this.dataAdmissao = dataAdmissao;
        this.valorSalarioBruto = valorSalarioBruto;
        this.valorSalarioLiquido = valorSalarioLiquido;
        this.diaPagamento = diaPagamento;
    }

    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
    public String getMatriculaFuncionario() { return matriculaFuncionario; }
    public void setMatriculaFuncionario(String matriculaFuncionario) { this.matriculaFuncionario = matriculaFuncionario; }
    public String getCpfFuncionario() { return cpfFuncionario; }
    public void setCpfFuncionario(String cpfFuncionario) { this.cpfFuncionario = cpfFuncionario; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }
    public LocalDate getDataRescisao() { return dataRescisao; }
    public void setDataRescisao(LocalDate dataRescisao) { this.dataRescisao = dataRescisao; }
    public BigDecimal getValorSalarioBruto() { return valorSalarioBruto; }
    public void setValorSalarioBruto(BigDecimal valorSalarioBruto) { this.valorSalarioBruto = valorSalarioBruto; }
    public BigDecimal getValorSalarioLiquido() { return valorSalarioLiquido; }
    public void setValorSalarioLiquido(BigDecimal valorSalarioLiquido) { this.valorSalarioLiquido = valorSalarioLiquido; }
    public Integer getDiaPagamento() { return diaPagamento; }
    public void setDiaPagamento(Integer diaPagamento) { this.diaPagamento = diaPagamento; }
    public Boolean getPortabilidadeAtiva() { return portabilidadeAtiva; }
    public void setPortabilidadeAtiva(Boolean portabilidadeAtiva) { this.portabilidadeAtiva = portabilidadeAtiva; }
    public StatusContaSalario getStatus() { return status; }
    public void setStatus(StatusContaSalario status) { this.status = status; }
}
