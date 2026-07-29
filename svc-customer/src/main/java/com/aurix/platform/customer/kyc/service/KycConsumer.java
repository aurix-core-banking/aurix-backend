package com.aurix.platform.customer.kyc.service;

import com.aurix.platform.customer.kyc.entity.SolicitacaoKYC;
import com.aurix.platform.customer.kyc.repository.SolicitacaoKycRepository;
import com.aurix.platform.shared.event.ClienteCriadoEvent;
import com.aurix.platform.shared.event.Topics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class KycConsumer {
    private final SolicitacaoKycRepository repository;

    public KycConsumer(SolicitacaoKycRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = Topics.CUSTOMER_CLIENTE_CRIADO, groupId = "aurix-kyc-group")
    public void onClienteCriado(ClienteCriadoEvent event) {
        SolicitacaoKYC solicitacao = new SolicitacaoKYC();
        solicitacao.setClienteId(event.getClienteId());
        solicitacao.setStatus("PENDENTE");
        solicitacao.setDataSolicitacao(LocalDateTime.now());
        repository.save(solicitacao);
    }
}
