package com.aurix.platform.banking.ted.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TedRequest {

    @NotNull(message = "ID da conta de origem e obrigatorio")
    private Long contaOrigemId;

    @NotBlank(message = "ISPB destino e obrigatorio")
    private String ispbDestino;

    @NotBlank(message = "Agencia destino e obrigatoria")
    private String agenciaDestino;

    @NotBlank(message = "Conta destino e obrigatoria")
    private String contaDestino;

    private String nomeDestinatario;
    private String documentoDestinatario;

    @NotNull(message = "Valor e obrigatorio")
    @DecimalMin(value = "0.01", message = "Valor minimo e R$ 0,01")
    private BigDecimal valor;

    private String descricao;

    private String codigoBancoDestino;

    public Long getContaOrigemId() { return contaOrigemId; }
    public void setContaOrigemId(Long contaOrigemId) { this.contaOrigemId = contaOrigemId; }
    public String getIspbDestino() { return ispbDestino; }
    public void setIspbDestino(String ispbDestino) { this.ispbDestino = ispbDestino; }
    public String getAgenciaDestino() { return agenciaDestino; }
    public void setAgenciaDestino(String agenciaDestino) { this.agenciaDestino = agenciaDestino; }
    public String getContaDestino() { return contaDestino; }
    public void setContaDestino(String contaDestino) { this.contaDestino = contaDestino; }
    public String getNomeDestinatario() { return nomeDestinatario; }
    public void setNomeDestinatario(String nomeDestinatario) { this.nomeDestinatario = nomeDestinatario; }
    public String getDocumentoDestinatario() { return documentoDestinatario; }
    public void setDocumentoDestinatario(String documentoDestinatario) { this.documentoDestinatario = documentoDestinatario; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getCodigoBancoDestino() { return codigoBancoDestino; }
    public void setCodigoBancoDestino(String codigoBancoDestino) { this.codigoBancoDestino = codigoBancoDestino; }
}
