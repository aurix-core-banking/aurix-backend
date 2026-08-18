package com.aurix.platform.customer.temporal.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OnboardingPJActivities {

    @ActivityMethod
    ResultadoConsulta consultarCnpj(String cnpj);

    @ActivityMethod
    ResultadoConsulta validarSocios(String cnpj, String sociosJson);

    @ActivityMethod
    ResultadoConsulta analisarDocumentos(String cnpj, String documentosJson);

    @ActivityMethod
    ResultadoConsulta verificarAml(String cnpj, String razaoSocial, BigDecimal faturamento);

    @ActivityMethod
    ResultadoConsulta verificarCompliance(String cnpj, String sociosJson);

    @ActivityMethod
    ResultadoConsulta solicitarAssinatura(String cnpj, String razaoSocial, String sociosJson);

    @ActivityMethod
    ResultadoConsulta criarClienteConta(String cnpj, String razaoSocial, String email, String telefone);

    @ActivityMethod
    void publicarEvento(String topico, String chave, String payload);

    record ResultadoConsulta(boolean aprovado, String motivo, String score, String detalhes) {}
}
