package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "historico_remuneracao", schema = "aurix")
public class HistoricoRemuneracao extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aplicacao_financeira_id", nullable = false)
    private AplicacaoFinanceira aplicacaoFinanceira;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "remuneracao_id")
    private Remuneracao remuneracao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoEvento tipoEvento;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorAnterior;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorPosterior;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorVariacao;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaAplicada;
    @Column(nullable = false)
    private LocalDateTime dataEvento;
    @Column
    private LocalDateTime dataProcessamento;
    @Column
    private LocalDateTime dataConclusao;
    @Column
    private Integer diasDecorridos;
    @Column
    private Integer diasRestantes;
    @Column
    private Boolean processamentoAutomatico = true;
    @Column
    private Boolean reversivel = true;
    @Column
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_evento", columnDefinition = "JSONB")
    private String detalhesEvento;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_aplicadas", columnDefinition = "JSONB")
    private String regrasAplicadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "calculo_aplicado", columnDefinition = "JSONB")
    private String calculoAplicado;
    @Column
    private String usuarioProcessamento;
    @Column
    private String sistemaOrigem;
    @Column
    private String codigoTransacao;
    @Column
    private String codigoLiquidacao;


    public enum TipoEvento {
        APLICACAO, REMUNERACAO, RESGATE, VENCIMENTO, RENOVACAO, REAPLICACAO, CANCELAMENTO, SUSPENSAO, BLOQUEIO, DESBLOQUEIO, AJUSTE, CORRECAO, ESTORNO, REVERSAO, OUTROS;
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
    public Remuneracao getRemuneracao() {
        return this.remuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoEvento getTipoEvento() {
        return this.tipoEvento;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAnterior() {
        return this.valorAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorPosterior() {
        return this.valorPosterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorVariacao() {
        return this.valorVariacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaAplicada() {
        return this.taxaAplicada;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataEvento() {
        return this.dataEvento;
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
    public Integer getDiasDecorridos() {
        return this.diasDecorridos;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getDiasRestantes() {
        return this.diasRestantes;
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
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesEvento() {
        return this.detalhesEvento;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasAplicadas() {
        return this.regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getCalculoAplicado() {
        return this.calculoAplicado;
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
    public void setAplicacaoFinanceira(final AplicacaoFinanceira aplicacaoFinanceira) {
        this.aplicacaoFinanceira = aplicacaoFinanceira;
    }

    @java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setRemuneracao(final Remuneracao remuneracao) {
        this.remuneracao = remuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoEvento(final TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorAnterior(final BigDecimal valorAnterior) {
        this.valorAnterior = valorAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorPosterior(final BigDecimal valorPosterior) {
        this.valorPosterior = valorPosterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorVariacao(final BigDecimal valorVariacao) {
        this.valorVariacao = valorVariacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaAplicada(final BigDecimal taxaAplicada) {
        this.taxaAplicada = taxaAplicada;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEvento(final LocalDateTime dataEvento) {
        this.dataEvento = dataEvento;
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
    public void setDiasDecorridos(final Integer diasDecorridos) {
        this.diasDecorridos = diasDecorridos;
    }

    @java.lang.SuppressWarnings("all")
    public void setDiasRestantes(final Integer diasRestantes) {
        this.diasRestantes = diasRestantes;
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
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesEvento(final String detalhesEvento) {
        this.detalhesEvento = detalhesEvento;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasAplicadas(final String regrasAplicadas) {
        this.regrasAplicadas = regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setCalculoAplicado(final String calculoAplicado) {
        this.calculoAplicado = calculoAplicado;
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
        return "HistoricoRemuneracao(id=" + this.getId() + ", aplicacaoFinanceira=" + this.getAplicacaoFinanceira() + ", conta=" + this.getConta() + ", remuneracao=" + this.getRemuneracao() + ", tipoEvento=" + this.getTipoEvento() + ", valorAnterior=" + this.getValorAnterior() + ", valorPosterior=" + this.getValorPosterior() + ", valorVariacao=" + this.getValorVariacao() + ", taxaAplicada=" + this.getTaxaAplicada() + ", dataEvento=" + this.getDataEvento() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataConclusao=" + this.getDataConclusao() + ", diasDecorridos=" + this.getDiasDecorridos() + ", diasRestantes=" + this.getDiasRestantes() + ", processamentoAutomatico=" + this.getProcessamentoAutomatico() + ", reversivel=" + this.getReversivel() + ", observacoes=" + this.getObservacoes() + ", detalhesEvento=" + this.getDetalhesEvento() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", calculoAplicado=" + this.getCalculoAplicado() + ", usuarioProcessamento=" + this.getUsuarioProcessamento() + ", sistemaOrigem=" + this.getSistemaOrigem() + ", codigoTransacao=" + this.getCodigoTransacao() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public HistoricoRemuneracao() {
    }

    @java.lang.SuppressWarnings("all")
    public HistoricoRemuneracao(final Long id, final AplicacaoFinanceira aplicacaoFinanceira, final Conta conta, final Remuneracao remuneracao, final TipoEvento tipoEvento, final BigDecimal valorAnterior, final BigDecimal valorPosterior, final BigDecimal valorVariacao, final BigDecimal taxaAplicada, final LocalDateTime dataEvento, final LocalDateTime dataProcessamento, final LocalDateTime dataConclusao, final Integer diasDecorridos, final Integer diasRestantes, final Boolean processamentoAutomatico, final Boolean reversivel, final String observacoes, final String detalhesEvento, final String regrasAplicadas, final String calculoAplicado, final String usuarioProcessamento, final String sistemaOrigem, final String codigoTransacao, final String codigoLiquidacao) {
        this.setId(id);
        this.aplicacaoFinanceira = aplicacaoFinanceira;
        this.conta = conta;
        this.remuneracao = remuneracao;
        this.tipoEvento = tipoEvento;
        this.valorAnterior = valorAnterior;
        this.valorPosterior = valorPosterior;
        this.valorVariacao = valorVariacao;
        this.taxaAplicada = taxaAplicada;
        this.dataEvento = dataEvento;
        this.dataProcessamento = dataProcessamento;
        this.dataConclusao = dataConclusao;
        this.diasDecorridos = diasDecorridos;
        this.diasRestantes = diasRestantes;
        this.processamentoAutomatico = processamentoAutomatico;
        this.reversivel = reversivel;
        this.observacoes = observacoes;
        this.detalhesEvento = detalhesEvento;
        this.regrasAplicadas = regrasAplicadas;
        this.calculoAplicado = calculoAplicado;
        this.usuarioProcessamento = usuarioProcessamento;
        this.sistemaOrigem = sistemaOrigem;
        this.codigoTransacao = codigoTransacao;
        this.codigoLiquidacao = codigoLiquidacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof HistoricoRemuneracao)) return false;
        final HistoricoRemuneracao other = (HistoricoRemuneracao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$diasDecorridos = this.getDiasDecorridos();
        final java.lang.Object other$diasDecorridos = other.getDiasDecorridos();
        if (this$diasDecorridos == null ? other$diasDecorridos != null : !this$diasDecorridos.equals(other$diasDecorridos)) return false;
        final java.lang.Object this$diasRestantes = this.getDiasRestantes();
        final java.lang.Object other$diasRestantes = other.getDiasRestantes();
        if (this$diasRestantes == null ? other$diasRestantes != null : !this$diasRestantes.equals(other$diasRestantes)) return false;
        final java.lang.Object this$processamentoAutomatico = this.getProcessamentoAutomatico();
        final java.lang.Object other$processamentoAutomatico = other.getProcessamentoAutomatico();
        if (this$processamentoAutomatico == null ? other$processamentoAutomatico != null : !this$processamentoAutomatico.equals(other$processamentoAutomatico)) return false;
        final java.lang.Object this$reversivel = this.getReversivel();
        final java.lang.Object other$reversivel = other.getReversivel();
        if (this$reversivel == null ? other$reversivel != null : !this$reversivel.equals(other$reversivel)) return false;
        final java.lang.Object this$aplicacaoFinanceira = this.getAplicacaoFinanceira();
        final java.lang.Object other$aplicacaoFinanceira = other.getAplicacaoFinanceira();
        if (this$aplicacaoFinanceira == null ? other$aplicacaoFinanceira != null : !this$aplicacaoFinanceira.equals(other$aplicacaoFinanceira)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$remuneracao = this.getRemuneracao();
        final java.lang.Object other$remuneracao = other.getRemuneracao();
        if (this$remuneracao == null ? other$remuneracao != null : !this$remuneracao.equals(other$remuneracao)) return false;
        final java.lang.Object this$tipoEvento = this.getTipoEvento();
        final java.lang.Object other$tipoEvento = other.getTipoEvento();
        if (this$tipoEvento == null ? other$tipoEvento != null : !this$tipoEvento.equals(other$tipoEvento)) return false;
        final java.lang.Object this$valorAnterior = this.getValorAnterior();
        final java.lang.Object other$valorAnterior = other.getValorAnterior();
        if (this$valorAnterior == null ? other$valorAnterior != null : !this$valorAnterior.equals(other$valorAnterior)) return false;
        final java.lang.Object this$valorPosterior = this.getValorPosterior();
        final java.lang.Object other$valorPosterior = other.getValorPosterior();
        if (this$valorPosterior == null ? other$valorPosterior != null : !this$valorPosterior.equals(other$valorPosterior)) return false;
        final java.lang.Object this$valorVariacao = this.getValorVariacao();
        final java.lang.Object other$valorVariacao = other.getValorVariacao();
        if (this$valorVariacao == null ? other$valorVariacao != null : !this$valorVariacao.equals(other$valorVariacao)) return false;
        final java.lang.Object this$taxaAplicada = this.getTaxaAplicada();
        final java.lang.Object other$taxaAplicada = other.getTaxaAplicada();
        if (this$taxaAplicada == null ? other$taxaAplicada != null : !this$taxaAplicada.equals(other$taxaAplicada)) return false;
        final java.lang.Object this$dataEvento = this.getDataEvento();
        final java.lang.Object other$dataEvento = other.getDataEvento();
        if (this$dataEvento == null ? other$dataEvento != null : !this$dataEvento.equals(other$dataEvento)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$dataConclusao = this.getDataConclusao();
        final java.lang.Object other$dataConclusao = other.getDataConclusao();
        if (this$dataConclusao == null ? other$dataConclusao != null : !this$dataConclusao.equals(other$dataConclusao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesEvento = this.getDetalhesEvento();
        final java.lang.Object other$detalhesEvento = other.getDetalhesEvento();
        if (this$detalhesEvento == null ? other$detalhesEvento != null : !this$detalhesEvento.equals(other$detalhesEvento)) return false;
        final java.lang.Object this$regrasAplicadas = this.getRegrasAplicadas();
        final java.lang.Object other$regrasAplicadas = other.getRegrasAplicadas();
        if (this$regrasAplicadas == null ? other$regrasAplicadas != null : !this$regrasAplicadas.equals(other$regrasAplicadas)) return false;
        final java.lang.Object this$calculoAplicado = this.getCalculoAplicado();
        final java.lang.Object other$calculoAplicado = other.getCalculoAplicado();
        if (this$calculoAplicado == null ? other$calculoAplicado != null : !this$calculoAplicado.equals(other$calculoAplicado)) return false;
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
        return other instanceof HistoricoRemuneracao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $diasDecorridos = this.getDiasDecorridos();
        result = result * PRIME + ($diasDecorridos == null ? 43 : $diasDecorridos.hashCode());
        final java.lang.Object $diasRestantes = this.getDiasRestantes();
        result = result * PRIME + ($diasRestantes == null ? 43 : $diasRestantes.hashCode());
        final java.lang.Object $processamentoAutomatico = this.getProcessamentoAutomatico();
        result = result * PRIME + ($processamentoAutomatico == null ? 43 : $processamentoAutomatico.hashCode());
        final java.lang.Object $reversivel = this.getReversivel();
        result = result * PRIME + ($reversivel == null ? 43 : $reversivel.hashCode());
        final java.lang.Object $aplicacaoFinanceira = this.getAplicacaoFinanceira();
        result = result * PRIME + ($aplicacaoFinanceira == null ? 43 : $aplicacaoFinanceira.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $remuneracao = this.getRemuneracao();
        result = result * PRIME + ($remuneracao == null ? 43 : $remuneracao.hashCode());
        final java.lang.Object $tipoEvento = this.getTipoEvento();
        result = result * PRIME + ($tipoEvento == null ? 43 : $tipoEvento.hashCode());
        final java.lang.Object $valorAnterior = this.getValorAnterior();
        result = result * PRIME + ($valorAnterior == null ? 43 : $valorAnterior.hashCode());
        final java.lang.Object $valorPosterior = this.getValorPosterior();
        result = result * PRIME + ($valorPosterior == null ? 43 : $valorPosterior.hashCode());
        final java.lang.Object $valorVariacao = this.getValorVariacao();
        result = result * PRIME + ($valorVariacao == null ? 43 : $valorVariacao.hashCode());
        final java.lang.Object $taxaAplicada = this.getTaxaAplicada();
        result = result * PRIME + ($taxaAplicada == null ? 43 : $taxaAplicada.hashCode());
        final java.lang.Object $dataEvento = this.getDataEvento();
        result = result * PRIME + ($dataEvento == null ? 43 : $dataEvento.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $dataConclusao = this.getDataConclusao();
        result = result * PRIME + ($dataConclusao == null ? 43 : $dataConclusao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesEvento = this.getDetalhesEvento();
        result = result * PRIME + ($detalhesEvento == null ? 43 : $detalhesEvento.hashCode());
        final java.lang.Object $regrasAplicadas = this.getRegrasAplicadas();
        result = result * PRIME + ($regrasAplicadas == null ? 43 : $regrasAplicadas.hashCode());
        final java.lang.Object $calculoAplicado = this.getCalculoAplicado();
        result = result * PRIME + ($calculoAplicado == null ? 43 : $calculoAplicado.hashCode());
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
