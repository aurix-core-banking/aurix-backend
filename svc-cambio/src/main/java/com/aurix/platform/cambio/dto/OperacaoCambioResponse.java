package com.aurix.platform.cambio.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OperacaoCambioResponse {

    private Long id;
    private Long contratoId;
    private Long clienteId;
    private String tipo;
    private BigDecimal valorMoedaEstrangeira;
    private BigDecimal valorMoedaNacional;
    private BigDecimal taxa;
    private LocalDateTime dataOperacao;
    private String registroBACEN;

    public OperacaoCambioResponse() {}

    public OperacaoCambioResponse(Long id, Long contratoId, Long clienteId, String tipo, BigDecimal valorMoedaEstrangeira, BigDecimal valorMoedaNacional, BigDecimal taxa, LocalDateTime dataOperacao, String registroBACEN) {
        this.id = id;
        this.contratoId = contratoId;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.valorMoedaEstrangeira = valorMoedaEstrangeira;
        this.valorMoedaNacional = valorMoedaNacional;
        this.taxa = taxa;
        this.dataOperacao = dataOperacao;
        this.registroBACEN = registroBACEN;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getContratoId() { return contratoId; }
    public void setContratoId(Long contratoId) { this.contratoId = contratoId; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getValorMoedaEstrangeira() { return valorMoedaEstrangeira; }
    public void setValorMoedaEstrangeira(BigDecimal valorMoedaEstrangeira) { this.valorMoedaEstrangeira = valorMoedaEstrangeira; }
    public BigDecimal getValorMoedaNacional() { return valorMoedaNacional; }
    public void setValorMoedaNacional(BigDecimal valorMoedaNacional) { this.valorMoedaNacional = valorMoedaNacional; }
    public BigDecimal getTaxa() { return taxa; }
    public void setTaxa(BigDecimal taxa) { this.taxa = taxa; }
    public LocalDateTime getDataOperacao() { return dataOperacao; }
    public void setDataOperacao(LocalDateTime dataOperacao) { this.dataOperacao = dataOperacao; }
    public String getRegistroBACEN() { return registroBACEN; }
    public void setRegistroBACEN(String registroBACEN) { this.registroBACEN = registroBACEN; }
}
