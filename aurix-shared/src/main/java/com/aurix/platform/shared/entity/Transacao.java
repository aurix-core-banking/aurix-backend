package com.aurix.platform.shared.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Transação do Aurix.
 * Representa uma transação bancária.
 */
@Entity
@Table(name = "transacoes", schema = "aurix")
public class Transacao extends BaseEntity {
    /**
     * Comprimento do código de transação.
     */
    private static final int TRANSACTION_CODE_LENGTH = 100;
    /**
     * Comprimento padrão para descrições.
     */
    private static final int DESCRIPTION_MAX_LENGTH = 500;
    /**
     * Precisão decimal para valores monetários.
     */
    private static final int DECIMAL_PRECISION = 15;
    /**
     * Escala decimal para valores monetários.
     */
    private static final int DECIMAL_SCALE = 2;
    /**
     * Conta que cede os fundos (débito).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_origem_id")
    private Conta contaOrigem;
    /**
     * Conta que recebe os fundos (crédito).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_destino_id")
    private Conta contaDestino;
    /**
     * Tipo técnico da transação (TED, PIX, etc.).
     */
    @NotNull(message = "Tipo da transação é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao", nullable = false)
    private TipoTransacao tipoTransacao;
    /**
     * Valor monetário bruto da transação.
     */
    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    @Column(name = "valor", precision = DECIMAL_PRECISION, scale = DECIMAL_SCALE, nullable = false)
    private BigDecimal valor;
    /**
     * Descrição amigável para extrato.
     */
    @Size(max = DESCRIPTION_MAX_LENGTH, message = "Descrição deve ter no máximo 500 caracteres")
    @Column(name = "descricao", length = DESCRIPTION_MAX_LENGTH)
    private String descricao;
    /**
     * Estado atual no fluxo de clearing financeiro.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusTransacao status = StatusTransacao.PENDENTE;
    /**
     * Identificador único para conciliação bancária.
     */
    @Column(name = "codigo_transacao", unique = true, length = TRANSACTION_CODE_LENGTH)
    private String codigoTransacao;
    /**
     * Metadados específicos se a transação for PIX (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_pix", columnDefinition = "jsonb")
    private String dadosPix;
    /**
     * Metadados específicos se a transação for TED (JSON).
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "dados_ted", columnDefinition = "jsonb")
    private String dadosTed;
    /**
     * Data e hora da solicitação da transação.
     */
    @Column(name = "data_transacao", nullable = false)
    private LocalDateTime dataTransacao = LocalDateTime.now();
    /**
     * Data e hora da efetiva liquidação financeira.
     */
    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<TransactionLeg> legs = new ArrayList<>();

    /**
     * Enum para tipo de transação.
     */
    public enum TipoTransacao {
        /**
         * PIX.
         */
        PIX("PIX"), /**
         * TED.
         */
        TED("TED"), /**
         * DOC.
         */
        DOC("DOC"), /**
         * Saque.
         */
        SAQUE("Saque"), /**
         * Depósito.
         */
        DEPOSITO("Depósito"), /**
         * Transferência Interna.
         */
        TRANSFERENCIA_INTERNA("Transferência Interna"), /**
         * Pagamento de Boleto.
         */
        PAGAMENTO_BOLETO("Pagamento de Boleto"), /**
         * Pagamento de Cartão.
         */
        PAGAMENTO_CARTAO("Pagamento de Cartão");
        /**
         * Descrição do tipo.
         */
        private final String descricao;

        TipoTransacao(final String desc) {
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
     * Enum para status da transação.
     */
    public enum StatusTransacao {
        /**
         * Pendente.
         */
        PENDENTE("Pendente"), /**
         * Processada.
         */
        PROCESSADA("Processada"), /**
         * Cancelada.
         */
        CANCELADA("Cancelada"), /**
         * Falhada.
         */
        FALHADA("Falhada"), /**
         * Revertida.
         */
        REVERTIDA("Revertida");
        /**
         * Descrição do status.
         */
        private final String descricao;

        StatusTransacao(final String desc) {
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
     * Conta que cede os fundos (débito).
     */
    @java.lang.SuppressWarnings("all")
    public Conta getContaOrigem() {
        return this.contaOrigem;
    }

    /**
     * Conta que recebe os fundos (crédito).
     */
    @java.lang.SuppressWarnings("all")
    public Conta getContaDestino() {
        return this.contaDestino;
    }

    /**
     * Tipo técnico da transação (TED, PIX, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public TipoTransacao getTipoTransacao() {
        return this.tipoTransacao;
    }

    /**
     * Valor monetário bruto da transação.
     */
    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    /**
     * Descrição amigável para extrato.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Estado atual no fluxo de clearing financeiro.
     */
    @java.lang.SuppressWarnings("all")
    public StatusTransacao getStatus() {
        return this.status;
    }

    /**
     * Identificador único para conciliação bancária.
     */
    @java.lang.SuppressWarnings("all")
    public String getCodigoTransacao() {
        return this.codigoTransacao;
    }

    /**
     * Metadados específicos se a transação for PIX (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosPix() {
        return this.dadosPix;
    }

    /**
     * Metadados específicos se a transação for TED (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public String getDadosTed() {
        return this.dadosTed;
    }

    /**
     * Data e hora da solicitação da transação.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataTransacao() {
        return this.dataTransacao;
    }

    /**
     * Data e hora da efetiva liquidação financeira.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    public List<TransactionLeg> getLegs() {
        return this.legs;
    }

    /**
     * Conta que cede os fundos (débito).
     */
    @java.lang.SuppressWarnings("all")
    public void setContaOrigem(final Conta contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    /**
     * Conta que recebe os fundos (crédito).
     */
    @java.lang.SuppressWarnings("all")
    public void setContaDestino(final Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    /**
     * Tipo técnico da transação (TED, PIX, etc.).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoTransacao(final TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    /**
     * Valor monetário bruto da transação.
     */
    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * Descrição amigável para extrato.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Estado atual no fluxo de clearing financeiro.
     */
    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusTransacao status) {
        this.status = status;
    }

    /**
     * Identificador único para conciliação bancária.
     */
    @java.lang.SuppressWarnings("all")
    public void setCodigoTransacao(final String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    /**
     * Metadados específicos se a transação for PIX (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosPix(final String dadosPix) {
        this.dadosPix = dadosPix;
    }

    /**
     * Metadados específicos se a transação for TED (JSON).
     */
    @java.lang.SuppressWarnings("all")
    public void setDadosTed(final String dadosTed) {
        this.dadosTed = dadosTed;
    }

    /**
     * Data e hora da solicitação da transação.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataTransacao(final LocalDateTime dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    /**
     * Data e hora da efetiva liquidação financeira.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public void setLegs(final List<TransactionLeg> legs) {
        this.legs = legs;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Transacao(contaOrigem=" + this.getContaOrigem() + ", contaDestino=" + this.getContaDestino() + ", tipoTransacao=" + this.getTipoTransacao() + ", valor=" + this.getValor() + ", descricao=" + this.getDescricao() + ", status=" + this.getStatus() + ", codigoTransacao=" + this.getCodigoTransacao() + ", dadosPix=" + this.getDadosPix() + ", dadosTed=" + this.getDadosTed() + ", dataTransacao=" + this.getDataTransacao() + ", dataProcessamento=" + this.getDataProcessamento() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Transacao)) return false;
        final Transacao other = (Transacao) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$contaOrigem = this.getContaOrigem();
        final java.lang.Object other$contaOrigem = other.getContaOrigem();
        if (this$contaOrigem == null ? other$contaOrigem != null : !this$contaOrigem.equals(other$contaOrigem)) return false;
        final java.lang.Object this$contaDestino = this.getContaDestino();
        final java.lang.Object other$contaDestino = other.getContaDestino();
        if (this$contaDestino == null ? other$contaDestino != null : !this$contaDestino.equals(other$contaDestino)) return false;
        final java.lang.Object this$tipoTransacao = this.getTipoTransacao();
        final java.lang.Object other$tipoTransacao = other.getTipoTransacao();
        if (this$tipoTransacao == null ? other$tipoTransacao != null : !this$tipoTransacao.equals(other$tipoTransacao)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$codigoTransacao = this.getCodigoTransacao();
        final java.lang.Object other$codigoTransacao = other.getCodigoTransacao();
        if (this$codigoTransacao == null ? other$codigoTransacao != null : !this$codigoTransacao.equals(other$codigoTransacao)) return false;
        final java.lang.Object this$dadosPix = this.getDadosPix();
        final java.lang.Object other$dadosPix = other.getDadosPix();
        if (this$dadosPix == null ? other$dadosPix != null : !this$dadosPix.equals(other$dadosPix)) return false;
        final java.lang.Object this$dadosTed = this.getDadosTed();
        final java.lang.Object other$dadosTed = other.getDadosTed();
        if (this$dadosTed == null ? other$dadosTed != null : !this$dadosTed.equals(other$dadosTed)) return false;
        final java.lang.Object this$dataTransacao = this.getDataTransacao();
        final java.lang.Object other$dataTransacao = other.getDataTransacao();
        if (this$dataTransacao == null ? other$dataTransacao != null : !this$dataTransacao.equals(other$dataTransacao)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Transacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $contaOrigem = this.getContaOrigem();
        result = result * PRIME + ($contaOrigem == null ? 43 : $contaOrigem.hashCode());
        final java.lang.Object $contaDestino = this.getContaDestino();
        result = result * PRIME + ($contaDestino == null ? 43 : $contaDestino.hashCode());
        final java.lang.Object $tipoTransacao = this.getTipoTransacao();
        result = result * PRIME + ($tipoTransacao == null ? 43 : $tipoTransacao.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $codigoTransacao = this.getCodigoTransacao();
        result = result * PRIME + ($codigoTransacao == null ? 43 : $codigoTransacao.hashCode());
        final java.lang.Object $dadosPix = this.getDadosPix();
        result = result * PRIME + ($dadosPix == null ? 43 : $dadosPix.hashCode());
        final java.lang.Object $dadosTed = this.getDadosTed();
        result = result * PRIME + ($dadosTed == null ? 43 : $dadosTed.hashCode());
        final java.lang.Object $dataTransacao = this.getDataTransacao();
        result = result * PRIME + ($dataTransacao == null ? 43 : $dataTransacao.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Transacao() {
    }

    /**
     * Creates a new {@code Transacao} instance.
     *
     * @param contaOrigem Conta que cede os fundos (débito).
     * @param contaDestino Conta que recebe os fundos (crédito).
     * @param tipoTransacao Tipo técnico da transação (TED, PIX, etc.).
     * @param valor Valor monetário bruto da transação.
     * @param descricao Descrição amigável para extrato.
     * @param status Estado atual no fluxo de clearing financeiro.
     * @param codigoTransacao Identificador único para conciliação bancária.
     * @param dadosPix Metadados específicos se a transação for PIX (JSON).
     * @param dadosTed Metadados específicos se a transação for TED (JSON).
     * @param dataTransacao Data e hora da solicitação da transação.
     * @param dataProcessamento Data e hora da efetiva liquidação financeira.
     * @param legs Lista de pernas da transação (n:n).
     */
    @java.lang.SuppressWarnings("all")
    public Transacao(final Conta contaOrigem, final Conta contaDestino, final TipoTransacao tipoTransacao, final BigDecimal valor, final String descricao, final StatusTransacao status, final String codigoTransacao, final String dadosPix, final String dadosTed, final LocalDateTime dataTransacao, final LocalDateTime dataProcessamento, final List<TransactionLeg> legs) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.tipoTransacao = tipoTransacao;
        this.valor = valor;
        this.descricao = descricao;
        this.status = status;
        this.codigoTransacao = codigoTransacao;
        this.dadosPix = dadosPix;
        this.dadosTed = dadosTed;
        this.dataTransacao = dataTransacao;
        this.dataProcessamento = dataProcessamento;
        this.legs = legs;
    }
}
