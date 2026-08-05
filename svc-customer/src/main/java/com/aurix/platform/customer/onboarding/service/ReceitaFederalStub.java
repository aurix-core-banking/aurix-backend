package com.aurix.platform.customer.onboarding.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"dev", "test", "stub"})
public class ReceitaFederalStub implements ReceitaFederalService {

    @Override
    public ResultadoReceita consultarCnpj(String cnpj) {
        if (cnpj == null || cnpj.replaceAll("\\D", "").length() != 14) {
            return ResultadoReceita.erro(cnpj, "CNPJ inválido");
        }
        return ResultadoReceita.ok(cnpj, "Empresa Exemplo Ltda", "ATIVA");
    }
}
