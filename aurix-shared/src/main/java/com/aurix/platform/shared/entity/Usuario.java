package com.aurix.platform.shared.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entidade Usuario do Aurix.
 * Representa um usuário do sistema.
 */
@Entity
@Table(name = "usuarios", schema = "aurix")
public class Usuario extends BaseEntity {
    /**
     * Comprimento máximo do nome.
     */
    private static final int NAME_MAX_LENGTH = 100;
    /**
     * Comprimento mínimo da senha.
     */
    private static final int PASSWORD_MIN_LENGTH = 6;
    /**
     * Máximo de tentativas de login antes de bloquear.
     */
    private static final int MAX_LOGIN_ATTEMPTS = 5;
    /**
     * Nome completo do usuário.
     */
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = NAME_MAX_LENGTH, message = "Nome deve ter entre 2 e 100 caracteres")
    @Column(nullable = false)
    private String nome;
    /**
     * Endereço de email único (usado como login).
     */
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(nullable = false, unique = true)
    private String email;
    /**
     * Hash seguro da senha de acesso.
     */
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = PASSWORD_MIN_LENGTH, message = "Senha deve ter pelo menos 6 caracteres")
    @Column(nullable = false)
    private String senha;
    /**
     * Data e hora do último acesso bem-sucedido.
     */
    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;
    /**
     * Contador de falhas consecutivas de autenticação.
     */
    @Column(name = "tentativas_login")
    private Integer tentativasLogin = 0;
    /**
     * Indica se o usuário possui permissão de acesso.
     */
    @Column(name = "ativo")
    private Boolean ativo = true;
    /**
     * Indica se a conta foi bloqueada por excesso de erros.
     */
    @Column(name = "conta_bloqueada")
    private Boolean contaBloqueada = false;
    /**
     * Data limite para validade da senha atual.
     */
    @Column(name = "data_expiracao_senha")
    private LocalDateTime dataExpiracaoSenha;
    /**
     * Conjunto de papéis (roles) atribuídos ao usuário.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    /**
     * Cliente associado ao usuário (opcional para admin).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    /**
     * Verifica se a conta está ativa.
     *
     * @return true se ativa, false caso contrário.
     */
    public boolean isContaAtiva() {
        return getAtivo() && !contaBloqueada;
    }

    /**
     * Verifica se a senha expirou.
     *
     * @return true se expirada, false caso contrário.
     */
    public boolean isSenhaExpirada() {
        return dataExpiracaoSenha != null && LocalDateTime.now().isAfter(dataExpiracaoSenha);
    }

    /**
     * Incrementa tentativas de login.
     */
    public void incrementarTentativasLogin() {
        this.tentativasLogin++;
        if (this.tentativasLogin >= MAX_LOGIN_ATTEMPTS) {
            this.contaBloqueada = true;
        }
    }

    /**
     * Reseta tentativas de login.
     */
    public void resetarTentativasLogin() {
        this.tentativasLogin = 0;
        this.contaBloqueada = false;
    }

    /**
     * Nome completo do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Endereço de email único (usado como login).
     */
    @java.lang.SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    /**
     * Hash seguro da senha de acesso.
     */
    @java.lang.SuppressWarnings("all")
    public String getSenha() {
        return this.senha;
    }

    /**
     * Data e hora do último acesso bem-sucedido.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getUltimoLogin() {
        return this.ultimoLogin;
    }

    /**
     * Contador de falhas consecutivas de autenticação.
     */
    @java.lang.SuppressWarnings("all")
    public Integer getTentativasLogin() {
        return this.tentativasLogin;
    }

    /**
     * Indica se o usuário possui permissão de acesso.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

    /**
     * Indica se a conta foi bloqueada por excesso de erros.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getContaBloqueada() {
        return this.contaBloqueada;
    }

    /**
     * Data limite para validade da senha atual.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracaoSenha() {
        return this.dataExpiracaoSenha;
    }

    /**
     * Conjunto de papéis (roles) atribuídos ao usuário.
     */
    @java.lang.SuppressWarnings("all")
    public Set<Role> getRoles() {
        return this.roles;
    }

    /**
     * Cliente associado ao usuário (opcional para admin).
     */
    @java.lang.SuppressWarnings("all")
    public Cliente getCliente() {
        return this.cliente;
    }

    /**
     * Nome completo do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Endereço de email único (usado como login).
     */
    @java.lang.SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Hash seguro da senha de acesso.
     */
    @java.lang.SuppressWarnings("all")
    public void setSenha(final String senha) {
        this.senha = senha;
    }

    /**
     * Data e hora do último acesso bem-sucedido.
     */
    @java.lang.SuppressWarnings("all")
    public void setUltimoLogin(final LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    /**
     * Contador de falhas consecutivas de autenticação.
     */
    @java.lang.SuppressWarnings("all")
    public void setTentativasLogin(final Integer tentativasLogin) {
        this.tentativasLogin = tentativasLogin;
    }

    /**
     * Indica se o usuário possui permissão de acesso.
     */
    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Indica se a conta foi bloqueada por excesso de erros.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaBloqueada(final Boolean contaBloqueada) {
        this.contaBloqueada = contaBloqueada;
    }

    /**
     * Data limite para validade da senha atual.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataExpiracaoSenha(final LocalDateTime dataExpiracaoSenha) {
        this.dataExpiracaoSenha = dataExpiracaoSenha;
    }

    /**
     * Conjunto de papéis (roles) atribuídos ao usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Cliente associado ao usuário (opcional para admin).
     */
    @java.lang.SuppressWarnings("all")
    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "Usuario(nome=" + this.getNome() + ", email=" + this.getEmail() + ", senha=" + this.getSenha() + ", ultimoLogin=" + this.getUltimoLogin() + ", tentativasLogin=" + this.getTentativasLogin() + ", ativo=" + this.getAtivo() + ", contaBloqueada=" + this.getContaBloqueada() + ", dataExpiracaoSenha=" + this.getDataExpiracaoSenha() + ", roles=" + this.getRoles() + ", cliente=" + this.getCliente() + ")";
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Usuario)) return false;
        final Usuario other = (Usuario) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        if (!super.equals(o)) return false;
        final java.lang.Object this$tentativasLogin = this.getTentativasLogin();
        final java.lang.Object other$tentativasLogin = other.getTentativasLogin();
        if (this$tentativasLogin == null ? other$tentativasLogin != null : !this$tentativasLogin.equals(other$tentativasLogin)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
        final java.lang.Object this$contaBloqueada = this.getContaBloqueada();
        final java.lang.Object other$contaBloqueada = other.getContaBloqueada();
        if (this$contaBloqueada == null ? other$contaBloqueada != null : !this$contaBloqueada.equals(other$contaBloqueada)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$senha = this.getSenha();
        final java.lang.Object other$senha = other.getSenha();
        if (this$senha == null ? other$senha != null : !this$senha.equals(other$senha)) return false;
        final java.lang.Object this$ultimoLogin = this.getUltimoLogin();
        final java.lang.Object other$ultimoLogin = other.getUltimoLogin();
        if (this$ultimoLogin == null ? other$ultimoLogin != null : !this$ultimoLogin.equals(other$ultimoLogin)) return false;
        final java.lang.Object this$dataExpiracaoSenha = this.getDataExpiracaoSenha();
        final java.lang.Object other$dataExpiracaoSenha = other.getDataExpiracaoSenha();
        if (this$dataExpiracaoSenha == null ? other$dataExpiracaoSenha != null : !this$dataExpiracaoSenha.equals(other$dataExpiracaoSenha)) return false;
        final java.lang.Object this$roles = this.getRoles();
        final java.lang.Object other$roles = other.getRoles();
        if (this$roles == null ? other$roles != null : !this$roles.equals(other$roles)) return false;
        final java.lang.Object this$cliente = this.getCliente();
        final java.lang.Object other$cliente = other.getCliente();
        if (this$cliente == null ? other$cliente != null : !this$cliente.equals(other$cliente)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Usuario;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final java.lang.Object $tentativasLogin = this.getTentativasLogin();
        result = result * PRIME + ($tentativasLogin == null ? 43 : $tentativasLogin.hashCode());
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
        final java.lang.Object $contaBloqueada = this.getContaBloqueada();
        result = result * PRIME + ($contaBloqueada == null ? 43 : $contaBloqueada.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $senha = this.getSenha();
        result = result * PRIME + ($senha == null ? 43 : $senha.hashCode());
        final java.lang.Object $ultimoLogin = this.getUltimoLogin();
        result = result * PRIME + ($ultimoLogin == null ? 43 : $ultimoLogin.hashCode());
        final java.lang.Object $dataExpiracaoSenha = this.getDataExpiracaoSenha();
        result = result * PRIME + ($dataExpiracaoSenha == null ? 43 : $dataExpiracaoSenha.hashCode());
        final java.lang.Object $roles = this.getRoles();
        result = result * PRIME + ($roles == null ? 43 : $roles.hashCode());
        final java.lang.Object $cliente = this.getCliente();
        result = result * PRIME + ($cliente == null ? 43 : $cliente.hashCode());
        return result;
    }

    @java.lang.SuppressWarnings("all")
    public Usuario() {
    }

    /**
     * Creates a new {@code Usuario} instance.
     *
     * @param nome Nome completo do usuário.
     * @param email Endereço de email único (usado como login).
     * @param senha Hash seguro da senha de acesso.
     * @param ultimoLogin Data e hora do último acesso bem-sucedido.
     * @param tentativasLogin Contador de falhas consecutivas de autenticação.
     * @param ativo Indica se o usuário possui permissão de acesso.
     * @param contaBloqueada Indica se a conta foi bloqueada por excesso de erros.
     * @param dataExpiracaoSenha Data limite para validade da senha atual.
     * @param roles Conjunto de papéis (roles) atribuídos ao usuário.
     * @param cliente Cliente associado ao usuário (opcional para admin).
     */
    @java.lang.SuppressWarnings("all")
    public Usuario(final String nome, final String email, final String senha, final LocalDateTime ultimoLogin, final Integer tentativasLogin, final Boolean ativo, final Boolean contaBloqueada, final LocalDateTime dataExpiracaoSenha, final Set<Role> roles, final Cliente cliente) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ultimoLogin = ultimoLogin;
        this.tentativasLogin = tentativasLogin;
        this.ativo = ativo;
        this.contaBloqueada = contaBloqueada;
        this.dataExpiracaoSenha = dataExpiracaoSenha;
        this.roles = roles;
        this.cliente = cliente;
    }
}
