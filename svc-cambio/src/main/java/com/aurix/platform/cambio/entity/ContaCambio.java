package com.aurix.platform.cambio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "contas_cambio")
public class ContaCambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @Column(columnDefinition = "TEXT")
    private String saldoPorMoeda;

    @Column(nullable = false)
    private LocalDateTime dataAtualizacao;

    @Column(nullable = false, length = 50)
    private String tenantId;

    public ContaCambio() {}

    public ContaCambio(Long clienteId, String saldoPorMoeda, LocalDateTime dataAtualizacao, String tenantId) {
        this.clienteId = clienteId;
        this.saldoPorMoeda = saldoPorMoeda;
        this.dataAtualizacao = dataAtualizacao;
        this.tenantId = tenantId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getSaldoPorMoeda() { return saldoPorMoeda; }
    public void setSaldoPorMoeda(String saldoPorMoeda) { this.saldoPorMoeda = saldoPorMoeda; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
}
