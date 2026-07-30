package com.aurix.platform.customer.onboarding.service;

import java.util.List;

public interface KycProviderService {

    ResultadoKyc validarDocumentos(String cpf, List<DocumentoInfo> documentos, String selfieBase64);

    record DocumentoInfo(String tipo, String urlOuBase64, String nomeArquivo) {}

    record ResultadoKyc(boolean aprovado, String codigoResultado, String mensagem) {}
}
