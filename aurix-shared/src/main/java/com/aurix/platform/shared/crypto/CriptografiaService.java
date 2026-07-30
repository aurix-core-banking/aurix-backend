package com.aurix.platform.shared.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Serviço de criptografia para proteção de dados sensíveis.
 */
public final class CriptografiaService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(CriptografiaService.class);
    /**
     * Gerador de números aleatórios seguro.
     */
    private static final SecureRandom RANDOM = new SecureRandom();
    /**
     * Nome do algoritmo de criptografia AES GCM sem padding.
     */
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    /**
     * Tamanho da tag GCM em bits: 128.
     */
    private static final int GCM_TAG_LENGTH = 128;
    /**
     * Tamanho do IV GCM em bytes: 12.
     */
    private static final int GCM_IV_LENGTH = 12;
    /**
     * Tamanho mínimo da chave AES: 16 bytes.
     */
    private static final int MIN_KEY_SIZE = 16;
    /**
     * Tamanho máximo da chave AES: 32 bytes.
     */
    private static final int MAX_KEY_SIZE = 32;
    /**
     * Base 16 para conversão hexadecimal.
     */
    private static final int BASE_HEX = 16;
    /**
     * Deslocamento para conversão hexadecimal.
     */
    private static final int HEX_SHIFT = 4;
    /**
     * Chave secreta AES.
     */
    private final SecretKey key;

    /**
     * Construtor que inicializa o serviço com uma chave AES.
     *
     * @param keyBytes Bytes da chave AES (16 ou 32)
     */
    public CriptografiaService(final byte[] keyBytes) {
        if (keyBytes == null || (keyBytes.length != MIN_KEY_SIZE && keyBytes.length != MAX_KEY_SIZE)) {
            throw new IllegalArgumentException("Chave AES deve ter 16 ou 32 bytes");
        }
        this.key = new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * Criptografa um texto claro usando AES GCM.
     *
     * @param textoClaro Texto a ser criptografado
     * @return Texto criptografado em Base64
     */
    public String criptografar(final String textoClaro) {
        if (textoClaro == null) {
            return null;
        }
        try {
            byte[] iv = new byte[GCM_IV_LENGTH];
            RANDOM.nextBytes(iv);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            byte[] cipherText = cipher.doFinal(textoClaro.getBytes(StandardCharsets.UTF_8));
            ByteBuffer bb = ByteBuffer.allocate(iv.length + cipherText.length);
            bb.put(iv);
            bb.put(cipherText);
            return Base64.getEncoder().encodeToString(bb.array());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Falha ao criptografar", e);
        }
    }

    /**
     * Descriptografa um texto criptografado em Base64.
     *
     * @param textoCriptografado Texto em Base64
     * @return Texto original descriptografado
     */
    public String descriptografar(final String textoCriptografado) {
        if (textoCriptografado == null || textoCriptografado.isBlank()) {
            return null;
        }
        try {
            byte[] decoded = Base64.getDecoder().decode(textoCriptografado);
            ByteBuffer bb = ByteBuffer.wrap(decoded);
            byte[] iv = new byte[GCM_IV_LENGTH];
            bb.get(iv);
            byte[] cipherText = new byte[bb.remaining()];
            bb.get(cipherText);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            byte[] plain = cipher.doFinal(cipherText);
            return new String(plain, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Falha ao descriptografar", e);
        }
    }

    /**
     * Decodifica uma chave em Base64 para bytes.
     *
     * @param base64 Chave em Base64
     * @return Bytes da chave
     */
    public static byte[] chaveDeBase64(final String base64) {
        return Base64.getDecoder().decode(base64);
    }

    /**
     * Decodifica uma chave em hexadecimal para bytes.
     *
     * @param hex Chave em hexadecimal
     * @return Bytes da chave
     */
    public static byte[] chaveDeHex(final String hex) {
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), BASE_HEX) << HEX_SHIFT) + Character.digit(hex.charAt(i + 1), BASE_HEX));
        }
        return data;
    }
}
