package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.entity.AuditLog;
import com.aurix.platform.banking.core.repository.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    private AuditLogRepository auditLogRepository;

    private AuditService auditService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        auditService = new AuditService(auditLogRepository, objectMapper);
        lenient().when(auditLogRepository.save(any(AuditLog.class)))
            .thenAnswer(i -> i.getArgument(0));
    }

    @Test
    void registrar_deveSalvarLogComValores() {
        Map<String, Object> anterior = Map.of("saldo", 1000);
        Map<String, Object> novo = Map.of("saldo", 1500);

        auditService.registrar("ALTERACAO_SALDO", "Conta", "42",
            "admin", "TENANT_001", anterior, novo, "127.0.0.1");

        verify(auditLogRepository).save(argThat(log ->
            "ALTERACAO_SALDO".equals(log.getAcao()) &&
            "Conta".equals(log.getEntidade()) &&
            "42".equals(log.getEntidadeId()) &&
            "admin".equals(log.getUsuario()) &&
            "TENANT_001".equals(log.getTenantId()) &&
            log.getValorAnterior() != null &&
            log.getValorNovo() != null &&
            "127.0.0.1".equals(log.getIpOrigem())
        ));
    }

    @Test
    void buscarPorEntidade_deveRetornarLogs() {
        AuditLog log = new AuditLog();
        log.setAcao("CRIACAO");
        log.setEntidade("Conta");
        log.setEntidadeId("1");
        when(auditLogRepository.findByEntidadeAndEntidadeIdOrderByDataCriacaoDesc("Conta", "1"))
            .thenReturn(List.of(log));

        List<AuditLog> logs = auditService.buscarPorEntidade("Conta", "1");

        assertEquals(1, logs.size());
        assertEquals("CRIACAO", logs.get(0).getAcao());
    }

    @Test
    void listarTodos_deveRetornarTodosLogs() {
        when(auditLogRepository.findAll()).thenReturn(List.of(new AuditLog(), new AuditLog()));

        List<AuditLog> logs = auditService.listarTodos();

        assertEquals(2, logs.size());
    }
}
