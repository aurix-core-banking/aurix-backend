package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa uma sessão mobile
 * 
 * Gerencia sessões de usuários no app mobile
 */
@Entity
@Table(name = "sessoes_mobile", schema = "aurix")
public class SessaoMobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sessao_id", unique = true, nullable = false, length = 100)
    private String sessaoId;
    @Column(name = "dispositivo_id", nullable = false, length = 100)
    private String dispositivoId;
    @Column(name = "cliente_id", nullable = false, length = 50)
    private String clienteId;
    @Column(name = "usuario_id", nullable = false, length = 50)
    private String usuarioId;
    @Column(name = "device_token", length = 500)
    private String deviceToken;
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    @Column(name = "user_agent", length = 500)
    private String userAgent;
    @Column(name = "app_version", length = 50)
    private String appVersion;
    @Column(name = "os_version", length = 50)
    private String osVersion;
    @Column(name = "device_model", length = 100)
    private String deviceModel;
    @Column(name = "device_manufacturer", length = 50)
    private String deviceManufacturer;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusSessao status;
    @Column(name = "data_login", nullable = false)
    private LocalDateTime dataLogin;
    @Column(name = "data_logout")
    private LocalDateTime dataLogout;
    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;
    @Column(name = "ultima_atividade")
    private LocalDateTime ultimaAtividade;
    @Column(name = "timeout_minutos", nullable = false)
    private Integer timeoutMinutos;
    @Column(name = "mfa_verificado", nullable = false)
    private Boolean mfaVerificado;
    @Column(name = "biometrico_verificado")
    private Boolean biometricoVerificado;
    @Column(name = "face_id_verificado")
    private Boolean faceIdVerificado;
    @Column(name = "touch_id_verificado")
    private Boolean touchIdVerificado;
    @Column(name = "pin_verificado")
    private Boolean pinVerificado;
    @Column(name = "localizacao_verificada")
    private Boolean localizacaoVerificada;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "precisao_gps")
    private Double precisaoGps;
    @Column(name = "endereco_localizacao", length = 200)
    private String enderecoLocalizacao;
    @Column(name = "rede_wifi", length = 100)
    private String redeWifi;
    @Column(name = "operadora", length = 50)
    private String operadora;
    @Column(name = "tipo_conexao", length = 20)
    private String tipoConexao;
    @Column(name = "velocidade_conexao")
    private Integer velocidadeConexao;
    @Column(name = "tentativas_falha")
    private Integer tentativasFalha;
    @Column(name = "bloqueado")
    private Boolean bloqueado;
    @Column(name = "motivo_bloqueio", length = 200)
    private String motivoBloqueio;
    @Column(name = "push_notifications_ativas", nullable = false)
    private Boolean pushNotificationsAtivas;
    @Column(name = "background_refresh_habilitado")
    private Boolean backgroundRefreshHabilitado;
    @Column(name = "bateria_nivel")
    private Integer bateriaNivel;
    @Column(name = "armazenamento_disponivel")
    private Long armazenamentoDisponivel;
    @Column(name = "ram_disponivel")
    private Long ramDisponivel;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "atividades_realizadas", columnDefinition = "jsonb")
    private String atividadesRealizadas;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadados", columnDefinition = "jsonb")
    private String metadados;
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    @UpdateTimestamp
    @Column(name = "data_atualizacao", nullable = false)
    private LocalDateTime dataAtualizacao;
    @Column(name = "versao_controle", nullable = false)
    @Version
    private Long versaoControle;


    /**
     * Status da sessão
     */
    public enum StatusSessao {
        ATIVA,  // Sessão ativa
        EXPIRADA,  // Sessão expirada
        ENCERRADA,  // Sessão encerrada pelo usuário
        BLOQUEADA,  // Sessão bloqueada por segurança
        SUSPENSA,  // Sessão suspensa
        INATIVA,  // Sessão inativa
        BACKGROUND // Sessão em background
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class SessaoMobileBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String sessaoId;
        @java.lang.SuppressWarnings("all")
        private String dispositivoId;
        @java.lang.SuppressWarnings("all")
        private String clienteId;
        @java.lang.SuppressWarnings("all")
        private String usuarioId;
        @java.lang.SuppressWarnings("all")
        private String deviceToken;
        @java.lang.SuppressWarnings("all")
        private String ipAddress;
        @java.lang.SuppressWarnings("all")
        private String userAgent;
        @java.lang.SuppressWarnings("all")
        private String appVersion;
        @java.lang.SuppressWarnings("all")
        private String osVersion;
        @java.lang.SuppressWarnings("all")
        private String deviceModel;
        @java.lang.SuppressWarnings("all")
        private String deviceManufacturer;
        @java.lang.SuppressWarnings("all")
        private StatusSessao status;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataLogin;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataLogout;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataExpiracao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime ultimaAtividade;
        @java.lang.SuppressWarnings("all")
        private Integer timeoutMinutos;
        @java.lang.SuppressWarnings("all")
        private Boolean mfaVerificado;
        @java.lang.SuppressWarnings("all")
        private Boolean biometricoVerificado;
        @java.lang.SuppressWarnings("all")
        private Boolean faceIdVerificado;
        @java.lang.SuppressWarnings("all")
        private Boolean touchIdVerificado;
        @java.lang.SuppressWarnings("all")
        private Boolean pinVerificado;
        @java.lang.SuppressWarnings("all")
        private Boolean localizacaoVerificada;
        @java.lang.SuppressWarnings("all")
        private Double latitude;
        @java.lang.SuppressWarnings("all")
        private Double longitude;
        @java.lang.SuppressWarnings("all")
        private Double precisaoGps;
        @java.lang.SuppressWarnings("all")
        private String enderecoLocalizacao;
        @java.lang.SuppressWarnings("all")
        private String redeWifi;
        @java.lang.SuppressWarnings("all")
        private String operadora;
        @java.lang.SuppressWarnings("all")
        private String tipoConexao;
        @java.lang.SuppressWarnings("all")
        private Integer velocidadeConexao;
        @java.lang.SuppressWarnings("all")
        private Integer tentativasFalha;
        @java.lang.SuppressWarnings("all")
        private Boolean bloqueado;
        @java.lang.SuppressWarnings("all")
        private String motivoBloqueio;
        @java.lang.SuppressWarnings("all")
        private Boolean pushNotificationsAtivas;
        @java.lang.SuppressWarnings("all")
        private Boolean backgroundRefreshHabilitado;
        @java.lang.SuppressWarnings("all")
        private Integer bateriaNivel;
        @java.lang.SuppressWarnings("all")
        private Long armazenamentoDisponivel;
        @java.lang.SuppressWarnings("all")
        private Long ramDisponivel;
        @java.lang.SuppressWarnings("all")
        private String atividadesRealizadas;
        @java.lang.SuppressWarnings("all")
        private String metadados;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versaoControle;

        @java.lang.SuppressWarnings("all")
        SessaoMobileBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder sessaoId(final String sessaoId) {
            this.sessaoId = sessaoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder dispositivoId(final String dispositivoId) {
            this.dispositivoId = dispositivoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder clienteId(final String clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder usuarioId(final String usuarioId) {
            this.usuarioId = usuarioId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder deviceToken(final String deviceToken) {
            this.deviceToken = deviceToken;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder ipAddress(final String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder userAgent(final String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder appVersion(final String appVersion) {
            this.appVersion = appVersion;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder osVersion(final String osVersion) {
            this.osVersion = osVersion;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder deviceModel(final String deviceModel) {
            this.deviceModel = deviceModel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder deviceManufacturer(final String deviceManufacturer) {
            this.deviceManufacturer = deviceManufacturer;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder status(final StatusSessao status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder dataLogin(final LocalDateTime dataLogin) {
            this.dataLogin = dataLogin;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder dataLogout(final LocalDateTime dataLogout) {
            this.dataLogout = dataLogout;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder dataExpiracao(final LocalDateTime dataExpiracao) {
            this.dataExpiracao = dataExpiracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder ultimaAtividade(final LocalDateTime ultimaAtividade) {
            this.ultimaAtividade = ultimaAtividade;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder timeoutMinutos(final Integer timeoutMinutos) {
            this.timeoutMinutos = timeoutMinutos;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder mfaVerificado(final Boolean mfaVerificado) {
            this.mfaVerificado = mfaVerificado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder biometricoVerificado(final Boolean biometricoVerificado) {
            this.biometricoVerificado = biometricoVerificado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder faceIdVerificado(final Boolean faceIdVerificado) {
            this.faceIdVerificado = faceIdVerificado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder touchIdVerificado(final Boolean touchIdVerificado) {
            this.touchIdVerificado = touchIdVerificado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder pinVerificado(final Boolean pinVerificado) {
            this.pinVerificado = pinVerificado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder localizacaoVerificada(final Boolean localizacaoVerificada) {
            this.localizacaoVerificada = localizacaoVerificada;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder latitude(final Double latitude) {
            this.latitude = latitude;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder longitude(final Double longitude) {
            this.longitude = longitude;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder precisaoGps(final Double precisaoGps) {
            this.precisaoGps = precisaoGps;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder enderecoLocalizacao(final String enderecoLocalizacao) {
            this.enderecoLocalizacao = enderecoLocalizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder redeWifi(final String redeWifi) {
            this.redeWifi = redeWifi;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder operadora(final String operadora) {
            this.operadora = operadora;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder tipoConexao(final String tipoConexao) {
            this.tipoConexao = tipoConexao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder velocidadeConexao(final Integer velocidadeConexao) {
            this.velocidadeConexao = velocidadeConexao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder tentativasFalha(final Integer tentativasFalha) {
            this.tentativasFalha = tentativasFalha;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder bloqueado(final Boolean bloqueado) {
            this.bloqueado = bloqueado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder motivoBloqueio(final String motivoBloqueio) {
            this.motivoBloqueio = motivoBloqueio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder pushNotificationsAtivas(final Boolean pushNotificationsAtivas) {
            this.pushNotificationsAtivas = pushNotificationsAtivas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder backgroundRefreshHabilitado(final Boolean backgroundRefreshHabilitado) {
            this.backgroundRefreshHabilitado = backgroundRefreshHabilitado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder bateriaNivel(final Integer bateriaNivel) {
            this.bateriaNivel = bateriaNivel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder armazenamentoDisponivel(final Long armazenamentoDisponivel) {
            this.armazenamentoDisponivel = armazenamentoDisponivel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder ramDisponivel(final Long ramDisponivel) {
            this.ramDisponivel = ramDisponivel;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder atividadesRealizadas(final String atividadesRealizadas) {
            this.atividadesRealizadas = atividadesRealizadas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder metadados(final String metadados) {
            this.metadados = metadados;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public SessaoMobile.SessaoMobileBuilder versaoControle(final Long versaoControle) {
            this.versaoControle = versaoControle;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public SessaoMobile build() {
            return new SessaoMobile(this.id, this.sessaoId, this.dispositivoId, this.clienteId, this.usuarioId, this.deviceToken, this.ipAddress, this.userAgent, this.appVersion, this.osVersion, this.deviceModel, this.deviceManufacturer, this.status, this.dataLogin, this.dataLogout, this.dataExpiracao, this.ultimaAtividade, this.timeoutMinutos, this.mfaVerificado, this.biometricoVerificado, this.faceIdVerificado, this.touchIdVerificado, this.pinVerificado, this.localizacaoVerificada, this.latitude, this.longitude, this.precisaoGps, this.enderecoLocalizacao, this.redeWifi, this.operadora, this.tipoConexao, this.velocidadeConexao, this.tentativasFalha, this.bloqueado, this.motivoBloqueio, this.pushNotificationsAtivas, this.backgroundRefreshHabilitado, this.bateriaNivel, this.armazenamentoDisponivel, this.ramDisponivel, this.atividadesRealizadas, this.metadados, this.dataCriacao, this.dataAtualizacao, this.versaoControle);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "SessaoMobile.SessaoMobileBuilder(id=" + this.id + ", sessaoId=" + this.sessaoId + ", dispositivoId=" + this.dispositivoId + ", clienteId=" + this.clienteId + ", usuarioId=" + this.usuarioId + ", deviceToken=" + this.deviceToken + ", ipAddress=" + this.ipAddress + ", userAgent=" + this.userAgent + ", appVersion=" + this.appVersion + ", osVersion=" + this.osVersion + ", deviceModel=" + this.deviceModel + ", deviceManufacturer=" + this.deviceManufacturer + ", status=" + this.status + ", dataLogin=" + this.dataLogin + ", dataLogout=" + this.dataLogout + ", dataExpiracao=" + this.dataExpiracao + ", ultimaAtividade=" + this.ultimaAtividade + ", timeoutMinutos=" + this.timeoutMinutos + ", mfaVerificado=" + this.mfaVerificado + ", biometricoVerificado=" + this.biometricoVerificado + ", faceIdVerificado=" + this.faceIdVerificado + ", touchIdVerificado=" + this.touchIdVerificado + ", pinVerificado=" + this.pinVerificado + ", localizacaoVerificada=" + this.localizacaoVerificada + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", precisaoGps=" + this.precisaoGps + ", enderecoLocalizacao=" + this.enderecoLocalizacao + ", redeWifi=" + this.redeWifi + ", operadora=" + this.operadora + ", tipoConexao=" + this.tipoConexao + ", velocidadeConexao=" + this.velocidadeConexao + ", tentativasFalha=" + this.tentativasFalha + ", bloqueado=" + this.bloqueado + ", motivoBloqueio=" + this.motivoBloqueio + ", pushNotificationsAtivas=" + this.pushNotificationsAtivas + ", backgroundRefreshHabilitado=" + this.backgroundRefreshHabilitado + ", bateriaNivel=" + this.bateriaNivel + ", armazenamentoDisponivel=" + this.armazenamentoDisponivel + ", ramDisponivel=" + this.ramDisponivel + ", atividadesRealizadas=" + this.atividadesRealizadas + ", metadados=" + this.metadados + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versaoControle=" + this.versaoControle + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static SessaoMobile.SessaoMobileBuilder builder() {
        return new SessaoMobile.SessaoMobileBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @java.lang.SuppressWarnings("all")
    public String getSessaoId() {
        return this.sessaoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getDispositivoId() {
        return this.dispositivoId;
    }

    @java.lang.SuppressWarnings("all")
    public String getClienteId() {
        return this.clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public String getUsuarioId() {
        return this.usuarioId;
    }

    @java.lang.SuppressWarnings("all")
    public String getDeviceToken() {
        return this.deviceToken;
    }

    @java.lang.SuppressWarnings("all")
    public String getIpAddress() {
        return this.ipAddress;
    }

    @java.lang.SuppressWarnings("all")
    public String getUserAgent() {
        return this.userAgent;
    }

    @java.lang.SuppressWarnings("all")
    public String getAppVersion() {
        return this.appVersion;
    }

    @java.lang.SuppressWarnings("all")
    public String getOsVersion() {
        return this.osVersion;
    }

    @java.lang.SuppressWarnings("all")
    public String getDeviceModel() {
        return this.deviceModel;
    }

    @java.lang.SuppressWarnings("all")
    public String getDeviceManufacturer() {
        return this.deviceManufacturer;
    }

    @java.lang.SuppressWarnings("all")
    public StatusSessao getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataLogin() {
        return this.dataLogin;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataLogout() {
        return this.dataLogout;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getUltimaAtividade() {
        return this.ultimaAtividade;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTimeoutMinutos() {
        return this.timeoutMinutos;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getMfaVerificado() {
        return this.mfaVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getBiometricoVerificado() {
        return this.biometricoVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getFaceIdVerificado() {
        return this.faceIdVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getTouchIdVerificado() {
        return this.touchIdVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPinVerificado() {
        return this.pinVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getLocalizacaoVerificada() {
        return this.localizacaoVerificada;
    }

    @java.lang.SuppressWarnings("all")
    public Double getLatitude() {
        return this.latitude;
    }

    @java.lang.SuppressWarnings("all")
    public Double getLongitude() {
        return this.longitude;
    }

    @java.lang.SuppressWarnings("all")
    public Double getPrecisaoGps() {
        return this.precisaoGps;
    }

    @java.lang.SuppressWarnings("all")
    public String getEnderecoLocalizacao() {
        return this.enderecoLocalizacao;
    }

    @java.lang.SuppressWarnings("all")
    public String getRedeWifi() {
        return this.redeWifi;
    }

    @java.lang.SuppressWarnings("all")
    public String getOperadora() {
        return this.operadora;
    }

    @java.lang.SuppressWarnings("all")
    public String getTipoConexao() {
        return this.tipoConexao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getVelocidadeConexao() {
        return this.velocidadeConexao;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getTentativasFalha() {
        return this.tentativasFalha;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getBloqueado() {
        return this.bloqueado;
    }

    @java.lang.SuppressWarnings("all")
    public String getMotivoBloqueio() {
        return this.motivoBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPushNotificationsAtivas() {
        return this.pushNotificationsAtivas;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getBackgroundRefreshHabilitado() {
        return this.backgroundRefreshHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public Integer getBateriaNivel() {
        return this.bateriaNivel;
    }

    @java.lang.SuppressWarnings("all")
    public Long getArmazenamentoDisponivel() {
        return this.armazenamentoDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public Long getRamDisponivel() {
        return this.ramDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public String getAtividadesRealizadas() {
        return this.atividadesRealizadas;
    }

    @java.lang.SuppressWarnings("all")
    public String getMetadados() {
        return this.metadados;
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
    public Long getVersaoControle() {
        return this.versaoControle;
    }

    @java.lang.SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @java.lang.SuppressWarnings("all")
    public void setSessaoId(final String sessaoId) {
        this.sessaoId = sessaoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setDispositivoId(final String dispositivoId) {
        this.dispositivoId = dispositivoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setClienteId(final String clienteId) {
        this.clienteId = clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setUsuarioId(final String usuarioId) {
        this.usuarioId = usuarioId;
    }

    @java.lang.SuppressWarnings("all")
    public void setDeviceToken(final String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @java.lang.SuppressWarnings("all")
    public void setIpAddress(final String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @java.lang.SuppressWarnings("all")
    public void setUserAgent(final String userAgent) {
        this.userAgent = userAgent;
    }

    @java.lang.SuppressWarnings("all")
    public void setAppVersion(final String appVersion) {
        this.appVersion = appVersion;
    }

    @java.lang.SuppressWarnings("all")
    public void setOsVersion(final String osVersion) {
        this.osVersion = osVersion;
    }

    @java.lang.SuppressWarnings("all")
    public void setDeviceModel(final String deviceModel) {
        this.deviceModel = deviceModel;
    }

    @java.lang.SuppressWarnings("all")
    public void setDeviceManufacturer(final String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusSessao status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataLogin(final LocalDateTime dataLogin) {
        this.dataLogin = dataLogin;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataLogout(final LocalDateTime dataLogout) {
        this.dataLogout = dataLogout;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setUltimaAtividade(final LocalDateTime ultimaAtividade) {
        this.ultimaAtividade = ultimaAtividade;
    }

    @java.lang.SuppressWarnings("all")
    public void setTimeoutMinutos(final Integer timeoutMinutos) {
        this.timeoutMinutos = timeoutMinutos;
    }

    @java.lang.SuppressWarnings("all")
    public void setMfaVerificado(final Boolean mfaVerificado) {
        this.mfaVerificado = mfaVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public void setBiometricoVerificado(final Boolean biometricoVerificado) {
        this.biometricoVerificado = biometricoVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public void setFaceIdVerificado(final Boolean faceIdVerificado) {
        this.faceIdVerificado = faceIdVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public void setTouchIdVerificado(final Boolean touchIdVerificado) {
        this.touchIdVerificado = touchIdVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public void setPinVerificado(final Boolean pinVerificado) {
        this.pinVerificado = pinVerificado;
    }

    @java.lang.SuppressWarnings("all")
    public void setLocalizacaoVerificada(final Boolean localizacaoVerificada) {
        this.localizacaoVerificada = localizacaoVerificada;
    }

    @java.lang.SuppressWarnings("all")
    public void setLatitude(final Double latitude) {
        this.latitude = latitude;
    }

    @java.lang.SuppressWarnings("all")
    public void setLongitude(final Double longitude) {
        this.longitude = longitude;
    }

    @java.lang.SuppressWarnings("all")
    public void setPrecisaoGps(final Double precisaoGps) {
        this.precisaoGps = precisaoGps;
    }

    @java.lang.SuppressWarnings("all")
    public void setEnderecoLocalizacao(final String enderecoLocalizacao) {
        this.enderecoLocalizacao = enderecoLocalizacao;
    }

    @java.lang.SuppressWarnings("all")
    public void setRedeWifi(final String redeWifi) {
        this.redeWifi = redeWifi;
    }

    @java.lang.SuppressWarnings("all")
    public void setOperadora(final String operadora) {
        this.operadora = operadora;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoConexao(final String tipoConexao) {
        this.tipoConexao = tipoConexao;
    }

    @java.lang.SuppressWarnings("all")
    public void setVelocidadeConexao(final Integer velocidadeConexao) {
        this.velocidadeConexao = velocidadeConexao;
    }

    @java.lang.SuppressWarnings("all")
    public void setTentativasFalha(final Integer tentativasFalha) {
        this.tentativasFalha = tentativasFalha;
    }

    @java.lang.SuppressWarnings("all")
    public void setBloqueado(final Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    @java.lang.SuppressWarnings("all")
    public void setMotivoBloqueio(final String motivoBloqueio) {
        this.motivoBloqueio = motivoBloqueio;
    }

    @java.lang.SuppressWarnings("all")
    public void setPushNotificationsAtivas(final Boolean pushNotificationsAtivas) {
        this.pushNotificationsAtivas = pushNotificationsAtivas;
    }

    @java.lang.SuppressWarnings("all")
    public void setBackgroundRefreshHabilitado(final Boolean backgroundRefreshHabilitado) {
        this.backgroundRefreshHabilitado = backgroundRefreshHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public void setBateriaNivel(final Integer bateriaNivel) {
        this.bateriaNivel = bateriaNivel;
    }

    @java.lang.SuppressWarnings("all")
    public void setArmazenamentoDisponivel(final Long armazenamentoDisponivel) {
        this.armazenamentoDisponivel = armazenamentoDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public void setRamDisponivel(final Long ramDisponivel) {
        this.ramDisponivel = ramDisponivel;
    }

    @java.lang.SuppressWarnings("all")
    public void setAtividadesRealizadas(final String atividadesRealizadas) {
        this.atividadesRealizadas = atividadesRealizadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setMetadados(final String metadados) {
        this.metadados = metadados;
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
    public void setVersaoControle(final Long versaoControle) {
        this.versaoControle = versaoControle;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SessaoMobile)) return false;
        final SessaoMobile other = (SessaoMobile) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$timeoutMinutos = this.getTimeoutMinutos();
        final java.lang.Object other$timeoutMinutos = other.getTimeoutMinutos();
        if (this$timeoutMinutos == null ? other$timeoutMinutos != null : !this$timeoutMinutos.equals(other$timeoutMinutos)) return false;
        final java.lang.Object this$mfaVerificado = this.getMfaVerificado();
        final java.lang.Object other$mfaVerificado = other.getMfaVerificado();
        if (this$mfaVerificado == null ? other$mfaVerificado != null : !this$mfaVerificado.equals(other$mfaVerificado)) return false;
        final java.lang.Object this$biometricoVerificado = this.getBiometricoVerificado();
        final java.lang.Object other$biometricoVerificado = other.getBiometricoVerificado();
        if (this$biometricoVerificado == null ? other$biometricoVerificado != null : !this$biometricoVerificado.equals(other$biometricoVerificado)) return false;
        final java.lang.Object this$faceIdVerificado = this.getFaceIdVerificado();
        final java.lang.Object other$faceIdVerificado = other.getFaceIdVerificado();
        if (this$faceIdVerificado == null ? other$faceIdVerificado != null : !this$faceIdVerificado.equals(other$faceIdVerificado)) return false;
        final java.lang.Object this$touchIdVerificado = this.getTouchIdVerificado();
        final java.lang.Object other$touchIdVerificado = other.getTouchIdVerificado();
        if (this$touchIdVerificado == null ? other$touchIdVerificado != null : !this$touchIdVerificado.equals(other$touchIdVerificado)) return false;
        final java.lang.Object this$pinVerificado = this.getPinVerificado();
        final java.lang.Object other$pinVerificado = other.getPinVerificado();
        if (this$pinVerificado == null ? other$pinVerificado != null : !this$pinVerificado.equals(other$pinVerificado)) return false;
        final java.lang.Object this$localizacaoVerificada = this.getLocalizacaoVerificada();
        final java.lang.Object other$localizacaoVerificada = other.getLocalizacaoVerificada();
        if (this$localizacaoVerificada == null ? other$localizacaoVerificada != null : !this$localizacaoVerificada.equals(other$localizacaoVerificada)) return false;
        final java.lang.Object this$latitude = this.getLatitude();
        final java.lang.Object other$latitude = other.getLatitude();
        if (this$latitude == null ? other$latitude != null : !this$latitude.equals(other$latitude)) return false;
        final java.lang.Object this$longitude = this.getLongitude();
        final java.lang.Object other$longitude = other.getLongitude();
        if (this$longitude == null ? other$longitude != null : !this$longitude.equals(other$longitude)) return false;
        final java.lang.Object this$precisaoGps = this.getPrecisaoGps();
        final java.lang.Object other$precisaoGps = other.getPrecisaoGps();
        if (this$precisaoGps == null ? other$precisaoGps != null : !this$precisaoGps.equals(other$precisaoGps)) return false;
        final java.lang.Object this$velocidadeConexao = this.getVelocidadeConexao();
        final java.lang.Object other$velocidadeConexao = other.getVelocidadeConexao();
        if (this$velocidadeConexao == null ? other$velocidadeConexao != null : !this$velocidadeConexao.equals(other$velocidadeConexao)) return false;
        final java.lang.Object this$tentativasFalha = this.getTentativasFalha();
        final java.lang.Object other$tentativasFalha = other.getTentativasFalha();
        if (this$tentativasFalha == null ? other$tentativasFalha != null : !this$tentativasFalha.equals(other$tentativasFalha)) return false;
        final java.lang.Object this$bloqueado = this.getBloqueado();
        final java.lang.Object other$bloqueado = other.getBloqueado();
        if (this$bloqueado == null ? other$bloqueado != null : !this$bloqueado.equals(other$bloqueado)) return false;
        final java.lang.Object this$pushNotificationsAtivas = this.getPushNotificationsAtivas();
        final java.lang.Object other$pushNotificationsAtivas = other.getPushNotificationsAtivas();
        if (this$pushNotificationsAtivas == null ? other$pushNotificationsAtivas != null : !this$pushNotificationsAtivas.equals(other$pushNotificationsAtivas)) return false;
        final java.lang.Object this$backgroundRefreshHabilitado = this.getBackgroundRefreshHabilitado();
        final java.lang.Object other$backgroundRefreshHabilitado = other.getBackgroundRefreshHabilitado();
        if (this$backgroundRefreshHabilitado == null ? other$backgroundRefreshHabilitado != null : !this$backgroundRefreshHabilitado.equals(other$backgroundRefreshHabilitado)) return false;
        final java.lang.Object this$bateriaNivel = this.getBateriaNivel();
        final java.lang.Object other$bateriaNivel = other.getBateriaNivel();
        if (this$bateriaNivel == null ? other$bateriaNivel != null : !this$bateriaNivel.equals(other$bateriaNivel)) return false;
        final java.lang.Object this$armazenamentoDisponivel = this.getArmazenamentoDisponivel();
        final java.lang.Object other$armazenamentoDisponivel = other.getArmazenamentoDisponivel();
        if (this$armazenamentoDisponivel == null ? other$armazenamentoDisponivel != null : !this$armazenamentoDisponivel.equals(other$armazenamentoDisponivel)) return false;
        final java.lang.Object this$ramDisponivel = this.getRamDisponivel();
        final java.lang.Object other$ramDisponivel = other.getRamDisponivel();
        if (this$ramDisponivel == null ? other$ramDisponivel != null : !this$ramDisponivel.equals(other$ramDisponivel)) return false;
        final java.lang.Object this$versaoControle = this.getVersaoControle();
        final java.lang.Object other$versaoControle = other.getVersaoControle();
        if (this$versaoControle == null ? other$versaoControle != null : !this$versaoControle.equals(other$versaoControle)) return false;
        final java.lang.Object this$sessaoId = this.getSessaoId();
        final java.lang.Object other$sessaoId = other.getSessaoId();
        if (this$sessaoId == null ? other$sessaoId != null : !this$sessaoId.equals(other$sessaoId)) return false;
        final java.lang.Object this$dispositivoId = this.getDispositivoId();
        final java.lang.Object other$dispositivoId = other.getDispositivoId();
        if (this$dispositivoId == null ? other$dispositivoId != null : !this$dispositivoId.equals(other$dispositivoId)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$usuarioId = this.getUsuarioId();
        final java.lang.Object other$usuarioId = other.getUsuarioId();
        if (this$usuarioId == null ? other$usuarioId != null : !this$usuarioId.equals(other$usuarioId)) return false;
        final java.lang.Object this$deviceToken = this.getDeviceToken();
        final java.lang.Object other$deviceToken = other.getDeviceToken();
        if (this$deviceToken == null ? other$deviceToken != null : !this$deviceToken.equals(other$deviceToken)) return false;
        final java.lang.Object this$ipAddress = this.getIpAddress();
        final java.lang.Object other$ipAddress = other.getIpAddress();
        if (this$ipAddress == null ? other$ipAddress != null : !this$ipAddress.equals(other$ipAddress)) return false;
        final java.lang.Object this$userAgent = this.getUserAgent();
        final java.lang.Object other$userAgent = other.getUserAgent();
        if (this$userAgent == null ? other$userAgent != null : !this$userAgent.equals(other$userAgent)) return false;
        final java.lang.Object this$appVersion = this.getAppVersion();
        final java.lang.Object other$appVersion = other.getAppVersion();
        if (this$appVersion == null ? other$appVersion != null : !this$appVersion.equals(other$appVersion)) return false;
        final java.lang.Object this$osVersion = this.getOsVersion();
        final java.lang.Object other$osVersion = other.getOsVersion();
        if (this$osVersion == null ? other$osVersion != null : !this$osVersion.equals(other$osVersion)) return false;
        final java.lang.Object this$deviceModel = this.getDeviceModel();
        final java.lang.Object other$deviceModel = other.getDeviceModel();
        if (this$deviceModel == null ? other$deviceModel != null : !this$deviceModel.equals(other$deviceModel)) return false;
        final java.lang.Object this$deviceManufacturer = this.getDeviceManufacturer();
        final java.lang.Object other$deviceManufacturer = other.getDeviceManufacturer();
        if (this$deviceManufacturer == null ? other$deviceManufacturer != null : !this$deviceManufacturer.equals(other$deviceManufacturer)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataLogin = this.getDataLogin();
        final java.lang.Object other$dataLogin = other.getDataLogin();
        if (this$dataLogin == null ? other$dataLogin != null : !this$dataLogin.equals(other$dataLogin)) return false;
        final java.lang.Object this$dataLogout = this.getDataLogout();
        final java.lang.Object other$dataLogout = other.getDataLogout();
        if (this$dataLogout == null ? other$dataLogout != null : !this$dataLogout.equals(other$dataLogout)) return false;
        final java.lang.Object this$dataExpiracao = this.getDataExpiracao();
        final java.lang.Object other$dataExpiracao = other.getDataExpiracao();
        if (this$dataExpiracao == null ? other$dataExpiracao != null : !this$dataExpiracao.equals(other$dataExpiracao)) return false;
        final java.lang.Object this$ultimaAtividade = this.getUltimaAtividade();
        final java.lang.Object other$ultimaAtividade = other.getUltimaAtividade();
        if (this$ultimaAtividade == null ? other$ultimaAtividade != null : !this$ultimaAtividade.equals(other$ultimaAtividade)) return false;
        final java.lang.Object this$enderecoLocalizacao = this.getEnderecoLocalizacao();
        final java.lang.Object other$enderecoLocalizacao = other.getEnderecoLocalizacao();
        if (this$enderecoLocalizacao == null ? other$enderecoLocalizacao != null : !this$enderecoLocalizacao.equals(other$enderecoLocalizacao)) return false;
        final java.lang.Object this$redeWifi = this.getRedeWifi();
        final java.lang.Object other$redeWifi = other.getRedeWifi();
        if (this$redeWifi == null ? other$redeWifi != null : !this$redeWifi.equals(other$redeWifi)) return false;
        final java.lang.Object this$operadora = this.getOperadora();
        final java.lang.Object other$operadora = other.getOperadora();
        if (this$operadora == null ? other$operadora != null : !this$operadora.equals(other$operadora)) return false;
        final java.lang.Object this$tipoConexao = this.getTipoConexao();
        final java.lang.Object other$tipoConexao = other.getTipoConexao();
        if (this$tipoConexao == null ? other$tipoConexao != null : !this$tipoConexao.equals(other$tipoConexao)) return false;
        final java.lang.Object this$motivoBloqueio = this.getMotivoBloqueio();
        final java.lang.Object other$motivoBloqueio = other.getMotivoBloqueio();
        if (this$motivoBloqueio == null ? other$motivoBloqueio != null : !this$motivoBloqueio.equals(other$motivoBloqueio)) return false;
        final java.lang.Object this$atividadesRealizadas = this.getAtividadesRealizadas();
        final java.lang.Object other$atividadesRealizadas = other.getAtividadesRealizadas();
        if (this$atividadesRealizadas == null ? other$atividadesRealizadas != null : !this$atividadesRealizadas.equals(other$atividadesRealizadas)) return false;
        final java.lang.Object this$metadados = this.getMetadados();
        final java.lang.Object other$metadados = other.getMetadados();
        if (this$metadados == null ? other$metadados != null : !this$metadados.equals(other$metadados)) return false;
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
        return other instanceof SessaoMobile;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $timeoutMinutos = this.getTimeoutMinutos();
        result = result * PRIME + ($timeoutMinutos == null ? 43 : $timeoutMinutos.hashCode());
        final java.lang.Object $mfaVerificado = this.getMfaVerificado();
        result = result * PRIME + ($mfaVerificado == null ? 43 : $mfaVerificado.hashCode());
        final java.lang.Object $biometricoVerificado = this.getBiometricoVerificado();
        result = result * PRIME + ($biometricoVerificado == null ? 43 : $biometricoVerificado.hashCode());
        final java.lang.Object $faceIdVerificado = this.getFaceIdVerificado();
        result = result * PRIME + ($faceIdVerificado == null ? 43 : $faceIdVerificado.hashCode());
        final java.lang.Object $touchIdVerificado = this.getTouchIdVerificado();
        result = result * PRIME + ($touchIdVerificado == null ? 43 : $touchIdVerificado.hashCode());
        final java.lang.Object $pinVerificado = this.getPinVerificado();
        result = result * PRIME + ($pinVerificado == null ? 43 : $pinVerificado.hashCode());
        final java.lang.Object $localizacaoVerificada = this.getLocalizacaoVerificada();
        result = result * PRIME + ($localizacaoVerificada == null ? 43 : $localizacaoVerificada.hashCode());
        final java.lang.Object $latitude = this.getLatitude();
        result = result * PRIME + ($latitude == null ? 43 : $latitude.hashCode());
        final java.lang.Object $longitude = this.getLongitude();
        result = result * PRIME + ($longitude == null ? 43 : $longitude.hashCode());
        final java.lang.Object $precisaoGps = this.getPrecisaoGps();
        result = result * PRIME + ($precisaoGps == null ? 43 : $precisaoGps.hashCode());
        final java.lang.Object $velocidadeConexao = this.getVelocidadeConexao();
        result = result * PRIME + ($velocidadeConexao == null ? 43 : $velocidadeConexao.hashCode());
        final java.lang.Object $tentativasFalha = this.getTentativasFalha();
        result = result * PRIME + ($tentativasFalha == null ? 43 : $tentativasFalha.hashCode());
        final java.lang.Object $bloqueado = this.getBloqueado();
        result = result * PRIME + ($bloqueado == null ? 43 : $bloqueado.hashCode());
        final java.lang.Object $pushNotificationsAtivas = this.getPushNotificationsAtivas();
        result = result * PRIME + ($pushNotificationsAtivas == null ? 43 : $pushNotificationsAtivas.hashCode());
        final java.lang.Object $backgroundRefreshHabilitado = this.getBackgroundRefreshHabilitado();
        result = result * PRIME + ($backgroundRefreshHabilitado == null ? 43 : $backgroundRefreshHabilitado.hashCode());
        final java.lang.Object $bateriaNivel = this.getBateriaNivel();
        result = result * PRIME + ($bateriaNivel == null ? 43 : $bateriaNivel.hashCode());
        final java.lang.Object $armazenamentoDisponivel = this.getArmazenamentoDisponivel();
        result = result * PRIME + ($armazenamentoDisponivel == null ? 43 : $armazenamentoDisponivel.hashCode());
        final java.lang.Object $ramDisponivel = this.getRamDisponivel();
        result = result * PRIME + ($ramDisponivel == null ? 43 : $ramDisponivel.hashCode());
        final java.lang.Object $versaoControle = this.getVersaoControle();
        result = result * PRIME + ($versaoControle == null ? 43 : $versaoControle.hashCode());
        final java.lang.Object $sessaoId = this.getSessaoId();
        result = result * PRIME + ($sessaoId == null ? 43 : $sessaoId.hashCode());
        final java.lang.Object $dispositivoId = this.getDispositivoId();
        result = result * PRIME + ($dispositivoId == null ? 43 : $dispositivoId.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $usuarioId = this.getUsuarioId();
        result = result * PRIME + ($usuarioId == null ? 43 : $usuarioId.hashCode());
        final java.lang.Object $deviceToken = this.getDeviceToken();
        result = result * PRIME + ($deviceToken == null ? 43 : $deviceToken.hashCode());
        final java.lang.Object $ipAddress = this.getIpAddress();
        result = result * PRIME + ($ipAddress == null ? 43 : $ipAddress.hashCode());
        final java.lang.Object $userAgent = this.getUserAgent();
        result = result * PRIME + ($userAgent == null ? 43 : $userAgent.hashCode());
        final java.lang.Object $appVersion = this.getAppVersion();
        result = result * PRIME + ($appVersion == null ? 43 : $appVersion.hashCode());
        final java.lang.Object $osVersion = this.getOsVersion();
        result = result * PRIME + ($osVersion == null ? 43 : $osVersion.hashCode());
        final java.lang.Object $deviceModel = this.getDeviceModel();
        result = result * PRIME + ($deviceModel == null ? 43 : $deviceModel.hashCode());
        final java.lang.Object $deviceManufacturer = this.getDeviceManufacturer();
        result = result * PRIME + ($deviceManufacturer == null ? 43 : $deviceManufacturer.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataLogin = this.getDataLogin();
        result = result * PRIME + ($dataLogin == null ? 43 : $dataLogin.hashCode());
        final java.lang.Object $dataLogout = this.getDataLogout();
        result = result * PRIME + ($dataLogout == null ? 43 : $dataLogout.hashCode());
        final java.lang.Object $dataExpiracao = this.getDataExpiracao();
        result = result * PRIME + ($dataExpiracao == null ? 43 : $dataExpiracao.hashCode());
        final java.lang.Object $ultimaAtividade = this.getUltimaAtividade();
        result = result * PRIME + ($ultimaAtividade == null ? 43 : $ultimaAtividade.hashCode());
        final java.lang.Object $enderecoLocalizacao = this.getEnderecoLocalizacao();
        result = result * PRIME + ($enderecoLocalizacao == null ? 43 : $enderecoLocalizacao.hashCode());
        final java.lang.Object $redeWifi = this.getRedeWifi();
        result = result * PRIME + ($redeWifi == null ? 43 : $redeWifi.hashCode());
        final java.lang.Object $operadora = this.getOperadora();
        result = result * PRIME + ($operadora == null ? 43 : $operadora.hashCode());
        final java.lang.Object $tipoConexao = this.getTipoConexao();
        result = result * PRIME + ($tipoConexao == null ? 43 : $tipoConexao.hashCode());
        final java.lang.Object $motivoBloqueio = this.getMotivoBloqueio();
        result = result * PRIME + ($motivoBloqueio == null ? 43 : $motivoBloqueio.hashCode());
        final java.lang.Object $atividadesRealizadas = this.getAtividadesRealizadas();
        result = result * PRIME + ($atividadesRealizadas == null ? 43 : $atividadesRealizadas.hashCode());
        final java.lang.Object $metadados = this.getMetadados();
        result = result * PRIME + ($metadados == null ? 43 : $metadados.hashCode());
        final java.lang.Object $dataCriacao = this.getDataCriacao();
        result = result * PRIME + ($dataCriacao == null ? 43 : $dataCriacao.hashCode());
        final java.lang.Object $dataAtualizacao = this.getDataAtualizacao();
        result = result * PRIME + ($dataAtualizacao == null ? 43 : $dataAtualizacao.hashCode());
        return result;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
        return "SessaoMobile(id=" + this.getId() + ", sessaoId=" + this.getSessaoId() + ", dispositivoId=" + this.getDispositivoId() + ", clienteId=" + this.getClienteId() + ", usuarioId=" + this.getUsuarioId() + ", deviceToken=" + this.getDeviceToken() + ", ipAddress=" + this.getIpAddress() + ", userAgent=" + this.getUserAgent() + ", appVersion=" + this.getAppVersion() + ", osVersion=" + this.getOsVersion() + ", deviceModel=" + this.getDeviceModel() + ", deviceManufacturer=" + this.getDeviceManufacturer() + ", status=" + this.getStatus() + ", dataLogin=" + this.getDataLogin() + ", dataLogout=" + this.getDataLogout() + ", dataExpiracao=" + this.getDataExpiracao() + ", ultimaAtividade=" + this.getUltimaAtividade() + ", timeoutMinutos=" + this.getTimeoutMinutos() + ", mfaVerificado=" + this.getMfaVerificado() + ", biometricoVerificado=" + this.getBiometricoVerificado() + ", faceIdVerificado=" + this.getFaceIdVerificado() + ", touchIdVerificado=" + this.getTouchIdVerificado() + ", pinVerificado=" + this.getPinVerificado() + ", localizacaoVerificada=" + this.getLocalizacaoVerificada() + ", latitude=" + this.getLatitude() + ", longitude=" + this.getLongitude() + ", precisaoGps=" + this.getPrecisaoGps() + ", enderecoLocalizacao=" + this.getEnderecoLocalizacao() + ", redeWifi=" + this.getRedeWifi() + ", operadora=" + this.getOperadora() + ", tipoConexao=" + this.getTipoConexao() + ", velocidadeConexao=" + this.getVelocidadeConexao() + ", tentativasFalha=" + this.getTentativasFalha() + ", bloqueado=" + this.getBloqueado() + ", motivoBloqueio=" + this.getMotivoBloqueio() + ", pushNotificationsAtivas=" + this.getPushNotificationsAtivas() + ", backgroundRefreshHabilitado=" + this.getBackgroundRefreshHabilitado() + ", bateriaNivel=" + this.getBateriaNivel() + ", armazenamentoDisponivel=" + this.getArmazenamentoDisponivel() + ", ramDisponivel=" + this.getRamDisponivel() + ", atividadesRealizadas=" + this.getAtividadesRealizadas() + ", metadados=" + this.getMetadados() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versaoControle=" + this.getVersaoControle() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public SessaoMobile() {
    }

    @java.lang.SuppressWarnings("all")
    public SessaoMobile(final Long id, final String sessaoId, final String dispositivoId, final String clienteId, final String usuarioId, final String deviceToken, final String ipAddress, final String userAgent, final String appVersion, final String osVersion, final String deviceModel, final String deviceManufacturer, final StatusSessao status, final LocalDateTime dataLogin, final LocalDateTime dataLogout, final LocalDateTime dataExpiracao, final LocalDateTime ultimaAtividade, final Integer timeoutMinutos, final Boolean mfaVerificado, final Boolean biometricoVerificado, final Boolean faceIdVerificado, final Boolean touchIdVerificado, final Boolean pinVerificado, final Boolean localizacaoVerificada, final Double latitude, final Double longitude, final Double precisaoGps, final String enderecoLocalizacao, final String redeWifi, final String operadora, final String tipoConexao, final Integer velocidadeConexao, final Integer tentativasFalha, final Boolean bloqueado, final String motivoBloqueio, final Boolean pushNotificationsAtivas, final Boolean backgroundRefreshHabilitado, final Integer bateriaNivel, final Long armazenamentoDisponivel, final Long ramDisponivel, final String atividadesRealizadas, final String metadados, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versaoControle) {
        this.id = id;
        this.sessaoId = sessaoId;
        this.dispositivoId = dispositivoId;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
        this.deviceToken = deviceToken;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.appVersion = appVersion;
        this.osVersion = osVersion;
        this.deviceModel = deviceModel;
        this.deviceManufacturer = deviceManufacturer;
        this.status = status;
        this.dataLogin = dataLogin;
        this.dataLogout = dataLogout;
        this.dataExpiracao = dataExpiracao;
        this.ultimaAtividade = ultimaAtividade;
        this.timeoutMinutos = timeoutMinutos;
        this.mfaVerificado = mfaVerificado;
        this.biometricoVerificado = biometricoVerificado;
        this.faceIdVerificado = faceIdVerificado;
        this.touchIdVerificado = touchIdVerificado;
        this.pinVerificado = pinVerificado;
        this.localizacaoVerificada = localizacaoVerificada;
        this.latitude = latitude;
        this.longitude = longitude;
        this.precisaoGps = precisaoGps;
        this.enderecoLocalizacao = enderecoLocalizacao;
        this.redeWifi = redeWifi;
        this.operadora = operadora;
        this.tipoConexao = tipoConexao;
        this.velocidadeConexao = velocidadeConexao;
        this.tentativasFalha = tentativasFalha;
        this.bloqueado = bloqueado;
        this.motivoBloqueio = motivoBloqueio;
        this.pushNotificationsAtivas = pushNotificationsAtivas;
        this.backgroundRefreshHabilitado = backgroundRefreshHabilitado;
        this.bateriaNivel = bateriaNivel;
        this.armazenamentoDisponivel = armazenamentoDisponivel;
        this.ramDisponivel = ramDisponivel;
        this.atividadesRealizadas = atividadesRealizadas;
        this.metadados = metadados;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versaoControle = versaoControle;
    }
}
