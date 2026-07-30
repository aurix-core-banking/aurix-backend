package com.aurix.platform.credit.financiamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "parcelas_financiamento")
public class ParcelaFinanciamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long contratoId;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorParcela;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorAmortizacao;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorJuros;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorSaldoDevolver;

    private LocalDate dataPagamento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusParcela status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public ParcelaFinanciamento() {}

    public ParcelaFinanciamento(Long contratoId, int numero, LocalDate dataVencimento, BigDecimal valorParcela, BigDecimal valorAmortizacao, BigDecimal valorJuros, BigDecimal valorSaldoDevolver, LocalDate dataPagamento, StatusParcela status) {
        this.contratoId = contratoId;
        this.numero = numero;
        this.dataVencimento = dataVencimento;
        this.valorParcela = valorParcela;
        this.valorAmortizacao = valorAmortizacao;
        this.valorJuros = valorJuros;
        this.valorSaldoDevolver = valorSaldoDevolver;
        this.dataPagamento = dataPagamento;
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public BigDecimal getValorParcela() { return valorParcela; }
    public void setValorParcela(BigDecimal valorParcela) { this.valorParcela = valorParcela; }
    public BigDecimal getValorAmortizacao() { return valorAmortizacao; }
    public void setValorAmortizacao(BigDecimal valorAmortizacao) { this.valorAmortizacao = valorAmortizacao; }
    public BigDecimal getValorJuros() { return valorJuros; }
    public void setValorJuros(BigDecimal valorJuros) { this.valorJuros = valorJuros; }
    public BigDecimal getValorSaldoDevolver() { return valorSaldoDevolver; }
    public void setValorSaldoDevolver(BigDecimal valorSaldoDevolver) { this.valorSaldoDevolver = valorSaldoDevolver; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
    public StatusParcela getStatus() { return status; }
    public void setStatus(StatusParcela status) { this.status = status; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
