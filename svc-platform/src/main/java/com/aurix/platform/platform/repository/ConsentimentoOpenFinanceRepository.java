package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.ConsentimentoOpenFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para consentimentos do Open Finance
 */
@Repository
public interface ConsentimentoOpenFinanceRepository extends JpaRepository<ConsentimentoOpenFinance, Long> {
    
    /**
     * Busca consentimento por ID único
     */
    Optional<ConsentimentoOpenFinance> findByConsentId(String consentId);
    
    /**
     * Busca consentimentos por cliente
     */
    List<ConsentimentoOpenFinance> findByClientId(String clientId);
    
    /**
     * Busca consentimentos por usuário
     */
    List<ConsentimentoOpenFinance> findByUserId(Long userId);
    
    /**
     * Busca consentimentos por status
     */
    List<ConsentimentoOpenFinance> findByStatus(ConsentimentoOpenFinance.StatusConsentimento status);
    
    /**
     * Busca consentimentos por tipo
     */
    List<ConsentimentoOpenFinance> findByTipoConsentimento(ConsentimentoOpenFinance.TipoConsentimento tipoConsentimento);
    
    /**
     * Busca consentimentos ativos por usuário
     */
    @Query("SELECT c FROM ConsentimentoOpenFinance c WHERE c.userId = :userId AND c.status = 'APROVADO' AND c.dataExpiracao > :agora")
    List<ConsentimentoOpenFinance> findConsentimentosAtivosPorUsuario(@Param("userId") Long userId, @Param("agora") LocalDateTime agora);
    
    /**
     * Busca consentimentos expirados
     */
    @Query("SELECT c FROM ConsentimentoOpenFinance c WHERE c.dataExpiracao < :agora AND c.status IN ('APROVADO', 'PENDENTE_APROVACAO')")
    List<ConsentimentoOpenFinance> findConsentimentosExpirados(@Param("agora") LocalDateTime agora);
    
    /**
     * Busca consentimentos por cliente e usuário
     */
    List<ConsentimentoOpenFinance> findByClientIdAndUserId(String clientId, Long userId);
    
    /**
     * Busca consentimentos por conta autorizada
     */
    @Query("SELECT c FROM ConsentimentoOpenFinance c WHERE :contaId MEMBER OF c.contasAutorizadas")
    List<ConsentimentoOpenFinance> findByContaAutorizada(@Param("contaId") Long contaId);
    
    /**
     * Busca consentimentos por permissão
     */
    @Query("SELECT c FROM ConsentimentoOpenFinance c WHERE :permissao MEMBER OF c.permissoes")
    List<ConsentimentoOpenFinance> findByPermissao(@Param("permissao") String permissao);
    
    /**
     * Conta consentimentos por cliente
     */
    long countByClientId(String clientId);
    
    /**
     * Conta consentimentos por usuário
     */
    long countByUserId(Long userId);
    
    /**
     * Conta consentimentos por status
     */
    long countByStatus(ConsentimentoOpenFinance.StatusConsentimento status);
    
    /**
     * Busca consentimentos criados em um período
     */
    @Query("SELECT c FROM ConsentimentoOpenFinance c WHERE c.dataCriacao BETWEEN :inicio AND :fim")
    List<ConsentimentoOpenFinance> findByPeriodoCriacao(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca consentimentos que expiram em breve
     */
    @Query("SELECT c FROM ConsentimentoOpenFinance c WHERE c.dataExpiracao BETWEEN :agora AND :limite AND c.status = 'APROVADO'")
    List<ConsentimentoOpenFinance> findConsentimentosExpirandoEmBreve(@Param("agora") LocalDateTime agora, @Param("limite") LocalDateTime limite);
    
    /**
     * Verifica se existe consentimento ativo para cliente e usuário
     */
    @Query("SELECT COUNT(c) > 0 FROM ConsentimentoOpenFinance c WHERE c.clientId = :clientId AND c.userId = :userId AND c.status = 'APROVADO' AND c.dataExpiracao > :agora")
    boolean existsConsentimentoAtivo(@Param("clientId") String clientId, @Param("userId") Long userId, @Param("agora") LocalDateTime agora);
    
    /**
     * Busca consentimentos por risco
     */
    @Query("SELECT c FROM ConsentimentoOpenFinance c WHERE c.riskScore >= :riskScoreMinimo")
    List<ConsentimentoOpenFinance> findByRiskScoreMinimo(@Param("riskScoreMinimo") Double riskScoreMinimo);
    
    /**
     * Busca consentimentos por nível de risco
     */
    List<ConsentimentoOpenFinance> findByRiskLevel(String riskLevel);
    
    /**
     * Busca consentimentos por IP
     */
    List<ConsentimentoOpenFinance> findByIpAddress(String ipAddress);
    
    /**
     * Busca consentimentos por device ID
     */
    List<ConsentimentoOpenFinance> findByDeviceId(String deviceId);
}
