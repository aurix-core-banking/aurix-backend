package com.aurix.platform.customer.temporal.activity;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface OnboardingPFActivities {

    @ActivityMethod
    ResultadoConsulta consultarReceitaFederal(String cpf, String nome, String dataNascimento);

    @ActivityMethod
    ResultadoConsulta consultarBureauCredito(String cpf);

    @ActivityMethod
    ResultadoConsulta verificarPep(String cpf, String nome);

    @ActivityMethod
    ResultadoConsulta analisarFraude(String cpf, String nome, String ip, String dispositivo);

    @ActivityMethod
    ResultadoConsulta validarKyc(String cpf, String documentoFrente, String documentoVerso, String selfie);

    @ActivityMethod
    ResultadoConsulta criarClienteConta(String cpf, String nome, String email, String telefone);

    @ActivityMethod
    void publicarEvento(String topico, String chave, String payload);

    record ResultadoConsulta(boolean aprovado, String motivo, String score, String detalhes) {}
}
