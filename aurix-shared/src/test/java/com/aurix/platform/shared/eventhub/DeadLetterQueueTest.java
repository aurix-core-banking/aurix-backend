package com.aurix.platform.shared.eventhub;

import com.aurix.platform.shared.event.ContaEvent;
import com.aurix.platform.shared.event.Topics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeadLetterQueueTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private DeadLetterQueue deadLetterQueue;
    private ContaEvent event;

    @BeforeEach
    void setUp() {
        deadLetterQueue = new DeadLetterQueue(kafkaTemplate);
        event = ContaEvent.contaCriada("conta-1", "cliente-1", BigDecimal.TEN, "CORRENTE");
    }

    @Test
    void sendToDLQDevePublicarNoTopicoDLQCentralizadoEmTopics() {
        deadLetterQueue.sendToDLQ(event, "falha de teste");

        verify(kafkaTemplate).send(eq(Topics.DLQ), eq(event.getEventId()), any());
    }

    @Test
    void reprocessEventDevePublicarNoTopicoDeReprocessamentoQuandoAindaHaTentativas() {
        DeadLetterQueue.DLQEvent dlqEvent = DeadLetterQueue.DLQEvent.builder()
                .originalEvent(event)
                .failureReason("falha de teste")
                .retryCount(0)
                .maxRetries(3)
                .build();

        deadLetterQueue.reprocessEvent(dlqEvent);

        verify(kafkaTemplate).send(eq(Topics.REPROCESS), eq(event.getEventId()), eq(event));
    }

    @Test
    void reprocessEventDevePublicarNoTopicoDLQPermanenteQuandoExcedeMaxRetries() {
        DeadLetterQueue.DLQEvent dlqEvent = DeadLetterQueue.DLQEvent.builder()
                .originalEvent(event)
                .failureReason("falha de teste")
                .retryCount(3)
                .maxRetries(3)
                .build();

        deadLetterQueue.reprocessEvent(dlqEvent);

        verify(kafkaTemplate).send(eq(Topics.DLQ_PERMANENT), eq(event.getEventId()), eq(dlqEvent));
    }

}
