package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "tarifas", schema = "aurix")
public class Tarifa extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoTarifa;
    @Column(nullable = false)
    private String nomeTarifa;
    @Column(length = 1000)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTarifa tipoTarifa;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaTarifa categoriaTarifa;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorBase;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorMinimo;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorMaximo;
    @Column(precision = 5, scale = 4)
    private BigDecimal percentualBase;
    @Column(precision = 5, scale = 4)
    private BigDecimal percentualMinimo;
    @Column(precision = 5, scale = 4)
    private BigDecimal percentualMaximo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadeTarifa unidadeTarifa = UnidadeTarifa.VALOR_FIXO;
    @Column(nullable = false)
    private Integer nivelServico = 1;
    @Column(nullable = false)
    private Boolean ativa = true;
    @Column(nullable = false)
    private Boolean aplicavelPessoaFisica = true;
    @Column(nullable = false)
    private Boolean aplicavelPessoaJuridica = true;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_aplicacao", columnDefinition = "JSONB")
    private String regrasAplicacao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracoes_especiais", columnDefinition = "JSONB")
    private String configuracoesEspeciais;
    @Column
    private LocalDateTime dataInicioVigencia;
    @Column
    private LocalDateTime dataFimVigencia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private com.aurix.platform.banking.entity.Empresa empresa;


    public enum TipoTarifa {
        TRANSFERENCIA_PIX, TRANSFERENCIA_TED, TRANSFERENCIA_DOC, SAQUE_ATM, SAQUE_AGENCIA, DEPOSITO, CONSULTA_SALDO, EXTRATO, CARTAO_CREDITO, CARTAO_DEBITO, INVESTIMENTO, EMPRESTIMO, FINANCIAMENTO, SEGURO, OUTROS;
    }


    public enum CategoriaTarifa {
        TRANSACIONAL, MANUTENCAO, CREDITO, INVESTIMENTO, SEGURO, TRIBUTARIA, OUTROS;
    }


    public enum UnidadeTarifa {
        VALOR_FIXO, PERCENTUAL, PERCENTUAL_COM_MINIMO, PERCENTUAL_COM_MAXIMO, PERCENTUAL_COM_MIN_MAX, VALOR_POR_OPERACAO, VALOR_POR_DIA, VALOR_POR_MES, VALOR_POR_ANO;
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
    public TipoTarifa getTipoTarifa() {
        return this.tipoTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaTarifa getCategoriaTarifa() {
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
    public UnidadeTarifa getUnidadeTarifa() {
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
    public com.aurix.platform.banking.entity.Empresa getEmpresa() {
        return this.empresa;
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
    public void setTipoTarifa(final TipoTarifa tipoTarifa) {
        this.tipoTarifa = tipoTarifa;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoriaTarifa(final CategoriaTarifa categoriaTarifa) {
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
    public void setUnidadeTarifa(final UnidadeTarifa unidadeTarifa) {
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
    public void setEmpresa(final com.aurix.platform.banking.entity.Empresa empresa) {
        this.empresa = empresa;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Tarifa(id=" + this.getId() + ", codigoTarifa=" + this.getCodigoTarifa() + ", nomeTarifa=" + this.getNomeTarifa() + ", descricao=" + this.getDescricao() + ", tipoTarifa=" + this.getTipoTarifa() + ", categoriaTarifa=" + this.getCategoriaTarifa() + ", valorBase=" + this.getValorBase() + ", valorMinimo=" + this.getValorMinimo() + ", valorMaximo=" + this.getValorMaximo() + ", percentualBase=" + this.getPercentualBase() + ", percentualMinimo=" + this.getPercentualMinimo() + ", percentualMaximo=" + this.getPercentualMaximo() + ", unidadeTarifa=" + this.getUnidadeTarifa() + ", nivelServico=" + this.getNivelServico() + ", ativa=" + this.getAtiva() + ", aplicavelPessoaFisica=" + this.getAplicavelPessoaFisica() + ", aplicavelPessoaJuridica=" + this.getAplicavelPessoaJuridica() + ", regrasAplicacao=" + this.getRegrasAplicacao() + ", configuracoesEspeciais=" + this.getConfiguracoesEspeciais() + ", dataInicioVigencia=" + this.getDataInicioVigencia() + ", dataFimVigencia=" + this.getDataFimVigencia() + ", empresa=" + this.getEmpresa() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public Tarifa() {
    }

    @java.lang.SuppressWarnings("all")
    public Tarifa(final Long id, final String codigoTarifa, final String nomeTarifa, final String descricao, final TipoTarifa tipoTarifa, final CategoriaTarifa categoriaTarifa, final BigDecimal valorBase, final BigDecimal valorMinimo, final BigDecimal valorMaximo, final BigDecimal percentualBase, final BigDecimal percentualMinimo, final BigDecimal percentualMaximo, final UnidadeTarifa unidadeTarifa, final Integer nivelServico, final Boolean ativa, final Boolean aplicavelPessoaFisica, final Boolean aplicavelPessoaJuridica, final String regrasAplicacao, final String configuracoesEspeciais, final LocalDateTime dataInicioVigencia, final LocalDateTime dataFimVigencia, final com.aurix.platform.banking.entity.Empresa empresa) {
        this.setId(id);
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
        this.empresa = empresa;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Tarifa)) return false;
        final Tarifa other = (Tarifa) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
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
        final java.lang.Object this$empresa = this.getEmpresa();
        final java.lang.Object other$empresa = other.getEmpresa();
        if (this$empresa == null ? other$empresa != null : !this$empresa.equals(other$empresa)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Tarifa;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
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
        final java.lang.Object $empresa = this.getEmpresa();
        result = result * PRIME + ($empresa == null ? 43 : $empresa.hashCode());
        return result;
    }
}
