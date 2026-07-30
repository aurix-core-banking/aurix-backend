package com.aurix.platform.finance.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "perfis_financeiros_clientes", schema = "aurix")
public class PerfilFinanceiroCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id", nullable = false, unique = true)
    private Long clienteId;

    @Column(name = "codigo_cliente", unique = true, length = 20)
    private String codigoCliente;

    @Column(name = "limite_credito", precision = 15, scale = 2)
    private BigDecimal limiteCredito;

    @Column(name = "score_credito")
    private Integer scoreCredito;

    @Column(name = "observacoes", length = 1000)
    private String observacoes;

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;

    @Version
    @Column(name = "versao", nullable = false)
    private Long versao;

    @PrePersist
    protected void onCreate() {
        dataCriacao = LocalDateTime.now();
        dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    @java.lang.SuppressWarnings("all")
    public static class PerfilFinanceiroClienteBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private Long clienteId;
        @java.lang.SuppressWarnings("all")
        private String codigoCliente;
        @java.lang.SuppressWarnings("all")
        private BigDecimal limiteCredito;
        @java.lang.SuppressWarnings("all")
        private Integer scoreCredito;
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
        PerfilFinanceiroClienteBuilder() {
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder clienteId(final Long clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder codigoCliente(final String codigoCliente) {
            this.codigoCliente = codigoCliente;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder limiteCredito(final BigDecimal limiteCredito) {
            this.limiteCredito = limiteCredito;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder scoreCredito(final Integer scoreCredito) {
            this.scoreCredito = scoreCredito;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder observacoes(final String observacoes) {
            this.observacoes = observacoes;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder metadata(final String metadata) {
            this.metadata = metadata;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder versao(final Long versao) {
            this.versao = versao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public PerfilFinanceiroCliente build() {
            return new PerfilFinanceiroCliente(this.id, this.clienteId, this.codigoCliente, this.limiteCredito, this.scoreCredito, this.observacoes, this.metadata, this.dataCriacao, this.dataAtualizacao, this.versao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder(id=" + this.id + ", clienteId=" + this.clienteId + ", codigoCliente=" + this.codigoCliente + ", limiteCredito=" + this.limiteCredito + ", scoreCredito=" + this.scoreCredito + ", observacoes=" + this.observacoes + ", metadata=" + this.metadata + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versao=" + this.versao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder builder() {
        return new PerfilFinanceiroCliente.PerfilFinanceiroClienteBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public Long getClienteId() {
        return this.clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public String getCodigoCliente() {
        return this.codigoCliente;
    }

    @java.lang.SuppressWarnings("all")
    public BigDecimal getLimiteCredito() {
        return this.limiteCredito;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getScoreCredito() {
        return this.scoreCredito;
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
    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setCodigoCliente(final String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    @java.lang.SuppressWarnings("all")
    public void setLimiteCredito(final BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    @java.lang.SuppressWarnings("all")
    public void setScoreCredito(final Integer scoreCredito) {
        this.scoreCredito = scoreCredito;
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
        if (!(o instanceof PerfilFinanceiroCliente)) return false;
        final PerfilFinanceiroCliente other = (PerfilFinanceiroCliente) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$codigoCliente = this.getCodigoCliente();
        final java.lang.Object other$codigoCliente = other.getCodigoCliente();
        if (this$codigoCliente == null ? other$codigoCliente != null : !this$codigoCliente.equals(other$codigoCliente)) return false;
        final java.lang.Object this$limiteCredito = this.getLimiteCredito();
        final java.lang.Object other$limiteCredito = other.getLimiteCredito();
        if (this$limiteCredito == null ? other$limiteCredito != null : !this$limiteCredito.equals(other$limiteCredito)) return false;
        final java.lang.Object this$scoreCredito = this.getScoreCredito();
        final java.lang.Object other$scoreCredito = other.getScoreCredito();
        if (this$scoreCredito == null ? other$scoreCredito != null : !this$scoreCredito.equals(other$scoreCredito)) return false;
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
        final java.lang.Object this$versao = this.getVersao();
        final java.lang.Object other$versao = other.getVersao();
        if (this$versao == null ? other$versao != null : !this$versao.equals(other$versao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PerfilFinanceiroCliente;
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
        final java.lang.Object $codigoCliente = this.getCodigoCliente();
        result = result * PRIME + ($codigoCliente == null ? 43 : $codigoCliente.hashCode());
        final java.lang.Object $limiteCredito = this.getLimiteCredito();
        result = result * PRIME + ($limiteCredito == null ? 43 : $limiteCredito.hashCode());
        final java.lang.Object $scoreCredito = this.getScoreCredito();
        result = result * PRIME + ($scoreCredito == null ? 43 : $scoreCredito.hashCode());
        final java.lang.Object $observacoes = this.getObservacoes();
        result = result * PRIME + ($observacoes == null ? 43 : $observacoes.hashCode());
        final java.lang.Object $metadata = this.getMetadata();
        result = result * PRIME + ($metadata == null ? 43 : $metadata.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        final java.lang.Object $versao = this.getVersao();
        result = result * PRIME + ($versao == null ? 43 : $versao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PerfilFinanceiroCliente(id=" + this.getId() + ", clienteId=" + this.getClienteId() + ", codigoCliente=" + this.getCodigoCliente() + ", limiteCredito=" + this.getLimiteCredito() + ", scoreCredito=" + this.getScoreCredito() + ", observacoes=" + this.getObservacoes() + ", metadata=" + this.getMetadata() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versao=" + this.getVersao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public PerfilFinanceiroCliente() {
    }

    @java.lang.SuppressWarnings("all")
    public PerfilFinanceiroCliente(final Long id, final Long clienteId, final String codigoCliente, final BigDecimal limiteCredito, final Integer scoreCredito, final String observacoes, final String metadata, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versao) {
        this.id = id;
        this.clienteId = clienteId;
        this.codigoCliente = codigoCliente;
        this.limiteCredito = limiteCredito;
        this.scoreCredito = scoreCredito;
        this.observacoes = observacoes;
        this.metadata = metadata;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versao = versao;
    }
}
