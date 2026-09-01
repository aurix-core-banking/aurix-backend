package com.aurix.platform.customer.integration;

import com.aurix.platform.customer.onboarding.service.ReceitaFederalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Cliente de integração com a Receita Federal.
 * <p>
 * Para PF, valida a estrutura do CPF (11 dígitos) localmente. Para PJ, delega para
 * o {@link ReceitaFederalService} ativo (stub em dev/test; integração real à API RFB
 * é planejada na Fase 2.2).
 */
@Component
public class ReceitaFederalClient {

    private static final Logger log = LoggerFactory.getLogger(ReceitaFederalClient.class);

    private final ReceitaFederalService receitaFederalService;

    public ReceitaFederalClient(ReceitaFederalService receitaFederalService) {
        this.receitaFederalService = receitaFederalService;
    }

    public boolean validarCpf(String cpf, String nome, String dataNascimento) {
        log.debug("Validando CPF {} (Receita Federal)", cpf);
        if (cpf == null) {
            return false;
        }
        String digitos = cpf.replaceAll("\\D", "");
        return digitos.length() == 11 && !digitos.chars().allMatch(c -> c == digitos.charAt(0));
    }

    public boolean validarCnpj(String cnpj) {
        log.debug("Validando CNPJ {} (Receita Federal)", cnpj);
        String digitos = cnpj != null ? cnpj.replaceAll("\\D", "") : "";
        if (digitos.length() != 14 || digitos.chars().allMatch(c -> c == digitos.charAt(0))) {
            return false;
        }
        return receitaFederalService.consultarCnpj(cnpj).erro() == null;
    }
}