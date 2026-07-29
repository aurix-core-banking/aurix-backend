package com.aurix.platform.shared.exception;

/**
 * Exception thrown when an account is not found.
 */
public class ContaNaoEncontradaException extends AurixException {

    /**
     * Constructs exception for account not found by number.
     *
     * @param numeroConta the account number
     */
    public ContaNaoEncontradaException(final String numeroConta) {
        super("CONTA_NAO_ENCONTRADA",
                String.format("Conta %s não encontrada", numeroConta));
    }

    /**
     * Constructs exception for account not found by ID.
     *
     * @param id the ID of the account
     */
    public ContaNaoEncontradaException(final Long id) {
        super("CONTA_NAO_ENCONTRADA",
                String.format("Conta com ID %d não encontrada", id));
    }
}
