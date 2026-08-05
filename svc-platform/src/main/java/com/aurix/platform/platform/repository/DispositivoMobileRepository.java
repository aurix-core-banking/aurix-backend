package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.DispositivoMobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository para Dispositivos Mobile
 */
@Repository
public interface DispositivoMobileRepository extends JpaRepository<DispositivoMobile, Long> {
    
    /**
     * Busca dispositivo por ID
     */
    Optional<DispositivoMobile> findByDispositivoId(String dispositivoId);
    
    /**
     * Busca dispositivos por cliente
     */
    List<DispositivoMobile> findByClienteId(String clienteId);
    
    /**
     * Busca dispositivos ativos por cliente
     */
    @Query("SELECT d FROM DispositivoMobile d WHERE d.clienteId = :clienteId AND d.status = 'ATIVO'")
    List<DispositivoMobile> findDispositivosAtivosPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Busca dispositivo por IMEI
     */
    Optional<DispositivoMobile> findByImei(String imei);
    
    /**
     * Busca dispositivos por device token
     */
    Optional<DispositivoMobile> findByDeviceToken(String deviceToken);
    
    /**
     * Busca dispositivos por fingerprint
     */
    Optional<DispositivoMobile> findByDeviceFingerprint(String deviceFingerprint);
    
    /**
     * Busca dispositivos por tipo
     */
    List<DispositivoMobile> findByTipoDispositivo(DispositivoMobile.TipoDispositivo tipoDispositivo);
    
    /**
     * Busca dispositivos por status
     */
    List<DispositivoMobile> findByStatus(DispositivoMobile.StatusDispositivo status);
    
    /**
     * Busca dispositivos por marca
     */
    List<DispositivoMobile> findByMarca(String marca);
    
    /**
     * Busca dispositivos por sistema operacional
     */
    List<DispositivoMobile> findBySistemaOperacional(String sistemaOperacional);
    
    /**
     * Busca dispositivos com biometria habilitada
     */
    @Query("SELECT d FROM DispositivoMobile d WHERE d.biometricoHabilitado = true")
    List<DispositivoMobile> findDispositivosComBiometriaHabilitada();
    
    /**
     * Busca dispositivos com Face ID habilitado
     */
    @Query("SELECT d FROM DispositivoMobile d WHERE d.faceIdHabilitado = true")
    List<DispositivoMobile> findDispositivosComFaceIdHabilitado();
    
    /**
     * Busca dispositivos com Touch ID habilitado
     */
    @Query("SELECT d FROM DispositivoMobile d WHERE d.touchIdHabilitado = true")
    List<DispositivoMobile> findDispositivosComTouchIdHabilitado();
    
    /**
     * Busca dispositivos bloqueados
     */
    @Query("SELECT d FROM DispositivoMobile d WHERE d.bloqueado = true")
    List<DispositivoMobile> findDispositivosBloqueados();
    
    /**
     * Busca dispositivos por período de registro
     */
    @Query("SELECT d FROM DispositivoMobile d WHERE d.dataRegistro BETWEEN :dataInicio AND :dataFim")
    List<DispositivoMobile> findDispositivosPorPeriodoRegistro(@Param("dataInicio") LocalDateTime dataInicio, 
                                                               @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca dispositivos por período de último acesso
     */
    @Query("SELECT d FROM DispositivoMobile d WHERE d.dataUltimoAcesso BETWEEN :dataInicio AND :dataFim")
    List<DispositivoMobile> findDispositivosPorPeriodoUltimoAcesso(@Param("dataInicio") LocalDateTime dataInicio, 
                                                                   @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Conta dispositivos por cliente
     */
    @Query("SELECT COUNT(d) FROM DispositivoMobile d WHERE d.clienteId = :clienteId")
    long countDispositivosPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Conta dispositivos ativos por cliente
     */
    @Query("SELECT COUNT(d) FROM DispositivoMobile d WHERE d.clienteId = :clienteId AND d.status = 'ATIVO'")
    long countDispositivosAtivosPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Busca dispositivos por localização (proximidade)
     */
    @Query("SELECT d FROM DispositivoMobile d WHERE d.latitude BETWEEN :latMin AND :latMax AND d.longitude BETWEEN :lonMin AND :lonMax")
    List<DispositivoMobile> findDispositivosPorLocalizacao(@Param("latMin") Double latMin, 
                                                           @Param("latMax") Double latMax,
                                                           @Param("lonMin") Double lonMin, 
                                                           @Param("lonMax") Double lonMax);
    
    /**
     * Busca dispositivos com muitas tentativas de falha
     */
    @Query("SELECT d FROM DispositivoMobile d WHERE d.tentativasFalha >= :tentativasMinimas")
    List<DispositivoMobile> findDispositivosComMuitasTentativasFalha(@Param("tentativasMinimas") Integer tentativasMinimas);
    
    /**
     * Busca dispositivos expirados
     */
    @Query("SELECT d FROM DispositivoMobile d WHERE d.dataExpiracao < :dataAtual")
    List<DispositivoMobile> findDispositivosExpirados(@Param("dataAtual") LocalDateTime dataAtual);
}
