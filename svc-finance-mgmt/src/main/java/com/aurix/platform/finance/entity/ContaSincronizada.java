package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contas_sincronizadas", schema = "aurix")
public class ContaSincronizada {

    public enum StatusSync {
        ATIVO, DESINCronizado
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "conta_id", nullable = false, unique = true, length = 50)
    private String contaId;

    @Column(name = "cliente_id", nullable = false, length = 50)
    private String clienteId;

    @Column(name = "saldo_inicial", nullable = false, precision = 18, scale = 2)
    private BigDecimal saldoInicial;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_sincronizacao", nullable = false)
    private LocalDateTime dataSincronizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private StatusSync status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getContaId() { return contaId; }
    public void setContaId(String contaId) { this.contaId = contaId; }
    public String getClienteId() { return clienteId; }
    public void setClienteId(String clienteId) { this.clienteId = clienteId; }
    public BigDecimal getSaldoInicial() { return saldoInicial; }
    public void setSaldoInicial(BigDecimal saldoInicial) { this.saldoInicial = saldoInicial; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDateTime getDataSincronizacao() { return dataSincronizacao; }
    public void setDataSincronizacao(LocalDateTime dataSincronizacao) { this.dataSincronizacao = dataSincronizacao; }
    public StatusSync getStatus() { return status; }
    public void setStatus(StatusSync status) { this.status = status; }
}
