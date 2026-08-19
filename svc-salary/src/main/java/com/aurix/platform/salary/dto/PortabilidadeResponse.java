package com.aurix.platform.salary.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PortabilidadeResponse {

    private Long id;
    private Long contaSalarioId;
    private Long contaDestinoId;
    private BigDecimal valorPortabilidade;
    private String bancoDestino;
    private String status;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataAprovacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContaSalarioId() {
        return contaSalarioId;
    }

    public void setContaSalarioId(Long contaSalarioId) {
        this.contaSalarioId = contaSalarioId;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }
}
