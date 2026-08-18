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
 * Entidade DDA — Débito Agendado do Aurix.
 * Representa um débito automático agendado via DDA.
 */
@Entity
@Table(name = "dda_debitos", schema = "aurix")
public class DdaDebito extends BaseEntity {
    private static final int CODIGO_LENGTH = 100;
    private static final int NAME_MAX_LENGTH = 140;
    private static final int DOCUMENT_LENGTH = 14;
    private static final int DESC_LENGTH = 255;
    private static final int SHORT_CODE_LENGTH = 10;
    private static final int LONG_MESSAGE_LENGTH = 500;
    private static final int DECIMAL_PRECISION = 15;
    private static final int DECIMAL_SCALE = 2;

    @NotBlank(message = "Código débito é obrigatório")
    @Column(name = "codigo_debito", unique = true, nullable = false, length = CODIGO_LENGTH)
    private String codigoDebito;

    @NotNull(message = "Autorização é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autorizacao_id", nullable = false)
    private DdaAutorizacao autorizacao;

    @NotNull(message = "Conta debitada é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_debitada_id", nullable = false)
    private Conta contaDebitada;

    @NotBlank(message = "CNPJ beneficiário é obrigatório")
    @Size(max = DOCUMENT_LENGTH, message = "CNPJ deve ter no máximo 14 caracteres")
    @Column(name = "cnpj_beneficiario", nullable = false, length = DOCUMENT_LENGTH)
    private String cnpjBeneficiario;

    @NotBlank(message = "Nome beneficiário é obrigatório")
    @Size(max = NAME_MAX_LENGTH, message = "Nome beneficiário deve ter no máximo 140 caracteres")
    @Column(name = "nome_beneficiario", nullable = false, length = NAME_MAX_LENGTH)
    private String nomeBeneficiario;

    @NotNull(message = "Valor do débito é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor do débito deve ser maior que zero")
    @Column(name = "valor_debito", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal valorDebito;

    @NotNull(message = "Data de vencimento é obrigatória")
    @Column(name = "data_vencimento", nullable = false)
    private LocalDateTime dataVencimento;

    @Column(name = "data_notificacao")
    private LocalDateTime dataNotificacao;

    @Column(name = "data_debito")
    private LocalDateTime dataDebito;

    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusDebito status = StatusDebito.AGENDADO;

    @Size(max = DESC_LENGTH, message = "Descrição deve ter no máximo 255 caracteres")
    @Column(name = "descricao", length = DESC_LENGTH)
    private String descricao;

    @Size(max = SHORT_CODE_LENGTH)
    @Column(name = "codigo_retorno", length = SHORT_CODE_LENGTH)
    private String codigoRetorno;

    @Size(max = LONG_MESSAGE_LENGTH)
    @Column(name = "mensagem_retorno", length = LONG_MESSAGE_LENGTH)
    private String mensagemRetorno;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_adicionais", columnDefinition = "jsonb")
    private String dadosAdicionais;

    /**
     * Status do débito DDA.
     */
    public enum StatusDebito {
        AGENDADO("Agendado"),
        NOTIFICADO("Notificado"),
        EM_PROCESSAMENTO("Em Processamento"),
        DEBITADO("Debitado"),
        PAGO("Pago"),
        FALHADO("Falhado"),
        CANCELADO("Cancelado"),
        REVERTIDO("Revertido");

        private final String descricao;

        StatusDebito(final String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoDebito() {
        return this.codigoDebito;
    }

    @java.lang.SuppressWarnings("all")
    public DdaAutorizacao getAutorizacao() {
        return this.autorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getContaDebitada() {
        return this.contaDebitada;
    }

    @java.lang.SuppressWarnings("all")
    public String getCnpjBeneficiario() {
        return this.cnpjBeneficiario;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeBeneficiario() {
        return this.nomeBeneficiario;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorDebito() {
        return this.valorDebito;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataNotificacao() {
        return this.dataNotificacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataDebito() {
        return this.dataDebito;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public StatusDebito getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
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
    public void setCodigoDebito(final String codigoDebito) {
        this.codigoDebito = codigoDebito;
    }

    @java.lang.SuppressWarnings("all")
    public void setAutorizacao(final DdaAutorizacao autorizacao) {
        this.autorizacao = autorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaDebitada(final Conta contaDebitada) {
        this.contaDebitada = contaDebitada;
    }

    @java.lang.SuppressWarnings("all")
    public void setCnpjBeneficiario(final String cnpjBeneficiario) {
        this.cnpjBeneficiario = cnpjBeneficiario;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeBeneficiario(final String nomeBeneficiario) {
        this.nomeBeneficiario = nomeBeneficiario;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorDebito(final BigDecimal valorDebito) {
        this.valorDebito = valorDebito;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataNotificacao(final LocalDateTime dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataDebito(final LocalDateTime dataDebito) {
        this.dataDebito = dataDebito;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusDebito status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
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
