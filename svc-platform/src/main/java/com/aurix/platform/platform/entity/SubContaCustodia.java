package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "subcontas_custodia", schema = "aurix", uniqueConstraints = {@UniqueConstraint(columnNames = {"tenant_id", "parceiro_id", "identificador_externo"})})
public class SubContaCustodia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tenant_id", nullable = false, length = 64)
    private String tenantId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parceiro_id", nullable = false)
    private ParceiroCustodia parceiro;
    @Column(name = "conta_id", nullable = false)
    private Long contaId;
    @Column(name = "identificador_externo", nullable = false, length = 128)
    private String identificadorExterno;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;


    @java.lang.SuppressWarnings("all")
    public static class SubContaCustodiaBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String tenantId;
        @java.lang.SuppressWarnings("all")
        private ParceiroCustodia parceiro;
        @java.lang.SuppressWarnings("all")
        private Long contaId;
        @java.lang.SuppressWarnings("all")
        private String identificadorExterno;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;

        @java.lang.SuppressWarnings("all")
        SubContaCustodiaBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SubContaCustodia.SubContaCustodiaBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SubContaCustodia.SubContaCustodiaBuilder tenantId(final String tenantId) {
            this.tenantId = tenantId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SubContaCustodia.SubContaCustodiaBuilder parceiro(final ParceiroCustodia parceiro) {
            this.parceiro = parceiro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SubContaCustodia.SubContaCustodiaBuilder contaId(final Long contaId) {
            this.contaId = contaId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SubContaCustodia.SubContaCustodiaBuilder identificadorExterno(final String identificadorExterno) {
            this.identificadorExterno = identificadorExterno;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SubContaCustodia.SubContaCustodiaBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SubContaCustodia build() {
            return new SubContaCustodia(this.id, this.tenantId, this.parceiro, this.contaId, this.identificadorExterno, this.dataCriacao);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SubContaCustodia.SubContaCustodiaBuilder(id=" + this.id + ", tenantId=" + this.tenantId + ", parceiro=" + this.parceiro + ", contaId=" + this.contaId + ", identificadorExterno=" + this.identificadorExterno + ", dataCriacao=" + this.dataCriacao + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SubContaCustodia.SubContaCustodiaBuilder builder() {
        return new SubContaCustodia.SubContaCustodiaBuilder();
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
    public ParceiroCustodia getParceiro() {
        return this.parceiro;
    }

    @java.lang.SuppressWarnings("all")
    public Long getContaId() {
        return this.contaId;
    }

    @java.lang.SuppressWarnings("all")
    public String getIdentificadorExterno() {
        return this.identificadorExterno;
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
    public void setParceiro(final ParceiroCustodia parceiro) {
        this.parceiro = parceiro;
    }

    @java.lang.SuppressWarnings("all")
    public void setContaId(final Long contaId) {
        this.contaId = contaId;
    }

    @java.lang.SuppressWarnings("all")
    public void setIdentificadorExterno(final String identificadorExterno) {
        this.identificadorExterno = identificadorExterno;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @java.lang.SuppressWarnings("all")
    public SubContaCustodia() {
    }

    @java.lang.SuppressWarnings("all")
    public SubContaCustodia(final Long id, final String tenantId, final ParceiroCustodia parceiro, final Long contaId, final String identificadorExterno, final LocalDateTime dataCriacao) {
        this.id = id;
        this.tenantId = tenantId;
        this.parceiro = parceiro;
        this.contaId = contaId;
        this.identificadorExterno = identificadorExterno;
        this.dataCriacao = dataCriacao;
    }
}
