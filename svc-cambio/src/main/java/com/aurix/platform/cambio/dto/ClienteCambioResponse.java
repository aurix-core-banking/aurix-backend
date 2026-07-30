package com.aurix.platform.cambio.dto;

import java.math.BigDecimal;

public class ClienteCambioResponse {

    private Long id;
    private Long clienteId;
    private BigDecimal limiteRemessaMensal;
    private BigDecimal limiteRemessaAnual;
    private String categoriasAutorizadas;
    private String documentacao;

    public ClienteCambioResponse() {}

    public ClienteCambioResponse(Long id, Long clienteId, BigDecimal limiteRemessaMensal, BigDecimal limiteRemessaAnual, String categoriasAutorizadas, String documentacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.limiteRemessaMensal = limiteRemessaMensal;
        this.limiteRemessaAnual = limiteRemessaAnual;
        this.categoriasAutorizadas = categoriasAutorizadas;
        this.documentacao = documentacao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public BigDecimal getLimiteRemessaMensal() { return limiteRemessaMensal; }
    public void setLimiteRemessaMensal(BigDecimal limiteRemessaMensal) { this.limiteRemessaMensal = limiteRemessaMensal; }
    public BigDecimal getLimiteRemessaAnual() { return limiteRemessaAnual; }
    public void setLimiteRemessaAnual(BigDecimal limiteRemessaAnual) { this.limiteRemessaAnual = limiteRemessaAnual; }
    public String getCategoriasAutorizadas() { return categoriasAutorizadas; }
    public void setCategoriasAutorizadas(String categoriasAutorizadas) { this.categoriasAutorizadas = categoriasAutorizadas; }
    public String getDocumentacao() { return documentacao; }
    public void setDocumentacao(String documentacao) { this.documentacao = documentacao; }
}
