package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.Investimento;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para Investimento do Aurix.
 */
public class InvestimentoDTO {
    /**
     * Valor mínimo de investimento: 0.01.
     */
    private static final String MIN_INVESTMENT = "0.01";
    /**
     * Valor mínimo de rendimento: 0.0.
     */
    private static final String MIN_RATE = "0.0";
    /**
     * ID do investimento.
     */
    private Long id;
    /**
     * ID da conta associada.
     */
    @NotNull(message = "Conta é obrigatória")
    private Long contaId;
    /**
     * Número da conta associada.
     */
    private String contaNumero;
    /**
     * Tipo do investimento.
     */
    @NotNull(message = "Tipo do investimento é obrigatório")
    private Investimento.TipoInvestimento tipoInvestimento;
    /**
     * Valor inicialmente investido.
     */
    @NotNull(message = "Valor investido é obrigatório")
    @DecimalMin(value = MIN_INVESTMENT, message = "Valor investido deve ser maior que zero")
    private BigDecimal valorInvestido;
    /**
     * Taxa de rendimento (anual/mensal).
     */
    @NotNull(message = "Taxa de rendimento é obrigatória")
    @DecimalMin(value = MIN_RATE, message = "Taxa de rendimento não pode ser negativa")
    private BigDecimal taxaRendimento;
    /**
     * Data da aplicação inicial.
     */
    private LocalDateTime dataAplicacao;
    /**
     * Data prevista de vencimento.
     */
    private LocalDateTime dataVencimento;
    /**
     * Status atual do investimento.
     */
    private Investimento.StatusInvestimento status;
    /**
     * Valor acumulado de rendimento.
     */
    private BigDecimal rendimentoAtual;
    /**
     * Valor total (bruto) atual.
     */
    private BigDecimal valorTotal;
    /**
     * Valor líquido após impostos pagos.
     */
    private BigDecimal valorLiquido;
    /**
     * Valor retido de IOF.
     */
    private BigDecimal valorIOF;
    /**
     * Valor retido de Imposto de Renda.
     */
    private BigDecimal valorIR;
    /**
     * Detalhes extras em formato JSON.
     */
    private String dadosInvestimento;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID do investimento.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * ID da conta associada.
     */
    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    /**
     * Número da conta associada.
     */
    @java.lang.SuppressWarnings("all")
    public String getContaNumero() {
        return this.contaNumero;
    }

    /**
     * Tipo do investimento.
     */
    @java.lang.SuppressWarnings("all")
    public Investimento.TipoInvestimento getTipoInvestimento() {
        return this.tipoInvestimento;
    }

    /**
     * Valor inicialmente investido.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorInvestido() {
        return this.valorInvestido;
    }

    /**
     * Taxa de rendimento (anual/mensal).
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaRendimento() {
        return this.taxaRendimento;
    }

    /**
     * Data da aplicação inicial.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAplicacao() {
        return this.dataAplicacao;
    }

    /**
     * Data prevista de vencimento.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    /**
     * Status atual do investimento.
     */
    @java.lang.SuppressWarnings("all")
    public Investimento.StatusInvestimento getStatus() {
        return this.status;
    }

    /**
     * Valor acumulado de rendimento.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getRendimentoAtual() {
        return this.rendimentoAtual;
    }

    /**
     * Valor total (bruto) atual.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    /**
     * Valor líquido após impostos pagos.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorLiquido() {
        return this.valorLiquido;
    }

    /**
     * Valor retido de IOF.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorIOF() {
        return this.valorIOF;
    }

    /**
     * Valor retido de Imposto de Renda.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorIR() {
        return this.valorIR;
    }

    /**
     * Detalhes extras em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosInvestimento() {
        return this.dadosInvestimento;
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
     * ID do investimento.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * ID da conta associada.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    /**
     * Número da conta associada.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaNumero(final String contaNumero) {
        this.contaNumero = contaNumero;
    }

    /**
     * Tipo do investimento.
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoInvestimento(final Investimento.TipoInvestimento tipoInvestimento) {
        this.tipoInvestimento = tipoInvestimento;
    }

    /**
     * Valor inicialmente investido.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorInvestido(final BigDecimal valorInvestido) {
        this.valorInvestido = valorInvestido;
    }

    /**
     * Taxa de rendimento (anual/mensal).
     */
    @java.lang.SuppressWarnings("all")
    public void setTaxaRendimento(final BigDecimal taxaRendimento) {
        this.taxaRendimento = taxaRendimento;
    }

    /**
     * Data da aplicação inicial.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAplicacao(final LocalDateTime dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    /**
     * Data prevista de vencimento.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    /**
     * Status atual do investimento.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final Investimento.StatusInvestimento status) {
        this.status = status;
    }

    /**
     * Valor acumulado de rendimento.
     */
    @java.lang.SuppressWarnings("all")
    public void setRendimentoAtual(final BigDecimal rendimentoAtual) {
        this.rendimentoAtual = rendimentoAtual;
    }

    /**
     * Valor total (bruto) atual.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorTotal(final BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    /**
     * Valor líquido após impostos pagos.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorLiquido(final BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    /**
     * Valor retido de IOF.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorIOF(final BigDecimal valorIOF) {
        this.valorIOF = valorIOF;
    }

    /**
     * Valor retido de Imposto de Renda.
     */
    @java.lang.SuppressWarnings("all")
    public void setValorIR(final BigDecimal valorIR) {
        this.valorIR = valorIR;
    }

    /**
     * Detalhes extras em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosInvestimento(final String dadosInvestimento) {
        this.dadosInvestimento = dadosInvestimento;
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
        if (!(o instanceof InvestimentoDTO)) return false;
        final InvestimentoDTO other = (InvestimentoDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$contaId = this.getContaId();
        final java.lang.Object other$contaId = other.getContaId();
        if (this$contaId == null ? other$contaId != null : !this$contaId.equals(other$contaId)) return false;
        final java.lang.Object this$contaNumero = this.getContaNumero();
        final java.lang.Object other$contaNumero = other.getContaNumero();
        if (this$contaNumero == null ? other$contaNumero != null : !this$contaNumero.equals(other$contaNumero)) return false;
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
        final java.lang.Object this$valorTotal = this.getValorTotal();
        final java.lang.Object other$valorTotal = other.getValorTotal();
        if (this$valorTotal == null ? other$valorTotal != null : !this$valorTotal.equals(other$valorTotal)) return false;
        final java.lang.Object this$valorLiquido = this.getValorLiquido();
        final java.lang.Object other$valorLiquido = other.getValorLiquido();
        if (this$valorLiquido == null ? other$valorLiquido != null : !this$valorLiquido.equals(other$valorLiquido)) return false;
        final java.lang.Object this$valorIOF = this.getValorIOF();
        final java.lang.Object other$valorIOF = other.getValorIOF();
        if (this$valorIOF == null ? other$valorIOF != null : !this$valorIOF.equals(other$valorIOF)) return false;
        final java.lang.Object this$valorIR = this.getValorIR();
        final java.lang.Object other$valorIR = other.getValorIR();
        if (this$valorIR == null ? other$valorIR != null : !this$valorIR.equals(other$valorIR)) return false;
        final java.lang.Object this$dadosInvestimento = this.getDadosInvestimento();
        final java.lang.Object other$dadosInvestimento = other.getDadosInvestimento();
        if (this$dadosInvestimento == null ? other$dadosInvestimento != null : !this$dadosInvestimento.equals(other$dadosInvestimento)) return false;
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
        return other instanceof InvestimentoDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $contaId = this.getContaId();
        result = result * PRIME + ($contaId == null ? 43 : $contaId.hashCode());
        final java.lang.Object $contaNumero = this.getContaNumero();
        result = result * PRIME + ($contaNumero == null ? 43 : $contaNumero.hashCode());
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
        final java.lang.Object $valorTotal = this.getValorTotal();
        result = result * PRIME + ($valorTotal == null ? 43 : $valorTotal.hashCode());
        final java.lang.Object $valorLiquido = this.getValorLiquido();
        result = result * PRIME + ($valorLiquido == null ? 43 : $valorLiquido.hashCode());
        final java.lang.Object $valorIOF = this.getValorIOF();
        result = result * PRIME + ($valorIOF == null ? 43 : $valorIOF.hashCode());
        final java.lang.Object $valorIR = this.getValorIR();
        result = result * PRIME + ($valorIR == null ? 43 : $valorIR.hashCode());
        final java.lang.Object $dadosInvestimento = this.getDadosInvestimento();
        result = result * PRIME + ($dadosInvestimento == null ? 43 : $dadosInvestimento.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "InvestimentoDTO(id=" + this.getId() + ", contaId=" + this.getContaId() + ", contaNumero=" + this.getContaNumero() + ", tipoInvestimento=" + this.getTipoInvestimento() + ", valorInvestido=" + this.getValorInvestido() + ", taxaRendimento=" + this.getTaxaRendimento() + ", dataAplicacao=" + this.getDataAplicacao() + ", dataVencimento=" + this.getDataVencimento() + ", status=" + this.getStatus() + ", rendimentoAtual=" + this.getRendimentoAtual() + ", valorTotal=" + this.getValorTotal() + ", valorLiquido=" + this.getValorLiquido() + ", valorIOF=" + this.getValorIOF() + ", valorIR=" + this.getValorIR() + ", dadosInvestimento=" + this.getDadosInvestimento() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public InvestimentoDTO() {
    }

    /**
     * Creates a new {@code InvestimentoDTO} instance.
     *
     * @param id ID do investimento.
     * @param contaId ID da conta associada.
     * @param contaNumero Número da conta associada.
     * @param tipoInvestimento Tipo do investimento.
     * @param valorInvestido Valor inicialmente investido.
     * @param taxaRendimento Taxa de rendimento (anual/mensal).
     * @param dataAplicacao Data da aplicação inicial.
     * @param dataVencimento Data prevista de vencimento.
     * @param status Status atual do investimento.
     * @param rendimentoAtual Valor acumulado de rendimento.
     * @param valorTotal Valor total (bruto) atual.
     * @param valorLiquido Valor líquido após impostos pagos.
     * @param valorIOF Valor retido de IOF.
     * @param valorIR Valor retido de Imposto de Renda.
     * @param dadosInvestimento Detalhes extras em formato JSON.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public InvestimentoDTO(final Long id, final Long contaId, final String contaNumero, final Investimento.TipoInvestimento tipoInvestimento, final BigDecimal valorInvestido, final BigDecimal taxaRendimento, final LocalDateTime dataAplicacao, final LocalDateTime dataVencimento, final Investimento.StatusInvestimento status, final BigDecimal rendimentoAtual, final BigDecimal valorTotal, final BigDecimal valorLiquido, final BigDecimal valorIOF, final BigDecimal valorIR, final String dadosInvestimento, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.contaId = contaId;
        this.contaNumero = contaNumero;
        this.tipoInvestimento = tipoInvestimento;
        this.valorInvestido = valorInvestido;
        this.taxaRendimento = taxaRendimento;
        this.dataAplicacao = dataAplicacao;
        this.dataVencimento = dataVencimento;
        this.status = status;
        this.rendimentoAtual = rendimentoAtual;
        this.valorTotal = valorTotal;
        this.valorLiquido = valorLiquido;
        this.valorIOF = valorIOF;
        this.valorIR = valorIR;
        this.dadosInvestimento = dadosInvestimento;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
