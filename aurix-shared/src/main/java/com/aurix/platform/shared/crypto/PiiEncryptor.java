package com.aurix.platform.shared.crypto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Criptografia de colunas PII (CPF, CNPJ, dados bancários).
 *
 * Uso em JPA:
 *   @ColumnTransformer(read = "pii_decrypt(cpf_criptografado)", write = "pii_encrypt(?)")
 *   private String cpf;
 *
 * Ou via @Convert:
 *   @Convert(converter = PiiEncryptor.class)
 *   private String cpf;
 */
@Component
public class PiiEncryptor {

    private static final Logger log = LoggerFactory.getLogger(PiiEncryptor.class);
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128;
    private static final int IV_LENGTH = 12;

    private final SecretKeySpec secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    public PiiEncryptor(@Value("${aurix.security.encryption.key-base64:}") String keyBase64) {
        if (keyBase64 == null || keyBase64.isBlank()) {
            log.warn("Chave de criptografia PII não configurada — criptografia desabilitada");
            this.secretKey = null;
        } else {
            byte[] key = Base64.getDecoder().decode(keyBase64);
            this.secretKey = new SecretKeySpec(key, "AES");
            log.info("PiiEncryptor inicializado com chave AES-{}", key.length * 8);
        }
    }

    public String encrypt(String plainText) {
        if (plainText == null || plainText.isBlank() || secretKey == null) {
            return plainText;
        }
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH, iv));
            byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            byte[] combined = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            log.error("Erro ao criptografar PII: {}", e.getMessage());
            return plainText;
        }
    }

    public String decrypt(String cipherText) {
        if (cipherText == null || cipherText.isBlank() || secretKey == null) {
            return cipherText;
        }
        try {
            byte[] combined = Base64.getDecoder().decode(cipherText);
            byte[] iv = new byte[IV_LENGTH];
            byte[] encrypted = new byte[combined.length - IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, IV_LENGTH);
            System.arraycopy(combined, IV_LENGTH, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(GCM_TAG_LENGTH, iv));
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Erro ao descriptografar PII: {}", e.getMessage());
            return cipherText;
        }
    }
}
