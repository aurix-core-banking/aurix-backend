package com.aurix.platform.investimentos.aplicacao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "aplicacoes")
public class Aplicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String tenantId;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private Long produtoId;

    @Column(nullable = false, length = 20)
    private String produtoTipo;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorAplicado;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorBruto;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorLiquido;

    @Column(precision = 18, scale = 2)
    private BigDecimal iof;

    @Column(precision = 18, scale = 2)
    private BigDecimal ir;

    @Column(nullable = false, precision = 7, scale = 5)
    private BigDecimal taxaRendimento;

    @Column(nullable = false)
    private LocalDate dataAplicacao;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    @Column(nullable = false)
    private LocalDate dataResgate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusAplicacao status;

    @Column(nullable = false)
    private Long contaCorrenteId;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    public Aplicacao() {}

    public Aplicacao(String tenantId, Long clienteId, Long produtoId, String produtoTipo,
                     BigDecimal valorAplicado, BigDecimal taxaRendimento, LocalDate dataAplicacao,
                     LocalDate dataVencimento, Long contaCorrenteId) {
        this.tenantId = tenantId;
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.produtoTipo = produtoTipo;
        this.valorAplicado = valorAplicado;
        this.valorBruto = valorAplicado;
        this.valorLiquido = valorAplicado;
        this.taxaRendimento = taxaRendimento;
        this.dataAplicacao = dataAplicacao;
        this.dataVencimento = dataVencimento;
        this.contaCorrenteId = contaCorrenteId;
        this.status = StatusAplicacao.APLICADA;
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
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public String getProdutoTipo() { return produtoTipo; }
    public void setProdutoTipo(String produtoTipo) { this.produtoTipo = produtoTipo; }
    public BigDecimal getValorAplicado() { return valorAplicado; }
    public void setValorAplicado(BigDecimal valorAplicado) { this.valorAplicado = valorAplicado; }
    public BigDecimal getValorBruto() { return valorBruto; }
    public void setValorBruto(BigDecimal valorBruto) { this.valorBruto = valorBruto; }
    public BigDecimal getValorLiquido() { return valorLiquido; }
    public void setValorLiquido(BigDecimal valorLiquido) { this.valorLiquido = valorLiquido; }
    public BigDecimal getIof() { return iof; }
    public void setIof(BigDecimal iof) { this.iof = iof; }
    public BigDecimal getIr() { return ir; }
    public void setIr(BigDecimal ir) { this.ir = ir; }
    public BigDecimal getTaxaRendimento() { return taxaRendimento; }
    public void setTaxaRendimento(BigDecimal taxaRendimento) { this.taxaRendimento = taxaRendimento; }
    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDate dataAplicacao) { this.dataAplicacao = dataAplicacao; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public LocalDate getDataResgate() { return dataResgate; }
    public void setDataResgate(LocalDate dataResgate) { this.dataResgate = dataResgate; }
    public StatusAplicacao getStatus() { return status; }
    public void setStatus(StatusAplicacao status) { this.status = status; }
    public Long getContaCorrenteId() { return contaCorrenteId; }
    public void setContaCorrenteId(Long contaCorrenteId) { this.contaCorrenteId = contaCorrenteId; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
