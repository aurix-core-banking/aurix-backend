package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.LogAcessoOpenFinance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para logs de acesso do Open Finance
 */
@Repository
public interface LogAcessoOpenFinanceRepository extends JpaRepository<LogAcessoOpenFinance, Long> {
    
    /**
     * Busca log por request ID
     */
    LogAcessoOpenFinance findByRequestId(String requestId);
    
    /**
     * Busca logs por consentimento
     */
    List<LogAcessoOpenFinance> findByConsentId(String consentId);
    
    /**
     * Busca logs por cliente
     */
    List<LogAcessoOpenFinance> findByClientId(String clientId);
    
    /**
     * Busca logs por usuário
     */
    List<LogAcessoOpenFinance> findByUserId(Long userId);
    
    /**
     * Busca logs por endpoint
     */
    List<LogAcessoOpenFinance> findByEndpoint(String endpoint);
    
    /**
     * Busca logs por método HTTP
     */
    List<LogAcessoOpenFinance> findByMethod(String method);
    
    /**
     * Busca logs por status HTTP
     */
    List<LogAcessoOpenFinance> findByHttpStatus(Integer httpStatus);
    
    /**
     * Busca logs por tipo de acesso
     */
    List<LogAcessoOpenFinance> findByTipoAcesso(LogAcessoOpenFinance.TipoAcesso tipoAcesso);
    
    /**
     * Busca logs por categoria de dados
     */
    List<LogAcessoOpenFinance> findByCategoriaDados(LogAcessoOpenFinance.CategoriaDados categoriaDados);
    
    /**
     * Busca logs por período
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.dataAcesso BETWEEN :inicio AND :fim")
    List<LogAcessoOpenFinance> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs por IP
     */
    List<LogAcessoOpenFinance> findByIpAddress(String ipAddress);
    
    /**
     * Busca logs por device ID
     */
    List<LogAcessoOpenFinance> findByDeviceId(String deviceId);
    
    /**
     * Busca logs por geolocalização
     */
    List<LogAcessoOpenFinance> findByGeolocation(String geolocation);
    
    /**
     * Busca logs por nível de risco
     */
    List<LogAcessoOpenFinance> findByRiskLevel(String riskLevel);
    
    /**
     * Busca logs com score de risco alto
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.riskScore >= :scoreMinimo")
    List<LogAcessoOpenFinance> findByRiskScoreMinimo(@Param("scoreMinimo") Double scoreMinimo);
    
    /**
     * Busca logs de erro
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.httpStatus >= 400")
    List<LogAcessoOpenFinance> findLogsErro();
    
    /**
     * Busca logs de sucesso
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.httpStatus < 400")
    List<LogAcessoOpenFinance> findLogsSucesso();
    
    /**
     * Busca logs críticos
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.riskLevel = 'ALTO' OR l.httpStatus >= 500")
    List<LogAcessoOpenFinance> findLogsCriticos();
    
    /**
     * Conta logs por cliente
     */
    long countByClientId(String clientId);
    
    /**
     * Conta logs por usuário
     */
    long countByUserId(Long userId);
    
    /**
     * Conta logs por endpoint
     */
    long countByEndpoint(String endpoint);
    
    /**
     * Conta logs por status HTTP
     */
    long countByHttpStatus(Integer httpStatus);
    
    /**
     * Conta logs por tipo de acesso
     */
    long countByTipoAcesso(LogAcessoOpenFinance.TipoAcesso tipoAcesso);
    
    /**
     * Conta logs por categoria de dados
     */
    long countByCategoriaDados(LogAcessoOpenFinance.CategoriaDados categoriaDados);
    
    /**
     * Conta logs por período
     */
    @Query("SELECT COUNT(l) FROM LogAcessoOpenFinance l WHERE l.dataAcesso BETWEEN :inicio AND :fim")
    long countByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs por usuário e período
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.userId = :userId AND l.dataAcesso BETWEEN :inicio AND :fim")
    List<LogAcessoOpenFinance> findByUsuarioEPeriodo(@Param("userId") Long userId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs por cliente e período
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.clientId = :clientId AND l.dataAcesso BETWEEN :inicio AND :fim")
    List<LogAcessoOpenFinance> findByClienteEPeriodo(@Param("clientId") String clientId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs por endpoint e período
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.endpoint = :endpoint AND l.dataAcesso BETWEEN :inicio AND :fim")
    List<LogAcessoOpenFinance> findByEndpointEPeriodo(@Param("endpoint") String endpoint, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs por tipo de acesso e período
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.tipoAcesso = :tipoAcesso AND l.dataAcesso BETWEEN :inicio AND :fim")
    List<LogAcessoOpenFinance> findByTipoAcessoEPeriodo(@Param("tipoAcesso") LogAcessoOpenFinance.TipoAcesso tipoAcesso, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs por categoria de dados e período
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.categoriaDados = :categoriaDados AND l.dataAcesso BETWEEN :inicio AND :fim")
    List<LogAcessoOpenFinance> findByCategoriaDadosEPeriodo(@Param("categoriaDados") LogAcessoOpenFinance.CategoriaDados categoriaDados, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs por IP e período
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.ipAddress = :ipAddress AND l.dataAcesso BETWEEN :inicio AND :fim")
    List<LogAcessoOpenFinance> findByIpEPeriodo(@Param("ipAddress") String ipAddress, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs por device ID e período
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.deviceId = :deviceId AND l.dataAcesso BETWEEN :inicio AND :fim")
    List<LogAcessoOpenFinance> findByDeviceEPeriodo(@Param("deviceId") String deviceId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs por geolocalização e período
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.geolocation = :geolocation AND l.dataAcesso BETWEEN :inicio AND :fim")
    List<LogAcessoOpenFinance> findByGeolocationEPeriodo(@Param("geolocation") String geolocation, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs por nível de risco e período
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.riskLevel = :riskLevel AND l.dataAcesso BETWEEN :inicio AND :fim")
    List<LogAcessoOpenFinance> findByRiskLevelEPeriodo(@Param("riskLevel") String riskLevel, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca logs com tempo de resposta alto
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.responseTimeMs > :tempoMinimo")
    List<LogAcessoOpenFinance> findLogsComTempoRespostaAlto(@Param("tempoMinimo") Long tempoMinimo);
    
    /**
     * Busca logs com tamanho de resposta alto
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.responseSizeBytes > :tamanhoMinimo")
    List<LogAcessoOpenFinance> findLogsComTamanhoRespostaAlto(@Param("tamanhoMinimo") Long tamanhoMinimo);
    
    /**
     * Busca logs com tamanho de requisição alto
     */
    @Query("SELECT l FROM LogAcessoOpenFinance l WHERE l.requestSizeBytes > :tamanhoMinimo")
    List<LogAcessoOpenFinance> findLogsComTamanhoRequisicaoAlto(@Param("tamanhoMinimo") Long tamanhoMinimo);
}
