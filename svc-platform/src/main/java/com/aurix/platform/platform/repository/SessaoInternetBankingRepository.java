package com.aurix.platform.platform.repository;

import com.aurix.platform.platform.entity.SessaoInternetBanking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository para Sessões de Internet Banking
 */
@Repository
public interface SessaoInternetBankingRepository extends JpaRepository<SessaoInternetBanking, Long> {
    
    /**
     * Busca sessão por ID da sessão
     */
    Optional<SessaoInternetBanking> findBySessaoId(String sessaoId);
    
    /**
     * Busca sessões ativas por cliente
     */
    @Query("SELECT s FROM SessaoInternetBanking s WHERE s.clienteId = :clienteId AND s.status = 'ATIVA'")
    List<SessaoInternetBanking> findSessoesAtivasPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Busca sessões expiradas
     */
    @Query("SELECT s FROM SessaoInternetBanking s WHERE s.dataExpiracao < :dataAtual AND s.status = 'ATIVA'")
    List<SessaoInternetBanking> findSessoesExpiradas(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca sessões por IP
     */
    List<SessaoInternetBanking> findByIpAddress(String ipAddress);
    
    /**
     * Busca sessões por dispositivo
     */
    List<SessaoInternetBanking> findByDeviceId(String deviceId);
    
    /**
     * Conta sessões ativas por cliente
     */
    @Query("SELECT COUNT(s) FROM SessaoInternetBanking s WHERE s.clienteId = :clienteId AND s.status = 'ATIVA'")
    long countSessoesAtivasPorCliente(@Param("clienteId") String clienteId);
    
    /**
     * Busca sessões por período
     */
    @Query("SELECT s FROM SessaoInternetBanking s WHERE s.dataLogin BETWEEN :dataInicio AND :dataFim")
    List<SessaoInternetBanking> findSessoesPorPeriodo(@Param("dataInicio") LocalDateTime dataInicio, 
                                                      @Param("dataFim") LocalDateTime dataFim);
    
    /**
     * Busca sessões bloqueadas
     */
    @Query("SELECT s FROM SessaoInternetBanking s WHERE s.bloqueado = true")
    List<SessaoInternetBanking> findSessoesBloqueadas();
    
    /**
     * Busca sessões com muitas tentativas de falha
     */
    @Query("SELECT s FROM SessaoInternetBanking s WHERE s.tentativasFalha >= :tentativasMinimas")
    List<SessaoInternetBanking> findSessoesComMuitasTentativasFalha(@Param("tentativasMinimas") Integer tentativasMinimas);
}
