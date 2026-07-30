package com.aurix.platform.shared.util;

import java.util.Random;

/**
 * Utility class for account number generation and validation.
 */
public final class ContaUtil {

    /** Random number generator. */
    private static final Random RANDOM = new Random();

    /** Pattern for account number validation. */
    private static final String CONTA_PATTERN = "\\d{5}-\\d{1}";

    /** Account number length without separator. */
    private static final int ACCOUNT_LENGTH = 6;

    /** Account number part length. */
    private static final int ACCOUNT_NUMBER_LENGTH = 5;

    /** Check digit position. */
    private static final int CHECK_DIGIT_POS = 6;

    /** Modulo for check digit calculation. */
    private static final int MODULO = 10;

    /** Multiplier value 1. */
    private static final int MULTIPLIER_ONE = 1;

    /** Multiplier value 2. */
    private static final int MULTIPLIER_TWO = 2;

    /** Divisor for digit extraction. */
    private static final int DIVISOR = 10;

    /** Threshold for digit sum. */
    private static final int DIGIT_THRESHOLD = 9;

    /** Random range for account generation. */
    private static final int RANDOM_RANGE = 90000;

    /** Random base for account generation. */
    private static final int RANDOM_BASE = 10000;

    /** Last visible digit position for masking. */
    private static final int MASK_START_POS = 3;

    /** Mask end position. */
    private static final int MASK_END_POS = 5;

    /** Check digit start position for masking. */
    private static final int MASK_CHECK_START = 6;

    /** Check digit end position for masking. */
    private static final int MASK_CHECK_END = 7;

    /**
     * Private constructor to prevent instantiation.
     */
    private ContaUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Generates a unique account number.
     * Format: 12345-6 (5 digits + 1 check digit).
     *
     * @return generated account number
     */
    public static String gerarNumeroConta() {
        // Generate 5 random digits
        int numero = RANDOM.nextInt(RANDOM_RANGE) + RANDOM_BASE;

        // Calculate check digit using modulo 10
        int soma = 0;
        int temp = numero;
        int multiplicador = MULTIPLIER_TWO;

        while (temp > 0) {
            int digito = temp % DIVISOR;
            int produto = digito * multiplicador;

            // If product is greater than 9, sum the digits
            if (produto > DIGIT_THRESHOLD) {
                produto = (produto / DIVISOR) + (produto % DIVISOR);
            }

            soma += produto;
            temp /= DIVISOR;
            multiplicador = multiplicador == MULTIPLIER_TWO
                    ? MULTIPLIER_ONE
                    : MULTIPLIER_TWO;
        }

        int digitoVerificador = (MODULO - (soma % MODULO)) % MODULO;

        return String.format("%05d-%d", numero, digitoVerificador);
    }

    /**
     * Validates if an account number is valid.
     *
     * @param numeroConta the account number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValid(final String numeroConta) {
        if (numeroConta == null || !numeroConta.matches(CONTA_PATTERN)) {
            return false;
        }

        // Extract number and check digit
        String numero = numeroConta.substring(0, ACCOUNT_NUMBER_LENGTH);
        int digitoVerificador = Character.getNumericValue(numeroConta.charAt(CHECK_DIGIT_POS));

        // Calculate expected check digit
        int soma = 0;
        int multiplicador = MULTIPLIER_TWO;

        for (int i = ACCOUNT_NUMBER_LENGTH - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));
            int produto = digito * multiplicador;

            if (produto > DIGIT_THRESHOLD) {
                produto = (produto / DIVISOR) + (produto % DIVISOR);
            }

            soma += produto;
            multiplicador = multiplicador == MULTIPLIER_TWO
                    ? MULTIPLIER_ONE
                    : MULTIPLIER_TWO;
        }

        int digitoEsperado = (MODULO - (soma % MODULO)) % MODULO;

        return digitoVerificador == digitoEsperado;
    }

    /**
     * Formats an account number for display.
     *
     * @param numeroConta the account number to format
     * @return formatted account number or null if input is null
     */
    public static String format(final String numeroConta) {
        if (numeroConta == null) {
            return null;
        }

        // Remove existing formatting
        String apenasNumeros = numeroConta.replaceAll("\\D", "");

        if (apenasNumeros.length() != ACCOUNT_LENGTH) {
            return numeroConta;
        }

        return String.format("%s-%s",
                apenasNumeros.substring(0, ACCOUNT_NUMBER_LENGTH),
                apenasNumeros.substring(ACCOUNT_NUMBER_LENGTH,
                        ACCOUNT_LENGTH));
    }

    /**
     * Masks an account number for display (***45-6).
     *
     * @param numeroConta the account number to mask
     * @return masked account number or original if invalid
     */
    public static String mask(final String numeroConta) {
        if (numeroConta == null || !numeroConta.matches(CONTA_PATTERN)) {
            return numeroConta;
        }

        return String.format("***%s-%s",
                numeroConta.substring(MASK_START_POS, MASK_END_POS),
                numeroConta.substring(MASK_CHECK_START, MASK_CHECK_END));
    }
}
