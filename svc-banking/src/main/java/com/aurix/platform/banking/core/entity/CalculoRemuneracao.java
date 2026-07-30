package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "calculos_remuneracao", schema = "aurix")
public class CalculoRemuneracao extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoCalculo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aplicacao_financeira_id", nullable = false)
    private AplicacaoFinanceira aplicacaoFinanceira;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_financeiro_id", nullable = false)
    private ProdutoFinanceiro produtoFinanceiro;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCalculo tipoCalculo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCalculo status = StatusCalculo.PENDENTE;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorBase;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaAplicada;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorRemuneracao;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorImposto;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorLiquido;
    @Column(nullable = false)
    private LocalDateTime dataCalculo;
    @Column
    private LocalDateTime dataProcessamento;
    @Column
    private LocalDateTime dataConclusao;
    @Column
    private Integer diasCalculo;
    @Column
    private Integer diasAno;
    @Column
    private Boolean calculoAutomatico = true;
    @Column
    private Boolean calculoCorreto = true;
    @Column
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_calculo", columnDefinition = "JSONB")
    private String detalhesCalculo;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "formulas_aplicadas", columnDefinition = "JSONB")
    private String formulasAplicadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "parametros_calculo", columnDefinition = "JSONB")
    private String parametrosCalculo;
    @Column
    private String usuarioCalculo;
    @Column
    private String sistemaOrigem;
    @Column
    private String codigoTransacao;
    @Column
    private String codigoLiquidacao;


    public enum TipoCalculo {
        JUROS_SIMPLES, JUROS_COMPOSTOS, RENDIMENTO_DIARIO, RENDIMENTO_MENSAL, RENDIMENTO_ANUAL, CORRECAO_MONETARIA, DIVIDENDO, COUPON, AMORTIZACAO, OUTROS;
    }


    public enum StatusCalculo {
        PENDENTE, PROCESSANDO, CONCLUIDO, FALHADO, CANCELADO, ESTORNADO, REVERSADO, AGUARDANDO_APROVACAO, REJEITADO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoCalculo() {
        return this.codigoCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public AplicacaoFinanceira getAplicacaoFinanceira() {
        return this.aplicacaoFinanceira;
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoFinanceiro getProdutoFinanceiro() {
        return this.produtoFinanceiro;
    }

    @java.lang.SuppressWarnings("all")
    public TipoCalculo getTipoCalculo() {
        return this.tipoCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public StatusCalculo getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorBase() {
        return this.valorBase;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaAplicada() {
        return this.taxaAplicada;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorRemuneracao() {
        return this.valorRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorImposto() {
        return this.valorImposto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorLiquido() {
        return this.valorLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCalculo() {
        return this.dataCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataConclusao() {
        return this.dataConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getDiasCalculo() {
        return this.diasCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getDiasAno() {
        return this.diasAno;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getCalculoAutomatico() {
        return this.calculoAutomatico;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getCalculoCorreto() {
        return this.calculoCorreto;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesCalculo() {
        return this.detalhesCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public String getFormulasAplicadas() {
        return this.formulasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getParametrosCalculo() {
        return this.parametrosCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioCalculo() {
        return this.usuarioCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public String getSistemaOrigem() {
        return this.sistemaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoTransacao() {
        return this.codigoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoLiquidacao() {
        return this.codigoLiquidacao;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoCalculo(final String codigoCalculo) {
        this.codigoCalculo = codigoCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setAplicacaoFinanceira(final AplicacaoFinanceira aplicacaoFinanceira) {
        this.aplicacaoFinanceira = aplicacaoFinanceira;
    }

    @java.lang.SuppressWarnings("all")
    public void setProdutoFinanceiro(final ProdutoFinanceiro produtoFinanceiro) {
        this.produtoFinanceiro = produtoFinanceiro;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoCalculo(final TipoCalculo tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusCalculo status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorBase(final BigDecimal valorBase) {
        this.valorBase = valorBase;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaAplicada(final BigDecimal taxaAplicada) {
        this.taxaAplicada = taxaAplicada;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorRemuneracao(final BigDecimal valorRemuneracao) {
        this.valorRemuneracao = valorRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorImposto(final BigDecimal valorImposto) {
        this.valorImposto = valorImposto;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorLiquido(final BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCalculo(final LocalDateTime dataCalculo) {
        this.dataCalculo = dataCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataConclusao(final LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDiasCalculo(final Integer diasCalculo) {
        this.diasCalculo = diasCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setDiasAno(final Integer diasAno) {
        this.diasAno = diasAno;
    }

    @java.lang.SuppressWarnings("all")
    public void setCalculoAutomatico(final Boolean calculoAutomatico) {
        this.calculoAutomatico = calculoAutomatico;
    }

    @java.lang.SuppressWarnings("all")
    public void setCalculoCorreto(final Boolean calculoCorreto) {
        this.calculoCorreto = calculoCorreto;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesCalculo(final String detalhesCalculo) {
        this.detalhesCalculo = detalhesCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setFormulasAplicadas(final String formulasAplicadas) {
        this.formulasAplicadas = formulasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setParametrosCalculo(final String parametrosCalculo) {
        this.parametrosCalculo = parametrosCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioCalculo(final String usuarioCalculo) {
        this.usuarioCalculo = usuarioCalculo;
    }

    @java.lang.SuppressWarnings("all")
    public void setSistemaOrigem(final String sistemaOrigem) {
        this.sistemaOrigem = sistemaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoTransacao(final String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoLiquidacao(final String codigoLiquidacao) {
        this.codigoLiquidacao = codigoLiquidacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "CalculoRemuneracao(id=" + this.getId() + ", codigoCalculo=" + this.getCodigoCalculo() + ", aplicacaoFinanceira=" + this.getAplicacaoFinanceira() + ", produtoFinanceiro=" + this.getProdutoFinanceiro() + ", tipoCalculo=" + this.getTipoCalculo() + ", status=" + this.getStatus() + ", valorBase=" + this.getValorBase() + ", taxaAplicada=" + this.getTaxaAplicada() + ", valorRemuneracao=" + this.getValorRemuneracao() + ", valorImposto=" + this.getValorImposto() + ", valorLiquido=" + this.getValorLiquido() + ", dataCalculo=" + this.getDataCalculo() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataConclusao=" + this.getDataConclusao() + ", diasCalculo=" + this.getDiasCalculo() + ", diasAno=" + this.getDiasAno() + ", calculoAutomatico=" + this.getCalculoAutomatico() + ", calculoCorreto=" + this.getCalculoCorreto() + ", observacoes=" + this.getObservacoes() + ", detalhesCalculo=" + this.getDetalhesCalculo() + ", formulasAplicadas=" + this.getFormulasAplicadas() + ", parametrosCalculo=" + this.getParametrosCalculo() + ", usuarioCalculo=" + this.getUsuarioCalculo() + ", sistemaOrigem=" + this.getSistemaOrigem() + ", codigoTransacao=" + this.getCodigoTransacao() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public CalculoRemuneracao() {
    }

    @java.lang.SuppressWarnings("all")
    public CalculoRemuneracao(final Long id, final String codigoCalculo, final AplicacaoFinanceira aplicacaoFinanceira, final ProdutoFinanceiro produtoFinanceiro, final TipoCalculo tipoCalculo, final StatusCalculo status, final BigDecimal valorBase, final BigDecimal taxaAplicada, final BigDecimal valorRemuneracao, final BigDecimal valorImposto, final BigDecimal valorLiquido, final LocalDateTime dataCalculo, final LocalDateTime dataProcessamento, final LocalDateTime dataConclusao, final Integer diasCalculo, final Integer diasAno, final Boolean calculoAutomatico, final Boolean calculoCorreto, final String observacoes, final String detalhesCalculo, final String formulasAplicadas, final String parametrosCalculo, final String usuarioCalculo, final String sistemaOrigem, final String codigoTransacao, final String codigoLiquidacao) {
        this.setId(id);
        this.codigoCalculo = codigoCalculo;
        this.aplicacaoFinanceira = aplicacaoFinanceira;
        this.produtoFinanceiro = produtoFinanceiro;
        this.tipoCalculo = tipoCalculo;
        this.status = status;
        this.valorBase = valorBase;
        this.taxaAplicada = taxaAplicada;
        this.valorRemuneracao = valorRemuneracao;
        this.valorImposto = valorImposto;
        this.valorLiquido = valorLiquido;
        this.dataCalculo = dataCalculo;
        this.dataProcessamento = dataProcessamento;
        this.dataConclusao = dataConclusao;
        this.diasCalculo = diasCalculo;
        this.diasAno = diasAno;
        this.calculoAutomatico = calculoAutomatico;
        this.calculoCorreto = calculoCorreto;
        this.observacoes = observacoes;
        this.detalhesCalculo = detalhesCalculo;
        this.formulasAplicadas = formulasAplicadas;
        this.parametrosCalculo = parametrosCalculo;
        this.usuarioCalculo = usuarioCalculo;
        this.sistemaOrigem = sistemaOrigem;
        this.codigoTransacao = codigoTransacao;
        this.codigoLiquidacao = codigoLiquidacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof CalculoRemuneracao)) return false;
        final CalculoRemuneracao other = (CalculoRemuneracao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$diasCalculo = this.getDiasCalculo();
        final java.lang.Object other$diasCalculo = other.getDiasCalculo();
        if (this$diasCalculo == null ? other$diasCalculo != null : !this$diasCalculo.equals(other$diasCalculo)) return false;
        final java.lang.Object this$diasAno = this.getDiasAno();
        final java.lang.Object other$diasAno = other.getDiasAno();
        if (this$diasAno == null ? other$diasAno != null : !this$diasAno.equals(other$diasAno)) return false;
        final java.lang.Object this$calculoAutomatico = this.getCalculoAutomatico();
        final java.lang.Object other$calculoAutomatico = other.getCalculoAutomatico();
        if (this$calculoAutomatico == null ? other$calculoAutomatico != null : !this$calculoAutomatico.equals(other$calculoAutomatico)) return false;
        final java.lang.Object this$calculoCorreto = this.getCalculoCorreto();
        final java.lang.Object other$calculoCorreto = other.getCalculoCorreto();
        if (this$calculoCorreto == null ? other$calculoCorreto != null : !this$calculoCorreto.equals(other$calculoCorreto)) return false;
        final java.lang.Object this$codigoCalculo = this.getCodigoCalculo();
        final java.lang.Object other$codigoCalculo = other.getCodigoCalculo();
        if (this$codigoCalculo == null ? other$codigoCalculo != null : !this$codigoCalculo.equals(other$codigoCalculo)) return false;
        final java.lang.Object this$aplicacaoFinanceira = this.getAplicacaoFinanceira();
        final java.lang.Object other$aplicacaoFinanceira = other.getAplicacaoFinanceira();
        if (this$aplicacaoFinanceira == null ? other$aplicacaoFinanceira != null : !this$aplicacaoFinanceira.equals(other$aplicacaoFinanceira)) return false;
        final java.lang.Object this$produtoFinanceiro = this.getProdutoFinanceiro();
        final java.lang.Object other$produtoFinanceiro = other.getProdutoFinanceiro();
        if (this$produtoFinanceiro == null ? other$produtoFinanceiro != null : !this$produtoFinanceiro.equals(other$produtoFinanceiro)) return false;
        final java.lang.Object this$tipoCalculo = this.getTipoCalculo();
        final java.lang.Object other$tipoCalculo = other.getTipoCalculo();
        if (this$tipoCalculo == null ? other$tipoCalculo != null : !this$tipoCalculo.equals(other$tipoCalculo)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$valorBase = this.getValorBase();
        final java.lang.Object other$valorBase = other.getValorBase();
        if (this$valorBase == null ? other$valorBase != null : !this$valorBase.equals(other$valorBase)) return false;
        final java.lang.Object this$taxaAplicada = this.getTaxaAplicada();
        final java.lang.Object other$taxaAplicada = other.getTaxaAplicada();
        if (this$taxaAplicada == null ? other$taxaAplicada != null : !this$taxaAplicada.equals(other$taxaAplicada)) return false;
        final java.lang.Object this$valorRemuneracao = this.getValorRemuneracao();
        final java.lang.Object other$valorRemuneracao = other.getValorRemuneracao();
        if (this$valorRemuneracao == null ? other$valorRemuneracao != null : !this$valorRemuneracao.equals(other$valorRemuneracao)) return false;
        final java.lang.Object this$valorImposto = this.getValorImposto();
        final java.lang.Object other$valorImposto = other.getValorImposto();
        if (this$valorImposto == null ? other$valorImposto != null : !this$valorImposto.equals(other$valorImposto)) return false;
        final java.lang.Object this$valorLiquido = this.getValorLiquido();
        final java.lang.Object other$valorLiquido = other.getValorLiquido();
        if (this$valorLiquido == null ? other$valorLiquido != null : !this$valorLiquido.equals(other$valorLiquido)) return false;
        final java.lang.Object this$dataCalculo = this.getDataCalculo();
        final java.lang.Object other$dataCalculo = other.getDataCalculo();
        if (this$dataCalculo == null ? other$dataCalculo != null : !this$dataCalculo.equals(other$dataCalculo)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$dataConclusao = this.getDataConclusao();
        final java.lang.Object other$dataConclusao = other.getDataConclusao();
        if (this$dataConclusao == null ? other$dataConclusao != null : !this$dataConclusao.equals(other$dataConclusao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesCalculo = this.getDetalhesCalculo();
        final java.lang.Object other$detalhesCalculo = other.getDetalhesCalculo();
        if (this$detalhesCalculo == null ? other$detalhesCalculo != null : !this$detalhesCalculo.equals(other$detalhesCalculo)) return false;
        final java.lang.Object this$formulasAplicadas = this.getFormulasAplicadas();
        final java.lang.Object other$formulasAplicadas = other.getFormulasAplicadas();
        if (this$formulasAplicadas == null ? other$formulasAplicadas != null : !this$formulasAplicadas.equals(other$formulasAplicadas)) return false;
        final java.lang.Object this$parametrosCalculo = this.getParametrosCalculo();
        final java.lang.Object other$parametrosCalculo = other.getParametrosCalculo();
        if (this$parametrosCalculo == null ? other$parametrosCalculo != null : !this$parametrosCalculo.equals(other$parametrosCalculo)) return false;
        final java.lang.Object this$usuarioCalculo = this.getUsuarioCalculo();
        final java.lang.Object other$usuarioCalculo = other.getUsuarioCalculo();
        if (this$usuarioCalculo == null ? other$usuarioCalculo != null : !this$usuarioCalculo.equals(other$usuarioCalculo)) return false;
        final java.lang.Object this$sistemaOrigem = this.getSistemaOrigem();
        final java.lang.Object other$sistemaOrigem = other.getSistemaOrigem();
        if (this$sistemaOrigem == null ? other$sistemaOrigem != null : !this$sistemaOrigem.equals(other$sistemaOrigem)) return false;
        final java.lang.Object this$codigoTransacao = this.getCodigoTransacao();
        final java.lang.Object other$codigoTransacao = other.getCodigoTransacao();
        if (this$codigoTransacao == null ? other$codigoTransacao != null : !this$codigoTransacao.equals(other$codigoTransacao)) return false;
        final java.lang.Object this$codigoLiquidacao = this.getCodigoLiquidacao();
        final java.lang.Object other$codigoLiquidacao = other.getCodigoLiquidacao();
        if (this$codigoLiquidacao == null ? other$codigoLiquidacao != null : !this$codigoLiquidacao.equals(other$codigoLiquidacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof CalculoRemuneracao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $diasCalculo = this.getDiasCalculo();
        result = result * PRIME + ($diasCalculo == null ? 43 : $diasCalculo.hashCode());
        final java.lang.Object $diasAno = this.getDiasAno();
        result = result * PRIME + ($diasAno == null ? 43 : $diasAno.hashCode());
        final java.lang.Object $calculoAutomatico = this.getCalculoAutomatico();
        result = result * PRIME + ($calculoAutomatico == null ? 43 : $calculoAutomatico.hashCode());
        final java.lang.Object $calculoCorreto = this.getCalculoCorreto();
        result = result * PRIME + ($calculoCorreto == null ? 43 : $calculoCorreto.hashCode());
        final java.lang.Object $codigoCalculo = this.getCodigoCalculo();
        result = result * PRIME + ($codigoCalculo == null ? 43 : $codigoCalculo.hashCode());
        final java.lang.Object $aplicacaoFinanceira = this.getAplicacaoFinanceira();
        result = result * PRIME + ($aplicacaoFinanceira == null ? 43 : $aplicacaoFinanceira.hashCode());
        final java.lang.Object $produtoFinanceiro = this.getProdutoFinanceiro();
        result = result * PRIME + ($produtoFinanceiro == null ? 43 : $produtoFinanceiro.hashCode());
        final java.lang.Object $tipoCalculo = this.getTipoCalculo();
        result = result * PRIME + ($tipoCalculo == null ? 43 : $tipoCalculo.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $valorBase = this.getValorBase();
        result = result * PRIME + ($valorBase == null ? 43 : $valorBase.hashCode());
        final java.lang.Object $taxaAplicada = this.getTaxaAplicada();
        result = result * PRIME + ($taxaAplicada == null ? 43 : $taxaAplicada.hashCode());
        final java.lang.Object $valorRemuneracao = this.getValorRemuneracao();
        result = result * PRIME + ($valorRemuneracao == null ? 43 : $valorRemuneracao.hashCode());
        final java.lang.Object $valorImposto = this.getValorImposto();
        result = result * PRIME + ($valorImposto == null ? 43 : $valorImposto.hashCode());
        final java.lang.Object $valorLiquido = this.getValorLiquido();
        result = result * PRIME + ($valorLiquido == null ? 43 : $valorLiquido.hashCode());
        final java.lang.Object $dataCalculo = this.getDataCalculo();
        result = result * PRIME + ($dataCalculo == null ? 43 : $dataCalculo.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $dataConclusao = this.getDataConclusao();
        result = result * PRIME + ($dataConclusao == null ? 43 : $dataConclusao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesCalculo = this.getDetalhesCalculo();
        result = result * PRIME + ($detalhesCalculo == null ? 43 : $detalhesCalculo.hashCode());
        final java.lang.Object $formulasAplicadas = this.getFormulasAplicadas();
        result = result * PRIME + ($formulasAplicadas == null ? 43 : $formulasAplicadas.hashCode());
        final java.lang.Object $parametrosCalculo = this.getParametrosCalculo();
        result = result * PRIME + ($parametrosCalculo == null ? 43 : $parametrosCalculo.hashCode());
        final java.lang.Object $usuarioCalculo = this.getUsuarioCalculo();
        result = result * PRIME + ($usuarioCalculo == null ? 43 : $usuarioCalculo.hashCode());
        final java.lang.Object $sistemaOrigem = this.getSistemaOrigem();
        result = result * PRIME + ($sistemaOrigem == null ? 43 : $sistemaOrigem.hashCode());
        final java.lang.Object $codigoTransacao = this.getCodigoTransacao();
        result = result * PRIME + ($codigoTransacao == null ? 43 : $codigoTransacao.hashCode());
        final java.lang.Object $codigoLiquidacao = this.getCodigoLiquidacao();
        result = result * PRIME + ($codigoLiquidacao == null ? 43 : $codigoLiquidacao.hashCode());
        return result;
    }
}
