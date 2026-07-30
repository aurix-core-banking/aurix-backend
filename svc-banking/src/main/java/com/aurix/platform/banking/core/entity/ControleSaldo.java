package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Conta;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "controle_saldos", schema = "aurix")
public class ControleSaldo extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoDisponivel = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoBloqueado = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoPendente = BigDecimal.ZERO;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal saldoTotal = BigDecimal.ZERO;
    @Column(precision = 19, scale = 4)
    private BigDecimal limiteCredito = BigDecimal.ZERO;
    @Column(precision = 19, scale = 4)
    private BigDecimal limiteUtilizado = BigDecimal.ZERO;
    @Column(precision = 19, scale = 4)
    private BigDecimal limiteDisponivel = BigDecimal.ZERO;
    @Column
    private LocalDateTime dataUltimaAtualizacao;
    @Column
    private Integer versaoSaldo = 1;
    @Column
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
    private Boolean bloqueioOperacoes = false;
    @Column
    private String motivoBloqueio;
    @Column
    private LocalDateTime dataBloqueio;
    @Column
    private LocalDateTime dataDesbloqueio;
    @Column
    private String usuarioBloqueio;
    @Column
    private String usuarioDesbloqueio;

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
    public LocalDateTime getDataUltimaAtualizacao() {
        return this.dataUltimaAtualizacao;
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
    public Boolean getBloqueioOperacoes() {
        return this.bloqueioOperacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getMotivoBloqueio() {
        return this.motivoBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataBloqueio() {
        return this.dataBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataDesbloqueio() {
        return this.dataDesbloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioBloqueio() {
        return this.usuarioBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioDesbloqueio() {
        return this.usuarioDesbloqueio;
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
    public void setDataUltimaAtualizacao(final LocalDateTime dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
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
    public void setBloqueioOperacoes(final Boolean bloqueioOperacoes) {
        this.bloqueioOperacoes = bloqueioOperacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivoBloqueio(final String motivoBloqueio) {
        this.motivoBloqueio = motivoBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataBloqueio(final LocalDateTime dataBloqueio) {
        this.dataBloqueio = dataBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataDesbloqueio(final LocalDateTime dataDesbloqueio) {
        this.dataDesbloqueio = dataDesbloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioBloqueio(final String usuarioBloqueio) {
        this.usuarioBloqueio = usuarioBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioDesbloqueio(final String usuarioDesbloqueio) {
        this.usuarioDesbloqueio = usuarioDesbloqueio;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ControleSaldo(conta=" + this.getConta() + ", saldoDisponivel=" + this.getSaldoDisponivel() + ", saldoBloqueado=" + this.getSaldoBloqueado() + ", saldoPendente=" + this.getSaldoPendente() + ", saldoTotal=" + this.getSaldoTotal() + ", limiteCredito=" + this.getLimiteCredito() + ", limiteUtilizado=" + this.getLimiteUtilizado() + ", limiteDisponivel=" + this.getLimiteDisponivel() + ", dataUltimaAtualizacao=" + this.getDataUltimaAtualizacao() + ", versaoSaldo=" + this.getVersaoSaldo() + ", saldoConsistente=" + this.getSaldoConsistente() + ", observacoes=" + this.getObservacoes() + ", detalhesSaldo=" + this.getDetalhesSaldo() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", bloqueioOperacoes=" + this.getBloqueioOperacoes() + ", motivoBloqueio=" + this.getMotivoBloqueio() + ", dataBloqueio=" + this.getDataBloqueio() + ", dataDesbloqueio=" + this.getDataDesbloqueio() + ", usuarioBloqueio=" + this.getUsuarioBloqueio() + ", usuarioDesbloqueio=" + this.getUsuarioDesbloqueio() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ControleSaldo() {
    }

    @java.lang.SuppressWarnings("all")
    public ControleSaldo(final Conta conta, final BigDecimal saldoDisponivel, final BigDecimal saldoBloqueado, final BigDecimal saldoPendente, final BigDecimal saldoTotal, final BigDecimal limiteCredito, final BigDecimal limiteUtilizado, final BigDecimal limiteDisponivel, final LocalDateTime dataUltimaAtualizacao, final Integer versaoSaldo, final Boolean saldoConsistente, final String observacoes, final String detalhesSaldo, final String regrasAplicadas, final Boolean bloqueioOperacoes, final String motivoBloqueio, final LocalDateTime dataBloqueio, final LocalDateTime dataDesbloqueio, final String usuarioBloqueio, final String usuarioDesbloqueio) {
        this.conta = conta;
        this.saldoDisponivel = saldoDisponivel;
        this.saldoBloqueado = saldoBloqueado;
        this.saldoPendente = saldoPendente;
        this.saldoTotal = saldoTotal;
        this.limiteCredito = limiteCredito;
        this.limiteUtilizado = limiteUtilizado;
        this.limiteDisponivel = limiteDisponivel;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        this.versaoSaldo = versaoSaldo;
        this.saldoConsistente = saldoConsistente;
        this.observacoes = observacoes;
        this.detalhesSaldo = detalhesSaldo;
        this.regrasAplicadas = regrasAplicadas;
        this.bloqueioOperacoes = bloqueioOperacoes;
        this.motivoBloqueio = motivoBloqueio;
        this.dataBloqueio = dataBloqueio;
        this.dataDesbloqueio = dataDesbloqueio;
        this.usuarioBloqueio = usuarioBloqueio;
        this.usuarioDesbloqueio = usuarioDesbloqueio;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ControleSaldo)) return false;
        final ControleSaldo other = (ControleSaldo) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$versaoSaldo = this.getVersaoSaldo();
        final java.lang.Object other$versaoSaldo = other.getVersaoSaldo();
        if (this$versaoSaldo == null ? other$versaoSaldo != null : !this$versaoSaldo.equals(other$versaoSaldo)) return false;
        final java.lang.Object this$saldoConsistente = this.getSaldoConsistente();
        final java.lang.Object other$saldoConsistente = other.getSaldoConsistente();
        if (this$saldoConsistente == null ? other$saldoConsistente != null : !this$saldoConsistente.equals(other$saldoConsistente)) return false;
        final java.lang.Object this$bloqueioOperacoes = this.getBloqueioOperacoes();
        final java.lang.Object other$bloqueioOperacoes = other.getBloqueioOperacoes();
        if (this$bloqueioOperacoes == null ? other$bloqueioOperacoes != null : !this$bloqueioOperacoes.equals(other$bloqueioOperacoes)) return false;
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
        final java.lang.Object this$dataUltimaAtualizacao = this.getDataUltimaAtualizacao();
        final java.lang.Object other$dataUltimaAtualizacao = other.getDataUltimaAtualizacao();
        if (this$dataUltimaAtualizacao == null ? other$dataUltimaAtualizacao != null : !this$dataUltimaAtualizacao.equals(other$dataUltimaAtualizacao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesSaldo = this.getDetalhesSaldo();
        final java.lang.Object other$detalhesSaldo = other.getDetalhesSaldo();
        if (this$detalhesSaldo == null ? other$detalhesSaldo != null : !this$detalhesSaldo.equals(other$detalhesSaldo)) return false;
        final java.lang.Object this$regrasAplicadas = this.getRegrasAplicadas();
        final java.lang.Object other$regrasAplicadas = other.getRegrasAplicadas();
        if (this$regrasAplicadas == null ? other$regrasAplicadas != null : !this$regrasAplicadas.equals(other$regrasAplicadas)) return false;
        final java.lang.Object this$motivoBloqueio = this.getMotivoBloqueio();
        final java.lang.Object other$motivoBloqueio = other.getMotivoBloqueio();
        if (this$motivoBloqueio == null ? other$motivoBloqueio != null : !this$motivoBloqueio.equals(other$motivoBloqueio)) return false;
        final java.lang.Object this$dataBloqueio = this.getDataBloqueio();
        final java.lang.Object other$dataBloqueio = other.getDataBloqueio();
        if (this$dataBloqueio == null ? other$dataBloqueio != null : !this$dataBloqueio.equals(other$dataBloqueio)) return false;
        final java.lang.Object this$dataDesbloqueio = this.getDataDesbloqueio();
        final java.lang.Object other$dataDesbloqueio = other.getDataDesbloqueio();
        if (this$dataDesbloqueio == null ? other$dataDesbloqueio != null : !this$dataDesbloqueio.equals(other$dataDesbloqueio)) return false;
        final java.lang.Object this$usuarioBloqueio = this.getUsuarioBloqueio();
        final java.lang.Object other$usuarioBloqueio = other.getUsuarioBloqueio();
        if (this$usuarioBloqueio == null ? other$usuarioBloqueio != null : !this$usuarioBloqueio.equals(other$usuarioBloqueio)) return false;
        final java.lang.Object this$usuarioDesbloqueio = this.getUsuarioDesbloqueio();
        final java.lang.Object other$usuarioDesbloqueio = other.getUsuarioDesbloqueio();
        if (this$usuarioDesbloqueio == null ? other$usuarioDesbloqueio != null : !this$usuarioDesbloqueio.equals(other$usuarioDesbloqueio)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ControleSaldo;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $versaoSaldo = this.getVersaoSaldo();
        result = result * PRIME + ($versaoSaldo == null ? 43 : $versaoSaldo.hashCode());
        final java.lang.Object $saldoConsistente = this.getSaldoConsistente();
        result = result * PRIME + ($saldoConsistente == null ? 43 : $saldoConsistente.hashCode());
        final java.lang.Object $bloqueioOperacoes = this.getBloqueioOperacoes();
        result = result * PRIME + ($bloqueioOperacoes == null ? 43 : $bloqueioOperacoes.hashCode());
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
        final java.lang.Object $dataUltimaAtualizacao = this.getDataUltimaAtualizacao();
        result = result * PRIME + ($dataUltimaAtualizacao == null ? 43 : $dataUltimaAtualizacao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesSaldo = this.getDetalhesSaldo();
        result = result * PRIME + ($detalhesSaldo == null ? 43 : $detalhesSaldo.hashCode());
        final java.lang.Object $regrasAplicadas = this.getRegrasAplicadas();
        result = result * PRIME + ($regrasAplicadas == null ? 43 : $regrasAplicadas.hashCode());
        final java.lang.Object $motivoBloqueio = this.getMotivoBloqueio();
        result = result * PRIME + ($motivoBloqueio == null ? 43 : $motivoBloqueio.hashCode());
        final java.lang.Object $dataBloqueio = this.getDataBloqueio();
        result = result * PRIME + ($dataBloqueio == null ? 43 : $dataBloqueio.hashCode());
        final java.lang.Object $dataDesbloqueio = this.getDataDesbloqueio();
        result = result * PRIME + ($dataDesbloqueio == null ? 43 : $dataDesbloqueio.hashCode());
        final java.lang.Object $usuarioBloqueio = this.getUsuarioBloqueio();
        result = result * PRIME + ($usuarioBloqueio == null ? 43 : $usuarioBloqueio.hashCode());
        final java.lang.Object $usuarioDesbloqueio = this.getUsuarioDesbloqueio();
        result = result * PRIME + ($usuarioDesbloqueio == null ? 43 : $usuarioDesbloqueio.hashCode());
        return result;
    }
}
