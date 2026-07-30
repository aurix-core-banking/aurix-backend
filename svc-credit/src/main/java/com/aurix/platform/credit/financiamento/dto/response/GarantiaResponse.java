package com.aurix.platform.credit.financiamento.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GarantiaResponse {

    private Long id;
    private String tipo;
    private BigDecimal valor;
    private LocalDate dataRegistro;
    private LocalDate dataBaixa;
    private String status;
    private String orgaoRegistro;

    public GarantiaResponse() {}

    public GarantiaResponse(Long id, String tipo, BigDecimal valor, LocalDate dataRegistro, LocalDate dataBaixa, String status, String orgaoRegistro) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.dataRegistro = dataRegistro;
        this.dataBaixa = dataBaixa;
        this.status = status;
        this.orgaoRegistro = orgaoRegistro;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public LocalDate getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(LocalDate dataRegistro) { this.dataRegistro = dataRegistro; }
    public LocalDate getDataBaixa() { return dataBaixa; }
    public void setDataBaixa(LocalDate dataBaixa) { this.dataBaixa = dataBaixa; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getOrgaoRegistro() { return orgaoRegistro; }
    public void setOrgaoRegistro(String orgaoRegistro) { this.orgaoRegistro = orgaoRegistro; }
}
