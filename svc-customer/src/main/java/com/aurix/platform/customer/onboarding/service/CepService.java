package com.aurix.platform.customer.onboarding.service;

public interface CepService {

    ResultadoCep consultar(String cep);

    record ResultadoCep(String cep, String logradouro, String bairro, String cidade, String uf, String erro) {
        public static ResultadoCep ok(String cep, String logradouro, String bairro, String cidade, String uf) {
            return new ResultadoCep(cep, logradouro, bairro, cidade, uf, null);
        }
        public static ResultadoCep erro(String cep, String erro) {
            return new ResultadoCep(cep, null, null, null, null, erro);
        }
    }
}
