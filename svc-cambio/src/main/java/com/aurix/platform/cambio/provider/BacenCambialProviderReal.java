package com.aurix.platform.cambio.provider;

import com.aurix.platform.cambio.client.BacenClient;
import com.aurix.platform.cambio.entity.ContratoCambio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("producao")
public class BacenCambialProviderReal implements BacenCambialProvider {

    private static final Logger log = LoggerFactory.getLogger(BacenCambialProviderReal.class);

    private final BacenClient bacenClient;

    public BacenCambialProviderReal(BacenClient bacenClient) {
        this.bacenClient = bacenClient;
    }

    @Override
    public RegistroBacenResultado registrarRdeRof(ContratoCambio contrato, String clienteDoc) {
        BacenClient.RegistrarRdeRofResponse resp = bacenClient.registrarRdeRof(
            new BacenClient.RegistrarRdeRofRequest(
                contrato.getId(), contrato.getValorOrigem(), contrato.getMoedaOrigem(),
                clienteDoc, contrato.getTipo(), contrato.getFinalidade()));
        if (resp == null || resp.protocolo() == null || resp.protocolo().isBlank()) {
            throw new IllegalStateException(
                "BACEN não retornou protocolo RDE-ROF para o contrato " + contrato.getId());
        }
        log.info("Registro RDE-ROF (SISBACEN) confirmado: contrato={}, protocolo={}, status={}",
            contrato.getId(), resp.protocolo(), resp.status());
        return new RegistroBacenResultado(resp.protocolo(), resp.status());
    }
}