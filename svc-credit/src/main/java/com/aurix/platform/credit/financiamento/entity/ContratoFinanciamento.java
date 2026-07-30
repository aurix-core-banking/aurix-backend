package com.aurix.platform.credit.financiamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contratos_financiamento")
public class ContratoFinanciamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String tenantId;

    @NotNull
    @Column(nullable = false)
    private Long clienteId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoFinanciamento tipo;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorFinanciado;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorEntrada = BigDecimal.ZERO;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, precision = 7, scale = 5)
    private BigDecimal taxaJuros;

    @Min(1)
    @Column(nullable = false)
    private int prazoMeses;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SistemaAmortizacao sistemaAmortizacao;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorParcela;

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal saldoDevedor;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataContratacao;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataPrimeiraParcela;

    private LocalDate dataVencimento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusContrato status;

    @Column(nullable = false)
    private Long contaCorrenteId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    public ContratoFinanciamento() {}

    public ContratoFinanciamento(String tenantId, Long clienteId, TipoFinanciamento tipo, BigDecimal valorFinanciado, BigDecimal valorEntrada, BigDecimal taxaJuros, int prazoMeses, SistemaAmortizacao sistemaAmortizacao, BigDecimal valorParcela, BigDecimal saldoDevedor, LocalDate dataContratacao, LocalDate dataPrimeiraParcela, LocalDate dataVencimento, StatusContrato status, Long contaCorrenteId) {
        this.tenantId = tenantId;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.valorFinanciado = valorFinanciado;
        this.valorEntrada = valorEntrada;
        this.taxaJuros = taxaJuros;
        this.prazoMeses = prazoMeses;
        this.sistemaAmortizacao = sistemaAmortizacao;
        this.valorParcela = valorParcela;
        this.saldoDevedor = saldoDevedor;
        this.dataContratacao = dataContratacao;
        this.dataPrimeiraParcela = dataPrimeiraParcela;
        this.dataVencimento = dataVencimento;
        this.status = status;
        this.contaCorrenteId = contaCorrenteId;
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public TipoFinanciamento getTipo() { return tipo; }
    public void setTipo(TipoFinanciamento tipo) { this.tipo = tipo; }
    public BigDecimal getValorFinanciado() { return valorFinanciado; }
    public void setValorFinanciado(BigDecimal valorFinanciado) { this.valorFinanciado = valorFinanciado; }
    public BigDecimal getValorEntrada() { return valorEntrada; }
    public void setValorEntrada(BigDecimal valorEntrada) { this.valorEntrada = valorEntrada; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public int getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(int prazoMeses) { this.prazoMeses = prazoMeses; }
    public SistemaAmortizacao getSistemaAmortizacao() { return sistemaAmortizacao; }
    public void setSistemaAmortizacao(SistemaAmortizacao sistemaAmortizacao) { this.sistemaAmortizacao = sistemaAmortizacao; }
    public BigDecimal getValorParcela() { return valorParcela; }
    public void setValorParcela(BigDecimal valorParcela) { this.valorParcela = valorParcela; }
    public BigDecimal getSaldoDevedor() { return saldoDevedor; }
    public void setSaldoDevedor(BigDecimal saldoDevedor) { this.saldoDevedor = saldoDevedor; }
    public LocalDate getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDate dataContratacao) { this.dataContratacao = dataContratacao; }
    public LocalDate getDataPrimeiraParcela() { return dataPrimeiraParcela; }
    public void setDataPrimeiraParcela(LocalDate dataPrimeiraParcela) { this.dataPrimeiraParcela = dataPrimeiraParcela; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public StatusContrato getStatus() { return status; }
    public void setStatus(StatusContrato status) { this.status = status; }
    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
