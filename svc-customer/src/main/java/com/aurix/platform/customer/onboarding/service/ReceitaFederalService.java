package com.aurix.platform.customer.onboarding.service;

public interface ReceitaFederalService {

    ResultadoReceita consultarCnpj(String cnpj);

    record ResultadoReceita(String documento, String razaoSocial, String situacao, String erro) {
        public static ResultadoReceita ok(String doc, String razao, String situacao) {
            return new ResultadoReceita(doc, razao, situacao, null);
        }
        public static ResultadoReceita erro(String doc, String erro) {
            return new ResultadoReceita(doc, null, null, erro);
        }
    }
}
