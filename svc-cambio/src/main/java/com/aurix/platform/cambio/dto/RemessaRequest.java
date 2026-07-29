package com.aurix.platform.cambio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RemessaRequest {

    @NotNull
    private Long contratoId;

    @NotNull
    private Long clienteId;

    @NotNull
    private BigDecimal valor;

    @NotBlank
    private String moeda;

    @NotBlank
    private String bancoDestino;

    @NotBlank
    private String contaDestino;

    @NotBlank
    private String codigoSwift;

    @NotBlank
    private String finalidade;

    public RemessaRequest() {}

    public RemessaRequest(Long contratoId, Long clienteId, BigDecimal valor, String moeda, String bancoDestino, String contaDestino, String codigoSwift, String finalidade) {
        this.contratoId = contratoId;
        this.clienteId = clienteId;
        this.valor = valor;
        this.moeda = moeda;
        this.bancoDestino = bancoDestino;
        this.contaDestino = contaDestino;
        this.codigoSwift = codigoSwift;
        this.finalidade = finalidade;
    }

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
}
