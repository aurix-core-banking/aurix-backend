package com.aurix.platform.platform.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa um dispositivo mobile cadastrado
 * 
 * Gerencia dispositivos móveis dos clientes para autenticação biométrica
 */
@Entity
@Table(name = "dispositivos_mobile", schema = "aurix")
public class DispositivoMobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dispositivo_id", unique = true, nullable = false, length = 100)
    private String dispositivoId;
    @Column(name = "cliente_id", nullable = false, length = 50)
    private String clienteId;
    @Column(name = "nome_dispositivo", length = 100)
    private String nomeDispositivo;
    @Column(name = "marca", length = 50)
    private String marca;
    @Column(name = "modelo", length = 100)
    private String modelo;
    @Column(name = "sistema_operacional", length = 50)
    private String sistemaOperacional;
    @Column(name = "versao_so", length = 50)
    private String versaoSO;
    @Column(name = "versao_app", length = 50)
    private String versaoApp;
    @Column(name = "device_token", length = 500)
    private String deviceToken;
    @Column(name = "device_fingerprint", length = 200)
    private String deviceFingerprint;
    @Column(name = "imei", length = 20)
    private String imei;
    @Column(name = "numero_telefone", length = 20)
    private String numeroTelefone;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dispositivo", nullable = false)
    private TipoDispositivo tipoDispositivo;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusDispositivo status;
    @Column(name = "biometrico_habilitado", nullable = false)
    private Boolean biometricoHabilitado;
    @Column(name = "face_id_habilitado")
    private Boolean faceIdHabilitado;
    @Column(name = "touch_id_habilitado")
    private Boolean touchIdHabilitado;
    @Column(name = "voice_id_habilitado")
    private Boolean voiceIdHabilitado;
    @Column(name = "iris_id_habilitado")
    private Boolean irisIdHabilitado;
    @Column(name = "pin_habilitado", nullable = false)
    private Boolean pinHabilitado;
    @Column(name = "nfc_habilitado")
    private Boolean nfcHabilitado;
    @Column(name = "gps_habilitado")
    private Boolean gpsHabilitado;
    @Column(name = "camera_habilitada")
    private Boolean cameraHabilitada;
    @Column(name = "notificacoes_push_habilitadas", nullable = false)
    private Boolean notificacoesPushHabilitadas;
    @Column(name = "data_registro", nullable = false)
    private LocalDateTime dataRegistro;
    @Column(name = "data_ultimo_acesso")
    private LocalDateTime dataUltimoAcesso;
    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;
    @Column(name = "localizacao_atual", length = 200)
    private String localizacaoAtual;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "precisao_gps")
    private Double precisaoGps;
    @Column(name = "tentativas_falha")
    private Integer tentativasFalha;
    @Column(name = "bloqueado")
    private Boolean bloqueado;
    @Column(name = "motivo_bloqueio", length = 200)
    private String motivoBloqueio;
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
     * Tipo de dispositivo
     */
    public enum TipoDispositivo {
        SMARTPHONE,  // Smartphone
        TABLET,  // Tablet
        WEARABLE,  // Relógio inteligente
        SMART_TV,  // Smart TV
        OUTROS // Outros tipos
        ;
    }


    /**
     * Status do dispositivo
     */
    public enum StatusDispositivo {
        ATIVO,  // Dispositivo ativo
        INATIVO,  // Dispositivo inativo
        BLOQUEADO,  // Dispositivo bloqueado
        SUSPENSO,  // Dispositivo suspenso
        EXPIRADO,  // Dispositivo expirado
        REMOVIDO // Dispositivo removido
        ;
    }


    @java.lang.SuppressWarnings("all")
    public static class DispositivoMobileBuilder {
        @java.lang.SuppressWarnings("all")
        private Long id;
        @java.lang.SuppressWarnings("all")
        private String dispositivoId;
        @java.lang.SuppressWarnings("all")
        private String clienteId;
        @java.lang.SuppressWarnings("all")
        private String nomeDispositivo;
        @java.lang.SuppressWarnings("all")
        private String marca;
        @java.lang.SuppressWarnings("all")
        private String modelo;
        @java.lang.SuppressWarnings("all")
        private String sistemaOperacional;
        @java.lang.SuppressWarnings("all")
        private String versaoSO;
        @java.lang.SuppressWarnings("all")
        private String versaoApp;
        @java.lang.SuppressWarnings("all")
        private String deviceToken;
        @java.lang.SuppressWarnings("all")
        private String deviceFingerprint;
        @java.lang.SuppressWarnings("all")
        private String imei;
        @java.lang.SuppressWarnings("all")
        private String numeroTelefone;
        @java.lang.SuppressWarnings("all")
        private TipoDispositivo tipoDispositivo;
        @java.lang.SuppressWarnings("all")
        private StatusDispositivo status;
        @java.lang.SuppressWarnings("all")
        private Boolean biometricoHabilitado;
        @java.lang.SuppressWarnings("all")
        private Boolean faceIdHabilitado;
        @java.lang.SuppressWarnings("all")
        private Boolean touchIdHabilitado;
        @java.lang.SuppressWarnings("all")
        private Boolean voiceIdHabilitado;
        @java.lang.SuppressWarnings("all")
        private Boolean irisIdHabilitado;
        @java.lang.SuppressWarnings("all")
        private Boolean pinHabilitado;
        @java.lang.SuppressWarnings("all")
        private Boolean nfcHabilitado;
        @java.lang.SuppressWarnings("all")
        private Boolean gpsHabilitado;
        @java.lang.SuppressWarnings("all")
        private Boolean cameraHabilitada;
        @java.lang.SuppressWarnings("all")
        private Boolean notificacoesPushHabilitadas;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataRegistro;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataUltimoAcesso;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataExpiracao;
        @java.lang.SuppressWarnings("all")
        private String localizacaoAtual;
        @java.lang.SuppressWarnings("all")
        private Double latitude;
        @java.lang.SuppressWarnings("all")
        private Double longitude;
        @java.lang.SuppressWarnings("all")
        private Double precisaoGps;
        @java.lang.SuppressWarnings("all")
        private Integer tentativasFalha;
        @java.lang.SuppressWarnings("all")
        private Boolean bloqueado;
        @java.lang.SuppressWarnings("all")
        private String motivoBloqueio;
        @java.lang.SuppressWarnings("all")
        private String metadados;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataCriacao;
        @java.lang.SuppressWarnings("all")
        private LocalDateTime dataAtualizacao;
        @java.lang.SuppressWarnings("all")
        private Long versaoControle;

        @java.lang.SuppressWarnings("all")
        DispositivoMobileBuilder() {
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder dispositivoId(final String dispositivoId) {
            this.dispositivoId = dispositivoId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder clienteId(final String clienteId) {
            this.clienteId = clienteId;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder nomeDispositivo(final String nomeDispositivo) {
            this.nomeDispositivo = nomeDispositivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder marca(final String marca) {
            this.marca = marca;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder modelo(final String modelo) {
            this.modelo = modelo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder sistemaOperacional(final String sistemaOperacional) {
            this.sistemaOperacional = sistemaOperacional;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder versaoSO(final String versaoSO) {
            this.versaoSO = versaoSO;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder versaoApp(final String versaoApp) {
            this.versaoApp = versaoApp;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder deviceToken(final String deviceToken) {
            this.deviceToken = deviceToken;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder deviceFingerprint(final String deviceFingerprint) {
            this.deviceFingerprint = deviceFingerprint;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder imei(final String imei) {
            this.imei = imei;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder numeroTelefone(final String numeroTelefone) {
            this.numeroTelefone = numeroTelefone;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder tipoDispositivo(final TipoDispositivo tipoDispositivo) {
            this.tipoDispositivo = tipoDispositivo;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder status(final StatusDispositivo status) {
            this.status = status;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder biometricoHabilitado(final Boolean biometricoHabilitado) {
            this.biometricoHabilitado = biometricoHabilitado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder faceIdHabilitado(final Boolean faceIdHabilitado) {
            this.faceIdHabilitado = faceIdHabilitado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder touchIdHabilitado(final Boolean touchIdHabilitado) {
            this.touchIdHabilitado = touchIdHabilitado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder voiceIdHabilitado(final Boolean voiceIdHabilitado) {
            this.voiceIdHabilitado = voiceIdHabilitado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder irisIdHabilitado(final Boolean irisIdHabilitado) {
            this.irisIdHabilitado = irisIdHabilitado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder pinHabilitado(final Boolean pinHabilitado) {
            this.pinHabilitado = pinHabilitado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder nfcHabilitado(final Boolean nfcHabilitado) {
            this.nfcHabilitado = nfcHabilitado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder gpsHabilitado(final Boolean gpsHabilitado) {
            this.gpsHabilitado = gpsHabilitado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder cameraHabilitada(final Boolean cameraHabilitada) {
            this.cameraHabilitada = cameraHabilitada;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder notificacoesPushHabilitadas(final Boolean notificacoesPushHabilitadas) {
            this.notificacoesPushHabilitadas = notificacoesPushHabilitadas;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder dataRegistro(final LocalDateTime dataRegistro) {
            this.dataRegistro = dataRegistro;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder dataUltimoAcesso(final LocalDateTime dataUltimoAcesso) {
            this.dataUltimoAcesso = dataUltimoAcesso;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder dataExpiracao(final LocalDateTime dataExpiracao) {
            this.dataExpiracao = dataExpiracao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder localizacaoAtual(final String localizacaoAtual) {
            this.localizacaoAtual = localizacaoAtual;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder latitude(final Double latitude) {
            this.latitude = latitude;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder longitude(final Double longitude) {
            this.longitude = longitude;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder precisaoGps(final Double precisaoGps) {
            this.precisaoGps = precisaoGps;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder tentativasFalha(final Integer tentativasFalha) {
            this.tentativasFalha = tentativasFalha;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder bloqueado(final Boolean bloqueado) {
            this.bloqueado = bloqueado;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder motivoBloqueio(final String motivoBloqueio) {
            this.motivoBloqueio = motivoBloqueio;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder metadados(final String metadados) {
            this.metadados = metadados;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder dataCriacao(final LocalDateTime dataCriacao) {
            this.dataCriacao = dataCriacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder dataAtualizacao(final LocalDateTime dataAtualizacao) {
            this.dataAtualizacao = dataAtualizacao;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @java.lang.SuppressWarnings("all")
        public DispositivoMobile.DispositivoMobileBuilder versaoControle(final Long versaoControle) {
            this.versaoControle = versaoControle;
            return this;
        }

        @java.lang.SuppressWarnings("all")
        public DispositivoMobile build() {
            return new DispositivoMobile(this.id, this.dispositivoId, this.clienteId, this.nomeDispositivo, this.marca, this.modelo, this.sistemaOperacional, this.versaoSO, this.versaoApp, this.deviceToken, this.deviceFingerprint, this.imei, this.numeroTelefone, this.tipoDispositivo, this.status, this.biometricoHabilitado, this.faceIdHabilitado, this.touchIdHabilitado, this.voiceIdHabilitado, this.irisIdHabilitado, this.pinHabilitado, this.nfcHabilitado, this.gpsHabilitado, this.cameraHabilitada, this.notificacoesPushHabilitadas, this.dataRegistro, this.dataUltimoAcesso, this.dataExpiracao, this.localizacaoAtual, this.latitude, this.longitude, this.precisaoGps, this.tentativasFalha, this.bloqueado, this.motivoBloqueio, this.metadados, this.dataCriacao, this.dataAtualizacao, this.versaoControle);
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("all")
        public java.lang.String toString() {
            return "DispositivoMobile.DispositivoMobileBuilder(id=" + this.id + ", dispositivoId=" + this.dispositivoId + ", clienteId=" + this.clienteId + ", nomeDispositivo=" + this.nomeDispositivo + ", marca=" + this.marca + ", modelo=" + this.modelo + ", sistemaOperacional=" + this.sistemaOperacional + ", versaoSO=" + this.versaoSO + ", versaoApp=" + this.versaoApp + ", deviceToken=" + this.deviceToken + ", deviceFingerprint=" + this.deviceFingerprint + ", imei=" + this.imei + ", numeroTelefone=" + this.numeroTelefone + ", tipoDispositivo=" + this.tipoDispositivo + ", status=" + this.status + ", biometricoHabilitado=" + this.biometricoHabilitado + ", faceIdHabilitado=" + this.faceIdHabilitado + ", touchIdHabilitado=" + this.touchIdHabilitado + ", voiceIdHabilitado=" + this.voiceIdHabilitado + ", irisIdHabilitado=" + this.irisIdHabilitado + ", pinHabilitado=" + this.pinHabilitado + ", nfcHabilitado=" + this.nfcHabilitado + ", gpsHabilitado=" + this.gpsHabilitado + ", cameraHabilitada=" + this.cameraHabilitada + ", notificacoesPushHabilitadas=" + this.notificacoesPushHabilitadas + ", dataRegistro=" + this.dataRegistro + ", dataUltimoAcesso=" + this.dataUltimoAcesso + ", dataExpiracao=" + this.dataExpiracao + ", localizacaoAtual=" + this.localizacaoAtual + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", precisaoGps=" + this.precisaoGps + ", tentativasFalha=" + this.tentativasFalha + ", bloqueado=" + this.bloqueado + ", motivoBloqueio=" + this.motivoBloqueio + ", metadados=" + this.metadados + ", dataCriacao=" + this.dataCriacao + ", dataAtualizacao=" + this.dataAtualizacao + ", versaoControle=" + this.versaoControle + ")";
        }
    }

    @java.lang.SuppressWarnings("all")
    public static DispositivoMobile.DispositivoMobileBuilder builder() {
        return new DispositivoMobile.DispositivoMobileBuilder();
    }

    @java.lang.SuppressWarnings("all")
    public Long getId() {
        return this.id;
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
    public String getNomeDispositivo() {
        return this.nomeDispositivo;
    }

    @java.lang.SuppressWarnings("all")
    public String getMarca() {
        return this.marca;
    }

    @java.lang.SuppressWarnings("all")
    public String getModelo() {
        return this.modelo;
    }

    @java.lang.SuppressWarnings("all")
    public String getSistemaOperacional() {
        return this.sistemaOperacional;
    }

    @java.lang.SuppressWarnings("all")
    public String getVersaoSO() {
        return this.versaoSO;
    }

    @java.lang.SuppressWarnings("all")
    public String getVersaoApp() {
        return this.versaoApp;
    }

    @java.lang.SuppressWarnings("all")
    public String getDeviceToken() {
        return this.deviceToken;
    }

    @java.lang.SuppressWarnings("all")
    public String getDeviceFingerprint() {
        return this.deviceFingerprint;
    }

    @java.lang.SuppressWarnings("all")
    public String getImei() {
        return this.imei;
    }

    @java.lang.SuppressWarnings("all")
    public String getNumeroTelefone() {
        return this.numeroTelefone;
    }

    @java.lang.SuppressWarnings("all")
    public TipoDispositivo getTipoDispositivo() {
        return this.tipoDispositivo;
    }

    @java.lang.SuppressWarnings("all")
    public StatusDispositivo getStatus() {
        return this.status;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getBiometricoHabilitado() {
        return this.biometricoHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getFaceIdHabilitado() {
        return this.faceIdHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getTouchIdHabilitado() {
        return this.touchIdHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getVoiceIdHabilitado() {
        return this.voiceIdHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getIrisIdHabilitado() {
        return this.irisIdHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getPinHabilitado() {
        return this.pinHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getNfcHabilitado() {
        return this.nfcHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getGpsHabilitado() {
        return this.gpsHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getCameraHabilitada() {
        return this.cameraHabilitada;
    }

    @java.lang.SuppressWarnings("all")
    public Boolean getNotificacoesPushHabilitadas() {
        return this.notificacoesPushHabilitadas;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataRegistro() {
        return this.dataRegistro;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataUltimoAcesso() {
        return this.dataUltimoAcesso;
    }

    @java.lang.SuppressWarnings("all")
    public LocalDateTime getDataExpiracao() {
        return this.dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public String getLocalizacaoAtual() {
        return this.localizacaoAtual;
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
    public void setDispositivoId(final String dispositivoId) {
        this.dispositivoId = dispositivoId;
    }

    @java.lang.SuppressWarnings("all")
    public void setClienteId(final String clienteId) {
        this.clienteId = clienteId;
    }

    @java.lang.SuppressWarnings("all")
    public void setNomeDispositivo(final String nomeDispositivo) {
        this.nomeDispositivo = nomeDispositivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setMarca(final String marca) {
        this.marca = marca;
    }

    @java.lang.SuppressWarnings("all")
    public void setModelo(final String modelo) {
        this.modelo = modelo;
    }

    @java.lang.SuppressWarnings("all")
    public void setSistemaOperacional(final String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    @java.lang.SuppressWarnings("all")
    public void setVersaoSO(final String versaoSO) {
        this.versaoSO = versaoSO;
    }

    @java.lang.SuppressWarnings("all")
    public void setVersaoApp(final String versaoApp) {
        this.versaoApp = versaoApp;
    }

    @java.lang.SuppressWarnings("all")
    public void setDeviceToken(final String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @java.lang.SuppressWarnings("all")
    public void setDeviceFingerprint(final String deviceFingerprint) {
        this.deviceFingerprint = deviceFingerprint;
    }

    @java.lang.SuppressWarnings("all")
    public void setImei(final String imei) {
        this.imei = imei;
    }

    @java.lang.SuppressWarnings("all")
    public void setNumeroTelefone(final String numeroTelefone) {
        this.numeroTelefone = numeroTelefone;
    }

    @java.lang.SuppressWarnings("all")
    public void setTipoDispositivo(final TipoDispositivo tipoDispositivo) {
        this.tipoDispositivo = tipoDispositivo;
    }

    @java.lang.SuppressWarnings("all")
    public void setStatus(final StatusDispositivo status) {
        this.status = status;
    }

    @java.lang.SuppressWarnings("all")
    public void setBiometricoHabilitado(final Boolean biometricoHabilitado) {
        this.biometricoHabilitado = biometricoHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public void setFaceIdHabilitado(final Boolean faceIdHabilitado) {
        this.faceIdHabilitado = faceIdHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public void setTouchIdHabilitado(final Boolean touchIdHabilitado) {
        this.touchIdHabilitado = touchIdHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public void setVoiceIdHabilitado(final Boolean voiceIdHabilitado) {
        this.voiceIdHabilitado = voiceIdHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public void setIrisIdHabilitado(final Boolean irisIdHabilitado) {
        this.irisIdHabilitado = irisIdHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public void setPinHabilitado(final Boolean pinHabilitado) {
        this.pinHabilitado = pinHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public void setNfcHabilitado(final Boolean nfcHabilitado) {
        this.nfcHabilitado = nfcHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public void setGpsHabilitado(final Boolean gpsHabilitado) {
        this.gpsHabilitado = gpsHabilitado;
    }

    @java.lang.SuppressWarnings("all")
    public void setCameraHabilitada(final Boolean cameraHabilitada) {
        this.cameraHabilitada = cameraHabilitada;
    }

    @java.lang.SuppressWarnings("all")
    public void setNotificacoesPushHabilitadas(final Boolean notificacoesPushHabilitadas) {
        this.notificacoesPushHabilitadas = notificacoesPushHabilitadas;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataRegistro(final LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataUltimoAcesso(final LocalDateTime dataUltimoAcesso) {
        this.dataUltimoAcesso = dataUltimoAcesso;
    }

    @java.lang.SuppressWarnings("all")
    public void setDataExpiracao(final LocalDateTime dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    @java.lang.SuppressWarnings("all")
    public void setLocalizacaoAtual(final String localizacaoAtual) {
        this.localizacaoAtual = localizacaoAtual;
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
        if (!(o instanceof DispositivoMobile)) return false;
        final DispositivoMobile other = (DispositivoMobile) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$biometricoHabilitado = this.getBiometricoHabilitado();
        final java.lang.Object other$biometricoHabilitado = other.getBiometricoHabilitado();
        if (this$biometricoHabilitado == null ? other$biometricoHabilitado != null : !this$biometricoHabilitado.equals(other$biometricoHabilitado)) return false;
        final java.lang.Object this$faceIdHabilitado = this.getFaceIdHabilitado();
        final java.lang.Object other$faceIdHabilitado = other.getFaceIdHabilitado();
        if (this$faceIdHabilitado == null ? other$faceIdHabilitado != null : !this$faceIdHabilitado.equals(other$faceIdHabilitado)) return false;
        final java.lang.Object this$touchIdHabilitado = this.getTouchIdHabilitado();
        final java.lang.Object other$touchIdHabilitado = other.getTouchIdHabilitado();
        if (this$touchIdHabilitado == null ? other$touchIdHabilitado != null : !this$touchIdHabilitado.equals(other$touchIdHabilitado)) return false;
        final java.lang.Object this$voiceIdHabilitado = this.getVoiceIdHabilitado();
        final java.lang.Object other$voiceIdHabilitado = other.getVoiceIdHabilitado();
        if (this$voiceIdHabilitado == null ? other$voiceIdHabilitado != null : !this$voiceIdHabilitado.equals(other$voiceIdHabilitado)) return false;
        final java.lang.Object this$irisIdHabilitado = this.getIrisIdHabilitado();
        final java.lang.Object other$irisIdHabilitado = other.getIrisIdHabilitado();
        if (this$irisIdHabilitado == null ? other$irisIdHabilitado != null : !this$irisIdHabilitado.equals(other$irisIdHabilitado)) return false;
        final java.lang.Object this$pinHabilitado = this.getPinHabilitado();
        final java.lang.Object other$pinHabilitado = other.getPinHabilitado();
        if (this$pinHabilitado == null ? other$pinHabilitado != null : !this$pinHabilitado.equals(other$pinHabilitado)) return false;
        final java.lang.Object this$nfcHabilitado = this.getNfcHabilitado();
        final java.lang.Object other$nfcHabilitado = other.getNfcHabilitado();
        if (this$nfcHabilitado == null ? other$nfcHabilitado != null : !this$nfcHabilitado.equals(other$nfcHabilitado)) return false;
        final java.lang.Object this$gpsHabilitado = this.getGpsHabilitado();
        final java.lang.Object other$gpsHabilitado = other.getGpsHabilitado();
        if (this$gpsHabilitado == null ? other$gpsHabilitado != null : !this$gpsHabilitado.equals(other$gpsHabilitado)) return false;
        final java.lang.Object this$cameraHabilitada = this.getCameraHabilitada();
        final java.lang.Object other$cameraHabilitada = other.getCameraHabilitada();
        if (this$cameraHabilitada == null ? other$cameraHabilitada != null : !this$cameraHabilitada.equals(other$cameraHabilitada)) return false;
        final java.lang.Object this$notificacoesPushHabilitadas = this.getNotificacoesPushHabilitadas();
        final java.lang.Object other$notificacoesPushHabilitadas = other.getNotificacoesPushHabilitadas();
        if (this$notificacoesPushHabilitadas == null ? other$notificacoesPushHabilitadas != null : !this$notificacoesPushHabilitadas.equals(other$notificacoesPushHabilitadas)) return false;
        final java.lang.Object this$latitude = this.getLatitude();
        final java.lang.Object other$latitude = other.getLatitude();
        if (this$latitude == null ? other$latitude != null : !this$latitude.equals(other$latitude)) return false;
        final java.lang.Object this$longitude = this.getLongitude();
        final java.lang.Object other$longitude = other.getLongitude();
        if (this$longitude == null ? other$longitude != null : !this$longitude.equals(other$longitude)) return false;
        final java.lang.Object this$precisaoGps = this.getPrecisaoGps();
        final java.lang.Object other$precisaoGps = other.getPrecisaoGps();
        if (this$precisaoGps == null ? other$precisaoGps != null : !this$precisaoGps.equals(other$precisaoGps)) return false;
        final java.lang.Object this$tentativasFalha = this.getTentativasFalha();
        final java.lang.Object other$tentativasFalha = other.getTentativasFalha();
        if (this$tentativasFalha == null ? other$tentativasFalha != null : !this$tentativasFalha.equals(other$tentativasFalha)) return false;
        final java.lang.Object this$bloqueado = this.getBloqueado();
        final java.lang.Object other$bloqueado = other.getBloqueado();
        if (this$bloqueado == null ? other$bloqueado != null : !this$bloqueado.equals(other$bloqueado)) return false;
        final java.lang.Object this$versaoControle = this.getVersaoControle();
        final java.lang.Object other$versaoControle = other.getVersaoControle();
        if (this$versaoControle == null ? other$versaoControle != null : !this$versaoControle.equals(other$versaoControle)) return false;
        final java.lang.Object this$dispositivoId = this.getDispositivoId();
        final java.lang.Object other$dispositivoId = other.getDispositivoId();
        if (this$dispositivoId == null ? other$dispositivoId != null : !this$dispositivoId.equals(other$dispositivoId)) return false;
        final java.lang.Object this$clienteId = this.getClienteId();
        final java.lang.Object other$clienteId = other.getClienteId();
        if (this$clienteId == null ? other$clienteId != null : !this$clienteId.equals(other$clienteId)) return false;
        final java.lang.Object this$nomeDispositivo = this.getNomeDispositivo();
        final java.lang.Object other$nomeDispositivo = other.getNomeDispositivo();
        if (this$nomeDispositivo == null ? other$nomeDispositivo != null : !this$nomeDispositivo.equals(other$nomeDispositivo)) return false;
        final java.lang.Object this$marca = this.getMarca();
        final java.lang.Object other$marca = other.getMarca();
        if (this$marca == null ? other$marca != null : !this$marca.equals(other$marca)) return false;
        final java.lang.Object this$modelo = this.getModelo();
        final java.lang.Object other$modelo = other.getModelo();
        if (this$modelo == null ? other$modelo != null : !this$modelo.equals(other$modelo)) return false;
        final java.lang.Object this$sistemaOperacional = this.getSistemaOperacional();
        final java.lang.Object other$sistemaOperacional = other.getSistemaOperacional();
        if (this$sistemaOperacional == null ? other$sistemaOperacional != null : !this$sistemaOperacional.equals(other$sistemaOperacional)) return false;
        final java.lang.Object this$versaoSO = this.getVersaoSO();
        final java.lang.Object other$versaoSO = other.getVersaoSO();
        if (this$versaoSO == null ? other$versaoSO != null : !this$versaoSO.equals(other$versaoSO)) return false;
        final java.lang.Object this$versaoApp = this.getVersaoApp();
        final java.lang.Object other$versaoApp = other.getVersaoApp();
        if (this$versaoApp == null ? other$versaoApp != null : !this$versaoApp.equals(other$versaoApp)) return false;
        final java.lang.Object this$deviceToken = this.getDeviceToken();
        final java.lang.Object other$deviceToken = other.getDeviceToken();
        if (this$deviceToken == null ? other$deviceToken != null : !this$deviceToken.equals(other$deviceToken)) return false;
        final java.lang.Object this$deviceFingerprint = this.getDeviceFingerprint();
        final java.lang.Object other$deviceFingerprint = other.getDeviceFingerprint();
        if (this$deviceFingerprint == null ? other$deviceFingerprint != null : !this$deviceFingerprint.equals(other$deviceFingerprint)) return false;
        final java.lang.Object this$imei = this.getImei();
        final java.lang.Object other$imei = other.getImei();
        if (this$imei == null ? other$imei != null : !this$imei.equals(other$imei)) return false;
        final java.lang.Object this$numeroTelefone = this.getNumeroTelefone();
        final java.lang.Object other$numeroTelefone = other.getNumeroTelefone();
        if (this$numeroTelefone == null ? other$numeroTelefone != null : !this$numeroTelefone.equals(other$numeroTelefone)) return false;
        final java.lang.Object this$tipoDispositivo = this.getTipoDispositivo();
        final java.lang.Object other$tipoDispositivo = other.getTipoDispositivo();
        if (this$tipoDispositivo == null ? other$tipoDispositivo != null : !this$tipoDispositivo.equals(other$tipoDispositivo)) return false;
        final java.lang.Object this$status = this.getStatus();
        final java.lang.Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final java.lang.Object this$dataRegistro = this.getDataRegistro();
        final java.lang.Object other$dataRegistro = other.getDataRegistro();
        if (this$dataRegistro == null ? other$dataRegistro != null : !this$dataRegistro.equals(other$dataRegistro)) return false;
        final java.lang.Object this$dataUltimoAcesso = this.getDataUltimoAcesso();
        final java.lang.Object other$dataUltimoAcesso = other.getDataUltimoAcesso();
        if (this$dataUltimoAcesso == null ? other$dataUltimoAcesso != null : !this$dataUltimoAcesso.equals(other$dataUltimoAcesso)) return false;
        final java.lang.Object this$dataExpiracao = this.getDataExpiracao();
        final java.lang.Object other$dataExpiracao = other.getDataExpiracao();
        if (this$dataExpiracao == null ? other$dataExpiracao != null : !this$dataExpiracao.equals(other$dataExpiracao)) return false;
        final java.lang.Object this$localizacaoAtual = this.getLocalizacaoAtual();
        final java.lang.Object other$localizacaoAtual = other.getLocalizacaoAtual();
        if (this$localizacaoAtual == null ? other$localizacaoAtual != null : !this$localizacaoAtual.equals(other$localizacaoAtual)) return false;
        final java.lang.Object this$motivoBloqueio = this.getMotivoBloqueio();
        final java.lang.Object other$motivoBloqueio = other.getMotivoBloqueio();
        if (this$motivoBloqueio == null ? other$motivoBloqueio != null : !this$motivoBloqueio.equals(other$motivoBloqueio)) return false;
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
        return other instanceof DispositivoMobile;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $biometricoHabilitado = this.getBiometricoHabilitado();
        result = result * PRIME + ($biometricoHabilitado == null ? 43 : $biometricoHabilitado.hashCode());
        final java.lang.Object $faceIdHabilitado = this.getFaceIdHabilitado();
        result = result * PRIME + ($faceIdHabilitado == null ? 43 : $faceIdHabilitado.hashCode());
        final java.lang.Object $touchIdHabilitado = this.getTouchIdHabilitado();
        result = result * PRIME + ($touchIdHabilitado == null ? 43 : $touchIdHabilitado.hashCode());
        final java.lang.Object $voiceIdHabilitado = this.getVoiceIdHabilitado();
        result = result * PRIME + ($voiceIdHabilitado == null ? 43 : $voiceIdHabilitado.hashCode());
        final java.lang.Object $irisIdHabilitado = this.getIrisIdHabilitado();
        result = result * PRIME + ($irisIdHabilitado == null ? 43 : $irisIdHabilitado.hashCode());
        final java.lang.Object $pinHabilitado = this.getPinHabilitado();
        result = result * PRIME + ($pinHabilitado == null ? 43 : $pinHabilitado.hashCode());
        final java.lang.Object $nfcHabilitado = this.getNfcHabilitado();
        result = result * PRIME + ($nfcHabilitado == null ? 43 : $nfcHabilitado.hashCode());
        final java.lang.Object $gpsHabilitado = this.getGpsHabilitado();
        result = result * PRIME + ($gpsHabilitado == null ? 43 : $gpsHabilitado.hashCode());
        final java.lang.Object $cameraHabilitada = this.getCameraHabilitada();
        result = result * PRIME + ($cameraHabilitada == null ? 43 : $cameraHabilitada.hashCode());
        final java.lang.Object $notificacoesPushHabilitadas = this.getNotificacoesPushHabilitadas();
        result = result * PRIME + ($notificacoesPushHabilitadas == null ? 43 : $notificacoesPushHabilitadas.hashCode());
        final java.lang.Object $latitude = this.getLatitude();
        result = result * PRIME + ($latitude == null ? 43 : $latitude.hashCode());
        final java.lang.Object $longitude = this.getLongitude();
        result = result * PRIME + ($longitude == null ? 43 : $longitude.hashCode());
        final java.lang.Object $precisaoGps = this.getPrecisaoGps();
        result = result * PRIME + ($precisaoGps == null ? 43 : $precisaoGps.hashCode());
        final java.lang.Object $tentativasFalha = this.getTentativasFalha();
        result = result * PRIME + ($tentativasFalha == null ? 43 : $tentativasFalha.hashCode());
        final java.lang.Object $bloqueado = this.getBloqueado();
        result = result * PRIME + ($bloqueado == null ? 43 : $bloqueado.hashCode());
        final java.lang.Object $versaoControle = this.getVersaoControle();
        result = result * PRIME + ($versaoControle == null ? 43 : $versaoControle.hashCode());
        final java.lang.Object $dispositivoId = this.getDispositivoId();
        result = result * PRIME + ($dispositivoId == null ? 43 : $dispositivoId.hashCode());
        final java.lang.Object $clienteId = this.getClienteId();
        result = result * PRIME + ($clienteId == null ? 43 : $clienteId.hashCode());
        final java.lang.Object $nomeDispositivo = this.getNomeDispositivo();
        result = result * PRIME + ($nomeDispositivo == null ? 43 : $nomeDispositivo.hashCode());
        final java.lang.Object $marca = this.getMarca();
        result = result * PRIME + ($marca == null ? 43 : $marca.hashCode());
        final java.lang.Object $modelo = this.getModelo();
        result = result * PRIME + ($modelo == null ? 43 : $modelo.hashCode());
        final java.lang.Object $sistemaOperacional = this.getSistemaOperacional();
        result = result * PRIME + ($sistemaOperacional == null ? 43 : $sistemaOperacional.hashCode());
        final java.lang.Object $versaoSO = this.getVersaoSO();
        result = result * PRIME + ($versaoSO == null ? 43 : $versaoSO.hashCode());
        final java.lang.Object $versaoApp = this.getVersaoApp();
        result = result * PRIME + ($versaoApp == null ? 43 : $versaoApp.hashCode());
        final java.lang.Object $deviceToken = this.getDeviceToken();
        result = result * PRIME + ($deviceToken == null ? 43 : $deviceToken.hashCode());
        final java.lang.Object $deviceFingerprint = this.getDeviceFingerprint();
        result = result * PRIME + ($deviceFingerprint == null ? 43 : $deviceFingerprint.hashCode());
        final java.lang.Object $imei = this.getImei();
        result = result * PRIME + ($imei == null ? 43 : $imei.hashCode());
        final java.lang.Object $numeroTelefone = this.getNumeroTelefone();
        result = result * PRIME + ($numeroTelefone == null ? 43 : $numeroTelefone.hashCode());
        final java.lang.Object $tipoDispositivo = this.getTipoDispositivo();
        result = result * PRIME + ($tipoDispositivo == null ? 43 : $tipoDispositivo.hashCode());
        final java.lang.Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final java.lang.Object $dataRegistro = this.getDataRegistro();
        result = result * PRIME + ($dataRegistro == null ? 43 : $dataRegistro.hashCode());
        final java.lang.Object $dataUltimoAcesso = this.getDataUltimoAcesso();
        result = result * PRIME + ($dataUltimoAcesso == null ? 43 : $dataUltimoAcesso.hashCode());
        final java.lang.Object $dataExpiracao = this.getDataExpiracao();
        result = result * PRIME + ($dataExpiracao == null ? 43 : $dataExpiracao.hashCode());
        final java.lang.Object $localizacaoAtual = this.getLocalizacaoAtual();
        result = result * PRIME + ($localizacaoAtual == null ? 43 : $localizacaoAtual.hashCode());
        final java.lang.Object $motivoBloqueio = this.getMotivoBloqueio();
        result = result * PRIME + ($motivoBloqueio == null ? 43 : $motivoBloqueio.hashCode());
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
        return "DispositivoMobile(id=" + this.getId() + ", dispositivoId=" + this.getDispositivoId() + ", clienteId=" + this.getClienteId() + ", nomeDispositivo=" + this.getNomeDispositivo() + ", marca=" + this.getMarca() + ", modelo=" + this.getModelo() + ", sistemaOperacional=" + this.getSistemaOperacional() + ", versaoSO=" + this.getVersaoSO() + ", versaoApp=" + this.getVersaoApp() + ", deviceToken=" + this.getDeviceToken() + ", deviceFingerprint=" + this.getDeviceFingerprint() + ", imei=" + this.getImei() + ", numeroTelefone=" + this.getNumeroTelefone() + ", tipoDispositivo=" + this.getTipoDispositivo() + ", status=" + this.getStatus() + ", biometricoHabilitado=" + this.getBiometricoHabilitado() + ", faceIdHabilitado=" + this.getFaceIdHabilitado() + ", touchIdHabilitado=" + this.getTouchIdHabilitado() + ", voiceIdHabilitado=" + this.getVoiceIdHabilitado() + ", irisIdHabilitado=" + this.getIrisIdHabilitado() + ", pinHabilitado=" + this.getPinHabilitado() + ", nfcHabilitado=" + this.getNfcHabilitado() + ", gpsHabilitado=" + this.getGpsHabilitado() + ", cameraHabilitada=" + this.getCameraHabilitada() + ", notificacoesPushHabilitadas=" + this.getNotificacoesPushHabilitadas() + ", dataRegistro=" + this.getDataRegistro() + ", dataUltimoAcesso=" + this.getDataUltimoAcesso() + ", dataExpiracao=" + this.getDataExpiracao() + ", localizacaoAtual=" + this.getLocalizacaoAtual() + ", latitude=" + this.getLatitude() + ", longitude=" + this.getLongitude() + ", precisaoGps=" + this.getPrecisaoGps() + ", tentativasFalha=" + this.getTentativasFalha() + ", bloqueado=" + this.getBloqueado() + ", motivoBloqueio=" + this.getMotivoBloqueio() + ", metadados=" + this.getMetadados() + ", dataCriacao=" + this.getDataCriacao() + ", dataAtualizacao=" + this.getDataAtualizacao() + ", versaoControle=" + this.getVersaoControle() + ")";
    }

    @java.lang.SuppressWarnings("all")
    public DispositivoMobile() {
    }

    @java.lang.SuppressWarnings("all")
    public DispositivoMobile(final Long id, final String dispositivoId, final String clienteId, final String nomeDispositivo, final String marca, final String modelo, final String sistemaOperacional, final String versaoSO, final String versaoApp, final String deviceToken, final String deviceFingerprint, final String imei, final String numeroTelefone, final TipoDispositivo tipoDispositivo, final StatusDispositivo status, final Boolean biometricoHabilitado, final Boolean faceIdHabilitado, final Boolean touchIdHabilitado, final Boolean voiceIdHabilitado, final Boolean irisIdHabilitado, final Boolean pinHabilitado, final Boolean nfcHabilitado, final Boolean gpsHabilitado, final Boolean cameraHabilitada, final Boolean notificacoesPushHabilitadas, final LocalDateTime dataRegistro, final LocalDateTime dataUltimoAcesso, final LocalDateTime dataExpiracao, final String localizacaoAtual, final Double latitude, final Double longitude, final Double precisaoGps, final Integer tentativasFalha, final Boolean bloqueado, final String motivoBloqueio, final String metadados, final LocalDateTime dataCriacao, final LocalDateTime dataAtualizacao, final Long versaoControle) {
        this.id = id;
        this.dispositivoId = dispositivoId;
        this.clienteId = clienteId;
        this.nomeDispositivo = nomeDispositivo;
        this.marca = marca;
        this.modelo = modelo;
        this.sistemaOperacional = sistemaOperacional;
        this.versaoSO = versaoSO;
        this.versaoApp = versaoApp;
        this.deviceToken = deviceToken;
        this.deviceFingerprint = deviceFingerprint;
        this.imei = imei;
        this.numeroTelefone = numeroTelefone;
        this.tipoDispositivo = tipoDispositivo;
        this.status = status;
        this.biometricoHabilitado = biometricoHabilitado;
        this.faceIdHabilitado = faceIdHabilitado;
        this.touchIdHabilitado = touchIdHabilitado;
        this.voiceIdHabilitado = voiceIdHabilitado;
        this.irisIdHabilitado = irisIdHabilitado;
        this.pinHabilitado = pinHabilitado;
        this.nfcHabilitado = nfcHabilitado;
        this.gpsHabilitado = gpsHabilitado;
        this.cameraHabilitada = cameraHabilitada;
        this.notificacoesPushHabilitadas = notificacoesPushHabilitadas;
        this.dataRegistro = dataRegistro;
        this.dataUltimoAcesso = dataUltimoAcesso;
        this.dataExpiracao = dataExpiracao;
        this.localizacaoAtual = localizacaoAtual;
        this.latitude = latitude;
        this.longitude = longitude;
        this.precisaoGps = precisaoGps;
        this.tentativasFalha = tentativasFalha;
        this.bloqueado = bloqueado;
        this.motivoBloqueio = motivoBloqueio;
        this.metadados = metadados;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.versaoControle = versaoControle;
    }
}
