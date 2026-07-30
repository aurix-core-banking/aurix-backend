package com.aurix.platform.banking.salario.dto;

import com.aurix.platform.banking.salario.entity.FolhaPagamento.StatusFolha;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FolhaResponse {
    private Long id;
    private Long empresaId;
    private String arquivoNome;
    private Integer totalFuncionarios;
    private BigDecimal valorTotal;
    private LocalDate dataReferencia;
    private LocalDateTime dataProcessamento;
    private StatusFolha status;
    private LocalDateTime dataCriacao;

    public FolhaResponse() {}
    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long v) { this.empresaId = v; }
    public String getArquivoNome() { return arquivoNome; }
    public void setArquivoNome(String v) { this.arquivoNome = v; }
    public Integer getTotalFuncionarios() { return totalFuncionarios; }
    public void setTotalFuncionarios(Integer v) { this.totalFuncionarios = v; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal v) { this.valorTotal = v; }
    public LocalDate getDataReferencia() { return dataReferencia; }
    public void setDataReferencia(LocalDate v) { this.dataReferencia = v; }
    public LocalDateTime getDataProcessamento() { return dataProcessamento; }
    public void setDataProcessamento(LocalDateTime v) { this.dataProcessamento = v; }
    public StatusFolha getStatus() { return status; }
    public void setStatus(StatusFolha v) { this.status = v; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime v) { this.dataCriacao = v; }
}
