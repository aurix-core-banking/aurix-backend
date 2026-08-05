package com.aurix.platform.compliance.repository;

import com.aurix.platform.shared.entity.AuditoriaCompliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para AuditoriaCompliance
 */
@Repository
public interface AuditoriaComplianceRepository extends JpaRepository<AuditoriaCompliance, Long> {
    
    /**
     * Busca auditorias por regulamentação
     */
    List<AuditoriaCompliance> findByRegulacaoId(Long regulacaoId);
    
    /**
     * Busca auditorias por status
     */
    List<AuditoriaCompliance> findByStatus(AuditoriaCompliance.StatusAuditoria status);
    
    /**
     * Busca auditorias por tipo
     */
    List<AuditoriaCompliance> findByTipoAuditoria(AuditoriaCompliance.TipoAuditoria tipoAuditoria);
    
    /**
     * Busca auditorias por auditor responsável
     */
    List<AuditoriaCompliance> findByAuditorResponsavel(String auditorResponsavel);
    
    /**
     * Busca auditorias em andamento
     */
    @Query("SELECT a FROM AuditoriaCompliance a WHERE a.status = 'EM_ANDAMENTO'")
    List<AuditoriaCompliance> findAuditoriasEmAndamento();
    
    /**
     * Busca auditorias concluídas
     */
    @Query("SELECT a FROM AuditoriaCompliance a WHERE a.status = 'CONCLUIDA'")
    List<AuditoriaCompliance> findAuditoriasConcluidas();
    
    /**
     * Busca auditorias atrasadas
     */
    @Query("SELECT a FROM AuditoriaCompliance a WHERE a.dataFim < :dataAtual AND a.status != 'CONCLUIDA'")
    List<AuditoriaCompliance> findAuditoriasAtrasadas(@Param("dataAtual") LocalDateTime dataAtual);
    
    /**
     * Busca auditorias por período
     */
    @Query("SELECT a FROM AuditoriaCompliance a WHERE a.dataInicio BETWEEN :inicio AND :fim")
    List<AuditoriaCompliance> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Busca auditorias próximas do início
     */
    @Query("SELECT a FROM AuditoriaCompliance a WHERE a.dataInicio BETWEEN :inicio AND :fim AND a.status = 'PLANEJADA'")
    List<AuditoriaCompliance> findAuditoriasProximasInicio(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Conta auditorias por status
     */
    long countByStatus(AuditoriaCompliance.StatusAuditoria status);
    
    /**
     * Conta auditorias por tipo
     */
    long countByTipoAuditoria(AuditoriaCompliance.TipoAuditoria tipoAuditoria);
    
    /**
     * Conta auditorias por regulamentação
     */
    long countByRegulacaoId(Long regulacaoId);
}

