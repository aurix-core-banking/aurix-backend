package com.aurix.platform.shared.util;

import java.util.regex.Pattern;

/**
 * Utility class for CNPJ validation and formatting.
 */
public final class CNPJUtil {

    /**
     * Pattern for validating CNPJ format (14 digits).
     */
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");

    /**
     * Weights for calculating the first check digit.
     */
    private static final int[] PESO_DIGITO_1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

    /**
     * Weights for calculating the second check digit.
     */
    private static final int[] PESO_DIGITO_2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

    /** CNPJ length constant. */
    private static final int CNPJ_LENGTH = 14;

    /** Modulo for check digit calculation. */
    private static final int MODULO = 11;

    /** Minimum remainder for check digit zero. */
    private static final int MIN_REMAINDER = 2;

    /** First digit position. */
    private static final int FIRST_DIGIT_POS = 12;

    /** Second digit position. */
    private static final int SECOND_DIGIT_POS = 13;

    /** First substring end position. */
    private static final int REG1_END = 2;

    /** Second substring end position. */
    private static final int REG2_END = 5;

    /** Third substring end position. */
    private static final int REG3_END = 8;

    /** Fourth substring end position. */
    private static final int REG4_END = 12;

    /**
     * Private constructor to prevent instantiation.
     */
    private CNPJUtil() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Validates if a CNPJ is valid.
     *
     * @param cnpj the CNPJ to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValid(final String cnpj) {
        if (cnpj == null || !CNPJ_PATTERN.matcher(cnpj).matches()) {
            return false;
        }

        // Check if all digits are the same
        if (cnpj.matches("(\\d)\\1{13}")) {
            return false;
        }

        // Calculate first check digit
        int soma = 0;
        for (int i = 0; i < FIRST_DIGIT_POS; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i))
                    * PESO_DIGITO_1[i];
        }
        int resto = soma % MODULO;
        int digito1 = resto < MIN_REMAINDER ? 0 : MODULO - resto;

        if (Character.getNumericValue(cnpj.charAt(FIRST_DIGIT_POS)) != digito1) {
            return false;
        }

        // Calculate second check digit
        soma = 0;
        for (int i = 0; i < SECOND_DIGIT_POS; i++) {
            soma += Character.getNumericValue(cnpj.charAt(i))
                    * PESO_DIGITO_2[i];
        }
        resto = soma % MODULO;
        int digito2 = resto < MIN_REMAINDER ? 0 : MODULO - resto;

        return Character.getNumericValue(cnpj.charAt(SECOND_DIGIT_POS)) == digito2;
    }

    /**
     * Formats a CNPJ for display (11.222.333/0001-81).
     *
     * @param cnpj the CNPJ to format
     * @return formatted CNPJ or original if invalid
     */
    public static String format(final String cnpj) {
        if (cnpj == null || cnpj.length() != CNPJ_LENGTH) {
            return cnpj;
        }

        return String.format("%s.%s.%s/%s-%s",
                cnpj.substring(0, REG1_END),
                cnpj.substring(REG1_END, REG2_END),
                cnpj.substring(REG2_END, REG3_END),
                cnpj.substring(REG3_END, REG4_END),
                cnpj.substring(REG4_END, CNPJ_LENGTH));
    }

    /**
     * Removes formatting from a CNPJ (digits only).
     *
     * @param cnpj the CNPJ to unformat
     * @return unformatted CNPJ or null if input is null
     */
    public static String unformat(final String cnpj) {
        if (cnpj == null) {
            return null;
        }

        return cnpj.replaceAll("\\D", "");
    }

    /**
     * Masks a CNPJ for display (**.222.333/0001-81).
     *
     * @param cnpj the CNPJ to mask
     * @return masked CNPJ or original if invalid
     */
    public static String mask(final String cnpj) {
        if (cnpj == null || cnpj.length() != CNPJ_LENGTH) {
            return cnpj;
        }

        return String.format("**.%s.%s/%s-%s",
                cnpj.substring(REG1_END, REG2_END),
                cnpj.substring(REG2_END, REG3_END),
                cnpj.substring(REG3_END, REG4_END),
                cnpj.substring(REG4_END, CNPJ_LENGTH));
    }
}
