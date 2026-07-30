package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "movimentos_conta", schema = "aurix")
public class MovimentoConta extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoMovimento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_id")
    private Transacao transacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "liquidacao_id")
    private Liquidacao liquidacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimento tipoMovimento;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMovimento status = StatusMovimento.PENDENTE;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorMovimento;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoAnterior;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoPosterior;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoDisponivelAnterior;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoDisponivelPosterior;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoBloqueadoAnterior;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoBloqueadoPosterior;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoPendenteAnterior;
    @Column(precision = 19, scale = 4)
    private BigDecimal saldoPendentePosterior;
    @Column(nullable = false)
    private LocalDateTime dataMovimento;
    @Column
    private LocalDateTime dataProcessamento;
    @Column
    private LocalDateTime dataConclusao;
    @Column(length = 1000)
    private String descricaoMovimento;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_movimento", columnDefinition = "JSONB")
    private String detalhesMovimento;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_aplicadas", columnDefinition = "JSONB")
    private String regrasAplicadas;
    @Column
    private Boolean processamentoAutomatico = true;
    @Column
    private Boolean reversivel = true;
    @Column
    private String codigoContraparte;
    @Column
    private String codigoBacen;
    @Column
    private String codigoSPI;
    @Column
    private String codigoSTR;
    @Column
    private Integer versaoSaldo = 1;
    @Column
    private Boolean saldoConsistente = true;


    public enum TipoMovimento {
        DEBITO, CREDITO, BLOQUEIO, DESBLOQUEIO, RESERVA, LIBERACAO_RESERVA, AJUSTE_CREDITO, AJUSTE_DEBITO, TARIFA, JUROS, RENDIMENTO, CORRECAO_MONETARIA, IOF, IR, OUTROS;
    }


    public enum StatusMovimento {
        PENDENTE, PROCESSANDO, CONCLUIDO, FALHADO, CANCELADO, ESTORNADO, REVERSADO, AGUARDANDO_APROVACAO, REJEITADO;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoMovimento() {
        return this.codigoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public Transacao getTransacao() {
        return this.transacao;
    }

    @java.lang.SuppressWarnings("all")
    public Liquidacao getLiquidacao() {
        return this.liquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoMovimento getTipoMovimento() {
        return this.tipoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public StatusMovimento getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMovimento() {
        return this.valorMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoAnterior() {
        return this.saldoAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoPosterior() {
        return this.saldoPosterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoDisponivelAnterior() {
        return this.saldoDisponivelAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoDisponivelPosterior() {
        return this.saldoDisponivelPosterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoBloqueadoAnterior() {
        return this.saldoBloqueadoAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoBloqueadoPosterior() {
        return this.saldoBloqueadoPosterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoPendenteAnterior() {
        return this.saldoPendenteAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoPendentePosterior() {
        return this.saldoPendentePosterior;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataMovimento() {
        return this.dataMovimento;
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
    public String getDescricaoMovimento() {
        return this.descricaoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesMovimento() {
        return this.detalhesMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasAplicadas() {
        return this.regrasAplicadas;
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
    public String getCodigoContraparte() {
        return this.codigoContraparte;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoBacen() {
        return this.codigoBacen;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoSPI() {
        return this.codigoSPI;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoSTR() {
        return this.codigoSTR;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getVersaoSaldo() {
        return this.versaoSaldo;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getSaldoConsistente() {
        return this.saldoConsistente;
    }

@java.lang.SuppressWarnings("all")
    public void setCodigoMovimento(final String codigoMovimento) {
        this.codigoMovimento = codigoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacao(final Transacao transacao) {
        this.transacao = transacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setLiquidacao(final Liquidacao liquidacao) {
        this.liquidacao = liquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoMovimento(final TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusMovimento status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMovimento(final BigDecimal valorMovimento) {
        this.valorMovimento = valorMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoAnterior(final BigDecimal saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoPosterior(final BigDecimal saldoPosterior) {
        this.saldoPosterior = saldoPosterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoDisponivelAnterior(final BigDecimal saldoDisponivelAnterior) {
        this.saldoDisponivelAnterior = saldoDisponivelAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoDisponivelPosterior(final BigDecimal saldoDisponivelPosterior) {
        this.saldoDisponivelPosterior = saldoDisponivelPosterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoBloqueadoAnterior(final BigDecimal saldoBloqueadoAnterior) {
        this.saldoBloqueadoAnterior = saldoBloqueadoAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoBloqueadoPosterior(final BigDecimal saldoBloqueadoPosterior) {
        this.saldoBloqueadoPosterior = saldoBloqueadoPosterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoPendenteAnterior(final BigDecimal saldoPendenteAnterior) {
        this.saldoPendenteAnterior = saldoPendenteAnterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoPendentePosterior(final BigDecimal saldoPendentePosterior) {
        this.saldoPendentePosterior = saldoPendentePosterior;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataMovimento(final LocalDateTime dataMovimento) {
        this.dataMovimento = dataMovimento;
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
    public void setDescricaoMovimento(final String descricaoMovimento) {
        this.descricaoMovimento = descricaoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesMovimento(final String detalhesMovimento) {
        this.detalhesMovimento = detalhesMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasAplicadas(final String regrasAplicadas) {
        this.regrasAplicadas = regrasAplicadas;
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
    public void setCodigoContraparte(final String codigoContraparte) {
        this.codigoContraparte = codigoContraparte;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoBacen(final String codigoBacen) {
        this.codigoBacen = codigoBacen;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoSPI(final String codigoSPI) {
        this.codigoSPI = codigoSPI;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoSTR(final String codigoSTR) {
        this.codigoSTR = codigoSTR;
    }

    @java.lang.SuppressWarnings("all")
    public void setVersaoSaldo(final Integer versaoSaldo) {
        this.versaoSaldo = versaoSaldo;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoConsistente(final Boolean saldoConsistente) {
        this.saldoConsistente = saldoConsistente;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "MovimentoConta(id=" + this.getId() + ", codigoMovimento=" + this.getCodigoMovimento() + ", conta=" + this.getConta() + ", transacao=" + this.getTransacao() + ", liquidacao=" + this.getLiquidacao() + ", tipoMovimento=" + this.getTipoMovimento() + ", status=" + this.getStatus() + ", valorMovimento=" + this.getValorMovimento() + ", saldoAnterior=" + this.getSaldoAnterior() + ", saldoPosterior=" + this.getSaldoPosterior() + ", saldoDisponivelAnterior=" + this.getSaldoDisponivelAnterior() + ", saldoDisponivelPosterior=" + this.getSaldoDisponivelPosterior() + ", saldoBloqueadoAnterior=" + this.getSaldoBloqueadoAnterior() + ", saldoBloqueadoPosterior=" + this.getSaldoBloqueadoPosterior() + ", saldoPendenteAnterior=" + this.getSaldoPendenteAnterior() + ", saldoPendentePosterior=" + this.getSaldoPendentePosterior() + ", dataMovimento=" + this.getDataMovimento() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataConclusao=" + this.getDataConclusao() + ", descricaoMovimento=" + this.getDescricaoMovimento() + ", observacoes=" + this.getObservacoes() + ", detalhesMovimento=" + this.getDetalhesMovimento() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", processamentoAutomatico=" + this.getProcessamentoAutomatico() + ", reversivel=" + this.getReversivel() + ", codigoContraparte=" + this.getCodigoContraparte() + ", codigoBacen=" + this.getCodigoBacen() + ", codigoSPI=" + this.getCodigoSPI() + ", codigoSTR=" + this.getCodigoSTR() + ", versaoSaldo=" + this.getVersaoSaldo() + ", saldoConsistente=" + this.getSaldoConsistente() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public MovimentoConta() {
    }

    @java.lang.SuppressWarnings("all")
    public MovimentoConta(final Long id, final String codigoMovimento, final Conta conta, final Transacao transacao, final Liquidacao liquidacao, final TipoMovimento tipoMovimento, final StatusMovimento status, final BigDecimal valorMovimento, final BigDecimal saldoAnterior, final BigDecimal saldoPosterior, final BigDecimal saldoDisponivelAnterior, final BigDecimal saldoDisponivelPosterior, final BigDecimal saldoBloqueadoAnterior, final BigDecimal saldoBloqueadoPosterior, final BigDecimal saldoPendenteAnterior, final BigDecimal saldoPendentePosterior, final LocalDateTime dataMovimento, final LocalDateTime dataProcessamento, final LocalDateTime dataConclusao, final String descricaoMovimento, final String observacoes, final String detalhesMovimento, final String regrasAplicadas, final Boolean processamentoAutomatico, final Boolean reversivel, final String codigoContraparte, final String codigoBacen, final String codigoSPI, final String codigoSTR, final Integer versaoSaldo, final Boolean saldoConsistente) {
        this.setId(id);
        this.codigoMovimento = codigoMovimento;
        this.conta = conta;
        this.transacao = transacao;
        this.liquidacao = liquidacao;
        this.tipoMovimento = tipoMovimento;
        this.status = status;
        this.valorMovimento = valorMovimento;
        this.saldoAnterior = saldoAnterior;
        this.saldoPosterior = saldoPosterior;
        this.saldoDisponivelAnterior = saldoDisponivelAnterior;
        this.saldoDisponivelPosterior = saldoDisponivelPosterior;
        this.saldoBloqueadoAnterior = saldoBloqueadoAnterior;
        this.saldoBloqueadoPosterior = saldoBloqueadoPosterior;
        this.saldoPendenteAnterior = saldoPendenteAnterior;
        this.saldoPendentePosterior = saldoPendentePosterior;
        this.dataMovimento = dataMovimento;
        this.dataProcessamento = dataProcessamento;
        this.dataConclusao = dataConclusao;
        this.descricaoMovimento = descricaoMovimento;
        this.observacoes = observacoes;
        this.detalhesMovimento = detalhesMovimento;
        this.regrasAplicadas = regrasAplicadas;
        this.processamentoAutomatico = processamentoAutomatico;
        this.reversivel = reversivel;
        this.codigoContraparte = codigoContraparte;
        this.codigoBacen = codigoBacen;
        this.codigoSPI = codigoSPI;
        this.codigoSTR = codigoSTR;
        this.versaoSaldo = versaoSaldo;
        this.saldoConsistente = saldoConsistente;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof MovimentoConta)) return false;
        final MovimentoConta other = (MovimentoConta) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$processamentoAutomatico = this.getProcessamentoAutomatico();
        final java.lang.Object other$processamentoAutomatico = other.getProcessamentoAutomatico();
        if (this$processamentoAutomatico == null ? other$processamentoAutomatico != null : !this$processamentoAutomatico.equals(other$processamentoAutomatico)) return false;
        final java.lang.Object this$reversivel = this.getReversivel();
        final java.lang.Object other$reversivel = other.getReversivel();
        if (this$reversivel == null ? other$reversivel != null : !this$reversivel.equals(other$reversivel)) return false;
        final java.lang.Object this$versaoSaldo = this.getVersaoSaldo();
        final java.lang.Object other$versaoSaldo = other.getVersaoSaldo();
        if (this$versaoSaldo == null ? other$versaoSaldo != null : !this$versaoSaldo.equals(other$versaoSaldo)) return false;
        final java.lang.Object this$saldoConsistente = this.getSaldoConsistente();
        final java.lang.Object other$saldoConsistente = other.getSaldoConsistente();
        if (this$saldoConsistente == null ? other$saldoConsistente != null : !this$saldoConsistente.equals(other$saldoConsistente)) return false;
        final java.lang.Object this$codigoMovimento = this.getCodigoMovimento();
        final java.lang.Object other$codigoMovimento = other.getCodigoMovimento();
        if (this$codigoMovimento == null ? other$codigoMovimento != null : !this$codigoMovimento.equals(other$codigoMovimento)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$transacao = this.getTransacao();
        final java.lang.Object other$transacao = other.getTransacao();
        if (this$transacao == null ? other$transacao != null : !this$transacao.equals(other$transacao)) return false;
        final java.lang.Object this$liquidacao = this.getLiquidacao();
        final java.lang.Object other$liquidacao = other.getLiquidacao();
        if (this$liquidacao == null ? other$liquidacao != null : !this$liquidacao.equals(other$liquidacao)) return false;
        final java.lang.Object this$tipoMovimento = this.getTipoMovimento();
        final java.lang.Object other$tipoMovimento = other.getTipoMovimento();
        if (this$tipoMovimento == null ? other$tipoMovimento != null : !this$tipoMovimento.equals(other$tipoMovimento)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$valorMovimento = this.getValorMovimento();
        final java.lang.Object other$valorMovimento = other.getValorMovimento();
        if (this$valorMovimento == null ? other$valorMovimento != null : !this$valorMovimento.equals(other$valorMovimento)) return false;
        final java.lang.Object this$saldoAnterior = this.getSaldoAnterior();
        final java.lang.Object other$saldoAnterior = other.getSaldoAnterior();
        if (this$saldoAnterior == null ? other$saldoAnterior != null : !this$saldoAnterior.equals(other$saldoAnterior)) return false;
        final java.lang.Object this$saldoPosterior = this.getSaldoPosterior();
        final java.lang.Object other$saldoPosterior = other.getSaldoPosterior();
        if (this$saldoPosterior == null ? other$saldoPosterior != null : !this$saldoPosterior.equals(other$saldoPosterior)) return false;
        final java.lang.Object this$saldoDisponivelAnterior = this.getSaldoDisponivelAnterior();
        final java.lang.Object other$saldoDisponivelAnterior = other.getSaldoDisponivelAnterior();
        if (this$saldoDisponivelAnterior == null ? other$saldoDisponivelAnterior != null : !this$saldoDisponivelAnterior.equals(other$saldoDisponivelAnterior)) return false;
        final java.lang.Object this$saldoDisponivelPosterior = this.getSaldoDisponivelPosterior();
        final java.lang.Object other$saldoDisponivelPosterior = other.getSaldoDisponivelPosterior();
        if (this$saldoDisponivelPosterior == null ? other$saldoDisponivelPosterior != null : !this$saldoDisponivelPosterior.equals(other$saldoDisponivelPosterior)) return false;
        final java.lang.Object this$saldoBloqueadoAnterior = this.getSaldoBloqueadoAnterior();
        final java.lang.Object other$saldoBloqueadoAnterior = other.getSaldoBloqueadoAnterior();
        if (this$saldoBloqueadoAnterior == null ? other$saldoBloqueadoAnterior != null : !this$saldoBloqueadoAnterior.equals(other$saldoBloqueadoAnterior)) return false;
        final java.lang.Object this$saldoBloqueadoPosterior = this.getSaldoBloqueadoPosterior();
        final java.lang.Object other$saldoBloqueadoPosterior = other.getSaldoBloqueadoPosterior();
        if (this$saldoBloqueadoPosterior == null ? other$saldoBloqueadoPosterior != null : !this$saldoBloqueadoPosterior.equals(other$saldoBloqueadoPosterior)) return false;
        final java.lang.Object this$saldoPendenteAnterior = this.getSaldoPendenteAnterior();
        final java.lang.Object other$saldoPendenteAnterior = other.getSaldoPendenteAnterior();
        if (this$saldoPendenteAnterior == null ? other$saldoPendenteAnterior != null : !this$saldoPendenteAnterior.equals(other$saldoPendenteAnterior)) return false;
        final java.lang.Object this$saldoPendentePosterior = this.getSaldoPendentePosterior();
        final java.lang.Object other$saldoPendentePosterior = other.getSaldoPendentePosterior();
        if (this$saldoPendentePosterior == null ? other$saldoPendentePosterior != null : !this$saldoPendentePosterior.equals(other$saldoPendentePosterior)) return false;
        final java.lang.Object this$dataMovimento = this.getDataMovimento();
        final java.lang.Object other$dataMovimento = other.getDataMovimento();
        if (this$dataMovimento == null ? other$dataMovimento != null : !this$dataMovimento.equals(other$dataMovimento)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$dataConclusao = this.getDataConclusao();
        final java.lang.Object other$dataConclusao = other.getDataConclusao();
        if (this$dataConclusao == null ? other$dataConclusao != null : !this$dataConclusao.equals(other$dataConclusao)) return false;
        final java.lang.Object this$descricaoMovimento = this.getDescricaoMovimento();
        final java.lang.Object other$descricaoMovimento = other.getDescricaoMovimento();
        if (this$descricaoMovimento == null ? other$descricaoMovimento != null : !this$descricaoMovimento.equals(other$descricaoMovimento)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesMovimento = this.getDetalhesMovimento();
        final java.lang.Object other$detalhesMovimento = other.getDetalhesMovimento();
        if (this$detalhesMovimento == null ? other$detalhesMovimento != null : !this$detalhesMovimento.equals(other$detalhesMovimento)) return false;
        final java.lang.Object this$regrasAplicadas = this.getRegrasAplicadas();
        final java.lang.Object other$regrasAplicadas = other.getRegrasAplicadas();
        if (this$regrasAplicadas == null ? other$regrasAplicadas != null : !this$regrasAplicadas.equals(other$regrasAplicadas)) return false;
        final java.lang.Object this$codigoContraparte = this.getCodigoContraparte();
        final java.lang.Object other$codigoContraparte = other.getCodigoContraparte();
        if (this$codigoContraparte == null ? other$codigoContraparte != null : !this$codigoContraparte.equals(other$codigoContraparte)) return false;
        final java.lang.Object this$codigoBacen = this.getCodigoBacen();
        final java.lang.Object other$codigoBacen = other.getCodigoBacen();
        if (this$codigoBacen == null ? other$codigoBacen != null : !this$codigoBacen.equals(other$codigoBacen)) return false;
        final java.lang.Object this$codigoSPI = this.getCodigoSPI();
        final java.lang.Object other$codigoSPI = other.getCodigoSPI();
        if (this$codigoSPI == null ? other$codigoSPI != null : !this$codigoSPI.equals(other$codigoSPI)) return false;
        final java.lang.Object this$codigoSTR = this.getCodigoSTR();
        final java.lang.Object other$codigoSTR = other.getCodigoSTR();
        if (this$codigoSTR == null ? other$codigoSTR != null : !this$codigoSTR.equals(other$codigoSTR)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof MovimentoConta;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $processamentoAutomatico = this.getProcessamentoAutomatico();
        result = result * PRIME + ($processamentoAutomatico == null ? 43 : $processamentoAutomatico.hashCode());
        final java.lang.Object $reversivel = this.getReversivel();
        result = result * PRIME + ($reversivel == null ? 43 : $reversivel.hashCode());
        final java.lang.Object $versaoSaldo = this.getVersaoSaldo();
        result = result * PRIME + ($versaoSaldo == null ? 43 : $versaoSaldo.hashCode());
        final java.lang.Object $saldoConsistente = this.getSaldoConsistente();
        result = result * PRIME + ($saldoConsistente == null ? 43 : $saldoConsistente.hashCode());
        final java.lang.Object $codigoMovimento = this.getCodigoMovimento();
        result = result * PRIME + ($codigoMovimento == null ? 43 : $codigoMovimento.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $transacao = this.getTransacao();
        result = result * PRIME + ($transacao == null ? 43 : $transacao.hashCode());
        final java.lang.Object $liquidacao = this.getLiquidacao();
        result = result * PRIME + ($liquidacao == null ? 43 : $liquidacao.hashCode());
        final java.lang.Object $tipoMovimento = this.getTipoMovimento();
        result = result * PRIME + ($tipoMovimento == null ? 43 : $tipoMovimento.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $valorMovimento = this.getValorMovimento();
        result = result * PRIME + ($valorMovimento == null ? 43 : $valorMovimento.hashCode());
        final java.lang.Object $saldoAnterior = this.getSaldoAnterior();
        result = result * PRIME + ($saldoAnterior == null ? 43 : $saldoAnterior.hashCode());
        final java.lang.Object $saldoPosterior = this.getSaldoPosterior();
        result = result * PRIME + ($saldoPosterior == null ? 43 : $saldoPosterior.hashCode());
        final java.lang.Object $saldoDisponivelAnterior = this.getSaldoDisponivelAnterior();
        result = result * PRIME + ($saldoDisponivelAnterior == null ? 43 : $saldoDisponivelAnterior.hashCode());
        final java.lang.Object $saldoDisponivelPosterior = this.getSaldoDisponivelPosterior();
        result = result * PRIME + ($saldoDisponivelPosterior == null ? 43 : $saldoDisponivelPosterior.hashCode());
        final java.lang.Object $saldoBloqueadoAnterior = this.getSaldoBloqueadoAnterior();
        result = result * PRIME + ($saldoBloqueadoAnterior == null ? 43 : $saldoBloqueadoAnterior.hashCode());
        final java.lang.Object $saldoBloqueadoPosterior = this.getSaldoBloqueadoPosterior();
        result = result * PRIME + ($saldoBloqueadoPosterior == null ? 43 : $saldoBloqueadoPosterior.hashCode());
        final java.lang.Object $saldoPendenteAnterior = this.getSaldoPendenteAnterior();
        result = result * PRIME + ($saldoPendenteAnterior == null ? 43 : $saldoPendenteAnterior.hashCode());
        final java.lang.Object $saldoPendentePosterior = this.getSaldoPendentePosterior();
        result = result * PRIME + ($saldoPendentePosterior == null ? 43 : $saldoPendentePosterior.hashCode());
        final java.lang.Object $dataMovimento = this.getDataMovimento();
        result = result * PRIME + ($dataMovimento == null ? 43 : $dataMovimento.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $dataConclusao = this.getDataConclusao();
        result = result * PRIME + ($dataConclusao == null ? 43 : $dataConclusao.hashCode());
        final java.lang.Object $descricaoMovimento = this.getDescricaoMovimento();
        result = result * PRIME + ($descricaoMovimento == null ? 43 : $descricaoMovimento.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesMovimento = this.getDetalhesMovimento();
        result = result * PRIME + ($detalhesMovimento == null ? 43 : $detalhesMovimento.hashCode());
        final java.lang.Object $regrasAplicadas = this.getRegrasAplicadas();
        result = result * PRIME + ($regrasAplicadas == null ? 43 : $regrasAplicadas.hashCode());
        final java.lang.Object $codigoContraparte = this.getCodigoContraparte();
        result = result * PRIME + ($codigoContraparte == null ? 43 : $codigoContraparte.hashCode());
        final java.lang.Object $codigoBacen = this.getCodigoBacen();
        result = result * PRIME + ($codigoBacen == null ? 43 : $codigoBacen.hashCode());
        final java.lang.Object $codigoSPI = this.getCodigoSPI();
        result = result * PRIME + ($codigoSPI == null ? 43 : $codigoSPI.hashCode());
        final java.lang.Object $codigoSTR = this.getCodigoSTR();
        result = result * PRIME + ($codigoSTR == null ? 43 : $codigoSTR.hashCode());
        return result;
    }
}
