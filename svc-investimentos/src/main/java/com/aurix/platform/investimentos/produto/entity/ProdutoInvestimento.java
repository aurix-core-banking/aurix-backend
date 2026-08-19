package com.aurix.platform.investimentos.produto.entity;

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
@Table(name = "produtos_investimento")
public class ProdutoInvestimento {

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
    private TipoProdutoInvestimento tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoRenda tipoRenda;

    @Column(nullable = false, precision = 7, scale = 5)
    private BigDecimal taxaRendimento;

    @Column(precision = 7, scale = 5)
    private BigDecimal taxaAdm;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorMinimo;

    @Column(nullable = false)
    private Integer prazoMinimoDias;

    private LocalDate dataVencimento;

    private Integer carenciaDias;

    @Column(nullable = false)
    private Boolean ativo = true;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    public ProdutoInvestimento() {}

    public ProdutoInvestimento(String tenantId, String nome, String descricao, TipoProdutoInvestimento tipo,
                               TipoRenda tipoRenda, BigDecimal taxaRendimento, BigDecimal taxaAdm,
                               BigDecimal valorMinimo, Integer prazoMinimoDias, LocalDate dataVencimento,
                               Integer carenciaDias) {
        this.tenantId = tenantId;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.tipoRenda = tipoRenda;
        this.taxaRendimento = taxaRendimento;
        this.taxaAdm = taxaAdm;
        this.valorMinimo = valorMinimo;
        this.prazoMinimoDias = prazoMinimoDias;
        this.dataVencimento = dataVencimento;
        this.carenciaDias = carenciaDias;
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
    public TipoProdutoInvestimento getTipo() { return tipo; }
    public void setTipo(TipoProdutoInvestimento tipo) { this.tipo = tipo; }
    public TipoRenda getTipoRenda() { return tipoRenda; }
    public void setTipoRenda(TipoRenda tipoRenda) { this.tipoRenda = tipoRenda; }
    public BigDecimal getTaxaRendimento() { return taxaRendimento; }
    public void setTaxaRendimento(BigDecimal taxaRendimento) { this.taxaRendimento = taxaRendimento; }
    public BigDecimal getTaxaAdm() { return taxaAdm; }
    public void setTaxaAdm(BigDecimal taxaAdm) { this.taxaAdm = taxaAdm; }
    public BigDecimal getValorMinimo() { return valorMinimo; }
    public void setValorMinimo(BigDecimal valorMinimo) { this.valorMinimo = valorMinimo; }
    public Integer getPrazoMinimoDias() { return prazoMinimoDias; }
    public void setPrazoMinimoDias(Integer prazoMinimoDias) { this.prazoMinimoDias = prazoMinimoDias; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }
    public Integer getCarenciaDias() { return carenciaDias; }
    public void setCarenciaDias(Integer carenciaDias) { this.carenciaDias = carenciaDias; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
