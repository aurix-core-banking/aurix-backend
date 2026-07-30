package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.SolicitacaoCredito;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para Solicitação de Crédito do Aurix.
 */
public class SolicitacaoCreditoDTO {
    /**
     * Valor mínimo permitido: 0.01.
     */
    private static final String MIN_VALUE = "0.01";
    /**
     * Valor mínimo da taxa: 0.0.
     */
    private static final String MIN_RATE = "0.0";
    /**
     * Tamanho máximo das observações: 1000 caracteres.
     */
    private static final int MAX_OBS_SIZE = 1000;
    /**
     * ID da solicitação.
     */
    private Long id;
    /**
     * ID do cliente solicitante.
     */
    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;
    /**
     * Nome do cliente solicitante.
     */
    private String clienteNome;
    /**
     * Tipo de pessoa (FISICA/JURIDICA).
     */
    private String clienteTipoPessoa;
    /**
     * Valor total solicitado.
     */
    @NotNull(message = "Valor solicitado é obrigatório")
    @DecimalMin(value = MIN_VALUE, message = "Valor solicitado deve ser maior que zero")
    private BigDecimal valorSolicitado;
    /**
     * Prazo para pagamento em meses.
     */
    @NotNull(message = "Prazo em meses é obrigatório")
    private Integer prazoMeses;
    /**
     * Taxa de juros proposta.
     */
    @NotNull(message = "Taxa de juros é obrigatória")
    @DecimalMin(value = MIN_RATE, message = "Taxa de juros não pode ser negativa")
    private BigDecimal taxaJuros;
    /**
     * Status atual da solicitação.
     */
    private SolicitacaoCredito.StatusSolicitacao status;
    /**
     * Score de crédito calculado para o cliente.
     */
    private Integer scoreCredito;
    /**
     * Resumo da análise de risco realizada.
     */
    private String analiseRisco;
    /**
     * Data e hora da solicitação.
     */
    private LocalDateTime dataSolicitacao;
    /**
     * Data e hora em que a análise foi iniciada.
     */
    private LocalDateTime dataAnalise;
    /**
     * Data e hora da aprovação final.
     */
    private LocalDateTime dataAprovacao;
    /**
     * Observações adicionais do analista.
     */
    @Size(max = MAX_OBS_SIZE, message = "Observações devem ter no máximo 1000 caracteres")
    private String observacoes;
    /**
     * Valor efetivamente aprovado.
     */
    private BigDecimal valorAprovado;
    /**
     * Prazo efetivamente aprovado.
     */
    private Integer prazoAprovado;
    /**
     * Taxa de juros efetivamente aprovada.
     */
    private BigDecimal taxaAprovada;
    /**
     * Dados adicionais da solicitação em formato JSON.
     */
    private String dadosAdicionais;
    /**
     * ID do produto de crédito selecionado.
     */
    private Long produtoCreditoId;
    /**
     * URL do contrato gerado, se aplicável.
     */
    private String contratoUrl;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID da solicitação.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * ID do cliente solicitante.
     */
    @java.lang.SuppressWarnings("all")
    public Long getClienteId() {
        return this.clienteId;
    }

    /**
     * Nome do cliente solicitante.
     */
    @java.lang.SuppressWarnings("all")
    public String getClienteNome() {
        return this.clienteNome;
    }

    public String getClienteTipoPessoa() {
        return this.clienteTipoPessoa;
    }

    /**
     * Valor total solicitado.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorSolicitado() {
        return this.valorSolicitado;
    }

    /**
     * Prazo para pagamento em meses.
     */
    @java.lang.SuppressWarnings("all")
    public Integer getPrazoMeses() {
        return this.prazoMeses;
    }

    /**
     * Taxa de juros proposta.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaJuros() {
        return this.taxaJuros;
    }

    /**
     * Status atual da solicitação.
     */
    @java.lang.SuppressWarnings("all")
    public SolicitacaoCredito.StatusSolicitacao getStatus() {
        return this.status;
    }

    /**
     * Score de crédito calculado para o cliente.
     */
    @java.lang.SuppressWarnings("all")
    public Integer getScoreCredito() {
        return this.scoreCredito;
    }

    /**
     * Resumo da análise de risco realizada.
     */
    @java.lang.SuppressWarnings("all")
    public String getAnaliseRisco() {
        return this.analiseRisco;
    }

    /**
     * Data e hora da solicitação.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    /**
     * Data e hora em que a análise foi iniciada.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAnalise() {
        return this.dataAnalise;
    }

    /**
     * Data e hora da aprovação final.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAprovacao() {
        return this.dataAprovacao;
    }

    /**
     * Observações adicionais do analista.
     */
    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    /**
     * Valor efetivamente aprovado.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAprovado() {
        return this.valorAprovado;
    }

    /**
     * Prazo efetivamente aprovado.
     */
    @java.lang.SuppressWarnings("all")
    public Integer getPrazoAprovado() {
        return this.prazoAprovado;
    }

    /**
     * Taxa de juros efetivamente aprovada.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaAprovada() {
        return this.taxaAprovada;
    }

    /**
     * Dados adicionais da solicitação em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
    }

    /**
     * ID do produto de crédito selecionado.
     */
    @java.lang.SuppressWarnings("all")
    public Long getProdutoCreditoId() {
        return this.produtoCreditoId;
    }

    /**
     * URL do contrato gerado, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public String getContratoUrl() {
        return this.contratoUrl;
    }

    /**
     * Data de criação do registro.
     */
    @java.lang.SuppressWarnings("all")
    public String getDataCriacao() {
        return this.dataCriacao;
    }

    /**
     * Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public String getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    /**
     * ID da solicitação.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * ID do cliente solicitante.
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * Nome do cliente solicitante.
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteNome(final String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public void setClienteTipoPessoa(final String clienteTipoPessoa) {
        this.clienteTipoPessoa = clienteTipoPessoa;
    }

    /**
     * Valor total solicitado.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorSolicitado(final BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    /**
     * Prazo para pagamento em meses.
     */
    @java.lang.SuppressWarnings("all")
    public void setPrazoMeses(final Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    /**
     * Taxa de juros proposta.
     */
    @java.lang.SuppressWarnings("all")
    public void setTaxaJuros(final BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    /**
     * Status atual da solicitação.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final SolicitacaoCredito.StatusSolicitacao status) {
        this.status = status;
    }

    /**
     * Score de crédito calculado para o cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setScoreCredito(final Integer scoreCredito) {
        this.scoreCredito = scoreCredito;
    }

    /**
     * Resumo da análise de risco realizada.
     */
    @java.lang.SuppressWarnings("all")
    public void setAnaliseRisco(final String analiseRisco) {
        this.analiseRisco = analiseRisco;
    }

    /**
     * Data e hora da solicitação.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataSolicitacao(final LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    /**
     * Data e hora em que a análise foi iniciada.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAnalise(final LocalDateTime dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    /**
     * Data e hora da aprovação final.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAprovacao(final LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    /**
     * Observações adicionais do analista.
     */
    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * Valor efetivamente aprovado.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorAprovado(final BigDecimal valorAprovado) {
        this.valorAprovado = valorAprovado;
    }

    /**
     * Prazo efetivamente aprovado.
     */
    @java.lang.SuppressWarnings("all")
    public void setPrazoAprovado(final Integer prazoAprovado) {
        this.prazoAprovado = prazoAprovado;
    }

    /**
     * Taxa de juros efetivamente aprovada.
     */
    @java.lang.SuppressWarnings("all")
    public void setTaxaAprovada(final BigDecimal taxaAprovada) {
        this.taxaAprovada = taxaAprovada;
    }

    /**
     * Dados adicionais da solicitação em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    /**
     * ID do produto de crédito selecionado.
     */
    @java.lang.SuppressWarnings("all")
    public void setProdutoCreditoId(final Long produtoCreditoId) {
        this.produtoCreditoId = produtoCreditoId;
    }

    /**
     * URL do contrato gerado, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public void setContratoUrl(final String contratoUrl) {
        this.contratoUrl = contratoUrl;
    }

    /**
     * Data de criação do registro.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SolicitacaoCreditoDTO)) return false;
        final SolicitacaoCreditoDTO other = (SolicitacaoCreditoDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$prazoMeses = this.getPrazoMeses();
        final java.lang.Object other$prazoMeses = other.getPrazoMeses();
        if (this$prazoMeses == null ? other$prazoMeses != null : !this$prazoMeses.equals(other$prazoMeses)) return false;
        final java.lang.Object this$scoreCredito = this.getScoreCredito();
        final java.lang.Object other$scoreCredito = other.getScoreCredito();
        if (this$scoreCredito == null ? other$scoreCredito != null : !this$scoreCredito.equals(other$scoreCredito)) return false;
        final java.lang.Object this$prazoAprovado = this.getPrazoAprovado();
        final java.lang.Object other$prazoAprovado = other.getPrazoAprovado();
        if (this$prazoAprovado == null ? other$prazoAprovado != null : !this$prazoAprovado.equals(other$prazoAprovado)) return false;
        final java.lang.Object this$produtoCreditoId = this.getProdutoCreditoId();
        final java.lang.Object other$produtoCreditoId = other.getProdutoCreditoId();
        if (this$produtoCreditoId == null ? other$produtoCreditoId != null : !this$produtoCreditoId.equals(other$produtoCreditoId)) return false;
        final java.lang.Object this$clienteNome = this.getClienteNome();
        final java.lang.Object other$clienteNome = other.getClienteNome();
        if (this$clienteNome == null ? other$clienteNome != null : !this$clienteNome.equals(other$clienteNome)) return false;
        final java.lang.Object this$clienteTipoPessoa = this.getClienteTipoPessoa();
        final java.lang.Object other$clienteTipoPessoa = other.getClienteTipoPessoa();
        if (this$clienteTipoPessoa == null ? other$clienteTipoPessoa != null : !this$clienteTipoPessoa.equals(other$clienteTipoPessoa)) return false;
        final java.lang.Object this$valorSolicitado = this.getValorSolicitado();
        final java.lang.Object other$valorSolicitado = other.getValorSolicitado();
        if (this$valorSolicitado == null ? other$valorSolicitado != null : !this$valorSolicitado.equals(other$valorSolicitado)) return false;
        final java.lang.Object this$taxaJuros = this.getTaxaJuros();
        final java.lang.Object other$taxaJuros = other.getTaxaJuros();
        if (this$taxaJuros == null ? other$taxaJuros != null : !this$taxaJuros.equals(other$taxaJuros)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$analiseRisco = this.getAnaliseRisco();
        final java.lang.Object other$analiseRisco = other.getAnaliseRisco();
        if (this$analiseRisco == null ? other$analiseRisco != null : !this$analiseRisco.equals(other$analiseRisco)) return false;
        final java.lang.Object this$dataSolicitacao = this.getDataSolicitacao();
        final java.lang.Object other$dataSolicitacao = other.getDataSolicitacao();
        if (this$dataSolicitacao == null ? other$dataSolicitacao != null : !this$dataSolicitacao.equals(other$dataSolicitacao)) return false;
        final java.lang.Object this$dataAnalise = this.getDataAnalise();
        final java.lang.Object other$dataAnalise = other.getDataAnalise();
        if (this$dataAnalise == null ? other$dataAnalise != null : !this$dataAnalise.equals(other$dataAnalise)) return false;
        final java.lang.Object this$dataAprovacao = this.getDataAprovacao();
        final java.lang.Object other$dataAprovacao = other.getDataAprovacao();
        if (this$dataAprovacao == null ? other$dataAprovacao != null : !this$dataAprovacao.equals(other$dataAprovacao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$valorAprovado = this.getValorAprovado();
        final java.lang.Object other$valorAprovado = other.getValorAprovado();
        if (this$valorAprovado == null ? other$valorAprovado != null : !this$valorAprovado.equals(other$valorAprovado)) return false;
        final java.lang.Object this$taxaAprovada = this.getTaxaAprovada();
        final java.lang.Object other$taxaAprovada = other.getTaxaAprovada();
        if (this$taxaAprovada == null ? other$taxaAprovada != null : !this$taxaAprovada.equals(other$taxaAprovada)) return false;
        final java.lang.Object this$dadosAdicionais = this.getDadosAdicionais();
        final java.lang.Object other$dadosAdicionais = other.getDadosAdicionais();
        if (this$dadosAdicionais == null ? other$dadosAdicionais != null : !this$dadosAdicionais.equals(other$dadosAdicionais)) return false;
        final java.lang.Object this$contratoUrl = this.getContratoUrl();
        final java.lang.Object other$contratoUrl = other.getContratoUrl();
        if (this$contratoUrl == null ? other$contratoUrl != null : !this$contratoUrl.equals(other$contratoUrl)) return false;
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        final java.lang.Object this$dataAtualizacao = this.getDataAtualizacao();
        final java.lang.Object other$dataAtualizacao = other.getDataAtualizacao();
        if (this$dataAtualizacao == null ? other$dataAtualizacao != null : !this$dataAtualizacao.equals(other$dataAtualizacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof SolicitacaoCreditoDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $prazoMeses = this.getPrazoMeses();
        result = result * PRIME + ($prazoMeses == null ? 43 : $prazoMeses.hashCode());
        final java.lang.Object $scoreCredito = this.getScoreCredito();
        result = result * PRIME + ($scoreCredito == null ? 43 : $scoreCredito.hashCode());
        final java.lang.Object $prazoAprovado = this.getPrazoAprovado();
        result = result * PRIME + ($prazoAprovado == null ? 43 : $prazoAprovado.hashCode());
        final java.lang.Object $produtoCreditoId = this.getProdutoCreditoId();
        result = result * PRIME + ($produtoCreditoId == null ? 43 : $produtoCreditoId.hashCode());
        final java.lang.Object $clienteNome = this.getClienteNome();
        result = result * PRIME + ($clienteNome == null ? 43 : $clienteNome.hashCode());
        final java.lang.Object $clienteTipoPessoa = this.getClienteTipoPessoa();
        result = result * PRIME + ($clienteTipoPessoa == null ? 43 : $clienteTipoPessoa.hashCode());
        final java.lang.Object $valorSolicitado = this.getValorSolicitado();
        result = result * PRIME + ($valorSolicitado == null ? 43 : $valorSolicitado.hashCode());
        final java.lang.Object $taxaJuros = this.getTaxaJuros();
        result = result * PRIME + ($taxaJuros == null ? 43 : $taxaJuros.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $analiseRisco = this.getAnaliseRisco();
        result = result * PRIME + ($analiseRisco == null ? 43 : $analiseRisco.hashCode());
        final java.lang.Object $dataSolicitacao = this.getDataSolicitacao();
        result = result * PRIME + ($dataSolicitacao == null ? 43 : $dataSolicitacao.hashCode());
        final java.lang.Object $dataAnalise = this.getDataAnalise();
        result = result * PRIME + ($dataAnalise == null ? 43 : $dataAnalise.hashCode());
        final java.lang.Object $dataAprovacao = this.getDataAprovacao();
        result = result * PRIME + ($dataAprovacao == null ? 43 : $dataAprovacao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $valorAprovado = this.getValorAprovado();
        result = result * PRIME + ($valorAprovado == null ? 43 : $valorAprovado.hashCode());
        final java.lang.Object $taxaAprovada = this.getTaxaAprovada();
        result = result * PRIME + ($taxaAprovada == null ? 43 : $taxaAprovada.hashCode());
        final java.lang.Object $dadosAdicionais = this.getDadosAdicionais();
        result = result * PRIME + ($dadosAdicionais == null ? 43 : $dadosAdicionais.hashCode());
        final java.lang.Object $contratoUrl = this.getContratoUrl();
        result = result * PRIME + ($contratoUrl == null ? 43 : $contratoUrl.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "SolicitacaoCreditoDTO(id=" + this.getId() + ", clienteId=" + this.getClienteId() + ", clienteNome=" + this.getClienteNome() + ", clienteTipoPessoa=" + this.getClienteTipoPessoa() + ", valorSolicitado=" + this.getValorSolicitado() + ", prazoMeses=" + this.getPrazoMeses() + ", taxaJuros=" + this.getTaxaJuros() + ", status=" + this.getStatus() + ", scoreCredito=" + this.getScoreCredito() + ", analiseRisco=" + this.getAnaliseRisco() + ", dataSolicitacao=" + this.getDataSolicitacao() + ", dataAnalise=" + this.getDataAnalise() + ", dataAprovacao=" + this.getDataAprovacao() + ", observacoes=" + this.getObservacoes() + ", valorAprovado=" + this.getValorAprovado() + ", prazoAprovado=" + this.getPrazoAprovado() + ", taxaAprovada=" + this.getTaxaAprovada() + ", dadosAdicionais=" + this.getDadosAdicionais() + ", produtoCreditoId=" + this.getProdutoCreditoId() + ", contratoUrl=" + this.getContratoUrl() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public SolicitacaoCreditoDTO() {
    }

    /**
     * Creates a new {@code SolicitacaoCreditoDTO} instance.
     *
     * @param id ID da solicitação.
     * @param clienteId ID do cliente solicitante.
     * @param clienteNome Nome do cliente solicitante.
     * @param valorSolicitado Valor total solicitado.
     * @param prazoMeses Prazo para pagamento em meses.
     * @param taxaJuros Taxa de juros proposta.
     * @param status Status atual da solicitação.
     * @param scoreCredito Score de crédito calculado para o cliente.
     * @param analiseRisco Resumo da análise de risco realizada.
     * @param dataSolicitacao Data e hora da solicitação.
     * @param dataAnalise Data e hora em que a análise foi iniciada.
     * @param dataAprovacao Data e hora da aprovação final.
     * @param observacoes Observações adicionais do analista.
     * @param valorAprovado Valor efetivamente aprovado.
     * @param prazoAprovado Prazo efetivamente aprovado.
     * @param taxaAprovada Taxa de juros efetivamente aprovada.
     * @param dadosAdicionais Dados adicionais da solicitação em formato JSON.
     * @param produtoCreditoId ID do produto de crédito selecionado.
     * @param contratoUrl URL do contrato gerado, se aplicável.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public SolicitacaoCreditoDTO(final Long id, final Long clienteId, final String clienteNome, final String clienteTipoPessoa, final BigDecimal valorSolicitado, final Integer prazoMeses, final BigDecimal taxaJuros, final SolicitacaoCredito.StatusSolicitacao status, final Integer scoreCredito, final String analiseRisco, final LocalDateTime dataSolicitacao, final LocalDateTime dataAnalise, final LocalDateTime dataAprovacao, final String observacoes, final BigDecimal valorAprovado, final Integer prazoAprovado, final BigDecimal taxaAprovada, final String dadosAdicionais, final Long produtoCreditoId, final String contratoUrl, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.clienteTipoPessoa = clienteTipoPessoa;
        this.valorSolicitado = valorSolicitado;
        this.prazoMeses = prazoMeses;
        this.taxaJuros = taxaJuros;
        this.status = status;
        this.scoreCredito = scoreCredito;
        this.analiseRisco = analiseRisco;
        this.dataSolicitacao = dataSolicitacao;
        this.dataAnalise = dataAnalise;
        this.dataAprovacao = dataAprovacao;
        this.observacoes = observacoes;
        this.valorAprovado = valorAprovado;
        this.prazoAprovado = prazoAprovado;
        this.taxaAprovada = taxaAprovada;
        this.dadosAdicionais = dadosAdicionais;
        this.produtoCreditoId = produtoCreditoId;
        this.contratoUrl = contratoUrl;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
