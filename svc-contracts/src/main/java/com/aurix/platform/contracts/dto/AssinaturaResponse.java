package com.aurix.platform.contracts.dto;

import com.aurix.platform.contracts.entity.AssinaturaContrato;

import java.time.LocalDateTime;

public record AssinaturaResponse(
    Long id,
    Long contratoId,
    AssinaturaContrato.AssinanteTipo assinanteTipo,
    String assinanteDocumento,
    String assinanteNome,
    Boolean assinada,
    Boolean valida,
    LocalDateTime dataAssinatura
) {

    public static AssinaturaResponse de(AssinaturaContrato a) {
        return new AssinaturaResponse(
            a.getId(), a.getContratoId(), a.getAssinanteTipo(),
            a.getAssinanteDocumento(), a.getAssinanteNome(),
            a.getAssinada(), a.getValida(), a.getDataAssinatura()
        );
    }
}
