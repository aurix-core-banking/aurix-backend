package com.aurix.platform.banking.core.entity;

import com.aurix.platform.shared.entity.BaseEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "boletos", schema = "aurix")
public class Boleto extends BaseEntity {
    @Column(name = "numero_boleto", unique = true, nullable = false, length = 50)
    private String numeroBoleto;
    @Column(name = "linha_digitavel", nullable = false, length = 54)
    private String linhaDigitavel;
    @Column(name = "codigo_barras", length = 44)
    private String codigoBarras;
    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal valor;
    @Column(name = "data_vencimento", nullable = false)
    private LocalDate dataVencimento;
    @Column(name = "beneficiario_nome", nullable = false, length = 200)
    private String beneficiarioNome;
    @Column(name = "beneficiario_documento", length = 20)
    private String beneficiarioDocumento;
    @Column(name = "pagador_nome", length = 200)
    private String pagadorNome;
    @Column(name = "pagador_documento", length = 20)
    private String pagadorDocumento;
    @Column(name = "conta_id_pagador")
    private Long contaIdPagador;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusBoleto status = StatusBoleto.PENDENTE;
    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;
    @Column(name = "pdf_path", length = 500)
    private String pdfPath;
    @Column(length = 500)
    private String descricao;


    public enum StatusBoleto {
        PENDENTE, PAGO, CANCELADO, VENCIDO;
    }

    @java.lang.SuppressWarnings("all")
    public String getNumeroBoleto() {
        return this.numeroBoleto;
    }

    @java.lang.SuppressWarnings("all")
    public String getLinhaDigitavel() {
        return this.linhaDigitavel;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoBarras() {
        return this.codigoBarras;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public String getBeneficiarioNome() {
        return this.beneficiarioNome;
    }

    @java.lang.SuppressWarnings("all")
    public String getBeneficiarioDocumento() {
        return this.beneficiarioDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public String getPagadorNome() {
        return this.pagadorNome;
    }

    @java.lang.SuppressWarnings("all")
    public String getPagadorDocumento() {
        return this.pagadorDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaIdPagador() {
        return this.contaIdPagador;
    }

    @java.lang.SuppressWarnings("all")
    public StatusBoleto getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataPagamento() {
        return this.dataPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public String getPdfPath() {
        return this.pdfPath;
    }

    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    @java.lang.SuppressWarnings("all")
    public void setNumeroBoleto(final String numeroBoleto) {
        this.numeroBoleto = numeroBoleto;
    }

    @java.lang.SuppressWarnings("all")
    public void setLinhaDigitavel(final String linhaDigitavel) {
        this.linhaDigitavel = linhaDigitavel;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoBarras(final String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setBeneficiarioNome(final String beneficiarioNome) {
        this.beneficiarioNome = beneficiarioNome;
    }

    @java.lang.SuppressWarnings("all")
    public void setBeneficiarioDocumento(final String beneficiarioDocumento) {
        this.beneficiarioDocumento = beneficiarioDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public void setPagadorNome(final String pagadorNome) {
        this.pagadorNome = pagadorNome;
    }

    @java.lang.SuppressWarnings("all")
    public void setPagadorDocumento(final String pagadorDocumento) {
        this.pagadorDocumento = pagadorDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaIdPagador(final Long contaIdPagador) {
        this.contaIdPagador = contaIdPagador;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusBoleto status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataPagamento(final LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setPdfPath(final String pdfPath) {
        this.pdfPath = pdfPath;
    }

    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Boleto(numeroBoleto=" + this.getNumeroBoleto() + ", linhaDigitavel=" + this.getLinhaDigitavel() + ", codigoBarras=" + this.getCodigoBarras() + ", valor=" + this.getValor() + ", dataVencimento=" + this.getDataVencimento() + ", beneficiarioNome=" + this.getBeneficiarioNome() + ", beneficiarioDocumento=" + this.getBeneficiarioDocumento() + ", pagadorNome=" + this.getPagadorNome() + ", pagadorDocumento=" + this.getPagadorDocumento() + ", contaIdPagador=" + this.getContaIdPagador() + ", status=" + this.getStatus() + ", dataPagamento=" + this.getDataPagamento() + ", pdfPath=" + this.getPdfPath() + ", descricao=" + this.getDescricao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public Boleto() {
    }

    @java.lang.SuppressWarnings("all")
    public Boleto(final String numeroBoleto, final String linhaDigitavel, final String codigoBarras, final BigDecimal valor, final LocalDate dataVencimento, final String beneficiarioNome, final String beneficiarioDocumento, final String pagadorNome, final String pagadorDocumento, final Long contaIdPagador, final StatusBoleto status, final LocalDateTime dataPagamento, final String pdfPath, final String descricao) {
        this.numeroBoleto = numeroBoleto;
        this.linhaDigitavel = linhaDigitavel;
        this.codigoBarras = codigoBarras;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.beneficiarioNome = beneficiarioNome;
        this.beneficiarioDocumento = beneficiarioDocumento;
        this.pagadorNome = pagadorNome;
        this.pagadorDocumento = pagadorDocumento;
        this.contaIdPagador = contaIdPagador;
        this.status = status;
        this.dataPagamento = dataPagamento;
        this.pdfPath = pdfPath;
        this.descricao = descricao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Boleto)) return false;
        final Boleto other = (Boleto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$contaIdPagador = this.getContaIdPagador();
        final java.lang.Object other$contaIdPagador = other.getContaIdPagador();
        if (this$contaIdPagador == null ? other$contaIdPagador != null : !this$contaIdPagador.equals(other$contaIdPagador)) return false;
        final java.lang.Object this$numeroBoleto = this.getNumeroBoleto();
        final java.lang.Object other$numeroBoleto = other.getNumeroBoleto();
        if (this$numeroBoleto == null ? other$numeroBoleto != null : !this$numeroBoleto.equals(other$numeroBoleto)) return false;
        final java.lang.Object this$linhaDigitavel = this.getLinhaDigitavel();
        final java.lang.Object other$linhaDigitavel = other.getLinhaDigitavel();
        if (this$linhaDigitavel == null ? other$linhaDigitavel != null : !this$linhaDigitavel.equals(other$linhaDigitavel)) return false;
        final java.lang.Object this$codigoBarras = this.getCodigoBarras();
        final java.lang.Object other$codigoBarras = other.getCodigoBarras();
        if (this$codigoBarras == null ? other$codigoBarras != null : !this$codigoBarras.equals(other$codigoBarras)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$dataVencimento = this.getDataVencimento();
        final java.lang.Object other$dataVencimento = other.getDataVencimento();
        if (this$dataVencimento == null ? other$dataVencimento != null : !this$dataVencimento.equals(other$dataVencimento)) return false;
        final java.lang.Object this$beneficiarioNome = this.getBeneficiarioNome();
        final java.lang.Object other$beneficiarioNome = other.getBeneficiarioNome();
        if (this$beneficiarioNome == null ? other$beneficiarioNome != null : !this$beneficiarioNome.equals(other$beneficiarioNome)) return false;
        final java.lang.Object this$beneficiarioDocumento = this.getBeneficiarioDocumento();
        final java.lang.Object other$beneficiarioDocumento = other.getBeneficiarioDocumento();
        if (this$beneficiarioDocumento == null ? other$beneficiarioDocumento != null : !this$beneficiarioDocumento.equals(other$beneficiarioDocumento)) return false;
        final java.lang.Object this$pagadorNome = this.getPagadorNome();
        final java.lang.Object other$pagadorNome = other.getPagadorNome();
        if (this$pagadorNome == null ? other$pagadorNome != null : !this$pagadorNome.equals(other$pagadorNome)) return false;
        final java.lang.Object this$pagadorDocumento = this.getPagadorDocumento();
        final java.lang.Object other$pagadorDocumento = other.getPagadorDocumento();
        if (this$pagadorDocumento == null ? other$pagadorDocumento != null : !this$pagadorDocumento.equals(other$pagadorDocumento)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataPagamento = this.getDataPagamento();
        final java.lang.Object other$dataPagamento = other.getDataPagamento();
        if (this$dataPagamento == null ? other$dataPagamento != null : !this$dataPagamento.equals(other$dataPagamento)) return false;
        final java.lang.Object this$pdfPath = this.getPdfPath();
        final java.lang.Object other$pdfPath = other.getPdfPath();
        if (this$pdfPath == null ? other$pdfPath != null : !this$pdfPath.equals(other$pdfPath)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Boleto;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $contaIdPagador = this.getContaIdPagador();
        result = result * PRIME + ($contaIdPagador == null ? 43 : $contaIdPagador.hashCode());
        final java.lang.Object $numeroBoleto = this.getNumeroBoleto();
        result = result * PRIME + ($numeroBoleto == null ? 43 : $numeroBoleto.hashCode());
        final java.lang.Object $linhaDigitavel = this.getLinhaDigitavel();
        result = result * PRIME + ($linhaDigitavel == null ? 43 : $linhaDigitavel.hashCode());
        final java.lang.Object $codigoBarras = this.getCodigoBarras();
        result = result * PRIME + ($codigoBarras == null ? 43 : $codigoBarras.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $dataVencimento = this.getDataVencimento();
        result = result * PRIME + ($dataVencimento == null ? 43 : $dataVencimento.hashCode());
        final java.lang.Object $beneficiarioNome = this.getBeneficiarioNome();
        result = result * PRIME + ($beneficiarioNome == null ? 43 : $beneficiarioNome.hashCode());
        final java.lang.Object $beneficiarioDocumento = this.getBeneficiarioDocumento();
        result = result * PRIME + ($beneficiarioDocumento == null ? 43 : $beneficiarioDocumento.hashCode());
        final java.lang.Object $pagadorNome = this.getPagadorNome();
        result = result * PRIME + ($pagadorNome == null ? 43 : $pagadorNome.hashCode());
        final java.lang.Object $pagadorDocumento = this.getPagadorDocumento();
        result = result * PRIME + ($pagadorDocumento == null ? 43 : $pagadorDocumento.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataPagamento = this.getDataPagamento();
        result = result * PRIME + ($dataPagamento == null ? 43 : $dataPagamento.hashCode());
        final java.lang.Object $pdfPath = this.getPdfPath();
        result = result * PRIME + ($pdfPath == null ? 43 : $pdfPath.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        return result;
    }
}
