package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "produtos_financeiros", schema = "aurix")
public class ProdutoFinanceiro extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoProduto;
    @Column(nullable = false)
    private String nomeProduto;
    @Column(length = 1000)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoProduto tipoProduto;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaProduto categoriaProduto;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorMinimoAplicacao;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorMaximoAplicacao;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaRemuneracao;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaRemuneracaoAnual;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaRemuneracaoMensal;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaRemuneracaoDiaria;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRemuneracao tipoRemuneracao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PeriodicidadeRemuneracao periodicidadeRemuneracao;
    @Column
    private Integer prazoMinimoDias;
    @Column
    private Integer prazoMaximoDias;
    @Column
    private Boolean permiteResgateAntecipado = false;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaResgateAntecipado;
    @Column
    private Boolean permiteAplicacaoParcial = false;
    @Column
    private Boolean permiteAplicacaoTotal = true;
    @Column
    private Boolean permiteReaplicacao = false;
    @Column
    private Boolean permiteRenovacao = false;
    @Column
    private Boolean ativo = true;
    @Column
    private Boolean disponivelPublico = true;
    @Column
    private Boolean requerAprovacao = false;
    @Column
    private Boolean requerDocumentacao = false;
    @Column
    private Integer nivelRisco = 1;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_remuneracao", columnDefinition = "JSONB")
    private String regrasRemuneracao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "configuracoes_especiais", columnDefinition = "JSONB")
    private String configuracoesEspeciais;
    @Column
    private LocalDateTime dataInicioVigencia;
    @Column
    private LocalDateTime dataFimVigencia;
    @Column
    private String usuarioCriacao;
    @Column
    private String usuarioAprovacao;
    @Column
    private LocalDateTime dataAprovacao;


    public enum TipoProduto {
        POUPANCA, CDB, LCI, LCA, LC, DEBENTURE, FUNDO_INVESTIMENTO, PREVIDENCIA, SEGURO_VIDA, CREDITO_PESSOAL, FINANCIAMENTO, CARTÃO_CREDITO, CONTA_CORRENTE, CONTA_POUPANCA, OUTROS;
    }


    public enum CategoriaProduto {
        RENDA_FIXA, RENDA_VARIAVEL, HIBRIDO, CREDITO, SEGURO, PREVIDENCIA, CONTA_BANCARIA, OUTROS;
    }


    public enum TipoRemuneracao {
        FIXA, VARIAVEL, HIBRIDA, INDEXADA, PRE_FIXADA, POS_FIXADA;
    }


    public enum PeriodicidadeRemuneracao {
        DIARIA, MENSAL, TRIMESTRAL, SEMESTRAL, ANUAL, VENCIMENTO, PERSONALIZADA;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoProduto() {
        return this.codigoProduto;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeProduto() {
        return this.nomeProduto;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoProduto getTipoProduto() {
        return this.tipoProduto;
    }

    @java.lang.SuppressWarnings("all")
    public CategoriaProduto getCategoriaProduto() {
        return this.categoriaProduto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMinimoAplicacao() {
        return this.valorMinimoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMaximoAplicacao() {
        return this.valorMaximoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaRemuneracao() {
        return this.taxaRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaRemuneracaoAnual() {
        return this.taxaRemuneracaoAnual;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaRemuneracaoMensal() {
        return this.taxaRemuneracaoMensal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaRemuneracaoDiaria() {
        return this.taxaRemuneracaoDiaria;
    }

    @java.lang.SuppressWarnings("all")
    public TipoRemuneracao getTipoRemuneracao() {
        return this.tipoRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public PeriodicidadeRemuneracao getPeriodicidadeRemuneracao() {
        return this.periodicidadeRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getPrazoMinimoDias() {
        return this.prazoMinimoDias;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getPrazoMaximoDias() {
        return this.prazoMaximoDias;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPermiteResgateAntecipado() {
        return this.permiteResgateAntecipado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaResgateAntecipado() {
        return this.taxaResgateAntecipado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPermiteAplicacaoParcial() {
        return this.permiteAplicacaoParcial;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPermiteAplicacaoTotal() {
        return this.permiteAplicacaoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPermiteReaplicacao() {
        return this.permiteReaplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPermiteRenovacao() {
        return this.permiteRenovacao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getDisponivelPublico() {
        return this.disponivelPublico;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerAprovacao() {
        return this.requerAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRequerDocumentacao() {
        return this.requerDocumentacao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getNivelRisco() {
        return this.nivelRisco;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasRemuneracao() {
        return this.regrasRemuneracao;
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
    public String getUsuarioCriacao() {
        return this.usuarioCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioAprovacao() {
        return this.usuarioAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAprovacao() {
        return this.dataAprovacao;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoProduto(final String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeProduto(final String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoProduto(final TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    @java.lang.SuppressWarnings("all")
    public void setCategoriaProduto(final CategoriaProduto categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMinimoAplicacao(final BigDecimal valorMinimoAplicacao) {
        this.valorMinimoAplicacao = valorMinimoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMaximoAplicacao(final BigDecimal valorMaximoAplicacao) {
        this.valorMaximoAplicacao = valorMaximoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaRemuneracao(final BigDecimal taxaRemuneracao) {
        this.taxaRemuneracao = taxaRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaRemuneracaoAnual(final BigDecimal taxaRemuneracaoAnual) {
        this.taxaRemuneracaoAnual = taxaRemuneracaoAnual;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaRemuneracaoMensal(final BigDecimal taxaRemuneracaoMensal) {
        this.taxaRemuneracaoMensal = taxaRemuneracaoMensal;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaRemuneracaoDiaria(final BigDecimal taxaRemuneracaoDiaria) {
        this.taxaRemuneracaoDiaria = taxaRemuneracaoDiaria;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoRemuneracao(final TipoRemuneracao tipoRemuneracao) {
        this.tipoRemuneracao = tipoRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPeriodicidadeRemuneracao(final PeriodicidadeRemuneracao periodicidadeRemuneracao) {
        this.periodicidadeRemuneracao = periodicidadeRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrazoMinimoDias(final Integer prazoMinimoDias) {
        this.prazoMinimoDias = prazoMinimoDias;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrazoMaximoDias(final Integer prazoMaximoDias) {
        this.prazoMaximoDias = prazoMaximoDias;
    }

    @java.lang.SuppressWarnings("all")
    public void setPermiteResgateAntecipado(final Boolean permiteResgateAntecipado) {
        this.permiteResgateAntecipado = permiteResgateAntecipado;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaResgateAntecipado(final BigDecimal taxaResgateAntecipado) {
        this.taxaResgateAntecipado = taxaResgateAntecipado;
    }

    @java.lang.SuppressWarnings("all")
    public void setPermiteAplicacaoParcial(final Boolean permiteAplicacaoParcial) {
        this.permiteAplicacaoParcial = permiteAplicacaoParcial;
    }

    @java.lang.SuppressWarnings("all")
    public void setPermiteAplicacaoTotal(final Boolean permiteAplicacaoTotal) {
        this.permiteAplicacaoTotal = permiteAplicacaoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setPermiteReaplicacao(final Boolean permiteReaplicacao) {
        this.permiteReaplicacao = permiteReaplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPermiteRenovacao(final Boolean permiteRenovacao) {
        this.permiteRenovacao = permiteRenovacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDisponivelPublico(final Boolean disponivelPublico) {
        this.disponivelPublico = disponivelPublico;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerAprovacao(final Boolean requerAprovacao) {
        this.requerAprovacao = requerAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRequerDocumentacao(final Boolean requerDocumentacao) {
        this.requerDocumentacao = requerDocumentacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setNivelRisco(final Integer nivelRisco) {
        this.nivelRisco = nivelRisco;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasRemuneracao(final String regrasRemuneracao) {
        this.regrasRemuneracao = regrasRemuneracao;
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
    public void setUsuarioCriacao(final String usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioAprovacao(final String usuarioAprovacao) {
        this.usuarioAprovacao = usuarioAprovacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAprovacao(final LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ProdutoFinanceiro(id=" + this.getId() + ", codigoProduto=" + this.getCodigoProduto() + ", nomeProduto=" + this.getNomeProduto() + ", descricao=" + this.getDescricao() + ", tipoProduto=" + this.getTipoProduto() + ", categoriaProduto=" + this.getCategoriaProduto() + ", valorMinimoAplicacao=" + this.getValorMinimoAplicacao() + ", valorMaximoAplicacao=" + this.getValorMaximoAplicacao() + ", taxaRemuneracao=" + this.getTaxaRemuneracao() + ", taxaRemuneracaoAnual=" + this.getTaxaRemuneracaoAnual() + ", taxaRemuneracaoMensal=" + this.getTaxaRemuneracaoMensal() + ", taxaRemuneracaoDiaria=" + this.getTaxaRemuneracaoDiaria() + ", tipoRemuneracao=" + this.getTipoRemuneracao() + ", periodicidadeRemuneracao=" + this.getPeriodicidadeRemuneracao() + ", prazoMinimoDias=" + this.getPrazoMinimoDias() + ", prazoMaximoDias=" + this.getPrazoMaximoDias() + ", permiteResgateAntecipado=" + this.getPermiteResgateAntecipado() + ", taxaResgateAntecipado=" + this.getTaxaResgateAntecipado() + ", permiteAplicacaoParcial=" + this.getPermiteAplicacaoParcial() + ", permiteAplicacaoTotal=" + this.getPermiteAplicacaoTotal() + ", permiteReaplicacao=" + this.getPermiteReaplicacao() + ", permiteRenovacao=" + this.getPermiteRenovacao() + ", ativo=" + this.getAtivo() + ", disponivelPublico=" + this.getDisponivelPublico() + ", requerAprovacao=" + this.getRequerAprovacao() + ", requerDocumentacao=" + this.getRequerDocumentacao() + ", nivelRisco=" + this.getNivelRisco() + ", observacoes=" + this.getObservacoes() + ", regrasRemuneracao=" + this.getRegrasRemuneracao() + ", configuracoesEspeciais=" + this.getConfiguracoesEspeciais() + ", dataInicioVigencia=" + this.getDataInicioVigencia() + ", dataFimVigencia=" + this.getDataFimVigencia() + ", usuarioCriacao=" + this.getUsuarioCriacao() + ", usuarioAprovacao=" + this.getUsuarioAprovacao() + ", dataAprovacao=" + this.getDataAprovacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoFinanceiro() {
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoFinanceiro(final Long id, final String codigoProduto, final String nomeProduto, final String descricao, final TipoProduto tipoProduto, final CategoriaProduto categoriaProduto, final BigDecimal valorMinimoAplicacao, final BigDecimal valorMaximoAplicacao, final BigDecimal taxaRemuneracao, final BigDecimal taxaRemuneracaoAnual, final BigDecimal taxaRemuneracaoMensal, final BigDecimal taxaRemuneracaoDiaria, final TipoRemuneracao tipoRemuneracao, final PeriodicidadeRemuneracao periodicidadeRemuneracao, final Integer prazoMinimoDias, final Integer prazoMaximoDias, final Boolean permiteResgateAntecipado, final BigDecimal taxaResgateAntecipado, final Boolean permiteAplicacaoParcial, final Boolean permiteAplicacaoTotal, final Boolean permiteReaplicacao, final Boolean permiteRenovacao, final Boolean ativo, final Boolean disponivelPublico, final Boolean requerAprovacao, final Boolean requerDocumentacao, final Integer nivelRisco, final String observacoes, final String regrasRemuneracao, final String configuracoesEspeciais, final LocalDateTime dataInicioVigencia, final LocalDateTime dataFimVigencia, final String usuarioCriacao, final String usuarioAprovacao, final LocalDateTime dataAprovacao) {
        this.setId(id);
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
        this.tipoProduto = tipoProduto;
        this.categoriaProduto = categoriaProduto;
        this.valorMinimoAplicacao = valorMinimoAplicacao;
        this.valorMaximoAplicacao = valorMaximoAplicacao;
        this.taxaRemuneracao = taxaRemuneracao;
        this.taxaRemuneracaoAnual = taxaRemuneracaoAnual;
        this.taxaRemuneracaoMensal = taxaRemuneracaoMensal;
        this.taxaRemuneracaoDiaria = taxaRemuneracaoDiaria;
        this.tipoRemuneracao = tipoRemuneracao;
        this.periodicidadeRemuneracao = periodicidadeRemuneracao;
        this.prazoMinimoDias = prazoMinimoDias;
        this.prazoMaximoDias = prazoMaximoDias;
        this.permiteResgateAntecipado = permiteResgateAntecipado;
        this.taxaResgateAntecipado = taxaResgateAntecipado;
        this.permiteAplicacaoParcial = permiteAplicacaoParcial;
        this.permiteAplicacaoTotal = permiteAplicacaoTotal;
        this.permiteReaplicacao = permiteReaplicacao;
        this.permiteRenovacao = permiteRenovacao;
        this.ativo = ativo;
        this.disponivelPublico = disponivelPublico;
        this.requerAprovacao = requerAprovacao;
        this.requerDocumentacao = requerDocumentacao;
        this.nivelRisco = nivelRisco;
        this.observacoes = observacoes;
        this.regrasRemuneracao = regrasRemuneracao;
        this.configuracoesEspeciais = configuracoesEspeciais;
        this.dataInicioVigencia = dataInicioVigencia;
        this.dataFimVigencia = dataFimVigencia;
        this.usuarioCriacao = usuarioCriacao;
        this.usuarioAprovacao = usuarioAprovacao;
        this.dataAprovacao = dataAprovacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ProdutoFinanceiro)) return false;
        final ProdutoFinanceiro other = (ProdutoFinanceiro) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$prazoMinimoDias = this.getPrazoMinimoDias();
        final java.lang.Object other$prazoMinimoDias = other.getPrazoMinimoDias();
        if (this$prazoMinimoDias == null ? other$prazoMinimoDias != null : !this$prazoMinimoDias.equals(other$prazoMinimoDias)) return false;
        final java.lang.Object this$prazoMaximoDias = this.getPrazoMaximoDias();
        final java.lang.Object other$prazoMaximoDias = other.getPrazoMaximoDias();
        if (this$prazoMaximoDias == null ? other$prazoMaximoDias != null : !this$prazoMaximoDias.equals(other$prazoMaximoDias)) return false;
        final java.lang.Object this$permiteResgateAntecipado = this.getPermiteResgateAntecipado();
        final java.lang.Object other$permiteResgateAntecipado = other.getPermiteResgateAntecipado();
        if (this$permiteResgateAntecipado == null ? other$permiteResgateAntecipado != null : !this$permiteResgateAntecipado.equals(other$permiteResgateAntecipado)) return false;
        final java.lang.Object this$permiteAplicacaoParcial = this.getPermiteAplicacaoParcial();
        final java.lang.Object other$permiteAplicacaoParcial = other.getPermiteAplicacaoParcial();
        if (this$permiteAplicacaoParcial == null ? other$permiteAplicacaoParcial != null : !this$permiteAplicacaoParcial.equals(other$permiteAplicacaoParcial)) return false;
        final java.lang.Object this$permiteAplicacaoTotal = this.getPermiteAplicacaoTotal();
        final java.lang.Object other$permiteAplicacaoTotal = other.getPermiteAplicacaoTotal();
        if (this$permiteAplicacaoTotal == null ? other$permiteAplicacaoTotal != null : !this$permiteAplicacaoTotal.equals(other$permiteAplicacaoTotal)) return false;
        final java.lang.Object this$permiteReaplicacao = this.getPermiteReaplicacao();
        final java.lang.Object other$permiteReaplicacao = other.getPermiteReaplicacao();
        if (this$permiteReaplicacao == null ? other$permiteReaplicacao != null : !this$permiteReaplicacao.equals(other$permiteReaplicacao)) return false;
        final java.lang.Object this$permiteRenovacao = this.getPermiteRenovacao();
        final java.lang.Object other$permiteRenovacao = other.getPermiteRenovacao();
        if (this$permiteRenovacao == null ? other$permiteRenovacao != null : !this$permiteRenovacao.equals(other$permiteRenovacao)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
        final java.lang.Object this$disponivelPublico = this.getDisponivelPublico();
        final java.lang.Object other$disponivelPublico = other.getDisponivelPublico();
        if (this$disponivelPublico == null ? other$disponivelPublico != null : !this$disponivelPublico.equals(other$disponivelPublico)) return false;
        final java.lang.Object this$requerAprovacao = this.getRequerAprovacao();
        final java.lang.Object other$requerAprovacao = other.getRequerAprovacao();
        if (this$requerAprovacao == null ? other$requerAprovacao != null : !this$requerAprovacao.equals(other$requerAprovacao)) return false;
        final java.lang.Object this$requerDocumentacao = this.getRequerDocumentacao();
        final java.lang.Object other$requerDocumentacao = other.getRequerDocumentacao();
        if (this$requerDocumentacao == null ? other$requerDocumentacao != null : !this$requerDocumentacao.equals(other$requerDocumentacao)) return false;
        final java.lang.Object this$nivelRisco = this.getNivelRisco();
        final java.lang.Object other$nivelRisco = other.getNivelRisco();
        if (this$nivelRisco == null ? other$nivelRisco != null : !this$nivelRisco.equals(other$nivelRisco)) return false;
        final java.lang.Object this$codigoProduto = this.getCodigoProduto();
        final java.lang.Object other$codigoProduto = other.getCodigoProduto();
        if (this$codigoProduto == null ? other$codigoProduto != null : !this$codigoProduto.equals(other$codigoProduto)) return false;
        final java.lang.Object this$nomeProduto = this.getNomeProduto();
        final java.lang.Object other$nomeProduto = other.getNomeProduto();
        if (this$nomeProduto == null ? other$nomeProduto != null : !this$nomeProduto.equals(other$nomeProduto)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$tipoProduto = this.getTipoProduto();
        final java.lang.Object other$tipoProduto = other.getTipoProduto();
        if (this$tipoProduto == null ? other$tipoProduto != null : !this$tipoProduto.equals(other$tipoProduto)) return false;
        final java.lang.Object this$categoriaProduto = this.getCategoriaProduto();
        final java.lang.Object other$categoriaProduto = other.getCategoriaProduto();
        if (this$categoriaProduto == null ? other$categoriaProduto != null : !this$categoriaProduto.equals(other$categoriaProduto)) return false;
        final java.lang.Object this$valorMinimoAplicacao = this.getValorMinimoAplicacao();
        final java.lang.Object other$valorMinimoAplicacao = other.getValorMinimoAplicacao();
        if (this$valorMinimoAplicacao == null ? other$valorMinimoAplicacao != null : !this$valorMinimoAplicacao.equals(other$valorMinimoAplicacao)) return false;
        final java.lang.Object this$valorMaximoAplicacao = this.getValorMaximoAplicacao();
        final java.lang.Object other$valorMaximoAplicacao = other.getValorMaximoAplicacao();
        if (this$valorMaximoAplicacao == null ? other$valorMaximoAplicacao != null : !this$valorMaximoAplicacao.equals(other$valorMaximoAplicacao)) return false;
        final java.lang.Object this$taxaRemuneracao = this.getTaxaRemuneracao();
        final java.lang.Object other$taxaRemuneracao = other.getTaxaRemuneracao();
        if (this$taxaRemuneracao == null ? other$taxaRemuneracao != null : !this$taxaRemuneracao.equals(other$taxaRemuneracao)) return false;
        final java.lang.Object this$taxaRemuneracaoAnual = this.getTaxaRemuneracaoAnual();
        final java.lang.Object other$taxaRemuneracaoAnual = other.getTaxaRemuneracaoAnual();
        if (this$taxaRemuneracaoAnual == null ? other$taxaRemuneracaoAnual != null : !this$taxaRemuneracaoAnual.equals(other$taxaRemuneracaoAnual)) return false;
        final java.lang.Object this$taxaRemuneracaoMensal = this.getTaxaRemuneracaoMensal();
        final java.lang.Object other$taxaRemuneracaoMensal = other.getTaxaRemuneracaoMensal();
        if (this$taxaRemuneracaoMensal == null ? other$taxaRemuneracaoMensal != null : !this$taxaRemuneracaoMensal.equals(other$taxaRemuneracaoMensal)) return false;
        final java.lang.Object this$taxaRemuneracaoDiaria = this.getTaxaRemuneracaoDiaria();
        final java.lang.Object other$taxaRemuneracaoDiaria = other.getTaxaRemuneracaoDiaria();
        if (this$taxaRemuneracaoDiaria == null ? other$taxaRemuneracaoDiaria != null : !this$taxaRemuneracaoDiaria.equals(other$taxaRemuneracaoDiaria)) return false;
        final java.lang.Object this$tipoRemuneracao = this.getTipoRemuneracao();
        final java.lang.Object other$tipoRemuneracao = other.getTipoRemuneracao();
        if (this$tipoRemuneracao == null ? other$tipoRemuneracao != null : !this$tipoRemuneracao.equals(other$tipoRemuneracao)) return false;
        final java.lang.Object this$periodicidadeRemuneracao = this.getPeriodicidadeRemuneracao();
        final java.lang.Object other$periodicidadeRemuneracao = other.getPeriodicidadeRemuneracao();
        if (this$periodicidadeRemuneracao == null ? other$periodicidadeRemuneracao != null : !this$periodicidadeRemuneracao.equals(other$periodicidadeRemuneracao)) return false;
        final java.lang.Object this$taxaResgateAntecipado = this.getTaxaResgateAntecipado();
        final java.lang.Object other$taxaResgateAntecipado = other.getTaxaResgateAntecipado();
        if (this$taxaResgateAntecipado == null ? other$taxaResgateAntecipado != null : !this$taxaResgateAntecipado.equals(other$taxaResgateAntecipado)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$regrasRemuneracao = this.getRegrasRemuneracao();
        final java.lang.Object other$regrasRemuneracao = other.getRegrasRemuneracao();
        if (this$regrasRemuneracao == null ? other$regrasRemuneracao != null : !this$regrasRemuneracao.equals(other$regrasRemuneracao)) return false;
        final java.lang.Object this$configuracoesEspeciais = this.getConfiguracoesEspeciais();
        final java.lang.Object other$configuracoesEspeciais = other.getConfiguracoesEspeciais();
        if (this$configuracoesEspeciais == null ? other$configuracoesEspeciais != null : !this$configuracoesEspeciais.equals(other$configuracoesEspeciais)) return false;
        final java.lang.Object this$dataInicioVigencia = this.getDataInicioVigencia();
        final java.lang.Object other$dataInicioVigencia = other.getDataInicioVigencia();
        if (this$dataInicioVigencia == null ? other$dataInicioVigencia != null : !this$dataInicioVigencia.equals(other$dataInicioVigencia)) return false;
        final java.lang.Object this$dataFimVigencia = this.getDataFimVigencia();
        final java.lang.Object other$dataFimVigencia = other.getDataFimVigencia();
        if (this$dataFimVigencia == null ? other$dataFimVigencia != null : !this$dataFimVigencia.equals(other$dataFimVigencia)) return false;
        final java.lang.Object this$usuarioCriacao = this.getUsuarioCriacao();
        final java.lang.Object other$usuarioCriacao = other.getUsuarioCriacao();
        if (this$usuarioCriacao == null ? other$usuarioCriacao != null : !this$usuarioCriacao.equals(other$usuarioCriacao)) return false;
        final java.lang.Object this$usuarioAprovacao = this.getUsuarioAprovacao();
        final java.lang.Object other$usuarioAprovacao = other.getUsuarioAprovacao();
        if (this$usuarioAprovacao == null ? other$usuarioAprovacao != null : !this$usuarioAprovacao.equals(other$usuarioAprovacao)) return false;
        final java.lang.Object this$dataAprovacao = this.getDataAprovacao();
        final java.lang.Object other$dataAprovacao = other.getDataAprovacao();
        if (this$dataAprovacao == null ? other$dataAprovacao != null : !this$dataAprovacao.equals(other$dataAprovacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ProdutoFinanceiro;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $prazoMinimoDias = this.getPrazoMinimoDias();
        result = result * PRIME + ($prazoMinimoDias == null ? 43 : $prazoMinimoDias.hashCode());
        final java.lang.Object $prazoMaximoDias = this.getPrazoMaximoDias();
        result = result * PRIME + ($prazoMaximoDias == null ? 43 : $prazoMaximoDias.hashCode());
        final java.lang.Object $permiteResgateAntecipado = this.getPermiteResgateAntecipado();
        result = result * PRIME + ($permiteResgateAntecipado == null ? 43 : $permiteResgateAntecipado.hashCode());
        final java.lang.Object $permiteAplicacaoParcial = this.getPermiteAplicacaoParcial();
        result = result * PRIME + ($permiteAplicacaoParcial == null ? 43 : $permiteAplicacaoParcial.hashCode());
        final java.lang.Object $permiteAplicacaoTotal = this.getPermiteAplicacaoTotal();
        result = result * PRIME + ($permiteAplicacaoTotal == null ? 43 : $permiteAplicacaoTotal.hashCode());
        final java.lang.Object $permiteReaplicacao = this.getPermiteReaplicacao();
        result = result * PRIME + ($permiteReaplicacao == null ? 43 : $permiteReaplicacao.hashCode());
        final java.lang.Object $permiteRenovacao = this.getPermiteRenovacao();
        result = result * PRIME + ($permiteRenovacao == null ? 43 : $permiteRenovacao.hashCode());
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
        final java.lang.Object $disponivelPublico = this.getDisponivelPublico();
        result = result * PRIME + ($disponivelPublico == null ? 43 : $disponivelPublico.hashCode());
        final java.lang.Object $requerAprovacao = this.getRequerAprovacao();
        result = result * PRIME + ($requerAprovacao == null ? 43 : $requerAprovacao.hashCode());
        final java.lang.Object $requerDocumentacao = this.getRequerDocumentacao();
        result = result * PRIME + ($requerDocumentacao == null ? 43 : $requerDocumentacao.hashCode());
        final java.lang.Object $nivelRisco = this.getNivelRisco();
        result = result * PRIME + ($nivelRisco == null ? 43 : $nivelRisco.hashCode());
        final java.lang.Object $codigoProduto = this.getCodigoProduto();
        result = result * PRIME + ($codigoProduto == null ? 43 : $codigoProduto.hashCode());
        final java.lang.Object $nomeProduto = this.getNomeProduto();
        result = result * PRIME + ($nomeProduto == null ? 43 : $nomeProduto.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $tipoProduto = this.getTipoProduto();
        result = result * PRIME + ($tipoProduto == null ? 43 : $tipoProduto.hashCode());
        final java.lang.Object $categoriaProduto = this.getCategoriaProduto();
        result = result * PRIME + ($categoriaProduto == null ? 43 : $categoriaProduto.hashCode());
        final java.lang.Object $valorMinimoAplicacao = this.getValorMinimoAplicacao();
        result = result * PRIME + ($valorMinimoAplicacao == null ? 43 : $valorMinimoAplicacao.hashCode());
        final java.lang.Object $valorMaximoAplicacao = this.getValorMaximoAplicacao();
        result = result * PRIME + ($valorMaximoAplicacao == null ? 43 : $valorMaximoAplicacao.hashCode());
        final java.lang.Object $taxaRemuneracao = this.getTaxaRemuneracao();
        result = result * PRIME + ($taxaRemuneracao == null ? 43 : $taxaRemuneracao.hashCode());
        final java.lang.Object $taxaRemuneracaoAnual = this.getTaxaRemuneracaoAnual();
        result = result * PRIME + ($taxaRemuneracaoAnual == null ? 43 : $taxaRemuneracaoAnual.hashCode());
        final java.lang.Object $taxaRemuneracaoMensal = this.getTaxaRemuneracaoMensal();
        result = result * PRIME + ($taxaRemuneracaoMensal == null ? 43 : $taxaRemuneracaoMensal.hashCode());
        final java.lang.Object $taxaRemuneracaoDiaria = this.getTaxaRemuneracaoDiaria();
        result = result * PRIME + ($taxaRemuneracaoDiaria == null ? 43 : $taxaRemuneracaoDiaria.hashCode());
        final java.lang.Object $tipoRemuneracao = this.getTipoRemuneracao();
        result = result * PRIME + ($tipoRemuneracao == null ? 43 : $tipoRemuneracao.hashCode());
        final java.lang.Object $periodicidadeRemuneracao = this.getPeriodicidadeRemuneracao();
        result = result * PRIME + ($periodicidadeRemuneracao == null ? 43 : $periodicidadeRemuneracao.hashCode());
        final java.lang.Object $taxaResgateAntecipado = this.getTaxaResgateAntecipado();
        result = result * PRIME + ($taxaResgateAntecipado == null ? 43 : $taxaResgateAntecipado.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $regrasRemuneracao = this.getRegrasRemuneracao();
        result = result * PRIME + ($regrasRemuneracao == null ? 43 : $regrasRemuneracao.hashCode());
        final java.lang.Object $configuracoesEspeciais = this.getConfiguracoesEspeciais();
        result = result * PRIME + ($configuracoesEspeciais == null ? 43 : $configuracoesEspeciais.hashCode());
        final java.lang.Object $dataInicioVigencia = this.getDataInicioVigencia();
        result = result * PRIME + ($dataInicioVigencia == null ? 43 : $dataInicioVigencia.hashCode());
        final java.lang.Object $dataFimVigencia = this.getDataFimVigencia();
        result = result * PRIME + ($dataFimVigencia == null ? 43 : $dataFimVigencia.hashCode());
        final java.lang.Object $usuarioCriacao = this.getUsuarioCriacao();
        result = result * PRIME + ($usuarioCriacao == null ? 43 : $usuarioCriacao.hashCode());
        final java.lang.Object $usuarioAprovacao = this.getUsuarioAprovacao();
        result = result * PRIME + ($usuarioAprovacao == null ? 43 : $usuarioAprovacao.hashCode());
        final java.lang.Object $dataAprovacao = this.getDataAprovacao();
        result = result * PRIME + ($dataAprovacao == null ? 43 : $dataAprovacao.hashCode());
        return result;
    }
}
