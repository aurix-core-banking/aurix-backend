package com.aurix.platform.banking.poupanca.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes_poupanca")
public class MovimentacaoPoupanca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "conta_poupanca_id", nullable = false)
    private Long contaPoupancaId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoMovimentacao tipo;

    @NotNull
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal valor;

    @NotNull
    @Column(name = "saldo_anterior", nullable = false, precision = 18, scale = 2)
    private BigDecimal saldoAnterior;

    @NotNull
    @Column(name = "saldo_posterior", nullable = false, precision = 18, scale = 2)
    private BigDecimal saldoPosterior;

    @Column(name = "data_movimentacao", nullable = false)
    private LocalDateTime dataMovimentacao;

    @Column(length = 255)
    private String descricao;

    @Column(name = "transacao_origem_id")
    private Long transacaoOrigemId;

    @NotNull
    @Column(name = "tenant_id", nullable = false, length = 50)
    private String tenantId;

    public enum TipoMovimentacao {
        DEPOSITO, SAQUE, RENDIMENTO_TR, ESTORNO
    }

    @PrePersist
    protected void onCreate() {
        dataMovimentacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContaPoupancaId() { return contaPoupancaId; }
    public void setContaPoupancaId(Long contaPoupancaId) { this.contaPoupancaId = contaPoupancaId; }
    public TipoMovimentacao getTipo() { return tipo; }
    public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public BigDecimal getSaldoAnterior() { return saldoAnterior; }
    public void setSaldoAnterior(BigDecimal saldoAnterior) { this.saldoAnterior = saldoAnterior; }
    public BigDecimal getSaldoPosterior() { return saldoPosterior; }
    public void setSaldoPosterior(BigDecimal saldoPosterior) { this.saldoPosterior = saldoPosterior; }
    public LocalDateTime getDataMovimentacao() { return dataMovimentacao; }
    public void setDataMovimentacao(LocalDateTime dataMovimentacao) { this.dataMovimentacao = dataMovimentacao; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Long getTransacaoOrigemId() { return transacaoOrigemId; }
    public void setTransacaoOrigemId(Long transacaoOrigemId) { this.transacaoOrigemId = transacaoOrigemId; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
}
