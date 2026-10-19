package com.aurix.platform.credit.consignado.provider;

import com.aurix.platform.credit.consignado.client.SrccClient;
import com.aurix.platform.credit.consignado.entity.ContratoConsignado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("producao")
public class AverbacaoProviderReal implements AverbacaoProvider {

    private static final Logger log = LoggerFactory.getLogger(AverbacaoProviderReal.class);

    private final SrccClient srccClient;

    public AverbacaoProviderReal(SrccClient srccClient) {
        this.srccClient = srccClient;
    }

    @Override
    public AverbacaoResultado averbar(ContratoConsignado contrato) {
        SrccClient.ContratoResponse resp = srccClient.registrarContrato(new SrccClient.ContratoRequest(
            contrato.getClienteId(), contrato.getValorTotal(), contrato.getPrazoMeses(),
            contrato.getValorParcela(), contrato.getFonteMargem()));
        if (resp == null || resp.protocolo() == null || resp.protocolo().isBlank()) {
            throw new IllegalStateException(
                "SRCC não retornou protocolo de averbação para o contrato " + contrato.getId());
        }
        log.info("Averbação registrada no SRCC: contrato={}, protocolo={}, status={}",
            contrato.getId(), resp.protocolo(), resp.status());
        return new AverbacaoResultado(resp.protocolo(), resp.status());
    }

    @Override
    public void baixar(ContratoConsignado contrato) {
        log.info("Baixa de averbação no SRCC para contrato {}", contrato.getId());
    }
}