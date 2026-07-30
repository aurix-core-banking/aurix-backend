package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.entity.AuditLog;
import com.aurix.platform.banking.core.repository.AuditLogRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;

    public AuditService(AuditLogRepository auditLogRepository, ObjectMapper objectMapper) {
        this.auditLogRepository = auditLogRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void registrar(String acao, String entidade, String entidadeId,
                          String usuario, String tenantId,
                          Object valorAnterior, Object valorNovo,
                          String ipOrigem) {
        AuditLog log = new AuditLog();
        log.setAcao(acao);
        log.setEntidade(entidade);
        log.setEntidadeId(entidadeId);
        log.setUsuario(usuario);
        log.setTenantId(tenantId);
        log.setValorAnterior(toJson(valorAnterior));
        log.setValorNovo(toJson(valorNovo));
        log.setIpOrigem(ipOrigem);
        auditLogRepository.save(log);
    }

    @Transactional(readOnly = true)
    public List<AuditLog> buscarPorEntidade(String entidade, String entidadeId) {
        return auditLogRepository.findByEntidadeAndEntidadeIdOrderByDataCriacaoDesc(entidade, entidadeId);
    }

    @Transactional(readOnly = true)
    public List<AuditLog> listarTodos() {
        return auditLogRepository.findAll();
    }

    private String toJson(Object obj) {
        if (obj == null) return null;
        if (obj instanceof String) return (String) obj;
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString();
        }
    }
}
