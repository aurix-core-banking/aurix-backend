package com.aurix.platform.salary.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PortabilidadeRequest {

    @NotNull(message = "ID da conta de destino e obrigatorio")
    private Long contaDestinoId;

    @NotNull(message = "Valor da portabilidade e obrigatorio")
    private BigDecimal valorPortabilidade;

    private String bancoDestino;
    private String agenciaDestino;
    private String contaDestinoNumero;

    public Long getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public BigDecimal getValorPortabilidade() {
        return valorPortabilidade;
    }

    public void setValorPortabilidade(BigDecimal valorPortabilidade) {
        this.valorPortabilidade = valorPortabilidade;
    }

    public String getBancoDestino() {
        return bancoDestino;
    }

    public void setBancoDestino(String bancoDestino) {
        this.bancoDestino = bancoDestino;
    }

    public String getAgenciaDestino() {
        return agenciaDestino;
    }

    public void setAgenciaDestino(String agenciaDestino) {
        this.agenciaDestino = agenciaDestino;
    }

    public String getContaDestinoNumero() {
        return contaDestinoNumero;
    }

    public void setContaDestinoNumero(String contaDestinoNumero) {
        this.contaDestinoNumero = contaDestinoNumero;
    }
}
