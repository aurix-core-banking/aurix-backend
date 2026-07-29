package com.aurix.platform.customer.onboarding.service;

import org.springframework.stereotype.Service;

@Service
public class ReceitaFederalStub implements ReceitaFederalService {

    @Override
    public ResultadoReceita consultarCnpj(String cnpj) {
        if (cnpj == null || cnpj.replaceAll("\\D", "").length() != 14) {
            return ResultadoReceita.erro(cnpj, "CNPJ inválido");
        }
        return ResultadoReceita.ok(cnpj, "Empresa Exemplo Ltda", "ATIVA");
    }
}
