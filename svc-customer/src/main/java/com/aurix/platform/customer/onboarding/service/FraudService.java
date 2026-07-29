package com.aurix.platform.customer.onboarding.service;

public interface FraudService {

    ResultadoFraude analisar(String cpf, String nome, String email, String telefone);

    record ResultadoFraude(boolean aprovado, String codigo, String mensagem, int risco) {}
}
