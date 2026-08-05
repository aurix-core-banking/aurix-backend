package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.TokenOpenFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para tokens do Open Finance
 */
@Repository
public interface TokenOpenFinanceRepository extends JpaRepository<TokenOpenFinance, Long> {
    
    /**
     * Busca token por access token
     */
    Optional<TokenOpenFinance> findByAccessToken(String accessToken);
    
    /**
     * Busca token por refresh token
     */
    Optional<TokenOpenFinance> findByRefreshToken(String refreshToken);
    
    /**
     * Busca tokens por consentimento
     */
    List<TokenOpenFinance> findByConsentId(String consentId);
    
    /**
     * Busca tokens por cliente
     */
    List<TokenOpenFinance> findByClientId(String clientId);
    
    /**
     * Busca tokens por usuário
     */
    List<TokenOpenFinance> findByUserId(Long userId);
    
    /**
     * Busca tokens por status
     */
    List<TokenOpenFinance> findByStatus(TokenOpenFinance.StatusToken status);
    
    /**
     * Busca tokens ativos
     */
    @Query("SELECT t FROM TokenOpenFinance t WHERE t.status = 'ATIVO' AND t.dataExpiracao > :agora")
    List<TokenOpenFinance> findTokensAtivos(@Param("agora") LocalDateTime agora);
    
    /**
     * Busca tokens expirados
     */
    @Query("SELECT t FROM TokenOpenFinance t WHERE t.dataExpiracao < :agora AND t.status = 'ATIVO'")
    List<TokenOpenFinance> findTokensExpirados(@Param("agora") LocalDateTime agora);
    
    /**
     * Busca tokens por cliente e usuário
     */
    List<TokenOpenFinance> findByClientIdAndUserId(String clientId, Long userId);
    
    /**
     * Busca token ativo por access token
     */
    @Query("SELECT t FROM TokenOpenFinance t WHERE t.accessToken = :accessToken AND t.status = 'ATIVO' AND t.dataExpiracao > :agora")
    Optional<TokenOpenFinance> findTokenAtivoPorAccessToken(@Param("accessToken") String accessToken, @Param("agora") LocalDateTime agora);
    
    /**
     * Busca tokens que expiram em breve
     */
    @Query("SELECT t FROM TokenOpenFinance t WHERE t.dataExpiracao BETWEEN :agora AND :limite AND t.status = 'ATIVO'")
    List<TokenOpenFinance> findTokensExpirandoEmBreve(@Param("agora") LocalDateTime agora, @Param("limite") LocalDateTime limite);
    
    /**
     * Conta tokens por cliente
     */
    long countByClientId(String clientId);
    
    /**
     * Conta tokens por usuário
     */
    long countByUserId(Long userId);
    
    /**
     * Conta tokens por status
     */
    long countByStatus(TokenOpenFinance.StatusToken status);
    
    /**
     * Busca tokens criados em um período
     */
    @Query("SELECT t FROM TokenOpenFinance t WHERE t.dataCriacao BETWEEN :inicio AND :fim")
    List<TokenOpenFinance> findByPeriodoCriacao(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca tokens por IP
     */
    List<TokenOpenFinance> findByIpAddress(String ipAddress);
    
    /**
     * Busca tokens por device ID
     */
    List<TokenOpenFinance> findByDeviceId(String deviceId);
    
    /**
     * Busca tokens com rate limit baixo
     */
    @Query("SELECT t FROM TokenOpenFinance t WHERE t.rateLimitRemaining < :limite AND t.status = 'ATIVO'")
    List<TokenOpenFinance> findTokensComRateLimitBaixo(@Param("limite") Integer limite);
    
    /**
     * Busca tokens não utilizados há muito tempo
     */
    @Query("SELECT t FROM TokenOpenFinance t WHERE t.lastUsed < :limite AND t.status = 'ATIVO'")
    List<TokenOpenFinance> findTokensNaoUtilizados(@Param("limite") LocalDateTime limite);
    
    /**
     * Busca tokens por escopo
     */
    List<TokenOpenFinance> findByScopeContaining(String scope);
    
    /**
     * Busca tokens por tipo
     */
    List<TokenOpenFinance> findByTokenType(String tokenType);
    
    /**
     * Verifica se existe token ativo para consentimento
     */
    @Query("SELECT COUNT(t) > 0 FROM TokenOpenFinance t WHERE t.consentId = :consentId AND t.status = 'ATIVO' AND t.dataExpiracao > :agora")
    boolean existsTokenAtivoParaConsentimento(@Param("consentId") String consentId, @Param("agora") LocalDateTime agora);
    
    /**
     * Busca tokens por geolocalização
     */
    List<TokenOpenFinance> findByGeolocation(String geolocation);
    
    /**
     * Busca tokens por user agent
     */
    List<TokenOpenFinance> findByUserAgentContaining(String userAgent);
}
