package com.aurix.platform.credit.renegociacao.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CriarRenegociacaoRequest {

    @NotNull(message = "ID do contrato original é obrigatório")
    private Long contratoOriginalId;

    @NotNull(message = "Novo prazo é obrigatório")
    @Min(value = 1, message = "Novo prazo deve ser pelo menos 1 mês")
    private int novoPrazo;

    @DecimalMin(value = "0.0", message = "Nova taxa deve ser positiva")
    private BigDecimal novaTaxa;

    private String sistemaAmortizacao;

    private String observacoes;

    public CriarRenegociacaoRequest() {}

    public CriarRenegociacaoRequest(Long contratoOriginalId, int novoPrazo, BigDecimal novaTaxa,
                                    String sistemaAmortizacao, String observacoes) {
        this.contratoOriginalId = contratoOriginalId;
        this.novoPrazo = novoPrazo;
        this.novaTaxa = novaTaxa;
        this.sistemaAmortizacao = sistemaAmortizacao;
        this.observacoes = observacoes;
    }

    public Long getContratoOriginalId() { return contratoOriginalId; }
    public void setContratoOriginalId(Long contratoOriginalId) { this.contratoOriginalId = contratoOriginalId; }
    public int getNovoPrazo() { return novoPrazo; }
    public void setNovoPrazo(int novoPrazo) { this.novoPrazo = novoPrazo; }
    public BigDecimal getNovaTaxa() { return novaTaxa; }
    public void setNovaTaxa(BigDecimal novaTaxa) { this.novaTaxa = novaTaxa; }
    public String getSistemaAmortizacao() { return sistemaAmortizacao; }
    public void setSistemaAmortizacao(String sistemaAmortizacao) { this.sistemaAmortizacao = sistemaAmortizacao; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}
