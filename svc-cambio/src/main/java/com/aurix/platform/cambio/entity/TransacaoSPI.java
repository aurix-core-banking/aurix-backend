package com.aurix.platform.cambio.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "transacoes_spi", schema = "aurix")
public class TransacaoSPI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String endToEndId;
    @Column(nullable = false)
    private String ispbOrigem;
    @Column(nullable = false)
    private String ispbDestino;
    @Column(nullable = false)
    private String contaOrigem;
    @Column(nullable = false)
    private String contaDestino;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valor;
    @Column(length = 500)
    private String descricao;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSPI status = StatusSPI.PENDENTE;
    @Column
    private LocalDateTime dataCriacao;
    @Column
    private LocalDateTime dataEnvio;
    @Column
    private LocalDateTime dataLiquidacao;
    @Column(length = 100)
    private String codigoRetorno;
    @Column(length = 500)
    private String mensagemRetorno;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_resposta", columnDefinition = "JSONB")
    private String dadosResposta;
    @Column
    private Integer tentativasEnvio = 0;
    @Column
    private Integer maxTentativas = 3;
    @Column(length = 1000)
    private String observacoes;


    public enum StatusSPI {
        PENDENTE, ENVIANDO, LIQUIDADA, REJEITADA, CANCELADA, ERRO;
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getEndToEndId() {
        return this.endToEndId;
    }

    @java.lang.SuppressWarnings("all")
    public String getIspbOrigem() {
        return this.ispbOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public String getIspbDestino() {
        return this.ispbDestino;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaOrigem() {
        return this.contaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaDestino() {
        return this.contaDestino;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusSPI getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataEnvio() {
        return this.dataEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataLiquidacao() {
        return this.dataLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoRetorno() {
        return this.codigoRetorno;
    }

    @java.lang.SuppressWarnings("all")
    public String getMensagemRetorno() {
        return this.mensagemRetorno;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosResposta() {
        return this.dadosResposta;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTentativasEnvio() {
        return this.tentativasEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getMaxTentativas() {
        return this.maxTentativas;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setEndToEndId(final String endToEndId) {
        this.endToEndId = endToEndId;
    }

    @java.lang.SuppressWarnings("all")
    public void setIspbOrigem(final String ispbOrigem) {
        this.ispbOrigem = ispbOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setIspbDestino(final String ispbDestino) {
        this.ispbDestino = ispbDestino;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaOrigem(final String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaDestino(final String contaDestino) {
        this.contaDestino = contaDestino;
    }

    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusSPI status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEnvio(final LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataLiquidacao(final LocalDateTime dataLiquidacao) {
        this.dataLiquidacao = dataLiquidacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoRetorno(final String codigoRetorno) {
        this.codigoRetorno = codigoRetorno;
    }

    @java.lang.SuppressWarnings("all")
    public void setMensagemRetorno(final String mensagemRetorno) {
        this.mensagemRetorno = mensagemRetorno;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosResposta(final String dadosResposta) {
        this.dadosResposta = dadosResposta;
    }

    @java.lang.SuppressWarnings("all")
    public void setTentativasEnvio(final Integer tentativasEnvio) {
        this.tentativasEnvio = tentativasEnvio;
    }

    @java.lang.SuppressWarnings("all")
    public void setMaxTentativas(final Integer maxTentativas) {
        this.maxTentativas = maxTentativas;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof TransacaoSPI)) return false;
        final TransacaoSPI other = (TransacaoSPI) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$tentativasEnvio = this.getTentativasEnvio();
        final java.lang.Object other$tentativasEnvio = other.getTentativasEnvio();
        if (this$tentativasEnvio == null ? other$tentativasEnvio != null : !this$tentativasEnvio.equals(other$tentativasEnvio)) return false;
        final java.lang.Object this$maxTentativas = this.getMaxTentativas();
        final java.lang.Object other$maxTentativas = other.getMaxTentativas();
        if (this$maxTentativas == null ? other$maxTentativas != null : !this$maxTentativas.equals(other$maxTentativas)) return false;
        final java.lang.Object this$endToEndId = this.getEndToEndId();
        final java.lang.Object other$endToEndId = other.getEndToEndId();
        if (this$endToEndId == null ? other$endToEndId != null : !this$endToEndId.equals(other$endToEndId)) return false;
        final java.lang.Object this$ispbOrigem = this.getIspbOrigem();
        final java.lang.Object other$ispbOrigem = other.getIspbOrigem();
        if (this$ispbOrigem == null ? other$ispbOrigem != null : !this$ispbOrigem.equals(other$ispbOrigem)) return false;
        final java.lang.Object this$ispbDestino = this.getIspbDestino();
        final java.lang.Object other$ispbDestino = other.getIspbDestino();
        if (this$ispbDestino == null ? other$ispbDestino != null : !this$ispbDestino.equals(other$ispbDestino)) return false;
        final java.lang.Object this$contaOrigem = this.getContaOrigem();
        final java.lang.Object other$contaOrigem = other.getContaOrigem();
        if (this$contaOrigem == null ? other$contaOrigem != null : !this$contaOrigem.equals(other$contaOrigem)) return false;
        final java.lang.Object this$contaDestino = this.getContaDestino();
        final java.lang.Object other$contaDestino = other.getContaDestino();
        if (this$contaDestino == null ? other$contaDestino != null : !this$contaDestino.equals(other$contaDestino)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        final java.lang.Object this$dataEnvio = this.getDataEnvio();
        final java.lang.Object other$dataEnvio = other.getDataEnvio();
        if (this$dataEnvio == null ? other$dataEnvio != null : !this$dataEnvio.equals(other$dataEnvio)) return false;
        final java.lang.Object this$dataLiquidacao = this.getDataLiquidacao();
        final java.lang.Object other$dataLiquidacao = other.getDataLiquidacao();
        if (this$dataLiquidacao == null ? other$dataLiquidacao != null : !this$dataLiquidacao.equals(other$dataLiquidacao)) return false;
        final java.lang.Object this$codigoRetorno = this.getCodigoRetorno();
        final java.lang.Object other$codigoRetorno = other.getCodigoRetorno();
        if (this$codigoRetorno == null ? other$codigoRetorno != null : !this$codigoRetorno.equals(other$codigoRetorno)) return false;
        final java.lang.Object this$mensagemRetorno = this.getMensagemRetorno();
        final java.lang.Object other$mensagemRetorno = other.getMensagemRetorno();
        if (this$mensagemRetorno == null ? other$mensagemRetorno != null : !this$mensagemRetorno.equals(other$mensagemRetorno)) return false;
        final java.lang.Object this$dadosResposta = this.getDadosResposta();
        final java.lang.Object other$dadosResposta = other.getDadosResposta();
        if (this$dadosResposta == null ? other$dadosResposta != null : !this$dadosResposta.equals(other$dadosResposta)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof TransacaoSPI;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $tentativasEnvio = this.getTentativasEnvio();
        result = result * PRIME + ($tentativasEnvio == null ? 43 : $tentativasEnvio.hashCode());
        final java.lang.Object $maxTentativas = this.getMaxTentativas();
        result = result * PRIME + ($maxTentativas == null ? 43 : $maxTentativas.hashCode());
        final java.lang.Object $endToEndId = this.getEndToEndId();
        result = result * PRIME + ($endToEndId == null ? 43 : $endToEndId.hashCode());
        final java.lang.Object $ispbOrigem = this.getIspbOrigem();
        result = result * PRIME + ($ispbOrigem == null ? 43 : $ispbOrigem.hashCode());
        final java.lang.Object $ispbDestino = this.getIspbDestino();
        result = result * PRIME + ($ispbDestino == null ? 43 : $ispbDestino.hashCode());
        final java.lang.Object $contaOrigem = this.getContaOrigem();
        result = result * PRIME + ($contaOrigem == null ? 43 : $contaOrigem.hashCode());
        final java.lang.Object $contaDestino = this.getContaDestino();
        result = result * PRIME + ($contaDestino == null ? 43 : $contaDestino.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataEnvio = this.getDataEnvio();
        result = result * PRIME + ($dataEnvio == null ? 43 : $dataEnvio.hashCode());
        final java.lang.Object $dataLiquidacao = this.getDataLiquidacao();
        result = result * PRIME + ($dataLiquidacao == null ? 43 : $dataLiquidacao.hashCode());
        final java.lang.Object $codigoRetorno = this.getCodigoRetorno();
        result = result * PRIME + ($codigoRetorno == null ? 43 : $codigoRetorno.hashCode());
        final java.lang.Object $mensagemRetorno = this.getMensagemRetorno();
        result = result * PRIME + ($mensagemRetorno == null ? 43 : $mensagemRetorno.hashCode());
        final java.lang.Object $dadosResposta = this.getDadosResposta();
        result = result * PRIME + ($dadosResposta == null ? 43 : $dadosResposta.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "TransacaoSPI(id=" + this.getId() + ", endToEndId=" + this.getEndToEndId() + ", ispbOrigem=" + this.getIspbOrigem() + ", ispbDestino=" + this.getIspbDestino() + ", contaOrigem=" + this.getContaOrigem() + ", contaDestino=" + this.getContaDestino() + ", valor=" + this.getValor() + ", descricao=" + this.getDescricao() + ", status=" + this.getStatus() + ", dataCriacao=" + this.getDataCriacao() + ", dataEnvio=" + this.getDataEnvio() + ", dataLiquidacao=" + this.getDataLiquidacao() + ", codigoRetorno=" + this.getCodigoRetorno() + ", mensagemRetorno=" + this.getMensagemRetorno() + ", dadosResposta=" + this.getDadosResposta() + ", tentativasEnvio=" + this.getTentativasEnvio() + ", maxTentativas=" + this.getMaxTentativas() + ", observacoes=" + this.getObservacoes() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public TransacaoSPI() {
    }

    @java.lang.SuppressWarnings("all")
    public TransacaoSPI(final Long id, final String endToEndId, final String ispbOrigem, final String ispbDestino, final String contaOrigem, final String contaDestino, final BigDecimal valor, final String descricao, final StatusSPI status, final LocalDateTime dataCriacao, final LocalDateTime dataEnvio, final LocalDateTime dataLiquidacao, final String codigoRetorno, final String mensagemRetorno, final String dadosResposta, final Integer tentativasEnvio, final Integer maxTentativas, final String observacoes) {
        this.id = id;
        this.endToEndId = endToEndId;
        this.ispbOrigem = ispbOrigem;
        this.ispbDestino = ispbDestino;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
        this.descricao = descricao;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.dataEnvio = dataEnvio;
        this.dataLiquidacao = dataLiquidacao;
        this.codigoRetorno = codigoRetorno;
        this.mensagemRetorno = mensagemRetorno;
        this.dadosResposta = dadosResposta;
        this.tentativasEnvio = tentativasEnvio;
        this.maxTentativas = maxTentativas;
        this.observacoes = observacoes;
    }
}
