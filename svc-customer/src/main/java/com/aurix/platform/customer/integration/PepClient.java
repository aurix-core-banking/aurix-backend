package com.aurix.platform.customer.integration;

import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Cliente de verificação de Pessoa Exposta Politicamente (PEP).
 * <p>
 * Verificação determinística contra uma lista interna de PEPs conhecidas.
 * Integração real com fonte oficial (ex.: CGU) é planejada na Fase 2.2.
 */
@Component
public class PepClient {

    private static final Set<String> PEPS_CONHECIDAS = Set.of(
            "00000000000",
            "11111111111",
            "22222222222"
    );

    public boolean verificarPep(String cpf, String nome) {
        if (cpf == null) {
            return false;
        }
        return PEPS_CONHECIDAS.contains(cpf.replaceAll("\\D", ""));
    }
}