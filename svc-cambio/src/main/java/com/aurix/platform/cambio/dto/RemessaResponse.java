package com.aurix.platform.cambio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RemessaResponse {

    private Long id;
    private Long contratoId;
    private Long clienteId;
    private BigDecimal valor;
    private String moeda;
    private String bancoDestino;
    private String contaDestino;
    private String codigoSwift;
    private String finalidade;
    private String status;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataConfirmacao;

    public RemessaResponse() {}

    public RemessaResponse(Long id, Long contratoId, Long clienteId, BigDecimal valor, String moeda, String bancoDestino, String contaDestino, String codigoSwift, String finalidade, String status, LocalDateTime dataSolicitacao, LocalDateTime dataConfirmacao) {
        this.id = id;
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
}
