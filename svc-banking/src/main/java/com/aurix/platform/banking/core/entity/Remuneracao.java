package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "remuneracoes", schema = "aurix")
public class Remuneracao extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoRemuneracao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aplicacao_financeira_id", nullable = false)
    private AplicacaoFinanceira aplicacaoFinanceira;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoRemuneracao tipoRemuneracao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRemuneracao status = StatusRemuneracao.PENDENTE;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorRemuneracao;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorBase;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaAplicada;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorImposto;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorLiquido;
    @Column(nullable = false)
    private LocalDateTime dataRemuneracao;
    @Column
    private LocalDateTime dataProcessamento;
    @Column
    private LocalDateTime dataConclusao;
    @Column
    private LocalDateTime dataVencimento;
    @Column
    private Integer diasRemuneracao;
    @Column
    private Boolean processamentoAutomatico = true;
    @Column
    private Boolean reversivel = true;
    @Column
    private Boolean estornada = false;
    @Column
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_remuneracao", columnDefinition = "JSONB")
    private String detalhesRemuneracao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_aplicadas", columnDefinition = "JSONB")
    private String regrasAplicadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "calculo_remuneracao", columnDefinition = "JSONB")
    private String calculoRemuneracao;
    @Column
    private String usuarioProcessamento;
    @Column
    private String sistemaOrigem;
    @Column
    private String codigoTransacao;
    @Column
    private String codigoLiquidacao;


    public enum TipoRemuneracao {
        JUROS, RENDIMENTO, DIVIDENDO, COUPON, AMORTIZACAO, CORRECAO_MONETARIA, BONUS, PREMIO, OUTROS;
    }


    public enum StatusRemuneracao {
        PENDENTE, PROCESSANDO, CONCLUIDA, FALHADA, CANCELADA, ESTORNADA, REVERSADA, AGUARDANDO_APROVACAO, REJEITADA;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoRemuneracao() {
        return this.codigoRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public AplicacaoFinanceira getAplicacaoFinanceira() {
        return this.aplicacaoFinanceira;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public TipoRemuneracao getTipoRemuneracao() {
        return this.tipoRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusRemuneracao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorRemuneracao() {
        return this.valorRemuneracao;
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
    public BigDecimal getValorImposto() {
        return this.valorImposto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorLiquido() {
        return this.valorLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataRemuneracao() {
        return this.dataRemuneracao;
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
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getDiasRemuneracao() {
        return this.diasRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getProcessamentoAutomatico() {
        return this.processamentoAutomatico;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getReversivel() {
        return this.reversivel;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getEstornada() {
        return this.estornada;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesRemuneracao() {
        return this.detalhesRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasAplicadas() {
        return this.regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getCalculoRemuneracao() {
        return this.calculoRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioProcessamento() {
        return this.usuarioProcessamento;
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
    public void setCodigoRemuneracao(final String codigoRemuneracao) {
        this.codigoRemuneracao = codigoRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setAplicacaoFinanceira(final AplicacaoFinanceira aplicacaoFinanceira) {
        this.aplicacaoFinanceira = aplicacaoFinanceira;
    }

    @java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoRemuneracao(final TipoRemuneracao tipoRemuneracao) {
        this.tipoRemuneracao = tipoRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusRemuneracao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorRemuneracao(final BigDecimal valorRemuneracao) {
        this.valorRemuneracao = valorRemuneracao;
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
    public void setValorImposto(final BigDecimal valorImposto) {
        this.valorImposto = valorImposto;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorLiquido(final BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataRemuneracao(final LocalDateTime dataRemuneracao) {
        this.dataRemuneracao = dataRemuneracao;
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
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDiasRemuneracao(final Integer diasRemuneracao) {
        this.diasRemuneracao = diasRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setProcessamentoAutomatico(final Boolean processamentoAutomatico) {
        this.processamentoAutomatico = processamentoAutomatico;
    }

    @java.lang.SuppressWarnings("all")
    public void setReversivel(final Boolean reversivel) {
        this.reversivel = reversivel;
    }

    @java.lang.SuppressWarnings("all")
    public void setEstornada(final Boolean estornada) {
        this.estornada = estornada;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesRemuneracao(final String detalhesRemuneracao) {
        this.detalhesRemuneracao = detalhesRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasAplicadas(final String regrasAplicadas) {
        this.regrasAplicadas = regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setCalculoRemuneracao(final String calculoRemuneracao) {
        this.calculoRemuneracao = calculoRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioProcessamento(final String usuarioProcessamento) {
        this.usuarioProcessamento = usuarioProcessamento;
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
        return "Remuneracao(id=" + this.getId() + ", codigoRemuneracao=" + this.getCodigoRemuneracao() + ", aplicacaoFinanceira=" + this.getAplicacaoFinanceira() + ", conta=" + this.getConta() + ", tipoRemuneracao=" + this.getTipoRemuneracao() + ", status=" + this.getStatus() + ", valorRemuneracao=" + this.getValorRemuneracao() + ", valorBase=" + this.getValorBase() + ", taxaAplicada=" + this.getTaxaAplicada() + ", valorImposto=" + this.getValorImposto() + ", valorLiquido=" + this.getValorLiquido() + ", dataRemuneracao=" + this.getDataRemuneracao() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataConclusao=" + this.getDataConclusao() + ", dataVencimento=" + this.getDataVencimento() + ", diasRemuneracao=" + this.getDiasRemuneracao() + ", processamentoAutomatico=" + this.getProcessamentoAutomatico() + ", reversivel=" + this.getReversivel() + ", estornada=" + this.getEstornada() + ", observacoes=" + this.getObservacoes() + ", detalhesRemuneracao=" + this.getDetalhesRemuneracao() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", calculoRemuneracao=" + this.getCalculoRemuneracao() + ", usuarioProcessamento=" + this.getUsuarioProcessamento() + ", sistemaOrigem=" + this.getSistemaOrigem() + ", codigoTransacao=" + this.getCodigoTransacao() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public Remuneracao() {
    }

    @java.lang.SuppressWarnings("all")
    public Remuneracao(final Long id, final String codigoRemuneracao, final AplicacaoFinanceira aplicacaoFinanceira, final Conta conta, final TipoRemuneracao tipoRemuneracao, final StatusRemuneracao status, final BigDecimal valorRemuneracao, final BigDecimal valorBase, final BigDecimal taxaAplicada, final BigDecimal valorImposto, final BigDecimal valorLiquido, final LocalDateTime dataRemuneracao, final LocalDateTime dataProcessamento, final LocalDateTime dataConclusao, final LocalDateTime dataVencimento, final Integer diasRemuneracao, final Boolean processamentoAutomatico, final Boolean reversivel, final Boolean estornada, final String observacoes, final String detalhesRemuneracao, final String regrasAplicadas, final String calculoRemuneracao, final String usuarioProcessamento, final String sistemaOrigem, final String codigoTransacao, final String codigoLiquidacao) {
        this.setId(id);
        this.codigoRemuneracao = codigoRemuneracao;
        this.aplicacaoFinanceira = aplicacaoFinanceira;
        this.conta = conta;
        this.tipoRemuneracao = tipoRemuneracao;
        this.status = status;
        this.valorRemuneracao = valorRemuneracao;
        this.valorBase = valorBase;
        this.taxaAplicada = taxaAplicada;
        this.valorImposto = valorImposto;
        this.valorLiquido = valorLiquido;
        this.dataRemuneracao = dataRemuneracao;
        this.dataProcessamento = dataProcessamento;
        this.dataConclusao = dataConclusao;
        this.dataVencimento = dataVencimento;
        this.diasRemuneracao = diasRemuneracao;
        this.processamentoAutomatico = processamentoAutomatico;
        this.reversivel = reversivel;
        this.estornada = estornada;
        this.observacoes = observacoes;
        this.detalhesRemuneracao = detalhesRemuneracao;
        this.regrasAplicadas = regrasAplicadas;
        this.calculoRemuneracao = calculoRemuneracao;
        this.usuarioProcessamento = usuarioProcessamento;
        this.sistemaOrigem = sistemaOrigem;
        this.codigoTransacao = codigoTransacao;
        this.codigoLiquidacao = codigoLiquidacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Remuneracao)) return false;
        final Remuneracao other = (Remuneracao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$diasRemuneracao = this.getDiasRemuneracao();
        final java.lang.Object other$diasRemuneracao = other.getDiasRemuneracao();
        if (this$diasRemuneracao == null ? other$diasRemuneracao != null : !this$diasRemuneracao.equals(other$diasRemuneracao)) return false;
        final java.lang.Object this$processamentoAutomatico = this.getProcessamentoAutomatico();
        final java.lang.Object other$processamentoAutomatico = other.getProcessamentoAutomatico();
        if (this$processamentoAutomatico == null ? other$processamentoAutomatico != null : !this$processamentoAutomatico.equals(other$processamentoAutomatico)) return false;
        final java.lang.Object this$reversivel = this.getReversivel();
        final java.lang.Object other$reversivel = other.getReversivel();
        if (this$reversivel == null ? other$reversivel != null : !this$reversivel.equals(other$reversivel)) return false;
        final java.lang.Object this$estornada = this.getEstornada();
        final java.lang.Object other$estornada = other.getEstornada();
        if (this$estornada == null ? other$estornada != null : !this$estornada.equals(other$estornada)) return false;
        final java.lang.Object this$codigoRemuneracao = this.getCodigoRemuneracao();
        final java.lang.Object other$codigoRemuneracao = other.getCodigoRemuneracao();
        if (this$codigoRemuneracao == null ? other$codigoRemuneracao != null : !this$codigoRemuneracao.equals(other$codigoRemuneracao)) return false;
        final java.lang.Object this$aplicacaoFinanceira = this.getAplicacaoFinanceira();
        final java.lang.Object other$aplicacaoFinanceira = other.getAplicacaoFinanceira();
        if (this$aplicacaoFinanceira == null ? other$aplicacaoFinanceira != null : !this$aplicacaoFinanceira.equals(other$aplicacaoFinanceira)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$tipoRemuneracao = this.getTipoRemuneracao();
        final java.lang.Object other$tipoRemuneracao = other.getTipoRemuneracao();
        if (this$tipoRemuneracao == null ? other$tipoRemuneracao != null : !this$tipoRemuneracao.equals(other$tipoRemuneracao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$valorRemuneracao = this.getValorRemuneracao();
        final java.lang.Object other$valorRemuneracao = other.getValorRemuneracao();
        if (this$valorRemuneracao == null ? other$valorRemuneracao != null : !this$valorRemuneracao.equals(other$valorRemuneracao)) return false;
        final java.lang.Object this$valorBase = this.getValorBase();
        final java.lang.Object other$valorBase = other.getValorBase();
        if (this$valorBase == null ? other$valorBase != null : !this$valorBase.equals(other$valorBase)) return false;
        final java.lang.Object this$taxaAplicada = this.getTaxaAplicada();
        final java.lang.Object other$taxaAplicada = other.getTaxaAplicada();
        if (this$taxaAplicada == null ? other$taxaAplicada != null : !this$taxaAplicada.equals(other$taxaAplicada)) return false;
        final java.lang.Object this$valorImposto = this.getValorImposto();
        final java.lang.Object other$valorImposto = other.getValorImposto();
        if (this$valorImposto == null ? other$valorImposto != null : !this$valorImposto.equals(other$valorImposto)) return false;
        final java.lang.Object this$valorLiquido = this.getValorLiquido();
        final java.lang.Object other$valorLiquido = other.getValorLiquido();
        if (this$valorLiquido == null ? other$valorLiquido != null : !this$valorLiquido.equals(other$valorLiquido)) return false;
        final java.lang.Object this$dataRemuneracao = this.getDataRemuneracao();
        final java.lang.Object other$dataRemuneracao = other.getDataRemuneracao();
        if (this$dataRemuneracao == null ? other$dataRemuneracao != null : !this$dataRemuneracao.equals(other$dataRemuneracao)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$dataConclusao = this.getDataConclusao();
        final java.lang.Object other$dataConclusao = other.getDataConclusao();
        if (this$dataConclusao == null ? other$dataConclusao != null : !this$dataConclusao.equals(other$dataConclusao)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesRemuneracao = this.getDetalhesRemuneracao();
        final java.lang.Object other$detalhesRemuneracao = other.getDetalhesRemuneracao();
        if (this$detalhesRemuneracao == null ? other$detalhesRemuneracao != null : !this$detalhesRemuneracao.equals(other$detalhesRemuneracao)) return false;
        final java.lang.Object this$regrasAplicadas = this.getRegrasAplicadas();
        final java.lang.Object other$regrasAplicadas = other.getRegrasAplicadas();
        if (this$regrasAplicadas == null ? other$regrasAplicadas != null : !this$regrasAplicadas.equals(other$regrasAplicadas)) return false;
        final java.lang.Object this$calculoRemuneracao = this.getCalculoRemuneracao();
        final java.lang.Object other$calculoRemuneracao = other.getCalculoRemuneracao();
        if (this$calculoRemuneracao == null ? other$calculoRemuneracao != null : !this$calculoRemuneracao.equals(other$calculoRemuneracao)) return false;
        final java.lang.Object this$usuarioProcessamento = this.getUsuarioProcessamento();
        final java.lang.Object other$usuarioProcessamento = other.getUsuarioProcessamento();
        if (this$usuarioProcessamento == null ? other$usuarioProcessamento != null : !this$usuarioProcessamento.equals(other$usuarioProcessamento)) return false;
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
        return other instanceof Remuneracao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $diasRemuneracao = this.getDiasRemuneracao();
        result = result * PRIME + ($diasRemuneracao == null ? 43 : $diasRemuneracao.hashCode());
        final java.lang.Object $processamentoAutomatico = this.getProcessamentoAutomatico();
        result = result * PRIME + ($processamentoAutomatico == null ? 43 : $processamentoAutomatico.hashCode());
        final java.lang.Object $reversivel = this.getReversivel();
        result = result * PRIME + ($reversivel == null ? 43 : $reversivel.hashCode());
        final java.lang.Object $estornada = this.getEstornada();
        result = result * PRIME + ($estornada == null ? 43 : $estornada.hashCode());
        final java.lang.Object $codigoRemuneracao = this.getCodigoRemuneracao();
        result = result * PRIME + ($codigoRemuneracao == null ? 43 : $codigoRemuneracao.hashCode());
        final java.lang.Object $aplicacaoFinanceira = this.getAplicacaoFinanceira();
        result = result * PRIME + ($aplicacaoFinanceira == null ? 43 : $aplicacaoFinanceira.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $tipoRemuneracao = this.getTipoRemuneracao();
        result = result * PRIME + ($tipoRemuneracao == null ? 43 : $tipoRemuneracao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $valorRemuneracao = this.getValorRemuneracao();
        result = result * PRIME + ($valorRemuneracao == null ? 43 : $valorRemuneracao.hashCode());
        final java.lang.Object $valorBase = this.getValorBase();
        result = result * PRIME + ($valorBase == null ? 43 : $valorBase.hashCode());
        final java.lang.Object $taxaAplicada = this.getTaxaAplicada();
        result = result * PRIME + ($taxaAplicada == null ? 43 : $taxaAplicada.hashCode());
        final java.lang.Object $valorImposto = this.getValorImposto();
        result = result * PRIME + ($valorImposto == null ? 43 : $valorImposto.hashCode());
        final java.lang.Object $valorLiquido = this.getValorLiquido();
        result = result * PRIME + ($valorLiquido == null ? 43 : $valorLiquido.hashCode());
        final java.lang.Object $dataRemuneracao = this.getDataRemuneracao();
        result = result * PRIME + ($dataRemuneracao == null ? 43 : $dataRemuneracao.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $dataConclusao = this.getDataConclusao();
        result = result * PRIME + ($dataConclusao == null ? 43 : $dataConclusao.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesRemuneracao = this.getDetalhesRemuneracao();
        result = result * PRIME + ($detalhesRemuneracao == null ? 43 : $detalhesRemuneracao.hashCode());
        final java.lang.Object $regrasAplicadas = this.getRegrasAplicadas();
        result = result * PRIME + ($regrasAplicadas == null ? 43 : $regrasAplicadas.hashCode());
        final java.lang.Object $calculoRemuneracao = this.getCalculoRemuneracao();
        result = result * PRIME + ($calculoRemuneracao == null ? 43 : $calculoRemuneracao.hashCode());
        final java.lang.Object $usuarioProcessamento = this.getUsuarioProcessamento();
        result = result * PRIME + ($usuarioProcessamento == null ? 43 : $usuarioProcessamento.hashCode());
        final java.lang.Object $sistemaOrigem = this.getSistemaOrigem();
        result = result * PRIME + ($sistemaOrigem == null ? 43 : $sistemaOrigem.hashCode());
        final java.lang.Object $codigoTransacao = this.getCodigoTransacao();
        result = result * PRIME + ($codigoTransacao == null ? 43 : $codigoTransacao.hashCode());
        final java.lang.Object $codigoLiquidacao = this.getCodigoLiquidacao();
        result = result * PRIME + ($codigoLiquidacao == null ? 43 : $codigoLiquidacao.hashCode());
        return result;
    }
}
