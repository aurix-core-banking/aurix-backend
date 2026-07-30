package com.aurix.platform.platform.service;

import com.aurix.platform.shared.event.ClienteAtualizadoEvent;
import com.aurix.platform.shared.event.ClienteCriadoEvent;
import com.aurix.platform.shared.event.ClienteStatusAlteradoEvent;
import com.aurix.platform.shared.event.ConsignadoContratoAssinadoEvent;
import com.aurix.platform.shared.event.FinanciamentoContratoAssinadoEvent;
import com.aurix.platform.shared.event.KycAprovadoEvent;
import com.aurix.platform.shared.event.KycRejeitadoEvent;
import com.aurix.platform.shared.event.NotificacaoEnviadaEvent;
import com.aurix.platform.shared.event.NotificacaoFalhouEvent;
import com.aurix.platform.shared.event.OcorrenciaFraudEvent;
import com.aurix.platform.shared.event.ScoreAlteradoEvent;
import com.aurix.platform.shared.event.SegurosApoliceEmitidaEvent;
import com.aurix.platform.shared.event.SolicitacaoCreditoCriadaEvent;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.shared.event.TransacaoBloqueadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificacaoConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificacaoConsumer.class);
    private final NotificacaoService notificacaoService;

    public NotificacaoConsumer(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @KafkaListener(topics = Topics.CUSTOMER_CLIENTE_CRIADO, groupId = "aurix-notification-group")
    public void onClienteCriado(ClienteCriadoEvent event) {
        Long clienteId = event.getClienteId();
        if (clienteId == null) {
            log.warn("Evento CUSTOMER_CLIENTE_CRIADO sem clienteId, ignorando");
            return;
        }
        Map<String, String> variaveis = new HashMap<>();
        variaveis.put("clienteId", String.valueOf(clienteId));
        if (event.getNome() != null) {
            variaveis.put("nome", event.getNome());
        }
        if (event.getDocumento() != null) {
            variaveis.put("documento", event.getDocumento());
        }
        String destinatario = "cliente-" + clienteId;
        notificacaoService.enviar(clienteId, "cliente_criado", destinatario, variaveis);
    }

    @KafkaListener(topics = Topics.KYC_SOLICITACAO_APROVADA, groupId = "aurix-notification-group")
    public void onKycAprovado(KycAprovadoEvent event) {
        Long clienteId = event.getClienteId();
        if (clienteId == null) {
            log.warn("Evento KYC_SOLICITACAO_APROVADA sem clienteId, ignorando");
            return;
        }
        Map<String, String> variaveis = new HashMap<>();
        variaveis.put("clienteId", String.valueOf(clienteId));
        notificacaoService.enviar(clienteId, "kyc_aprovado", "cliente-" + clienteId, variaveis);
    }

    @KafkaListener(topics = Topics.KYC_SOLICITACAO_REJEITADA, groupId = "aurix-notification-group")
    public void onKycRejeitado(KycRejeitadoEvent event) {
        Long clienteId = event.getClienteId();
        if (clienteId == null) {
            log.warn("Evento KYC_SOLICITACAO_REJEITADA sem clienteId, ignorando");
            return;
        }
        Map<String, String> variaveis = new HashMap<>();
        variaveis.put("clienteId", String.valueOf(clienteId));
        if (event.getMotivo() != null) {
            variaveis.put("motivo", event.getMotivo());
        }
        notificacaoService.enviar(clienteId, "kyc_rejeitado", "cliente-" + clienteId, variaveis);
    }

    @KafkaListener(topics = Topics.FRAUD_TRANSACAO_BLOQUEADA, groupId = "aurix-notification-group")
    public void onFraudeTransacaoBloqueada(TransacaoBloqueadaEvent event) {
        Long clienteId = event.getClienteId();
        if (clienteId == null) {
            log.warn("Evento FRAUD_TRANSACAO_BLOQUEADA sem clienteId, ignorando");
            return;
        }
        Map<String, String> variaveis = new HashMap<>();
        variaveis.put("clienteId", String.valueOf(clienteId));
        if (event.getTransacaoRef() != null) {
            variaveis.put("transacaoRef", event.getTransacaoRef());
        }
        notificacaoService.enviar(clienteId, "fraude_transacao_bloqueada", "cliente-" + clienteId, variaveis);
    }

    @KafkaListener(topics = Topics.CUSTOMER_CLIENTE_ATUALIZADO, groupId = "aurix-notification-group")
    public void onClienteAtualizado(ClienteAtualizadoEvent event) {
        Long clienteId = event.getClienteId();
        if (clienteId == null) {
            log.warn("Evento CUSTOMER_CLIENTE_ATUALIZADO sem clienteId, ignorando");
            return;
        }
        Map<String, String> variaveis = new HashMap<>();
        variaveis.put("clienteId", String.valueOf(clienteId));
        if (event.getStatus() != null) {
            variaveis.put("status", event.getStatus());
        }
        notificacaoService.enviar(clienteId, "cliente_atualizado", "cliente-" + clienteId, variaveis);
    }

    @KafkaListener(topics = Topics.CUSTOMER_CLIENTE_STATUS_ALTERADO, groupId = "aurix-notification-group")
    public void onClienteStatusAlterado(ClienteStatusAlteradoEvent event) {
        Long clienteId = event.getClienteId();
        if (clienteId == null) {
            log.warn("Evento CUSTOMER_CLIENTE_STATUS_ALTERADO sem clienteId, ignorando");
            return;
        }
        Map<String, String> variaveis = new HashMap<>();
        variaveis.put("clienteId", String.valueOf(clienteId));
        if (event.getStatusAtual() != null) {
            variaveis.put("statusAtual", event.getStatusAtual());
        }
        if (event.getStatusAnterior() != null) {
            variaveis.put("statusAnterior", event.getStatusAnterior());
        }
        notificacaoService.enviar(clienteId, "cliente_status_alterado", "cliente-" + clienteId, variaveis);
    }

    @KafkaListener(topics = Topics.FRAUD_SCORE_ALTERADO, groupId = "aurix-notification-group")
    public void onScoreAlterado(ScoreAlteradoEvent event) {
        Long clienteId = event.getClienteId();
        if (clienteId == null) {
            log.warn("Evento FRAUD_SCORE_ALTERADO sem clienteId, ignorando");
            return;
        }
        Map<String, String> variaveis = new HashMap<>();
        variaveis.put("clienteId", String.valueOf(clienteId));
        if (event.getScore() != null) {
            variaveis.put("score", String.valueOf(event.getScore()));
        }
        if (event.getRisco() != null) {
            variaveis.put("risco", event.getRisco());
        }
        notificacaoService.enviar(clienteId, "score_alterado", "cliente-" + clienteId, variaveis);
    }

    @KafkaListener(topics = Topics.FRAUD_OCORRENCIA_CRIADA, groupId = "aurix-notification-group")
    public void onOcorrenciaFraud(OcorrenciaFraudEvent event) {
        Long clienteId = event.getClienteId();
        if (clienteId == null) {
            log.warn("Evento FRAUD_OCORRENCIA_CRIADA sem clienteId, ignorando");
            return;
        }
        Map<String, String> variaveis = new HashMap<>();
        variaveis.put("clienteId", String.valueOf(clienteId));
        if (event.getTipo() != null) {
            variaveis.put("tipo", event.getTipo());
        }
        if (event.getStatus() != null) {
            variaveis.put("status", event.getStatus());
        }
        notificacaoService.enviar(clienteId, "ocorrencia_fraude", "cliente-" + clienteId, variaveis);
    }

    @KafkaListener(topics = Topics.CREDIT_SOLICITACAO_CRIADA, groupId = "aurix-notification-group")
    public void onSolicitacaoCreditoCriada(SolicitacaoCreditoCriadaEvent event) {
        Long clienteId = event.getClienteId();
        if (clienteId == null) {
            log.warn("Evento CREDIT_SOLICITACAO_CRIADA sem clienteId, ignorando");
            return;
        }
        Map<String, String> variaveis = new HashMap<>();
        variaveis.put("clienteId", String.valueOf(clienteId));
        if (event.getValor() != null) {
            variaveis.put("valor", event.getValor().toPlainString());
        }
        if (event.getTipoCredito() != null) {
            variaveis.pu                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 