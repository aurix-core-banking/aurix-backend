package com.aurix.platform.credit.consignado.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "convenios_consignados")
public class ConvenioConsignado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(nullable = false, length = 20)
    private String codigoFonte;

    @Column(nullable = false)
    private boolean ativo;

    @Column(nullable = false, length = 50)
    private String tenantId;

    public ConvenioConsignado() {}

    public ConvenioConsignado(String nome, String tipo, String codigoFonte, boolean ativo, String tenantId) {
        this.nome = nome;
        this.tipo = tipo;
        this.codigoFonte = codigoFonte;
        this.ativo = ativo;
        this.tenantId = tenantId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getCodigoFonte() { return codigoFonte; }
    public void setCodigoFonte(String codigoFonte) { this.codigoFonte = codigoFonte; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
}
