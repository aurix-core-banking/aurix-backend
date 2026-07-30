package com.aurix.platform.customer.onboarding.service;

import org.springframework.stereotype.Service;

@Service
public class CepStub implements CepService {

    @Override
    public ResultadoCep consultar(String cep) {
        if (cep == null || cep.replaceAll("\\D", "").length() != 8) {
            return ResultadoCep.erro(cep, "CEP inválido");
        }
        String cepLimpo = cep.replaceAll("\\D", "");
        return ResultadoCep.ok(cepLimpo, "Rua Exemplo", "Centro", "São Paulo", "SP");
    }
}
