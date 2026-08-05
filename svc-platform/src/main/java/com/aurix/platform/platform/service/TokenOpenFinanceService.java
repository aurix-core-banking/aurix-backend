package com.aurix.platform.platform.service;

import com.aurix.platform.platform.dto.TokenOpenFinanceDTO;
import com.aurix.platform.platform.entity.ConsentimentoOpenFinance;
import com.aurix.platform.platform.entity.TokenOpenFinance;
import com.aurix.platform.platform.repository.TokenOpenFinanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service para gestão de tokens do Open Finance
 */
@Service
@Transactional
public class TokenOpenFinanceService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TokenOpenFinanceService.class);
    private final TokenOpenFinanceRepository tokenRepository;
    private final LogAcessoOpenFinanceService logService;

    /**
     * Gera token para consentimento aprovado
     */
    public TokenOpenFinanceDTO gerarTokenParaConsentimento(ConsentimentoOpenFinance consentimento) {
        log.info("Gerando token para consentimento: {}", consentimento.getConsentId());
        // Verificar se já existe token ativo
        if (tokenRepository.existsTokenAtivoParaConsentimento(consentimento.getConsentId(), LocalDateTime.now())) {
            throw new RuntimeException("Já existe token ativo para este consentimento");
        }
        // Gerar tokens
        String accessToken = gerarAccessToken();
        String refreshToken = gerarRefreshToken();
        // Calcular expiração (1 hora por padrão)
        LocalDateTime dataExpiracao = LocalDateTime.now().plusHours(1);
        // Criar token
        TokenOpenFinance token =  // 1 hora em segundos
        // Rate limit inicial
        TokenOpenFinance.builder().accessToken(accessToken).refreshToken(refreshToken).tokenType("Bearer").expiresIn(3600).scope(String.join(" ", consentimento.getPermissoes())).consentId(consentimento.getConsentId()).clientId(consentimento.getClientId()).userId(consentimento.getUserId()).status(TokenOpenFinance.StatusToken.ATIVO).dataExpiracao(dataExpiracao).ipAddress(consentimento.getIpAddress()).userAgent(consentimento.getUserAgent()).deviceId(consentimento.getDeviceId()).geolocation(consentimento.getGeolocation()).rateLimitRemaining(1000).rateLimitReset(LocalDateTime.now().plusMinutes(1)).usageCount(0L).build();
        token = tokenRepository.save(token);
        log.info("Token gerado com sucesso para consentimento: {}", consentimento.getConsentId());
        return TokenOpenFinanceDTO.fromEntity(token);
    }

    /**
     * Valida token de acesso
     */
    @Transactional(readOnly = true)
    public TokenOpenFinanceDTO validarToken(String accessToken) {
        log.debug("Validando token: {}", accessToken.substring(0, Math.min(10, accessToken.length())) + "...");
        TokenOpenFinance token = tokenRepository.findTokenAtivoPorAccessToken(accessToken, LocalDateTime.now()).orElseThrow(() -> new RuntimeException("Token inválido ou expirado"));
        // Atualizar uso
        token.setLastUsed(LocalDateTime.now());
        token.setUsageCount(token.getUsageCount() + 1);
        tokenRepository.save(token);
        return TokenOpenFinanceDTO.fromEntity(token);
    }

    /**
     * Renova token usando refresh token
     */
    public TokenOpenFinanceDTO renovarToken(String refreshToken) {
        log.info("Renovando token com refresh token");
        TokenOpenFinance token = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Refresh token inválido"));
        if (!token.getStatus().equals(TokenOpenFinance.StatusToken.ATIVO)) {
            throw new RuntimeException("Token não está ativo");
        }
        // Gerar novos tokens
        String novoAccessToken = gerarAccessToken();
        String novoRefreshToken = gerarRefreshToken();
        // Atualizar token
        token.setAccessToken(novoAccessToken);
        token.setRefreshToken(novoRefreshToken);
        token.setDataExpiracao(LocalDateTime.now().plusHours(1));
        token.setExpiresIn(3600);
        token.setRateLimitRemaining(1000);
        token.setRateLimitReset(LocalDateTime.now().plusMinutes(1));
        token.setLastUsed(LocalDateTime.now());
        token.setUsageCount(token.getUsageCount() + 1);
        token = tokenRepository.save(token);
        log.info("Token renovado com sucesso");
        return TokenOpenFinanceDTO.fromEntity(token);
    }

    /**
     * Revoga token
     */
    public void revogarToken(String accessToken, String motivo) {
        log.info("Revogando token - Motivo: {}", motivo);
        TokenOpenFinance token = tokenRepository.findByAccessToken(accessToken).orElseThrow(() -> new RuntimeException("Token não encontrado"));
        token.setStatus(TokenOpenFinance.StatusToken.REVOGADO);
        token.setDataRevocacao(LocalDateTime.now());
        token.setMotivoRevocacao(motivo);
        tokenRepository.save(token);
        log.info("Token revogado com sucesso");
    }

    /**
     * Revoga todos os tokens de um consentimento
     */
    public void revogarTokensPorConsentimento(String consentId) {
        log.info("Revogando todos os tokens do consentimento: {}", consentId);
        List<TokenOpenFinance> tokens = tokenRepository.findByConsentId(consentId);
        for (TokenOpenFinance token : tokens) {
            if (token.getStatus().equals(TokenOpenFinance.StatusToken.ATIVO)) {
                token.setStatus(TokenOpenFinance.StatusToken.REVOGADO);
                token.setDataRevocacao(LocalDateTime.now());
                token.setMotivoRevocacao("Consentimento revogado");
                tokenRepository.save(token);
            }
        }
        log.info("Tokens revogados com sucesso para consentimento: {}", consentId);
    }

    /**
     * Processa tokens expirados
     */
    public void processarTokensExpirados() {
        log.info("Processando tokens expirados");
        List<TokenOpenFinance> expirados = tokenRepository.findTokensExpirados(LocalDateTime.now());
        for (TokenOpenFinance token : expirados) {
            token.setStatus(TokenOpenFinance.StatusToken.EXPIRADO);
            tokenRepository.save(token);
            log.info("Token expirado processado: {}", token.getId());
        }
    }

    /**
     * Verifica rate limit
     */
    @Transactional(readOnly = true)
    public boolean verificarRateLimit(String accessToken) {
        TokenOpenFinance token = tokenRepository.findByAccessToken(accessToken).orElseThrow(() -> new RuntimeException("Token não encontrado"));
        if (token.getRateLimitRemaining() == null || token.getRateLimitRemaining() <= 0) {
            return false;
        }
        // Atualizar rate limit
        token.setRateLimitRemaining(token.getRateLimitRemaining() - 1);
        tokenRepository.save(token);
        return true;
    }

    /**
     * Lista tokens por usuário
     */
    @Transactional(readOnly = true)
    public List<TokenOpenFinanceDTO> listarTokensPorUsuario(Long userId) {
        List<TokenOpenFinance> tokens = tokenRepository.findByUserId(userId);
        return tokens.stream().map(TokenOpenFinanceDTO::fromEntity).collect(Collectors.toList());
    }

    /**
     * Lista tokens por cliente
     */
    @Transactional(readOnly = true)
    public List<TokenOpenFinanceDTO> listarTokensPorCliente(String clientId) {
        List<TokenOpenFinance> tokens = tokenRepository.findByClientId(clientId);
        return tokens.stream().map(TokenOpenFinanceDTO::fromEntity).collect(Collectors.toList());
    }

    /**
     * Gera access token
     */
    private String gerarAccessToken() {
        return "ACCESS-" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * Gera refresh token
     */
    private String gerarRefreshToken() {
        return "REFRESH-" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    @java.lang.SuppressWarnings("all")
    public TokenOpenFinanceService(final TokenOpenFinanceRepository tokenRepository, final LogAcessoOpenFinanceService logService) {
        this.tokenRepository = tokenRepository;
        this.logService = logService;
    }
}
