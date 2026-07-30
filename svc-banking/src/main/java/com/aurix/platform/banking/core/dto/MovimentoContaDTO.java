package com.aurix.platform.banking.core.dto;

import com.aurix.platform.banking.core.entity.MovimentoConta;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovimentoContaDTO {
    private Long id;
    private String codigoMovimento;
    private Long contaId;
    private Long transacaoId;
    private Long liquidacaoId;
    private String tipoMovimento;
    private String status;
    private BigDecimal valorMovimento;
    private BigDecimal saldoAnterior;
    private BigDecimal saldoPosterior;
    private BigDecimal saldoDisponivelAnterior;
    private BigDecimal saldoDisponivelPosterior;
    private BigDecimal saldoBloqueadoAnterior;
    private BigDecimal saldoBloqueadoPosterior;
    private BigDecimal saldoPendenteAnterior;
    private BigDecimal saldoPendentePosterior;
    private LocalDateTime dataMovimento;
    private LocalDateTime dataProcessamento;
    private LocalDateTime dataConclusao;
    private String descricaoMovimento;
    private String observacoes;
    private String detalhesMovimento;
    private String regrasAplicadas;
    private Boolean processamentoAutomatico;
    private Boolean reversivel;
    private String codigoContraparte;
    private String codigoBacen;
    private String codigoSPI;
    private String codigoSTR;
    private Integer versaoSaldo;
    private Boolean saldoConsistente;

    public static MovimentoContaDTO fromEntity(MovimentoConta movimento) {
        MovimentoContaDTO dto = new MovimentoContaDTO();
        dto.setId(movimento.getId());
        dto.setCodigoMovimento(movimento.getCodigoMovimento());
        dto.setContaId(movimento.getConta().getId());
        dto.setTransacaoId(movimento.getTransacao() != null ? movimento.getTransacao().getId() : null);
        dto.setLiquidacaoId(movimento.getLiquidacao() != null ? movimento.getLiquidacao().getId() : null);
        dto.setTipoMovimento(movimento.getTipoMovimento().name());
        dto.setStatus(movimento.getStatus().name());
        dto.setValorMovimento(movimento.getValorMovimento());
        dto.setSaldoAnterior(movimento.getSaldoAnterior());
        dto.setSaldoPosterior(movimento.getSaldoPosterior());
        dto.setSaldoDisponivelAnterior(movimento.getSaldoDisponivelAnterior());
        dto.setSaldoDisponivelPosterior(movimento.getSaldoDisponivelPosterior());
        dto.setSaldoBloqueadoAnterior(movimento.getSaldoBloqueadoAnterior());
        dto.setSaldoBloqueadoPosterior(movimento.getSaldoBloqueadoPosterior());
        dto.setSaldoPendenteAnterior(movimento.getSaldoPendenteAnterior());
        dto.setSaldoPendentePosterior(movimento.getSaldoPendentePosterior());
        dto.setDataMovimento(movimento.getDataMovimento());
        dto.setDataProcessamento(movimento.getDataProcessamento());
        dto.setDataConclusao(movimento.getDataConclusao());
        dto.setDescricaoMovimento(movimento.getDescricaoMovimento());
        dto.setObservacoes(movimento.getObservacoes());
        dto.setDetalhesMovimento(movimento.getDetalhesMovimento());
        dto.setRegrasAplicadas(movimento.getRegrasAplicadas());
        dto.setProcessamentoAutomatico(movimento.getProcessamentoAutomatico());
        dto.setReversivel(movimento.getReversivel());
        dto.setCodigoContraparte(movimento.getCodigoContraparte());
        dto.setCodigoBacen(movimento.getCodigoBacen());
        dto.setCodigoSPI(movimento.getCodigoSPI());
        dto.setCodigoSTR(movimento.getCodigoSTR());
        dto.setVersaoSaldo(movimento.getVersaoSaldo());
        dto.setSaldoConsistente(movimento.getSaldoConsistente());
        return dto;
    }

    public MovimentoConta toEntity() {
        MovimentoConta movimento = new MovimentoConta();
        movimento.setId(this.id);
        movimento.setCodigoMovimento(this.codigoMovimento);
        if (this.tipoMovimento != null) {
            movimento.setTipoMovimento(MovimentoConta.TipoMovimento.valueOf(this.tipoMovimento));
        }
        if (this.status != null) {
            movimento.setStatus(MovimentoConta.StatusMovimento.valueOf(this.status));
        }
        movimento.setValorMovimento(this.valorMovimento);
        movimento.setSaldoAnterior(this.saldoAnterior);
        movimento.setSaldoPosterior(this.saldoPosterior);
        movimento.setSaldoDisponivelAnterior(this.saldoDisponivelAnterior);
        movimento.setSaldoDisponivelPosterior(this.saldoDisponivelPosterior);
        movimento.setSaldoBloqueadoAnterior(this.saldoBloqueadoAnterior);
        movimento.setSaldoBloqueadoPosterior(this.saldoBloqueadoPosterior);
        movimento.setSaldoPendenteAnterior(this.saldoPendenteAnterior);
        movimento.setSaldoPendentePosterior(this.saldoPendentePosterior);
        movimento.setDataMovimento(this.dataMovimento);
        movimento.setDataProcessamento(this.dataProcessamento);
        movimento.setDataConclusao(this.dataConclusao);
        movimento.setDescricaoMovimento(this.descricaoMovimento);
        movimento.setObservacoes(this.observacoes);
        movimento.setDetalhesMovimento(this.detalhesMovimento);
        movimento.setRegrasAplicadas(this.regrasAplicadas);
        if (this.processamentoAutomatico != null) {
            movimento.setProcessamentoAutomatico(this.processamentoAutomatico);
        }
        if (this.reversivel != null) {
            movimento.setReversivel(this.reversivel);
        }
        movimento.setCodigoContraparte(this.codigoContraparte);
        movimento.setCodigoBacen(this.codigoBacen);
        movimento.setCodigoSPI(this.codigoSPI);
        movimento.setCodigoSTR(this.codigoSTR);
        if (this.versaoSaldo != null) {
            movimento.setVersaoSaldo(this.versaoSaldo);
        }
        if (this.saldoConsistente != null) {
            movimento.setSaldoConsistente(this.saldoConsistente);
        }
        return movimento;
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoMovimento() {
        return this.codigoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getTransacaoId() {
        return this.transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getLiquidacaoId() {
        return this.liquidacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getTipoMovimento() {
        return this.tipoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getStatus() {
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
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoMovimento(final String codigoMovimento) {
        this.codigoMovimento = codigoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTransacaoId(final Long transacaoId) {
        this.transacaoId = transacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setLiquidacaoId(final Long liquidacaoId) {
        this.liquidacaoId = liquidacaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoMovimento(final String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final String status) {
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
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof MovimentoContaDTO)) return false;
        final MovimentoContaDTO other = (MovimentoContaDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$contaId = this.getContaId();
        final java.lang.Object other$contaId = other.getContaId();
        if (this$contaId == null ? other$contaId != null : !this$contaId.equals(other$contaId)) return false;
        final java.lang.Object this$transacaoId = this.getTransacaoId();
        final java.lang.Object other$transacaoId = other.getTransacaoId();
        if (this$transacaoId == null ? other$transacaoId != null : !this$transacaoId.equals(other$transacaoId)) return false;
        final java.lang.Object this$liquidacaoId = this.getLiquidacaoId();
        final java.lang.Object other$liquidacaoId = other.getLiquidacaoId();
        if (this$liquidacaoId == null ? other$liquidacaoId != null : !this$liquidacaoId.equals(other$liquidacaoId)) return false;
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
        return other instanceof MovimentoContaDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $contaId = this.getContaId();
        result = result * PRIME + ($contaId == null ? 43 : $contaId.hashCode());
        final java.lang.Object $transacaoId = this.getTransacaoId();
        result = result * PRIME + ($transacaoId == null ? 43 : $transacaoId.hashCode());
        final java.lang.Object $liquidacaoId = this.getLiquidacaoId();
        result = result * PRIME + ($liquidacaoId == null ? 43 : $liquidacaoId.hashCode());
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

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "MovimentoContaDTO(id=" + this.getId() + ", codigoMovimento=" + this.getCodigoMovimento() + ", contaId=" + this.getContaId() + ", transacaoId=" + this.getTransacaoId() + ", liquidacaoId=" + this.getLiquidacaoId() + ", tipoMovimento=" + this.getTipoMovimento() + ", status=" + this.getStatus() + ", valorMovimento=" + this.getValorMovimento() + ", saldoAnterior=" + this.getSaldoAnterior() + ", saldoPosterior=" + this.getSaldoPosterior() + ", saldoDisponivelAnterior=" + this.getSaldoDisponivelAnterior() + ", saldoDisponivelPosterior=" + this.getSaldoDisponivelPosterior() + ", saldoBloqueadoAnterior=" + this.getSaldoBloqueadoAnterior() + ", saldoBloqueadoPosterior=" + this.getSaldoBloqueadoPosterior() + ", saldoPendenteAnterior=" + this.getSaldoPendenteAnterior() + ", saldoPendentePosterior=" + this.getSaldoPendentePosterior() + ", dataMovimento=" + this.getDataMovimento() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataConclusao=" + this.getDataConclusao() + ", descricaoMovimento=" + this.getDescricaoMovimento() + ", observacoes=" + this.getObservacoes() + ", detalhesMovimento=" + this.getDetalhesMovimento() + ", regrasAplicadas=" + this.getRegrasAplicadas() + ", processamentoAutomatico=" + this.getProcessamentoAutomatico() + ", reversivel=" + this.getReversivel() + ", codigoContraparte=" + this.getCodigoContraparte() + ", codigoBacen=" + this.getCodigoBacen() + ", codigoSPI=" + this.getCodigoSPI() + ", codigoSTR=" + this.getCodigoSTR() + ", versaoSaldo=" + this.getVersaoSaldo() + ", saldoConsistente=" + this.getSaldoConsistente() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public MovimentoContaDTO() {
    }

    @java.lang.SuppressWarnings("all")
    public MovimentoContaDTO(final Long id, final String codigoMovimento, final Long contaId, final Long transacaoId, final Long liquidacaoId, final String tipoMovimento, final String status, final BigDecimal valorMovimento, final BigDecimal saldoAnterior, final BigDecimal saldoPosterior, final BigDecimal saldoDisponivelAnterior, final BigDecimal saldoDisponivelPosterior, final BigDecimal saldoBloqueadoAnterior, final BigDecimal saldoBloqueadoPosterior, final BigDecimal saldoPendenteAnterior, final BigDecimal saldoPendentePosterior, final LocalDateTime dataMovimento, final LocalDateTime dataProcessamento, final LocalDateTime dataConclusao, final String descricaoMovimento, final String observacoes, final String detalhesMovimento, final String regrasAplicadas, final Boolean processamentoAutomatico, final Boolean reversivel, final String codigoContraparte, final String codigoBacen, final String codigoSPI, final String codigoSTR, final Integer versaoSaldo, final Boolean saldoConsistente) {
        this.id = id;
        this.codigoMovimento = codigoMovimento;
        this.contaId = contaId;
        this.transacaoId = transacaoId;
        this.liquidacaoId = liquidacaoId;
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
}
