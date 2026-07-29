package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "pacotes_tarifas", schema = "aurix")
public class PacoteTarifas extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoPacote;
    @Column(nullable = false)
    private String nomePacote;
    @Column(length = 1000)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPacote tipoPacote;
    @Column(nullable = false)
    private Integer nivelServico = 1;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorMensalidade = BigDecimal.ZERO;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorAnualidade = BigDecimal.ZERO;
    @Column(nullable = false)
    private Integer limiteOperacoesGratuitas = 0;
    @Column(nullable = false)
    private Boolean ativo = true;
    @Column(nullable = false)
    private Boolean aplicavelPessoaFisica = true;
    @Column(nullable = false)
    private Boolean aplicavelPessoaJuridica = true;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "beneficios", columnDefinition = "JSONB")
    private String beneficios;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "restricoes", columnDefinition = "JSONB")
    private String restricoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracoes_pacote", columnDefinition = "JSONB")
    private String configuracoesPacote;
    @Column
    private LocalDateTime dataInicioVigencia;
    @Column
    private LocalDateTime dataFimVigencia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id")
    private com.aurix.platform.banking.entity.Empresa empresa;
    @OneToMany(mappedBy = "pacoteTarifas", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PacoteTarifaItem> itens;


    public enum TipoPacote {
        BASICO, INTERMEDIARIO, PREMIUM, EXECUTIVO, EMPRESARIAL, PERSONALIZADO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoPacote() {
        return this.codigoPacote;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomePacote() {
        return this.nomePacote;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoPacote getTipoPacote() {
        return this.tipoPacote;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getNivelServico() {
        return this.nivelServico;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMensalidade() {
        return this.valorMensalidade;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAnualidade() {
        return this.valorAnualidade;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getLimiteOperacoesGratuitas() {
        return this.limiteOperacoesGratuitas;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
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
    public String getBeneficios() {
        return this.beneficios;
    }

    @java.lang.SuppressWarnings("all")
    public String getRestricoes() {
        return this.restricoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getConfiguracoesPacote() {
        return this.configuracoesPacote;
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
    public List<PacoteTarifaItem> getItens() {
        return this.itens;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoPacote(final String codigoPacote) {
        this.codigoPacote = codigoPacote;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomePacote(final String nomePacote) {
        this.nomePacote = nomePacote;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoPacote(final TipoPacote tipoPacote) {
        this.tipoPacote = tipoPacote;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelServico(final Integer nivelServico) {
        this.nivelServico = nivelServico;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMensalidade(final BigDecimal valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorAnualidade(final BigDecimal valorAnualidade) {
        this.valorAnualidade = valorAnualidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteOperacoesGratuitas(final Integer limiteOperacoesGratuitas) {
        this.limiteOperacoesGratuitas = limiteOperacoesGratuitas;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
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
    public void setBeneficios(final String beneficios) {
        this.beneficios = beneficios;
    }

    @java.lang.SuppressWarnings("all")
    public void setRestricoes(final String restricoes) {
        this.restricoes = restricoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setConfiguracoesPacote(final String configuracoesPacote) {
        this.configuracoesPacote = configuracoesPacote;
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

    @java.lang.SuppressWarnings("all")
    public void setItens(final List<PacoteTarifaItem> itens) {
        this.itens = itens;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PacoteTarifas(id=" + this.getId() + ", codigoPacote=" + this.getCodigoPacote() + ", nomePacote=" + this.getNomePacote() + ", descricao=" + this.getDescricao() + ", tipoPacote=" + this.getTipoPacote() + ", nivelServico=" + this.getNivelServico() + ", valorMensalidade=" + this.getValorMensalidade() + ", valorAnualidade=" + this.getValorAnualidade() + ", limiteOperacoesGratuitas=" + this.getLimiteOperacoesGratuitas() + ", ativo=" + this.getAtivo() + ", aplicavelPessoaFisica=" + this.getAplicavelPessoaFisica() + ", aplicavelPessoaJuridica=" + this.getAplicavelPessoaJuridica() + ", beneficios=" + this.getBeneficios() + ", restricoes=" + this.getRestricoes() + ", configuracoesPacote=" + this.getConfiguracoesPacote() + ", dataInicioVigencia=" + this.getDataInicioVigencia() + ", dataFimVigencia=" + this.getDataFimVigencia() + ", empresa=" + this.getEmpresa() + ", itens=" + this.getItens() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PacoteTarifas() {
    }

    @java.lang.SuppressWarnings("all")
    public PacoteTarifas(final Long id, final String codigoPacote, final String nomePacote, final String descricao, final TipoPacote tipoPacote, final Integer nivelServico, final BigDecimal valorMensalidade, final BigDecimal valorAnualidade, final Integer limiteOperacoesGratuitas, final Boolean ativo, final Boolean aplicavelPessoaFisica, final Boolean aplicavelPessoaJuridica, final String beneficios, final String restricoes, final String configuracoesPacote, final LocalDateTime dataInicioVigencia, final LocalDateTime dataFimVigencia, final com.aurix.platform.banking.entity.Empresa empresa, final List<PacoteTarifaItem> itens) {
        this.setId(id);
        this.codigoPacote = codigoPacote;
        this.nomePacote = nomePacote;
        this.descricao = descricao;
        this.tipoPacote = tipoPacote;
        this.nivelServico = nivelServico;
        this.valorMensalidade = valorMensalidade;
        this.valorAnualidade = valorAnualidade;
        this.limiteOperacoesGratuitas = limiteOperacoesGratuitas;
        this.ativo = ativo;
        this.aplicavelPessoaFisica = aplicavelPessoaFisica;
        this.aplicavelPessoaJuridica = aplicavelPessoaJuridica;
        this.beneficios = beneficios;
        this.restricoes = restricoes;
        this.configuracoesPacote = configuracoesPacote;
        this.dataInicioVigencia = dataInicioVigencia;
        this.dataFimVigencia = dataFimVigencia;
        this.empresa = empresa;
        this.itens = itens;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PacoteTarifas)) return false;
        final PacoteTarifas other = (PacoteTarifas) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$nivelServico = this.getNivelServico();
        final java.lang.Object other$nivelServico = other.getNivelServico();
        if (this$nivelServico == null ? other$nivelServico != null : !this$nivelServico.equals(other$nivelServico)) return false;
        final java.lang.Object this$limiteOperacoesGratuitas = this.getLimiteOperacoesGratuitas();
        final java.lang.Object other$limiteOperacoesGratuitas = other.getLimiteOperacoesGratuitas();
        if (this$limiteOperacoesGratuitas == null ? other$limiteOperacoesGratuitas != null : !this$limiteOperacoesGratuitas.equals(other$limiteOperacoesGratuitas)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
        final java.lang.Object this$aplicavelPessoaFisica = this.getAplicavelPessoaFisica();
        final java.lang.Object other$aplicavelPessoaFisica = other.getAplicavelPessoaFisica();
        if (this$aplicavelPessoaFisica == null ? other$aplicavelPessoaFisica != null : !this$aplicavelPessoaFisica.equals(other$aplicavelPessoaFisica)) return false;
        final java.lang.Object this$aplicavelPessoaJuridica = this.getAplicavelPessoaJuridica();
        final java.lang.Object other$aplicavelPessoaJuridica = other.getAplicavelPessoaJuridica();
        if (this$aplicavelPessoaJuridica == null ? other$aplicavelPessoaJuridica != null : !this$aplicavelPessoaJuridica.equals(other$aplicavelPessoaJuridica)) return false;
        final java.lang.Object this$codigoPacote = this.getCodigoPacote();
        final java.lang.Object other$codigoPacote = other.getCodigoPacote();
        if (this$codigoPacote == null ? other$codigoPacote != null : !this$codigoPacote.equals(other$codigoPacote)) return false;
        final java.lang.Object this$nomePacote = this.getNomePacote();
        final java.lang.Object other$nomePacote = other.getNomePacote();
        if (this$nomePacote == null ? other$nomePacote != null : !this$nomePacote.equals(other$nomePacote)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoPacote = this.getTipoPacote();
        final java.lang.Object other$tipoPacote = other.getTipoPacote();
        if (this$tipoPacote == null ? other$tipoPacote != null : !this$tipoPacote.equals(other$tipoPacote)) return false;
        final java.lang.Object this$valorMensalidade = this.getValorMensalidade();
        final java.lang.Object other$valorMensalidade = other.getValorMensalidade();
        if (this$valorMensalidade == null ? other$valorMensalidade != null : !this$valorMensalidade.equals(other$valorMensalidade)) return false;
        final java.lang.Object this$valorAnualidade = this.getValorAnualidade();
        final java.lang.Object other$valorAnualidade = other.getValorAnualidade();
        if (this$valorAnualidade == null ? other$valorAnualidade != null : !this$valorAnualidade.equals(other$valorAnualidade)) return false;
        final java.lang.Object this$beneficios = this.getBeneficios();
        final java.lang.Object other$beneficios = other.getBeneficios();
        if (this$beneficios == null ? other$beneficios != null : !this$beneficios.equals(other$beneficios)) return false;
        final java.lang.Object this$restricoes = this.getRestricoes();
        final java.lang.Object other$restricoes = other.getRestricoes();
        if (this$restricoes == null ? other$restricoes != null : !this$restricoes.equals(other$restricoes)) return false;
        final java.lang.Object this$configuracoesPacote = this.getConfiguracoesPacote();
        final java.lang.Object other$configuracoesPacote = other.getConfiguracoesPacote();
        if (this$configuracoesPacote == null ? other$configuracoesPacote != null : !this$configuracoesPacote.equals(other$configuracoesPacote)) return false;
        final java.lang.Object this$dataInicioVigencia = this.getDataInicioVigencia();
        final java.lang.Object other$dataInicioVigencia = other.getDataInicioVigencia();
        if (this$dataInicioVigencia == null ? other$dataInicioVigencia != null : !this$dataInicioVigencia.equals(other$dataInicioVigencia)) return false;
        final java.lang.Object this$dataFimVigencia = this.getDataFimVigencia();
        final java.lang.Object other$dataFimVigencia = other.getDataFimVigencia();
        if (this$dataFimVigencia == null ? other$dataFimVigencia != null : !this$dataFimVigencia.equals(other$dataFimVigencia)) return false;
        final java.lang.Object this$empresa = this.getEmpresa();
        final java.lang.Object other$empresa = other.getEmpresa();
        if (this$empresa == null ? other$empresa != null : !this$empresa.equals(other$empresa)) return false;
        final java.lang.Object this$itens = this.getItens();
        final java.lang.Object other$itens = other.getItens();
        if (this$itens == null ? other$itens != null : !this$itens.equals(other$itens)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PacoteTarifas;
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
        final java.lang.Object $limiteOperacoesGratuitas = this.getLimiteOperacoesGratuitas();
        result = result * PRIME + ($limiteOperacoesGratuitas == null ? 43 : $limiteOperacoesGratuitas.hashCode());
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
        final java.lang.Object $aplicavelPessoaFisica = this.getAplicavelPessoaFisica();
        result = result * PRIME + ($aplicavelPessoaFisica == null ? 43 : $aplicavelPessoaFisica.hashCode());
        final java.lang.Object $aplicavelPessoaJuridica = this.getAplicavelPessoaJuridica();
        result = result * PRIME + ($aplicavelPessoaJuridica == null ? 43 : $aplicavelPessoaJuridica.hashCode());
        final java.lang.Object $codigoPacote = this.getCodigoPacote();
        result = result * PRIME + ($codigoPacote == null ? 43 : $codigoPacote.hashCode());
        final java.lang.Object $nomePacote = this.getNomePacote();
        result = result * PRIME + ($nomePacote == null ? 43 : $nomePacote.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoPacote = this.getTipoPacote();
        result = result * PRIME + ($tipoPacote == null ? 43 : $tipoPacote.hashCode());
        final java.lang.Object $valorMensalidade = this.getValorMensalidade();
        result = result * PRIME + ($valorMensalidade == null ? 43 : $valorMensalidade.hashCode());
        final java.lang.Object $valorAnualidade = this.getValorAnualidade();
        result = result * PRIME + ($valorAnualidade == null ? 43 : $valorAnualidade.hashCode());
        final java.lang.Object $beneficios = this.getBeneficios();
        result = result * PRIME + ($beneficios == null ? 43 : $beneficios.hashCode());
        final java.lang.Object $restricoes = this.getRestricoes();
        result = result * PRIME + ($restricoes == null ? 43 : $restricoes.hashCode());
        final java.lang.Object $configuracoesPacote = this.getConfiguracoesPacote();
        result = result * PRIME + ($configuracoesPacote == null ? 43 : $configuracoesPacote.hashCode());
        final java.lang.Object $dataInicioVigencia = this.getDataInicioVigencia();
        result = result * PRIME + ($dataInicioVigencia == null ? 43 : $dataInicioVigencia.hashCode());
        final java.lang.Object $dataFimVigencia = this.getDataFimVigencia();
        result = result * PRIME + ($dataFimVigencia == null ? 43 : $dataFimVigencia.hashCode());
        final java.lang.Object $empresa = this.getEmpresa();
        result = result * PRIME + ($empresa == null ? 43 : $empresa.hashCode());
        final java.lang.Object $itens = this.getItens();
        result = result * PRIME + ($itens == null ? 43 : $itens.hashCode());
        return result;
    }
}
