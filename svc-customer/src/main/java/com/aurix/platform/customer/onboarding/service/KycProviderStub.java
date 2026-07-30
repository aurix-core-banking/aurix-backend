package com.aurix.platform.customer.onboarding.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Stub de KYC (aprova qualquer documento). Desabilitado em produção (perfil
 * "producao") porque não há ainda integração real com um provedor — sem este
 * stub e sem substituto real, a aplicação falha ao subir em vez de aprovar
 * onboarding real sem verificação de identidade.
 */
@Service
@Profile("!producao")
public class KycProviderStub implements KycProviderService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(KycProviderStub.class);

    @Override
    public ResultadoKyc validarDocumentos(String cpf, List<DocumentoInfo> documentos, String selfieBase64) {
        log.debug("KYC stub: validando documentos para CPF {} ({} documentos)", cpf, documentos != null ? documentos.size() : 0);
        if (documentos == null || documentos.isEmpty()) {
            return new ResultadoKyc(false, "DOCUMENTOS_INSUFICIENTES", "Envie pelo menos um documento");
        }
        return new ResultadoKyc(true, "APROVADO_STUB", "Validacao simulada - integrar provedor real (ex.: ID One)");
    }
}
