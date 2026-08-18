package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade Pagamento TED/DOC do Aurix.
 * Representa uma transferência TED ou DOC entre contas.
 */
@Entity
@Table(name = "pagamentos_ted", schema = "aurix")
public class PagamentoTed extends BaseEntity {
    private static final int CODIGO_LENGTH = 100;
    private static final int NAME_DESC_MAX_LENGTH = 140;
    private static final int SHORT_CODE_LENGTH = 10;
    private static final int LONG_MESSAGE_LENGTH = 500;
    private static final int DOCUMENT_LENGTH = 14;
    private static final int BANK_CODE_LENGTH = 10;
    private static final int AGENCY_LENGTH = 10;
    private static final int ACCOUNT_LENGTH = 20;
    private static final int ISPB_LENGTH = 8;
    private static final int DECIMAL_PRECISION = 15;
    private static final int DECIMAL_SCALE = 2;

    @NotBlank(message = "Código TED é obrigatório")
    @Column(name = "codigo_ted", unique = true, nullable = false, length = CODIGO_LENGTH)
    private String codigoTed;

    @NotNull(message = "Conta origem é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_origem_id", nullable = false)
    private Conta contaOrigem;

    @NotNull(message = "Tipo de pagamento é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pagamento", nullable = false)
    private TipoPagamento tipoPagamento;

    @NotBlank(message = "Nome do destinatário é obrigatório")
    @Size(max = NAME_DESC_MAX_LENGTH, message = "Nome do destinatário deve ter no máximo 140 caracteres")
    @Column(name = "nome_destinatario", nullable = false, length = NAME_DESC_MAX_LENGTH)
    private String nomeDestinatario;

    @Size(max = DOCUMENT_LENGTH, message = "CPF/CNPJ destino deve ter no máximo 14 caracteres")
    @Column(name = "cpf_cnpj_destino", length = DOCUMENT_LENGTH)
    private String cpfCnpjDestino;

    @NotBlank(message = "Banco destino é obrigatório")
    @Size(max = BANK_CODE_LENGTH, message = "Código do banco deve ter no máximo 10 caracteres")
    @Column(name = "banco_destino", nullable = false, length = BANK_CODE_LENGTH)
    private String bancoDestino;

    @NotBlank(message = "Agência destino é obrigatória")
    @Size(max = AGENCY_LENGTH, message = "Agência destino deve ter no máximo 10 caracteres")
    @Column(name = "agencia_destino", nullable = false, length = AGENCY_LENGTH)
    private String agenciaDestino;

    @NotBlank(message = "Conta destino é obrigatória")
    @Size(max = ACCOUNT_LENGTH, message = "Conta destino deve ter no máximo 20 caracteres")
    @Column(name = "conta_destino", nullable = false, length = ACCOUNT_LENGTH)
    private String contaDestino;

    @Size(max = ISPB_LENGTH, message = "ISPB deve ter no máximo 8 caracteres")
    @Column(name = "ispb_destino", length = ISPB_LENGTH)
    private String ispbDestino;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    @Column(name = "valor", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal valor;

    @Size(max = NAME_DESC_MAX_LENGTH, message = "Descrição deve ter no máximo 140 caracteres")
    @Column(name = "descricao", length = NAME_DESC_MAX_LENGTH)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusTed status = StatusTed.PENDENTE;

    @Column(name = "data_agendamento")
    private LocalDateTime dataAgendamento;

    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;

    @Size(max = SHORT_CODE_LENGTH, message = "Código retorno deve ter no máximo 10 caracteres")
    @Column(name = "codigo_retorno", length = SHORT_CODE_LENGTH)
    private String codigoRetorno;

    @Size(max = LONG_MESSAGE_LENGTH, message = "Mensagem retorno deve ter no máximo 500 caracteres")
    @Column(name = "mensagem_retorno", length = LONG_MESSAGE_LENGTH)
    private String mensagemRetorno;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_adicionais", columnDefinition = "jsonb")
    private String dadosAdicionais;

    /**
     * Tipo de pagamento bancário.
     */
    public enum TipoPagamento {
        TED("TED — Transferência Eletrônica Disponível"),
        DOC("DOC — Documento de Ordem de Crédito");

        private final String descricao;

        TipoPagamento(final String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Status do pagamento TED/DOC.
     */
    public enum StatusTed {
        PENDENTE("Pendente"),
        AGENDADO("Agendado"),
        EM_PROCESSAMENTO("Em Processamento"),
        PROCESSADO("Processado"),
        CANCELADO("Cancelado"),
        FALHADO("Falhado"),
        REVERTIDO("Revertido");

        private final String descricao;

        StatusTed(final String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoTed() {
        return this.codigoTed;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getContaOrigem() {
        return this.contaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public TipoPagamento getTipoPagamento() {
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
    public StatusTed getStatus() {
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
    public void setCodigoTed(final String codigoTed) {
        this.codigoTed = codigoTed;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaOrigem(final Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoPagamento(final TipoPagamento tipoPagamento) {
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
    public void setStatus(final StatusTed status) {
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
}
