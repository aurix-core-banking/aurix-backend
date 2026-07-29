package com.aurix.platform.banking.salario.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "folhas_pagamento", schema = "aurix")
public class FolhaPagamento extends BaseEntity {

    @NotNull
    @Column(name = "empresa_id", nullable = false)
    private Long empresaId;

    @NotNull
    @Column(name = "arquivo_nome", nullable = false, length = 255)
    private String arquivoNome;

    @NotNull
    @Column(name = "total_funcionarios", nullable = false)
    private Integer totalFuncionarios;

    @NotNull
    @DecimalMin("0.01")
    @Column(name = "valor_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorTotal;

    @NotNull
    @Column(name = "data_referencia", nullable = false)
    private LocalDate dataReferencia;

    @NotNull
    @Column(name = "data_processamento", nullable = false)
    private LocalDateTime dataProcessamento = LocalDateTime.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusFolha status = StatusFolha.RECEBIDO;

    public enum StatusFolha {
        RECEBIDO, VALIDADO, PROCESSADO, ERRO_ESTRUTURA
    }

    public FolhaPagamento() {}

    public FolhaPagamento(Long empresaId, String arquivoNome, Integer totalFuncionarios,
                          BigDecimal valorTotal, LocalDate dataReferencia) {
        this.empresaId = empresaId;
        this.arquivoNome = arquivoNome;
        this.totalFuncionarios = totalFuncionarios;
        this.valorTotal = valorTotal;
        this.dataReferencia = dataReferencia;
    }

    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
    public String getArquivoNome() { return arquivoNome; }
    public void setArquivoNome(String arquivoNome) { this.arquivoNome = arquivoNome; }
    public Integer getTotalFuncionarios() { return totalFuncionarios; }
    public void setTotalFuncionarios(Integer totalFuncionarios) { this.totalFuncionarios = totalFuncionarios; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public LocalDate getDataReferencia() { return dataReferencia; }
    public void setDataReferencia(LocalDate dataReferencia) { this.dataReferencia = dataReferencia; }
    public LocalDateTime getDataProcessamento() { return dataProcessamento; }
    public void setDataProcessamento(LocalDateTime dataProcessamento) { this.dataProcessamento = dataProcessamento; }
    public StatusFolha getStatus() { return status; }
    public void setStatus(StatusFolha status) { this.status = status; }
}
