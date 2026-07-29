package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import com.aurix.platform.shared.entity.Transacao;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "liquidacoes", schema = "aurix")
public class Liquidacao extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String codigoLiquidacao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transacao_id", nullable = false)
    private Transacao transacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoLiquidacao tipoLiquidacao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLiquidacao status = StatusLiquidacao.PENDENTE;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valorLiquidacao;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorTaxa;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorIOF;
    @Column(precision = 19, scale = 4)
    private BigDecimal valorTotal;
    @Column
    private LocalDateTime dataLiquidacao;
    @Column
    private LocalDateTime dataVencimento;
    @Column
    private LocalDateTime dataProcessamento;
    @Column
    private LocalDateTime dataConclusao;
    @Column(length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "detalhes_liquidacao", columnDefinition = "JSONB")
    private String detalhesLiquidacao;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "regras_aplicadas", columnDefinition = "JSONB")
    private String regrasAplicadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "erro_liquidacao", columnDefinition = "JSONB")
    private String erroLiquidacao;
    @Column
    private Integer tentativasLiquidacao = 0;
    @Column
    private Integer maxTentativas = 3;
    @Column
    private Boolean processamentoAutomatico = true;
    @Column
    private Boolean reversivel = true;
    @Column
    private String codigoBacen;
    @Column
    private String codigoSPI;
    @Column
    private String codigoSTR;


    public enum TipoLiquidacao {
        PIX_INSTANTANEO, PIX_AGENDADO, TED_IMEDIATA, TED_AGENDADA, DOC_IMEDIATA, DOC_AGENDADA, SAQUE_ATM, DEPOSITO_ESPECIE, DEPOSITO_CHEQUE, TRANSFERENCIA_INTERNA, APLICACAO_INVESTIMENTO, RESGATE_INVESTIMENTO, PAGAMENTO_BOLETO, PAGAMENTO_CARTAO, OUTROS;
    }


    public enum StatusLiquidacao {
        PENDENTE, PROCESSANDO, LIQUIDADA, FALHADA, CANCELADA, ESTORNADA, REVERSADA, AGUARDANDO_APROVACAO, REJEITADA;
    }

@java.lang.SuppressWarnings("all")
    public String getCodigoLiquidacao() {
        return this.codigoLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public Transacao getTransacao() {
        return this.transacao;
    }

    @java.lang.SuppressWarnings("all")
    public TipoLiquidacao getTipoLiquidacao() {
        return this.tipoLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusLiquidacao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorLiquidacao() {
        return this.valorLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTaxa() {
        return this.valorTaxa;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorIOF() {
        return this.valorIOF;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTotal() {
        return this.valorTotal;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataLiquidacao() {
        return this.dataLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
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
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDetalhesLiquidacao() {
        return this.detalhesLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getRegrasAplicadas() {
        return this.regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getErroLiquidacao() {
        return this.erroLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTentativasLiquidacao() {
        return this.tentativasLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getMaxTentativas() {
        return this.maxTentativas;
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
    public void setCodigoLiquidacao(final String codigoLiquidacao) {
        this.codigoLiquidacao = codigoLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacao(final Transacao transacao) {
        this.transacao = transacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoLiquidacao(final TipoLiquidacao tipoLiquidacao) {
        this.tipoLiquidacao = tipoLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusLiquidacao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorLiquidacao(final BigDecimal valorLiquidacao) {
        this.valorLiquidacao = valorLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTaxa(final BigDecimal valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorIOF(final BigDecimal valorIOF) {
        this.valorIOF = valorIOF;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTotal(final BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataLiquidacao(final LocalDateTime dataLiquidacao) {
        this.dataLiquidacao = dataLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
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
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDetalhesLiquidacao(final String detalhesLiquidacao) {
        this.detalhesLiquidacao = detalhesLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRegrasAplicadas(final String regrasAplicadas) {
        this.regrasAplicadas = regrasAplicadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setErroLiquidacao(final String erroLiquidacao) {
        this.erroLiquidacao = erroLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTentativasLiquidacao(final Integer tentativasLiquidacao) {
        this.tentativasLiquidacao = tentativasLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setMaxTentativas(final Integer maxTentativas) {
        this.maxTentativas = maxTentativas;
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

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Liquidacao(id=" + this.getId() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ", transacao=" + this.getTransacao() + ", tipoLiquidacao=" + this.getTipoLiquidacao() + ", status=" + this.getStatus() + ", valorLiquidacao=" + this.getValorLiquidacao() + ", valorTaxa=" + this.getValorTaxa() + ", valorIOF=" + this.getValorIOF() + ", valorTotal=" + this.getValorTotal() + ", dataLiquidacao=" + this.getDataLiquidacao() + ", dataVencimento=" + this.getDataVencimento() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataConclusao=" + this.getDataConclusao() + ", observacoes=" + this.getObservacoes() + ", detalhesLiquidacao=" + this.getDetalhesLiquidacao() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", erroLiquidacao=" + this.getErroLiquidacao() + ", tentativasLiquidacao=" + this.getTentativasLiquidacao() + ", maxTentativas=" + this.getMaxTentativas() + ", processamentoAutomatico=" + this.getProcessamentoAutomatico() + ", reversivel=" + this.getReversivel() + ", codigoBacen=" + this.getCodigoBacen() + ", codigoSPI=" + this.getCodigoSPI() + ", codigoSTR=" + this.getCodigoSTR() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public Liquidacao() {
    }

    @java.lang.SuppressWarnings("all")
    public Liquidacao(final Long id, final String codigoLiquidacao, final Transacao transacao, final TipoLiquidacao tipoLiquidacao, final StatusLiquidacao status, final BigDecimal valorLiquidacao, final BigDecimal valorTaxa, final BigDecimal valorIOF, final BigDecimal valorTotal, final LocalDateTime dataLiquidacao, final LocalDateTime dataVencimento, final LocalDateTime dataProcessamento, final LocalDateTime dataConclusao, final String observacoes, final String detalhesLiquidacao, final String regrasAplicadas, final String erroLiquidacao, final Integer tentativasLiquidacao, final Integer maxTentativas, final Boolean processamentoAutomatico, final Boolean reversivel, final String codigoBacen, final String codigoSPI, final String codigoSTR) {
        this.setId(id);
        this.codigoLiquidacao = codigoLiquidacao;
        this.transacao = transacao;
        this.tipoLiquidacao = tipoLiquidacao;
        this.status = status;
        this.valorLiquidacao = valorLiquidacao;
        this.valorTaxa = valorTaxa;
        this.valorIOF = valorIOF;
        this.valorTotal = valorTotal;
        this.dataLiquidacao = dataLiquidacao;
        this.dataVencimento = dataVencimento;
        this.dataProcessamento = dataProcessamento;
        this.dataConclusao = dataConclusao;
        this.observacoes = observacoes;
        this.detalhesLiquidacao = detalhesLiquidacao;
        this.regrasAplicadas = regrasAplicadas;
        this.erroLiquidacao = erroLiquidacao;
        this.tentativasLiquidacao = tentativasLiquidacao;
        this.maxTentativas = maxTentativas;
        this.processamentoAutomatico = processamentoAutomatico;
        this.reversivel = reversivel;
        this.codigoBacen = codigoBacen;
        this.codigoSPI = codigoSPI;
        this.codigoSTR = codigoSTR;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Liquidacao)) return false;
        final Liquidacao other = (Liquidacao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$tentativasLiquidacao = this.getTentativasLiquidacao();
        final java.lang.Object other$tentativasLiquidacao = other.getTentativasLiquidacao();
        if (this$tentativasLiquidacao == null ? other$tentativasLiquidacao != null : !this$tentativasLiquidacao.equals(other$tentativasLiquidacao)) return false;
        final java.lang.Object this$maxTentativas = this.getMaxTentativas();
        final java.lang.Object other$maxTentativas = other.getMaxTentativas();
        if (this$maxTentativas == null ? other$maxTentativas != null : !this$maxTentativas.equals(other$maxTentativas)) return false;
        final java.lang.Object this$processamentoAutomatico = this.getProcessamentoAutomatico();
        final java.lang.Object other$processamentoAutomatico = other.getProcessamentoAutomatico();
        if (this$processamentoAutomatico == null ? other$processamentoAutomatico != null : !this$processamentoAutomatico.equals(other$processamentoAutomatico)) return false;
        final java.lang.Object this$reversivel = this.getReversivel();
        final java.lang.Object other$reversivel = other.getReversivel();
        if (this$reversivel == null ? other$reversivel != null : !this$reversivel.equals(other$reversivel)) return false;
        final java.lang.Object this$codigoLiquidacao = this.getCodigoLiquidacao();
        final java.lang.Object other$codigoLiquidacao = other.getCodigoLiquidacao();
        if (this$codigoLiquidacao == null ? other$codigoLiquidacao != null : !this$codigoLiquidacao.equals(other$codigoLiquidacao)) return false;
        final java.lang.Object this$transacao = this.getTransacao();
        final java.lang.Object other$transacao = other.getTransacao();
        if (this$transacao == null ? other$transacao != null : !this$transacao.equals(other$transacao)) return false;
        final java.lang.Object this$tipoLiquidacao = this.getTipoLiquidacao();
        final java.lang.Object other$tipoLiquidacao = other.getTipoLiquidacao();
        if (this$tipoLiquidacao == null ? other$tipoLiquidacao != null : !this$tipoLiquidacao.equals(other$tipoLiquidacao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$valorLiquidacao = this.getValorLiquidacao();
        final java.lang.Object other$valorLiquidacao = other.getValorLiquidacao();
        if (this$valorLiquidacao == null ? other$valorLiquidacao != null : !this$valorLiquidacao.equals(other$valorLiquidacao)) return false;
        final java.lang.Object this$valorTaxa = this.getValorTaxa();
        final java.lang.Object other$valorTaxa = other.getValorTaxa();
        if (this$valorTaxa == null ? other$valorTaxa != null : !this$valorTaxa.equals(other$valorTaxa)) return false;
        final java.lang.Object this$valorIOF = this.getValorIOF();
        final java.lang.Object other$valorIOF = other.getValorIOF();
        if (this$valorIOF == null ? other$valorIOF != null : !this$valorIOF.equals(other$valorIOF)) return false;
        final java.lang.Object this$valorTotal = this.getValorTotal();
        final java.lang.Object other$valorTotal = other.getValorTotal();
        if (this$valorTotal == null ? other$valorTotal != null : !this$valorTotal.equals(other$valorTotal)) return false;
        final java.lang.Object this$dataLiquidacao = this.getDataLiquidacao();
        final java.lang.Object other$dataLiquidacao = other.getDataLiquidacao();
        if (this$dataLiquidacao == null ? other$dataLiquidacao != null : !this$dataLiquidacao.equals(other$dataLiquidacao)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$dataConclusao = this.getDataConclusao();
        final java.lang.Object other$dataConclusao = other.getDataConclusao();
        if (this$dataConclusao == null ? other$dataConclusao != null : !this$dataConclusao.equals(other$dataConclusao)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$detalhesLiquidacao = this.getDetalhesLiquidacao();
        final java.lang.Object other$detalhesLiquidacao = other.getDetalhesLiquidacao();
        if (this$detalhesLiquidacao == null ? other$detalhesLiquidacao != null : !this$detalhesLiquidacao.equals(other$detalhesLiquidacao)) return false;
        final java.lang.Object this$regrasAplicadas = this.getRegrasAplicadas();
        final java.lang.Object other$regrasAplicadas = other.getRegrasAplicadas();
        if (this$regrasAplicadas == null ? other$regrasAplicadas != null : !this$regrasAplicadas.equals(other$regrasAplicadas)) return false;
        final java.lang.Object this$erroLiquidacao = this.getErroLiquidacao();
        final java.lang.Object other$erroLiquidacao = other.getErroLiquidacao();
        if (this$erroLiquidacao == null ? other$erroLiquidacao != null : !this$erroLiquidacao.equals(other$erroLiquidacao)) return false;
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
        return other instanceof Liquidacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $tentativasLiquidacao = this.getTentativasLiquidacao();
        result = result * PRIME + ($tentativasLiquidacao == null ? 43 : $tentativasLiquidacao.hashCode());
        final java.lang.Object $maxTentativas = this.getMaxTentativas();
        result = result * PRIME + ($maxTentativas == null ? 43 : $maxTentativas.hashCode());
        final java.lang.Object $processamentoAutomatico = this.getProcessamentoAutomatico();
        result = result * PRIME + ($processamentoAutomatico == null ? 43 : $processamentoAutomatico.hashCode());
        final java.lang.Object $reversivel = this.getReversivel();
        result = result * PRIME + ($reversivel == null ? 43 : $reversivel.hashCode());
        final java.lang.Object $codigoLiquidacao = this.getCodigoLiquidacao();
        result = result * PRIME + ($codigoLiquidacao == null ? 43 : $codigoLiquidacao.hashCode());
        final java.lang.Object $transacao = this.getTransacao();
        result = result * PRIME + ($transacao == null ? 43 : $transacao.hashCode());
        final java.lang.Object $tipoLiquidacao = this.getTipoLiquidacao();
        result = result * PRIME + ($tipoLiquidacao == null ? 43 : $tipoLiquidacao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $valorLiquidacao = this.getValorLiquidacao();
        result = result * PRIME + ($valorLiquidacao == null ? 43 : $valorLiquidacao.hashCode());
        final java.lang.Object $valorTaxa = this.getValorTaxa();
        result = result * PRIME + ($valorTaxa == null ? 43 : $valorTaxa.hashCode());
        final java.lang.Object $valorIOF = this.getValorIOF();
        result = result * PRIME + ($valorIOF == null ? 43 : $valorIOF.hashCode());
        final java.lang.Object $valorTotal = this.getValorTotal();
        result = result * PRIME + ($valorTotal == null ? 43 : $valorTotal.hashCode());
        final java.lang.Object $dataLiquidacao = this.getDataLiquidacao();
        result = result * PRIME + ($dataLiquidacao == null ? 43 : $dataLiquidacao.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $dataConclusao = this.getDataConclusao();
        result = result * PRIME + ($dataConclusao == null ? 43 : $dataConclusao.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $detalhesLiquidacao = this.getDetalhesLiquidacao();
        result = result * PRIME + ($detalhesLiquidacao == null ? 43 : $detalhesLiquidacao.hashCode());
        final java.lang.Object $regrasAplicadas = this.getRegrasAplicadas();
        result = result * PRIME + ($regrasAplicadas == null ? 43 : $regrasAplicadas.hashCode());
        final java.lang.Object $erroLiquidacao = this.getErroLiquidacao();
        result = result * PRIME + ($erroLiquidacao == null ? 43 : $erroLiquidacao.hashCode());
        final java.lang.Object $codigoBacen = this.getCodigoBacen();
        result = result * PRIME + ($codigoBacen == null ? 43 : $codigoBacen.hashCode());
        final java.lang.Object $codigoSPI = this.getCodigoSPI();
        result = result * PRIME + ($codigoSPI == null ? 43 : $codigoSPI.hashCode());
        final java.lang.Object $codigoSTR = this.getCodigoSTR();
        result = result * PRIME + ($codigoSTR == null ? 43 : $codigoSTR.hashCode());
        return result;
    }
}
