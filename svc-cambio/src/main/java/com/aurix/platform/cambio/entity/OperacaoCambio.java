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
@Table(name = "operacoes_cambio")
public class OperacaoCambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long contratoId;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal valorMoedaEstrangeira;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal valorMoedaNacional;

    @Column(nullable = false, precision = 18, scale = 8)
    private BigDecimal taxa;

    @Column(nullable = false)
    private LocalDateTime dataOperacao;

    @Column(length = 50)
    private String registroBACEN;

    @Column(nullable = false, length = 50)
    private String tenantId;

    public OperacaoCambio() {}

    public OperacaoCambio(Long contratoId, Long clienteId, String tipo, BigDecimal valorMoedaEstrangeira, BigDecimal valorMoedaNacional, BigDecimal taxa, LocalDateTime dataOperacao, String registroBACEN, String tenantId) {
        this.contratoId = contratoId;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.valorMoedaEstrangeira = valorMoedaEstrangeira;
        this.valorMoedaNacional = valorMoedaNacional;
        this.taxa = taxa;
        this.dataOperacao = dataOperacao;
        this.registroBACEN = registroBACEN;
        this.tenantId = tenantId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getValorMoedaEstrangeira() { return valorMoedaEstrangeira; }
    public void setValorMoedaEstrangeira(BigDecimal valorMoedaEstrangeira) { this.valorMoedaEstrangeira = valorMoedaEstrangeira; }
    public BigDecimal getValorMoedaNacional() { return valorMoedaNacional; }
    public void setValorMoedaNacional(BigDecimal valorMoedaNacional) { this.valorMoedaNacional = valorMoedaNacional; }
    public BigDecimal getTaxa() { return taxa; }
    public void setTaxa(BigDecimal taxa) { this.taxa = taxa; }
    public LocalDateTime getDataOperacao() { return dataOperacao; }
    public void setDataOperacao(LocalDateTime dataOperacao) { this.dataOperacao = dataOperacao; }
    public String getRegistroBACEN() { return registroBACEN; }
    public void setRegistroBACEN(String registroBACEN) { this.registroBACEN = registroBACEN; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
}
