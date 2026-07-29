package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "historico_saldos", schema = "aurix")
public class HistoricoSaldo extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoDisponivel;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoBloqueado;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoPendente;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoTotal;
    @Column(precision = 19, scale = 4)
    private BigDecimal limiteCredito;
    @Column(precision = 19, scale = 4)
    private BigDecimal limiteUtilizado;
    @Column(precision = 19, scale = 4)
    private BigDecimal limiteDisponivel;
    @Column(nullable = false)
    private LocalDateTime dataReferencia;
    @Column
    private LocalDateTime dataAtualizacao;
    @Column(nullable = false)
    private Integer versaoSaldo;
    @Column(nullable = false)
    private Boolean saldoConsistente = true;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_saldo", columnDefinition = "JSONB")
    private String detalhesSaldo;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_aplicadas", columnDefinition = "JSONB")
    private String regrasAplicadas;
    @Column
    private String usuarioAtualizacao;
    @Column
    private String sistemaOrigem;
    @Column
    private String codigoTransacao;
    @Column
    private String codigoLiquidacao;
    @Column
    private String codigoMovimento;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAtualizacao tipoAtualizacao = TipoAtualizacao.AUTOMATICA;


    public enum TipoAtualizacao {
        AUTOMATICA, MANUAL, AJUSTE, CORRECAO, RECONCILIACAO, AUDITORIA;
    }

@java.lang.SuppressWarnings("all")
    public Conta getConta() {
        return this.conta;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoDisponivel() {
        return this.saldoDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoBloqueado() {
        return this.saldoBloqueado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoPendente() {
        return this.saldoPendente;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldoTotal() {
        return this.saldoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteCredito() {
        return this.limiteCredito;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteUtilizado() {
        return this.limiteUtilizado;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteDisponivel() {
        return this.limiteDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataReferencia() {
        return this.dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAtualizacao() {
        return this.dataAtualizacao;
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
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesSaldo() {
        return this.detalhesSaldo;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasAplicadas() {
        return this.regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioAtualizacao() {
        return this.usuarioAtualizacao;
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
    public String getCodigoMovimento() {
        return this.codigoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public TipoAtualizacao getTipoAtualizacao() {
        return this.tipoAtualizacao;
    }

@java.lang.SuppressWarnings("all")
    public void setConta(final Conta conta) {
        this.conta = conta;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoDisponivel(final BigDecimal saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoBloqueado(final BigDecimal saldoBloqueado) {
        this.saldoBloqueado = saldoBloqueado;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoPendente(final BigDecimal saldoPendente) {
        this.saldoPendente = saldoPendente;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoTotal(final BigDecimal saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteCredito(final BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteUtilizado(final BigDecimal limiteUtilizado) {
        this.limiteUtilizado = limiteUtilizado;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteDisponivel(final BigDecimal limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataReferencia(final LocalDateTime dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setVersaoSaldo(final Integer versaoSaldo) {
        this.versaoSaldo = versaoSaldo;
    }

    @java.lang.SuppressWarnings("all")
    public void setSaldoConsistente(final Boolean saldoConsistente) {
        this.saldoConsistente = saldoConsistente;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesSaldo(final String detalhesSaldo) {
        this.detalhesSaldo = detalhesSaldo;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasAplicadas(final String regrasAplicadas) {
        this.regrasAplicadas = regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioAtualizacao(final String usuarioAtualizacao) {
        this.usuarioAtualizacao = usuarioAtualizacao;
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

    @java.lang.SuppressWarnings("all")
    public void setCodigoMovimento(final String codigoMovimento) {
        this.codigoMovimento = codigoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoAtualizacao(final TipoAtualizacao tipoAtualizacao) {
        this.tipoAtualizacao = tipoAtualizacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "HistoricoSaldo(id=" + this.getId() + ", conta=" + this.getConta() + ", saldoDisponivel=" + this.getSaldoDisponivel() + ", saldoBloqueado=" + this.getSaldoBloqueado() + ", saldoPendente=" + this.getSaldoPendente() + ", saldoTotal=" + this.getSaldoTotal() + ", limiteCredito=" + this.getLimiteCredito() + ", limiteUtilizado=" + this.getLimiteUtilizado() + ", limiteDisponivel=" + this.getLimiteDisponivel() + ", dataReferencia=" + this.getDataReferencia() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versaoSaldo=" + this.getVersaoSaldo() + ", saldoConsistente=" + this.getSaldoConsistente() + ", observacoes=" + this.getObservacoes() + ", detalhesSaldo=" + this.getDetalhesSaldo() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", usuarioAtualizacao=" + this.getUsuarioAtualizacao() + ", sistemaOrigem=" + this.getSistemaOrigem() + ", codigoTransacao=" + this.getCodigoTransacao() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ", codigoMovimento=" + this.getCodigoMovimento() + ", tipoAtualizacao=" + this.getTipoAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public HistoricoSaldo() {
    }

    @java.lang.SuppressWarnings("all")
    public HistoricoSaldo(final Long id, final Conta conta, final BigDecimal saldoDisponivel, final BigDecimal saldoBloqueado, final BigDecimal saldoPendente, final BigDecimal saldoTotal, final BigDecimal limiteCredito, final BigDecimal limiteUtilizado, final BigDecimal limiteDisponivel, final LocalDateTime dataReferencia, final LocalDateTime dataAtualizacao, final Integer versaoSaldo, final Boolean saldoConsistente, final String observacoes, final String detalhesSaldo, final String regrasAplicadas, final String usuarioAtualizacao, final String sistemaOrigem, final String codigoTransacao, final String codigoLiquidacao, final String codigoMovimento, final TipoAtualizacao tipoAtualizacao) {
        this.setId(id);
        this.conta = conta;
        this.saldoDisponivel = saldoDisponivel;
        this.saldoBloqueado = saldoBloqueado;
        this.saldoPendente = saldoPendente;
        this.saldoTotal = saldoTotal;
        this.limiteCredito = limiteCredito;
        this.limiteUtilizado = limiteUtilizado;
        this.limiteDisponivel = limiteDisponivel;
        this.dataReferencia = dataReferencia;
        this.dataAtualizacao = dataAtualizacao;
        this.versaoSaldo = versaoSaldo;
        this.saldoConsistente = saldoConsistente;
        this.observacoes = observacoes;
        this.detalhesSaldo = detalhesSaldo;
        this.regrasAplicadas = regrasAplicadas;
        this.usuarioAtualizacao = usuarioAtualizacao;
        this.sistemaOrigem = sistemaOrigem;
        this.codigoTransacao = codigoTransacao;
        this.codigoLiquidacao = codigoLiquidacao;
        this.codigoMovimento = codigoMovimento;
        this.tipoAtualizacao = tipoAtualizacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof HistoricoSaldo)) return false;
        final HistoricoSaldo other = (HistoricoSaldo) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$versaoSaldo = this.getVersaoSaldo();
        final java.lang.Object other$versaoSaldo = other.getVersaoSaldo();
        if (this$versaoSaldo == null ? other$versaoSaldo != null : !this$versaoSaldo.equals(other$versaoSaldo)) return false;
        final java.lang.Object this$saldoConsistente = this.getSaldoConsistente();
        final java.lang.Object other$saldoConsistente = other.getSaldoConsistente();
        if (this$saldoConsistente == null ? other$saldoConsistente != null : !this$saldoConsistente.equals(other$saldoConsistente)) return false;
        final java.lang.Object this$conta = this.getConta();
        final java.lang.Object other$conta = other.getConta();
        if (this$conta == null ? other$conta != null : !this$conta.equals(other$conta)) return false;
        final java.lang.Object this$saldoDisponivel = this.getSaldoDisponivel();
        final java.lang.Object other$saldoDisponivel = other.getSaldoDisponivel();
        if (this$saldoDisponivel == null ? other$saldoDisponivel != null : !this$saldoDisponivel.equals(other$saldoDisponivel)) return false;
        final java.lang.Object this$saldoBloqueado = this.getSaldoBloqueado();
        final java.lang.Object other$saldoBloqueado = other.getSaldoBloqueado();
        if (this$saldoBloqueado == null ? other$saldoBloqueado != null : !this$saldoBloqueado.equals(other$saldoBloqueado)) return false;
        final java.lang.Object this$saldoPendente = this.getSaldoPendente();
        final java.lang.Object other$saldoPendente = other.getSaldoPendente();
        if (this$saldoPendente == null ? other$saldoPendente != null : !this$saldoPendente.equals(other$saldoPendente)) return false;
        final java.lang.Object this$saldoTotal = this.getSaldoTotal();
        final java.lang.Object other$saldoTotal = other.getSaldoTotal();
        if (this$saldoTotal == null ? other$saldoTotal != null : !this$saldoTotal.equals(other$saldoTotal)) return false;
        final java.lang.Object this$limiteCredito = this.getLimiteCredito();
        final java.lang.Object other$limiteCredito = other.getLimiteCredito();
        if (this$limiteCredito == null ? other$limiteCredito != null : !this$limiteCredito.equals(other$limiteCredito)) return false;
        final java.lang.Object this$limiteUtilizado = this.getLimiteUtilizado();
        final java.lang.Object other$limiteUtilizado = other.getLimiteUtilizado();
        if (this$limiteUtilizado == null ? other$limiteUtilizado != null : !this$limiteUtilizado.equals(other$limiteUtilizado)) return false;
        final java.lang.Object this$limiteDisponivel = this.getLimiteDisponivel();
        final java.lang.Object other$limiteDisponivel = other.getLimiteDisponivel();
        if (this$limiteDisponivel == null ? other$limiteDisponivel != null : !this$limiteDisponivel.equals(other$limiteDisponivel)) return false;
        final java.lang.Object this$dataReferencia = this.getDataReferencia();
        final java.lang.Object other$dataReferencia = other.getDataReferencia();
        if (this$dataReferencia == null ? other$dataReferencia != null : !this$dataReferencia.equals(other$dataReferencia)) return false;
        final java.lang.Object this$dataAtualizacao = this.getDataAtualizacao();
        final java.lang.Object other$dataAtualizacao = other.getDataAtualizacao();
        if (this$dataAtualizacao == null ? other$dataAtualizacao != null : !this$dataAtualizacao.equals(other$dataAtualizacao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesSaldo = this.getDetalhesSaldo();
        final java.lang.Object other$detalhesSaldo = other.getDetalhesSaldo();
        if (this$detalhesSaldo == null ? other$detalhesSaldo != null : !this$detalhesSaldo.equals(other$detalhesSaldo)) return false;
        final java.lang.Object this$regrasAplicadas = this.getRegrasAplicadas();
        final java.lang.Object other$regrasAplicadas = other.getRegrasAplicadas();
        if (this$regrasAplicadas == null ? other$regrasAplicadas != null : !this$regrasAplicadas.equals(other$regrasAplicadas)) return false;
        final java.lang.Object this$usuarioAtualizacao = this.getUsuarioAtualizacao();
        final java.lang.Object other$usuarioAtualizacao = other.getUsuarioAtualizacao();
        if (this$usuarioAtualizacao == null ? other$usuarioAtualizacao != null : !this$usuarioAtualizacao.equals(other$usuarioAtualizacao)) return false;
        final java.lang.Object this$sistemaOrigem = this.getSistemaOrigem();
        final java.lang.Object other$sistemaOrigem = other.getSistemaOrigem();
        if (this$sistemaOrigem == null ? other$sistemaOrigem != null : !this$sistemaOrigem.equals(other$sistemaOrigem)) return false;
        final java.lang.Object this$codigoTransacao = this.getCodigoTransacao();
        final java.lang.Object other$codigoTransacao = other.getCodigoTransacao();
        if (this$codigoTransacao == null ? other$codigoTransacao != null : !this$codigoTransacao.equals(other$codigoTransacao)) return false;
        final java.lang.Object this$codigoLiquidacao = this.getCodigoLiquidacao();
        final java.lang.Object other$codigoLiquidacao = other.getCodigoLiquidacao();
        if (this$codigoLiquidacao == null ? other$codigoLiquidacao != null : !this$codigoLiquidacao.equals(other$codigoLiquidacao)) return false;
        final java.lang.Object this$codigoMovimento = this.getCodigoMovimento();
        final java.lang.Object other$codigoMovimento = other.getCodigoMovimento();
        if (this$codigoMovimento == null ? other$codigoMovimento != null : !this$codigoMovimento.equals(other$codigoMovimento)) return false;
        final java.lang.Object this$tipoAtualizacao = this.getTipoAtualizacao();
        final java.lang.Object other$tipoAtualizacao = other.getTipoAtualizacao();
        if (this$tipoAtualizacao == null ? other$tipoAtualizacao != null : !this$tipoAtualizacao.equals(other$tipoAtualizacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof HistoricoSaldo;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $versaoSaldo = this.getVersaoSaldo();
        result = result * PRIME + ($versaoSaldo == null ? 43 : $versaoSaldo.hashCode());
        final java.lang.Object $saldoConsistente = this.getSaldoConsistente();
        result = result * PRIME + ($saldoConsistente == null ? 43 : $saldoConsistente.hashCode());
        final java.lang.Object $conta = this.getConta();
        result = result * PRIME + ($conta == null ? 43 : $conta.hashCode());
        final java.lang.Object $saldoDisponivel = this.getSaldoDisponivel();
        result = result * PRIME + ($saldoDisponivel == null ? 43 : $saldoDisponivel.hashCode());
        final java.lang.Object $saldoBloqueado = this.getSaldoBloqueado();
        result = result * PRIME + ($saldoBloqueado == null ? 43 : $saldoBloqueado.hashCode());
        final java.lang.Object $saldoPendente = this.getSaldoPendente();
        result = result * PRIME + ($saldoPendente == null ? 43 : $saldoPendente.hashCode());
        final java.lang.Object $saldoTotal = this.getSaldoTotal();
        result = result * PRIME + ($saldoTotal == null ? 43 : $saldoTotal.hashCode());
        final java.lang.Object $limiteCredito = this.getLimiteCredito();
        result = result * PRIME + ($limiteCredito == null ? 43 : $limiteCredito.hashCode());
        final java.lang.Object $limiteUtilizado = this.getLimiteUtilizado();
        result = result * PRIME + ($limiteUtilizado == null ? 43 : $limiteUtilizado.hashCode());
        final java.lang.Object $limiteDisponivel = this.getLimiteDisponivel();
        result = result * PRIME + ($limiteDisponivel == null ? 43 : $limiteDisponivel.hashCode());
        final java.lang.Object $dataReferencia = this.getDataReferencia();
        result = result * PRIME + ($dataReferencia == null ? 43 : $dataReferencia.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesSaldo = this.getDetalhesSaldo();
        result = result * PRIME + ($detalhesSaldo == null ? 43 : $detalhesSaldo.hashCode());
        final java.lang.Object $regrasAplicadas = this.getRegrasAplicadas();
        result = result * PRIME + ($regrasAplicadas == null ? 43 : $regrasAplicadas.hashCode());
        final java.lang.Object $usuarioAtualizacao = this.getUsuarioAtualizacao();
        result = result * PRIME + ($usuarioAtualizacao == null ? 43 : $usuarioAtualizacao.hashCode());
        final java.lang.Object $sistemaOrigem = this.getSistemaOrigem();
        result = result * PRIME + ($sistemaOrigem == null ? 43 : $sistemaOrigem.hashCode());
        final java.lang.Object $codigoTransacao = this.getCodigoTransacao();
        result = result * PRIME + ($codigoTransacao == null ? 43 : $codigoTransacao.hashCode());
        final java.lang.Object $codigoLiquidacao = this.getCodigoLiquidacao();
        result = result * PRIME + ($codigoLiquidacao == null ? 43 : $codigoLiquidacao.hashCode());
        final java.lang.Object $codigoMovimento = this.getCodigoMovimento();
        result = result * PRIME + ($codigoMovimento == null ? 43 : $codigoMovimento.hashCode());
        final java.lang.Object $tipoAtualizacao = this.getTipoAtualizacao();
        result = result * PRIME + ($tipoAtualizacao == null ? 43 : $tipoAtualizacao.hashCode());
        return result;
    }
}
