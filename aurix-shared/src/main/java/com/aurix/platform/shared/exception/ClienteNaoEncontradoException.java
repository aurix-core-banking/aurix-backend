package com.aurix.platform.shared.exception;

/**
 * Exception thrown when a client is not found.
 */
public class ClienteNaoEncontradoException extends AurixException {

    /**
     * Constructs exception for client not found by CPF.
     *
     * @param cpf the CPF of the client
     */
    public ClienteNaoEncontradoException(final String cpf) {
        super("CLIENTE_NAO_ENCONTRADO",
                String.format("Cliente com CPF %s não encontrado", cpf));
    }

    /**
     * Constructs exception for client not found by ID.
     *
     * @param id the ID of the client
     */
    public ClienteNaoEncontradoException(final Long id) {
        super("CLIENTE_NAO_ENCONTRADO",
                String.format("Cliente com ID %d não encontrado", id));
    }

    /**
     * Constructs exception for client not found by document (CPF or CNPJ).
     *
     * @param documento the document number (CPF or CNPJ)
     * @param isCnpj    true if the document is a CNPJ, false if CPF
     */
    public ClienteNaoEncontradoException(final String documento, final boolean isCnpj) {
        super("CLIENTE_NAO_ENCONTRADO",
                String.format("Cliente com %s %s não encontrado",
                        isCnpj ? "CNPJ" : "CPF", documento));
    }
}
