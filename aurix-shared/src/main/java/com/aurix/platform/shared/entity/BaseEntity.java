package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * Entidade base para todas as entidades do Aurix.
 * Contém campos comuns como ID, timestamps e versionamento.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    /**
     * Comprimento do ID do locatário.
     */
    private static final int TENANT_ID_LENGTH = 64;
    /**
     * ID do locatário (multi-tenant).
     */
    @Column(name = "tenant_id", nullable = true, length = TENANT_ID_LENGTH)
    private String tenantId;
    /**
     * Identificador único da entidade.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Data e hora de criação da entidade.
     */
    @CreatedDate
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    /**
     * Data e hora da última atualização da entidade.
     */
    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    /**
     * Versão da entidade para controle de concorrência otimista.
     */
    @Version
    @Column(name = "versao")
    private Integer versao = 1;

    /**
     * Método executado antes de persistir a entidade.
     */
    @PrePersist
    protected void onCreate() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }
    }

    /**
     * Método executado antes de atualizar a entidade.
     */
    @PreUpdate
    protected void onUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }

    /**
     * ID do locatário (multi-tenant).
     */
    @java.lang.SuppressWarnings("all")
    public String getTenantId() {
        return this.tenantId;
    }

    /**
     * Identificador único da entidade.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Data e hora de criação da entidade.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataCriacao() {
        return this.dataCriacao;
    }

    /**
     * Data e hora da última atualização da entidade.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    /**
     * Versão da entidade para controle de concorrência otimista.
     */
    @java.lang.SuppressWarnings("all")
    public Integer getVersao() {
        return this.versao;
    }

    /**
     * ID do locatário (multi-tenant).
     */
    @java.lang.SuppressWarnings("all")
    public void setTenantId(final String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Identificador único da entidade.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Data e hora de criação da entidade.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * Data e hora da última atualização da entidade.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    /**
     * Versão da entidade para controle de concorrência otimista.
     */
    @java.lang.SuppressWarnings("all")
    public void setVersao(final Integer versao) {
        this.versao = versao;
    }
}
