package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.SessaoMobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository para Sessões Mobile
 */
@Repository
public interface SessaoMobileRepository extends JpaRepository<SessaoMobile, Long> {
    
    /**
     * Busca sessão por ID
     */
    Optional<SessaoMobile> findBySessaoId(String sessaoId);
    
    /**
     * Busca sessões por dispositivo
     */
    List<SessaoMobile> findByDispositivoId(String dispositivoId);
    
    /**
     * Busca sessões por cliente
     */
    List<SessaoMobile> findByClienteId(String clienteId);
    
    /**
     * Busca sessões ativas por cliente
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.clienteId = :clienteId AND s.status = 'ATIVA'")
    List<SessaoMobile> findSessoesAtivasPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Busca sessões por status
     */
    List<SessaoMobile> findByStatus(SessaoMobile.StatusSessao status);
    
    /**
     * Busca sessões expiradas
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.dataExpiracao < :dataAtual AND s.status = 'ATIVA'")
    List<SessaoMobile> findSessoesExpiradas(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca sessões por período
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.dataLogin BETWEEN :dataInicio AND :dataFim")
    List<SessaoMobile> findSessoesPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                             @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca sessões por localização
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.latitude BETWEEN :latMin AND :latMax AND s.longitude BETWEEN :lonMin AND :lonMax")
    List<SessaoMobile> findSessoesPorLocalizacao(@Param("latMin") Double latMin, 
                                                 @Param("latMax") Double latMax,
                                                 @Param("lonMin") Double lonMin, 
                                                 @Param("lonMax") Double lonMax);
    
    /**
     * Busca sessões com biometria verificada
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.biometricoVerificado = true")
    List<SessaoMobile> findSessoesComBiometriaVerificada();
    
    /**
     * Busca sessões com Face ID verificada
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.faceIdVerificado = true")
    List<SessaoMobile> findSessoesComFaceIdVerificada();
    
    /**
     * Busca sessões com Touch ID verificada
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.touchIdVerificado = true")
    List<SessaoMobile> findSessoesComTouchIdVerificada();
    
    /**
     * Busca sessões com PIN verificado
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.pinVerificado = true")
    List<SessaoMobile> findSessoesComPinVerificado();
    
    /**
     * Busca sessões com localização verificada
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.localizacaoVerificada = true")
    List<SessaoMobile> findSessoesComLocalizacaoVerificada();
    
    /**
     * Busca sessões bloqueadas
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.bloqueado = true")
    List<SessaoMobile> findSessoesBloqueadas();
    
    /**
     * Busca sessões em background
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.status = 'BACKGROUND'")
    List<SessaoMobile> findSessoesBackground();
    
    /**
     * Busca sessões por versão do app
     */
    List<SessaoMobile> findByAppVersion(String appVersion);
    
    /**
     * Busca sessões por sistema operacional
     */
    List<SessaoMobile> findByOsVersion(String osVersion);
    
    /**
     * Busca sessões por modelo do dispositivo
     */
    List<SessaoMobile> findByDeviceModel(String deviceModel);
    
    /**
     * Busca sessões por fabricante do dispositivo
     */
    List<SessaoMobile> findByDeviceManufacturer(String deviceManufacturer);
    
    /**
     * Busca sessões por IP
     */
    List<SessaoMobile> findByIpAddress(String ipAddress);
    
    /**
     * Busca sessões com muitas tentativas de falha
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.tentativasFalha >= :tentativasMinimas")
    List<SessaoMobile> findSessoesComMuitasTentativasFalha(@Param("tentativasMinimas") Integer tentativasMinimas);
    
    /**
     * Conta sessões ativas por cliente
     */
    @Query("SELECT COUNT(s) FROM SessaoMobile s WHERE s.clienteId = :clienteId AND s.status = 'ATIVA'")
    long countSessoesAtivasPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Conta sessões por dispositivo
     */
    @Query("SELECT COUNT(s) FROM SessaoMobile s WHERE s.dispositivoId = :dispositivoId")
    long countSessoesPorDispositivo(@Param("dispositivoId") String dispositivoId);
    
    /**
     * Busca últimas sessões do cliente
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.clienteId = :clienteId ORDER BY s.dataLogin DESC")
    List<SessaoMobile> findUltimasSessoesPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Busca sessões com notificações push ativas
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.pushNotificationsAtivas = true")
    List<SessaoMobile> findSessoesComPushNotificationsAtivas();
    
    /**
     * Busca sessões com refresh em background habilitado
     */
    @Query("SELECT s FROM SessaoMobile s WHERE s.backgroundRefreshHabilitado = true")
    List<SessaoMobile> findSessoesComBackgroundRefreshHabilitado();
}
