package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.PagamentoTed;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para Pagamento TED/DOC do Aurix.
 */
public class PagamentoTedDTO {
    private static final int MAX_TEXT_SIZE = 140;
    private static final int MAX_DOCUMENT_SIZE = 14;
    private static final int MAX_BANK_SIZE = 10;
    private static final int MAX_AGENCY_SIZE = 10;
    private static final int MAX_ACCOUNT_SIZE = 20;
    private static final int MAX_ISPB_SIZE = 8;
    private static final String MIN_VALUE = "0.01";

    private Long id;

    private String codigoTed;

    @NotNull(message = "Conta origem é obrigatória")
    private Long contaOrigemId;

    private String contaOrigemNumero;

    @NotNull(message = "Tipo de pagamento é obrigatório")
    private PagamentoTed.TipoPagamento tipoPagamento;

    @NotBlank(message = "Nome do destinatário é obrigatório")
    @Size(max = MAX_TEXT_SIZE, message = "Nome do destinatário deve ter no máximo 140 caracteres")
    private String nomeDestinatario;

    @Size(max = MAX_DOCUMENT_SIZE, message = "CPF/CNPJ destino deve ter no máximo 14 caracteres")
    private String cpfCnpjDestino;

    @NotBlank(message = "Banco destino é obrigatório")
    @Size(max = MAX_BANK_SIZE)
    private String bancoDestino;

    @NotBlank(message = "Agência destino é obrigatória")
    @Size(max = MAX_AGENCY_SIZE)
    private String agenciaDestino;

    @NotBlank(message = "Conta destino é obrigatória")
    @Size(max = MAX_ACCOUNT_SIZE)
    private String contaDestino;

    @Size(max = MAX_ISPB_SIZE)
    private String ispbDestino;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = MIN_VALUE, message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    @Size(max = MAX_TEXT_SIZE)
    private String descricao;

    private PagamentoTed.StatusTed status;

    private LocalDateTime dataAgendamento;

    private LocalDateTime dataProcessamento;

    private String codigoRetorno;

    private String mensagemRetorno;

    private String dadosAdicionais;

    private String dataCriacao;

    private String dataAtualizacao;

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoTed() {
        return this.codigoTed;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaOrigemId() {
        return this.contaOrigemId;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaOrigemNumero() {
        return this.contaOrigemNumero;
    }

    @java.lang.SuppressWarnings("all")
    public PagamentoTed.TipoPagamento getTipoPagamento() {
        return this.tipoPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeDestinatario() {
        return this.nomeDestinatario;
    }

    @java.lang.SuppressWarnings("all")
    public String getCpfCnpjDestino() {
        return this.cpfCnpjDestino;
    }

    @java.lang.SuppressWarnings("all")
    public String getBancoDestino() {
        return this.bancoDestino;
    }

    @java.lang.SuppressWarnings("all")
    public String getAgenciaDestino() {
        return this.agenciaDestino;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaDestino() {
        return this.contaDestino;
    }

    @java.lang.SuppressWarnings("all")
    public String getIspbDestino() {
        return this.ispbDestino;
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
    public PagamentoTed.StatusTed getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAgendamento() {
        return this.dataAgendamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
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
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
    }

    @java.lang.SuppressWarnings("all")
    public String getDataCriacao() {
        return this.dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoTed(final String codigoTed) {
        this.codigoTed = codigoTed;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaOrigemId(final Long contaOrigemId) {
        this.contaOrigemId = contaOrigemId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaOrigemNumero(final String contaOrigemNumero) {
        this.contaOrigemNumero = contaOrigemNumero;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoPagamento(final PagamentoTed.TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeDestinatario(final String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    @java.lang.SuppressWarnings("all")
    public void setCpfCnpjDestino(final String cpfCnpjDestino) {
        this.cpfCnpjDestino = cpfCnpjDestino;
    }

    @java.lang.SuppressWarnings("all")
    public void setBancoDestino(final String bancoDestino) {
        this.bancoDestino = bancoDestino;
    }

    @java.lang.SuppressWarnings("all")
    public void setAgenciaDestino(final String agenciaDestino) {
        this.agenciaDestino = agenciaDestino;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaDestino(final String contaDestino) {
        this.contaDestino = contaDestino;
    }

    @java.lang.SuppressWarnings("all")
    public void setIspbDestino(final String ispbDestino) {
        this.ispbDestino = ispbDestino;
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
    public void setStatus(final PagamentoTed.StatusTed status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAgendamento(final LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
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
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
