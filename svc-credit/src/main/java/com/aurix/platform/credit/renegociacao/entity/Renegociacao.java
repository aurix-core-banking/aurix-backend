package com.aurix.platform.credit.renegociacao.entity;

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
import java.time.LocalDateTime;

@Entity
@Table(name = "renegociacoes")
public class Renegociacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String tenantId;

    @NotNull
    @Column(nullable = false)
    private Long contratoOriginalId;

    @NotNull
    @Column(nullable = false)
    private Long clienteId;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, name = "saldo_devedor_anterior", precision = 18, scale = 2)
    private BigDecimal saldoDevedorAnterior;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, name = "saldo_devedor_renegociado", precision = 18, scale = 2)
    private BigDecimal saldoDevedorRenegociado;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, name = "taxa_juros_anterior", precision = 7, scale = 5)
    private BigDecimal taxaJurosAnterior;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, name = "taxa_juros_renegociada", precision = 7, scale = 5)
    private BigDecimal taxaJurosRenegociada;

    @Min(1)
    @Column(nullable = false, name = "prazo_anterior")
    private int prazoAnterior;

    @Min(1)
    @Column(nullable = false, name = "prazo_renegociado")
    private int prazoRenegociado;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, name = "valor_parcela_anterior", precision = 18, scale = 2)
    private BigDecimal valorParcelaAnterior;

    @NotNull
    @DecimalMin("0.01")
    @Column(nullable = false, name = "valor_parcela_renegociada", precision = 18, scale = 2)
    private BigDecimal valorParcelaRenegociada;

    @NotBlank
    @Column(nullable = false, name = "sistema_amortizacao", length = 10)
    private String sistemaAmortizacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusRenegociacao status;

    @Column(nullable = false, name = "data_solicitacao")
    private LocalDateTime dataSolicitacao;

    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;

    @Column(name = "data_contratacao")
    private LocalDateTime dataContratacao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    public Renegociacao() {}

    public Renegociacao(String tenantId, Long contratoOriginalId, Long clienteId,
                        BigDecimal saldoDevedorAnterior, BigDecimal saldoDevedorRenegociado,
                        BigDecimal taxaJurosAnterior, BigDecimal taxaJurosRenegociada,
                        int prazoAnterior, int prazoRenegociado,
                        BigDecimal valorParcelaAnterior, BigDecimal valorParcelaRenegociada,
                        String sistemaAmortizacao, StatusRenegociacao status) {
        this.tenantId = tenantId;
        this.contratoOriginalId = contratoOriginalId;
        this.clienteId = clienteId;
        this.saldoDevedorAnterior = saldoDevedorAnterior;
        this.saldoDevedorRenegociado = saldoDevedorRenegociado;
        this.taxaJurosAnterior = taxaJurosAnterior;
        this.taxaJurosRenegociada = taxaJurosRenegociada;
        this.prazoAnterior = prazoAnterior;
        this.prazoRenegociado = prazoRenegociado;
        this.valorParcelaAnterior = valorParcelaAnterior;
        this.valorParcelaRenegociada = valorParcelaRenegociada;
        this.sistemaAmortizacao = sistemaAmortizacao;
        this.status = status;
        this.dataSolicitacao = LocalDateTime.now();
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
    public Long getContratoOriginalId() { return contratoOriginalId; }
    public void setContratoOriginalId(Long contratoOriginalId) { this.contratoOriginalId = contratoOriginalId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public BigDecimal getSaldoDevedorAnterior() { return saldoDevedorAnterior; }
    public void setSaldoDevedorAnterior(BigDecimal saldoDevedorAnterior) { this.saldoDevedorAnterior = saldoDevedorAnterior; }
    public BigDecimal getSaldoDevedorRenegociado() { return saldoDevedorRenegociado; }
    public void setSaldoDevedorRenegociado(BigDecimal saldoDevedorRenegociado) { this.saldoDevedorRenegociado = saldoDevedorRenegociado; }
    public BigDecimal getTaxaJurosAnterior() { return taxaJurosAnterior; }
    public void setTaxaJurosAnterior(BigDecimal taxaJurosAnterior) { this.taxaJurosAnterior = taxaJurosAnterior; }
    public BigDecimal getTaxaJurosRenegociada() { return taxaJurosRenegociada; }
    public void setTaxaJurosRenegociada(BigDecimal taxaJurosRenegociada) { this.taxaJurosRenegociada = taxaJurosRenegociada; }
    public int getPrazoAnterior() { return prazoAnterior; }
    public void setPrazoAnterior(int prazoAnterior) { this.prazoAnterior = prazoAnterior; }
    public int getPrazoRenegociado() { return prazoRenegociado; }
    public void setPrazoRenegociado(int prazoRenegociado) { this.prazoRenegociado = prazoRenegociado; }
    public BigDecimal getValorParcelaAnterior() { return valorParcelaAnterior; }
    public void setValorParcelaAnterior(BigDecimal valorParcelaAnterior) { this.valorParcelaAnterior = valorParcelaAnterior; }
    public BigDecimal getValorParcelaRenegociada() { return valorParcelaRenegociada; }
    public void setValorParcelaRenegociada(BigDecimal valorParcelaRenegociada) { this.valorParcelaRenegociada = valorParcelaRenegociada; }
    public String getSistemaAmortizacao() { return sistemaAmortizacao; }
    public void setSistemaAmortizacao(String sistemaAmortizacao) { this.sistemaAmortizacao = sistemaAmortizacao; }
    public StatusRenegociacao getStatus() { return status; }
    public void setStatus(StatusRenegociacao status) { this.status = status; }
    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
    public LocalDateTime getDataAprovacao() { return dataAprovacao; }
    public void setDataAprovacao(LocalDateTime dataAprovacao) { this.dataAprovacao = dataAprovacao; }
    public LocalDateTime getDataContratacao() { return dataContratacao; }
    public void setDataContratacao(LocalDateTime dataContratacao) { this.dataContratacao = dataContratacao; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
}
