package com.aurix.platform.banking.core.service;

import com.aurix.platform.banking.core.entity.OutboxEvent;
import com.aurix.platform.banking.core.repository.OutboxEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutboxRelayTest {

    @Mock
    private OutboxEventRepository outboxEventRepository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private OutboxRelay outboxRelay;

    @BeforeEach
    void setUp() {
        outboxRelay = new OutboxRelay(outboxEventRepository, kafkaTemplate);
    }

    @Test
    void deveEnviarParaOTopicoExatoArmazenadoNoEventTypeENaoUmRecalculado() {
        OutboxEvent event = new OutboxEvent();
        event.setId(1L);
        event.setAggregateType("CONTA");
        event.setAggregateId("conta-123");
        event.setEventType("core.conta.criada.v1");
        event.setPayload("{}");
        event.setStatus(OutboxEvent.Status.PENDING);

        when(outboxEventRepository.findByStatusOrderByCreatedAtAsc(OutboxEvent.Status.PENDING))
                .thenReturn(List.of(event));
        when(kafkaTemplate.send(any(String.class), any(String.class), any()))
                .thenReturn(new CompletableFuture<SendResult<String, Object>>());

        outboxRelay.processOutboxEvents();

        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaTemplate).send(topicCaptor.capture(), eq("conta-123"), eq("{}"));

        // Antes da correção o tópico era recalculado como
        // "conta.core.conta.criada.v1" (aggregateType + "." + eventType, ambos em
        // lowercase), que nenhum consumidor escutava. O tópico enviado precisa ser
        // exatamente o valor de eventType.
        assertEquals("core.conta.criada.v1", topicCaptor.getValue());
        verify(outboxEventRepository).save(event);
        assertEquals(OutboxEvent.Status.PROCESSED, event.getStatus());
    }
}
