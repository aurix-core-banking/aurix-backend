package com.aurix.platform.customer.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Cliente de integração com o core banking (svc-banking/aurix-gateway) usado pelos
 * workflows de onboarding.
 * <p>
 * Adapter sobre {@code com.aurix.platform.customer.onboarding.client.CoreApiClient}
 * com assinaturas simplificadas para o workflow. Retorna o id do cliente como String,
 * ou {@code null} quando a criação não teve sucesso.
 */
@Component("coreApiTemporalClient")
public class CoreApiClient {

    private static final Logger log = LoggerFactory.getLogger(CoreApiClient.class);

    private final com.aurix.platform.customer.onboarding.client.CoreApiClient coreApiClient;

    public CoreApiClient(@Qualifier("coreApiClient")
                         com.aurix.platform.customer.onboarding.client.CoreApiClient coreApiClient) {
        this.coreApiClient = coreApiClient;
    }

    public String criarClientePFeConta(String cpf, String nome, String email, String telefone) {
        var resultado = coreApiClient.criarClientePFeConta(
                null, cpf, nome, email, telefone, null, "{}", false);
        if (resultado.sucesso() && resultado.clienteId() != null) {
            return String.valueOf(resultado.clienteId());
        }
        log.warn("Falha ao criar cliente PF no core para CPF {}", cpf);
        return null;
    }

    public String criarClientePJeConta(String cnpj, String razaoSocial, String email, String telefone) {
        var resultado = coreApiClient.criarClientePJeConta(
                null, cnpj, razaoSocial, email, telefone, "{}", false, null, null, null, null, null);
        if (resultado.sucesso() && resultado.clienteId() != null) {
            return String.valueOf(resultado.clienteId());
        }
        log.warn("Falha ao criar cliente PJ no core para CNPJ {}", cnpj);
        return null;
    }
}