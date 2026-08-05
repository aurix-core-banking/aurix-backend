package com.aurix.platform.platform.service;

import com.aurix.platform.platform.entity.LogAcessoOpenFinance;
import com.aurix.platform.platform.repository.LogAcessoOpenFinanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

/**
 * Service para gestão de logs de acesso do Open Finance
 */
@Service
@Transactional
@SuppressWarnings({"PMD.UnusedFormalParameter"})
public class LogAcessoOpenFinanceService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LogAcessoOpenFinanceService.class);
    private final LogAcessoOpenFinanceRepository logRepository;

    /**
     * Registra acesso às APIs do Open Finance
     */
    public void registrarAcesso(String consentId, String clientId, Long userId, String endpoint, String method, Integer httpStatus, Long responseTimeMs, String ipAddress, String userAgent, String deviceId, String geolocation, String dadosAcessados) {
        log.debug("Registrando acesso - Endpoint: {} {} - Status: {}", method, endpoint, httpStatus);
        LogAcessoOpenFinance logAcesso = LogAcessoOpenFinance.builder().requestId(gerarRequestId()).consentId(consentId).clientId(clientId).userId(userId).endpoint(endpoint).method(method).httpStatus(httpStatus).responseTimeMs(responseTimeMs).ipAddress(ipAddress).userAgent(userAgent).deviceId(deviceId).geolocation(geolocation).riskScore(calcularRiskScore(ipAddress, deviceId, geolocation)).riskLevel(determinarRiskLevel(calcularRiskScore(ipAddress, deviceId, geolocation))).tipoAcesso(determinarTipoAcesso(endpoint)).categoriaDados(determinarCategoriaDados(endpoint)).dadosAcessados(dadosAcessados).erroDetalhes(httpStatus >= 400 ? "Erro na requisição" : null).build();
        logRepository.save(logAcesso);
        log.debug("Acesso registrado com sucesso - Request ID: {}", logAcesso.getRequestId());
    }

    /**
     * Registra erro de acesso
     */
    public void registrarErro(String consentId, String clientId, Long userId, String endpoint, String method, Integer httpStatus, String erroDetalhes, String ipAddress, String userAgent) {
        log.warn("Registrando erro de acesso - Endpoint: {} {} - Status: {} - Erro: {}", method, endpoint, httpStatus, erroDetalhes);
        LogAcessoOpenFinance logAcesso =  // Score alto para erros
        LogAcessoOpenFinance.builder().requestId(gerarRequestId()).consentId(consentId).clientId(clientId).userId(userId).endpoint(endpoint).method(method).httpStatus(httpStatus).responseTimeMs(0L).ipAddress(ipAddress).userAgent(userAgent).riskScore(1.0).riskLevel("ALTO").tipoAcesso(determinarTipoAcesso(endpoint)).categoriaDados(determinarCategoriaDados(endpoint)).erroDetalhes(erroDetalhes).build();
        logRepository.save(logAcesso);
    }

    /**
     * Gera ID único para requisição
     */
    private String gerarRequestId() {
        return "REQ-" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * Calcula score de risco baseado nos parâmetros
     */
    private Double calcularRiskScore(String ipAddress, String deviceId, String geolocation) {
        double score = 0.0;
        // Verificar IP suspeito (implementar lógica de verificação)
        if (ipAddress != null && isIpSuspeito(ipAddress)) {
            score += 0.3;
        }
        // Verificar device suspeito (implementar lógica de verificação)
        if (deviceId != null && isDeviceSuspeito(deviceId)) {
            score += 0.2;
        }
        // Verificar geolocalização suspeita (implementar lógica de verificação)
        if (geolocation != null && isGeolocationSuspeita(geolocation)) {
            score += 0.2;
        }
        return Math.min(score, 1.0);
    }

    /**
     * Determina nível de risco
     */
    private String determinarRiskLevel(Double riskScore) {
        if (riskScore < 0.3) {
            return "BAIXO";
        } else if (riskScore < 0.7) {
            return "MEDIO";
        } else {
            return "ALTO";
        }
    }

    /**
     * Determina tipo de acesso baseado no endpoint
     */
    private LogAcessoOpenFinance.TipoAcesso determinarTipoAcesso(String endpoint) {
        if (endpoint.contains("/accounts")) {
            return LogAcessoOpenFinance.TipoAcesso.CONTA;
        } else if (endpoint.contains("/transactions")) {
            return LogAcessoOpenFinance.TipoAcesso.TRANSACAO;
        } else if (endpoint.contains("/credit-cards")) {
            return LogAcessoOpenFinance.TipoAcesso.CARTAO_CREDITO;
        } else if (endpoint.contains("/personal")) {
            return LogAcessoOpenFinance.TipoAcesso.DADOS_PESSOAIS;
        } else if (endpoint.contains("/business")) {
            return LogAcessoOpenFinance.TipoAcesso.DADOS_EMPRESARIAIS;
        } else if (endpoint.contains("/financing")) {
            return LogAcessoOpenFinance.TipoAcesso.FINANCIAMENTO;
        } else if (endpoint.contains("/loans")) {
            return LogAcessoOpenFinance.TipoAcesso.EMPRESTIMO;
        } else {
            return LogAcessoOpenFinance.TipoAcesso.RECURSOS;
        }
    }

    /**
     * Determina categoria dos dados baseado no endpoint
     */
    private LogAcessoOpenFinance.CategoriaDados determinarCategoriaDados(String endpoint) {
        if (endpoint.contains("/personal") || endpoint.contains("/identifications")) {
            return LogAcessoOpenFinance.CategoriaDados.IDENTIFICACAO;
        } else if (endpoint.contains("/contacts")) {
            return LogAcessoOpenFinance.CategoriaDados.CONTATOS;
        } else if (endpoint.contains("/accounts") || endpoint.contains("/transactions")) {
            return LogAcessoOpenFinance.CategoriaDados.FINANCEIRO;
        } else if (endpoint.contains("/transactions")) {
            return LogAcessoOpenFinance.CategoriaDados.TRANSACIONAL;
        } else if (endpoint.contains("/behavioral")) {
            return LogAcessoOpenFinance.CategoriaDados.COMPORTAMENTAL;
        } else {
            return LogAcessoOpenFinance.CategoriaDados.SENSIVEL;
        }
    }

    /**
     * Verifica se IP é suspeito (implementar lógica real)
     */
    private boolean isIpSuspeito(String ipAddress) {
        // Implementar lógica de verificação de IP suspeito
        // Por exemplo, verificar blacklists, geolocalização suspeita, etc.
        return false;
    }

    /**
     * Verifica se device é suspeito (implementar lógica real)
     */
    private boolean isDeviceSuspeito(String deviceId) {
        // Implementar lógica de verificação de device suspeito
        // Por exemplo, verificar se device é conhecido, se tem histórico suspeito, etc.
        return false;
    }

    /**
     * Verifica se geolocalização é suspeita (implementar lógica real)
     */
    private boolean isGeolocationSuspeita(String geolocation) {
        // Implementar lógica de verificação de geolocalização suspeita
        // Por exemplo, verificar se localização é incomum para o usuário, etc.
        return false;
    }

    @java.lang.SuppressWarnings("all")
    public LogAcessoOpenFinanceService(final LogAcessoOpenFinanceRepository logRepository) {
        this.logRepository = logRepository;
    }
}
