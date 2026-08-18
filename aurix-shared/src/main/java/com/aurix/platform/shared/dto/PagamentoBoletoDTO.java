package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.PagamentoBoleto;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para Pagamento Boleto Registrado do Aurix.
 */
public class PagamentoBoletoDTO {
    private static final int MAX_NAME_SIZE = 140;
    private static final int MAX_DOCUMENT_SIZE = 14;
    private static final int MAX_ADDRESS_SIZE = 255;
    private static final int MAX_CITY_SIZE = 100;
    private static final int MAX_UF_SIZE = 2;
    private static final int MAX_CEP_SIZE = 9;
    private static final int MAX_CARTEIRA_SIZE = 10;
    private static final int MAX_CONVENIO_SIZE = 20;
    private static final int MAX_NOSSO_NUMERO_SIZE = 20;
    private static final String MIN_VALUE = "0.01";

    private Long id;

    private String codigoBoleto;

    private String nossoNumero;

    @NotBlank(message = "Carteira é obrigatória")
    @Size(max = MAX_CARTEIRA_SIZE)
    private String carteira;

    @Size(max = MAX_CONVENIO_SIZE)
    private String convenio;

    @NotNull(message = "Conta cedente é obrigatória")
    private Long contaCedenteId;

    private String contaCedenteNumero;

    @NotBlank(message = "Nome do sacado é obrigatório")
    @Size(max = MAX_NAME_SIZE)
    private String sacadoNome;

    @Size(max = MAX_DOCUMENT_SIZE)
    private String sacadoDocumento;

    @Size(max = MAX_ADDRESS_SIZE)
    private String sacadoEndereco;

    @Size(max = MAX_CITY_SIZE)
    private String sacadoCidade;

    @Size(max = MAX_UF_SIZE)
    private String sacadoUf;

    @Size(max = MAX_CEP_SIZE)
    private String sacadoCep;

    @NotNull(message = "Valor original é obrigatório")
    @DecimalMin(value = MIN_VALUE, message = "Valor original deve ser maior que zero")
    private BigDecimal valorOriginal;

    private BigDecimal valorDesconto;

    private BigDecimal valorJuros;

    private BigDecimal valorMulta;

    private BigDecimal valorPago;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDateTime dataVencimento;

    private LocalDateTime dataEmissao;

    private LocalDateTime dataPagamento;

    private LocalDateTime dataBaixa;

    private LocalDateTime dataProtesto;

    private LocalDateTime dataLimiteDesconto;

    private PagamentoBoleto.StatusBoleto status;

    private PagamentoBoleto.TipoDocumento tipoDocumento;

    private String instrucoes;

    private String codigoBarras;

    private String linhaDigitavel;

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
    public Long getContaCedenteId() {
        return this.contaCedenteId;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaCedenteNumero() {
        return this.contaCedenteNumero;
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
    public LocalDateTime getDataVencimento() {
        return this.dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataEmissao() {
        return this.dataEmissao;
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
    public PagamentoBoleto.StatusBoleto getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public PagamentoBoleto.TipoDocumento getTipoDocumento() {
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
    public void setContaCedenteId(final Long contaCedenteId) {
        this.contaCedenteId = contaCedenteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaCedenteNumero(final String contaCedenteNumero) {
        this.contaCedenteNumero = contaCedenteNumero;
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
    public void setDataVencimento(final LocalDateTime dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataEmissao(final LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
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
    public void setStatus(final PagamentoBoleto.StatusBoleto status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoDocumento(final PagamentoBoleto.TipoDocumento tipoDocumento) {
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

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
