package com.aurix.platform.shared.dto;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO para resposta de login.
 */
public class LoginResponseDTO {
    /**
     * Token de acesso JWT.
     */
    private String token;
    /**
     * Tipo do token (ex: Bearer).
     */
    private String tipoToken;
    /**
     * ID do usuário autenticado.
     */
    private Long usuarioId;
    /**
     * Nome do usuário.
     */
    private String nome;
    /**
     * Email do usuário.
     */
    private String email;
    /**
     * Roles/Papéis do usuário.
     */
    private Set<String> roles;
    /**
     * Permissões granulares do usuário.
     */
    private Set<String> permissions;
    /**
     * Data e hora de expiração do token.
     */
    private LocalDateTime dataExpiracao;
    /**
     * Data e hora do último login realizado.
     */
    private LocalDateTime ultimoLogin;

    @java.lang.SuppressWarnings("all")
    private static String $default$tipoToken() {
        return "Bearer";
    }


    @java.lang.SuppressWarnings("all")
    public static class LoginResponseDTOBuilder {
        @java.lang.SuppressWarnings("all")
        private String token;
        @java.lang.SuppressWarnings("all")
        private boolean tipoToken$set;
        @java.lang.SuppressWarnings("all")
        private String tipoToken$value;
        @java.lang.SuppressWarnings("all")
        private Long usuarioId;
        @java.lang.SuppressWarnings("all")
        private String nome;
        @java.lang.SuppressWarnings("all")
        private String email;
        @java.lang.SuppressWarnings("all")
        private Set<String> roles;
        @java.lang.SuppressWarnings("all")
        private Set<String> permissions;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataExpiracao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime ultimoLogin;

        @java.lang.SuppressWarnings("all")
        LoginResponseDTOBuilder() {
        }

        /**
         * Token de acesso JWT.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LoginResponseDTO.LoginResponseDTOBuilder token(final String token) {
            this.token = token;
            return this;
        }

        /**
         * Tipo do token (ex: Bearer).
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LoginResponseDTO.LoginResponseDTOBuilder tipoToken(final String tipoToken) {
            this.tipoToken$value = tipoToken;
            tipoToken$set = true;
            return this;
        }

        /**
         * ID do usuário autenticado.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LoginResponseDTO.LoginResponseDTOBuilder usuarioId(final Long usuarioId) {
            this.usuarioId = usuarioId;
            return this;
        }

        /**
         * Nome do usuário.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LoginResponseDTO.LoginResponseDTOBuilder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        /**
         * Email do usuário.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LoginResponseDTO.LoginResponseDTOBuilder email(final String email) {
            this.email = email;
            return this;
        }

        /**
         * Roles/Papéis do usuário.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LoginResponseDTO.LoginResponseDTOBuilder roles(final Set<String> roles) {
            this.roles = roles;
            return this;
        }

        /**
         * Permissões granulares do usuário.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LoginResponseDTO.LoginResponseDTOBuilder permissions(final Set<String> permissions) {
            this.permissions = permissions;
            return this;
        }

        /**
         * Data e hora de expiração do token.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LoginResponseDTO.LoginResponseDTOBuilder dataExpiracao(final LocalDateTime dataExpiracao) {
            this.dataExpiracao = dataExpiracao;
            return this;
        }

        /**
         * Data e hora do último login realizado.
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public LoginResponseDTO.LoginResponseDTOBuilder ultimoLogin(final LocalDateTime ultimoLogin) {
            this.ultimoLogin = ultimoLogin;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public LoginResponseDTO build() {
            String tipoToken$value = this.tipoToken$value;
            if (!this.tipoToken$set) tipoToken$value = LoginResponseDTO.$default$tipoToken();
            return new LoginResponseDTO(this.token, tipoToken$value, this.usuarioId, this.nome, this.email, this.roles, this.permissions, this.dataExpiracao, this.ultimoLogin);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "LoginResponseDTO.LoginResponseDTOBuilder(token=" + this.token + ", tipoToken$value=" + this.tipoToken$value + ", usuarioId=" + this.usuarioId + ", nome=" + this.nome + ", email=" + this.email + ", roles=" + this.roles + ", permissions=" + this.permissions + ", dataExpiracao=" + this.dataExpiracao + ", ultimoLogin=" + this.ultimoLogin + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static LoginResponseDTO.LoginResponseDTOBuilder builder() {
        return new LoginResponseDTO.LoginResponseDTOBuilder();
    }

    /**
     * Token de acesso JWT.
     */
    @java.lang.SuppressWarnings("all")
    public String getToken() {
        return this.token;
    }

    /**
     * Tipo do token (ex: Bearer).
     */
    @java.lang.SuppressWarnings("all")
    public String getTipoToken() {
        return this.tipoToken;
    }

    /**
     * ID do usuário autenticado.
     */
    @java.lang.SuppressWarnings("all")
    public Long getUsuarioId() {
        return this.usuarioId;
    }

    /**
     * Nome do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public String getNome() {
        return this.nome;
    }

    /**
     * Email do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public String getEmail() {
        return this.email;
    }

    /**
     * Roles/Papéis do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public Set<String> getRoles() {
        return this.roles;
    }

    /**
     * Permissões granulares do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public Set<String> getPermissions() {
        return this.permissions;
    }

    /**
     * Data e hora de expiração do token.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    /**
     * Data e hora do último login realizado.
     */
    @java.lang.SuppressWarnings("all")
    public LocalDateTime getUltimoLogin() {
        return this.ultimoLogin;
    }

    /**
     * Token de acesso JWT.
     */
    @java.lang.SuppressWarnings("all")
    public void setToken(final String token) {
        this.token = token;
    }

    /**
     * Tipo do token (ex: Bearer).
     */
    @java.lang.SuppressWarnings("all")
    public void setTipoToken(final String tipoToken) {
        this.tipoToken = tipoToken;
    }

    /**
     * ID do usuário autenticado.
     */
    @java.lang.SuppressWarnings("all")
    public void setUsuarioId(final Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * Nome do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setNome(final String nome) {
        this.nome = nome;
    }

    /**
     * Email do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Roles/Papéis do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setRoles(final Set<String> roles) {
        this.roles = roles;
    }

    /**
     * Permissões granulares do usuário.
     */
    @java.lang.SuppressWarnings("all")
    public void setPermissions(final Set<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * Data e hora de expiração do token.
     */
    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    /**
     * Data e hora do último login realizado.
     */
    @java.lang.SuppressWarnings("all")
    public void setUltimoLogin(final LocalDateTime ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LoginResponseDTO)) return false;
        final LoginResponseDTO other = (LoginResponseDTO) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$usuarioId = this.getUsuarioId();
        final java.lang.Object other$usuarioId = other.getUsuarioId();
        if (this$usuarioId == null ? other$usuarioId != null : !this$usuarioId.equals(other$usuarioId)) return false;
        final java.lang.Object this$token = this.getToken();
        final java.lang.Object other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) return false;
        final java.lang.Object this$tipoToken = this.getTipoToken();
        final java.lang.Object other$tipoToken = other.getTipoToken();
        if (this$tipoToken == null ? other$tipoToken != null : !this$tipoToken.equals(other$tipoToken)) return false;
        final java.lang.Object this$nome = this.getNome();
        final java.lang.Object other$nome = other.getNome();
        if (this$nome == null ? other$nome != null : !this$nome.equals(other$nome)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$roles = this.getRoles();
        final java.lang.Object other$roles = other.getRoles();
        if (this$roles == null ? other$roles != null : !this$roles.equals(other$roles)) return false;
        final java.lang.Object this$permissions = this.getPermissions();
        final java.lang.Object other$permissions = other.getPermissions();
        if (this$permissions == null ? other$permissions != null : !this$permissions.equals(other$permissions)) return false;
        final java.lang.Object this$dataExpiracao = this.getDataExpiracao();
        final java.lang.Object other$dataExpiracao = other.getDataExpiracao();
        if (this$dataExpiracao == null ? other$dataExpiracao != null : !this$dataExpiracao.equals(other$dataExpiracao)) return false;
        final java.lang.Object this$ultimoLogin = this.getUltimoLogin();
        final java.lang.Object other$ultimoLogin = other.getUltimoLogin();
        if (this$ultimoLogin == null ? other$ultimoLogin != null : !this$ultimoLogin.equals(other$ultimoLogin)) return false;
        return true;
    }

    @java.lang.SuppressWarnings("all")
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LoginResponseDTO;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $usuarioId = this.getUsuarioId();
        result = result * PRIME + ($usuarioId == null ? 43 : $usuarioId.hashCode());
        final java.lang.Object $token = this.getToken();
        result = result * PRIME + ($token == null ? 43 : $token.hashCode());
        final java.lang.Object $tipoToken = this.getTipoToken();
        result = result * PRIME + ($tipoToken == null ? 43 : $tipoToken.hashCode());
        final java.lang.Object $nome = this.getNome();
        result = result * PRIME + ($nome == null ? 43 : $nome.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $roles = this.getRoles();
        result = result * PRIME + ($roles == null ? 43 : $roles.hashCode());
        final java.lang.Object $permissions = this.getPermissions();
        result = result * PRIME + ($permissions == null ? 43 : $permissions.hashCode());
        final java.lang.Object $dataExpiracao = this.getDataExpiracao();
        result = result * PRIME + ($dataExpiracao == null ? 43 : $dataExpiracao.hashCode());
        final java.lang.Object $ultimoLogin = this.getUltimoLogin();
        result = result * PRIME + ($ultimoLogin == null ? 43 : $ultimoLogin.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "LoginResponseDTO(token=" + this.getToken() + ", tipoToken=" + this.getTipoToken() + ", usuarioId=" + this.getUsuarioId() + ", nome=" + this.getNome() + ", email=" + this.getEmail() + ", roles=" + this.getRoles() + ", permissions=" + this.getPermissions() + ", dataExpiracao=" + this.getDataExpiracao() + ", ultimoLogin=" + this.getUltimoLogin() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public LoginResponseDTO() {
        this.tipoToken = LoginResponseDTO.$default$tipoToken();
    }

    /**
     * Creates a new {@code LoginResponseDTO} instance.
     *
     * @param token Token de acesso JWT.
     * @param tipoToken Tipo do token (ex: Bearer).
     * @param usuarioId ID do usuário autenticado.
     * @param nome Nome do usuário.
     * @param email Email do usuário.
     * @param roles Roles/Papéis do usuário.
     * @param permissions Permissões granulares do usuário.
     * @param dataExpiracao Data e hora de expiração do token.
     * @param ultimoLogin Data e hora do último login realizado.
     */
    @java.lang.SuppressWarnings("all")
    public LoginResponseDTO(final String token, final String tipoToken, final Long usuarioId, final String nome, final String email, final Set<String> roles, final Set<String> permissions, final LocalDateTime dataExpiracao, final LocalDateTime ultimoLogin) {
        this.token = token;
        this.tipoToken = tipoToken;
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
        this.roles = roles;
        this.permissions = permissions;
        this.dataExpiracao = dataExpiracao;
        this.ultimoLogin = ultimoLogin;
    }
}
