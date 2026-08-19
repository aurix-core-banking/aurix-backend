package com.aurix.platform.seguros.apolice.entity;

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
@Table(name = "apolices")
public class Apolice {

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

    @Column(nullable = false, length = 20)
    private String cobertura;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valorSegurado;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal premio;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal premioMensal;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFim;

    private LocalDate dataCancelamento;

    @Column(precision = 18, scale = 2)
    private BigDecimal valorRestituido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusApolice status;

    @Column(nullable = false)
    private Boolean renovacaoAutomatica = true;

    @Column(nullable = false)
    private Integer idadeSegurado;

    @Column(length = 2)
    private String uf;

    @Column(length = 1)
    private String sexo;

    @Column(length = 100)
    private String profissao;

    @Column(length = 50)
    private String numeroApolice;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    public Apolice() {}

    public Apolice(String tenantId, Long clienteId, Long produtoId, String produtoTipo,
                   String cobertura, BigDecimal valorSegurado, BigDecimal premio,
                   BigDecimal premioMensal, LocalDate dataInicio, LocalDate dataFim,
                   StatusApolice status, Integer idadeSegurado, String uf, String sexo,
                   String profissao, String numeroApolice) {
        this.tenantId = tenantId;
        this.clienteId = clienteId;
        this.produtoId = produtoId;
        this.produtoTipo = produtoTipo;
        this.cobertura = cobertura;
        this.valorSegurado = valorSegurado;
        this.premio = premio;
        this.premioMensal = premioMensal;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.idadeSegurado = idadeSegurado;
        this.uf = uf;
        this.sexo = sexo;
        this.profissao = profissao;
        this.numeroApolice = numeroApolice;
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
    public String getCobertura() { return cobertura; }
    public void setCobertura(String cobertura) { this.cobertura = cobertura; }
    public BigDecimal getValorSegurado() { return valorSegurado; }
    public void setValorSegurado(BigDecimal valorSegurado) { this.valorSegurado = valorSegurado; }
    public BigDecimal getPremio() { return premio; }
    public void setPremio(BigDecimal premio) { this.premio = premio; }
    public BigDecimal getPremioMensal() { return premioMensal; }
    public void setPremioMensal(BigDecimal premioMensal) { this.premioMensal = premioMensal; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public LocalDate getDataCancelamento() { return dataCancelamento; }
    public void setDataCancelamento(LocalDate dataCancelamento) { this.dataCancelamento = dataCancelamento; }
    public BigDecimal getValorRestituido() { return valorRestituido; }
    public void setValorRestituido(BigDecimal valorRestituido) { this.valorRestituido = valorRestituido; }
    public StatusApolice getStatus() { return status; }
    public void setStatus(StatusApolice status) { this.status = status; }
    public Boolean getRenovacaoAutomatica() { return renovacaoAutomatica; }
    public void setRenovacaoAutomatica(Boolean renovacaoAutomatica) { this.renovacaoAutomatica = renovacaoAutomatica; }
    public Integer getIdadeSegurado() { return idadeSegurado; }
    public void setIdadeSegurado(Integer idadeSegurado) { this.idadeSegurado = idadeSegurado; }
    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public String getProfissao() { return profissao; }
    public void setProfissao(String profissao) { this.profissao = profissao; }
    public String getNumeroApolice() { return numeroApolice; }
    public void setNumeroApolice(String numeroApolice) { this.numeroApolice = numeroApolice; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
