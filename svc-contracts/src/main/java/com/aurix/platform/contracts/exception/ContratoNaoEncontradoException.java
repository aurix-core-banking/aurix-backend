package com.aurix.platform.contracts.exception;

import com.aurix.platform.shared.exception.AurixException;

public class ContratoNaoEncontradoException extends AurixException {

    public ContratoNaoEncontradoException(Long id) {
        super("CONTRATO_NAO_ENCONTRADO", "Contrato não encontrado: " + id);
    }

    public ContratoNaoEncontradoException(String numeroContrato) {
        super("CONTRATO_NAO_ENCONTRADO", "Contrato não encontrado com número: " + numeroContrato);
    }
}
