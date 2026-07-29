package com.aurix.platform.banking.core.dto;

import com.aurix.platform.banking.core.entity.Tarifa;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TarifaDTO {
    private Long id;
    private String codigoTarifa;
    private String nomeTarifa;
    private String descricao;
    private String tipoTarifa;
    private String categoriaTarifa;
    private BigDecimal valorBase;
    private BigDecimal valorMinimo;
    private BigDecimal valorMaximo;
    private BigDecimal percentualBase;
    private BigDecimal percentualMinimo;
    private BigDecimal percentualMaximo;
    private String unidadeTarifa;
    private Integer nivelServico;
    private Boolean ativa;
    private Boolean aplicavelPessoaFisica;
    private Boolean aplicavelPessoaJuridica;
    private String regrasAplicacao;
    private String configuracoesEspeciais;
    private LocalDateTime dataInicioVigencia;
    private LocalDateTime dataFimVigencia;
    private Long empresaId;

    public static TarifaDTO fromEntity(Tarifa tarifa) {
        TarifaDTO dto = new TarifaDTO();
        dto.setId(tarifa.getId());
        dto.setCodigoTarifa(tarifa.getCodigoTarifa());
        dto.setNomeTarifa(tarifa.getNomeTarifa());
        dto.setDescricao(tarifa.getDescricao());
        dto.setTipoTarifa(tarifa.getTipoTarifa().name());
        dto.setCategoriaTarifa(tarifa.getCategoriaTarifa().name());
        dto.setValorBase(tarifa.getValorBase());
        dto.setValorMinimo(tarifa.getValorMinimo());
        dto.setValorMaximo(tarifa.getValorMaximo());
        dto.setPercentualBase(tarifa.getPercentualBase());
        dto.setPercentualMinimo(tarifa.getPercentualMinimo());
        dto.setPercentualMaximo(tarifa.getPercentualMaximo());
        dto.setUnidadeTarifa(tarifa.getUnidadeTarifa().name());
        dto.setNivelServico(tarifa.getNivelServico());
        dto.setAtiva(tarifa.getAtiva());
        dto.setAplicavelPessoaFisica(tarifa.getAplicavelPessoaFisica());
        dto.setAplicavelPessoaJuridica(tarifa.getAplicavelPessoaJuridica());
        dto.setRegrasAplicacao(tarifa.getRegrasAplicacao());
        dto.setConfiguracoesEspeciais(tarifa.getConfiguracoesEspeciais());
        dto.setDataInicioVigencia(tarifa.getDataInicioVigencia());
        dto.setDataFimVigencia(tarifa.getDataFimVigencia());
        dto.setEmpresaId(tarifa.getEmpresa() != null ? tarifa.getEmpresa().getId() : null);
        return dto;
    }

    public Tarifa toEntity() {
        Tarifa tarifa = new Tarifa();
        tarifa.setId(this.id);
        tarifa.setCodigoTarifa(this.codigoTarifa);
        tarifa.setNomeTarifa(this.nomeTarifa);
        tarifa.setDescricao(this.descricao);
        tarifa.setTipoTarifa(Tarifa.TipoTarifa.valueOf(this.tipoTarifa));
        tarifa.setCategoriaTarifa(Tarifa.CategoriaTarifa.valueOf(this.categoriaTarifa));
        tarifa.setValorBase(this.valorBase);
        tarifa.setValorMinimo(this.valorMinimo);
        tarifa.setValorMaximo(this.valorMaximo);
        tarifa.setPercentualBase(this.percentualBase);
        tarifa.setPercentualMinimo(this.percentualMinimo);
        tarifa.setPercentualMaximo(this.percentualMaximo);
        tarifa.setUnidadeTarifa(Tarifa.UnidadeTarifa.valueOf(this.unidadeTarifa));
        tarifa.setNivelServico(this.nivelServico);
        tarifa.setAtiva(this.ativa);
        tarifa.setAplicavelPessoaFisica(this.aplicavelPessoaFisica);
        tarifa.setAplicavelPessoaJuridica(this.aplicavelPessoaJuridica);
        tarifa.setRegrasAplicacao(this.regrasAplicacao);
        tarifa.setConfiguracoesEspeciais(this.configuracoesEspeciais);
        tarifa.setDataInicioVigencia(this.dataInicioVigencia);
        tarifa.setDataFimVigencia(this.dataFimVigencia);
        return tarifa;
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoTarifa() {
        return this.codigoTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeTarifa() {
        return this.nomeTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public String getTipoTarifa() {
        return this.tipoTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public String getCategoriaTarifa() {
        return this.categoriaTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorBase() {
        return this.valorBase;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMinimo() {
        return this.valorMinimo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMaximo() {
        return this.valorMaximo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualBase() {
        return this.percentualBase;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualMinimo() {
        return this.percentualMinimo;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getPercentualMaximo() {
        return this.percentualMaximo;
    }

    @java.lang.SuppressWarnings("all")
    public String getUnidadeTarifa() {
        return this.unidadeTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getNivelServico() {
        return this.nivelServico;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtiva() {
        return this.ativa;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAplicavelPessoaFisica() {
        return this.aplicavelPessoaFisica;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAplicavelPessoaJuridica() {
        return this.aplicavelPessoaJuridica;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasAplicacao() {
        return this.regrasAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getConfiguracoesEspeciais() {
        return this.configuracoesEspeciais;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataInicioVigencia() {
        return this.dataInicioVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFimVigencia() {
        return this.dataFimVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public Long getEmpresaId() {
        return this.empresaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoTarifa(final String codigoTarifa) {
        this.codigoTarifa = codigoTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeTarifa(final String nomeTarifa) {
        this.nomeTarifa = nomeTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoTarifa(final String tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoriaTarifa(final String categoriaTarifa) {
        this.categoriaTarifa = categoriaTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorBase(final BigDecimal valorBase) {
        this.valorBase = valorBase;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMinimo(final BigDecimal valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMaximo(final BigDecimal valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualBase(final BigDecimal percentualBase) {
        this.percentualBase = percentualBase;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualMinimo(final BigDecimal percentualMinimo) {
        this.percentualMinimo = percentualMinimo;
    }

    @java.lang.SuppressWarnings("all")
    public void setPercentualMaximo(final BigDecimal percentualMaximo) {
        this.percentualMaximo = percentualMaximo;
    }

    @java.lang.SuppressWarnings("all")
    public void setUnidadeTarifa(final String unidadeTarifa) {
        this.unidadeTarifa = unidadeTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelServico(final Integer nivelServico) {
        this.nivelServico = nivelServico;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtiva(final Boolean ativa) {
        this.ativa = ativa;
    }

    @java.lang.SuppressWarnings("all")
    public void setAplicavelPessoaFisica(final Boolean aplicavelPessoaFisica) {
        this.aplicavelPessoaFisica = aplicavelPessoaFisica;
    }

    @java.lang.SuppressWarnings("all")
    public void setAplicavelPessoaJuridica(final Boolean aplicavelPessoaJuridica) {
        this.aplicavelPessoaJuridica = aplicavelPessoaJuridica;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasAplicacao(final String regrasAplicacao) {
        this.regrasAplicacao = regrasAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setConfiguracoesEspeciais(final String configuracoesEspeciais) {
        this.configuracoesEspeciais = configuracoesEspeciais;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataInicioVigencia(final LocalDateTime dataInicioVigencia) {
        this.dataInicioVigencia = dataInicioVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataFimVigencia(final LocalDateTime dataFimVigencia) {
        this.dataFimVigencia = dataFimVigencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setEmpresaId(final Long empresaId) {
        this.empresaId = empresaId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof TarifaDTO)) return false;
        final TarifaDTO other = (TarifaDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$nivelServico = this.getNivelServico();
        final java.lang.Object other$nivelServico = other.getNivelServico();
        if (this$nivelServico == null ? other$nivelServico != null : !this$nivelServico.equals(other$nivelServico)) return false;
        final java.lang.Object this$ativa = this.getAtiva();
        final java.lang.Object other$ativa = other.getAtiva();
        if (this$ativa == null ? other$ativa != null : !this$ativa.equals(other$ativa)) return false;
        final java.lang.Object this$aplicavelPessoaFisica = this.getAplicavelPessoaFisica();
        final java.lang.Object other$aplicavelPessoaFisica = other.getAplicavelPessoaFisica();
        if (this$aplicavelPessoaFisica == null ? other$aplicavelPessoaFisica != null : !this$aplicavelPessoaFisica.equals(other$aplicavelPessoaFisica)) return false;
        final java.lang.Object this$aplicavelPessoaJuridica = this.getAplicavelPessoaJuridica();
        final java.lang.Object other$aplicavelPessoaJuridica = other.getAplicavelPessoaJuridica();
        if (this$aplicavelPessoaJuridica == null ? other$aplicavelPessoaJuridica != null : !this$aplicavelPessoaJuridica.equals(other$aplicavelPessoaJuridica)) return false;
        final java.lang.Object this$empresaId = this.getEmpresaId();
        final java.lang.Object other$empresaId = other.getEmpresaId();
        if (this$empresaId == null ? other$empresaId != null : !this$empresaId.equals(other$empresaId)) return false;
        final java.lang.Object this$codigoTarifa = this.getCodigoTarifa();
        final java.lang.Object other$codigoTarifa = other.getCodigoTarifa();
        if (this$codigoTarifa == null ? other$codigoTarifa != null : !this$codigoTarifa.equals(other$codigoTarifa)) return false;
        final java.lang.Object this$nomeTarifa = this.getNomeTarifa();
        final java.lang.Object other$nomeTarifa = other.getNomeTarifa();
        if (this$nomeTarifa == null ? other$nomeTarifa != null : !this$nomeTarifa.equals(other$nomeTarifa)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoTarifa = this.getTipoTarifa();
        final java.lang.Object other$tipoTarifa = other.getTipoTarifa();
        if (this$tipoTarifa == null ? other$tipoTarifa != null : !this$tipoTarifa.equals(other$tipoTarifa)) return false;
        final java.lang.Object this$categoriaTarifa = this.getCategoriaTarifa();
        final java.lang.Object other$categoriaTarifa = other.getCategoriaTarifa();
        if (this$categoriaTarifa == null ? other$categoriaTarifa != null : !this$categoriaTarifa.equals(other$categoriaTarifa)) return false;
        final java.lang.Object this$valorBase = this.getValorBase();
        final java.lang.Object other$valorBase = other.getValorBase();
        if (this$valorBase == null ? other$valorBase != null : !this$valorBase.equals(other$valorBase)) return false;
        final java.lang.Object this$valorMinimo = this.getValorMinimo();
        final java.lang.Object other$valorMinimo = other.getValorMinimo();
        if (this$valorMinimo == null ? other$valorMinimo != null : !this$valorMinimo.equals(other$valorMinimo)) return false;
        final java.lang.Object this$valorMaximo = this.getValorMaximo();
        final java.lang.Object other$valorMaximo = other.getValorMaximo();
        if (this$valorMaximo == null ? other$valorMaximo != null : !this$valorMaximo.equals(other$valorMaximo)) return false;
        final java.lang.Object this$percentualBase = this.getPercentualBase();
        final java.lang.Object other$percentualBase = other.getPercentualBase();
        if (this$percentualBase == null ? other$percentualBase != null : !this$percentualBase.equals(other$percentualBase)) return false;
        final java.lang.Object this$percentualMinimo = this.getPercentualMinimo();
        final java.lang.Object other$percentualMinimo = other.getPercentualMinimo();
        if (this$percentualMinimo == null ? other$percentualMinimo != null : !this$percentualMinimo.equals(other$percentualMinimo)) return false;
        final java.lang.Object this$percentualMaximo = this.getPercentualMaximo();
        final java.lang.Object other$percentualMaximo = other.getPercentualMaximo();
        if (this$percentualMaximo == null ? other$percentualMaximo != null : !this$percentualMaximo.equals(other$percentualMaximo)) return false;
        final java.lang.Object this$unidadeTarifa = this.getUnidadeTarifa();
        final java.lang.Object other$unidadeTarifa = other.getUnidadeTarifa();
        if (this$unidadeTarifa == null ? other$unidadeTarifa != null : !this$unidadeTarifa.equals(other$unidadeTarifa)) return false;
        final java.lang.Object this$regrasAplicacao = this.getRegrasAplicacao();
        final java.lang.Object other$regrasAplicacao = other.getRegrasAplicacao();
        if (this$regrasAplicacao == null ? other$regrasAplicacao != null : !this$regrasAplicacao.equals(other$regrasAplicacao)) return false;
        final java.lang.Object this$configuracoesEspeciais = this.getConfiguracoesEspeciais();
        final java.lang.Object other$configuracoesEspeciais = other.getConfiguracoesEspeciais();
        if (this$configuracoesEspeciais == null ? other$configuracoesEspeciais != null : !this$configuracoesEspeciais.equals(other$configuracoesEspeciais)) return false;
        final java.lang.Object this$dataInicioVigencia = this.getDataInicioVigencia();
        final java.lang.Object other$dataInicioVigencia = other.getDataInicioVigencia();
        if (this$dataInicioVigencia == null ? other$dataInicioVigencia != null : !this$dataInicioVigencia.equals(other$dataInicioVigencia)) return false;
        final java.lang.Object this$dataFimVigencia = this.getDataFimVigencia();
        final java.lang.Object other$dataFimVigencia = other.getDataFimVigencia();
        if (this$dataFimVigencia == null ? other$dataFimVigencia != null : !this$dataFimVigencia.equals(other$dataFimVigencia)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof TarifaDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $nivelServico = this.getNivelServico();
        result = result * PRIME + ($nivelServico == null ? 43 : $nivelServico.hashCode());
        final java.lang.Object $ativa = this.getAtiva();
        result = result * PRIME + ($ativa == null ? 43 : $ativa.hashCode());
        final java.lang.Object $aplicavelPessoaFisica = this.getAplicavelPessoaFisica();
        result = result * PRIME + ($aplicavelPessoaFisica == null ? 43 : $aplicavelPessoaFisica.hashCode());
        final java.lang.Object $aplicavelPessoaJuridica = this.getAplicavelPessoaJuridica();
        result = result * PRIME + ($aplicavelPessoaJuridica == null ? 43 : $aplicavelPessoaJuridica.hashCode());
        final java.lang.Object $empresaId = this.getEmpresaId();
        result = result * PRIME + ($empresaId == null ? 43 : $empresaId.hashCode());
        final java.lang.Object $codigoTarifa = this.getCodigoTarifa();
        result = result * PRIME + ($codigoTarifa == null ? 43 : $codigoTarifa.hashCode());
        final java.lang.Object $nomeTarifa = this.getNomeTarifa();
        result = result * PRIME + ($nomeTarifa == null ? 43 : $nomeTarifa.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoTarifa = this.getTipoTarifa();
        result = result * PRIME + ($tipoTarifa == null ? 43 : $tipoTarifa.hashCode());
        final java.lang.Object $categoriaTarifa = this.getCategoriaTarifa();
        result = result * PRIME + ($categoriaTarifa == null ? 43 : $categoriaTarifa.hashCode());
        final java.lang.Object $valorBase = this.getValorBase();
        result = result * PRIME + ($valorBase == null ? 43 : $valorBase.hashCode());
        final java.lang.Object $valorMinimo = this.getValorMinimo();
        result = result * PRIME + ($valorMinimo == null ? 43 : $valorMinimo.hashCode());
        final java.lang.Object $valorMaximo = this.getValorMaximo();
        result = result * PRIME + ($valorMaximo == null ? 43 : $valorMaximo.hashCode());
        final java.lang.Object $percentualBase = this.getPercentualBase();
        result = result * PRIME + ($percentualBase == null ? 43 : $percentualBase.hashCode());
        final java.lang.Object $percentualMinimo = this.getPercentualMinimo();
        result = result * PRIME + ($percentualMinimo == null ? 43 : $percentualMinimo.hashCode());
        final java.lang.Object $percentualMaximo = this.getPercentualMaximo();
        result = result * PRIME + ($percentualMaximo == null ? 43 : $percentualMaximo.hashCode());
        final java.lang.Object $unidadeTarifa = this.getUnidadeTarifa();
        result = result * PRIME + ($unidadeTarifa == null ? 43 : $unidadeTarifa.hashCode());
        final java.lang.Object $regrasAplicacao = this.getRegrasAplicacao();
        result = result * PRIME + ($regrasAplicacao == null ? 43 : $regrasAplicacao.hashCode());
        final java.lang.Object $configuracoesEspeciais = this.getConfiguracoesEspeciais();
        result = result * PRIME + ($configuracoesEspeciais == null ? 43 : $configuracoesEspeciais.hashCode());
        final java.lang.Object $dataInicioVigencia = this.getDataInicioVigencia();
        result = result * PRIME + ($dataInicioVigencia == null ? 43 : $dataInicioVigencia.hashCode());
        final java.lang.Object $dataFimVigencia = this.getDataFimVigencia();
        result = result * PRIME + ($dataFimVigencia == null ? 43 : $dataFimVigencia.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "TarifaDTO(id=" + this.getId() + ", codigoTarifa=" + this.getCodigoTarifa() + ", nomeTarifa=" + this.getNomeTarifa() + ", descricao=" + this.getDescricao() + ", tipoTarifa=" + this.getTipoTarifa() + ", categoriaTarifa=" + this.getCategoriaTarifa() + ", valorBase=" + this.getValorBase() + ", valorMinimo=" + this.getValorMinimo() + ", valorMaximo=" + this.getValorMaximo() + ", percentualBase=" + this.getPercentualBase() + ", percentualMinimo=" + this.getPercentualMinimo() + ", percentualMaximo=" + this.getPercentualMaximo() + ", unidadeTarifa=" + this.getUnidadeTarifa() + ", nivelServico=" + this.getNivelServico() + ", ativa=" + this.getAtiva() + ", aplicavelPessoaFisica=" + this.getAplicavelPessoaFisica() + ", aplicavelPessoaJuridica=" + this.getAplicavelPessoaJuridica() + ", regrasAplicacao=" + this.getRegrasAplicacao() + ", configuracoesEspeciais=" + this.getConfiguracoesEspeciais() + ", dataInicioVigencia=" + this.getDataInicioVigencia() + ", dataFimVigencia=" + this.getDataFimVigencia() + ", empresaId=" + this.getEmpresaId() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public TarifaDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public TarifaDTO(final Long id, final String codigoTarifa, final String nomeTarifa, final String descricao, final String tipoTarifa, final String categoriaTarifa, final BigDecimal valorBase, final BigDecimal valorMinimo, final BigDecimal valorMaximo, final BigDecimal percentualBase, final BigDecimal percentualMinimo, final BigDecimal percentualMaximo, final String unidadeTarifa, final Integer nivelServico, final Boolean ativa, final Boolean aplicavelPessoaFisica, final Boolean aplicavelPessoaJuridica, final String regrasAplicacao, final String configuracoesEspeciais, final LocalDateTime dataInicioVigencia, final LocalDateTime dataFimVigencia, final Long empresaId) {
        this.id = id;
        this.codigoTarifa = codigoTarifa;
        this.nomeTarifa = nomeTarifa;
        this.descricao = descricao;
        this.tipoTarifa = tipoTarifa;
        this.categoriaTarifa = categoriaTarifa;
        this.valorBase = valorBase;
        this.valorMinimo = valorMinimo;
        this.valorMaximo = valorMaximo;
        this.percentualBase = percentualBase;
        this.percentualMinimo = percentualMinimo;
        this.percentualMaximo = percentualMaximo;
        this.unidadeTarifa = unidadeTarifa;
        this.nivelServico = nivelServico;
        this.ativa = ativa;
        this.aplicavelPessoaFisica = aplicavelPessoaFisica;
        this.aplicavelPessoaJuridica = aplicavelPessoaJuridica;
        this.regrasAplicacao = regrasAplicacao;
        this.configuracoesEspeciais = configuracoesEspeciais;
        this.dataInicioVigencia = dataInicioVigencia;
        this.dataFimVigencia = dataFimVigencia;
        this.empresaId = empresaId;
    }
}
