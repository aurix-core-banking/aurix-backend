package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.Chargeback;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para Chargeback do Aurix.
 */
public class ChargebackDTO {
    private static final int MAX_ORIGEM_SIZE = 30;
    private static final int MAX_DOCUMENT_SIZE = 100;
    private static final int MAX_MOTIVO_SIZE = 500;
    private static final int MAX_JUSTIFICATIVA_SIZE = 500;
    private static final String MIN_VALUE = "0.01";

    private Long id;

    private String codigoChargeback;

    @NotNull(message = "Conta é obrigatória")
    private Long contaId;

    private String contaNumero;

    private Long transacaoOrigemId;

    @NotBlank(message = "Tipo de origem é obrigatório")
    @Size(max = MAX_ORIGEM_SIZE)
    private String tipoOrigem;

    @Size(max = MAX_DOCUMENT_SIZE)
    private String documentoOrigem;

    @NotNull(message = "Valor original é obrigatório")
    @DecimalMin(value = MIN_VALUE, message = "Valor original deve ser maior que zero")
    private BigDecimal valorOriginal;

    @NotNull(message = "Valor do chargeback é obrigatório")
    @DecimalMin(value = MIN_VALUE, message = "Valor do chargeback deve ser maior que zero")
    private BigDecimal valorChargeback;

    @NotNull(message = "Motivo é obrigatório")
    private Chargeback.MotivoChargeback motivo;

    @Size(max = MAX_MOTIVO_SIZE)
    private String descricaoMotivo;

    private Chargeback.StatusChargeback status;

    private LocalDateTime dataTransacaoOrigem;

    private LocalDateTime dataSolicitacao;

    private LocalDateTime dataAnalise;

    private LocalDateTime dataContestacao;

    private LocalDateTime dataResolucao;

    private Chargeback.ResultadoChargeback resultado;

    @Size(max = MAX_JUSTIFICATIVA_SIZE)
    private String justificativaResolucao;

    private LocalDateTime prazoLimite;

    private String dadosAdicionais;

    private String dataCriacao;

    private String dataAtualizacao;

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoChargeback() {
        return this.codigoChargeback;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaNumero() {
        return this.contaNumero;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTransacaoOrigemId() {
        return this.transacaoOrigemId;
    }

    @java.lang.SuppressWarnings("all")
    public String getTipoOrigem() {
        return this.tipoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public String getDocumentoOrigem() {
        return this.documentoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorOriginal() {
        return this.valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorChargeback() {
        return this.valorChargeback;
    }

    @java.lang.SuppressWarnings("all")
    public Chargeback.MotivoChargeback getMotivo() {
        return this.motivo;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricaoMotivo() {
        return this.descricaoMotivo;
    }

    @java.lang.SuppressWarnings("all")
    public Chargeback.StatusChargeback getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataTransacaoOrigem() {
        return this.dataTransacaoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAnalise() {
        return this.dataAnalise;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataContestacao() {
        return this.dataContestacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataResolucao() {
        return this.dataResolucao;
    }

    @java.lang.SuppressWarnings("all")
    public Chargeback.ResultadoChargeback getResultado() {
        return this.resultado;
    }

    @java.lang.SuppressWarnings("all")
    public String getJustificativaResolucao() {
        return this.justificativaResolucao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getPrazoLimite() {
        return this.prazoLimite;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
    }

    @java.lang.SuppressWarnings("all")
    public String getDataCriacao() {
        return this.dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoChargeback(final String codigoChargeback) {
        this.codigoChargeback = codigoChargeback;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaNumero(final String contaNumero) {
        this.contaNumero = contaNumero;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacaoOrigemId(final Long transacaoOrigemId) {
        this.transacaoOrigemId = transacaoOrigemId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoOrigem(final String tipoOrigem) {
        this.tipoOrigem = tipoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setDocumentoOrigem(final String documentoOrigem) {
        this.documentoOrigem = documentoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorOriginal(final BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorChargeback(final BigDecimal valorChargeback) {
        this.valorChargeback = valorChargeback;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivo(final Chargeback.MotivoChargeback motivo) {
        this.motivo = motivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricaoMotivo(final String descricaoMotivo) {
        this.descricaoMotivo = descricaoMotivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final Chargeback.StatusChargeback status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataTransacaoOrigem(final LocalDateTime dataTransacaoOrigem) {
        this.dataTransacaoOrigem = dataTransacaoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataSolicitacao(final LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAnalise(final LocalDateTime dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataContestacao(final LocalDateTime dataContestacao) {
        this.dataContestacao = dataContestacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataResolucao(final LocalDateTime dataResolucao) {
        this.dataResolucao = dataResolucao;
    }

    @java.lang.SuppressWarnings("all")
    public void setResultado(final Chargeback.ResultadoChargeback resultado) {
        this.resultado = resultado;
    }

    @java.lang.SuppressWarnings("all")
    public void setJustificativaResolucao(final String justificativaResolucao) {
        this.justificativaResolucao = justificativaResolucao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrazoLimite(final LocalDateTime prazoLimite) {
        this.prazoLimite = prazoLimite;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
