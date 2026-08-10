package com.aurix.platform.contracts.exception;

import com.aurix.platform.shared.exception.AurixException;

public class TemplateNaoEncontradoException extends AurixException {

    public TemplateNaoEncontradoException(Long id) {
        super("TEMPLATE_NAO_ENCONTRADO", "Template de contrato não encontrado: " + id);
    }

    public TemplateNaoEncontradoException(String codigo) {
        super("TEMPLATE_NAO_ENCONTRADO", "Template de contrato não encontrado com código: " + codigo);
    }
}
