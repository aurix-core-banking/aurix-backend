package com.aurix.platform.shared.util;

import java.util.regex.Pattern;

/**
 * Utility class for CPF validation and formatting.
 */
public final class CPFUtil {

    /**
     * Pattern for validating CPF format (11 digits).
     */
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");

    /**
     * Weights for calculating the first check digit.
     */
    private static final int[] PESO_DIGITO_1 = { 10, 9, 8, 7, 6, 5, 4, 3, 2 };

    /**
     * Weights for calculating the second check digit.
     */
    private static final int[] PESO_DIGITO_2 = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

    /** CPF length constant. */
    private static final int CPF_LENGTH = 11;

    /** Modulo for check digit calculation. */
    private static final int MODULO = 11;

    /** Minimum remainder for check digit zero. */
    private static final int MIN_REMAINDER = 2;

    /** First digit position. */
    private static final int FIRST_DIGIT_POS = 9;

    /** Second digit position. */
    private static final int SECOND_DIGIT_POS = 10;

    /** First substring end position. */
    private static final int FIRST_SUBSTR_END = 3;

    /** Second substring end position. */
    private static final int SECOND_SUBSTR_END = 6;

    /** Third substring end position. */
    private static final int THIRD_SUBSTR_END = 9;

    /**
     * Private constructor to prevent instantiation.
     */
    private CPFUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Validates if a CPF is valid.
     *
     * @param cpf the CPF to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValid(final String cpf) {
        if (cpf == null || !CPF_PATTERN.matcher(cpf).matches()) {
            return false;
        }

        // Check if all digits are the same
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calculate first check digit
        int soma = 0;
        for (int i = 0; i < FIRST_DIGIT_POS; i++) {
            soma += Character.getNumericValue(cpf.charAt(i))
                    * PESO_DIGITO_1[i];
        }
        int resto = soma % MODULO;
        int digito1 = resto < MIN_REMAINDER ? 0 : MODULO - resto;

        if (Character.getNumericValue(cpf.charAt(FIRST_DIGIT_POS)) != digito1) {
            return false;
        }

        // Calculate second check digit
        soma = 0;
        for (int i = 0; i < SECOND_DIGIT_POS; i++) {
            soma += Character.getNumericValue(cpf.charAt(i))
                    * PESO_DIGITO_2[i];
        }
        resto = soma % MODULO;
        int digito2 = resto < MIN_REMAINDER ? 0 : MODULO - resto;

        return Character.getNumericValue(cpf.charAt(SECOND_DIGIT_POS)) == digito2;
    }

    /**
     * Formats a CPF for display (123.456.789-01).
     *
     * @param cpf the CPF to format
     * @return formatted CPF or original if invalid
     */
    public static String format(final String cpf) {
        if (cpf == null || cpf.length() != CPF_LENGTH) {
            return cpf;
        }

        return String.format("%s.%s.%s-%s",
                cpf.substring(0, FIRST_SUBSTR_END),
                cpf.substring(FIRST_SUBSTR_END, SECOND_SUBSTR_END),
                cpf.substring(SECOND_SUBSTR_END, THIRD_SUBSTR_END),
                cpf.substring(THIRD_SUBSTR_END, CPF_LENGTH));
    }

    /**
     * Removes formatting from a CPF (digits only).
     *
     * @param cpf the CPF to unformat
     * @return unformatted CPF or null if input is null
     */
    public static String unformat(final String cpf) {
        if (cpf == null) {
            return null;
        }

        return cpf.replaceAll("\\D", "");
    }

    /**
     * Masks a CPF for display (***.***.***-01).
     *
     * @param cpf the CPF to mask
     * @return masked CPF or original if invalid
     */
    public static String mask(final String cpf) {
        if (cpf == null || cpf.length() != CPF_LENGTH) {
            return cpf;
        }

        return String.format("***.***.***-%s",
                cpf.substring(THIRD_SUBSTR_END, CPF_LENGTH));
    }
}
