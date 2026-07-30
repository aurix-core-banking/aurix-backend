package com.aurix.platform.credit.consignado.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "consignado_sources")
public class ConsignadoSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false, length = 255)
    private String endpoint;

    @Column(nullable = false, length = 255)
    private String credenciais;

    @Column(columnDefinition = "TEXT")
    private String config;

    @Column(nullable = false, length = 50)
    private String tenantId;

    public ConsignadoSource() {}

    public ConsignadoSource(String tipo, String endpoint, String credenciais, String config, String tenantId) {
        this.tipo = tipo;
        this.endpoint = endpoint;
        this.credenciais = credenciais;
        this.config = config;
        this.tenantId = tenantId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getEndpoint() { return endpoint; }
    public void setEndpoint(String endpoint) { this.endpoint = endpoint; }
    public String getCredenciais() { return credenciais; }
    public void setCredenciais(String credenciais) { this.credenciais = credenciais; }
    public String getConfig() { return config; }
    public void setConfig(String config) { this.config = config; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
}
