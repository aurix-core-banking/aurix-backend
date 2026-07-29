package com.aurix.platform.customer.onboarding.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!producao")
public class FraudStub implements FraudService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FraudStub.class);

    @Override
    public ResultadoFraude analisar(String cpf, String nome, String email, String telefone) {
        log.debug("Fraude stub: analisando CPF {}", cpf);
        return new ResultadoFraude(true, "APROVADO_STUB", "Stub de fraude - integrar ClearSale", 0);
    }
}
