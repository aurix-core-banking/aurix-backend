package com.aurix.platform.shared.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * Utility class for transaction code generation and validation.
 */
public final class TransacaoUtil {

    /** Random number generator. */
    private static final Random RANDOM = new Random();

    /** Prefix for transaction codes. */
    private static final String PREFIXO = "Aurix";

    /**
     * Formatter for timestamps in transaction codes.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /** Range de números aleatórios para transação: 9000. */
    private static final int RANGE_ALEATORIO_TRANSACAO = 9000;

    /** Base de números aleatórios para transação: 1000. */
    private static final int BASE_ALEATORIO_TRANSACAO = 1000;

    /** Range de números aleatórios para PIX/TED/DOC: 900000. */
    private static final int RANGE_ALEATORIO_REF = 900000;

    /** Base de números aleatórios para PIX/TED/DOC: 100000. */
    private static final int BASE_ALEATORIO_REF = 100000;

    /**
     * Private constructor to prevent instantiation.
     */
    private TransacaoUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Gera um código único para transação.
     * Formato: Aurix-20240120143025-1234
     *
     * @return Código de transação gerado
     */
    public static String gerarCodigoTransacao() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        int numeroAleatorio = RANDOM.nextInt(RANGE_ALEATORIO_TRANSACAO)
                + BASE_ALEATORIO_TRANSACAO;

        return String.format("%s-%s-%04d", PREFIXO, timestamp, numeroAleatorio);
    }

    /**
     * Valida se um código de transação é válido.
     *
     * @param codigo Código a validar
     * @return true se o código é válido
     */
    public static boolean isValidCodigoTransacao(final String codigo) {
        if (codigo == null || codigo.isEmpty()) {
            return false;
        }

        // Verificar formato: Aurix-YYYYMMDDHHMMSS-NNNN
        String pattern = "^" + PREFIXO + "-\\d{14}-\\d{4}$";
        return codigo.matches(pattern);
    }

    /**
     * Extrai o timestamp de um código de transação.
     *
     * @param codigo Código de transação
     * @return LocalDateTime extraído ou null
     */
    public static LocalDateTime extrairTimestamp(final String codigo) {
        if (!isValidCodigoTransacao(codigo)) {
            return null;
        }

        try {
            String[] partes = codigo.split("-");
            if (partes.length >= 2) {
                String timestampStr = partes[1];
                return LocalDateTime.parse(timestampStr, FORMATTER);
            }
        } catch (Exception e) {
            // Log de erro de parsing ignorado conforme regra original, mas agora
            // documentado
            System.err.println("Erro ao extrair timestamp: " + e.getMessage());
        }

        return null;
    }

    /**
     * Gera um código de referência para PIX.
     *
     * @return Código PIX gerado
     */
    public static String gerarCodigoPix() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        int numeroAleatorio = RANDOM.nextInt(RANGE_ALEATORIO_REF)
                + BASE_ALEATORIO_REF;

        return String.format("PIX%s%s", timestamp, numeroAleatorio);
    }

    /**
     * Gera um código de referência para TED.
     *
     * @return Código TED gerado
     */
    public static String gerarCodigoTed() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        int numeroAleatorio = RANDOM.nextInt(RANGE_ALEATORIO_REF)
                + BASE_ALEATORIO_REF;

        return String.format("TED%s%s", timestamp, numeroAleatorio);
    }

    /**
     * Gera um código de referência para DOC.
     *
     * @return Código DOC gerado
     */
    public static String gerarCodigoDoc() {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        int numeroAleatorio = RANDOM.nextInt(RANGE_ALEATORIO_REF)
                + BASE_ALEATORIO_REF;

        return String.format("DOC%s%s", timestamp, numeroAleatorio);
    }
}
