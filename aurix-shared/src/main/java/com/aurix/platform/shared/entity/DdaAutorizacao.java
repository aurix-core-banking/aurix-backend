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
 * Entidade DDA — Débito Direto Autorizado do Aurix.
 * Representa a autorização prévia do titular para débito automático.
 */
@Entity
@Table(name = "dda_autorizacoes", schema = "aurix")
public class DdaAutorizacao extends BaseEntity {
    private static final int CODIGO_LENGTH = 100;
    private static final int NAME_MAX_LENGTH = 140;
    private static final int DOCUMENT_LENGTH = 14;
    private static final int CONVENIO_LENGTH = 20;
    private static final int LONG_MESSAGE_LENGTH = 500;
    private static final int DECIMAL_PRECISION = 15;
    private static final int DECIMAL_SCALE = 2;

    @NotBlank(message = "Código autorização é obrigatório")
    @Column(name = "codigo_autorizacao", unique = true, nullable = false, length = CODIGO_LENGTH)
    private String codigoAutorizacao;

    @NotNull(message = "Conta debitada é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_debitada_id", nullable = false)
    private Conta contaDebitada;

    @NotBlank(message = "Documento (CPF/CNPJ) é obrigatório")
    @Size(max = DOCUMENT_LENGTH, message = "CPF/CNPJ deve ter no máximo 14 caracteres")
    @Column(name = "documento_cpf_cnpj", nullable = false, length = DOCUMENT_LENGTH)
    private String documentoCpfCnpj;

    @NotBlank(message = "Nome do titular é obrigatório")
    @Size(max = NAME_MAX_LENGTH, message = "Nome do titular deve ter no máximo 140 caracteres")
    @Column(name = "nome_titular", nullable = false, length = NAME_MAX_LENGTH)
    private String nomeTitular;

    @NotBlank(message = "CNPJ do beneficiário é obrigatório")
    @Size(max = DOCUMENT_LENGTH, message = "CNPJ beneficiário deve ter no máximo 14 caracteres")
    @Column(name = "cnpj_beneficiario", nullable = false, length = DOCUMENT_LENGTH)
    private String cnpjBeneficiario;

    @NotBlank(message = "Nome do beneficiário é obrigatório")
    @Size(max = NAME_MAX_LENGTH, message = "Nome beneficiário deve ter no máximo 140 caracteres")
    @Column(name = "nome_beneficiario", nullable = false, length = NAME_MAX_LENGTH)
    private String nomeBeneficiario;

    @NotBlank(message = "Código de convênio é obrigatório")
    @Size(max = CONVENIO_LENGTH, message = "Código convênio deve ter no máximo 20 caracteres")
    @Column(name = "codigo_convenio", nullable = false, length = CONVENIO_LENGTH)
    private String codigoConvenio;

    @DecimalMin(value = "0.01", message = "Valor máximo de débito deve ser maior que zero")
    @Column(name = "valor_maximo_debito", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal valorMaximoDebito;

    @Column(name = "data_autorizacao", nullable = false)
    private LocalDateTime dataAutorizacao = LocalDateTime.now();

    @Column(name = "data_revogacao")
    private LocalDateTime dataRevogacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusDda status = StatusDda.ATIVA;

    @Size(max = LONG_MESSAGE_LENGTH, message = "Observações devem ter no máximo 500 caracteres")
    @Column(name = "observacoes", length = LONG_MESSAGE_LENGTH)
    private String observacoes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_adicionais", columnDefinition = "jsonb")
    private String dadosAdicionais;

    /**
     * Status da autorização DDA.
     */
    public enum StatusDda {
        ATIVA("Ativa"),
        REVOGADA("Revogada"),
        SUSPENSA("Suspensa"),
        EXPIRADA("Expirada");

        private final String descricao;

        StatusDda(final String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoAutorizacao() {
        return this.codigoAutorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getContaDebitada() {
        return this.contaDebitada;
    }

    @java.lang.SuppressWarnings("all")
    public String getDocumentoCpfCnpj() {
        return this.documentoCpfCnpj;
    }

    @java.lang.SuppressWarnings("all")
    public String getNomeTitular() {
        return this.nomeTitular;
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
    public String getCodigoConvenio() {
        return this.codigoConvenio;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMaximoDebito() {
        return this.valorMaximoDebito;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAutorizacao() {
        return this.dataAutorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataRevogacao() {
        return this.dataRevogacao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusDda getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getDadosAdicionais() {
        return this.dadosAdicionais;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoAutorizacao(final String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaDebitada(final Conta contaDebitada) {
        this.contaDebitada = contaDebitada;
    }

    @java.lang.SuppressWarnings("all")
    public void setDocumentoCpfCnpj(final String documentoCpfCnpj) {
        this.documentoCpfCnpj = documentoCpfCnpj;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeTitular(final String nomeTitular) {
        this.nomeTitular = nomeTitular;
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
    public void setCodigoConvenio(final String codigoConvenio) {
        this.codigoConvenio = codigoConvenio;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMaximoDebito(final BigDecimal valorMaximoDebito) {
        this.valorMaximoDebito = valorMaximoDebito;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAutorizacao(final LocalDateTime dataAutorizacao) {
        this.dataAutorizacao = dataAutorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataRevogacao(final LocalDateTime dataRevogacao) {
        this.dataRevogacao = dataRevogacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusDda status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setDadosAdicionais(final String dadosAdicionais) {
        this.dadosAdicionais = dadosAdicionais;
    }
}
