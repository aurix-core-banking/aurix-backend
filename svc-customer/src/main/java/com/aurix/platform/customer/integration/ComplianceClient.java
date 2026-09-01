package com.aurix.platform.customer.integration;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Cliente de compliance e PLD (Prevenção à Lavagem de Dinheiro) para pessoa jurídica.
 * <p>
 * Implementa verificações determinísticas de validação de sócios, documentos, AML e
 * assinatura digital. Integração real com provedores (ex.: Bureau/Quod, assinatura
 * eletrônica) é planejada na Fase 2.2/2.7.
 */
@Component
public class ComplianceClient {

    private static final Set<String> LISTA_NEGRA_CNPJ = Set.of(
            "00000000000000",
            "11111111111111"
    );

    private static final BigDecimal FATURAMENTO_ALTO = new BigDecimal("100000000");

    public boolean validarSocios(String cnpj, String sociosJson) {
        return sociosJson != null && !sociosJson.isBlank() && !LISTA_NEGRA_CNPJ.contains(cnpj);
    }

    public boolean analisarDocumentos(String cnpj, String documentosJson) {
        return documentosJson != null && !documentosJson.isBlank();
    }

    public boolean verificarAml(String cnpj, String razaoSocial, BigDecimal faturamento) {
        if (LISTA_NEGRA_CNPJ.contains(cnpj)) {
            return false;
        }
        return faturamento == null || faturamento.compareTo(FATURAMENTO_ALTO) <= 0;
    }

    public boolean verificarCompliance(String cnpj, String sociosJson) {
        return validarSocios(cnpj, sociosJson);
    }

    public boolean solicitarAssinatura(String cnpj, String razaoSocial, String sociosJson) {
        return razaoSocial != null && !razaoSocial.isBlank();
    }
}