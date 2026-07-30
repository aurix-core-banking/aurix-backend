package com.aurix.platform.payments.pix.service;

import com.aurix.platform.payments.pix.entity.OutboxEvent;
import com.aurix.platform.payments.pix.repository.OutboxEventRepository;
import com.aurix.platform.shared.event.TransacaoEvent;
import com.aurix.platform.shared.event.Topics;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OutboxEventPublisherTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private OutboxEventRepository outboxEventRepository;

    private OutboxEventPublisher outboxEventPublisher;

    @BeforeEach
    void setUp() {
        outboxEventPublisher = new OutboxEventPublisher(kafkaTemplate, outboxEventRepository, new ObjectMapper().registerModule(new JavaTimeModule()));
    }

    @Test
    void publicarTransacaoRealizadaDeveSalvarNoOutboxEmVezDeEnviarDiretoParaOKafka() {
        TransacaoEvent event = TransacaoEvent.transacaoRealizada("123", "conta-1", "cliente-1", BigDecimal.TEN, "PIX", "transferência teste");

        outboxEventPublisher.publicarTransacaoRealizada(event);

        ArgumentCaptor<OutboxEvent> captor = ArgumentCaptor.forClass(OutboxEvent.class);
        verify(outboxEventRepository).save(captor.capture());
        OutboxEvent saved = captor.getValue();
        assertEquals("TRANSACAO", saved.getAggregateType());
        assertEquals(event.getEventId(), saved.getAggregateId());
        assertEquals(Topics.TRANSACAO_REALIZADA, saved.getEventType());
        assertEquals(OutboxEvent.Status.PENDING, saved.getStatus());
        assertTrue(saved.getPayload().contains("123"));

        // O outbox garante atomicidade com a transação do banco — o envio ao
        // Kafka acontece depois, via OutboxRelay, nunca diretamente aqui.
        verify(kafkaTemplate, never()).send(org.mockito.ArgumentMatchers.anyString(), org.mockito.ArgumentMatchers.any(), org.mockito.ArgumentMatchers.any());
    }
}
