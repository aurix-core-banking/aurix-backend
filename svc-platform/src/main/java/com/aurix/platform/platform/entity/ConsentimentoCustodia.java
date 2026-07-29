package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "consentimentos_custodia", schema = "aurix")
public class ConsentimentoCustodia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tenant_id", nullable = false, length = 64)
    private String tenantId;
    @Column(name = "conta_id", nullable = false)
    private Long contaId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parceiro_id", nullable = false)
    private ParceiroCustodia parceiro;
    @ElementCollection
    @CollectionTable(name = "consentimento_custodia_escopos", schema = "aurix", joinColumns = @JoinColumn(name = "consentimento_id"))
    @Column(name = "escopo")
    @Enumerated(EnumType.STRING)
    private List<EscopoCustodia> escopos;
    @Column(name = "data_expiracao", nullable = false)
    private LocalDateTime dataExpiracao;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusConsentimento status;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;


    public enum EscopoCustodia {
        CONSULTAR_SALDO, MOVIMENTAR;
    }


    public enum StatusConsentimento {
        ATIVO, REVOGADO, EXPIRADO;
    }


    @java.lang.SuppressWarnings("all")
    public static class ConsentimentoCustodiaBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String tenantId;
        @java.lang.SuppressWarnings("all")
        private Long contaId;
        @java.lang.SuppressWarnings("all")
        private ParceiroCustodia parceiro;
        @java.lang.SuppressWarnings("all")
        private List<EscopoCustodia> escopos;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataExpiracao;
        @java.lang.SuppressWarnings("all")
        private StatusConsentimento status;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;

        @java.lang.SuppressWarnings("all")
        ConsentimentoCustodiaBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ConsentimentoCustodia.ConsentimentoCustodiaBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ConsentimentoCustodia.ConsentimentoCustodiaBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ConsentimentoCustodia.ConsentimentoCustodiaBuilder contaId(final Long contaId) {
            this.contaId = contaId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ConsentimentoCustodia.ConsentimentoCustodiaBuilder parceiro(final ParceiroCustodia parceiro) {
            this.parceiro = parceiro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ConsentimentoCustodia.ConsentimentoCustodiaBuilder escopos(final List<EscopoCustodia> escopos) {
            this.escopos = escopos;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ConsentimentoCustodia.ConsentimentoCustodiaBuilder dataExpiracao(final LocalDateTime dataExpiracao) {
            this.dataExpiracao = dataExpiracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ConsentimentoCustodia.ConsentimentoCustodiaBuilder status(final StatusConsentimento status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public ConsentimentoCustodia.ConsentimentoCustodiaBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public ConsentimentoCustodia build() {
            return new ConsentimentoCustodia(this.id, this.tenantId, this.contaId, this.parceiro, this.escopos, this.dataExpiracao, this.status, this.dataCriacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "ConsentimentoCustodia.ConsentimentoCustodiaBuilder(id=" + this.id + ", tenantId=" + this.tenantId + ", contaId=" + this.contaId + ", parceiro=" + this.parceiro + ", escopos=" + this.escopos + ", dataExpiracao=" + this.dataExpiracao + ", status=" + this.status + ", dataCriacao=" + this.dataCriacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static ConsentimentoCustodia.ConsentimentoCustodiaBuilder builder() {
        return new ConsentimentoCustodia.ConsentimentoCustodiaBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getTenantId() {
        return this.tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    @java.lang.SuppressWarnings("all")
    public ParceiroCustodia getParceiro() {
        return this.parceiro;
    }

    @java.lang.SuppressWarnings("all")
    public List<EscopoCustodia> getEscopos() {
        return this.escopos;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public StatusConsentimento getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setParceiro(final ParceiroCustodia parceiro) {
        this.parceiro = parceiro;
    }

    @java.lang.SuppressWarnings("all")
    public void setEscopos(final List<EscopoCustodia> escopos) {
        this.escopos = escopos;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusConsentimento status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public ConsentimentoCustodia() {
    }

    @java.lang.SuppressWarnings("all")
    public ConsentimentoCustodia(final Long id, final String tenantId, final Long contaId, final ParceiroCustodia parceiro, final List<EscopoCustodia> escopos, final LocalDateTime dataExpiracao, final StatusConsentimento status, final LocalDateTime dataCriacao) {
        this.id = id;
        this.tenantId = tenantId;
        this.contaId = contaId;
        this.parceiro = parceiro;
        this.escopos = escopos;
        this.dataExpiracao = dataExpiracao;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }
}
