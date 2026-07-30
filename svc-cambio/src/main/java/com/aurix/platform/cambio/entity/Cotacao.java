package com.aurix.platform.cambio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cotacoes_cambio")
public class Cotacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String moeda;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal taxaCompra;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal taxaVenda;

    @Column(nullable = false)
    private LocalDateTime dataCotacao;

    @Column(nullable = false, length = 20)
    private String fonte;

    @Column(nullable = false, length = 50)
    private String tenantId;

    public Cotacao() {}

    public Cotacao(String moeda, BigDecimal taxaCompra, BigDecimal taxaVenda, LocalDateTime dataCotacao, String fonte, String tenantId) {
        this.moeda = moeda;
        this.taxaCompra = taxaCompra;
        this.taxaVenda = taxaVenda;
        this.dataCotacao = dataCotacao;
        this.fonte = fonte;
        this.tenantId = tenantId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMoeda() { return moeda; }
    public void setMoeda(String moeda) { this.moeda = moeda; }
    public BigDecimal getTaxaCompra() { return taxaCompra; }
    public void setTaxaCompra(BigDecimal taxaCompra) { this.taxaCompra = taxaCompra; }
    public BigDecimal getTaxaVenda() { return taxaVenda; }
    public void setTaxaVenda(BigDecimal taxaVenda) { this.taxaVenda = taxaVenda; }
    public LocalDateTime getDataCotacao() { return dataCotacao; }
    public void setDataCotacao(LocalDateTime dataCotacao) { this.dataCotacao = dataCotacao; }
    public String getFonte() { return fonte; }
    public void setFonte(String fonte) { this.fonte = fonte; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
}
