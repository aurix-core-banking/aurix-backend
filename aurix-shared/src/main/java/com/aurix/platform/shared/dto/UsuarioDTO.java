package com.aurix.platform.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO para Usuario.
 */
public class UsuarioDTO {
    /**
     * Tamanho mínimo do nome do usuário.
     */
    private static final int MIN_NOME_SIZE = 2;
    /**
     * Tamanho máximo do nome do usuário.
     */
    private static final int MAX_NOME_SIZE = 100;
    /**
     * ID do usuário.
     */
    private Long id;
    /**
     * Nome completo do usuário.
     */
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = MIN_NOME_SIZE, max = MAX_NOME_SIZE, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    /**
     * Endereço de email (usado como login).
     */
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;
    /**
     * Senha do usuário (não será exposto na resposta).
     */
    private String senha;
    /**
     * Data e hora do último login realizado com sucesso.
     */
    private LocalDateTime ultimoLogin;
    /**
     * Contador de tentativas de login inválidas.
     */
    private Integer tentativasLogin;
    /**
     * Indica se a conta está temporariamente bloqueada.
     */
    private Boolean contaBloqueada;
    /**
     * Data limite para troca obrigatória de senha.
     */
    private LocalDateTime dataExpiracaoSenha;
    /**
     * Conjunto de papéis/roles atribuídos ao usuário.
     */
    private Set<String> roles;
    /**
     * Conjunto de permissões granulares herdadas das roles.
     */
    private Set<String> permissions;
    /**
     * ID do cliente vinculado a este usuário.
     */
    private Long clienteId;
    /**
     * Nome do cliente vinculado.
     */
    private String clienteNome;
    /**
     * CPF do cliente vinculado.
     */
    private String clienteCpf;
    /**
     * Documento do cliente (CPF ou CNPJ).
     */
    private String clienteDocumento;
    /**
     * Tipo de pessoa do cliente ("FISICA" ou "JURIDICA").
     */
    private String clienteTipoPessoa;
    /**
     * Indica se o usuário está ativo no sistema.
     */
    private Boolean ativo;
    /**
     * Data de criação do registro.
     */
    private String dataCriacao;
    /**
     * Data da última atualização.
     */
    private String dataAtualizacao;

    /**
     * ID do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    /**
     * Nome completo do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Endereço de email (usado como login).
     */
    @java.lang.SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    /**
     * Senha do usuário (não será exposto na resposta).
     */
    @java.lang.SuppressWarnings("all")
    public String getSenha() {
        return this.senha;
    }

    /**
     * Data e hora do último login realizado com sucesso.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getUltimoLogin() {
        return this.ultimoLogin;
    }

    /**
     * Contador de tentativas de login inválidas.
     */
    @java.lang.SuppressWarnings("all")
    public Integer getTentativasLogin() {
        return this.tentativasLogin;
    }

    /**
     * Indica se a conta está temporariamente bloqueada.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getContaBloqueada() {
        return this.contaBloqueada;
    }

    /**
     * Data limite para troca obrigatória de senha.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracaoSenha() {
        return this.dataExpiracaoSenha;
    }

    /**
     * Conjunto de papéis/roles atribuídos ao usuário.
     */
    @java.lang.SuppressWarnings("all")
    public Set<String> getRoles() {
        return this.roles;
    }

    /**
     * Conjunto de permissões granulares herdadas das roles.
     */
    @java.lang.SuppressWarnings("all")
    public Set<String> getPermissions() {
        return this.permissions;
    }

    /**
     * ID do cliente vinculado a este usuário.
     */
    @java.lang.SuppressWarnings("all")
    public Long getClienteId() {
        return this.clienteId;
    }

    /**
     * Nome do cliente vinculado.
     */
    @java.lang.SuppressWarnings("all")
    public String getClienteNome() {
        return this.clienteNome;
    }

    /**
     * CPF do cliente vinculado.
     */
    @java.lang.SuppressWarnings("all")
    public String getClienteCpf() {
        return this.clienteCpf;
    }

    /**
     * Documento do cliente (CPF ou CNPJ).
     */
    @java.lang.SuppressWarnings("all")
    public String getClienteDocumento() {
        return this.clienteDocumento;
    }

    /**
     * Tipo de pessoa do cliente ("FISICA" ou "JURIDICA").
     */
    @java.lang.SuppressWarnings("all")
    public String getClienteTipoPessoa() {
        return this.clienteTipoPessoa;
    }

    /**
     * Indica se o usuário está ativo no sistema.
     */
    @java.lang.SuppressWarnings("all")
    public Boolean getAtivo() {
        return this.ativo;
    }

    /**
     * Data de criação do registro.
     */
    @java.lang.SuppressWarnings("all")
    public String getDataCriacao() {
        return this.dataCriacao;
    }

    /**
     * Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public String getDataAtualizacao() {
        return this.dataAtualizacao;
    }

    /**
     * ID do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Nome completo do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Endereço de email (usado como login).
     */
    @java.lang.SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Senha do usuário (não será exposto na resposta).
     */
    @java.lang.SuppressWarnings("all")
    public void setSenha(final String senha) {
        this.senha = senha;
    }

    /**
     * Data e hora do último login realizado com sucesso.
     */
    @java.lang.SuppressWarnings("all")
    public void setUltimoLogin(final LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    /**
     * Contador de tentativas de login inválidas.
     */
    @java.lang.SuppressWarnings("all")
    public void setTentativasLogin(final Integer tentativasLogin) {
        this.tentativasLogin = tentativasLogin;
    }

    /**
     * Indica se a conta está temporariamente bloqueada.
     */
    @java.lang.SuppressWarnings("all")
    public void setContaBloqueada(final Boolean contaBloqueada) {
        this.contaBloqueada = contaBloqueada;
    }

    /**
     * Data limite para troca obrigatória de senha.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataExpiracaoSenha(final LocalDateTime dataExpiracaoSenha) {
        this.dataExpiracaoSenha = dataExpiracaoSenha;
    }

    /**
     * Conjunto de papéis/roles atribuídos ao usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setRoles(final Set<String> roles) {
        this.roles = roles;
    }

    /**
     * Conjunto de permissões granulares herdadas das roles.
     */
    @java.lang.SuppressWarnings("all")
    public void setPermissions(final Set<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * ID do cliente vinculado a este usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteId(final Long clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * Nome do cliente vinculado.
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteNome(final String clienteNome) {
        this.clienteNome = clienteNome;
    }

    /**
     * CPF do cliente vinculado.
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteCpf(final String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }

    /**
     * Documento do cliente (CPF ou CNPJ).
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteDocumento(final String clienteDocumento) {
        this.clienteDocumento = clienteDocumento;
    }

    /**
     * Tipo de pessoa do cliente ("FISICA" ou "JURIDICA").
     */
    @java.lang.SuppressWarnings("all")
    public void setClienteTipoPessoa(final String clienteTipoPessoa) {
        this.clienteTipoPessoa = clienteTipoPessoa;
    }

    /**
     * Indica se o usuário está ativo no sistema.
     */
    @java.lang.SuppressWarnings("all")
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Data de criação do registro.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataCriacao(final String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    /**
     * Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataAtualizacao(final String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof UsuarioDTO)) return false;
        final UsuarioDTO other = (UsuarioDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$tentativasLogin = this.getTentativasLogin();
        final java.lang.Object other$tentativasLogin = other.getTentativasLogin();
        if (this$tentativasLogin == null ? other$tentativasLogin != null : !this$tentativasLogin.equals(other$tentativasLogin)) return false;
        final java.lang.Object this$contaBloqueada = this.getContaBloqueada();
        final java.lang.Object other$contaBloqueada = other.getContaBloqueada();
        if (this$contaBloqueada == null ? other$contaBloqueada != null : !this$contaBloqueada.equals(other$contaBloqueada)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$ativo = this.getAtivo();
        final java.lang.Object other$ativo = other.getAtivo();
        if (this$ativo == null ? other$ativo != null : !this$ativo.equals(other$ativo)) return false;
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
        final java.lang.Object this$permissions = this.getPermissions();
        final java.lang.Object other$permissions = other.getPermissions();
        if (this$permissions == null ? other$permissions != null : !this$permissions.equals(other$permissions)) return false;
        final java.lang.Object this$clienteNome = this.getClienteNome();
        final java.lang.Object other$clienteNome = other.getClienteNome();
        if (this$clienteNome == null ? other$clienteNome != null : !this$clienteNome.equals(other$clienteNome)) return false;
        final java.lang.Object this$clienteCpf = this.getClienteCpf();
        final java.lang.Object other$clienteCpf = other.getClienteCpf();
        if (this$clienteCpf == null ? other$clienteCpf != null : !this$clienteCpf.equals(other$clienteCpf)) return false;
        final java.lang.Object this$clienteDocumento = this.getClienteDocumento();
        final java.lang.Object other$clienteDocumento = other.getClienteDocumento();
        if (this$clienteDocumento == null ? other$clienteDocumento != null : !this$clienteDocumento.equals(other$clienteDocumento)) return false;
        final java.lang.Object this$clienteTipoPessoa = this.getClienteTipoPessoa();
        final java.lang.Object other$clienteTipoPessoa = other.getClienteTipoPessoa();
        if (this$clienteTipoPessoa == null ? other$clienteTipoPessoa != null : !this$clienteTipoPessoa.equals(other$clienteTipoPessoa)) return false;
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
        return other instanceof UsuarioDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $tentativasLogin = this.getTentativasLogin();
        result = result * PRIME + ($tentativasLogin == null ? 43 : $tentativasLogin.hashCode());
        final java.lang.Object $contaBloqueada = this.getContaBloqueada();
        result = result * PRIME + ($contaBloqueada == null ? 43 : $contaBloqueada.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $ativo = this.getAtivo();
        result = result * PRIME + ($ativo == null ? 43 : $ativo.hashCode());
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
        final java.lang.Object $permissions = this.getPermissions();
        result = result * PRIME + ($permissions == null ? 43 : $permissions.hashCode());
        final java.lang.Object $clienteNome = this.getClienteNome();
        result = result * PRIME + ($clienteNome == null ? 43 : $clienteNome.hashCode());
        final java.lang.Object $clienteCpf = this.getClienteCpf();
        result = result * PRIME + ($clienteCpf == null ? 43 : $clienteCpf.hashCode());
        final java.lang.Object $clienteDocumento = this.getClienteDocumento();
        result = result * PRIME + ($clienteDocumento == null ? 43 : $clienteDocumento.hashCode());
        final java.lang.Object $clienteTipoPessoa = this.getClienteTipoPessoa();
        result = result * PRIME + ($clienteTipoPessoa == null ? 43 : $clienteTipoPessoa.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "UsuarioDTO(id=" + this.getId() + ", nome=" + this.getNome() + ", email=" + this.getEmail() + ", senha=" + this.getSenha() + ", ultimoLogin=" + this.getUltimoLogin() + ", tentativasLogin=" + this.getTentativasLogin() + ", contaBloqueada=" + this.getContaBloqueada() + ", dataExpiracaoSenha=" + this.getDataExpiracaoSenha() + ", roles=" + this.getRoles() + ", permissions=" + this.getPermissions() + ", clienteId=" + this.getClienteId() + ", clienteNome=" + this.getClienteNome() + ", clienteCpf=" + this.getClienteCpf() + ", clienteDocumento=" + this.getClienteDocumento() + ", clienteTipoPessoa=" + this.getClienteTipoPessoa() + ", ativo=" + this.getAtivo() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public UsuarioDTO() {
    }

    /**
     * Creates a new {@code UsuarioDTO} instance.
     *
     * @param id ID do usuário.
     * @param nome Nome completo do usuário.
     * @param email Endereço de email (usado como login).
     * @param senha Senha do usuário (não será exposto na resposta).
     * @param ultimoLogin Data e hora do último login realizado com sucesso.
     * @param tentativasLogin Contador de tentativas de login inválidas.
     * @param contaBloqueada Indica se a conta está temporariamente bloqueada.
     * @param dataExpiracaoSenha Data limite para troca obrigatória de senha.
     * @param roles Conjunto de papéis/roles atribuídos ao usuário.
     * @param permissions Conjunto de permissões granulares herdadas das roles.
     * @param clienteId ID do cliente vinculado a este usuário.
     * @param clienteNome Nome do cliente vinculado.
     * @param clienteCpf CPF do cliente vinculado.
     * @param clienteDocumento Documento do cliente (CPF ou CNPJ).
     * @param clienteTipoPessoa Tipo de pessoa do cliente.
     * @param ativo Indica se o usuário está ativo no sistema.
     * @param dataCriacao Data de criação do registro.
     * @param dataAtualizacao Data da última atualização.
     */
    @java.lang.SuppressWarnings("all")
    public UsuarioDTO(final Long id, final String nome, final String email, final String senha, final LocalDateTime ultimoLogin, final Integer tentativasLogin, final Boolean contaBloqueada, final LocalDateTime dataExpiracaoSenha, final Set<String> roles, final Set<String> permissions, final Long clienteId, final String clienteNome, final String clienteCpf, final String clienteDocumento, final String clienteTipoPessoa, final Boolean ativo, final String dataCriacao, final String dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.ultimoLogin = ultimoLogin;
        this.tentativasLogin = tentativasLogin;
        this.contaBloqueada = contaBloqueada;
        this.dataExpiracaoSenha = dataExpiracaoSenha;
        this.roles = roles;
        this.permissions = permissions;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.clienteCpf = clienteCpf;
        this.clienteDocumento = clienteDocumento;
        this.clienteTipoPessoa = clienteTipoPessoa;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }
}
