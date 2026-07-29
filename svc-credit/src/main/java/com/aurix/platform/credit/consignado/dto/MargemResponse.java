package com.aurix.platform.credit.consignado.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MargemResponse {

    private Long clienteId;
    private String fonteMargem;
    private BigDecimal margemTotal;
    private BigDecimal margemDisponivel;
    private BigDecimal margemUtilizada;
    private LocalDateTime dataAtualizacao;

    public MargemResponse() {}

    public MargemResponse(Long clienteId, String fonteMargem, BigDecimal margemTotal, BigDecimal margemDisponivel, BigDecimal margemUtilizada, LocalDateTime dataAtualizacao) {
        this.clienteId = clienteId;
        this.fonteMargem = fonteMargem;
        this.margemTotal = margemTotal;
        this.margemDisponivel = margemDisponivel;
        this.margemUtilizada = margemUtilizada;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getFonteMargem() { return fonteMargem; }
    public void setFonteMargem(String fonteMargem) { this.fonteMargem = fonteMargem; }
    public BigDecimal getMargemTotal() { return margemTotal; }
    public void setMargemTotal(BigDecimal margemTotal) { this.margemTotal = margemTotal; }
    public BigDecimal getMargemDisponivel() { return margemDisponivel; }
    public void setMargemDisponivel(BigDecimal margemDisponivel) { this.margemDisponivel = margemDisponivel; }
    public BigDecimal getMargemUtilizada() { return margemUtilizada; }
    public void setMargemUtilizada(BigDecimal margemUtilizada) { this.margemUtilizada = margemUtilizada; }
    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
