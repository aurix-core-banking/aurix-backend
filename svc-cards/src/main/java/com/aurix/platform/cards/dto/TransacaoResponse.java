package com.aurix.platform.cards.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransacaoResponse {

    private Long id;
    private String codigoTransacao;
    private Long cartaoId;
    private BigDecimal valor;
    private String estabelecimento;
    private String autorizacao;
    private String modo;
    private String status;
    private LocalDateTime dataTransacao;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCodigoTransacao() { return codigoTransacao; }
    public void setCodigoTransacao(String codigoTransacao) { this.codigoTransacao = codigoTransacao; }
    public Long getCartaoId() { return cartaoId; }
    public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getEstabelecimento() { return estabelecimento; }
    public void setEstabelecimento(String estabelecimento) { this.estabelecimento = estabelecimento; }
    public String getAutorizacao() { return autorizacao; }
    public void setAutorizacao(String autorizacao) { this.autorizacao = autorizacao; }
    public String getModo() { return modo; }
    public void setModo(String modo) { this.modo = modo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDataTransacao() { return dataTransacao; }
    public void setDataTransacao(LocalDateTime dataTransacao) { this.dataTransacao = dataTransacao; }
}
