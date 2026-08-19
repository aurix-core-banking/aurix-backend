package com.aurix.platform.seguros.sinistro.entity;

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
@Table(name = "sinistros")
public class Sinistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String tenantId;

    @Column(nullable = false)
    private Long apoliceId;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private Long produtoId;

    @Column(nullable = false, length = 20)
    private String produtoTipo;

    @Column(nullable = false, length = 200)
    private String descricaoEvento;

    @Column(nullable = false)
    private LocalDate dataEvento;

    @Column(nullable = false)
    private LocalDate dataAbertura;

    @Column(precision = 18, scale = 2)
    private BigDecimal valorSolicitado;

    @Column(precision = 18, scale = 2)
    private BigDecimal valorAprovado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusSinistro status;

    @Column(length = 500)
    private String motivoReprovacao;

    private LocalDate dataAnalise;

    private LocalDate dataAprovacao;

    private LocalDate dataPagamento;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    public Sinistro() {}

    public Sinistro(String tenantId, Long apoliceId, Long clienteId, Long produtoId,
                    String produtoTipo, String descricaoEvento, LocalDate dataEvento,
                    BigDecimal valorSolicitado) {
        this.tenantId = tenantId;
        this.apoliceId = apoliceId;
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.produtoTipo = produtoTipo;
        this.descricaoEvento = descricaoEvento;
        this.dataEvento = dataEvento;
        this.dataAbertura = LocalDate.now();
        this.valorSolicitado = valorSolicitado;
        this.status = StatusSinistro.ABERTO;
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
    public Long getApoliceId() { return apoliceId; }
    public void setApoliceId(Long apoliceId) { this.apoliceId = apoliceId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public String getProdutoTipo() { return produtoTipo; }
    public void setProdutoTipo(String produtoTipo) { this.produtoTipo = produtoTipo; }
    public String getDescricaoEvento() { return descricaoEvento; }
    public void setDescricaoEvento(String descricaoEvento) { this.descricaoEvento = descricaoEvento; }
    public LocalDate getDataEvento() { return dataEvento; }
    public void setDataEvento(LocalDate dataEvento) { this.dataEvento = dataEvento; }
    public LocalDate getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDate dataAbertura) { this.dataAbertura = dataAbertura; }
    public BigDecimal getValorSolicitado() { return valorSolicitado; }
    public void setValorSolicitado(BigDecimal valorSolicitado) { this.valorSolicitado = valorSolicitado; }
    public BigDecimal getValorAprovado() { return valorAprovado; }
    public void setValorAprovado(BigDecimal valorAprovado) { this.valorAprovado = valorAprovado; }
    public StatusSinistro getStatus() { return status; }
    public void setStatus(StatusSinistro status) { this.status = status; }
    public String getMotivoReprovacao() { return motivoReprovacao; }
    public void setMotivoReprovacao(String motivoReprovacao) { this.motivoReprovacao = motivoReprovacao; }
    public LocalDate getDataAnalise() { return dataAnalise; }
    public void setDataAnalise(LocalDate dataAnalise) { this.dataAnalise = dataAnalise; }
    public LocalDate getDataAprovacao() { return dataAprovacao; }
    public void setDataAprovacao(LocalDate dataAprovacao) { this.dataAprovacao = dataAprovacao; }
    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
