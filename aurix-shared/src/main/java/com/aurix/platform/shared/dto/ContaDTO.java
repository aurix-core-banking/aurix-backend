package com.aurix.platform.shared.dto;

import com.aurix.platform.shared.entity.Conta;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para Conta do Aurix.
 */
public class ContaDTO {
    /**
     * Valor mínimo permitido para saldos e limites.
     */
    private static final String MIN_VALUE = "0.0";
    /**
     * ID da conta.
     */
    private Long id;
    /**
     * Número da conta formatado (ex: 12345-6).
     */
    @Pattern(regexp = "\\d{5}-\\d{1}", message = "Número da conta deve ter formato 12345-6")
    private String numeroConta;
    /**
     * ID do cliente titular.
     */
    @NotNull(message = "Cliente é obrigatório")
    private Long clienteId;
    /**
     * Nome do cliente titular.
     */
    private String clienteNome;
    /**
     * Tipo de pessoa (FISICA/JURIDICA).
     */
    private String clienteTipoPessoa;
    /**
     * Tipo da conta (CORRENTE, POUPANCA, etc).
     */
    @NotNull(message = "Tipo da conta é obrigatório")
    private Conta.TipoConta tipoConta;
    /**
     * Saldo atual da conta.
     */
    @DecimalMin(value = MIN_VALUE, message = "Saldo não pode ser negativo")
    private BigDecimal saldo;
    /**
     * Limite de crédito total.
     */
    @DecimalMin(value = MIN_VALUE, message = "Limite de crédito não pode ser negativo")
    private BigDecimal limiteCredito;
    /**
     * Valor do limite já utilizado.
     */
    @DecimalMin(value = MIN_VALUE, message = "Limite utilizado não pode ser negativo")
    private BigDecimal limiteUtilizado;
    /**
     * Valor do limite ainda disponível.
     */
    private BigDecimal limiteDisponivel;
    /**
     * Status atual da conta.
     */
    private Conta.StatusConta status;
    /**
     * Data de abertura da conta.
     */
    private LocalDateTime dataAbertura;
    /**
     * Data de encerramento da conta, se houver.
     */
    private LocalDateTime dataFechamento;
    /**
     * Dados adicionais em formato JSON.
     */
    private String dadosExtras;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID da conta.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Número da conta formatado (ex: 12345-6).
     */
    @java.lang.SuppressWarnings("all")
    public String getNumeroConta() {
        return this.numeroConta;
    }

    /**
     * ID do cliente titular.
     */
    @java.lang.SuppressWarnings("all")
    public Long getClienteId() {
        return this.clienteId;
    }

    /**
     * Nome do cliente titular.
     */
    @java.lang.SuppressWarnings("all")
    public String getClienteNome() {
        return this.clienteNome;
    }

    /**
     * Tipo de pessoa (FISICA/JURIDICA).
     */
    @java.lang.SuppressWarnings("all")
    public String getClienteTipoPessoa() {
        return this.clienteTipoPessoa;
    }

    /**
     * Tipo da conta (CORRENTE, POUPANCA, etc).
     */
    @java.lang.SuppressWarnings("all")
    public Conta.TipoConta getTipoConta() {
        return this.tipoConta;
    }

    /**
     * Saldo atual da conta.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldo() {
        return this.saldo;
    }

    /**
     * Limite de crédito total.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteCredito() {
        return this.limiteCredito;
    }

    /**
     * Valor do limite já utilizado.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteUtilizado() {
        return this.limiteUtilizado;
    }

    /**
     * Valor do limite ainda disponível.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteDisponivel() {
        return this.limiteDisponivel;
    }

    /**
     * Status atual da conta.
     */
    @java.lang.SuppressWarnings("all")
    public Conta.StatusConta getStatus() {
        return this.status;
    }

    /**
     * Data de abertura da conta.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAbertura() {
        return this.dataAbertura;
    }

    /**
     * Data de encerramento da conta, se houver.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFechamento() {
        return this.dataFechamento;
    }

    /**
     * Dados adicionais em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosExtras() {
        return this.dadosExtras;
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
     * ID da conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Número da conta formatado (ex: 12345-6).
     */
    @java.lang.SuppressWarnings("all")
    public void setNumeroConta(final String numeroConta) {
        this.numeroConta = numeroConta;
    }

    /**
     * ID do cliente titular.
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * Nome do cliente titular.
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteNome(final String clienteNome) {
        this.clienteNome = clienteNome;
    }

    /**
     * Tipo de pessoa (FISICA/JURIDICA).
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteTipoPessoa(final String clienteTipoPessoa) {
        this.clienteTipoPessoa = clienteTipoPessoa;
    }

    /**
     * Tipo da conta (CORRENTE, POUPANCA, etc).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoConta(final Conta.TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    /**
     * Saldo atual da conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setSaldo(final BigDecimal saldo) {
        this.saldo = saldo;
    }

    /**
     * Limite de crédito total.
     */
    @java.lang.SuppressWarnings("all")
    public void setLimiteCredito(final BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    /**
     * Valor do limite já utilizado.
     */
    @java.lang.SuppressWarnings("all")
    public void setLimiteUtilizado(final BigDecimal limiteUtilizado) {
        this.limiteUtilizado = limiteUtilizado;
    }

    /**
     * Valor do limite ainda disponível.
     */
    @java.lang.SuppressWarnings("all")
    public void setLimiteDisponivel(final BigDecimal limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    /**
     * Status atual da conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final Conta.StatusConta status) {
        this.status = status;
    }

    /**
     * Data de abertura da conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAbertura(final LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    /**
     * Data de encerramento da conta, se houver.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataFechamento(final LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    /**
     * Dados adicionais em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosExtras(final String dadosExtras) {
        this.dadosExtras = dadosExtras;
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
        if (!(o instanceof ContaDTO)) return false;
        final ContaDTO other = (ContaDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$numeroConta = this.getNumeroConta();
        final java.lang.Object other$numeroConta = other.getNumeroConta();
        if (this$numeroConta == null ? other$numeroConta != null : !this$numeroConta.equals(other$numeroConta)) return false;
        final java.lang.Object this$clienteNome = this.getClienteNome();
        final java.lang.Object other$clienteNome = other.getClienteNome();
        if (this$clienteNome == null ? other$clienteNome != null : !this$clienteNome.equals(other$clienteNome)) return false;
        final java.lang.Object this$clienteTipoPessoa = this.getClienteTipoPessoa();
        final java.lang.Object other$clienteTipoPessoa = other.getClienteTipoPessoa();
        if (this$clienteTipoPessoa == null ? other$clienteTipoPessoa != null : !this$clienteTipoPessoa.equals(other$clienteTipoPessoa)) return false;
        final java.lang.Object this$tipoConta = this.getTipoConta();
        final java.lang.Object other$tipoConta = other.getTipoConta();
        if (this$tipoConta == null ? other$tipoConta != null : !this$tipoConta.equals(other$tipoConta)) return false;
        final java.lang.Object this$saldo = this.getSaldo();
        final java.lang.Object other$saldo = other.getSaldo();
        if (this$saldo == null ? other$saldo != null : !this$saldo.equals(other$saldo)) return false;
        final java.lang.Object this$limiteCredito = this.getLimiteCredito();
        final java.lang.Object other$limiteCredito = other.getLimiteCredito();
        if (this$limiteCredito == null ? other$limiteCredito != null : !this$limiteCredito.equals(other$limiteCredito)) return false;
        final java.lang.Object this$limiteUtilizado = this.getLimiteUtilizado();
        final java.lang.Object other$limiteUtilizado = other.getLimiteUtilizado();
        if (this$limiteUtilizado == null ? other$limiteUtilizado != null : !this$limiteUtilizado.equals(other$limiteUtilizado)) return false;
        final java.lang.Object this$limiteDisponivel = this.getLimiteDisponivel();
        final java.lang.Object other$limiteDisponivel = other.getLimiteDisponivel();
        if (this$limiteDisponivel == null ? other$limiteDisponivel != null : !this$limiteDisponivel.equals(other$limiteDisponivel)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataAbertura = this.getDataAbertura();
        final java.lang.Object other$dataAbertura = other.getDataAbertura();
        if (this$dataAbertura == null ? other$dataAbertura != null : !this$dataAbertura.equals(other$dataAbertura)) return false;
        final java.lang.Object this$dataFechamento = this.getDataFechamento();
        final java.lang.Object other$dataFechamento = other.getDataFechamento();
        if (this$dataFechamento == null ? other$dataFechamento != null : !this$dataFechamento.equals(other$dataFechamento)) return false;
        final java.lang.Object this$dadosExtras = this.getDadosExtras();
        final java.lang.Object other$dadosExtras = other.getDadosExtras();
        if (this$dadosExtras == null ? other$dadosExtras != null : !this$dadosExtras.equals(other$dadosExtras)) return false;
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
        return other instanceof ContaDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $numeroConta = this.getNumeroConta();
        result = result * PRIME + ($numeroConta == null ? 43 : $numeroConta.hashCode());
        final java.lang.Object $clienteNome = this.getClienteNome();
        result = result * PRIME + ($clienteNome == null ? 43 : $clienteNome.hashCode());
        final java.lang.Object $clienteTipoPessoa = this.getClienteTipoPessoa();
        result = result * PRIME + ($clienteTipoPessoa == null ? 43 : $clienteTipoPessoa.hashCode());
        final java.lang.Object $tipoConta = this.getTipoConta();
        result = result * PRIME + ($tipoConta == null ? 43 : $tipoConta.hashCode());
        final java.lang.Object $saldo = this.getSaldo();
        result = result * PRIME + ($saldo == null ? 43 : $saldo.hashCode());
        final java.lang.Object $limiteCredito = this.getLimiteCredito();
        result = result * PRIME + ($limiteCredito == null ? 43 : $limiteCredito.hashCode());
        final java.lang.Object $limiteUtilizado = this.getLimiteUtilizado();
        result = result * PRIME + ($limiteUtilizado == null ? 43 : $limiteUtilizado.hashCode());
        final java.lang.Object $limiteDisponivel = this.getLimiteDisponivel();
        result = result * PRIME + ($limiteDisponivel == null ? 43 : $limiteDisponivel.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataAbertura = this.getDataAbertura();
        result = result * PRIME + ($dataAbertura == null ? 43 : $dataAbertura.hashCode());
        final java.lang.Object $dataFechamento = this.getDataFechamento();
        result = result * PRIME + ($dataFechamento == null ? 43 : $dataFechamento.hashCode());
        final java.lang.Object $dadosExtras = this.getDadosExtras();
        result = result * PRIME + ($dadosExtras == null ? 43 : $dadosExtras.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "ContaDTO(id=" + this.getId() + ", numeroConta=" + this.getNumeroConta() + ", clienteId=" + this.getClienteId() + ", clienteNome=" + this.getClienteNome() + ", clienteTipoPessoa=" + this.getClienteTipoPessoa() + ", tipoConta=" + this.getTipoConta() + ", saldo=" + this.getSaldo() + ", limiteCredito=" + this.getLimiteCredito() + ", limiteUtilizado=" + this.getLimiteUtilizado() + ", limiteDisponivel=" + this.getLimiteDisponivel() + ", status=" + this.getStatus() + ", dataAbertura=" + this.getDataAbertura() + ", dataFechamento=" + this.getDataFechamento() + ", dadosExtras=" + this.getDadosExtras() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public ContaDTO() {
    }

    /**
     * Creates a new {@code ContaDTO} instance.
     *
     * @param id ID da conta.
     * @param numeroConta Número da conta formatado (ex: 12345-6).
     * @param clienteId ID do cliente titular.
     * @param clienteNome Nome do cliente titular.
     * @param clienteTipoPessoa Tipo de pessoa (FISICA/JURIDICA).
     * @param tipoConta Tipo da conta (CORRENTE, POUPANCA, etc).
     * @param saldo Saldo atual da conta.
     * @param limiteCredito Limite de crédito total.
     * @param limiteUtilizado Valor do limite já utilizado.
     * @param limiteDisponivel Valor do limite ainda disponível.
     * @param status Status atual da conta.
     * @param dataAbertura Data de abertura da conta.
     * @param dataFechamento Data de encerramento da conta, se houver.
     * @param dadosExtras Dados adicionais em formato JSON.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public ContaDTO(final Long id, final String numeroConta, final Long clienteId, final String clienteNome, final String clienteTipoPessoa, final Conta.TipoConta tipoConta, final BigDecimal saldo, final BigDecimal limiteCredito, final BigDecimal limiteUtilizado, final BigDecimal limiteDisponivel, final Conta.StatusConta status, final LocalDateTime dataAbertura, final LocalDateTime dataFechamento, final String dadosExtras, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.clienteTipoPessoa = clienteTipoPessoa;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
        this.limiteUtilizado = limiteUtilizado;
        this.limiteDisponivel = limiteDisponivel;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.dadosExtras = dadosExtras;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
