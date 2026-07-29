package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.Transacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para Transação do Aurix.
 */
public class TransacaoDTO {
    /**
     * Valor mínimo da transação: 0.01.
     */
    private static final String MIN_VALUE = "0.01";
    /**
     * Tamanho máximo da descrição: 500 caracteres.
     */
    private static final int MAX_DESC_SIZE = 500;
    /**
     * ID da transação.
     */
    private Long id;
    /**
     * ID da conta de origem.
     */
    private Long contaOrigemId;
    /**
     * Número da conta de origem.
     */
    private String contaOrigemNumero;
    /**
     * ID da conta de destino.
     */
    private Long contaDestinoId;
    /**
     * Número da conta de destino.
     */
    private String contaDestinoNumero;
    /**
     * Tipo técnico da transação (ex: PIX, TED, DOC, SAQUE).
     */
    @NotNull(message = "Tipo da transação é obrigatório")
    private Transacao.TipoTransacao tipoTransacao;
    /**
     * Valor financeiro da transação.
     */
    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = MIN_VALUE, message = "Valor deve ser maior que zero")
    private BigDecimal valor;
    /**
     * Descrição amigável da transação.
     */
    @Size(max = MAX_DESC_SIZE, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;
    /**
     * Status atual do processamento (PENDENTE, CONCLUIDO, FALHA).
     */
    private Transacao.StatusTransacao status;
    /**
     * Código de identificação único da transação.
     */
    private String codigoTransacao;
    /**
     * Dados específicos de PIX, se aplicável.
     */
    private String dadosPix;
    /**
     * Dados específicos de TED, se aplicável.
     */
    private String dadosTed;
    /**
     * Data e hora em que a transação foi solicitada.
     */
    private LocalDateTime dataTransacao;
    /**
     * Data e hora do processamento pela instituição.
     */
    private LocalDateTime dataProcessamento;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID da transação.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * ID da conta de origem.
     */
    @java.lang.SuppressWarnings("all")
    public Long getContaOrigemId() {
        return this.contaOrigemId;
    }

    /**
     * Número da conta de origem.
     */
    @java.lang.SuppressWarnings("all")
    public String getContaOrigemNumero() {
        return this.contaOrigemNumero;
    }

    /**
     * ID da conta de destino.
     */
    @java.lang.SuppressWarnings("all")
    public Long getContaDestinoId() {
        return this.contaDestinoId;
    }

    /**
     * Número da conta de destino.
     */
    @java.lang.SuppressWarnings("all")
    public String getContaDestinoNumero() {
        return this.contaDestinoNumero;
    }

    /**
     * Tipo técnico da transação (ex: PIX, TED, DOC, SAQUE).
     */
    @java.lang.SuppressWarnings("all")
    public Transacao.TipoTransacao getTipoTransacao() {
        return this.tipoTransacao;
    }

    /**
     * Valor financeiro da transação.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    /**
     * Descrição amigável da transação.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Status atual do processamento (PENDENTE, CONCLUIDO, FALHA).
     */
    @java.lang.SuppressWarnings("all")
    public Transacao.StatusTransacao getStatus() {
        return this.status;
    }

    /**
     * Código de identificação único da transação.
     */
    @java.lang.SuppressWarnings("all")
    public String getCodigoTransacao() {
        return this.codigoTransacao;
    }

    /**
     * Dados específicos de PIX, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosPix() {
        return this.dadosPix;
    }

    /**
     * Dados específicos de TED, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosTed() {
        return this.dadosTed;
    }

    /**
     * Data e hora em que a transação foi solicitada.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataTransacao() {
        return this.dataTransacao;
    }

    /**
     * Data e hora do processamento pela instituição.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    /**
     * Data de criação do registro.
     */
    @java.lang.SuppressWarnings("all")
    public String getDataCriacao() {
        return this.dataCriacao;
    }

    /**
     * Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public String getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    /**
     * ID da transação.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * ID da conta de origem.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaOrigemId(final Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    /**
     * Número da conta de origem.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaOrigemNumero(final String contaOrigemNumero) {
        this.contaOrigemNumero = contaOrigemNumero;
    }

    /**
     * ID da conta de destino.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaDestinoId(final Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    /**
     * Número da conta de destino.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaDestinoNumero(final String contaDestinoNumero) {
        this.contaDestinoNumero = contaDestinoNumero;
    }

    /**
     * Tipo técnico da transação (ex: PIX, TED, DOC, SAQUE).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoTransacao(final Transacao.TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    /**
     * Valor financeiro da transação.
     */
    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Descrição amigável da transação.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Status atual do processamento (PENDENTE, CONCLUIDO, FALHA).
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final Transacao.StatusTransacao status) {
        this.status = status;
    }

    /**
     * Código de identificação único da transação.
     */
    @java.lang.SuppressWarnings("all")
    public void setCodigoTransacao(final String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    /**
     * Dados específicos de PIX, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosPix(final String dadosPix) {
        this.dadosPix = dadosPix;
    }

    /**
     * Dados específicos de TED, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosTed(final String dadosTed) {
        this.dadosTed = dadosTed;
    }

    /**
     * Data e hora em que a transação foi solicitada.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataTransacao(final LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    /**
     * Data e hora do processamento pela instituição.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    /**
     * Data de criação do registro.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof TransacaoDTO)) return false;
        final TransacaoDTO other = (TransacaoDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$contaOrigemId = this.getContaOrigemId();
        final java.lang.Object other$contaOrigemId = other.getContaOrigemId();
        if (this$contaOrigemId == null ? other$contaOrigemId != null : !this$contaOrigemId.equals(other$contaOrigemId)) return false;
        final java.lang.Object this$contaDestinoId = this.getContaDestinoId();
        final java.lang.Object other$contaDestinoId = other.getContaDestinoId();
        if (this$contaDestinoId == null ? other$contaDestinoId != null : !this$contaDestinoId.equals(other$contaDestinoId)) return false;
        final java.lang.Object this$contaOrigemNumero = this.getContaOrigemNumero();
        final java.lang.Object other$contaOrigemNumero = other.getContaOrigemNumero();
        if (this$contaOrigemNumero == null ? other$contaOrigemNumero != null : !this$contaOrigemNumero.equals(other$contaOrigemNumero)) return false;
        final java.lang.Object this$contaDestinoNumero = this.getContaDestinoNumero();
        final java.lang.Object other$contaDestinoNumero = other.getContaDestinoNumero();
        if (this$contaDestinoNumero == null ? other$contaDestinoNumero != null : !this$contaDestinoNumero.equals(other$contaDestinoNumero)) return false;
        final java.lang.Object this$tipoTransacao = this.getTipoTransacao();
        final java.lang.Object other$tipoTransacao = other.getTipoTransacao();
        if (this$tipoTransacao == null ? other$tipoTransacao != null : !this$tipoTransacao.equals(other$tipoTransacao)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$codigoTransacao = this.getCodigoTransacao();
        final java.lang.Object other$codigoTransacao = other.getCodigoTransacao();
        if (this$codigoTransacao == null ? other$codigoTransacao != null : !this$codigoTransacao.equals(other$codigoTransacao)) return false;
        final java.lang.Object this$dadosPix = this.getDadosPix();
        final java.lang.Object other$dadosPix = other.getDadosPix();
        if (this$dadosPix == null ? other$dadosPix != null : !this$dadosPix.equals(other$dadosPix)) return false;
        final java.lang.Object this$dadosTed = this.getDadosTed();
        final java.lang.Object other$dadosTed = other.getDadosTed();
        if (this$dadosTed == null ? other$dadosTed != null : !this$dadosTed.equals(other$dadosTed)) return false;
        final java.lang.Object this$dataTransacao = this.getDataTransacao();
        final java.lang.Object other$dataTransacao = other.getDataTransacao();
        if (this$dataTransacao == null ? other$dataTransacao != null : !this$dataTransacao.equals(other$dataTransacao)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$dataCriacao = this.getDataCriacao();
        final java.lang.Object other$dataCriacao = other.getDataCriacao();
        if (this$dataCriacao == null ? other$dataCriacao != null : !this$dataCriacao.equals(other$dataCriacao)) return false;
        final java.lang.Object this$dataAtualizacao = this.getDataAtualizacao();
        final java.lang.Object other$dataAtualizacao = other.getDataAtualizacao();
        if (this$dataAtualizacao == null ? other$dataAtualizacao != null : !this$dataAtualizacao.equals(other$dataAtualizacao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof TransacaoDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $contaOrigemId = this.getContaOrigemId();
        result = result * PRIME + ($contaOrigemId == null ? 43 : $contaOrigemId.hashCode());
        final java.lang.Object $contaDestinoId = this.getContaDestinoId();
        result = result * PRIME + ($contaDestinoId == null ? 43 : $contaDestinoId.hashCode());
        final java.lang.Object $contaOrigemNumero = this.getContaOrigemNumero();
        result = result * PRIME + ($contaOrigemNumero == null ? 43 : $contaOrigemNumero.hashCode());
        final java.lang.Object $contaDestinoNumero = this.getContaDestinoNumero();
        result = result * PRIME + ($contaDestinoNumero == null ? 43 : $contaDestinoNumero.hashCode());
        final java.lang.Object $tipoTransacao = this.getTipoTransacao();
        result = result * PRIME + ($tipoTransacao == null ? 43 : $tipoTransacao.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $codigoTransacao = this.getCodigoTransacao();
        result = result * PRIME + ($codigoTransacao == null ? 43 : $codigoTransacao.hashCode());
        final java.lang.Object $dadosPix = this.getDadosPix();
        result = result * PRIME + ($dadosPix == null ? 43 : $dadosPix.hashCode());
        final java.lang.Object $dadosTed = this.getDadosTed();
        result = result * PRIME + ($dadosTed == null ? 43 : $dadosTed.hashCode());
        final java.lang.Object $dataTransacao = this.getDataTransacao();
        result = result * PRIME + ($dataTransacao == null ? 43 : $dataTransacao.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "TransacaoDTO(id=" + this.getId() + ", contaOrigemId=" + this.getContaOrigemId() + ", contaOrigemNumero=" + this.getContaOrigemNumero() + ", contaDestinoId=" + this.getContaDestinoId() + ", contaDestinoNumero=" + this.getContaDestinoNumero() + ", tipoTransacao=" + this.getTipoTransacao() + ", valor=" + this.getValor() + ", descricao=" + this.getDescricao() + ", status=" + this.getStatus() + ", codigoTransacao=" + this.getCodigoTransacao() + ", dadosPix=" + this.getDadosPix() + ", dadosTed=" + this.getDadosTed() + ", dataTransacao=" + this.getDataTransacao() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public TransacaoDTO() {
    }

    /**
     * Creates a new {@code TransacaoDTO} instance.
     *
     * @param id ID da transação.
     * @param contaOrigemId ID da conta de origem.
     * @param contaOrigemNumero Número da conta de origem.
     * @param contaDestinoId ID da conta de destino.
     * @param contaDestinoNumero Número da conta de destino.
     * @param tipoTransacao Tipo técnico da transação (ex: PIX, TED, DOC, SAQUE).
     * @param valor Valor financeiro da transação.
     * @param descricao Descrição amigável da transação.
     * @param status Status atual do processamento (PENDENTE, CONCLUIDO, FALHA).
     * @param codigoTransacao Código de identificação único da transação.
     * @param dadosPix Dados específicos de PIX, se aplicável.
     * @param dadosTed Dados específicos de TED, se aplicável.
     * @param dataTransacao Data e hora em que a transação foi solicitada.
     * @param dataProcessamento Data e hora do processamento pela instituição.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public TransacaoDTO(final Long id, final Long contaOrigemId, final String contaOrigemNumero, final Long contaDestinoId, final String contaDestinoNumero, final Transacao.TipoTransacao tipoTransacao, final BigDecimal valor, final String descricao, final Transacao.StatusTransacao status, final String codigoTransacao, final String dadosPix, final String dadosTed, final LocalDateTime dataTransacao, final LocalDateTime dataProcessamento, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.contaOrigemId = contaOrigemId;
        this.contaOrigemNumero = contaOrigemNumero;
        this.contaDestinoId = contaDestinoId;
        this.contaDestinoNumero = contaDestinoNumero;
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.descricao = descricao;
        this.status = status;
        this.codigoTransacao = codigoTransacao;
        this.dadosPix = dadosPix;
        this.dadosTed = dadosTed;
        this.dataTransacao = dataTransacao;
        this.dataProcessamento = dataProcessamento;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
