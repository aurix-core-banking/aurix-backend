package com.aurix.platform.cambio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CotacaoResponse {

    private Long id;
    private String moeda;
    private BigDecimal taxaCompra;
    private BigDecimal taxaVenda;
    private LocalDateTime dataCotacao;
    private String fonte;

    public CotacaoResponse() {}

    public CotacaoResponse(Long id, String moeda, BigDecimal taxaCompra, BigDecimal taxaVenda, LocalDateTime dataCotacao, String fonte) {
        this.id = id;
        this.moeda = moeda;
        this.taxaCompra = taxaCompra;
        this.taxaVenda = taxaVenda;
        this.dataCotacao = dataCotacao;
        this.fonte = fonte;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getMoeda() { return moeda; }
    public void setMoeda(String moeda) { this.moeda = moeda; }
    public BigDecimal getTaxaCompra() { return taxaCompra; }
    public void setTaxaCompra(BigDecimal taxaCompra) { this.taxaCompra = taxaCompra; }
    public BigDecimal getTaxaVenda() { return taxaVenda; }
    public void setTaxaVenda(BigDecimal taxaVenda) { this.taxaVenda = taxaVenda; }
    public LocalDateTime getDataCotacao() { return dataCotacao; }
    public void setDataCotacao(LocalDateTime dataCotacao) { this.dataCotacao = dataCotacao; }
    public String getFonte() { return fonte; }
    public void setFonte(String fonte) { this.fonte = fonte; }
}
