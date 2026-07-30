package com.aurix.platform.banking.core.dto;

import com.aurix.platform.banking.core.entity.Liquidacao;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LiquidacaoDTO {
    private Long id;
    private String codigoLiquidacao;
    private Long transacaoId;
    private String tipoLiquidacao;
    private String status;
    private BigDecimal valorLiquidacao;
    private BigDecimal valorTaxa;
    private BigDecimal valorIOF;
    private BigDecimal valorTotal;
    private LocalDateTime dataLiquidacao;
    private LocalDateTime dataVencimento;
    private LocalDateTime dataProcessamento;
    private LocalDateTime dataConclusao;
    private String observacoes;
    private String detalhesLiquidacao;
    private String regrasAplicadas;
    private String erroLiquidacao;
    private Integer tentativasLiquidacao;
    private Integer maxTentativas;
    private Boolean processamentoAutomatico;
    private Boolean reversivel;
    private String codigoBacen;
    private String codigoSPI;
    private String codigoSTR;

    public static LiquidacaoDTO fromEntity(Liquidacao liquidacao) {
        LiquidacaoDTO dto = new LiquidacaoDTO();
        dto.setId(liquidacao.getId());
        dto.setCodigoLiquidacao(liquidacao.getCodigoLiquidacao());
        dto.setTransacaoId(liquidacao.getTransacao().getId());
        dto.setTipoLiquidacao(liquidacao.getTipoLiquidacao().name());
        dto.setStatus(liquidacao.getStatus().name());
        dto.setValorLiquidacao(liquidacao.getValorLiquidacao());
        dto.setValorTaxa(liquidacao.getValorTaxa());
        dto.setValorIOF(liquidacao.getValorIOF());
        dto.setValorTotal(liquidacao.getValorTotal());
        dto.setDataLiquidacao(liquidacao.getDataLiquidacao());
        dto.setDataVencimento(liquidacao.getDataVencimento());
        dto.setDataProcessamento(liquidacao.getDataProcessamento());
        dto.setDataConclusao(liquidacao.getDataConclusao());
        dto.setObservacoes(liquidacao.getObservacoes());
        dto.setDetalhesLiquidacao(liquidacao.getDetalhesLiquidacao());
        dto.setRegrasAplicadas(liquidacao.getRegrasAplicadas());
        dto.setErroLiquidacao(liquidacao.getErroLiquidacao());
        dto.setTentativasLiquidacao(liquidacao.getTentativasLiquidacao());
        dto.setMaxTentativas(liquidacao.getMaxTentativas());
        dto.setProcessamentoAutomatico(liquidacao.getProcessamentoAutomatico());
        dto.setReversivel(liquidacao.getReversivel());
        dto.setCodigoBacen(liquidacao.getCodigoBacen());
        dto.setCodigoSPI(liquidacao.getCodigoSPI());
        dto.setCodigoSTR(liquidacao.getCodigoSTR());
        return dto;
    }

    public Liquidacao toEntity() {
        Liquidacao liquidacao = new Liquidacao();
        liquidacao.setId(this.id);
        liquidacao.setCodigoLiquidacao(this.codigoLiquidacao);
        liquidacao.setTipoLiquidacao(Liquidacao.TipoLiquidacao.valueOf(this.tipoLiquidacao));
        liquidacao.setStatus(Liquidacao.StatusLiquidacao.valueOf(this.status));
        liquidacao.setValorLiquidacao(this.valorLiquidacao);
        liquidacao.setValorTaxa(this.valorTaxa);
        liquidacao.setValorIOF(this.valorIOF);
        liquidacao.setValorTotal(this.valorTotal);
        liquidacao.setDataLiquidacao(this.dataLiquidacao);
        liquidacao.setDataVencimento(this.dataVencimento);
        liquidacao.setDataProcessamento(this.dataProcessamento);
        liquidacao.setDataConclusao(this.dataConclusao);
        liquidacao.setObservacoes(this.observacoes);
        liquidacao.setDetalhesLiquidacao(this.detalhesLiquidacao);
        liquidacao.setRegrasAplicadas(this.regrasAplicadas);
        liquidacao.setErroLiquidacao(this.erroLiquidacao);
        liquidacao.setTentativasLiquidacao(this.tentativasLiquidacao);
        liquidacao.setMaxTentativas(this.maxTentativas);
        liquidacao.setProcessamentoAutomatico(this.processamentoAutomatico);
        liquidacao.setReversivel(this.reversivel);
        liquidacao.setCodigoBacen(this.codigoBacen);
        liquidacao.setCodigoSPI(this.codigoSPI);
        liquidacao.setCodigoSTR(this.codigoSTR);
        return liquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoLiquidacao() {
        return this.codigoLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTransacaoId() {
        return this.transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getTipoLiquidacao() {
        return this.tipoLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getStatus() {
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
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoLiquidacao(final String codigoLiquidacao) {
        this.codigoLiquidacao = codigoLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacaoId(final Long transacaoId) {
        this.transacaoId = transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoLiquidacao(final String tipoLiquidacao) {
        this.tipoLiquidacao = tipoLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) {
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
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LiquidacaoDTO)) return false;
        final LiquidacaoDTO other = (LiquidacaoDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$transacaoId = this.getTransacaoId();
        final java.lang.Object other$transacaoId = other.getTransacaoId();
        if (this$transacaoId == null ? other$transacaoId != null : !this$transacaoId.equals(other$transacaoId)) return false;
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
        return other instanceof LiquidacaoDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $transacaoId = this.getTransacaoId();
        result = result * PRIME + ($transacaoId == null ? 43 : $transacaoId.hashCode());
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

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "LiquidacaoDTO(id=" + this.getId() + ", codigoLiquidacao=" + this.getCodigoLiquidacao() + ", transacaoId=" + this.getTransacaoId() + ", tipoLiquidacao=" + this.getTipoLiquidacao() + ", status=" + this.getStatus() + ", valorLiquidacao=" + this.getValorLiquidacao() + ", valorTaxa=" + this.getValorTaxa() + ", valorIOF=" + this.getValorIOF() + ", valorTotal=" + this.getValorTotal() + ", dataLiquidacao=" + this.getDataLiquidacao() + ", dataVencimento=" + this.getDataVencimento() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataConclusao=" + this.getDataConclusao() + ", observacoes=" + this.getObservacoes() + ", detalhesLiquidacao=" + this.getDetalhesLiquidacao() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", erroLiquidacao=" + this.getErroLiquidacao() + ", tentativasLiquidacao=" + this.getTentativasLiquidacao() + ", maxTentativas=" + this.getMaxTentativas() + ", processamentoAutomatico=" + this.getProcessamentoAutomatico() + ", reversivel=" + this.getReversivel() + ", codigoBacen=" + this.getCodigoBacen() + ", codigoSPI=" + this.getCodigoSPI() + ", codigoSTR=" + this.getCodigoSTR() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public LiquidacaoDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public LiquidacaoDTO(final Long id, final String codigoLiquidacao, final Long transacaoId, final String tipoLiquidacao, final String status, final BigDecimal valorLiquidacao, final BigDecimal valorTaxa, final BigDecimal valorIOF, final BigDecimal valorTotal, final LocalDateTime dataLiquidacao, final LocalDateTime dataVencimento, final LocalDateTime dataProcessamento, final LocalDateTime dataConclusao, final String observacoes, final String detalhesLiquidacao, final String regrasAplicadas, final String erroLiquidacao, final Integer tentativasLiquidacao, final Integer maxTentativas, final Boolean processamentoAutomatico, final Boolean reversivel, final String codigoBacen, final String codigoSPI, final String codigoSTR) {
        this.id = id;
        this.codigoLiquidacao = codigoLiquidacao;
        this.transacaoId = transacaoId;
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
}
