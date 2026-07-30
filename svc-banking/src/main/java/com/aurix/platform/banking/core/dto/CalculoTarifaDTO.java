package com.aurix.platform.banking.core.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CalculoTarifaDTO {
    private Long contaId;
    private String tipoTarifa;
    private BigDecimal valorTransacao;
    private Integer nivelServico;
    private Boolean pessoaFisica;
    private Boolean pessoaJuridica;
    private Long empresaId;
    private LocalDateTime dataTransacao;
    private String regrasEspeciais;
    private String configuracoesEspeciais;
    // Resultado do cálculo
    private BigDecimal valorTarifa;
    private BigDecimal valorOriginal;
    private BigDecimal percentualDesconto;
    private BigDecimal valorDesconto;
    private String unidadeTarifa;
    private String justificativa;
    private String regrasAplicadas;
    private Boolean aplicavel;
    private String motivoNaoAplicavel;

    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    @java.lang.SuppressWarnings("all")
    public String getTipoTarifa() {
        return this.tipoTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTransacao() {
        return this.valorTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getNivelServico() {
        return this.nivelServico;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPessoaFisica() {
        return this.pessoaFisica;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPessoaJuridica() {
        return this.pessoaJuridica;
    }

    @java.lang.SuppressWarnings("all")
    public Long getEmpresaId() {
        return this.empresaId;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataTransacao() {
        return this.dataTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasEspeciais() {
        return this.regrasEspeciais;
    }

    @java.lang.SuppressWarnings("all")
    public String getConfiguracoesEspeciais() {
        return this.configuracoesEspeciais;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTarifa() {
        return this.valorTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorOriginal() {
        return this.valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualDesconto() {
        return this.percentualDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorDesconto() {
        return this.valorDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public String getUnidadeTarifa() {
        return this.unidadeTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public String getJustificativa() {
        return this.justificativa;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasAplicadas() {
        return this.regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAplicavel() {
        return this.aplicavel;
    }

    @java.lang.SuppressWarnings("all")
    public String getMotivoNaoAplicavel() {
        return this.motivoNaoAplicavel;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoTarifa(final String tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTransacao(final BigDecimal valorTransacao) {
        this.valorTransacao = valorTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelServico(final Integer nivelServico) {
        this.nivelServico = nivelServico;
    }

    @java.lang.SuppressWarnings("all")
    public void setPessoaFisica(final Boolean pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    @java.lang.SuppressWarnings("all")
    public void setPessoaJuridica(final Boolean pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    @java.lang.SuppressWarnings("all")
    public void setEmpresaId(final Long empresaId) {
        this.empresaId = empresaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataTransacao(final LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasEspeciais(final String regrasEspeciais) {
        this.regrasEspeciais = regrasEspeciais;
    }

    @java.lang.SuppressWarnings("all")
    public void setConfiguracoesEspeciais(final String configuracoesEspeciais) {
        this.configuracoesEspeciais = configuracoesEspeciais;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTarifa(final BigDecimal valorTarifa) {
        this.valorTarifa = valorTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorOriginal(final BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualDesconto(final BigDecimal percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorDesconto(final BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public void setUnidadeTarifa(final String unidadeTarifa) {
        this.unidadeTarifa = unidadeTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setJustificativa(final String justificativa) {
        this.justificativa = justificativa;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasAplicadas(final String regrasAplicadas) {
        this.regrasAplicadas = regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setAplicavel(final Boolean aplicavel) {
        this.aplicavel = aplicavel;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivoNaoAplicavel(final String motivoNaoAplicavel) {
        this.motivoNaoAplicavel = motivoNaoAplicavel;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof CalculoTarifaDTO)) return false;
        final CalculoTarifaDTO other = (CalculoTarifaDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$contaId = this.getContaId();
        final java.lang.Object other$contaId = other.getContaId();
        if (this$contaId == null ? other$contaId != null : !this$contaId.equals(other$contaId)) return false;
        final java.lang.Object this$nivelServico = this.getNivelServico();
        final java.lang.Object other$nivelServico = other.getNivelServico();
        if (this$nivelServico == null ? other$nivelServico != null : !this$nivelServico.equals(other$nivelServico)) return false;
        final java.lang.Object this$pessoaFisica = this.getPessoaFisica();
        final java.lang.Object other$pessoaFisica = other.getPessoaFisica();
        if (this$pessoaFisica == null ? other$pessoaFisica != null : !this$pessoaFisica.equals(other$pessoaFisica)) return false;
        final java.lang.Object this$pessoaJuridica = this.getPessoaJuridica();
        final java.lang.Object other$pessoaJuridica = other.getPessoaJuridica();
        if (this$pessoaJuridica == null ? other$pessoaJuridica != null : !this$pessoaJuridica.equals(other$pessoaJuridica)) return false;
        final java.lang.Object this$empresaId = this.getEmpresaId();
        final java.lang.Object other$empresaId = other.getEmpresaId();
        if (this$empresaId == null ? other$empresaId != null : !this$empresaId.equals(other$empresaId)) return false;
        final java.lang.Object this$aplicavel = this.getAplicavel();
        final java.lang.Object other$aplicavel = other.getAplicavel();
        if (this$aplicavel == null ? other$aplicavel != null : !this$aplicavel.equals(other$aplicavel)) return false;
        final java.lang.Object this$tipoTarifa = this.getTipoTarifa();
        final java.lang.Object other$tipoTarifa = other.getTipoTarifa();
        if (this$tipoTarifa == null ? other$tipoTarifa != null : !this$tipoTarifa.equals(other$tipoTarifa)) return false;
        final java.lang.Object this$valorTransacao = this.getValorTransacao();
        final java.lang.Object other$valorTransacao = other.getValorTransacao();
        if (this$valorTransacao == null ? other$valorTransacao != null : !this$valorTransacao.equals(other$valorTransacao)) return false;
        final java.lang.Object this$dataTransacao = this.getDataTransacao();
        final java.lang.Object other$dataTransacao = other.getDataTransacao();
        if (this$dataTransacao == null ? other$dataTransacao != null : !this$dataTransacao.equals(other$dataTransacao)) return false;
        final java.lang.Object this$regrasEspeciais = this.getRegrasEspeciais();
        final java.lang.Object other$regrasEspeciais = other.getRegrasEspeciais();
        if (this$regrasEspeciais == null ? other$regrasEspeciais != null : !this$regrasEspeciais.equals(other$regrasEspeciais)) return false;
        final java.lang.Object this$configuracoesEspeciais = this.getConfiguracoesEspeciais();
        final java.lang.Object other$configuracoesEspeciais = other.getConfiguracoesEspeciais();
        if (this$configuracoesEspeciais == null ? other$configuracoesEspeciais != null : !this$configuracoesEspeciais.equals(other$configuracoesEspeciais)) return false;
        final java.lang.Object this$valorTarifa = this.getValorTarifa();
        final java.lang.Object other$valorTarifa = other.getValorTarifa();
        if (this$valorTarifa == null ? other$valorTarifa != null : !this$valorTarifa.equals(other$valorTarifa)) return false;
        final java.lang.Object this$valorOriginal = this.getValorOriginal();
        final java.lang.Object other$valorOriginal = other.getValorOriginal();
        if (this$valorOriginal == null ? other$valorOriginal != null : !this$valorOriginal.equals(other$valorOriginal)) return false;
        final java.lang.Object this$percentualDesconto = this.getPercentualDesconto();
        final java.lang.Object other$percentualDesconto = other.getPercentualDesconto();
        if (this$percentualDesconto == null ? other$percentualDesconto != null : !this$percentualDesconto.equals(other$percentualDesconto)) return false;
        final java.lang.Object this$valorDesconto = this.getValorDesconto();
        final java.lang.Object other$valorDesconto = other.getValorDesconto();
        if (this$valorDesconto == null ? other$valorDesconto != null : !this$valorDesconto.equals(other$valorDesconto)) return false;
        final java.lang.Object this$unidadeTarifa = this.getUnidadeTarifa();
        final java.lang.Object other$unidadeTarifa = other.getUnidadeTarifa();
        if (this$unidadeTarifa == null ? other$unidadeTarifa != null : !this$unidadeTarifa.equals(other$unidadeTarifa)) return false;
        final java.lang.Object this$justificativa = this.getJustificativa();
        final java.lang.Object other$justificativa = other.getJustificativa();
        if (this$justificativa == null ? other$justificativa != null : !this$justificativa.equals(other$justificativa)) return false;
        final java.lang.Object this$regrasAplicadas = this.getRegrasAplicadas();
        final java.lang.Object other$regrasAplicadas = other.getRegrasAplicadas();
        if (this$regrasAplicadas == null ? other$regrasAplicadas != null : !this$regrasAplicadas.equals(other$regrasAplicadas)) return false;
        final java.lang.Object this$motivoNaoAplicavel = this.getMotivoNaoAplicavel();
        final java.lang.Object other$motivoNaoAplicavel = other.getMotivoNaoAplicavel();
        if (this$motivoNaoAplicavel == null ? other$motivoNaoAplicavel != null : !this$motivoNaoAplicavel.equals(other$motivoNaoAplicavel)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof CalculoTarifaDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $contaId = this.getContaId();
        result = result * PRIME + ($contaId == null ? 43 : $contaId.hashCode());
        final java.lang.Object $nivelServico = this.getNivelServico();
        result = result * PRIME + ($nivelServico == null ? 43 : $nivelServico.hashCode());
        final java.lang.Object $pessoaFisica = this.getPessoaFisica();
        result = result * PRIME + ($pessoaFisica == null ? 43 : $pessoaFisica.hashCode());
        final java.lang.Object $pessoaJuridica = this.getPessoaJuridica();
        result = result * PRIME + ($pessoaJuridica == null ? 43 : $pessoaJuridica.hashCode());
        final java.lang.Object $empresaId = this.getEmpresaId();
        result = result * PRIME + ($empresaId == null ? 43 : $empresaId.hashCode());
        final java.lang.Object $aplicavel = this.getAplicavel();
        result = result * PRIME + ($aplicavel == null ? 43 : $aplicavel.hashCode());
        final java.lang.Object $tipoTarifa = this.getTipoTarifa();
        result = result * PRIME + ($tipoTarifa == null ? 43 : $tipoTarifa.hashCode());
        final java.lang.Object $valorTransacao = this.getValorTransacao();
        result = result * PRIME + ($valorTransacao == null ? 43 : $valorTransacao.hashCode());
        final java.lang.Object $dataTransacao = this.getDataTransacao();
        result = result * PRIME + ($dataTransacao == null ? 43 : $dataTransacao.hashCode());
        final java.lang.Object $regrasEspeciais = this.getRegrasEspeciais();
        result = result * PRIME + ($regrasEspeciais == null ? 43 : $regrasEspeciais.hashCode());
        final java.lang.Object $configuracoesEspeciais = this.getConfiguracoesEspeciais();
        result = result * PRIME + ($configuracoesEspeciais == null ? 43 : $configuracoesEspeciais.hashCode());
        final java.lang.Object $valorTarifa = this.getValorTarifa();
        result = result * PRIME + ($valorTarifa == null ? 43 : $valorTarifa.hashCode());
        final java.lang.Object $valorOriginal = this.getValorOriginal();
        result = result * PRIME + ($valorOriginal == null ? 43 : $valorOriginal.hashCode());
        final java.lang.Object $percentualDesconto = this.getPercentualDesconto();
        result = result * PRIME + ($percentualDesconto == null ? 43 : $percentualDesconto.hashCode());
        final java.lang.Object $valorDesconto = this.getValorDesconto();
        result = result * PRIME + ($valorDesconto == null ? 43 : $valorDesconto.hashCode());
        final java.lang.Object $unidadeTarifa = this.getUnidadeTarifa();
        result = result * PRIME + ($unidadeTarifa == null ? 43 : $unidadeTarifa.hashCode());
        final java.lang.Object $justificativa = this.getJustificativa();
        result = result * PRIME + ($justificativa == null ? 43 : $justificativa.hashCode());
        final java.lang.Object $regrasAplicadas = this.getRegrasAplicadas();
        result = result * PRIME + ($regrasAplicadas == null ? 43 : $regrasAplicadas.hashCode());
        final java.lang.Object $motivoNaoAplicavel = this.getMotivoNaoAplicavel();
        result = result * PRIME + ($motivoNaoAplicavel == null ? 43 : $motivoNaoAplicavel.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "CalculoTarifaDTO(contaId=" + this.getContaId() + ", tipoTarifa=" + this.getTipoTarifa() + ", valorTransacao=" + this.getValorTransacao() + ", nivelServico=" + this.getNivelServico() + ", pessoaFisica=" + this.getPessoaFisica() + ", pessoaJuridica=" + this.getPessoaJuridica() + ", empresaId=" + this.getEmpresaId() + ", dataTransacao=" + this.getDataTransacao() + ", regrasEspeciais=" + this.getRegrasEspeciais() + ", configuracoesEspeciais=" + this.getConfiguracoesEspeciais() + ", valorTarifa=" + this.getValorTarifa() + ", valorOriginal=" + this.getValorOriginal() + ", percentualDesconto=" + this.getPercentualDesconto() + ", valorDesconto=" + this.getValorDesconto() + ", unidadeTarifa=" + this.getUnidadeTarifa() + ", justificativa=" + this.getJustificativa() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", aplicavel=" + this.getAplicavel() + ", motivoNaoAplicavel=" + this.getMotivoNaoAplicavel() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public CalculoTarifaDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public CalculoTarifaDTO(final Long contaId, final String tipoTarifa, final BigDecimal valorTransacao, final Integer nivelServico, final Boolean pessoaFisica, final Boolean pessoaJuridica, final Long empresaId, final LocalDateTime dataTransacao, final String regrasEspeciais, final String configuracoesEspeciais, final BigDecimal valorTarifa, final BigDecimal valorOriginal, final BigDecimal percentualDesconto, final BigDecimal valorDesconto, final String unidadeTarifa, final String justificativa, final String regrasAplicadas, final Boolean aplicavel, final String motivoNaoAplicavel) {
        this.contaId = contaId;
        this.tipoTarifa = tipoTarifa;
        this.valorTransacao = valorTransacao;
        this.nivelServico = nivelServico;
        this.pessoaFisica = pessoaFisica;
        this.pessoaJuridica = pessoaJuridica;
        this.empresaId = empresaId;
        this.dataTransacao = dataTransacao;
        this.regrasEspeciais = regrasEspeciais;
        this.configuracoesEspeciais = configuracoesEspeciais;
        this.valorTarifa = valorTarifa;
        this.valorOriginal = valorOriginal;
        this.percentualDesconto = percentualDesconto;
        this.valorDesconto = valorDesconto;
        this.unidadeTarifa = unidadeTarifa;
        this.justificativa = justificativa;
        this.regrasAplicadas = regrasAplicadas;
        this.aplicavel = aplicavel;
        this.motivoNaoAplicavel = motivoNaoAplicavel;
    }
}
