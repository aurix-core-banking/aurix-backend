package com.aurix.platform.seguros.produto.entity;

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
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos_seguro")
public class ProdutoSeguro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String tenantId;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoSeguro tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoCobertura coberturaPadrao;

    @Column(nullable = false, precision = 7, scale = 5)
    private BigDecimal taxaBase;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal premioMinimo;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal carenciaMeses;

    @Column(nullable = false)
    private Integer prazoAnaliseDias;

    @Column(nullable = false)
    private Integer prazoPagamentoSinistroDias;

    @Column(nullable = false)
    private Boolean ativo = true;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    public ProdutoSeguro() {}

    public ProdutoSeguro(String tenantId, String nome, String descricao, TipoSeguro tipo,
                         TipoCobertura coberturaPadrao, BigDecimal taxaBase, BigDecimal premioMinimo,
                         BigDecimal carenciaMeses, Integer prazoAnaliseDias,
                         Integer prazoPagamentoSinistroDias) {
        this.tenantId = tenantId;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.coberturaPadrao = coberturaPadrao;
        this.taxaBase = taxaBase;
        this.premioMinimo = premioMinimo;
        this.carenciaMeses = carenciaMeses;
        this.prazoAnaliseDias = prazoAnaliseDias;
        this.prazoPagamentoSinistroDias = prazoPagamentoSinistroDias;
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
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public TipoSeguro getTipo() { return tipo; }
    public void setTipo(TipoSeguro tipo) { this.tipo = tipo; }
    public TipoCobertura getCoberturaPadrao() { return coberturaPadrao; }
    public void setCoberturaPadrao(TipoCobertura coberturaPadrao) { this.coberturaPadrao = coberturaPadrao; }
    public BigDecimal getTaxaBase() { return taxaBase; }
    public void setTaxaBase(BigDecimal taxaBase) { this.taxaBase = taxaBase; }
    public BigDecimal getPremioMinimo() { return premioMinimo; }
    public void setPremioMinimo(BigDecimal premioMinimo) { this.premioMinimo = premioMinimo; }
    public BigDecimal getCarenciaMeses() { return carenciaMeses; }
    public void setCarenciaMeses(BigDecimal carenciaMeses) { this.carenciaMeses = carenciaMeses; }
    public Integer getPrazoAnaliseDias() { return prazoAnaliseDias; }
    public void setPrazoAnaliseDias(Integer prazoAnaliseDias) { this.prazoAnaliseDias = prazoAnaliseDias; }
    public Integer getPrazoPagamentoSinistroDias() { return prazoPagamentoSinistroDias; }
    public void setPrazoPagamentoSinistroDias(Integer prazoPagamentoSinistroDias) { this.prazoPagamentoSinistroDias = prazoPagamentoSinistroDias; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
