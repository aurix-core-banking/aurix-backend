package com.aurix.platform.banking.settlement.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa uma liquidação
 * 
 * Gerencia liquidação automática de transações bancárias
 */
@Entity
@Table(name = "liquidez", schema = "aurix")
public class Liquidez {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero_liquidez", unique = true, nullable = false, length = 50)
    private String numeroLiquidez;
    @Column(name = "data_liquidez", nullable = false)
    private LocalDate dataLiquidez;
    @Column(name = "hora_liquidez", nullable = false)
    private LocalDateTime horaLiquidez;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacao", nullable = false)
    private TipoOperacao tipoOperacao;
    @Enumerated(EnumType.STRING)
    @Column(name = "canal", nullable = false)
    private Canal canal;
    @Column(name = "conta_origem", length = 50)
    private String contaOrigem;
    @Column(name = "conta_destino", length = 50)
    private String contaDestino;
    @Column(name = "banco_origem", length = 10)
    private String bancoOrigem;
    @Column(name = "banco_destino", length = 10)
    private String bancoDestino;
    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;
    @Column(name = "taxa_operacao", precision = 8, scale = 4)
    private BigDecimal taxaOperacao;
    @Column(name = "valor_taxa", precision = 15, scale = 2)
    private BigDecimal valorTaxa;
    @Column(name = "valor_liquido", precision = 15, scale = 2)
    private BigDecimal valorLiquido;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusLiquidez status;
    @Column(name = "protocolo_sistema", length = 100)
    private String protocoloSistema;
    @Column(name = "protocolo_bacen", length = 100)
    private String protocoloBacen;
    @Column(name = "codigo_retorno", length = 50)
    private String codigoRetorno;
    @Column(name = "mensagem_retorno", length = 500)
    private String mensagemRetorno;
    @Column(name = "data_processamento")
    private LocalDateTime dataProcessamento;
    @Column(name = "data_confirmacao")
    private LocalDateTime dataConfirmacao;
    @Column(name = "tentativas_processamento")
    private Integer tentativasProcessamento;
    @Column(name = "max_tentativas")
    private Integer maxTentativas;
    @Column(name = "proximo_retry")
    private LocalDateTime proximoRetry;
    @Column(name = "observacoes", length = 1000)
    private String observacoes;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    @Column(name = "versao", nullable = false)
    @Version
    private Long versao;


    /**
     * Tipo de operação
     */
    public enum TipoOperacao {
        PIX,  // PIX
        TED,  // TED
        DOC,  // DOC
        TRANSFERENCIA_INTERNA,  // Transferência interna
        DEPOSITO,  // Depósito
        SAQUE,  // Saque
        PAGAMENTO,  // Pagamento
        COBRANCA,  // Cobrança
        INVESTIMENTO,  // Investimento
        OUTROS // Outros
        ;
    }


    /**
     * Canal da operação
     */
    public enum Canal {
        INTERNET_BANKING,  // Internet Banking
        MOBILE_BANKING,  // Mobile Banking
        CAIXA_ELETRONICO,  // Caixa Eletrônico
        AGENCIA,  // Agência
        API,  // API
        WEBHOOK,  // Webhook
        SISTEMA_INTERNO,  // Sistema Interno
        BATCH,  // Processamento em lote
        OUTROS // Outros
        ;
    }


    /**
     * Status da liquidação
     */
    public enum StatusLiquidez {
        PENDENTE,  // Pendente
        PROCESSANDO,  // Processando
        LIQUIDADO,  // Liquidado
        CONFIRMADO,  // Confirmado
        REJEITADO,  // Rejeitado
        CANCELADO,  // Cancelado
        FALHA,  // Falha
        RETRY,  // Tentar novamente
        TIMEOUT,  // Timeout
        SUSPENSO // Suspenso
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class LiquidezBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String numeroLiquidez;
        @java.lang.SuppressWarnings("all")
        private LocalDate dataLiquidez;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime horaLiquidez;
        @java.lang.SuppressWarnings("all")
        private TipoOperacao tipoOperacao;
        @java.lang.SuppressWarnings("all")
        private Canal canal;
        @java.lang.SuppressWarnings("all")
        private String contaOrigem;
        @java.lang.SuppressWarnings("all")
        private String contaDestino;
        @java.lang.SuppressWarnings("all")
        private String bancoOrigem;
        @java.lang.SuppressWarnings("all")
        private String bancoDestino;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valor;
        @java.lang.SuppressWarnings("all")
        private BigDecimal taxaOperacao;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorTaxa;
        @java.lang.SuppressWarnings("all")
        private BigDecimal valorLiquido;
        @java.lang.SuppressWarnings("all")
        private StatusLiquidez status;
        @java.lang.SuppressWarnings("all")
        private String protocoloSistema;
        @java.lang.SuppressWarnings("all")
        private String protocoloBacen;
        @java.lang.SuppressWarnings("all")
        private String codigoRetorno;
        @java.lang.SuppressWarnings("all")
        private String mensagemRetorno;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataProcessamento;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataConfirmacao;
        @java.lang.SuppressWarnings("all")
        private Integer tentativasProcessamento;
        @java.lang.SuppressWarnings("all")
        private Integer maxTentativas;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime proximoRetry;
        @java.lang.SuppressWarnings("all")
        private String observacoes;
        @java.lang.SuppressWarnings("all")
        private String metadata;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versao;

        @java.lang.SuppressWarnings("all")
        LiquidezBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder numeroLiquidez(final String numeroLiquidez) {
            this.numeroLiquidez = numeroLiquidez;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder dataLiquidez(final LocalDate dataLiquidez) {
            this.dataLiquidez = dataLiquidez;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder horaLiquidez(final LocalDateTime horaLiquidez) {
            this.horaLiquidez = horaLiquidez;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder tipoOperacao(final TipoOperacao tipoOperacao) {
            this.tipoOperacao = tipoOperacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder canal(final Canal canal) {
            this.canal = canal;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder contaOrigem(final String contaOrigem) {
            this.contaOrigem = contaOrigem;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder contaDestino(final String contaDestino) {
            this.contaDestino = contaDestino;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder bancoOrigem(final String bancoOrigem) {
            this.bancoOrigem = bancoOrigem;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder bancoDestino(final String bancoDestino) {
            this.bancoDestino = bancoDestino;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder valor(final BigDecimal valor) {
            this.valor = valor;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder taxaOperacao(final BigDecimal taxaOperacao) {
            this.taxaOperacao = taxaOperacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder valorTaxa(final BigDecimal valorTaxa) {
            this.valorTaxa = valorTaxa;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder valorLiquido(final BigDecimal valorLiquido) {
            this.valorLiquido = valorLiquido;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder status(final StatusLiquidez status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder protocoloSistema(final String protocoloSistema) {
            this.protocoloSistema = protocoloSistema;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder protocoloBacen(final String protocoloBacen) {
            this.protocoloBacen = protocoloBacen;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder codigoRetorno(final String codigoRetorno) {
            this.codigoRetorno = codigoRetorno;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder mensagemRetorno(final String mensagemRetorno) {
            this.mensagemRetorno = mensagemRetorno;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder dataProcessamento(final LocalDateTime dataProcessamento) {
            this.dataProcessamento = dataProcessamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder dataConfirmacao(final LocalDateTime dataConfirmacao) {
            this.dataConfirmacao = dataConfirmacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder tentativasProcessamento(final Integer tentativasProcessamento) {
            this.tentativasProcessamento = tentativasProcessamento;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder maxTentativas(final Integer maxTentativas) {
            this.maxTentativas = maxTentativas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder proximoRetry(final LocalDateTime proximoRetry) {
            this.proximoRetry = proximoRetry;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public Liquidez.LiquidezBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public Liquidez build() {
            return new Liquidez(this.id, this.numeroLiquidez, this.dataLiquidez, this.horaLiquidez, this.tipoOperacao, this.canal, this.contaOrigem, this.contaDestino, this.bancoOrigem, this.bancoDestino, this.valor, this.taxaOperacao, this.valorTaxa, this.valorLiquido, this.status, this.protocoloSistema, this.protocoloBacen, this.codigoRetorno, this.mensagemRetorno, this.dataProcessamento, this.dataConfirmacao, this.tentativasProcessamento, this.maxTentativas, this.proximoRetry, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "Liquidez.LiquidezBuilder(id=" + this.id + ", numeroLiquidez=" + this.numeroLiquidez + ", dataLiquidez=" + this.dataLiquidez + ", horaLiquidez=" + this.horaLiquidez + ", tipoOperacao=" + this.tipoOperacao + ", canal=" + this.canal + ", contaOrigem=" + this.contaOrigem + ", contaDestino=" + this.contaDestino + ", bancoOrigem=" + this.bancoOrigem + ", bancoDestino=" + this.bancoDestino + ", valor=" + this.valor + ", taxaOperacao=" + this.taxaOperacao + ", valorTaxa=" + this.valorTaxa + ", valorLiquido=" + this.valorLiquido + ", status=" + this.status + ", protocoloSistema=" + this.protocoloSistema + ", protocoloBacen=" + this.protocoloBacen + ", codigoRetorno=" + this.codigoRetorno + ", mensagemRetorno=" + this.mensagemRetorno + ", dataProcessamento=" + this.dataProcessamento + ", dataConfirmacao=" + this.dataConfirmacao + ", tentativasProcessamento=" + this.tentativasProcessamento + ", maxTentativas=" + this.maxTentativas + ", proximoRetry=" + this.proximoRetry + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static Liquidez.LiquidezBuilder builder() {
        return new Liquidez.LiquidezBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getNumeroLiquidez() {
        return this.numeroLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDate getDataLiquidez() {
        return this.dataLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getHoraLiquidez() {
        return this.horaLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public TipoOperacao getTipoOperacao() {
        return this.tipoOperacao;
    }

    @java.lang.SuppressWarnings("all")
    public Canal getCanal() {
        return this.canal;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaOrigem() {
        return this.contaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public String getContaDestino() {
        return this.contaDestino;
    }

    @java.lang.SuppressWarnings("all")
    public String getBancoOrigem() {
        return this.bancoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public String getBancoDestino() {
        return this.bancoDestino;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValor() {
        return this.valor;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getTaxaOperacao() {
        return this.taxaOperacao;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorTaxa() {
        return this.valorTaxa;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getValorLiquido() {
        return this.valorLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public StatusLiquidez getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public String getProtocoloSistema() {
        return this.protocoloSistema;
    }

    @java.lang.SuppressWarnings("all")
    public String getProtocoloBacen() {
        return this.protocoloBacen;
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
    public LocalDateTime getDataProcessamento() {
        return this.dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataConfirmacao() {
        return this.dataConfirmacao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTentativasProcessamento() {
        return this.tentativasProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getMaxTentativas() {
        return this.maxTentativas;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getProximoRetry() {
        return this.proximoRetry;
    }

    @java.lang.SuppressWarnings("all")
    public String getObservacoes() {
        return this.observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetadata() {
        return this.metadata;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public Long getVersao() {
        return this.versao;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setNumeroLiquidez(final String numeroLiquidez) {
        this.numeroLiquidez = numeroLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataLiquidez(final LocalDate dataLiquidez) {
        this.dataLiquidez = dataLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public void setHoraLiquidez(final LocalDateTime horaLiquidez) {
        this.horaLiquidez = horaLiquidez;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoOperacao(final TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setCanal(final Canal canal) {
        this.canal = canal;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaOrigem(final String contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaDestino(final String contaDestino) {
        this.contaDestino = contaDestino;
    }

    @java.lang.SuppressWarnings("all")
    public void setBancoOrigem(final String bancoOrigem) {
        this.bancoOrigem = bancoOrigem;
    }

    @java.lang.SuppressWarnings("all")
    public void setBancoDestino(final String bancoDestino) {
        this.bancoDestino = bancoDestino;
    }

    @java.lang.SuppressWarnings("all")
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    @java.lang.SuppressWarnings("all")
    public void setTaxaOperacao(final BigDecimal taxaOperacao) {
        this.taxaOperacao = taxaOperacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorTaxa(final BigDecimal valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    @java.lang.SuppressWarnings("all")
    public void setValorLiquido(final BigDecimal valorLiquido) {
        this.valorLiquido = valorLiquido;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusLiquidez status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setProtocoloSistema(final String protocoloSistema) {
        this.protocoloSistema = protocoloSistema;
    }

    @java.lang.SuppressWarnings("all")
    public void setProtocoloBacen(final String protocoloBacen) {
        this.protocoloBacen = protocoloBacen;
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
    public void setDataProcessamento(final LocalDateTime dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataConfirmacao(final LocalDateTime dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTentativasProcessamento(final Integer tentativasProcessamento) {
        this.tentativasProcessamento = tentativasProcessamento;
    }

    @java.lang.SuppressWarnings("all")
    public void setMaxTentativas(final Integer maxTentativas) {
        this.maxTentativas = maxTentativas;
    }

    @java.lang.SuppressWarnings("all")
    public void setProximoRetry(final LocalDateTime proximoRetry) {
        this.proximoRetry = proximoRetry;
    }

    @java.lang.SuppressWarnings("all")
    public void setObservacoes(final String observacoes) {
        this.observacoes = observacoes;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetadata(final String metadata) {
        this.metadata = metadata;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setVersao(final Long versao) {
        this.versao = versao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Liquidez)) return false;
        final Liquidez other = (Liquidez) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$tentativasProcessamento = this.getTentativasProcessamento();
        final java.lang.Object other$tentativasProcessamento = other.getTentativasProcessamento();
        if (this$tentativasProcessamento == null ? other$tentativasProcessamento != null : !this$tentativasProcessamento.equals(other$tentativasProcessamento)) return false;
        final java.lang.Object this$maxTentativas = this.getMaxTentativas();
        final java.lang.Object other$maxTentativas = other.getMaxTentativas();
        if (this$maxTentativas == null ? other$maxTentativas != null : !this$maxTentativas.equals(other$maxTentativas)) return false;
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        final java.lang.Object this$numeroLiquidez = this.getNumeroLiquidez();
        final java.lang.Object other$numeroLiquidez = other.getNumeroLiquidez();
        if (this$numeroLiquidez == null ? other$numeroLiquidez != null : !this$numeroLiquidez.equals(other$numeroLiquidez)) return false;
        final java.lang.Object this$dataLiquidez = this.getDataLiquidez();
        final java.lang.Object other$dataLiquidez = other.getDataLiquidez();
        if (this$dataLiquidez == null ? other$dataLiquidez != null : !this$dataLiquidez.equals(other$dataLiquidez)) return false;
        final java.lang.Object this$horaLiquidez = this.getHoraLiquidez();
        final java.lang.Object other$horaLiquidez = other.getHoraLiquidez();
        if (this$horaLiquidez == null ? other$horaLiquidez != null : !this$horaLiquidez.equals(other$horaLiquidez)) return false;
        final java.lang.Object this$tipoOperacao = this.getTipoOperacao();
        final java.lang.Object other$tipoOperacao = other.getTipoOperacao();
        if (this$tipoOperacao == null ? other$tipoOperacao != null : !this$tipoOperacao.equals(other$tipoOperacao)) return false;
        final java.lang.Object this$canal = this.getCanal();
        final java.lang.Object other$canal = other.getCanal();
        if (this$canal == null ? other$canal != null : !this$canal.equals(other$canal)) return false;
        final java.lang.Object this$contaOrigem = this.getContaOrigem();
        final java.lang.Object other$contaOrigem = other.getContaOrigem();
        if (this$contaOrigem == null ? other$contaOrigem != null : !this$contaOrigem.equals(other$contaOrigem)) return false;
        final java.lang.Object this$contaDestino = this.getContaDestino();
        final java.lang.Object other$contaDestino = other.getContaDestino();
        if (this$contaDestino == null ? other$contaDestino != null : !this$contaDestino.equals(other$contaDestino)) return false;
        final java.lang.Object this$bancoOrigem = this.getBancoOrigem();
        final java.lang.Object other$bancoOrigem = other.getBancoOrigem();
        if (this$bancoOrigem == null ? other$bancoOrigem != null : !this$bancoOrigem.equals(other$bancoOrigem)) return false;
        final java.lang.Object this$bancoDestino = this.getBancoDestino();
        final java.lang.Object other$bancoDestino = other.getBancoDestino();
        if (this$bancoDestino == null ? other$bancoDestino != null : !this$bancoDestino.equals(other$bancoDestino)) return false;
        final java.lang.Object this$valor = this.getValor();
        final java.lang.Object other$valor = other.getValor();
        if (this$valor == null ? other$valor != null : !this$valor.equals(other$valor)) return false;
        final java.lang.Object this$taxaOperacao = this.getTaxaOperacao();
        final java.lang.Object other$taxaOperacao = other.getTaxaOperacao();
        if (this$taxaOperacao == null ? other$taxaOperacao != null : !this$taxaOperacao.equals(other$taxaOperacao)) return false;
        final java.lang.Object this$valorTaxa = this.getValorTaxa();
        final java.lang.Object other$valorTaxa = other.getValorTaxa();
        if (this$valorTaxa == null ? other$valorTaxa != null : !this$valorTaxa.equals(other$valorTaxa)) return false;
        final java.lang.Object this$valorLiquido = this.getValorLiquido();
        final java.lang.Object other$valorLiquido = other.getValorLiquido();
        if (this$valorLiquido == null ? other$valorLiquido != null : !this$valorLiquido.equals(other$valorLiquido)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$protocoloSistema = this.getProtocoloSistema();
        final java.lang.Object other$protocoloSistema = other.getProtocoloSistema();
        if (this$protocoloSistema == null ? other$protocoloSistema != null : !this$protocoloSistema.equals(other$protocoloSistema)) return false;
        final java.lang.Object this$protocoloBacen = this.getProtocoloBacen();
        final java.lang.Object other$protocoloBacen = other.getProtocoloBacen();
        if (this$protocoloBacen == null ? other$protocoloBacen != null : !this$protocoloBacen.equals(other$protocoloBacen)) return false;
        final java.lang.Object this$codigoRetorno = this.getCodigoRetorno();
        final java.lang.Object other$codigoRetorno = other.getCodigoRetorno();
        if (this$codigoRetorno == null ? other$codigoRetorno != null : !this$codigoRetorno.equals(other$codigoRetorno)) return false;
        final java.lang.Object this$mensagemRetorno = this.getMensagemRetorno();
        final java.lang.Object other$mensagemRetorno = other.getMensagemRetorno();
        if (this$mensagemRetorno == null ? other$mensagemRetorno != null : !this$mensagemRetorno.equals(other$mensagemRetorno)) return false;
        final java.lang.Object this$dataProcessamento = this.getDataProcessamento();
        final java.lang.Object other$dataProcessamento = other.getDataProcessamento();
        if (this$dataProcessamento == null ? other$dataProcessamento != null : !this$dataProcessamento.equals(other$dataProcessamento)) return false;
        final java.lang.Object this$dataConfirmacao = this.getDataConfirmacao();
        final java.lang.Object other$dataConfirmacao = other.getDataConfirmacao();
        if (this$dataConfirmacao == null ? other$dataConfirmacao != null : !this$dataConfirmacao.equals(other$dataConfirmacao)) return false;
        final java.lang.Object this$proximoRetry = this.getProximoRetry();
        final java.lang.Object other$proximoRetry = other.getProximoRetry();
        if (this$proximoRetry == null ? other$proximoRetry != null : !this$proximoRetry.equals(other$proximoRetry)) return false;
        final java.lang.Object this$observacoes = this.getObservacoes();
        final java.lang.Object other$observacoes = other.getObservacoes();
        if (this$observacoes == null ? other$observacoes != null : !this$observacoes.equals(other$observacoes)) return false;
        final java.lang.Object this$metadata = this.getMetadata();
        final java.lang.Object other$metadata = other.getMetadata();
        if (this$metadata == null ? other$metadata != null : !this$metadata.equals(other$metadata)) return false;
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
        return other instanceof Liquidez;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $tentativasProcessamento = this.getTentativasProcessamento();
        result = result * PRIME + ($tentativasProcessamento == null ? 43 : $tentativasProcessamento.hashCode());
        final java.lang.Object $maxTentativas = this.getMaxTentativas();
        result = result * PRIME + ($maxTentativas == null ? 43 : $maxTentativas.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        final java.lang.Object $numeroLiquidez = this.getNumeroLiquidez();
        result = result * PRIME + ($numeroLiquidez == null ? 43 : $numeroLiquidez.hashCode());
        final java.lang.Object $dataLiquidez = this.getDataLiquidez();
        result = result * PRIME + ($dataLiquidez == null ? 43 : $dataLiquidez.hashCode());
        final java.lang.Object $horaLiquidez = this.getHoraLiquidez();
        result = result * PRIME + ($horaLiquidez == null ? 43 : $horaLiquidez.hashCode());
        final java.lang.Object $tipoOperacao = this.getTipoOperacao();
        result = result * PRIME + ($tipoOperacao == null ? 43 : $tipoOperacao.hashCode());
        final java.lang.Object $canal = this.getCanal();
        result = result * PRIME + ($canal == null ? 43 : $canal.hashCode());
        final java.lang.Object $contaOrigem = this.getContaOrigem();
        result = result * PRIME + ($contaOrigem == null ? 43 : $contaOrigem.hashCode());
        final java.lang.Object $contaDestino = this.getContaDestino();
        result = result * PRIME + ($contaDestino == null ? 43 : $contaDestino.hashCode());
        final java.lang.Object $bancoOrigem = this.getBancoOrigem();
        result = result * PRIME + ($bancoOrigem == null ? 43 : $bancoOrigem.hashCode());
        final java.lang.Object $bancoDestino = this.getBancoDestino();
        result = result * PRIME + ($bancoDestino == null ? 43 : $bancoDestino.hashCode());
        final java.lang.Object $valor = this.getValor();
        result = result * PRIME + ($valor == null ? 43 : $valor.hashCode());
        final java.lang.Object $taxaOperacao = this.getTaxaOperacao();
        result = result * PRIME + ($taxaOperacao == null ? 43 : $taxaOperacao.hashCode());
        final java.lang.Object $valorTaxa = this.getValorTaxa();
        result = result * PRIME + ($valorTaxa == null ? 43 : $valorTaxa.hashCode());
        final java.lang.Object $valorLiquido = this.getValorLiquido();
        result = result * PRIME + ($valorLiquido == null ? 43 : $valorLiquido.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $protocoloSistema = this.getProtocoloSistema();
        result = result * PRIME + ($protocoloSistema == null ? 43 : $protocoloSistema.hashCode());
        final java.lang.Object $protocoloBacen = this.getProtocoloBacen();
        result = result * PRIME + ($protocoloBacen == null ? 43 : $protocoloBacen.hashCode());
        final java.lang.Object $codigoRetorno = this.getCodigoRetorno();
        result = result * PRIME + ($codigoRetorno == null ? 43 : $codigoRetorno.hashCode());
        final java.lang.Object $mensagemRetorno = this.getMensagemRetorno();
        result = result * PRIME + ($mensagemRetorno == null ? 43 : $mensagemRetorno.hashCode());
        final java.lang.Object $dataProcessamento = this.getDataProcessamento();
        result = result * PRIME + ($dataProcessamento == null ? 43 : $dataProcessamento.hashCode());
        final java.lang.Object $dataConfirmacao = this.getDataConfirmacao();
        result = result * PRIME + ($dataConfirmacao == null ? 43 : $dataConfirmacao.hashCode());
        final java.lang.Object $proximoRetry = this.getProximoRetry();
        result = result * PRIME + ($proximoRetry == null ? 43 : $proximoRetry.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $metadata = this.getMetadata();
        result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Liquidez(id=" + this.getId() + ", numeroLiquidez=" + this.getNumeroLiquidez() + ", dataLiquidez=" + this.getDataLiquidez() + ", horaLiquidez=" + this.getHoraLiquidez() + ", tipoOperacao=" + this.getTipoOperacao() + ", canal=" + this.getCanal() + ", contaOrigem=" + this.getContaOrigem() + ", contaDestino=" + this.getContaDestino() + ", bancoOrigem=" + this.getBancoOrigem() + ", bancoDestino=" + this.getBancoDestino() + ", valor=" + this.getValor() + ", taxaOperacao=" + this.getTaxaOperacao() + ", valorTaxa=" + this.getValorTaxa() + ", valorLiquido=" + this.getValorLiquido() + ", status=" + this.getStatus() + ", protocoloSistema=" + this.getProtocoloSistema() + ", protocoloBacen=" + this.getProtocoloBacen() + ", codigoRetorno=" + this.getCodigoRetorno() + ", mensagemRetorno=" + this.getMensagemRetorno() + ", dataProcessamento=" + this.getDataProcessamento() + ", dataConfirmacao=" + this.getDataConfirmacao() + ", tentativasProcessamento=" + this.getTentativasProcessamento() + ", maxTentativas=" + this.getMaxTentativas() + ", proximoRetry=" + this.getProximoRetry() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public Liquidez() {
    }

    @java.lang.SuppressWarnings("all")
    public Liquidez(final Long id, final String numeroLiquidez, final LocalDate dataLiquidez, final LocalDateTime horaLiquidez, final TipoOperacao tipoOperacao, final Canal canal, final String contaOrigem, final String contaDestino, final String bancoOrigem, final String bancoDestino, final BigDecimal valor, final BigDecimal taxaOperacao, final BigDecimal valorTaxa, final BigDecimal valorLiquido, final StatusLiquidez status, final String protocoloSistema, final String protocoloBacen, final String codigoRetorno, final String mensagemRetorno, final LocalDateTime dataProcessamento, final LocalDateTime dataConfirmacao, final Integer tentativasProcessamento, final Integer maxTentativas, final LocalDateTime proximoRetry, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.numeroLiquidez = numeroLiquidez;
        this.dataLiquidez = dataLiquidez;
        this.horaLiquidez = horaLiquidez;
        this.tipoOperacao = tipoOperacao;
        this.canal = canal;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.bancoOrigem = bancoOrigem;
        this.bancoDestino = bancoDestino;
        this.valor = valor;
        this.taxaOperacao = taxaOperacao;
        this.valorTaxa = valorTaxa;
        this.valorLiquido = valorLiquido;
        this.status = status;
        this.protocoloSistema = protocoloSistema;
        this.protocoloBacen = protocoloBacen;
        this.codigoRetorno = codigoRetorno;
        this.mensagemRetorno = mensagemRetorno;
        this.dataProcessamento = dataProcessamento;
        this.dataConfirmacao = dataConfirmacao;
        this.tentativasProcessamento = tentativasProcessamento;
        this.maxTentativas = maxTentativas;
        this.proximoRetry = proximoRetry;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
