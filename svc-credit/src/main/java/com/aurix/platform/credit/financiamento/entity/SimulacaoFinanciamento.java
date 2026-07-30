package com.aurix.platform.credit.financiamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "simulacoes_financiamento")
public class SimulacaoFinanciamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorFinanciado;

    @Column(nullable = false)
    private int prazoMeses;

    @Column(nullable = false, precision = 7, scale = 5)
    private BigDecimal taxaJuros;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SistemaAmortizacao sistemaAmortizacao;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorParcela;

    @Column(columnDefinition = "TEXT")
    private String tabelaSAC;

    @Column(columnDefinition = "TEXT")
    private String tabelaPrice;

    @Column(nullable = false)
    private LocalDateTime dataSimulacao;

    public SimulacaoFinanciamento() {}

    public SimulacaoFinanciamento(String tenantId, Long clienteId, TipoFinanciamento tipo, BigDecimal valorFinanciado, int prazoMeses, BigDecimal taxaJuros, SistemaAmortizacao sistemaAmortizacao, BigDecimal valorParcela, String tabelaSAC, String tabelaPrice, LocalDateTime dataSimulacao) {
        this.tenantId = tenantId;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.valorFinanciado = valorFinanciado;
        this.prazoMeses = prazoMeses;
        this.taxaJuros = taxaJuros;
        this.sistemaAmortizacao = sistemaAmortizacao;
        this.valorParcela = valorParcela;
        this.tabelaSAC = tabelaSAC;
        this.tabelaPrice = tabelaPrice;
        this.dataSimulacao = dataSimulacao;
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
    public int getPrazoMeses() { return prazoMeses; }
    public void setPrazoMeses(int prazoMeses) { this.prazoMeses = prazoMeses; }
    public BigDecimal getTaxaJuros() { return taxaJuros; }
    public void setTaxaJuros(BigDecimal taxaJuros) { this.taxaJuros = taxaJuros; }
    public SistemaAmortizacao getSistemaAmortizacao() { return sistemaAmortizacao; }
    public void setSistemaAmortizacao(SistemaAmortizacao sistemaAmortizacao) { this.sistemaAmortizacao = sistemaAmortizacao; }
    public BigDecimal getValorParcela() { return valorParcela; }
    public void setValorParcela(BigDecimal valorParcela) { this.valorParcela = valorParcela; }
    public String getTabelaSAC() { return tabelaSAC; }
    public void setTabelaSAC(String tabelaSAC) { this.tabelaSAC = tabelaSAC; }
    public String getTabelaPrice() { return tabelaPrice; }
    public void setTabelaPrice(String tabelaPrice) { this.tabelaPrice = tabelaPrice; }
    public LocalDateTime getDataSimulacao() { return dataSimulacao; }
    public void setDataSimulacao(LocalDateTime dataSimulacao) { this.dataSimulacao = dataSimulacao; }
}
