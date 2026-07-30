package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade Conta do Aurix.
 * Representa uma conta bancária.
 */
@Entity
@Table(name = "contas", schema = "aurix", uniqueConstraints = @UniqueConstraint(columnNames = {"tenantId", "numeroConta"}))
public class Conta extends BaseEntity {
    /**
     * Comprimento do número da conta.
     */
    private static final int ACCOUNT_NUMBER_LENGTH = 20;
    /**
     * Precisão decimal para valores monetários.
     */
    private static final int DECIMAL_PRECISION = 15;
    /**
     * Escala decimal para valores monetários.
     */
    private static final int DECIMAL_SCALE = 2;
    /**
     * Número único da conta no formato 12345-6.
     */
    @NotBlank(message = "Número da conta é obrigatório")
    @Pattern(regexp = "\\d{5}-\\d{1}", message = "Número da conta deve ter formato 12345-6")
    @Column(name = "numero_conta", nullable = false, length = ACCOUNT_NUMBER_LENGTH)
    private String numeroConta;
    /**
     * Cliente titular da conta.
     */
    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    /**
     * Tipo técnico da conta (CORRENTE, POUPANCA, SALARIO).
     */
    @NotNull(message = "Tipo da conta é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta", nullable = false)
    private TipoConta tipoConta;
    /**
     * Saldo financeiro disponível (não inclui limite).
     */
    @DecimalMin(value = "0.0", message = "Saldo não pode ser negativo")
    @Column(name = "saldo", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal saldo = BigDecimal.ZERO;
    /**
     * Limite de crédito total concedido à conta.
     */
    @DecimalMin(value = "0.0", message = "Limite de crédito não pode ser negativo")
    @Column(name = "limite_credito", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal limiteCredito = BigDecimal.ZERO;
    /**
     * Parcela do limite de crédito que já foi utilizada.
     */
    @DecimalMin(value = "0.0", message = "Limite utilizado não pode ser negativo")
    @Column(name = "limite_utilizado", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal limiteUtilizado = BigDecimal.ZERO;
    /**
     * Status atual da conta (ATIVA, BLOQUEADA, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusConta status = StatusConta.ATIVA;
    /**
     * Data e hora de abertura formal da conta.
     */
    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura = LocalDateTime.now();
    /**
     * Data e hora de encerramento da conta, se aplicável.
     */
    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;
    /**
     * Metadados adicionais da conta em formato JSON.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_extras", columnDefinition = "jsonb")
    private String dadosExtras;

    /**
     * Retorna o saldo atual.
     *
     * @return BigDecimal saldo.
     */
    public BigDecimal getSaldoAtual() {
        return saldo;
    }

    /**
     * Define o saldo atual.
     *
     * @param novoSaldo novo saldo.
     */
    public void setSaldoAtual(final BigDecimal novoSaldo) {
        this.saldo = novoSaldo;
    }

    /**
     * Retorna o limite disponível.
     *
     * @return BigDecimal limite.
     */
    public BigDecimal getLimiteDisponivel() {
        return limiteCredito.subtract(limiteUtilizado);
    }

    /**
     * Verifica se a conta tem saldo suficiente.
     * 
     * @param valor valor a verificar.
     * @return true se tiver saldo, false caso contrário.
     */
    public boolean temSaldoSuficiente(final BigDecimal valor) {
        return saldo.compareTo(valor) >= 0;
    }

    /**
     * Verifica se a conta tem limite suficiente.
     * 
     * @param valor valor a verificar.
     * @return true se houver limite, false caso contrário.
     */
    public boolean temLimiteSuficiente(final BigDecimal valor) {
        return getLimiteDisponivel().compareTo(valor) >= 0;
    }


    /**
     * Enum para tipo de conta.
     */
    public enum TipoConta {
        /**
         * Conta Corrente.
         */
        CORRENTE("Conta Corrente"), /**
         * Conta Poupança.
         */
        POUPANCA("Conta Poupança"), /**
         * Conta Salário.
         */
         SALARIO("Conta Salário"), /**
         * Conta Empresarial.
         */
        EMPRESARIAL("Conta Empresarial");
        /**
         * Descrição do tipo.
         */
        private final String descricao;

        TipoConta(final String desc) {
            this.descricao = desc;
        }

        /**
         * Retorna a descrição do tipo.
         * 
         * @return a descrição.
         */
        public String getDescricao() {
            return descricao;
        }
    }


    /**
     * Enum para status da conta.
     */
    public enum StatusConta {
        /**
         * Ativa.
         */
        ATIVA("Ativa"), /**
         * Inativa.
         */
        INATIVA("Inativa"), /**
         * Bloqueada.
         */
        BLOQUEADA("Bloqueada"), /**
         * Suspensa.
         */
        SUSPENSA("Suspensa"), /**
         * Fechada.
         */
        FECHADA("Fechada");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusConta(final String desc) {
            this.descricao = desc;
        }

        /**
         * Retorna a descrição do status.
         * 
         * @return a descrição.
         */
        public String getDescricao() {
            return descricao;
        }
    }

    /**
     * Número único da conta no formato 12345-6.
     */
    @java.lang.SuppressWarnings("all")
    public String getNumeroConta() {
        return this.numeroConta;
    }

    /**
     * Cliente titular da conta.
     */
    @java.lang.SuppressWarnings("all")
    public Cliente getCliente() {
        return this.cliente;
    }

    /**
     * Tipo técnico da conta (CORRENTE, POUPANCA, SALARIO).
     */
    @java.lang.SuppressWarnings("all")
    public TipoConta getTipoConta() {
        return this.tipoConta;
    }

    /**
     * Saldo financeiro disponível (não inclui limite).
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getSaldo() {
        return this.saldo;
    }

    /**
     * Limite de crédito total concedido à conta.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteCredito() {
        return this.limiteCredito;
    }

    /**
     * Parcela do limite de crédito que já foi utilizada.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteUtilizado() {
        return this.limiteUtilizado;
    }

    /**
     * Status atual da conta (ATIVA, BLOQUEADA, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public StatusConta getStatus() {
        return this.status;
    }

    /**
     * Data e hora de abertura formal da conta.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAbertura() {
        return this.dataAbertura;
    }

    /**
     * Data e hora de encerramento da conta, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataFechamento() {
        return this.dataFechamento;
    }

    /**
     * Metadados adicionais da conta em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosExtras() {
        return this.dadosExtras;
    }

    /**
     * Número único da conta no formato 12345-6.
     */
    @java.lang.SuppressWarnings("all")
    public void setNumeroConta(final String numeroConta) {
        this.numeroConta = numeroConta;
    }

    /**
     * Cliente titular da conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Tipo técnico da conta (CORRENTE, POUPANCA, SALARIO).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoConta(final TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    /**
     * Saldo financeiro disponível (não inclui limite).
     */
    @java.lang.SuppressWarnings("all")
    public void setSaldo(final BigDecimal saldo) {
        this.saldo = saldo;
    }

    /**
     * Limite de crédito total concedido à conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setLimiteCredito(final BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    /**
     * Parcela do limite de crédito que já foi utilizada.
     */
    @java.lang.SuppressWarnings("all")
    public void setLimiteUtilizado(final BigDecimal limiteUtilizado) {
        this.limiteUtilizado = limiteUtilizado;
    }

    /**
     * Status atual da conta (ATIVA, BLOQUEADA, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusConta status) {
        this.status = status;
    }

    /**
     * Data e hora de abertura formal da conta.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAbertura(final LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    /**
     * Data e hora de encerramento da conta, se aplicável.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataFechamento(final LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    /**
     * Metadados adicionais da conta em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosExtras(final String dadosExtras) {
        this.dadosExtras = dadosExtras;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Conta(numeroConta=" + this.getNumeroConta() + ", cliente=" + this.getCliente() + ", tipoConta=" + this.getTipoConta() + ", saldo=" + this.getSaldo() + ", limiteCredito=" + this.getLimiteCredito() + ", limiteUtilizado=" + this.getLimiteUtilizado() + ", status=" + this.getStatus() + ", dataAbertura=" + this.getDataAbertura() + ", dataFechamento=" + this.getDataFechamento() + ", dadosExtras=" + this.getDadosExtras() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Conta)) return false;
        final Conta other = (Conta) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$numeroConta = this.getNumeroConta();
        final java.lang.Object other$numeroConta = other.getNumeroConta();
        if (this$numeroConta == null ? other$numeroConta != null : !this$numeroConta.equals(other$numeroConta)) return false;
        final java.lang.Object this$cliente = this.getCliente();
        final java.lang.Object other$cliente = other.getCliente();
        if (this$cliente == null ? other$cliente != null : !this$cliente.equals(other$cliente)) return false;
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
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Conta;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $numeroConta = this.getNumeroConta();
        result = result * PRIME + ($numeroConta == null ? 43 : $numeroConta.hashCode());
        final java.lang.Object $cliente = this.getCliente();
        result = result * PRIME + ($cliente == null ? 43 : $cliente.hashCode());
        final java.lang.Object $tipoConta = this.getTipoConta();
        result = result * PRIME + ($tipoConta == null ? 43 : $tipoConta.hashCode());
        final java.lang.Object $saldo = this.getSaldo();
        result = result * PRIME + ($saldo == null ? 43 : $saldo.hashCode());
        final java.lang.Object $limiteCredito = this.getLimiteCredito();
        result = result * PRIME + ($limiteCredito == null ? 43 : $limiteCredito.hashCode());
        final java.lang.Object $limiteUtilizado = this.getLimiteUtilizado();
        result = result * PRIME + ($limiteUtilizado == null ? 43 : $limiteUtilizado.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataAbertura = this.getDataAbertura();
        result = result * PRIME + ($dataAbertura == null ? 43 : $dataAbertura.hashCode());
        final java.lang.Object $dataFechamento = this.getDataFechamento();
        result = result * PRIME + ($dataFechamento == null ? 43 : $dataFechamento.hashCode());
        final java.lang.Object $dadosExtras = this.getDadosExtras();
        result = result * PRIME + ($dadosExtras == null ? 43 : $dadosExtras.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Conta() {
    }

    /**
     * Creates a new {@code Conta} instance.
     *
     * @param numeroConta Número único da conta no formato 12345-6.
     * @param cliente Cliente titular da conta.
     * @param tipoConta Tipo técnico da conta (CORRENTE, POUPANCA, SALARIO).
     * @param saldo Saldo financeiro disponível (não inclui limite).
     * @param limiteCredito Limite de crédito total concedido à conta.
     * @param limiteUtilizado Parcela do limite de crédito que já foi utilizada.
     * @param status Status atual da conta (ATIVA, BLOQUEADA, etc.).
     * @param dataAbertura Data e hora de abertura formal da conta.
     * @param dataFechamento Data e hora de encerramento da conta, se aplicável.
     * @param dadosExtras Metadados adicionais da conta em formato JSON.
     */
    @java.lang.SuppressWarnings("all")
    public Conta(final String numeroConta, final Cliente cliente, final TipoConta tipoConta, final BigDecimal saldo, final BigDecimal limiteCredito, final BigDecimal limiteUtilizado, final StatusConta status, final LocalDateTime dataAbertura, final LocalDateTime dataFechamento, final String dadosExtras) {
        this.numeroConta = numeroConta;
        this.cliente = cliente;
        this.tipoConta = tipoConta;
        this.saldo = saldo;
        this.limiteCredito = limiteCredito;
        this.limiteUtilizado = limiteUtilizado;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.dadosExtras = dadosExtras;
    }
}
