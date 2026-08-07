package com.aurix.platform.banking.core.event;

import com.aurix.platform.shared.repository.ContaRepository;
import com.aurix.platform.shared.repository.TransacaoRepository;
import com.aurix.platform.shared.cache.SharedCacheService;
import com.aurix.platform.shared.dto.ContaDTO;
import com.aurix.platform.shared.dto.TransacaoDTO;
import com.aurix.platform.shared.entity.Conta;
import com.aurix.platform.shared.entity.Transacao;
import com.aurix.platform.shared.event.ContaEvent;
import com.aurix.platform.shared.event.Topics;
import com.aurix.platform.shared.event.TransacaoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Consome os eventos de conta/transação publicados por este próprio módulo (e por
 * aurix-pix, no caso de transacao-realizada) para manter o cache compartilhado
 * (Redis) atualizado.
 *
 * <p>Antes ficava em aurix-shared (ver ADR-0001) — uma biblioteca compartilhada não
 * deveria hospedar consumidores de evento de negócio, porque isso faz a lógica rodar
 * implicitamente em todos os ~25 módulos que dependem de aurix-shared, todos no
 * mesmo consumer group "aurix-integration" (o Kafka balanceia partições entre eles
 * de forma imprevisível). Conta/Transacao pertencem a aurix-core, então o consumidor
 * mora aqui agora.</p>
 *
 * <p>Os lookups antes eram dados fabricados (`ContaDTO` com número de conta e saldo
 * fixos, "12345-6" / 1000.0) em vez de uma consulta real — corrigido para usar os
 * repositórios reais.</p>
 *
 * <p>Nota: o evento não carrega tenantId, então este consumidor busca por ID direto
 * no repositório (sem o filtro de tenant que os endpoints HTTP aplicam via
 * TenantContext). Para multi-tenant real, o evento precisaria carregar o tenantId.</p>
 */
@Component
public class ContaTransacaoEventListener {

    private static final Logger log = LoggerFactory.getLogger(ContaTransacaoEventListener.class);

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final SharedCacheService sharedCacheService;

    public ContaTransacaoEventListener(final ContaRepository contaRepository,
            final TransacaoRepository transacaoRepository,
            final SharedCacheService sharedCacheService) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        this.sharedCacheService = sharedCacheService;
    }

    @KafkaListener(topics = Topics.CONTA_CRIADA, groupId = "aurix-core-integration")
    public void processarContaCriada(@Payload final ContaEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) final String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) final int partition,
            @Header(KafkaHeaders.OFFSET) final long offset,
            final Acknowledgment acknowledgment) {
        log.info("Processando evento CONTA_CRIADA: Conta={}, Topic={}, Partition={}, Offset={}",
                event.getContaId(), topic, partition, offset);
        try {
            buscarContaDTO(event.getContaId()).ifPresent(conta -> {
                sharedCacheService.salvarConta(event.getContaId(), conta);
                log.info("Conta {} salva no cache após evento de criação", event.getContaId());
            });
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Erro ao processar evento CONTA_CRIADA: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = Topics.CONTA_ATUALIZADA, groupId = "aurix-core-integration")
    public void processarContaAtualizada(@Payload final ContaEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) final String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) final int partition,
            @Header(KafkaHeaders.OFFSET) final long offset,
            final Acknowledgment acknowledgment) {
        log.info("Processando evento CONTA_ATUALIZADA: Conta={}, Topic={}, Partition={}, Offset={}",
                event.getContaId(), topic, partition, offset);
        try {
            sharedCacheService.removerConta(event.getContaId());
            buscarContaDTO(event.getContaId()).ifPresent(conta -> {
                sharedCacheService.salvarConta(event.getContaId(), conta);
                log.info("Conta {} atualizada no cache", event.getContaId());
            });
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Erro ao processar evento CONTA_ATUALIZADA: {}", e.getMessage());
        }
    }

    @KafkaListener(topics = Topics.TRANSACAO_REALIZADA, groupId = "aurix-core-integration")
    public void processarTransacaoRealizada(@Payload final TransacaoEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) final String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) final int partition,
            @Header(KafkaHeaders.OFFSET) final long offset,
            final Acknowledgment acknowledgment) {
        log.info("Processando evento TRANSACAO_REALIZADA: Transacao={}, Topic={}, Partition={}, Offset={}",
                event.getTransacaoId(), topic, partition, offset);
        try {
            buscarTransacaoDTO(event.getTransacaoId()).ifPresent(transacao -> {
                sharedCacheService.salvarTransacao(event.getTransacaoId(), transacao);
                log.info("Transação {} salva no cache após evento de realização", event.getTransacaoId());
            });
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Erro ao processar evento TRANSACAO_REALIZADA: {}", e.getMessage());
        }
    }

    private java.util.Optional<ContaDTO> buscarContaDTO(final String contaId) {
        return contaRepository.findById(Long.parseLong(contaId)).map(this::toDTO);
    }

    private java.util.Optional<TransacaoDTO> buscarTransacaoDTO(final String transacaoId) {
        return transacaoRepository.findById(Long.parseLong(transacaoId)).map(this::toDTO);
    }

    private ContaDTO toDTO(final Conta conta) {
        ContaDTO dto = new ContaDTO();
        dto.setId(conta.getId());
        dto.setNumeroConta(conta.getNumeroConta());
        dto.setClienteId(conta.getCliente() != null ? conta.getCliente().getId() : null);
        dto.setTipoConta(conta.getTipoConta());
        dto.setSaldo(conta.getSaldo());
        dto.setStatus(conta.getStatus());
        return dto;
    }

    private TransacaoDTO toDTO(final Transacao transacao) {
        TransacaoDTO dto = new TransacaoDTO();
        dto.setId(transacao.getId());
        dto.setValor(transacao.getValor());
        dto.setTipoTransacao(transacao.getTipoTransacao());
        dto.setStatus(transacao.getStatus());
        dto.setDescricao(transacao.getDescricao());
        return dto;
    }
}
