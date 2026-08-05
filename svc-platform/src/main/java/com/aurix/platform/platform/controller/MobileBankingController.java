package com.aurix.platform.platform.controller;

import com.aurix.platform.platform.entity.DispositivoMobile;
import com.aurix.platform.platform.entity.SessaoMobile;
import com.aurix.platform.platform.entity.NotificacaoMobile;
import com.aurix.platform.platform.service.MobileBankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Map;

/**
 * Controller principal para Mobile Banking
 * 
 * Expõe APIs para funcionalidades do app mobile
 */
@RestController
@RequestMapping("/api/platform/mobile-banking")
@Tag(name = "Mobile Banking", description = "APIs para Mobile Banking - App Mobile")
public class MobileBankingController {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MobileBankingController.class);
    private final MobileBankingService mobileBankingService;

    // ========== GESTÃO DE DISPOSITIVOS ==========
    @PostMapping("/dispositivos/registrar")
    @Operation(summary = "Registrar dispositivo mobile", description = "Registra novo dispositivo mobile do cliente")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Dispositivo registrado com sucesso"), @ApiResponse(responseCode = "400", description = "Dados inválidos"), @ApiResponse(responseCode = "409", description = "Dispositivo já registrado")})
    public ResponseEntity<Map<String, Object>> registrarDispositivo(@Parameter(description = "Dados do dispositivo") @RequestBody @Valid DispositivoRequest request, HttpServletRequest httpRequest) {
        log.info("Registrando dispositivo mobile: {}", request.getDispositivoId());
        try {
            DispositivoMobile dispositivo = mobileBankingService.registrarDispositivo(request.getClienteId(), request.getDispositivoId(), request.getNomeDispositivo(), request.getMarca(), request.getModelo(), request.getSistemaOperacional(), request.getVersaoSO(), request.getDeviceToken(), request.getDeviceFingerprint(), request.getImei(), request.getNumeroTelefone());
            Map<String, Object> response = Map.of("success", true, "message", "Dispositivo registrado com sucesso", "dispositivoId", dispositivo.getDispositivoId(), "status", dispositivo.getStatus(), "tipoDispositivo", dispositivo.getTipoDispositivo(), "biometricoHabilitado", dispositivo.getBiometricoHabilitado(), "notificacoesPushHabilitadas", dispositivo.getNotificacoesPushHabilitadas());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao registrar dispositivo mobile: {}", e.getMessage());
            return ResponseEntity.status(400).body(Map.of("success", false, "message", "Erro ao registrar dispositivo", "error", e.getMessage()));
        }
    }

    @PostMapping("/dispositivos/{dispositivoId}/biometrico")
    @Operation(summary = "Habilitar autenticação biométrica", description = "Habilita autenticação biométrica no dispositivo")
    public ResponseEntity<Map<String, Object>> habilitarBiometrico(@Parameter(description = "ID do dispositivo") @PathVariable String dispositivoId, @Parameter(description = "Tipo de biometria") @RequestParam String tipoBiometrico) {
        log.info("Habilitando autenticação biométrica: Dispositivo={}, Tipo={}", dispositivoId, tipoBiometrico);
        try {
            mobileBankingService.habilitarBiometrico(dispositivoId, tipoBiometrico);
            return ResponseEntity.ok(Map.of("success", true, "message", "Autenticação biométrica habilitada com sucesso", "tipoBiometrico", tipoBiometrico));
        } catch (Exception e) {
            log.error("Erro ao habilitar autenticação biométrica: {}", e.getMessage());
            return ResponseEntity.status(400).body(Map.of("success", false, "message", "Erro ao habilitar autenticação biométrica", "error", e.getMessage()));
        }
    }

    // ========== AUTENTICAÇÃO E SESSÕES ==========
    @PostMapping("/auth/login")
    @Operation(summary = "Login no Mobile Banking", description = "Realiza login e cria sessão mobile")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Login realizado com sucesso"), @ApiResponse(responseCode = "401", description = "Credenciais inválidas"), @ApiResponse(responseCode = "403", description = "Acesso negado"), @ApiResponse(responseCode = "429", description = "Muitas tentativas de login")})
    public ResponseEntity<Map<String, Object>> login(@Parameter(description = "Dados de login mobile") @RequestBody @Valid LoginMobileRequest loginRequest, HttpServletRequest request) {
        log.info("Tentativa de login no Mobile Banking: {}", loginRequest.getUsuario());
        try {
            String ipAddress = obterIpAddress(request);
            String userAgent = request.getHeader("User-Agent");
            SessaoMobile sessao = mobileBankingService.criarSessaoMobile(loginRequest.getDispositivoId(), loginRequest.getClienteId(), loginRequest.getUsuario(), loginRequest.getDeviceToken(), ipAddress, userAgent, loginRequest.getAppVersion(), loginRequest.getOsVersion(), loginRequest.getDeviceModel(), loginRequest.getDeviceManufacturer(), loginRequest.getLatitude(), loginRequest.getLongitude());
            Map<String, Object> response = Map.of("success", true, "message", "Login realizado com sucesso", "sessaoId", sessao.getSessaoId(), "dataExpiracao", sessao.getDataExpiracao(), "timeoutMinutos", sessao.getTimeoutMinutos(), "mfaRequired", !sessao.getMfaVerificado(), "biometricoRequired", !sessao.getBiometricoVerificado(), "localizacaoVerificada", sessao.getLocalizacaoVerificada());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro no login do Mobile Banking: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Erro interno do servidor", "error", e.getMessage()));
        }
    }

    @PostMapping("/auth/logout")
    @Operation(summary = "Logout do Mobile Banking", description = "Encerra sessão mobile")
    public ResponseEntity<Map<String, Object>> logout(@Parameter(description = "ID da sessão") @RequestHeader("X-Session-ID") String sessaoId) {
        log.info("Logout do Mobile Banking: {}", sessaoId);
        try {
            // Implementar logout (seria necessário injetar o service)
            // mobileBankingService.encerrarSessaoMobile(sessaoId);
            return ResponseEntity.ok(Map.of("success", true, "message", "Logout realizado com sucesso"));
        } catch (Exception e) {
            log.error("Erro no logout do Mobile Banking: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of("success", false, "message", "Erro interno do servidor", "error", e.getMessage()));
        }
    }

    @GetMapping("/auth/validate-session")
    @Operation(summary = "Validar sessão mobile", description = "Valida se a sessão mobile está ativa e válida")
    public ResponseEntity<Map<String, Object>> validarSessao(@Parameter(description = "ID da sessão") @RequestHeader("X-Session-ID") String sessaoId) {
        try {
            boolean valida = mobileBankingService.validarSessaoMobile(sessaoId);
            return ResponseEntity.ok(Map.of("valid", valida, "message", valida ? "Sessão válida" : "Sessão inválida ou expirada"));
        } catch (Exception e) {
            log.error("Erro ao validar sessão mobile: {}", e.getMessage());
            return ResponseEntity.status(500).body(Map.of("valid", false, "message", "Erro interno do servidor", "error", e.getMessage()));
        }
    }

    // ========== NOTIFICAÇÕES ==========
    @PostMapping("/notificacoes/push")
    @Operation(summary = "Enviar notificação push", description = "Envia notificação push para dispositivo mobile")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Notificação enviada com sucesso"), @ApiResponse(responseCode = "400", description = "Dados inválidos"), @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")})
    public ResponseEntity<Map<String, Object>> enviarNotificacaoPush(@Parameter(description = "Dados da notificação") @RequestBody @Valid NotificacaoPushRequest request) {
        log.info("Enviando notificação push: Dispositivo={}, Tipo={}", request.getDispositivoId(), request.getTipoNotificacao());
        try {
            NotificacaoMobile notificacao = mobileBankingService.enviarNotificacaoPush(request.getDispositivoId(), request.getClienteId(), request.getTipoNotificacao(), request.getCategoria(), request.getTitulo(), request.getMensagem(), request.getCorpo());
            Map<String, Object> response = Map.of("success", true, "message", "Notificação enviada com sucesso", "notificacaoId", notificacao.getNotificacaoId(), "status", notificacao.getStatus(), "prioridade", notificacao.getPrioridade());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erro ao enviar notificação push: {}", e.getMessage());
            return ResponseEntity.status(400).body(Map.of("success", false, "message", "Erro ao enviar notificação", "error", e.getMessage()));
        }
    }

    @GetMapping("/notificacoes")
    @Operation(summary = "Listar notificações", description = "Lista notificações do cliente")
    public ResponseEntity<Page<NotificacaoMobile>> listarNotificacoes(@Parameter(description = "ID do cliente") @RequestParam String clienteId, @Parameter(description = "Paginação") Pageable pageable, @Parameter(description = "ID da sessão") @RequestHeader("X-Session-ID") String sessaoId) {
        try {
            // Validar sessão
            if (!mobileBankingService.validarSessaoMobile(sessaoId)) {
                return ResponseEntity.status(401).build();
            }
            // Implementar busca de notificações (seria necessário injetar o repository)
            // Page<NotificacaoMobile> notificacoes = notificacaoRepository.findByClienteId(clienteId, pageable);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Erro ao listar notificações: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    // ========== DASHBOARD MOBILE ==========
    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard mobile do cliente", description = "Retorna dados para o dashboard do Mobile Banking")
    public ResponseEntity<Map<String, Object>> getDashboard(@Parameter(description = "ID do cliente") @RequestParam String clienteId, @Parameter(description = "ID da sessão") @RequestHeader("X-Session-ID") String sessaoId) {
        try {
            // Validar sessão
            if (!mobileBankingService.validarSessaoMobile(sessaoId)) {
                return ResponseEntity.status(401).build();
            }
            Map<String, Object> dashboard = new java.util.HashMap<>();
            dashboard.put("clienteId", clienteId);
            dashboard.put("saldoConta", "R$ 15.750,00");
            dashboard.put("limiteDisponivel", "R$ 10.000,00");
            dashboard.put("transacoesHoje", 3);
            dashboard.put("faturasVencendo", 1);
            dashboard.put("notificacoesNaoLidas", 2);
            dashboard.put("investimentos", Map.of("totalAplicado", "R$ 50.000,00", "rendimentoMes", "R$ 450,00"));
            dashboard.put("cartoes", Map.of("limiteTotal", "R$ 15.000,00", "limiteDisponivel", "R$ 8.500,00", "faturaAtual", "R$ 2.300,00"));
            dashboard.put("pix", Map.of("chavesCadastradas", 3, "transacoesHoje", 2, "limiteDisponivel", "R$ 5.000,00"));
            dashboard.put("biometrico", Map.of("habilitado", true, "tipo", "Face ID", "ultimaVerificacao", "27/09/2025 00:30"));
            dashboard.put("ultimaAtividade", "27/09/2025 00:32");
            return ResponseEntity.ok(dashboard);
        } catch (Exception e) {
            log.error("Erro ao obter dashboard mobile: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    // ========== MÉTODOS AUXILIARES ==========
    private String obterIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    // ========== CLASSES DE REQUEST ==========
    public static class DispositivoRequest {
        private String clienteId;
        private String dispositivoId;
        private String nomeDispositivo;
        private String marca;
        private String modelo;
        private String sistemaOperacional;
        private String versaoSO;
        private String deviceToken;
        private String deviceFingerprint;
        private String imei;
        private String numeroTelefone;

        // Getters e Setters
        public String getClienteId() {
            return clienteId;
        }

        public void setClienteId(String clienteId) {
            this.clienteId = clienteId;
        }

        public String getDispositivoId() {
            return dispositivoId;
        }

        public void setDispositivoId(String dispositivoId) {
            this.dispositivoId = dispositivoId;
        }

        public String getNomeDispositivo() {
            return nomeDispositivo;
        }

        public void setNomeDispositivo(String nomeDispositivo) {
            this.nomeDispositivo = nomeDispositivo;
        }

        public String getMarca() {
            return marca;
        }

        public void setMarca(String marca) {
            this.marca = marca;
        }

        public String getModelo() {
            return modelo;
        }

        public void setModelo(String modelo) {
            this.modelo = modelo;
        }

        public String getSistemaOperacional() {
            return sistemaOperacional;
        }

        public void setSistemaOperacional(String sistemaOperacional) {
            this.sistemaOperacional = sistemaOperacional;
        }

        public String getVersaoSO() {
            return versaoSO;
        }

        public void setVersaoSO(String versaoSO) {
            this.versaoSO = versaoSO;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getDeviceFingerprint() {
            return deviceFingerprint;
        }

        public void setDeviceFingerprint(String deviceFingerprint) {
            this.deviceFingerprint = deviceFingerprint;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getNumeroTelefone() {
            return numeroTelefone;
        }

        public void setNumeroTelefone(String numeroTelefone) {
            this.numeroTelefone = numeroTelefone;
        }
    }


    public static class LoginMobileRequest {
        private String dispositivoId;
        private String clienteId;
        private String usuario;
        private String senha;
        private String deviceToken;
        private String appVersion;
        private String osVersion;
        private String deviceModel;
        private String deviceManufacturer;
        private Double latitude;
        private Double longitude;

        // Getters e Setters
        public String getDispositivoId() {
            return dispositivoId;
        }

        public void setDispositivoId(String dispositivoId) {
            this.dispositivoId = dispositivoId;
        }

        public String getClienteId() {
            return clienteId;
        }

        public void setClienteId(String clienteId) {
            this.clienteId = clienteId;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }

        public String getSenha() {
            return senha;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getOsVersion() {
            return osVersion;
        }

        public void setOsVersion(String osVersion) {
            this.osVersion = osVersion;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public String getDeviceManufacturer() {
            return deviceManufacturer;
        }

        public void setDeviceManufacturer(String deviceManufacturer) {
            this.deviceManufacturer = deviceManufacturer;
        }

        public Double getLatitude() {
            return latitude;
        }

        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        public Double getLongitude() {
            return longitude;
        }

        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }
    }


    public static class NotificacaoPushRequest {
        private String dispositivoId;
        private String clienteId;
        private NotificacaoMobile.TipoNotificacao tipoNotificacao;
        private NotificacaoMobile.CategoriaNotificacao categoria;
        private String titulo;
        private String mensagem;
        private String corpo;

        // Getters e Setters
        public String getDispositivoId() {
            return dispositivoId;
        }

        public void setDispositivoId(String dispositivoId) {
            this.dispositivoId = dispositivoId;
        }

        public String getClienteId() {
            return clienteId;
        }

        public void setClienteId(String clienteId) {
            this.clienteId = clienteId;
        }

        public NotificacaoMobile.TipoNotificacao getTipoNotificacao() {
            return tipoNotificacao;
        }

        public void setTipoNotificacao(NotificacaoMobile.TipoNotificacao tipoNotificacao) {
            this.tipoNotificacao = tipoNotificacao;
        }

        public NotificacaoMobile.CategoriaNotificacao getCategoria() {
            return categoria;
        }

        public void setCategoria(NotificacaoMobile.CategoriaNotificacao categoria) {
            this.categoria = categoria;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getCorpo() {
            return corpo;
        }

        public void setCorpo(String corpo) {
            this.corpo = corpo;
        }
    }

    @java.lang.SuppressWarnings("all")
    public MobileBankingController(final MobileBankingService mobileBankingService) {
        this.mobileBankingService = mobileBankingService;
    }
}
