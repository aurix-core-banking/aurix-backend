package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Entidade Permission do Aurix.
 * Representa uma permissão do sistema.
 */
@Entity
@Table(name = "permissions", schema = "aurix")
public class Permission extends BaseEntity {
    /**
     * Comprimento máximo para o nome da permissão.
     */
    private static final int NAME_MAX_LENGTH = 100;
    /**
     * Comprimento mínimo para o nome da permissão.
     */
    private static final int NAME_MIN_LENGTH = 2;
    /**
     * Comprimento máximo para descrições.
     */
    private static final int DESCRIPTION_MAX_LENGTH = 200;
    /**
     * Nome único da permissão.
     */
    @NotBlank(message = "Nome da permissão é obrigatório")
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = "Nome da permissão deve ter entre 2 e 100 caracteres")
    @Column(nullable = false, unique = true)
    private String nome;
    /**
     * Descrição detalhada da permissão.
     */
    @Column(length = DESCRIPTION_MAX_LENGTH)
    private String descricao;
    /**
     * Recurso associado.
     */
    @Column(name = "recurso")
    private String recurso;
    /**
     * Ação associada.
     */
    @Column(name = "acao")
    private String acao;

    /**
     * Construtor para facilitar criação de permissões.
     *
     * @param name     nome da permissão.
     * @param desc     descrição da permissão.
     * @param resource recurso da permissão.
     * @param action   ação da permissão.
     */
    public Permission(final String name, final String desc, final String resource, final String action) {
        this.nome = name;
        this.descricao = desc;
        this.recurso = resource;
        this.acao = action;
    }

    /**
     * Nome único da permissão.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Descrição detalhada da permissão.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Recurso associado.
     */
    @java.lang.SuppressWarnings("all")
    public String getRecurso() {
        return this.recurso;
    }

    /**
     * Ação associada.
     */
    @java.lang.SuppressWarnings("all")
    public String getAcao() {
        return this.acao;
    }

    /**
     * Nome único da permissão.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Descrição detalhada da permissão.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Recurso associado.
     */
    @java.lang.SuppressWarnings("all")
    public void setRecurso(final String recurso) {
        this.recurso = recurso;
    }

    /**
     * Ação associada.
     */
    @java.lang.SuppressWarnings("all")
    public void setAcao(final String acao) {
        this.acao = acao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Permission(nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", recurso=" + this.getRecurso() + ", acao=" + this.getAcao() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Permission)) return false;
        final Permission other = (Permission) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$recurso = this.getRecurso();
        final java.lang.Object other$recurso = other.getRecurso();
        if (this$recurso == null ? other$recurso != null : !this$recurso.equals(other$recurso)) return false;
        final java.lang.Object this$acao = this.getAcao();
        final java.lang.Object other$acao = other.getAcao();
        if (this$acao == null ? other$acao != null : !this$acao.equals(other$acao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Permission;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        final java.lang.Object $recurso = this.getRecurso();
        result = result * PRIME + ($recurso == null ? 43 : $recurso.hashCode());
        final java.lang.Object $acao = this.getAcao();
        result = result * PRIME + ($acao == null ? 43 : $acao.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Permission() {
    }
}
