package com.aurix.platform.banking.core.repository;

import com.aurix.platform.banking.core.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntidadeAndEntidadeIdOrderByDataCriacaoDesc(String entidade, String entidadeId);
    List<AuditLog> findByEntidadeOrderByDataCriacaoDesc(String entidade);
    List<AuditLog> findByAcaoOrderByDataCriacaoDesc(String acao);
}
