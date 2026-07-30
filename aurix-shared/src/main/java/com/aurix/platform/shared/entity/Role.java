package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

/**
 * Entidade Role do Aurix.
 * Representa uma role/perfil de usuário.
 */
@Entity
@Table(name = "roles", schema = "aurix")
public class Role extends BaseEntity {
    /**
     * Comprimento máximo para o nome da role.
     */
    private static final int NAME_MAX_LENGTH = 50;
    /**
     * Comprimento mínimo para o nome da role.
     */
    private static final int NAME_MIN_LENGTH = 2;
    /**
     * Comprimento máximo para descrições.
     */
    private static final int DESCRIPTION_MAX_LENGTH = 200;
    /**
     * Nome único da role.
     */
    @NotBlank(message = "Nome da role é obrigatório")
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = "Nome da role deve ter entre 2 e 50 caracteres")
    @Column(nullable = false, unique = true)
    private String nome;
    /**
     * Descrição detalhada da role.
     */
    @Column(length = DESCRIPTION_MAX_LENGTH)
    private String descricao;
    /**
     * Conjunto de permissões desta role.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions;

    /**
     * Verifica se a role tem uma permissão específica.
     *
     * @param permissionName nome da permissão.
     * @return true se tiver, false caso contrário.
     */
    public boolean hasPermission(final String permissionName) {
        return permissions.stream().anyMatch(permission -> permission.getNome().equals(permissionName));
    }

    /**
     * Nome único da role.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Descrição detalhada da role.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Conjunto de permissões desta role.
     */
    @java.lang.SuppressWarnings("all")
    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    /**
     * Nome único da role.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Descrição detalhada da role.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * Conjunto de permissões desta role.
     */
    @java.lang.SuppressWarnings("all")
    public void setPermissions(final Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Role(nome=" + this.getNome() + ", descricao=" + this.getDescricao() + ", permissions=" + this.getPermissions() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Role)) return false;
        final Role other = (Role) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        final java.lang.Object this$permissions = this.getPermissions();
        final java.lang.Object other$permissions = other.getPermissions();
        if (this$permissions == null ? other$permissions != null : !this$permissions.equals(other$permissions)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Role;
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
        final java.lang.Object $permissions = this.getPermissions();
        result = result * PRIME + ($permissions == null ? 43 : $permissions.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Role() {
    }

    /**
     * Creates a new {@code Role} instance.
     *
     * @param nome Nome único da role.
     * @param descricao Descrição detalhada da role.
     * @param permissions Conjunto de permissões desta role.
     */
    @java.lang.SuppressWarnings("all")
    public Role(final String nome, final String descricao, final Set<Permission> permissions) {
        this.nome = nome;
        this.descricao = descricao;
        this.permissions = permissions;
    }
}
