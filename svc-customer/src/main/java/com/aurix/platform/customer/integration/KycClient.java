package com.aurix.platform.customer.integration;

import com.aurix.platform.customer.onboarding.service.KycProviderService;
import com.aurix.platform.customer.onboarding.service.KycProviderService.DocumentoInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Cliente de verificação KYC (Identificação de Clientes).
 * <p>
 * Delega para o {@link KycProviderService} ativo (stub em dev/test; provedor real
 * como ID One é planejado na Fase 2.2). Converte os parâmetros de URL/base64 do
 * workflow em {@link DocumentoInfo}.
 */
@Component
public class KycClient {

    private final KycProviderService kycProviderService;

    public KycClient(KycProviderService kycProviderService) {
        this.kycProviderService = kycProviderService;
    }

    public boolean validarDocumentos(String cpf, String documentoFrente,
                                     String documentoVerso, String selfie) {
        List<DocumentoInfo> documentos = new ArrayList<>();
        if (documentoFrente != null) {
            documentos.add(new DocumentoInfo("RG_FRENTE", documentoFrente, "frente.jpg"));
        }
        if (documentoVerso != null) {
            documentos.add(new DocumentoInfo("RG_VERSO", documentoVerso, "verso.jpg"));
        }
        var resultado = kycProviderService.validarDocumentos(cpf, documentos, selfie);
        return resultado.aprovado();
    }
}