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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade Investimento do Aurix.
 * Representa um investimento.
 */
@Entity
@Table(name = "investimentos", schema = "aurix")
public class Investimento extends BaseEntity {
    /**
     * Precisão decimal para valores monetários.
     */
    private static final int DECIMAL_PRECISION = 15;
    /**
     * Escala decimal para valores monetários.
     */
    private static final int DECIMAL_SCALE = 2;
    /**
     * Precisão decimal para taxas percentuais.
     */
    private static final int PERCENTAGE_PRECISION = 5;
    /**
     * Escala decimal para taxas percentuais.
     */
    private static final int PERCENTAGE_SCALE = 4;
    /**
     * Conta vinculada ao investimento.
     */
    @NotNull(message = "Conta é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    /**
     * Categoria do ativo financeiro (CDB, Tesouro, etc.).
     */
    @NotNull(message = "Tipo do investimento é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_investimento", nullable = false)
    private TipoInvestimento tipoInvestimento;
    /**
     * Valor monetário inicialmente aplicado.
     */
    @NotNull(message = "Valor investido é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor investido deve ser maior que zero")
    @Column(name = "valor_investido", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal valorInvestido;
    /**
     * Taxa de rentabilidade acordada (ex: 0.12 para 12%).
     */
    @NotNull(message = "Taxa de rentabilidade acordada")
    @DecimalMin(value = "0.0", message = "Taxa de rendimento não pode ser negativa")
    @Column(name = "taxa_rendimento", precision = PERCENTAGE_PRECISION, scale = PERCENTAGE_SCALE, nullable = false)
    private BigDecimal taxaRendimento;
    /**
     * Data e hora da aplicação inicial.
     */
    @Column(name = "data_aplicacao", nullable = false)
    private LocalDateTime dataAplicacao = LocalDateTime.now();
    /**
     * Data e hora previstas para o vencimento.
     */
    @Column(name = "data_vencimento")
    private LocalDateTime dataVencimento;
    /**
     * Status da custódia do investimento.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusInvestimento status = StatusInvestimento.ATIVO;
    /**
     * Valor acumulado de rendimento até o momento.
     */
    @DecimalMin(value = "0.0", message = "Rendimento atual não pode ser negativo")
    @Column(name = "rendimento_atual", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal rendimentoAtual = BigDecimal.ZERO;
    /**
     * Detalhes técnicos específicos do ativo (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_investimento", columnDefinition = "jsonb")
    private String dadosInvestimento;

    /**
     * Calcula o valor total (investido + rendimento).
     *
     * @return BigDecimal valor total.
     */
    public BigDecimal getValorTotal() {
        return valorInvestido.add(rendimentoAtual);
    }

    /**
     * Verifica se o investimento está vencido.
     *
     * @return true se vencido, false caso contrário.
     */
    public boolean isVencido() {
        return dataVencimento != null && LocalDateTime.now().isAfter(dataVencimento);
    }


    /**
     * Enum para tipo de investimento.
     */
    public enum TipoInvestimento {
        /**
         * CDB.
         */
        CDB("CDB - Certificado de Depósito Bancário"), /**
         * LCA.
         */
        LCA("LCA - Letra de Crédito do Agronegócio"), /**
         * LCI.
         */
        LCI("LCI - Letra de Crédito Imobiliário"), /**
         * Tesouro Selic.
         */
        TESOURO_SELIC("Tesouro Selic"), /**
         * Tesouro IPCA+.
         */
        TESOURO_IPCA("Tesouro IPCA+"), /**
         * Tesouro Prefixado.
         */
        TESOURO_PREFIXADO("Tesouro Prefixado"), /**
         * Fundo DI.
         */
        FUNDO_DI("Fundo DI"), /**
         * Fundo Renda Fixa.
         */
        FUNDO_RENDA_FIXA("Fundo Renda Fixa"), /**
         * Fundo Multimercado.
         */
        FUNDO_MULTIMERCADO("Fundo Multimercado");
        /**
         * Descrição do tipo.
         */
        private final String descricao;

        TipoInvestimento(final String desc) {
            this.descricao = desc;
        }

        /**
         * Retorna a descrição do tipo.
         * 
         * @return a descrição.
         */
        public String getDescricao() {
            return descricao;
        }
    }


    /**
     * Enum para status do investimento.
     */
    public enum StatusInvestimento {
        /**
         * Ativo.
         */
        ATIVO("Ativa"), /**
         * Resgatado.
         */
        RESGATADO("Resgatado"), /**
         * Vencido.
         */
        VENCIDO("Vencido"), /**
         * Cancelado.
         */
        CANCELADO("Cancelado");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusInvestimento(final String desc) {
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
     * Conta vinculada ao investimento.
     */
    @java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    /**
     * Categoria do ativo financeiro (CDB, Tesouro, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public TipoInvestimento getTipoInvestimento() {
        return this.tipoInvestimento;
    }

    /**
     * Valor monetário inicialmente aplicado.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorInvestido() {
        return this.valorInvestido;
    }

    /**
     * Taxa de rentabilidade acordada (ex: 0.12 para 12%).
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaRendimento() {
        return this.taxaRendimento;
    }

    /**
     * Data e hora da aplicação inicial.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAplicacao() {
        return this.dataAplicacao;
    }

    /**
     * Data e hora previstas para o vencimento.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    /**
     * Status da custódia do investimento.
     */
    @java.lang.SuppressWarnings("all")
    public StatusInvestimento getStatus() {
        return this.status;
    }

    /**
     * Valor acumulado de rendimento até o momento.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getRendimentoAtual() {
        return this.rendimentoAtual;
    }

    /**
     * Detalhes técnicos específicos do ativo (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosInvestimento() {
        return this.dadosInvestimento;
    }

    /**
     * Conta vinculada ao investimento.
     */
    @java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    /**
     * Categoria do ativo financeiro (CDB, Tesouro, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoInvestimento(final TipoInvestimento tipoInvestimento) {
        this.tipoInvestimento = tipoInvestimento;
    }

    /**
     * Valor monetário inicialmente aplicado.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorInvestido(final BigDecimal valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    /**
     * Taxa de rentabilidade acordada (ex: 0.12 para 12%).
     */
    @java.lang.SuppressWarnings("all")
    public void setTaxaRendimento(final BigDecimal taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    /**
     * Data e hora da aplicação inicial.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAplicacao(final LocalDateTime dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    /**
     * Data e hora previstas para o vencimento.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * Status da custódia do investimento.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusInvestimento status) {
        this.status = status;
    }

    /**
     * Valor acumulado de rendimento até o momento.
     */
    @java.lang.SuppressWarnings("all")
    public void setRendimentoAtual(final BigDecimal rendimentoAtual) {
        this.rendimentoAtual = rendimentoAtual;
    }

    /**
     * Detalhes técnicos específicos do ativo (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosInvestimento(final String dadosInvestimento) {
        this.dadosInvestimento = dadosInvestimento;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Investimento(conta=" + this.getConta() + ", tipoInvestimento=" + this.getTipoInvestimento() + ", valorInvestido=" + this.getValorInvestido() + ", taxaRendimento=" + this.getTaxaRendimento() + ", dataAplicacao=" + this.getDataAplicacao() + ", dataVencimento=" + this.getDataVencimento() + ", status=" + this.getStatus() + ", rendimentoAtual=" + this.getRendimentoAtual() + ", dadosInvestimento=" + this.getDadosInvestimento() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Investimento)) return false;
        final Investimento other = (Investimento) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$tipoInvestimento = this.getTipoInvestimento();
        final java.lang.Object other$tipoInvestimento = other.getTipoInvestimento();
        if (this$tipoInvestimento == null ? other$tipoInvestimento != null : !this$tipoInvestimento.equals(other$tipoInvestimento)) return false;
        final java.lang.Object this$valorInvestido = this.getValorInvestido();
        final java.lang.Object other$valorInvestido = other.getValorInvestido();
        if (this$valorInvestido == null ? other$valorInvestido != null : !this$valorInvestido.equals(other$valorInvestido)) return false;
        final java.lang.Object this$taxaRendimento = this.getTaxaRendimento();
        final java.lang.Object other$taxaRendimento = other.getTaxaRendimento();
        if (this$taxaRendimento == null ? other$taxaRendimento != null : !this$taxaRendimento.equals(other$taxaRendimento)) return false;
        final java.lang.Object this$dataAplicacao = this.getDataAplicacao();
        final java.lang.Object other$dataAplicacao = other.getDataAplicacao();
        if (this$dataAplicacao == null ? other$dataAplicacao != null : !this$dataAplicacao.equals(other$dataAplicacao)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$rendimentoAtual = this.getRendimentoAtual();
        final java.lang.Object other$rendimentoAtual = other.getRendimentoAtual();
        if (this$rendimentoAtual == null ? other$rendimentoAtual != null : !this$rendimentoAtual.equals(other$rendimentoAtual)) return false;
        final java.lang.Object this$dadosInvestimento = this.getDadosInvestimento();
        final java.lang.Object other$dadosInvestimento = other.getDadosInvestimento();
        if (this$dadosInvestimento == null ? other$dadosInvestimento != null : !this$dadosInvestimento.equals(other$dadosInvestimento)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Investimento;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $tipoInvestimento = this.getTipoInvestimento();
        result = result * PRIME + ($tipoInvestimento == null ? 43 : $tipoInvestimento.hashCode());
        final java.lang.Object $valorInvestido = this.getValorInvestido();
        result = result * PRIME + ($valorInvestido == null ? 43 : $valorInvestido.hashCode());
        final java.lang.Object $taxaRendimento = this.getTaxaRendimento();
        result = result * PRIME + ($taxaRendimento == null ? 43 : $taxaRendimento.hashCode());
        final java.lang.Object $dataAplicacao = this.getDataAplicacao();
        result = result * PRIME + ($dataAplicacao == null ? 43 : $dataAplicacao.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $rendimentoAtual = this.getRendimentoAtual();
        result = result * PRIME + ($rendimentoAtual == null ? 43 : $rendimentoAtual.hashCode());
        final java.lang.Object $dadosInvestimento = this.getDadosInvestimento();
        result = result * PRIME + ($dadosInvestimento == null ? 43 : $dadosInvestimento.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Investimento() {
    }

    /**
     * Creates a new {@code Investimento} instance.
     *
     * @param conta Conta vinculada ao investimento.
     * @param tipoInvestimento Categoria do ativo financeiro (CDB, Tesouro, etc.).
     * @param valorInvestido Valor monetário inicialmente aplicado.
     * @param taxaRendimento Taxa de rentabilidade acordada (ex: 0.12 para 12%).
     * @param dataAplicacao Data e hora da aplicação inicial.
     * @param dataVencimento Data e hora previstas para o vencimento.
     * @param status Status da custódia do investimento.
     * @param rendimentoAtual Valor acumulado de rendimento até o momento.
     * @param dadosInvestimento Detalhes técnicos específicos do ativo (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public Investimento(final Conta conta, final TipoInvestimento tipoInvestimento, final BigDecimal valorInvestido, final BigDecimal taxaRendimento, final LocalDateTime dataAplicacao, final LocalDateTime dataVencimento, final StatusInvestimento status, final BigDecimal rendimentoAtual, final String dadosInvestimento) {
        this.conta = conta;
        this.tipoInvestimento = tipoInvestimento;
        this.valorInvestido = valorInvestido;
        this.taxaRendimento = taxaRendimento;
        this.dataAplicacao = dataAplicacao;
        this.dataVencimento = dataVencimento;
        this.status = status;
        this.rendimentoAtual = rendimentoAtual;
        this.dadosInvestimento = dadosInvestimento;
    }
}
