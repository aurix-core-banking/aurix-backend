package com.aurix.platform.platform.service;

import com.aurix.platform.shared.dto.LogAuditoriaDTO;
import com.aurix.platform.shared.event.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AuditEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(AuditEventConsumer.class);
    private final LogAuditoriaService logAuditoriaService;

    public AuditEventConsumer(LogAuditoriaService logAuditoriaService) {
        this.logAuditoriaService = logAuditoriaService;
    }

    @KafkaListener(topics = {
        Topics.CONTA_CRIADA,
        Topics.CONTA_ATUALIZADA,
        Topics.CONTA_BLOQUEADA,
        Topics.TRANSACAO_REALIZADA,
        Topics.TRANSACAO_LIQUIDADA,
        Topics.TRANSACAO_CONCILIADA,
        Topics.IMPOSTO_CALCULADO,
        Topics.IMPOSTO_REGISTRADO,
        Topics.LIQUIDEZ_PROCESSADA,
        Topics.LIQUIDEZ_REJEITADA,
        Topics.FATURA_EMITIDA,
        Topics.FATURA_PAGA,
        Topics.RELATORIO_GERADO,
        Topics.RELATORIO_ENVIADO,
        Topics.CUSTOMER_CLIENTE_CRIADO,
        Topics.CUSTOMER_CLIENTE_ATUALIZADO,
        Topics.CUSTOMER_CLIENTE_STATUS_ALTERADO,
        Topics.KYC_SOLICITACAO_APROVADA,
        Topics.KYC_SOLICITACAO_REJEITADA,
        Topics.FRAUD_TRANSACAO_BLOQUEADA,
        Topics.FRAUD_OCORRENCIA_CRIADA,
        Topics.FRAUD_SCORE_ALTERADO,
        Topics.NOTIFICATION_NOTIFICACAO_ENVIADA,
        Topics.NOTIFICATION_NOTIFICACAO_FALHOU,
        Topics.CREDIT_SOLICITACAO_CRIADA,
        Topics.CARTOES_CARTAO_EMITIDO,
        Topics.CARTOES_TRANSACAO_AUTORIZADA,
        Topics.CARTOES_TRANSACAO_ESTORNADA,
        Topics.CARTOES_FATURA_FECHADA,
        Topics.CARTOES_FATURA_PAGA,
        Topics.CONSIGNADO_CONTRATO_ASSINADO,
        Topics.FINANCIAMENTO_CONTRATO_ASSINADO,
        Topics.INVESTIMENTO_ORDEM_EXECUTADA,
        Topics.SEGUROS_APOLICE_EMITIDA,
    }, groupId = "aurix-audit-group")
    public void onEvent(Object event) {
        try {
            LogAuditoriaDTO dto = new LogAuditoriaDTO();
            String eventJson = toStringSafely(event);
            dto.setAcao("EVENTO_KAFKA");
            dto.setDescricao("Evento recebido: " + event.getClass().getSimpleName());
            dto.setEntidade("Event");
            dto.setDadosExtras(eventJson);
            logAuditoriaService.criarLogAuditoria(dto);
        } catch (Exception e) {
            log.warn("Erro ao processar evento de auditoria: {}", e.getMessage());
        }
    }

    private String toStringSafely(Object obj) {
        try {
            return obj != null ? obj.toString() : "null";
        } catch (Exception e) {
            return "Erro ao serializar evento: " + e.getMessage();
        }
    }
}
