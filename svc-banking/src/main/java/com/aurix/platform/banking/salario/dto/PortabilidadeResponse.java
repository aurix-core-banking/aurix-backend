package com.aurix.platform.banking.salario.dto;

import com.aurix.platform.banking.salario.entity.SolicitacaoPortabilidade.StatusPortabilidade;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PortabilidadeResponse {
    private Long id;
    private Long contaSalarioId;
    private String codigoBancoDestino;
    private String agenciaDestino;
    private String contaDestino;
    private BigDecimal valorPercentual;
    private StatusPortabilidade status;
    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataCriacao;

    public PortabilidadeResponse() {}
    public Long getId() { return id; }
    public void setId(Long v) { this.id = v; }
    public Long getContaSalarioId() { return contaSalarioId; }
    public void setContaSalarioId(Long v) { this.contaSalarioId = v; }
    public String getCodigoBancoDestino() { return codigoBancoDestino; }
    public void setCodigoBancoDestino(String v) { this.codigoBancoDestino = v; }
    public String getAgenciaDestino() { return agenciaDestino; }
    public void setAgenciaDestino(String v) { this.agenciaDestino = v; }
    public String getContaDestino() { return contaDestino; }
    public void setContaDestino(String v) { this.contaDestino = v; }
    public BigDecimal getValorPercentual() { return valorPercentual; }
    public void setValorPercentual(BigDecimal v) { this.valorPercentual = v; }
    public StatusPortabilidade getStatus() { return status; }
    public void setStatus(StatusPortabilidade v) { this.status = v; }
    public LocalDateTime getDataSolicitacao() { return dataSolicitacao; }
    public void setDataSolicitacao(LocalDateTime v) { this.dataSolicitacao = v; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime v) { this.dataCriacao = v; }
}
