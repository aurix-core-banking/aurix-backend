package com.aurix.platform.cambio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "clientes_cambio")
public class ClienteCambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @Column(columnDefinition = "TEXT")
    private String documentacao;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal limiteRemessaMensal;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal limiteRemessaAnual;

    @Column(columnDefinition = "TEXT")
    private String categoriasAutorizadas;

    @Column(nullable = false, length = 50)
    private String tenantId;

    public ClienteCambio() {}

    public ClienteCambio(Long clienteId, String documentacao, BigDecimal limiteRemessaMensal, BigDecimal limiteRemessaAnual, String categoriasAutorizadas, String tenantId) {
        this.clienteId = clienteId;
        this.documentacao = documentacao;
        this.limiteRemessaMensal = limiteRemessaMensal;
        this.limiteRemessaAnual = limiteRemessaAnual;
        this.categoriasAutorizadas = categoriasAutorizadas;
        this.tenantId = tenantId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getDocumentacao() { return documentacao; }
    public void setDocumentacao(String documentacao) { this.documentacao = documentacao; }
    public BigDecimal getLimiteRemessaMensal() { return limiteRemessaMensal; }
    public void setLimiteRemessaMensal(BigDecimal limiteRemessaMensal) { this.limiteRemessaMensal = limiteRemessaMensal; }
    public BigDecimal getLimiteRemessaAnual() { return limiteRemessaAnual; }
    public void setLimiteRemessaAnual(BigDecimal limiteRemessaAnual) { this.limiteRemessaAnual = limiteRemessaAnual; }
    public String getCategoriasAutorizadas() { return categoriasAutorizadas; }
    public void setCategoriasAutorizadas(String categoriasAutorizadas) { this.categoriasAutorizadas = categoriasAutorizadas; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
}
