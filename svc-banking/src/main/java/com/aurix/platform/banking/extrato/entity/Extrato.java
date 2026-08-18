package com.aurix.platform.banking.extrato.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "extratos", schema = "aurix")
public class Extrato extends BaseEntity {

    @NotNull
    @Column(name = "conta_id", nullable = false)
    private Long contaId;

    @NotNull
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "pdf_path", length = 500)
    private String pdfPath;

    @Column(name = "saldo_anterior", precision = 15, scale = 2)
    private java.math.BigDecimal saldoAnterior;

    @Column(name = "saldo_final", precision = 15, scale = 2)
    private java.math.BigDecimal saldoFinal;

    @Column(name = "total_creditos", precision = 15, scale = 2)
    private java.math.BigDecimal totalCreditos;

    @Column(name = "total_debitos", precision = 15, scale = 2)
    private java.math.BigDecimal totalDebitos;

    @Column(name = "quantidade_movimentacoes")
    private Integer quantidadeMovimentacoes;

    @Column(name = "data_geracao")
    private LocalDateTime dataGeracao = LocalDateTime.now();

    public Extrato() {}

    public Extrato(Long contaId, LocalDate dataInicio, LocalDate dataFim) {
        this.contaId = contaId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Long getContaId() { return contaId; }
    public void setContaId(Long contaId) { this.contaId = contaId; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }
    public java.math.BigDecimal getSaldoAnterior() { return saldoAnterior; }
    public void setSaldoAnterior(java.math.BigDecimal saldoAnterior) { this.saldoAnterior = saldoAnterior; }
    public java.math.BigDecimal getSaldoFinal() { return saldoFinal; }
    public void setSaldoFinal(java.math.BigDecimal saldoFinal) { this.saldoFinal = saldoFinal; }
    public java.math.BigDecimal getTotalCreditos() { return totalCreditos; }
    public void setTotalCreditos(java.math.BigDecimal totalCreditos) { this.totalCreditos = totalCreditos; }
    public java.math.BigDecimal getTotalDebitos() { return totalDebitos; }
    public void setTotalDebitos(java.math.BigDecimal totalDebitos) { this.totalDebitos = totalDebitos; }
    public Integer getQuantidadeMovimentacoes() { return quantidadeMovimentacoes; }
    public void setQuantidadeMovimentacoes(Integer quantidadeMovimentacoes) { this.quantidadeMovimentacoes = quantidadeMovimentacoes; }
    public LocalDateTime getDataGeracao() { return dataGeracao; }
    public void setDataGeracao(LocalDateTime dataGeracao) { this.dataGeracao = dataGeracao; }
}
