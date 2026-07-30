package com.aurix.platform.platform.repository;

import com.aurix.platform.shared.entity.SessaoAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para SessaoAuditoria
 */
@Repository
public interface SessaoAuditoriaRepository extends JpaRepository<SessaoAuditoria, Long> {
    
    /**
     * Busca sessões por usuário
     */
    List<SessaoAuditoria> findByUsuarioId(Long usuarioId);
    
    /**
     * Busca sessões por status
     */
    List<SessaoAuditoria> findByStatus(SessaoAuditoria.StatusSessao status);
    
    /**
     * Busca sessões por IP
     */
    List<SessaoAuditoria> findByIpOrigem(String ipOrigem);
    
    /**
     * Busca sessões por token
     */
    List<SessaoAuditoria> findByTokenSessao(String tokenSessao);
    
    /**
     * Busca sessões por período
     */
    @Query("SELECT s FROM SessaoAuditoria s WHERE s.dataInicio BETWEEN :inicio AND :fim")
    List<SessaoAuditoria> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca sessões ativas
     */
    @Query("SELECT s FROM SessaoAuditoria s WHERE s.status = 'ATIVA'")
    List<SessaoAuditoria> findSessoesAtivas();
    
    /**
     * Busca sessões encerradas
     */
    @Query("SELECT s FROM SessaoAuditoria s WHERE s.status = 'ENCERRADA'")
    List<SessaoAuditoria> findSessoesEncerradas();
    
    /**
     * Busca sessões expiradas
     */
    @Query("SELECT s FROM SessaoAuditoria s WHERE s.status = 'EXPIRADA'")
    List<SessaoAuditoria> findSessoesExpiradas();
    
    /**
     * Busca sessões por usuário e período
     */
    @Query("SELECT s FROM SessaoAuditoria s WHERE s.usuarioId = :usuarioId AND s.dataInicio BETWEEN :inicio AND :fim")
    List<SessaoAuditoria> findByUsuarioAndPeriodo(@Param("usuarioId") Long usuarioId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca sessões longas (mais de X minutos)
     */
    @Query("SELECT s FROM SessaoAuditoria s WHERE s.dataFim IS NOT NULL AND (EXTRACT(EPOCH FROM s.dataFim) - EXTRACT(EPOCH FROM s.dataInicio)) / 60 > :minutos")
    List<SessaoAuditoria> findSessoesLongas(@Param("minutos") Long minutos);
    
    /**
     * Conta sessões por usuário
     */
    long countByUsuarioId(Long usuarioId);
    
    /**
     * Conta sessões por status
     */
    long countByStatus(SessaoAuditoria.StatusSessao status);
    
    /**
     * Conta sessões por IP
     */
    long countByIpOrigem(String ipOrigem);
}
