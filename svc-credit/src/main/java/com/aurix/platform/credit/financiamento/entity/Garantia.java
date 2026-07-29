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
@Table(name = "garantias")
public class Garantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long contratoId;

    @NotNull
    @Column(nullable = false)
    private Long bemId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoGarantia tipo;

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataRegistro;

    private LocalDate dataBaixa;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusGarantia status;

    @NotNull
    @Column(nullable = false, length = 50)
    private String orgaoRegistro;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public Garantia() {}

    public Garantia(Long contratoId, Long bemId, TipoGarantia tipo, BigDecimal valor, LocalDate dataRegistro, LocalDate dataBaixa, StatusGarantia status, String orgaoRegistro) {
        this.contratoId = contratoId;
        this.bemId = bemId;
        this.tipo = tipo;
        this.valor = valor;
        this.dataRegistro = dataRegistro;
        this.dataBaixa = dataBaixa;
        this.status = status;
        this.orgaoRegistro = orgaoRegistro;
    }

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public Long getBemId() { return bemId; }
    public void setBemId(Long bemId) { this.bemId = bemId; }
    public TipoGarantia getTipo() { return tipo; }
    public void setTipo(TipoGarantia tipo) { this.tipo = tipo; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }
    public LocalDate getDataBaixa() { return dataBaixa; }
    public void setDataBaixa(LocalDate dataBaixa) { this.dataBaixa = dataBaixa; }
    public StatusGarantia getStatus() { return status; }
    public void setStatus(StatusGarantia status) { this.status = status; }
    public String getOrgaoRegistro() { return orgaoRegistro; }
    public void setOrgaoRegistro(String orgaoRegistro) { this.orgaoRegistro = orgaoRegistro; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}
