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
@Table(name = "remessas_cambio")
public class Remessa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long contratoId;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false, precision = 18, scale = 6)
    private BigDecimal valor;

    @Column(nullable = false, length = 10)
    private String moeda;

    @Column(nullable = false, length = 100)
    private String bancoDestino;

    @Column(nullable = false, length = 50)
    private String contaDestino;

    @Column(nullable = false, length = 20)
    private String codigoSwift;

    @Column(nullable = false, length = 100)
    private String finalidade;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false)
    private LocalDateTime dataSolicitacao;

    @Column
    private LocalDateTime dataConfirmacao;

    @Column(nullable = false, length = 50)
    private String tenantId;

    public Remessa() {}

    public Remessa(Long contratoId, Long clienteId, BigDecimal valor, String moeda, String bancoDestino, String contaDestino, String codigoSwift, String finalidade, String status, LocalDateTime dataSolicitacao, LocalDateTime dataConfirmacao, String tenantId) {
        this.contratoId = contratoId;
        this.clienteId = clienteId;
        this.valor = valor;
        this.moeda = moeda;
        this.bancoDestino = bancoDestino;
        this.contaDestino = contaDestino;
        this.codigoSwift = codigoSwift;
        this.finalidade = finalidade;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
        this.dataConfirmacao = dataConfirmacao;
        this.tenantId = tenantId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getMoeda() { return moeda; }
    public void setMoeda(String moeda) { this.moeda = moeda; }
    public String getBancoDestino() { return bancoDestino; }
    public void setBancoDestino(String bancoDestino) { this.bancoDestino = bancoDestino; }
    public String getContaDestino() { return contaDestino; }
    public void setContaDestino(String contaDestino) { this.contaDestino = contaDestino; }
    public String getCodigoSwift() { return codigoSwift; }
    public void setCodigoSwift(String codigoSwift) { this.codigoSwift = codigoSwift; }
    public String getFinalidade() { return finalidade; }
    public void setFinalidade(String finalidade) { this.finalidade = finalidade; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime dataSolicitacao) { this.dataSolicitacao = dataSolicitacao; }
    public LocalDateTime getDataConfirmacao() { return dataConfirmacao; }
    public void setDataConfirmacao(LocalDateTime dataConfirmacao) { this.dataConfirmacao = dataConfirmacao; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
}
