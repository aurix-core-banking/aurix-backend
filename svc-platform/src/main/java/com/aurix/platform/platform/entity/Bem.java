package com.aurix.platform.platform.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "bens", schema = "aurix")
public class Bem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // IMOVEL/VEICULO/EQUIPAMENTO/TITULO
    private String descricao;
    private BigDecimal valorAvaliacao;
    private String registroCartorio;
    private String chassi;
    private String placa;
    private String tenantId;

    public Bem() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String v) { this.tipo = v; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String v) { this.descricao = v; }
    public BigDecimal getValorAvaliacao() { return valorAvaliacao; }
    public void setValorAvaliacao(BigDecimal v) { this.valorAvaliacao = v; }
    public String getRegistroCartorio() { return registroCartorio; }
    public void setRegistroCartorio(String v) { this.registroCartorio = v; }
    public String getChassi() { return chassi; }
    public void setChassi(String v) { this.chassi = v; }
    public String getPlaca() { return placa; }
    public void setPlaca(String v) { this.placa = v; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String v) { this.tenantId = v; }
}
