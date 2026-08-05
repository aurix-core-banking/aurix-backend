package com.aurix.platform.customer.service;

import com.aurix.platform.customer.entity.Cliente;
import com.aurix.platform.shared.event.ClienteCriadoEvent;
import com.aurix.platform.shared.event.ClienteAtualizadoEvent;
import com.aurix.platform.shared.event.ClienteStatusAlteradoEvent;
import com.aurix.platform.shared.event.Topics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClienteProducer {
    private static final Logger log = LoggerFactory.getLogger(ClienteProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ClienteProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void clienteCriado(Cliente cliente) {
        ClienteCriadoEvent event = ClienteCriadoEvent.criado(
            cliente.getId(), cliente.getDocumento(), cliente.getNomeCompleto(),
            cliente.getTipoPessoa(), cliente.getSegmento());
        kafkaTemplate.send(Topics.CUSTOMER_CLIENTE_CRIADO, String.valueOf(cliente.getId()), event);
        log.info("Evento {} publicado para clienteId={}", event.getEventType(), cliente.getId());
    }

    public void clienteAtualizado(Cliente cliente) {
        ClienteAtualizadoEvent event = ClienteAtualizadoEvent.atualizado(
            cliente.getId(), cliente.getDocumento(), cliente.getStatus());
        kafkaTemplate.send(Topics.CUSTOMER_CLIENTE_ATUALIZADO, String.valueOf(cliente.getId()), event);
    }

    public void clienteStatusAlterado(Cliente cliente, String statusAnterior) {
        ClienteStatusAlteradoEvent event = ClienteStatusAlteradoEvent.alterado(
            cliente.getId(), statusAnterior, cliente.getStatus());
        kafkaTemplate.send(Topics.CUSTOMER_CLIENTE_STATUS_ALTERADO, String.valueOf(cliente.getId()), event);
    }
}
