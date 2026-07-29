package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade Solicitação de Crédito do Aurix.
 * Representa uma solicitação de crédito.
 */
@Entity
@Table(name = "solicitacoes_credito", schema = "aurix")
public class SolicitacaoCredito extends BaseEntity {
    /**
     * Comprimento para campos longos.
     */
    private static final int LENGTH_LONG = 1000;
    /**
     * Comprimento para URIs de contrato.
     */
    private static final int CONTRATO_URL_LENGTH = 500;
    /**
     * Precisão decimal para valores monetários.
     */
    private static final int DECIMAL_PRECISION = 15;
    /**
     * Escala decimal para valores monetários.
     */
    private static final int DECIMAL_SCALE = 2;
    /**
     * Precisão decimal para taxas.
     */
    private static final int TAXA_PRECISION = 5;
    /**
     * Escala decimal para taxas.
     */
    private static final int TAXA_SCALE = 4;
    /**
     * Cliente interessado no crédito.
     */
    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    /**
     * Valor monetário bruto solicitado originalmente.
     */
    @NotNull(message = "Valor solicitado é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor solicitado deve ser maior que zero")
    @Column(name = "valor_solicitado", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal valorSolicitado;
    /**
     * Duração desejada para o pagamento em meses.
     */
    @NotNull(message = "Prazo em meses é obrigatório")
    @Column(name = "prazo_meses", nullable = false)
    private Integer prazoMeses;
    /**
     * Percentual de juros esperado ou proposto.
     */
    @NotNull(message = "Taxa de juros é obrigatória")
    @DecimalMin(value = "0.0", message = "Taxa de juros não pode ser negativa")
    @Column(name = "taxa_juros", precision = TAXA_PRECISION, scale = TAXA_SCALE, nullable = false)
    private BigDecimal taxaJuros;
    /**
     * Ciclo de vida atual da solicitação no motor de crédito.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSolicitacao status = StatusSolicitacao.PENDENTE;
    /**
     * Pontuação de crédito obtida em bureaus externos.
     */
    @Column(name = "score_credito")
    private Integer scoreCredito;
    /**
     * Detalhes resultantes do motor de decisão (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "analise_risco", columnDefinition = "jsonb")
    private String analiseRisco;
    /**
     * Data e hora da submissão da proposta.
     */
    @Column(name = "data_solicitacao", nullable = false)
    private LocalDateTime dataSolicitacao = LocalDateTime.now();
    /**
     * Data e hora do início do processamento técnico.
     */
    @Column(name = "data_analise")
    private LocalDateTime dataAnalise;
    /**
     * Data e hora do veredito positivo/negativo.
     */
    @Column(name = "data_aprovacao")
    private LocalDateTime dataAprovacao;
    /**
     * Comentários qualitativos do analista de crédito.
     */
    @Size(max = LENGTH_LONG, message = "Observações devem ter no máximo 1000 caracteres")
    @Column(name = "observacoes", length = LENGTH_LONG)
    private String observacoes;
    /**
     * Valor final aprovado pelo comitê de crédito.
     */
    @Column(name = "valor_aprovado", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal valorAprovado;
    /**
     * Prazo final concedido para o pagamento.
     */
    @Column(name = "prazo_aprovado")
    private Integer prazoAprovado;
    /**
     * Taxa de juros final precificada após análise.
     */
    @Column(name = "taxa_aprovada", precision = TAXA_PRECISION, scale = TAXA_SCALE)
    private BigDecimal taxaAprovada;
    /**
     * Metadados suplementares da proposta (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_adicionais", columnDefinition = "jsonb")
    private String dadosAdicionais;
    /**
     * Identificador do produto de crédito selecionado.
     */
    @Column(name = "produto_credito_id")
    private Long produtoCreditoId;
    /**
     * Link permanente para a minuta ou contrato assinado.
     */
    @Column(name = "contrato_url", length = CONTRATO_URL_LENGTH)
    private String contratoUrl;
    /**
     * Data em que o cliente aceitou as condições propostas.
     */
    @Column(name = "data_aceite")
    private LocalDateTime dataAceite;
    /**
     * Data da efetiva transferência dos fundos ao cliente.
     */
    @Column(name = "data_liberacao")
    private LocalDateTime dataLiberacao;


    /**
     * Status da solicitação.
     */
    public enum StatusSolicitacao {
        /**
         * Pendente.
         */
        PENDENTE("Pendente"), /**
         * Em análise.
         */
        EM_ANALISE("Em Análise"), /**
         * Refer.
         */
        REFER("Refer para analista"), /**
         * Aprovada.
         */
        APROVADA("Aprovada"), /**
         * Rejeitada.
         */
        REJEITADA("Rejeitada"), /**
         * Oferta emitida.
         */
        OFERTA_EMITIDA("Oferta emitida"), /**
         * Aceito.
         */
        ACEITO("Aceito pelo cliente"), /**
         * Contrato assinado.
         */
        CONTRATO_ASSINADO("Contrato assinado"), /**
         * Liberado.
         */
        LIBERADO("Liberado"), /**
         * Cancelada.
         */
        CANCELADA("Cancelada"), /**
         * Expirada.
         */
        EXPIRADA("Expirada");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusSolicitacao(final String desc) {
            this.descricao = desc;
        }

        /**
         * Retorna a descrição do status.
         * 
         * @return a descrição.
         */
        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Cliente interessado no crédito.
     */
    @java.lang.SuppressWarnings("all")
    public Cliente getCliente() {
        return this.cliente;
    }

    /**
     * Valor monetário bruto solicitado originalmente.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorSolicitado() {
        return this.valorSolicitado;
    }

    /**
     * Duração desejada para o pagamento em meses.
     */
    @java.lang.SuppressWarnings("all")
    public Integer getPrazoMeses() {
        return this.prazoMeses;
    }

    /**
     * Percentual de juros esperado ou proposto.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaJuros() {
        return this.taxaJuros;
    }

    /**
     * Ciclo de vida atual da solicitação no motor de crédito.
     */
    @java.lang.SuppressWarnings("all")
    public StatusSolicitacao getStatus() {
        return this.status;
    }

    /**
     * Pontuação de crédito obtida em bureaus externos.
     */
    @java.lang.SuppressWarnings("all")
    public Integer getScoreCredito() {
        return this.scoreCredito;
    }

    /**
     * Detalhes resultantes do motor de decisão (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getAnaliseRisco() {
        return this.analiseRisco;
    }

    /**
     * Data e hora da submissão da proposta.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataSolicitacao() {
        return this.dataSolicitacao;
    }

    /**
     * Data e hora do início do processamento técnico.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAnalise() {
        return this.dataAnalise;
    }

    /**
     * Data e hora do veredito positivo/negativo.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAprovacao() {
        return this.dataAprovacao;
    }

    /**
     * Comentários qualitativos do analista de crédito.
     */
    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    /**
     * Valor final aprovado pelo comitê de crédito.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAprovado() {
        return this.valorAprovado;
    }

    /**
     * Prazo final concedido para o pagamento.
     */
    @java.lang.SuppressWarnings("all")
    public Integer getPrazoAprovado() {
        return this.prazoAprovado;
    }

    /**
     * Taxa de juros final precificada após análise.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaAprovada() {
        return this.taxaAprovada;
    }

    /**
     * Metadados suplementares da proposta (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
    }

    /**
     * Identificador do produto de crédito selecionado.
     */
    @java.lang.SuppressWarnings("all")
    public Long getProdutoCreditoId() {
        return this.produtoCreditoId;
    }

    /**
     * Link permanente para a minuta ou contrato assinado.
     */
    @java.lang.SuppressWarnings("all")
    public String getContratoUrl() {
        return this.contratoUrl;
    }

    /**
     * Data em que o cliente aceitou as condições propostas.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAceite() {
        return this.dataAceite;
    }

    /**
     * Data da efetiva transferência dos fundos ao cliente.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataLiberacao() {
        return this.dataLiberacao;
    }

    /**
     * Cliente interessado no crédito.
     */
    @java.lang.SuppressWarnings("all")
    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Valor monetário bruto solicitado originalmente.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorSolicitado(final BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    /**
     * Duração desejada para o pagamento em meses.
     */
    @java.lang.SuppressWarnings("all")
    public void setPrazoMeses(final Integer prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    /**
     * Percentual de juros esperado ou proposto.
     */
    @java.lang.SuppressWarnings("all")
    public void setTaxaJuros(final BigDecimal taxaJuros) {
        this.taxaJuros = taxaJuros;
    }

    /**
     * Ciclo de vida atual da solicitação no motor de crédito.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusSolicitacao status) {
        this.status = status;
    }

    /**
     * Pontuação de crédito obtida em bureaus externos.
     */
    @java.lang.SuppressWarnings("all")
    public void setScoreCredito(final Integer scoreCredito) {
        this.scoreCredito = scoreCredito;
    }

    /**
     * Detalhes resultantes do motor de decisão (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setAnaliseRisco(final String analiseRisco) {
        this.analiseRisco = analiseRisco;
    }

    /**
     * Data e hora da submissão da proposta.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataSolicitacao(final LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    /**
     * Data e hora do início do processamento técnico.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAnalise(final LocalDateTime dataAnalise) {
        this.dataAnalise = dataAnalise;
    }

    /**
     * Data e hora do veredito positivo/negativo.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAprovacao(final LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    /**
     * Comentários qualitativos do analista de crédito.
     */
    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    /**
     * Valor final aprovado pelo comitê de crédito.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorAprovado(final BigDecimal valorAprovado) {
        this.valorAprovado = valorAprovado;
    }

    /**
     * Prazo final concedido para o pagamento.
     */
    @java.lang.SuppressWarnings("all")
    public void setPrazoAprovado(final Integer prazoAprovado) {
        this.prazoAprovado = prazoAprovado;
    }

    /**
     * Taxa de juros final precificada após análise.
     */
    @java.lang.SuppressWarnings("all")
    public void setTaxaAprovada(final BigDecimal taxaAprovada) {
        this.taxaAprovada = taxaAprovada;
    }

    /**
     * Metadados suplementares da proposta (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    /**
     * Identificador do produto de crédito selecionado.
     */
    @java.lang.SuppressWarnings("all")
    public void setProdutoCreditoId(final Long produtoCreditoId) {
        this.produtoCreditoId = produtoCreditoId;
    }

    /**
     * Link permanente para a minuta ou contrato assinado.
     */
    @java.lang.SuppressWarnings("all")
    public void setContratoUrl(final String contratoUrl) {
        this.contratoUrl = contratoUrl;
    }

    /**
     * Data em que o cliente aceitou as condições propostas.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAceite(final LocalDateTime dataAceite) {
        this.dataAceite = dataAceite;
    }

    /**
     * Data da efetiva transferência dos fundos ao cliente.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataLiberacao(final LocalDateTime dataLiberacao) {
        this.dataLiberacao = dataLiberacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "SolicitacaoCredito(cliente=" + this.getCliente() + ", valorSolicitado=" + this.getValorSolicitado() + ", prazoMeses=" + this.getPrazoMeses() + ", taxaJuros=" + this.getTaxaJuros() + ", status=" + this.getStatus() + ", scoreCredito=" + this.getScoreCredito() + ", analiseRisco=" + this.getAnaliseRisco() + ", dataSolicitacao=" + this.getDataSolicitacao() + ", dataAnalise=" + this.getDataAnalise() + ", dataAprovacao=" + this.getDataAprovacao() + ", observacoes=" + this.getObservacoes() + ", valorAprovado=" + this.getValorAprovado() + ", prazoAprovado=" + this.getPrazoAprovado() + ", taxaAprovada=" + this.getTaxaAprovada() + ", dadosAdicionais=" + this.getDadosAdicionais() + ", produtoCreditoId=" + this.getProdutoCreditoId() + ", contratoUrl=" + this.getContratoUrl() + ", dataAceite=" + this.getDataAceite() + ", dataLiberacao=" + this.getDataLiberacao() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SolicitacaoCredito)) return false;
        final SolicitacaoCredito other = (SolicitacaoCredito) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
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
        final java.lang.Object this$cliente = this.getCliente();
        final java.lang.Object other$cliente = other.getCliente();
        if (this$cliente == null ? other$cliente != null : !this$cliente.equals(other$cliente)) return false;
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
        final java.lang.Object this$dataAceite = this.getDataAceite();
        final java.lang.Object other$dataAceite = other.getDataAceite();
        if (this$dataAceite == null ? other$dataAceite != null : !this$dataAceite.equals(other$dataAceite)) return false;
        final java.lang.Object this$dataLiberacao = this.getDataLiberacao();
        final java.lang.Object other$dataLiberacao = other.getDataLiberacao();
        if (this$dataLiberacao == null ? other$dataLiberacao != null : !this$dataLiberacao.equals(other$dataLiberacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof SolicitacaoCredito;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $prazoMeses = this.getPrazoMeses();
        result = result * PRIME + ($prazoMeses == null ? 43 : $prazoMeses.hashCode());
        final java.lang.Object $scoreCredito = this.getScoreCredito();
        result = result * PRIME + ($scoreCredito == null ? 43 : $scoreCredito.hashCode());
        final java.lang.Object $prazoAprovado = this.getPrazoAprovado();
        result = result * PRIME + ($prazoAprovado == null ? 43 : $prazoAprovado.hashCode());
        final java.lang.Object $produtoCreditoId = this.getProdutoCreditoId();
        result = result * PRIME + ($produtoCreditoId == null ? 43 : $produtoCreditoId.hashCode());
        final java.lang.Object $cliente = this.getCliente();
        result = result * PRIME + ($cliente == null ? 43 : $cliente.hashCode());
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
        final java.lang.Object $dataAceite = this.getDataAceite();
        result = result * PRIME + ($dataAceite == null ? 43 : $dataAceite.hashCode());
        final java.lang.Object $dataLiberacao = this.getDataLiberacao();
        result = result * PRIME + ($dataLiberacao == null ? 43 : $dataLiberacao.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public SolicitacaoCredito() {
    }

    /**
     * Creates a new {@code SolicitacaoCredito} instance.
     *
     * @param cliente Cliente interessado no crédito.
     * @param valorSolicitado Valor monetário bruto solicitado originalmente.
     * @param prazoMeses Duração desejada para o pagamento em meses.
     * @param taxaJuros Percentual de juros esperado ou proposto.
     * @param status Ciclo de vida atual da solicitação no motor de crédito.
     * @param scoreCredito Pontuação de crédito obtida em bureaus externos.
     * @param analiseRisco Detalhes resultantes do motor de decisão (JSON).
     * @param dataSolicitacao Data e hora da submissão da proposta.
     * @param dataAnalise Data e hora do início do processamento técnico.
     * @param dataAprovacao Data e hora do veredito positivo/negativo.
     * @param observacoes Comentários qualitativos do analista de crédito.
     * @param valorAprovado Valor final aprovado pelo comitê de crédito.
     * @param prazoAprovado Prazo final concedido para o pagamento.
     * @param taxaAprovada Taxa de juros final precificada após análise.
     * @param dadosAdicionais Metadados suplementares da proposta (JSON).
     * @param produtoCreditoId Identificador do produto de crédito selecionado.
     * @param contratoUrl Link permanente para a minuta ou contrato assinado.
     * @param dataAceite Data em que o cliente aceitou as condições propostas.
     * @param dataLiberacao Data da efetiva transferência dos fundos ao cliente.
     */
    @java.lang.SuppressWarnings("all")
    public SolicitacaoCredito(final Cliente cliente, final BigDecimal valorSolicitado, final Integer prazoMeses, final BigDecimal taxaJuros, final StatusSolicitacao status, final Integer scoreCredito, final String analiseRisco, final LocalDateTime dataSolicitacao, final LocalDateTime dataAnalise, final LocalDateTime dataAprovacao, final String observacoes, final BigDecimal valorAprovado, final Integer prazoAprovado, final BigDecimal taxaAprovada, final String dadosAdicionais, final Long produtoCreditoId, final String contratoUrl, final LocalDateTime dataAceite, final LocalDateTime dataLiberacao) {
        this.cliente = cliente;
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
        this.dataAceite = dataAceite;
        this.dataLiberacao = dataLiberacao;
    }
}
