package com.aurix.platform.credit.consignado.provider;

import com.aurix.platform.credit.consignado.entity.ContratoConsignado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev", "test", "stub"})
public class AverbacaoProviderStub implements AverbacaoProvider {

    private static final Logger log = LoggerFactory.getLogger(AverbacaoProviderStub.class);

    @Override
    public AverbacaoResultado averbar(ContratoConsignado contrato) {
        log.info("[stub] Averbação SRCC para contrato {}", contrato.getId());
        return new AverbacaoResultado("AVB-" + contrato.getId(), "REGISTRADO");
    }

    @Override
    public void baixar(ContratoConsignado contrato) {
        log.info("[stub] Baixa de averbação para contrato {}", contrato.getId());
    }
}