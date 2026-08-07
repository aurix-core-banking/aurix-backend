package com.aurix.platform.shared.repository;

import com.aurix.platform.shared.entity.SessaoAuditoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessaoAuditoriaRepository extends JpaRepository<SessaoAuditoria, Long> {

    List<SessaoAuditoria> findByUsuarioId(Long usuarioId);

    List<SessaoAuditoria> findByStatus(SessaoAuditoria.StatusSessao status);

    List<SessaoAuditoria> findByIpOrigem(String ipOrigem);

    List<SessaoAuditoria> findByTokenSessao(String tokenSessao);

    @Query("SELECT s FROM SessaoAuditoria s WHERE s.dataInicio BETWEEN :inicio AND :fim")
    List<SessaoAuditoria> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT s FROM SessaoAuditoria s WHERE s.status = 'ATIVA'")
    List<SessaoAuditoria> findSessoesAtivas();

    @Query("SELECT s FROM SessaoAuditoria s WHERE s.status = 'ENCERRADA'")
    List<SessaoAuditoria> findSessoesEncerradas();

    @Query("SELECT s FROM SessaoAuditoria s WHERE s.status = 'EXPIRADA'")
    List<SessaoAuditoria> findSessoesExpiradas();

    @Query("SELECT s FROM SessaoAuditoria s WHERE s.usuarioId = :usuarioId AND s.dataInicio BETWEEN :inicio AND :fim")
    List<SessaoAuditoria> findByUsuarioAndPeriodo(@Param("usuarioId") Long usuarioId, @Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query("SELECT s FROM SessaoAuditoria s WHERE s.dataFim IS NOT NULL AND (EXTRACT(EPOCH FROM s.dataFim) - EXTRACT(EPOCH FROM s.dataInicio)) / 60 > :minutos")
    List<SessaoAuditoria> findSessoesLongas(@Param("minutos") Long minutos);

    long countByUsuarioId(Long usuarioId);

    long countByStatus(SessaoAuditoria.StatusSessao status);

    long countByIpOrigem(String ipOrigem);
}
