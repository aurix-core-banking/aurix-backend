package com.aurix.platform.contracts.dto;

import com.aurix.platform.contracts.entity.Contrato;
import com.aurix.platform.contracts.entity.TemplateContrato;

public record TemplateResponse(
    Long id,
    String codigo,
    String nome,
    Contrato.TipoContrato tipoContrato,
    String corpoTexto,
    TemplateContrato.StatusTemplate status,
    Integer versao
) {

    public static TemplateResponse de(TemplateContrato t) {
        return new TemplateResponse(
            t.getId(), t.getCodigo(), t.getNome(), t.getTipoContrato(),
            t.getCorpoTexto(), t.getStatus(), t.getVersao()
        );
    }
}
