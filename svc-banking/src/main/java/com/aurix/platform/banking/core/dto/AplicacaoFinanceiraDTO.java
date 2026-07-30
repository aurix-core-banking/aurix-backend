package com.aurix.platform.banking.core.dto;

import com.aurix.platform.banking.core.entity.AplicacaoFinanceira;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AplicacaoFinanceiraDTO {
    private Long id;
    private String codigoAplicacao;
    private Long contaId;
    private Long produtoFinanceiroId;
    private AplicacaoFinanceira.StatusAplicacao status;
    private BigDecimal valorAplicado;
    private BigDecimal valorAtual;
    private BigDecimal valorResgate;
    private BigDecimal valorRendimento;
    private BigDecimal taxaRemuneracao;
    private LocalDateTime dataAplicacao;
    private LocalDateTime dataVencimento;
    private LocalDateTime dataResgate;

    public static AplicacaoFinanceiraDTO fromEntity(AplicacaoFinanceira e) {
        if (e == null) return null;
        AplicacaoFinanceiraDTO dto = new AplicacaoFinanceiraDTO();
        dto.setId(e.getId());
        dto.setCodigoAplicacao(e.getCodigoAplicacao());
        dto.setContaId(e.getConta() != null ? e.getConta().getId() : null);
        dto.setProdutoFinanceiroId(e.getProdutoFinanceiro() != null ? e.getProdutoFinanceiro().getId() : null);
        dto.setStatus(e.getStatus());
        dto.setValorAplicado(e.getValorAplicacao());
        dto.setValorAtual(e.getValorAtual());
        dto.setValorResgate(e.getValorResgate());
        dto.setValorRendimento(e.getValorRendimento());
        dto.setTaxaRemuneracao(e.getTaxaRemuneracao());
        dto.setDataAplicacao(e.getDataAplicacao());
        dto.setDataVencimento(e.getDataVencimento());
        dto.setDataResgate(e.getDataResgate());
        return dto;
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoAplicacao() {
        return this.codigoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getProdutoFinanceiroId() {
        return this.produtoFinanceiroId;
    }

    @java.lang.SuppressWarnings("all")
    public AplicacaoFinanceira.StatusAplicacao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAplicado() {
        return this.valorAplicado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAtual() {
        return this.valorAtual;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorResgate() {
        return this.valorResgate;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorRendimento() {
        return this.valorRendimento;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaRemuneracao() {
        return this.taxaRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAplicacao() {
        return this.dataAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataResgate() {
        return this.dataResgate;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoAplicacao(final String codigoAplicacao) {
        this.codigoAplicacao = codigoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setProdutoFinanceiroId(final Long produtoFinanceiroId) {
        this.produtoFinanceiroId = produtoFinanceiroId;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final AplicacaoFinanceira.StatusAplicacao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorAplicado(final BigDecimal valorAplicado) {
        this.valorAplicado = valorAplicado;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorAtual(final BigDecimal valorAtual) {
        this.valorAtual = valorAtual;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorResgate(final BigDecimal valorResgate) {
        this.valorResgate = valorResgate;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorRendimento(final BigDecimal valorRendimento) {
        this.valorRendimento = valorRendimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaRemuneracao(final BigDecimal taxaRemuneracao) {
        this.taxaRemuneracao = taxaRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAplicacao(final LocalDateTime dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataResgate(final LocalDateTime dataResgate) {
        this.dataResgate = dataResgate;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AplicacaoFinanceiraDTO)) return false;
        final AplicacaoFinanceiraDTO other = (AplicacaoFinanceiraDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$contaId = this.getContaId();
        final java.lang.Object other$contaId = other.getContaId();
        if (this$contaId == null ? other$contaId != null : !this$contaId.equals(other$contaId)) return false;
        final java.lang.Object this$produtoFinanceiroId = this.getProdutoFinanceiroId();
        final java.lang.Object other$produtoFinanceiroId = other.getProdutoFinanceiroId();
        if (this$produtoFinanceiroId == null ? other$produtoFinanceiroId != null : !this$produtoFinanceiroId.equals(other$produtoFinanceiroId)) return false;
        final java.lang.Object this$codigoAplicacao = this.getCodigoAplicacao();
        final java.lang.Object other$codigoAplicacao = other.getCodigoAplicacao();
        if (this$codigoAplicacao == null ? other$codigoAplicacao != null : !this$codigoAplicacao.equals(other$codigoAplicacao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$valorAplicado = this.getValorAplicado();
        final java.lang.Object other$valorAplicado = other.getValorAplicado();
        if (this$valorAplicado == null ? other$valorAplicado != null : !this$valorAplicado.equals(other$valorAplicado)) return false;
        final java.lang.Object this$valorAtual = this.getValorAtual();
        final java.lang.Object other$valorAtual = other.getValorAtual();
        if (this$valorAtual == null ? other$valorAtual != null : !this$valorAtual.equals(other$valorAtual)) return false;
        final java.lang.Object this$valorResgate = this.getValorResgate();
        final java.lang.Object other$valorResgate = other.getValorResgate();
        if (this$valorResgate == null ? other$valorResgate != null : !this$valorResgate.equals(other$valorResgate)) return false;
        final java.lang.Object this$valorRendimento = this.getValorRendimento();
        final java.lang.Object other$valorRendimento = other.getValorRendimento();
        if (this$valorRendimento == null ? other$valorRendimento != null : !this$valorRendimento.equals(other$valorRendimento)) return false;
        final java.lang.Object this$taxaRemuneracao = this.getTaxaRemuneracao();
        final java.lang.Object other$taxaRemuneracao = other.getTaxaRemuneracao();
        if (this$taxaRemuneracao == null ? other$taxaRemuneracao != null : !this$taxaRemuneracao.equals(other$taxaRemuneracao)) return false;
        final java.lang.Object this$dataAplicacao = this.getDataAplicacao();
        final java.lang.Object other$dataAplicacao = other.getDataAplicacao();
        if (this$dataAplicacao == null ? other$dataAplicacao != null : !this$dataAplicacao.equals(other$dataAplicacao)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$dataResgate = this.getDataResgate();
        final java.lang.Object other$dataResgate = other.getDataResgate();
        if (this$dataResgate == null ? other$dataResgate != null : !this$dataResgate.equals(other$dataResgate)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof AplicacaoFinanceiraDTO;
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
        final java.lang.Object $produtoFinanceiroId = this.getProdutoFinanceiroId();
        result = result * PRIME + ($produtoFinanceiroId == null ? 43 : $produtoFinanceiroId.hashCode());
        final java.lang.Object $codigoAplicacao = this.getCodigoAplicacao();
        result = result * PRIME + ($codigoAplicacao == null ? 43 : $codigoAplicacao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $valorAplicado = this.getValorAplicado();
        result = result * PRIME + ($valorAplicado == null ? 43 : $valorAplicado.hashCode());
        final java.lang.Object $valorAtual = this.getValorAtual();
        result = result * PRIME + ($valorAtual == null ? 43 : $valorAtual.hashCode());
        final java.lang.Object $valorResgate = this.getValorResgate();
        result = result * PRIME + ($valorResgate == null ? 43 : $valorResgate.hashCode());
        final java.lang.Object $valorRendimento = this.getValorRendimento();
        result = result * PRIME + ($valorRendimento == null ? 43 : $valorRendimento.hashCode());
        final java.lang.Object $taxaRemuneracao = this.getTaxaRemuneracao();
        result = result * PRIME + ($taxaRemuneracao == null ? 43 : $taxaRemuneracao.hashCode());
        final java.lang.Object $dataAplicacao = this.getDataAplicacao();
        result = result * PRIME + ($dataAplicacao == null ? 43 : $dataAplicacao.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $dataResgate = this.getDataResgate();
        result = result * PRIME + ($dataResgate == null ? 43 : $dataResgate.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "AplicacaoFinanceiraDTO(id=" + this.getId() + ", codigoAplicacao=" + this.getCodigoAplicacao() + ", contaId=" + this.getContaId() + ", produtoFinanceiroId=" + this.getProdutoFinanceiroId() + ", status=" + this.getStatus() + ", valorAplicado=" + this.getValorAplicado() + ", valorAtual=" + this.getValorAtual() + ", valorResgate=" + this.getValorResgate() + ", valorRendimento=" + this.getValorRendimento() + ", taxaRemuneracao=" + this.getTaxaRemuneracao() + ", dataAplicacao=" + this.getDataAplicacao() + ", dataVencimento=" + this.getDataVencimento() + ", dataResgate=" + this.getDataResgate() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public AplicacaoFinanceiraDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public AplicacaoFinanceiraDTO(final Long id, final String codigoAplicacao, final Long contaId, final Long produtoFinanceiroId, final AplicacaoFinanceira.StatusAplicacao status, final BigDecimal valorAplicado, final BigDecimal valorAtual, final BigDecimal valorResgate, final BigDecimal valorRendimento, final BigDecimal taxaRemuneracao, final LocalDateTime dataAplicacao, final LocalDateTime dataVencimento, final LocalDateTime dataResgate) {
        this.id = id;
        this.codigoAplicacao = codigoAplicacao;
        this.contaId = contaId;
        this.produtoFinanceiroId = produtoFinanceiroId;
        this.status = status;
        this.valorAplicado = valorAplicado;
        this.valorAtual = valorAtual;
        this.valorResgate = valorResgate;
        this.valorRendimento = valorRendimento;
        this.taxaRemuneracao = taxaRemuneracao;
        this.dataAplicacao = dataAplicacao;
        this.dataVencimento = dataVencimento;
        this.dataResgate = dataResgate;
    }
}
