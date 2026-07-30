package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Entidade PermissaoGranular do Aurix.
 * Representa uma permissão refinada com recurso, ação, condição e escopo.
 */
@Entity
@Table(name = "permissoes_granulares", schema = "aurix", indexes = {@Index(name = "idx_perm_gran_role", columnList = "role_id"), @Index(name = "idx_perm_gran_recurso_acao", columnList = "recurso, acao")})
public class PermissaoGranular extends BaseEntity {
    /**
     * Comprimento padrão para nomes de recursos.
     */
    private static final int RESOURCE_NAME_LENGTH = 80;
    /**
     * Comprimento padrão para nomes de ações.
     */
    private static final int ACTION_NAME_LENGTH = 40;
    /**
     * Comprimento padrão para descrições.
     */
    private static final int DESCRIPTION_MAX_LENGTH = 300;
    /**
     * Comprimento padrão para condições (JSON/Logic).
     */
    private static final int CONDITION_MAX_LENGTH = 500;
    /**
     * Comprimento padrão para nomes de escopo.
     */
    private static final int SCOPE_NAME_LENGTH = 40;
    /**
     * ID da role associada.
     */
    @NotNull(message = "Role ID é obrigatório")
    @Column(name = "role_id", nullable = false)
    private Long roleId;
    /**
     * Recurso protegido.
     */
    @NotBlank(message = "Recurso é obrigatório")
    @Size(max = RESOURCE_NAME_LENGTH, message = "Recurso deve ter no máximo 80 caracteres")
    @Column(nullable = false, length = RESOURCE_NAME_LENGTH)
    private String recurso;
    /**
     * Ação permitida.
     */
    @NotBlank(message = "Ação é obrigatória")
    @Size(max = ACTION_NAME_LENGTH, message = "Ação deve ter no máximo 40 caracteres")
    @Column(nullable = false, length = ACTION_NAME_LENGTH)
    private String acao;
    /**
     * Condição técnica para a permissão.
     */
    @Size(max = CONDITION_MAX_LENGTH, message = "Condição deve ter no máximo 500 caracteres")
    @Column(length = CONDITION_MAX_LENGTH)
    private String condicao;
    /**
     * Escopo de aplicação da permissão.
     */
    @NotBlank(message = "Escopo é obrigatório")
    @Size(max = SCOPE_NAME_LENGTH, message = "Escopo deve ter no máximo 40 caracteres")
    @Column(nullable = false, length = SCOPE_NAME_LENGTH)
    private String escopo;
    /**
     * Indica se a permissão está ativa.
     */
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;
    /**
     * Descrição amigável da permissão.
     */
    @Size(max = DESCRIPTION_MAX_LENGTH, message = "Descrição deve ter no máximo 300 caracteres")
    @Column(length = DESCRIPTION_MAX_LENGTH)
    private String descricao;
    /**
     * Recurso: Conta.
     */
    public static final String RECURSO_CONTA = "conta";
    /**
     * Recurso: Cliente.
     */
    public static final String RECURSO_CLIENTE = "cliente";
    /**
     * Recurso: Transação.
     */
    public static final String RECURSO_TRANSACAO = "transacao";
    /**
     * Recurso: Crédito.
     */
    public static final String RECURSO_CREDITO = "credito";
    /**
     * Recurso: PIX.
     */
    public static final String RECURSO_PIX = "pix";
    /**
     * Recurso: Cartão.
     */
    public static final String RECURSO_CARTAO = "cartao";
    /**
     * Recurso: Relatório.
     */
    public static final String RECURSO_RELATORIO = "relatorio";
    /**
     * Ação: Ler.
     */
    public static final String ACAO_LER = "ler";
    /**
     * Ação: Escrever.
     */
    public static final String ACAO_ESCREVER = "escrever";
    /**
     * Ação: Aprovar.
     */
    public static final String ACAO_APROVAR = "aprovar";
    /**
     * Ação: Excluir.
     */
    public static final String ACAO_EXCLUIR = "excluir";
    /**
     * Ação: Executar.
     */
    public static final String ACAO_EXECUTAR = "executar";
    /**
     * Escopo: Própria.
     */
    public static final String ESCOPO_PROPRIA = "propria";
    /**
     * Escopo: Agência.
     */
    public static final String ESCOPO_AGENCIA = "agencia";
    /**
     * Escopo: Banco.
     */
    public static final String ESCOPO_BANCO = "banco";

    /**
     * ID da role associada.
     */
    @java.lang.SuppressWarnings("all")
    public Long getRoleId() {
        return this.roleId;
    }

    /**
     * Recurso protegido.
     */
    @java.lang.SuppressWarnings("all")
    public String getRecurso() {
        return this.recurso;
    }

    /**
     * Ação permitida.
     */
    @java.lang.SuppressWarnings("all")
    public String getAcao() {
        return this.acao;
    }

    /**
     * Condição técnica para a permissão.
     */
    @java.lang.SuppressWarnings("all")
    public String getCondicao() {
        return this.condicao;
    }

    /**
     * Escopo de aplicação da permissão.
     */
    @java.lang.SuppressWarnings("all")
    public String getEscopo() {
        return this.escopo;
    }

    /**
     * Indica se a permissão está ativa.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

    /**
     * Descrição amigável da permissão.
     */
    @java.lang.SuppressWarnings("all")
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * ID da role associada.
     */
    @java.lang.SuppressWarnings("all")
    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }

    /**
     * Recurso protegido.
     */
    @java.lang.SuppressWarnings("all")
    public void setRecurso(final String recurso) {
        this.recurso = recurso;
    }

    /**
     * Ação permitida.
     */
    @java.lang.SuppressWarnings("all")
    public void setAcao(final String acao) {
        this.acao = acao;
    }

    /**
     * Condição técnica para a permissão.
     */
    @java.lang.SuppressWarnings("all")
    public void setCondicao(final String condicao) {
        this.condicao = condicao;
    }

    /**
     * Escopo de aplicação da permissão.
     */
    @java.lang.SuppressWarnings("all")
    public void setEscopo(final String escopo) {
        this.escopo = escopo;
    }

    /**
     * Indica se a permissão está ativa.
     */
    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Descrição amigável da permissão.
     */
    @java.lang.SuppressWarnings("all")
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "PermissaoGranular(roleId=" + this.getRoleId() + ", recurso=" + this.getRecurso() + ", acao=" + this.getAcao() + ", condicao=" + this.getCondicao() + ", escopo=" + this.getEscopo() + ", ativo=" + this.getAtivo() + ", descricao=" + this.getDescricao() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PermissaoGranular)) return false;
        final PermissaoGranular other = (PermissaoGranular) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$roleId = this.getRoleId();
        final java.lang.Object other$roleId = other.getRoleId();
        if (this$roleId == null ? other$roleId != null : !this$roleId.equals(other$roleId)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
        final java.lang.Object this$recurso = this.getRecurso();
        final java.lang.Object other$recurso = other.getRecurso();
        if (this$recurso == null ? other$recurso != null : !this$recurso.equals(other$recurso)) return false;
        final java.lang.Object this$acao = this.getAcao();
        final java.lang.Object other$acao = other.getAcao();
        if (this$acao == null ? other$acao != null : !this$acao.equals(other$acao)) return false;
        final java.lang.Object this$condicao = this.getCondicao();
        final java.lang.Object other$condicao = other.getCondicao();
        if (this$condicao == null ? other$condicao != null : !this$condicao.equals(other$condicao)) return false;
        final java.lang.Object this$escopo = this.getEscopo();
        final java.lang.Object other$escopo = other.getEscopo();
        if (this$escopo == null ? other$escopo != null : !this$escopo.equals(other$escopo)) return false;
        final java.lang.Object this$descricao = this.getDescricao();
        final java.lang.Object other$descricao = other.getDescricao();
        if (this$descricao == null ? other$descricao != null : !this$descricao.equals(other$descricao)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PermissaoGranular;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $roleId = this.getRoleId();
        result = result * PRIME + ($roleId == null ? 43 : $roleId.hashCode());
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
        final java.lang.Object $recurso = this.getRecurso();
        result = result * PRIME + ($recurso == null ? 43 : $recurso.hashCode());
        final java.lang.Object $acao = this.getAcao();
        result = result * PRIME + ($acao == null ? 43 : $acao.hashCode());
        final java.lang.Object $condicao = this.getCondicao();
        result = result * PRIME + ($condicao == null ? 43 : $condicao.hashCode());
        final java.lang.Object $escopo = this.getEscopo();
        result = result * PRIME + ($escopo == null ? 43 : $escopo.hashCode());
        final java.lang.Object $descricao = this.getDescricao();
        result = result * PRIME + ($descricao == null ? 43 : $descricao.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public PermissaoGranular() {
    }
}
