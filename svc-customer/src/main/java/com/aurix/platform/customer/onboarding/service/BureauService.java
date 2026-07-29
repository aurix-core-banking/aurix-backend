package com.aurix.platform.customer.onboarding.service;

public interface BureauService {

    ResultadoBureau consultar(String cpf);

    record ResultadoBureau(Integer score, String situacao, String mensagem) {}
}
