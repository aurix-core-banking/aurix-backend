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
 * Entidade Pagamento Boleto Registrado do Aurix.
 * Representa um boleto registrado na câmara (CEPEL) com 生命周期 completo.
 */
@Entity
@Table(name = "pagamentos_boleto", schema = "aurix")
public class PagamentoBoleto extends BaseEntity {
    private static final int CODIGO_LENGTH = 100;
    private static final int NOSSO_NUMERO_LENGTH = 20;
    private static final int CARTEIRA_LENGTH = 10;
    private static final int CONVENIO_LENGTH = 20;
    private static final int NAME_MAX_LENGTH = 140;
    private static final int DOCUMENT_LENGTH = 14;
    private static final int ADDRESS_LENGTH = 255;
    private static final int CITY_LENGTH = 100;
    private static final int UF_LENGTH = 2;
    private static final int CEP_LENGTH = 9;
    private static final int SHORT_CODE_LENGTH = 10;
    private static final int LONG_MESSAGE_LENGTH = 500;
    private static final int BARRAS_LENGTH = 54;
    private static final int DIGITAVEL_LENGTH = 54;
    private static final int DECIMAL_PRECISION = 15;
    private static final int DECIMAL_SCALE = 2;

    @NotBlank(message = "Código boleto é obrigatório")
    @Column(name = "codigo_boleto", unique = true, nullable = false, length = CODIGO_LENGTH)
    private String codigoBoleto;

    @NotBlank(message = "Nosso número é obrigatório")
    @Column(name = "nosso_numero", nullable = false, length = NOSSO_NUMERO_LENGTH)
    private String nossoNumero;

    @NotBlank(message = "Carteira é obrigatória")
    @Column(name = "carteira", nullable = false, length = CARTEIRA_LENGTH)
    private String carteira;

    @Column(name = "convenio", length = CONVENIO_LENGTH)
    private String convenio;

    @NotNull(message = "Conta cedente é obrigatória")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_cedente_id", nullable = false)
    private Conta contaCedente;

    @NotBlank(message = "Nome do sacado é obrigatório")
    @Size(max = NAME_MAX_LENGTH, message = "Nome sacado deve ter no máximo 140 caracteres")
    @Column(name = "sacado_nome", nullable = false, length = NAME_MAX_LENGTH)
    private String sacadoNome;

    @Size(max = DOCUMENT_LENGTH, message = "Documento sacado deve ter no máximo 14 caracteres")
    @Column(name = "sacado_documento", length = DOCUMENT_LENGTH)
    private String sacadoDocumento;

    @Size(max = ADDRESS_LENGTH, message = "Endereço sacado deve ter no máximo 255 caracteres")
    @Column(name = "sacado_endereco", length = ADDRESS_LENGTH)
    private String sacadoEndereco;

    @Size(max = CITY_LENGTH, message = "Cidade sacado deve ter no máximo 100 caracteres")
    @Column(name = "sacado_cidade", length = CITY_LENGTH)
    private String sacadoCidade;

    @Size(max = UF_LENGTH, message = "UF sacado deve ter no máximo 2 caracteres")
    @Column(name = "sacado_uf", length = UF_LENGTH)
    private String sacadoUf;

    @Size(max = CEP_LENGTH, message = "CEP sacado deve ter no máximo 9 caracteres")
    @Column(name = "sacado_cep", length = CEP_LENGTH)
    private String sacadoCep;

    @NotNull(message = "Valor original é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor original deve ser maior que zero")
    @Column(name = "valor_original", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal valorOriginal;

    @Column(name = "valor_desconto", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal valorDesconto = BigDecimal.ZERO;

    @Column(name = "valor_juros", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal valorJuros = BigDecimal.ZERO;

    @Column(name = "valor_multa", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal valorMulta = BigDecimal.ZERO;

    @Column(name = "valor_pago", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE)
    private BigDecimal valorPago;

    @Column(name = "data_emissao", nullable = false)
    private LocalDateTime dataEmissao = LocalDateTime.now();

    @NotNull(message = "Data de vencimento é obrigatória")
    @Column(name = "data_vencimento", nullable = false)
    private LocalDateTime dataVencimento;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @Column(name = "data_baixa")
    private LocalDateTime dataBaixa;

    @Column(name = "data_protesto")
    private LocalDateTime dataProtesto;

    @Column(name = "data_limite_desconto")
    private LocalDateTime dataLimiteDesconto;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusBoleto status = StatusBoleto.REGISTRADO;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", nullable = false)
    private TipoDocumento tipoDocumento = TipoDocumento.BOLETO;

    @Column(name = "instrucoes", columnDefinition = "TEXT")
    private String instrucoes;

    @Size(max = BARRAS_LENGTH, message = "Código de barras deve ter no máximo 54 caracteres")
    @Column(name = "codigo_barras", length = BARRAS_LENGTH)
    private String codigoBarras;

    @Size(max = DIGITAVEL_LENGTH, message = "Linha digitável deve ter no máximo 54 caracteres")
    @Column(name = "linha_digitavel", length = DIGITAVEL_LENGTH)
    private String linhaDigitavel;

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
     * Status do boleto registrado.
     */
    public enum StatusBoleto {
        REGISTRADO("Registrado"),
        PAGO("Pago"),
        BAIXADO("Baixado"),
        PROTESTADO("Protestado"),
        DEVOLVIDO("Devolvido"),
        CANCELADO("Cancelado"),
        VENCIDO("Vencido"),
        EM_ANALISE("Em Análise");

        private final String descricao;

        StatusBoleto(final String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Tipo de documento bancário.
     */
    public enum TipoDocumento {
        BOLETO("Boleto de Cobrança"),
        CARTAO("Cartão de Crédito"),
        CONVENIO("Convênio");

        private final String descricao;

        TipoDocumento(final String desc) {
            this.descricao = desc;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoBoleto() {
        return this.codigoBoleto;
    }

    @java.lang.SuppressWarnings("all")
    public String getNossoNumero() {
        return this.nossoNumero;
    }

    @java.lang.SuppressWarnings("all")
    public String getCarteira() {
        return this.carteira;
    }

    @java.lang.SuppressWarnings("all")
    public String getConvenio() {
        return this.convenio;
    }

    @java.lang.SuppressWarnings("all")
    public Conta getContaCedente() {
        return this.contaCedente;
    }

    @java.lang.SuppressWarnings("all")
    public String getSacadoNome() {
        return this.sacadoNome;
    }

    @java.lang.SuppressWarnings("all")
    public String getSacadoDocumento() {
        return this.sacadoDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public String getSacadoEndereco() {
        return this.sacadoEndereco;
    }

    @java.lang.SuppressWarnings("all")
    public String getSacadoCidade() {
        return this.sacadoCidade;
    }

    @java.lang.SuppressWarnings("all")
    public String getSacadoUf() {
        return this.sacadoUf;
    }

    @java.lang.SuppressWarnings("all")
    public String getSacadoCep() {
        return this.sacadoCep;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorOriginal() {
        return this.valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorDesconto() {
        return this.valorDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorJuros() {
        return this.valorJuros;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorMulta() {
        return this.valorMulta;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorPago() {
        return this.valorPago;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataEmissao() {
        return this.dataEmissao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataPagamento() {
        return this.dataPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataBaixa() {
        return this.dataBaixa;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProtesto() {
        return this.dataProtesto;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataLimiteDesconto() {
        return this.dataLimiteDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public StatusBoleto getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public TipoDocumento getTipoDocumento() {
        return this.tipoDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public String getInstrucoes() {
        return this.instrucoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoBarras() {
        return this.codigoBarras;
    }

    @java.lang.SuppressWarnings("all")
    public String getLinhaDigitavel() {
        return this.linhaDigitavel;
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
    public void setCodigoBoleto(final String codigoBoleto) {
        this.codigoBoleto = codigoBoleto;
    }

    @java.lang.SuppressWarnings("all")
    public void setNossoNumero(final String nossoNumero) {
        this.nossoNumero = nossoNumero;
    }

    @java.lang.SuppressWarnings("all")
    public void setCarteira(final String carteira) {
        this.carteira = carteira;
    }

    @java.lang.SuppressWarnings("all")
    public void setConvenio(final String convenio) {
        this.convenio = convenio;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaCedente(final Conta contaCedente) {
        this.contaCedente = contaCedente;
    }

    @java.lang.SuppressWarnings("all")
    public void setSacadoNome(final String sacadoNome) {
        this.sacadoNome = sacadoNome;
    }

    @java.lang.SuppressWarnings("all")
    public void setSacadoDocumento(final String sacadoDocumento) {
        this.sacadoDocumento = sacadoDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public void setSacadoEndereco(final String sacadoEndereco) {
        this.sacadoEndereco = sacadoEndereco;
    }

    @java.lang.SuppressWarnings("all")
    public void setSacadoCidade(final String sacadoCidade) {
        this.sacadoCidade = sacadoCidade;
    }

    @java.lang.SuppressWarnings("all")
    public void setSacadoUf(final String sacadoUf) {
        this.sacadoUf = sacadoUf;
    }

    @java.lang.SuppressWarnings("all")
    public void setSacadoCep(final String sacadoCep) {
        this.sacadoCep = sacadoCep;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorOriginal(final BigDecimal valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorDesconto(final BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorJuros(final BigDecimal valorJuros) {
        this.valorJuros = valorJuros;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorMulta(final BigDecimal valorMulta) {
        this.valorMulta = valorMulta;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorPago(final BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEmissao(final LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataPagamento(final LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataBaixa(final LocalDateTime dataBaixa) {
        this.dataBaixa = dataBaixa;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataProtesto(final LocalDateTime dataProtesto) {
        this.dataProtesto = dataProtesto;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataLimiteDesconto(final LocalDateTime dataLimiteDesconto) {
        this.dataLimiteDesconto = dataLimiteDesconto;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusBoleto status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoDocumento(final TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    @java.lang.SuppressWarnings("all")
    public void setInstrucoes(final String instrucoes) {
        this.instrucoes = instrucoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoBarras(final String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    @java.lang.SuppressWarnings("all")
    public void setLinhaDigitavel(final String linhaDigitavel) {
        this.linhaDigitavel = linhaDigitavel;
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
