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
        Topics.CONSIGNADO_PARCELA_DEBITADA,
        Topics.CONSIGNADO_CONTRATO_LIQUIDADO,
        Topics.FINANCIAMENTO_CONTRATO_ASSINADO,
        Topics.FINANCIAMENTO_SIMULACAO_REALIZADA,
        Topics.FINANCIAMENTO_PARCELA_PAGA,
        Topics.FINANCIAMENTO_CONTRATO_LIQUIDADO,
        Topics.FINANCIAMENTO_GARANTIA_REGISTRADA,
        Topics.INVESTIMENTO_ORDEM_EXECUTADA,
        Topics.SEGUROS_APOLICE_EMITIDA,
        Topics.PRODUTO_CRIADO,
        Topics.PRODUTO_ATUALIZADO,
        Topics.PRODUTO_DESCONTINUADO,
        Topics.CONTRATO_CRIADO,
        Topics.CONTRATO_ASSINADO,
        Topics.CONTRATO_LIQUIDADO,
        Topics.CONTRATO_CANCELADO,
        Topics.CAMBIO_COTACAO_ATUALIZADA,
        Topics.CAMBIO_CONTRATO_FECHADO,
        Topics.CAMBIO_CONTRATO_LIQUIDADO,
        Topics.CAMBIO_REMESSA_PROCESSADA,
        Topics.POUPANCA_CONTA_CRIADA,
        Topics.POUPANCA_DEPOSITO_REALIZADO,
        Topics.POUPANCA_SAQUE_REALIZADO,
        Topics.POUPANCA_RENDIMENTO_CREDITADO,
        Topics.SALARIO_CONTA_CRIADA,
        Topics.SALARIO_CREDITADO,
        Topics.SALARIO_PORTABILIDADE_SOLICITADA,
        Topics.LGPD_DADOS_EXCLUIDOS,
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
