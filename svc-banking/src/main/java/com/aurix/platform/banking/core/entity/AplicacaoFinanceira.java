package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "aplicacoes_financeiras", schema = "aurix")
public class AplicacaoFinanceira extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoAplicacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_financeiro_id", nullable = false)
    private ProdutoFinanceiro produtoFinanceiro;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAplicacao status = StatusAplicacao.ATIVA;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorAplicacao;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorAtual;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorResgate;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorRendimento;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaRemuneracao;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaRemuneracaoAtual;
    @Column(nullable = false)
    private LocalDateTime dataAplicacao;
    @Column
    private LocalDateTime dataVencimento;
    @Column
    private LocalDateTime dataResgate;
    @Column
    private LocalDateTime dataUltimaRemuneracao;
    @Column
    private LocalDateTime dataProximaRemuneracao;
    @Column
    private Integer prazoDias;
    @Column
    private Integer diasDecorridos;
    @Column
    private Integer diasRestantes;
    @Column
    private Boolean permiteResgateAntecipado = false;
    @Column(precision = 5, scale = 4)
    private BigDecimal taxaResgateAntecipado;
    @Column
    private Boolean resgateAntecipado = false;
    @Column
    private Boolean renovacaoAutomatica = false;
    @Column
    private Boolean reaplicacaoAutomatica = false;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_aplicacao", columnDefinition = "JSONB")
    private String detalhesAplicacao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_aplicadas", columnDefinition = "JSONB")
    private String regrasAplicadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "historico_remuneracao", columnDefinition = "JSONB")
    private String historicoRemuneracao;
    @Column
    private String usuarioAplicacao;
    @Column
    private String usuarioResgate;
    @Column
    private String sistemaOrigem;
    @Column
    private String codigoTransacao;
    @Column
    private String codigoLiquidacao;


    public enum StatusAplicacao {
        ATIVA, RESGATADA, VENCIDA, CANCELADA, SUSPENSA, BLOQUEADA, RENOVADA, REAPLICADA;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoAplicacao() {
        return this.codigoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public ProdutoFinanceiro getProdutoFinanceiro() {
        return this.produtoFinanceiro;
    }

    @java.lang.SuppressWarnings("all")
    public StatusAplicacao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorAplicacao() {
        return this.valorAplicacao;
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
    public BigDecimal getTaxaRemuneracaoAtual() {
        return this.taxaRemuneracaoAtual;
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
    public LocalDateTime getDataUltimaRemuneracao() {
        return this.dataUltimaRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProximaRemuneracao() {
        return this.dataProximaRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getPrazoDias() {
        return this.prazoDias;
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
    public Boolean getPermiteResgateAntecipado() {
        return this.permiteResgateAntecipado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaResgateAntecipado() {
        return this.taxaResgateAntecipado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getResgateAntecipado() {
        return this.resgateAntecipado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getRenovacaoAutomatica() {
        return this.renovacaoAutomatica;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getReaplicacaoAutomatica() {
        return this.reaplicacaoAutomatica;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesAplicacao() {
        return this.detalhesAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasAplicadas() {
        return this.regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getHistoricoRemuneracao() {
        return this.historicoRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioAplicacao() {
        return this.usuarioAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioResgate() {
        return this.usuarioResgate;
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
    public void setCodigoAplicacao(final String codigoAplicacao) {
        this.codigoAplicacao = codigoAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setProdutoFinanceiro(final ProdutoFinanceiro produtoFinanceiro) {
        this.produtoFinanceiro = produtoFinanceiro;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusAplicacao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorAplicacao(final BigDecimal valorAplicacao) {
        this.valorAplicacao = valorAplicacao;
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
    public void setTaxaRemuneracaoAtual(final BigDecimal taxaRemuneracaoAtual) {
        this.taxaRemuneracaoAtual = taxaRemuneracaoAtual;
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

    @java.lang.SuppressWarnings("all")
    public void setDataUltimaRemuneracao(final LocalDateTime dataUltimaRemuneracao) {
        this.dataUltimaRemuneracao = dataUltimaRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataProximaRemuneracao(final LocalDateTime dataProximaRemuneracao) {
        this.dataProximaRemuneracao = dataProximaRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrazoDias(final Integer prazoDias) {
        this.prazoDias = prazoDias;
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
    public void setPermiteResgateAntecipado(final Boolean permiteResgateAntecipado) {
        this.permiteResgateAntecipado = permiteResgateAntecipado;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaResgateAntecipado(final BigDecimal taxaResgateAntecipado) {
        this.taxaResgateAntecipado = taxaResgateAntecipado;
    }

    @java.lang.SuppressWarnings("all")
    public void setResgateAntecipado(final Boolean resgateAntecipado) {
        this.resgateAntecipado = resgateAntecipado;
    }

    @java.lang.SuppressWarnings("all")
    public void setRenovacaoAutomatica(final Boolean renovacaoAutomatica) {
        this.renovacaoAutomatica = renovacaoAutomatica;
    }

    @java.lang.SuppressWarnings("all")
    public void setReaplicacaoAutomatica(final Boolean reaplicacaoAutomatica) {
        this.reaplicacaoAutomatica = reaplicacaoAutomatica;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesAplicacao(final String detalhesAplicacao) {
        this.detalhesAplicacao = detalhesAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasAplicadas(final String regrasAplicadas) {
        this.regrasAplicadas = regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setHistoricoRemuneracao(final String historicoRemuneracao) {
        this.historicoRemuneracao = historicoRemuneracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioAplicacao(final String usuarioAplicacao) {
        this.usuarioAplicacao = usuarioAplicacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioResgate(final String usuarioResgate) {
        this.usuarioResgate = usuarioResgate;
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
        return "AplicacaoFinanceira(id=" + this.getId() + ", codigoAplicacao=" + this.getCodigoAplicacao() + ", conta=" + this.getConta() + ", produtoFinanceiro=" + this.getProdutoFinanceiro() + ", status=" + this.getStatus() + ", valorAplicacao=" + this.getValorAplicacao() + ", valorAtual=" + this.getValorAtual() + ", valorResgate=" + this.getValorResgate() + ", valorRendimento=" + this.getValorRendimento() + ", taxaRemuneracao=" + this.getTaxaRemuneracao() + ", taxaRemuneracaoAtual=" + this.getTaxaRemuneracaoAtual() + ", dataAplicacao=" + this.getDataAplicacao() + ", dataVencimento=" + this.getDataVencimento() + ", dataResgate=" + this.getDataResgate() + ", dataUltimaRemuneracao=" + this.getDataUltimaRemuneracao() + ", dataProximaRemuneracao=" + this.getDataProximaRemuneracao() + ", prazoDias=" + this.getPrazoDias() + ", diasDecorridos=" + this.getDiasDecorridos() + ", diasRestantes=" + this.getDiasRestantes() + ", permiteResgateAntecipado=" + this.getPermiteResgateAntecipado() + ", taxaResgateAntecipado=" + this.getTaxaResgateAntecipado() + ", resgateAntecipado=" + this.getResgateAntecipado() + ", renovacaoAutomatica=" + this.getRenovacaoAutomatica() + ", reaplicacaoAutomatica=" + this.getReaplicacaoAutomatica() + ", observacoes=" + this.getObservacoes() + ", detalhesAplicacao=" + this.getDetalhesAplicacao() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", historicoRemuneracao=" + this.getHistoricoRemuneracao() + ", usuarioAplicacao=" + this.getUsuarioAplicacao() + ", usuarioResgate=" + this.getUsuarioResgate() + ", sistemaOrigem=" + this.getSistemaOrigem() + ", codigoTransacao=" + this.getCodigoTransacao() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public AplicacaoFinanceira() {
    }

    @java.lang.SuppressWarnings("all")
    public AplicacaoFinanceira(final Long id, final String codigoAplicacao, final Conta conta, final ProdutoFinanceiro produtoFinanceiro, final StatusAplicacao status, final BigDecimal valorAplicacao, final BigDecimal valorAtual, final BigDecimal valorResgate, final BigDecimal valorRendimento, final BigDecimal taxaRemuneracao, final BigDecimal taxaRemuneracaoAtual, final LocalDateTime dataAplicacao, final LocalDateTime dataVencimento, final LocalDateTime dataResgate, final LocalDateTime dataUltimaRemuneracao, final LocalDateTime dataProximaRemuneracao, final Integer prazoDias, final Integer diasDecorridos, final Integer diasRestantes, final Boolean permiteResgateAntecipado, final BigDecimal taxaResgateAntecipado, final Boolean resgateAntecipado, final Boolean renovacaoAutomatica, final Boolean reaplicacaoAutomatica, final String observacoes, final String detalhesAplicacao, final String regrasAplicadas, final String historicoRemuneracao, final String usuarioAplicacao, final String usuarioResgate, final String sistemaOrigem, final String codigoTransacao, final String codigoLiquidacao) {
        this.setId(id);
        this.codigoAplicacao = codigoAplicacao;
        this.conta = conta;
        this.produtoFinanceiro = produtoFinanceiro;
        this.status = status;
        this.valorAplicacao = valorAplicacao;
        this.valorAtual = valorAtual;
        this.valorResgate = valorResgate;
        this.valorRendimento = valorRendimento;
        this.taxaRemuneracao = taxaRemuneracao;
        this.taxaRemuneracaoAtual = taxaRemuneracaoAtual;
        this.dataAplicacao = dataAplicacao;
        this.dataVencimento = dataVencimento;
        this.dataResgate = dataResgate;
        this.dataUltimaRemuneracao = dataUltimaRemuneracao;
        this.dataProximaRemuneracao = dataProximaRemuneracao;
        this.prazoDias = prazoDias;
        this.diasDecorridos = diasDecorridos;
        this.diasRestantes = diasRestantes;
        this.permiteResgateAntecipado = permiteResgateAntecipado;
        this.taxaResgateAntecipado = taxaResgateAntecipado;
        this.resgateAntecipado = resgateAntecipado;
        this.renovacaoAutomatica = renovacaoAutomatica;
        this.reaplicacaoAutomatica = reaplicacaoAutomatica;
        this.observacoes = observacoes;
        this.detalhesAplicacao = detalhesAplicacao;
        this.regrasAplicadas = regrasAplicadas;
        this.historicoRemuneracao = historicoRemuneracao;
        this.usuarioAplicacao = usuarioAplicacao;
        this.usuarioResgate = usuarioResgate;
        this.sistemaOrigem = sistemaOrigem;
        this.codigoTransacao = codigoTransacao;
        this.codigoLiquidacao = codigoLiquidacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof AplicacaoFinanceira)) return false;
        final AplicacaoFinanceira other = (AplicacaoFinanceira) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$prazoDias = this.getPrazoDias();
        final java.lang.Object other$prazoDias = other.getPrazoDias();
        if (this$prazoDias == null ? other$prazoDias != null : !this$prazoDias.equals(other$prazoDias)) return false;
        final java.lang.Object this$diasDecorridos = this.getDiasDecorridos();
        final java.lang.Object other$diasDecorridos = other.getDiasDecorridos();
        if (this$diasDecorridos == null ? other$diasDecorridos != null : !this$diasDecorridos.equals(other$diasDecorridos)) return false;
        final java.lang.Object this$diasRestantes = this.getDiasRestantes();
        final java.lang.Object other$diasRestantes = other.getDiasRestantes();
        if (this$diasRestantes == null ? other$diasRestantes != null : !this$diasRestantes.equals(other$diasRestantes)) return false;
        final java.lang.Object this$permiteResgateAntecipado = this.getPermiteResgateAntecipado();
        final java.lang.Object other$permiteResgateAntecipado = other.getPermiteResgateAntecipado();
        if (this$permiteResgateAntecipado == null ? other$permiteResgateAntecipado != null : !this$permiteResgateAntecipado.equals(other$permiteResgateAntecipado)) return false;
        final java.lang.Object this$resgateAntecipado = this.getResgateAntecipado();
        final java.lang.Object other$resgateAntecipado = other.getResgateAntecipado();
        if (this$resgateAntecipado == null ? other$resgateAntecipado != null : !this$resgateAntecipado.equals(other$resgateAntecipado)) return false;
        final java.lang.Object this$renovacaoAutomatica = this.getRenovacaoAutomatica();
        final java.lang.Object other$renovacaoAutomatica = other.getRenovacaoAutomatica();
        if (this$renovacaoAutomatica == null ? other$renovacaoAutomatica != null : !this$renovacaoAutomatica.equals(other$renovacaoAutomatica)) return false;
        final java.lang.Object this$reaplicacaoAutomatica = this.getReaplicacaoAutomatica();
        final java.lang.Object other$reaplicacaoAutomatica = other.getReaplicacaoAutomatica();
        if (this$reaplicacaoAutomatica == null ? other$reaplicacaoAutomatica != null : !this$reaplicacaoAutomatica.equals(other$reaplicacaoAutomatica)) return false;
        final java.lang.Object this$codigoAplicacao = this.getCodigoAplicacao();
        final java.lang.Object other$codigoAplicacao = other.getCodigoAplicacao();
        if (this$codigoAplicacao == null ? other$codigoAplicacao != null : !this$codigoAplicacao.equals(other$codigoAplicacao)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$produtoFinanceiro = this.getProdutoFinanceiro();
        final java.lang.Object other$produtoFinanceiro = other.getProdutoFinanceiro();
        if (this$produtoFinanceiro == null ? other$produtoFinanceiro != null : !this$produtoFinanceiro.equals(other$produtoFinanceiro)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$valorAplicacao = this.getValorAplicacao();
        final java.lang.Object other$valorAplicacao = other.getValorAplicacao();
        if (this$valorAplicacao == null ? other$valorAplicacao != null : !this$valorAplicacao.equals(other$valorAplicacao)) return false;
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
        final java.lang.Object this$taxaRemuneracaoAtual = this.getTaxaRemuneracaoAtual();
        final java.lang.Object other$taxaRemuneracaoAtual = other.getTaxaRemuneracaoAtual();
        if (this$taxaRemuneracaoAtual == null ? other$taxaRemuneracaoAtual != null : !this$taxaRemuneracaoAtual.equals(other$taxaRemuneracaoAtual)) return false;
        final java.lang.Object this$dataAplicacao = this.getDataAplicacao();
        final java.lang.Object other$dataAplicacao = other.getDataAplicacao();
        if (this$dataAplicacao == null ? other$dataAplicacao != null : !this$dataAplicacao.equals(other$dataAplicacao)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$dataResgate = this.getDataResgate();
        final java.lang.Object other$dataResgate = other.getDataResgate();
        if (this$dataResgate == null ? other$dataResgate != null : !this$dataResgate.equals(other$dataResgate)) return false;
        final java.lang.Object this$dataUltimaRemuneracao = this.getDataUltimaRemuneracao();
        final java.lang.Object other$dataUltimaRemuneracao = other.getDataUltimaRemuneracao();
        if (this$dataUltimaRemuneracao == null ? other$dataUltimaRemuneracao != null : !this$dataUltimaRemuneracao.equals(other$dataUltimaRemuneracao)) return false;
        final java.lang.Object this$dataProximaRemuneracao = this.getDataProximaRemuneracao();
        final java.lang.Object other$dataProximaRemuneracao = other.getDataProximaRemuneracao();
        if (this$dataProximaRemuneracao == null ? other$dataProximaRemuneracao != null : !this$dataProximaRemuneracao.equals(other$dataProximaRemuneracao)) return false;
        final java.lang.Object this$taxaResgateAntecipado = this.getTaxaResgateAntecipado();
        final java.lang.Object other$taxaResgateAntecipado = other.getTaxaResgateAntecipado();
        if (this$taxaResgateAntecipado == null ? other$taxaResgateAntecipado != null : !this$taxaResgateAntecipado.equals(other$taxaResgateAntecipado)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesAplicacao = this.getDetalhesAplicacao();
        final java.lang.Object other$detalhesAplicacao = other.getDetalhesAplicacao();
        if (this$detalhesAplicacao == null ? other$detalhesAplicacao != null : !this$detalhesAplicacao.equals(other$detalhesAplicacao)) return false;
        final java.lang.Object this$regrasAplicadas = this.getRegrasAplicadas();
        final java.lang.Object other$regrasAplicadas = other.getRegrasAplicadas();
        if (this$regrasAplicadas == null ? other$regrasAplicadas != null : !this$regrasAplicadas.equals(other$regrasAplicadas)) return false;
        final java.lang.Object this$historicoRemuneracao = this.getHistoricoRemuneracao();
        final java.lang.Object other$historicoRemuneracao = other.getHistoricoRemuneracao();
        if (this$historicoRemuneracao == null ? other$historicoRemuneracao != null : !this$historicoRemuneracao.equals(other$historicoRemuneracao)) return false;
        final java.lang.Object this$usuarioAplicacao = this.getUsuarioAplicacao();
        final java.lang.Object other$usuarioAplicacao = other.getUsuarioAplicacao();
        if (this$usuarioAplicacao == null ? other$usuarioAplicacao != null : !this$usuarioAplicacao.equals(other$usuarioAplicacao)) return false;
        final java.lang.Object this$usuarioResgate = this.getUsuarioResgate();
        final java.lang.Object other$usuarioResgate = other.getUsuarioResgate();
        if (this$usuarioResgate == null ? other$usuarioResgate != null : !this$usuarioResgate.equals(other$usuarioResgate)) return false;
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
        return other instanceof AplicacaoFinanceira;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $prazoDias = this.getPrazoDias();
        result = result * PRIME + ($prazoDias == null ? 43 : $prazoDias.hashCode());
        final java.lang.Object $diasDecorridos = this.getDiasDecorridos();
        result = result * PRIME + ($diasDecorridos == null ? 43 : $diasDecorridos.hashCode());
        final java.lang.Object $diasRestantes = this.getDiasRestantes();
        result = result * PRIME + ($diasRestantes == null ? 43 : $diasRestantes.hashCode());
        final java.lang.Object $permiteResgateAntecipado = this.getPermiteResgateAntecipado();
        result = result * PRIME + ($permiteResgateAntecipado == null ? 43 : $permiteResgateAntecipado.hashCode());
        final java.lang.Object $resgateAntecipado = this.getResgateAntecipado();
        result = result * PRIME + ($resgateAntecipado == null ? 43 : $resgateAntecipado.hashCode());
        final java.lang.Object $renovacaoAutomatica = this.getRenovacaoAutomatica();
        result = result * PRIME + ($renovacaoAutomatica == null ? 43 : $renovacaoAutomatica.hashCode());
        final java.lang.Object $reaplicacaoAutomatica = this.getReaplicacaoAutomatica();
        result = result * PRIME + ($reaplicacaoAutomatica == null ? 43 : $reaplicacaoAutomatica.hashCode());
        final java.lang.Object $codigoAplicacao = this.getCodigoAplicacao();
        result = result * PRIME + ($codigoAplicacao == null ? 43 : $codigoAplicacao.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $produtoFinanceiro = this.getProdutoFinanceiro();
        result = result * PRIME + ($produtoFinanceiro == null ? 43 : $produtoFinanceiro.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $valorAplicacao = this.getValorAplicacao();
        result = result * PRIME + ($valorAplicacao == null ? 43 : $valorAplicacao.hashCode());
        final java.lang.Object $valorAtual = this.getValorAtual();
        result = result * PRIME + ($valorAtual == null ? 43 : $valorAtual.hashCode());
        final java.lang.Object $valorResgate = this.getValorResgate();
        result = result * PRIME + ($valorResgate == null ? 43 : $valorResgate.hashCode());
        final java.lang.Object $valorRendimento = this.getValorRendimento();
        result = result * PRIME + ($valorRendimento == null ? 43 : $valorRendimento.hashCode());
        final java.lang.Object $taxaRemuneracao = this.getTaxaRemuneracao();
        result = result * PRIME + ($taxaRemuneracao == null ? 43 : $taxaRemuneracao.hashCode());
        final java.lang.Object $taxaRemuneracaoAtual = this.getTaxaRemuneracaoAtual();
        result = result * PRIME + ($taxaRemuneracaoAtual == null ? 43 : $taxaRemuneracaoAtual.hashCode());
        final java.lang.Object $dataAplicacao = this.getDataAplicacao();
        result = result * PRIME + ($dataAplicacao == null ? 43 : $dataAplicacao.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $dataResgate = this.getDataResgate();
        result = result * PRIME + ($dataResgate == null ? 43 : $dataResgate.hashCode());
        final java.lang.Object $dataUltimaRemuneracao = this.getDataUltimaRemuneracao();
        result = result * PRIME + ($dataUltimaRemuneracao == null ? 43 : $dataUltimaRemuneracao.hashCode());
        final java.lang.Object $dataProximaRemuneracao = this.getDataProximaRemuneracao();
        result = result * PRIME + ($dataProximaRemuneracao == null ? 43 : $dataProximaRemuneracao.hashCode());
        final java.lang.Object $taxaResgateAntecipado = this.getTaxaResgateAntecipado();
        result = result * PRIME + ($taxaResgateAntecipado == null ? 43 : $taxaResgateAntecipado.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesAplicacao = this.getDetalhesAplicacao();
        result = result * PRIME + ($detalhesAplicacao == null ? 43 : $detalhesAplicacao.hashCode());
        final java.lang.Object $regrasAplicadas = this.getRegrasAplicadas();
        result = result * PRIME + ($regrasAplicadas == null ? 43 : $regrasAplicadas.hashCode());
        final java.lang.Object $historicoRemuneracao = this.getHistoricoRemuneracao();
        result = result * PRIME + ($historicoRemuneracao == null ? 43 : $historicoRemuneracao.hashCode());
        final java.lang.Object $usuarioAplicacao = this.getUsuarioAplicacao();
        result = result * PRIME + ($usuarioAplicacao == null ? 43 : $usuarioAplicacao.hashCode());
        final java.lang.Object $usuarioResgate = this.getUsuarioResgate();
        result = result * PRIME + ($usuarioResgate == null ? 43 : $usuarioResgate.hashCode());
        final java.lang.Object $sistemaOrigem = this.getSistemaOrigem();
        result = result * PRIME + ($sistemaOrigem == null ? 43 : $sistemaOrigem.hashCode());
        final java.lang.Object $codigoTransacao = this.getCodigoTransacao();
        result = result * PRIME + ($codigoTransacao == null ? 43 : $codigoTransacao.hashCode());
        final java.lang.Object $codigoLiquidacao = this.getCodigoLiquidacao();
        result = result * PRIME + ($codigoLiquidacao == null ? 43 : $codigoLiquidacao.hashCode());
        return result;
    }
}
