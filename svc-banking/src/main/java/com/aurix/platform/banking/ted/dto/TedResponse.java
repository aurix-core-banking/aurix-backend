package com.aurix.platform.banking.ted.dto;

import com.aurix.platform.banking.ted.entity.TransferenciaTed.StatusTed;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TedResponse {
    private Long id;
    private Long contaOrigemId;
    private String contaOrigemNumero;
    private String ispbDestino;
    private String contaDestinoAgencia;
    private String contaDestinoConta;
    private String contaDestinoNome;
    private BigDecimal valor;
    private String descricao;
    private StatusTed status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataProcessamento;
    private LocalDateTime dataConfirmacao;
    private String spiProtocolo;
    private String motivoFalha;

    public TedResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContaOrigemId() { return contaOrigemId; }
    public void setContaOrigemId(Long contaOrigemId) { this.contaOrigemId = contaOrigemId; }
    public String getContaOrigemNumero() { return contaOrigemNumero; }
    public void setContaOrigemNumero(String contaOrigemNumero) { this.contaOrigemNumero = contaOrigemNumero; }
    public String getIspbDestino() { return ispbDestino; }
    public void setIspbDestino(String ispbDestino) { this.ispbDestino = ispbDestino; }
    public String getContaDestinoAgencia() { return contaDestinoAgencia; }
    public void setContaDestinoAgencia(String contaDestinoAgencia) { this.contaDestinoAgencia = contaDestinoAgencia; }
    public String getContaDestinoConta() { return contaDestinoConta; }
    public void setContaDestinoConta(String contaDestinoConta) { this.contaDestinoConta = contaDestinoConta; }
    public String getContaDestinoNome() { return contaDestinoNome; }
    public void setContaDestinoNome(String contaDestinoNome) { this.contaDestinoNome = contaDestinoNome; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public StatusTed getStatus() { return status; }
    public void setStatus(StatusTed status) { this.status = status; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDateTime getDataProcessamento() { return dataProcessamento; }
    public void setDataProcessamento(LocalDateTime dataProcessamento) { this.dataProcessamento = dataProcessamento; }
    public LocalDateTime getDataConfirmacao() { return dataConfirmacao; }
    public void setDataConfirmacao(LocalDateTime dataConfirmacao) { this.dataConfirmacao = dataConfirmacao; }
    public String getSpiProtocolo() { return spiProtocolo; }
    public void setSpiProtocolo(String spiProtocolo) { this.spiProtocolo = spiProtocolo; }
    public String getMotivoFalha() { return motivoFalha; }
    public void setMotivoFalha(String motivoFalha) { this.motivoFalha = motivoFalha; }
}
