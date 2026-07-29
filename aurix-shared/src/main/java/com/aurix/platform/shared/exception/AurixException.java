package com.aurix.platform.shared.exception;

/**
 * Base exception for the Aurix platform.
 *
 * <p>
 * This exception serves as the parent class for all custom
 * exceptions in the Aurix system, providing consistent error
     * handling with error codes and details.
 */
public class AurixException extends RuntimeException {

    /**
     * Error code identifying the type of exception.
     */
    private final String codigo;

    /**
     * Additional details about the exception.
     */
    private final String detalhes;

    /**
     * Constructs a new AurixException with the specified message.
     *
     * @param mensagem the detail message
     */
    public AurixException(final String mensagem) {
        super(mensagem);
        this.codigo = "AURIX_ERROR";
        this.detalhes = null;
    }

    /**
     * Constructs a new AurixException with code and message.
     *
     * @param errorCode the error code
     * @param mensagem  the detail message
     */
    public AurixException(final String errorCode, final String mensagem) {
        super(mensagem);
        this.codigo = errorCode;
        this.detalhes = null;
    }

    /**
     * Constructs a new AurixException with code, message and details.
     *
     * @param errorCode    the error code
     * @param mensagem     the detail message
     * @param errorDetails additional error details
     */
    public AurixException(final String errorCode, final String mensagem,
            final String errorDetails) {
        super(mensagem);
        this.codigo = errorCode;
        this.detalhes = errorDetails;
    }

    /**
     * Constructs a new AurixException with code, message and cause.
     *
     * @param errorCode the error code
     * @param mensagem  the detail message
     * @param causa     the cause of the exception
     */
    public AurixException(final String errorCode, final String mensagem,
            final Throwable causa) {
        super(mensagem, causa);
        this.codigo = errorCode;
        this.detalhes = null;
    }

    /**
     * Returns the error code.
     *
     * @return the error code
     */
    public final String getCodigo() {
        return codigo;
    }

    /**
     * Returns the error details.
     *
     * @return the error details, or null if not provided
     */
    public final String getDetalhes() {
        return detalhes;
    }
}
