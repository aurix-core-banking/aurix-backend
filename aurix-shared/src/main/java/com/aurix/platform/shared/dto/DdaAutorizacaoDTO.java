package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.DdaAutorizacao;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para DDA — Débito Direto Autorizado do Aurix.
 */
public class DdaAutorizacaoDTO {
    private static final int MAX_NAME_SIZE = 140;
    private static final int MAX_DOCUMENT_SIZE = 14;
    private static final int MAX_CONVENIO_SIZE = 20;
    private static final int MAX_MESSAGE_SIZE = 500;

    private Long id;

    private String codigoAutorizacao;

    @NotNull(message = "Conta debitada é obrigatória")
    private Long contaDebitadaId;

    private String contaDebitadaNumero;

    @NotBlank(message = "CPF/CNPJ é obrigatório")
    @Size(max = MAX_DOCUMENT_SIZE)
    private String documentoCpfCnpj;

    @NotBlank(message = "Nome do titular é obrigatório")
    @Size(max = MAX_NAME_SIZE)
    private String nomeTitular;

    @NotBlank(message = "CNPJ do beneficiário é obrigatório")
    @Size(max = MAX_DOCUMENT_SIZE)
    private String cnpjBeneficiario;

    @NotBlank(message = "Nome do beneficiário é obrigatório")
    @Size(max = MAX_NAME_SIZE)
    private String nomeBeneficiario;

    @NotBlank(message = "Código de convênio é obrigatório")
    @Size(max = MAX_CONVENIO_SIZE)
    private String codigoConvenio;

    @DecimalMin(value = "0.01", message = "Valor máximo deve ser maior que zero")
    private BigDecimal valorMaximoDebito;

    private DdaAutorizacao.StatusDda status;

    private LocalDateTime dataAutorizacao;

    private LocalDateTime dataRevogacao;

    @Size(max = MAX_MESSAGE_SIZE)
    private String observacoes;

    private String dadosAdicionais;

    private String dataCriacao;

    private String dataAtualizacao;

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoAutorizacao() {
        return this.codigoAutorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaDebitadaId() {
        return this.contaDebitadaId;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaDebitadaNumero() {
        return this.contaDebitadaNumero;
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
    public DdaAutorizacao.StatusDda getStatus() {
        return this.status;
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
    public String getObservacoes() {
        return this.observacoes;
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
    public void setCodigoAutorizacao(final String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaDebitadaId(final Long contaDebitadaId) {
        this.contaDebitadaId = contaDebitadaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaDebitadaNumero(final String contaDebitadaNumero) {
        this.contaDebitadaNumero = contaDebitadaNumero;
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
    public void setStatus(final DdaAutorizacao.StatusDda status) {
        this.status = status;
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
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
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
