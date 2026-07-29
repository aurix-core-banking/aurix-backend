package com.aurix.platform.platform.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "garantias", schema = "aurix")
public class Garantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Long contratoId;

    @NotNull
    @Column(nullable = false)
    private Long clienteId;

    @NotNull
    @Column(nullable = false)
    private Long bemId;

    @NotNull
    @Column(nullable = false, length = 30)
    private String tipo;

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataRegistro;

    @NotNull
    @Column(nullable = false)
    private LocalDate dataVencimento;

    @NotNull
    @Column(nullable = false, length = 20)
    private String status;

    private LocalDate dataBaixa;

    @Column(length = 50)
    private String tenantId;

    public Garantia() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long v) { this.contratoId = v; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long v) { this.clienteId = v; }
    public Long getBemId() { return bemId; }
    public void setBemId(Long v) { this.bemId = v; }
    public String getTipo() { return tipo; }
    public void setTipo(String v) { this.tipo = v; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal v) { this.valor = v; }
    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate v) { this.dataRegistro = v; }
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate v) { this.dataVencimento = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public LocalDate getDataBaixa() { return dataBaixa; }
    public void setDataBaixa(LocalDate v) { this.dataBaixa = v; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String v) { this.tenantId = v; }
}
