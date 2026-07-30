package com.aurix.platform.shared.eventhub;

import com.aurix.platform.shared.event.BaseEvent;
import com.aurix.platform.shared.event.ContaEvent;
import com.aurix.platform.shared.event.TransacaoEvent;
import com.aurix.platform.shared.event.ImpostoEvent;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Validador de eventos.
 *
 * Valida eventos antes do processamento no Event Hub.
 */
@Component
public class EventValidator {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EventValidator.class);
    private final AtomicLong totalValidations = new AtomicLong(0);
    private final AtomicLong totalValidEvents = new AtomicLong(0);
    private final AtomicLong totalInvalidEvents = new AtomicLong(0);
    /**
     * Margem de tempo futuro permitida em minutos.
     */
    private static final int FUTURE_TIME_MARGIN = 5;
    /**
     * Máximo de dias de idade para um evento.
     */
    private static final int MAX_EVENT_AGE_DAYS = 30;
    /**
     * Limite máximo de valor para transação.
     */
    private static final int MAX_TRANSACTION_LIMIT = 1000000;
    /**
     * Limite mínimo de valor para transação.
     */
    private static final double MIN_TRANSACTION_LIMIT = 0.01;
    /**
     * Limite máximo de saldo de conta.
     */
    private static final int MAX_ACCOUNT_BALANCE_LIMIT = 10000000;

    /**
     * Valida evento.
     *
     * @param event Evento a ser validado
     * @return true se o evento é válido
     */
    public boolean validate(final BaseEvent event) {
        totalValidations.incrementAndGet();
        log.debug("Validando evento: Tipo={}, ID={}", event.getEventType(), event.getEventId());
        try {
            // Validações básicas
            if (!validateBasicFields(event)) {
                return false;
            }
            // Validações específicas por tipo
            if (!validateTypeSpecificFields(event)) {
                return false;
            }
            // Validações de negócio
            if (!validateBusinessRules(event)) {
                return false;
            }
            totalValidEvents.incrementAndGet();
            log.debug("Evento validado com sucesso: {}", event.getEventId());
            return true;
        } catch (Exception e) {
            totalInvalidEvents.incrementAndGet();
            log.error("Erro na validação do evento {}: {}", event.getEventId(), e.getMessage());
            return false;
        }
    }

    /**
     * Valida campos básicos do evento.
     *
     * @param event Evento a ser validado
     * @return true se campos básicos são válidos
     */
    private boolean validateBasicFields(final BaseEvent event) {
        // Validar ID do evento
        if (event.getEventId() == null || event.getEventId().trim().isEmpty()) {
            log.warn("Validação falhou: EventId é obrigatório");
            return false;
        }
        // Validar tipo do evento
        if (event.getEventType() == null || event.getEventType().trim().isEmpty()) {
            log.warn("Validação falhou: EventType é obrigatório");
            return false;
        }
        // Validar timestamp
        if (event.getTimestamp() == null) {
            log.warn("Validação falhou: Timestamp é obrigatório");
            return false;
        }
        // Validar se timestamp não é futuro
        if (event.getTimestamp().isAfter(LocalDateTime.now().plusMinutes(FUTURE_TIME_MARGIN))) {
            log.warn("Validação falhou: Timestamp não pode ser futuro");
            return false;
        }
        // Validar se timestamp não é muito antigo (mais de 30 dias)
        if (event.getTimestamp().isBefore(LocalDateTime.now().minusDays(MAX_EVENT_AGE_DAYS))) {
            log.warn("Validação falhou: Timestamp muito antigo");
            return false;
        }
        // Validar source
        if (event.getSource() == null || event.getSource().trim().isEmpty()) {
            log.warn("Validação falhou: Source é obrigatório");
            return false;
        }
        return true;
    }

    /**
     * Valida campos específicos por tipo de evento.
     *
     * @param event Evento a ser validado
     * @return true se campos específicos são válidos
     */
    private boolean validateTypeSpecificFields(final BaseEvent event) {
        switch (event.getEventType()) {
        case "CONTA_CRIADA": 
        case "CONTA_ATUALIZADA": 
        case "CONTA_BLOQUEADA": 
            return (event instanceof ContaEvent e) && validateContaEvent(e);
        case "TRANSACAO_REALIZADA": 
        case "TRANSACAO_LIQUIDADA": 
        case "TRANSACAO_CONCILIADA": 
            return (event instanceof TransacaoEvent e) && validateTransacaoEvent(e);
        case "IMPOSTO_CALCULADO": 
        case "IMPOSTO_REGISTRADO": 
            return (event instanceof ImpostoEvent e) && validateImpostoEvent(e);
        default: 
            return true; // Para tipos não específicos, apenas validações básicas
        }
    }

    /**
     * Valida evento de conta.
     *
     * @param event Evento de conta
     * @return true se campos da conta são válidos
     */
    private boolean validateContaEvent(final ContaEvent event) {
        // Validar ID da conta
        if (event.getContaId() == null || event.getContaId().trim().isEmpty()) {
            log.warn("Validação falhou: ContaId é obrigatório " + "para eventos de conta");
            return false;
        }
        // Validar ID do cliente
        if (event.getClienteId() == null || event.getClienteId().trim().isEmpty()) {
            log.warn("Validação falhou: ClienteId é obrigatório " + "para eventos de conta");
            return false;
        }
        // Validar saldo (deve ser não nulo e não negativo)
        if (event.getSaldo() == null) {
            log.warn("Validação falhou: Saldo é obrigatório " + "para eventos de conta");
            return false;
        }
        if (event.getSaldo().compareTo(BigDecimal.ZERO) < 0) {
            log.warn("Validação falhou: Saldo não pode ser negativo");
            return false;
        }
        // Validar tipo de conta
        if (event.getTipoConta() == null || event.getTipoConta().trim().isEmpty()) {
            log.warn("Validação falhou: TipoConta é obrigatório " + "para eventos de conta");
            return false;
        }
        // Validar status
        if (event.getStatus() == null || event.getStatus().trim().isEmpty()) {
            log.warn("Validação falhou: Status é obrigatório " + "para eventos de conta");
            return false;
        }
        return true;
    }

    /**
     * Valida evento de transação.
     *
     * @param event Evento de transação
     * @return true se campos da transação são válidos
     */
    private boolean validateTransacaoEvent(final TransacaoEvent event) {
        // Validar ID da transação
        if (event.getTransacaoId() == null || event.getTransacaoId().trim().isEmpty()) {
            log.warn("Validação falhou: TransacaoId é obrigatório " + "para eventos de transação");
            return false;
        }
        // Validar ID da conta
        if (event.getContaId() == null || event.getContaId().trim().isEmpty()) {
            log.warn("Validação falhou: ContaId é obrigatório " + "para eventos de transação");
            return false;
        }
        // Validar valor (deve ser não nulo e positivo)
        if (event.getValor() == null) {
            log.warn("Validação falhou: Valor é obrigatório " + "para eventos de transação");
            return false;
        }
        if (event.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Validação falhou: Valor deve ser positivo para eventos de transação");
            return false;
        }
        // Validar tipo de transação
        if (event.getTipoTransacao() == null || event.getTipoTransacao().trim().isEmpty()) {
            log.warn("Validação falhou: TipoTransacao é obrigatório " + "para eventos de transação");
            return false;
        }
        // Validar status
        if (event.getStatus() == null || event.getStatus().trim().isEmpty()) {
            log.warn("Validação falhou: Status é obrigatório " + "para eventos de transação");
            return false;
        }
        return true;
    }

    /**
     * Valida evento de imposto.
     *
     * @param event Evento de imposto
     * @return true se campos do imposto são válidos
     */
    private boolean validateImpostoEvent(final ImpostoEvent event) {
        // Validar ID do imposto
        if (event.getImpostoId() == null || event.getImpostoId().trim().isEmpty()) {
            log.warn("Validação falhou: ImpostoId é obrigatório " + "para eventos de imposto");
            return false;
        }
        // Validar ID do lançamento
        if (event.getLancamentoId() == null || event.getLancamentoId().trim().isEmpty()) {
            log.warn("Validação falhou: LancamentoId é obrigatório " + "para eventos de imposto");
            return false;
        }
        // Validar valor do imposto (deve ser não nulo e não negativo)
        if (event.getValorImposto() == null) {
            log.warn("Validação falhou: ValorImposto é obrigatório " + "para eventos de imposto");
            return false;
        }
        if (event.getValorImposto().compareTo(BigDecimal.ZERO) < 0) {
            log.warn("Validação falhou: ValorImposto não pode ser negativo");
            return false;
        }
        // Validar tipo de imposto
        if (event.getTipoImposto() == null || event.getTipoImposto().trim().isEmpty()) {
            log.warn("Validação falhou: TipoImposto é obrigatório " + "para eventos de imposto");
            return false;
        }
        // Validar período
        if (event.getPeriodo() == null || event.getPeriodo().trim().isEmpty()) {
            log.warn("Validação falhou: Periodo é obrigatório " + "para eventos de imposto");
            return false;
        }
        return true;
    }

    /**
     * Valida regras de negócio.
     *
     * @param event Evento a ser validado
     * @return true se regras de negócio são atendidas
     */
    private boolean validateBusinessRules(final BaseEvent event) {
        // Validar se evento não é duplicado (baseado em metadados)
        Map<String, Object> metadata = event.getMetadata();
        if (metadata != null) {
            // Verificar se já foi processado
            if (Boolean.TRUE.equals(metadata.get("alreadyProcessed"))) {
                log.warn("Validação falhou: Evento já foi processado");
                return false;
            }
            // Verificar se é evento de teste em ambiente de produção
            if (Boolean.TRUE.equals(metadata.get("testEvent")) && "prod".equals(System.getProperty("spring.profiles.active"))) {
                log.warn("Validação falhou: Evento de teste em ambiente de produção");
                return false;
            }
        }
        // Validar limites de valor para transações
        if (event instanceof TransacaoEvent) {
            TransacaoEvent transacaoEvent = (TransacaoEvent) event;
            BigDecimal valor = transacaoEvent.getValor();
            // Limite máximo de transação
            BigDecimal limiteMaximo = BigDecimal.valueOf(MAX_TRANSACTION_LIMIT);
            if (valor.compareTo(limiteMaximo) > 0) {
                log.warn("Validação falhou: Valor da transação " + "excede limite máximo");
                return false;
            }
            // Limite mínimo de transação
            BigDecimal limiteMinimo = BigDecimal.valueOf(MIN_TRANSACTION_LIMIT);
            if (valor.compareTo(limiteMinimo) < 0) {
                log.warn("Validação falhou: Valor da transação " + "abaixo do limite mínimo");
                return false;
            }
        }
        // Validar limites de saldo para contas
        if (event instanceof ContaEvent) {
            ContaEvent contaEvent = (ContaEvent) event;
            BigDecimal saldo = contaEvent.getSaldo();
            // Limite máximo de saldo
            BigDecimal limiteMaximoSaldo = BigDecimal.valueOf(MAX_ACCOUNT_BALANCE_LIMIT);
            if (saldo.compareTo(limiteMaximoSaldo) > 0) {
                log.warn("Validação falhou: Saldo da conta excede " + "limite máximo");
                return false;
            }
        }
        return true;
    }

    /**
     * Obtém total de validações realizadas.
     *
     * @return Total de validações
     */
    public long getTotalValidations() {
        return totalValidations.get();
    }

    /**
     * Obtém total de eventos válidos.
     *
     * @return Total de eventos válidos
     */
    public long getTotalValidEvents() {
        return totalValidEvents.get();
    }

    /**
     * Obtém total de eventos inválidos.
     *
     * @return Total de eventos inválidos
     */
    public long getTotalInvalidEvents() {
        return totalInvalidEvents.get();
    }

    /**
     * Obtém estatísticas de validação.
     *
     * @return Mapa com estatísticas
     */
    public Map<String, Object> getValidationStatistics() {
        long total = totalValidations.get();
        double successRate = total > 0 ? (double) totalValidEvents.get() / total * 100 : 0.0;
        return Map.of("totalValidations", total, "totalValidEvents", totalValidEvents.get(), "totalInvalidEvents", totalInvalidEvents.get(), "successRate", successRate, "timestamp", LocalDateTime.now());
    }
}
