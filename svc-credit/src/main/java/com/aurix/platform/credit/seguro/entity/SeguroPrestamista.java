package com.aurix.platform.credit.seguro.entity;

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
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "seguros_prestamista")
public class SeguroPrestamista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String tenantId;

    @NotNull
    @Column(nullable = false)
    private Long contratoId;

    @NotNull
    @Column(nullable = false)
    private Long clienteId;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, name = "saldo_devedor_inicial", precision = 18, scale = 2)
    private BigDecimal saldoDevedorInicial;

    @NotNull
    @DecimalMin("0.0001")
    @Column(nullable = false, name = "taxa_mensal", precision = 7, scale = 4)
    private BigDecimal taxaMensal;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, name = "valor_premio_mensal", precision = 18, scale = 2)
    private BigDecimal valorPremioMensal;

    @Min(1)
    @Column(nullable = false, name = "carencia_dias")
    private int carenciaDias;

    @NotNull
    @Column(nullable = false, name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "data_cancelamento")
    private LocalDate dataCancelamento;

    @Column(name = "motivo_cancelamento", columnDefinition = "TEXT")
    private String motivoCancelamento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusSeguro status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    public SeguroPrestamista() {}

    public SeguroPrestamista(String tenantId, Long contratoId, Long clienteId,
                             BigDecimal saldoDevedorInicial, BigDecimal taxaMensal,
                             BigDecimal valorPremioMensal, int carenciaDias,
                             LocalDate dataInicio, StatusSeguro status) {
        this.tenantId = tenantId;
        this.contratoId = contratoId;
        this.clienteId = clienteId;
        this.saldoDevedorInicial = saldoDevedorInicial;
        this.taxaMensal = taxaMensal;
        this.valorPremioMensal = valorPremioMensal;
        this.carenciaDias = carenciaDias;
        this.dataInicio = dataInicio;
        this.status = status;
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
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public BigDecimal getSaldoDevedorInicial() { return saldoDevedorInicial; }
    public void setSaldoDevedorInicial(BigDecimal saldoDevedorInicial) { this.saldoDevedorInicial = saldoDevedorInicial; }
    public BigDecimal getTaxaMensal() { return taxaMensal; }
    public void setTaxaMensal(BigDecimal taxaMensal) { this.taxaMensal = taxaMensal; }
    public BigDecimal getValorPremioMensal() { return valorPremioMensal; }
    public void setValorPremioMensal(BigDecimal valorPremioMensal) { this.valorPremioMensal = valorPremioMensal; }
    public int getCarenciaDias() { return carenciaDias; }
    public void setCarenciaDias(int carenciaDias) { this.carenciaDias = carenciaDias; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public LocalDate getDataCancelamento() { return dataCancelamento; }
    public void setDataCancelamento(LocalDate dataCancelamento) { this.dataCancelamento = dataCancelamento; }
    public String getMotivoCancelamento() { return motivoCancelamento; }
    public void setMotivoCancelamento(String motivoCancelamento) { this.motivoCancelamento = motivoCancelamento; }
    public StatusSeguro getStatus() { return status; }
    public void setStatus(StatusSeguro status) { this.status = status; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
