package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.AuditoriaCompliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditoriaComplianceRepository extends JpaRepository<AuditoriaCompliance, Long> {

    List<AuditoriaCompliance> findByRegulacaoId(Long regulacaoId);

    List<AuditoriaCompliance> findByStatus(AuditoriaCompliance.StatusAuditoria status);

    List<AuditoriaCompliance> findByTipoAuditoria(AuditoriaCompliance.TipoAuditoria tipoAuditoria);

    List<AuditoriaCompliance> findByAuditorResponsavel(String auditorResponsavel);

    @Query("SELECT a FROM AuditoriaCompliance a WHERE a.status = 'EM_ANDAMENTO'")
    List<AuditoriaCompliance> findAuditoriasEmAndamento();

    @Query("SELECT a FROM AuditoriaCompliance a WHERE a.status = 'CONCLUIDA'")
    List<AuditoriaCompliance> findAuditoriasConcluidas();

    @Query("SELECT a FROM AuditoriaCompliance a WHERE a.dataFim < :dataAtual AND a.status != 'CONCLUIDA'")
    List<AuditoriaCompliance> findAuditoriasAtrasadas(@Param("dataAtual") LocalDateTime dataAtual);

    @Query("SELECT a FROM AuditoriaCompliance a WHERE a.dataInicio BETWEEN :inicio AND :fim")
    List<AuditoriaCompliance> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT a FROM AuditoriaCompliance a WHERE a.dataInicio BETWEEN :inicio AND :fim AND a.status = 'PLANEJADA'")
    List<AuditoriaCompliance> findAuditoriasProximasInicio(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    long countByStatus(AuditoriaCompliance.StatusAuditoria status);

    long countByTipoAuditoria(AuditoriaCompliance.TipoAuditoria tipoAuditoria);

    long countByRegulacaoId(Long regulacaoId);
}
