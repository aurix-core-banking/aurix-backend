package com.aurix.platform.shared.crypto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converter JPA para criptografia automática de colunas PII.
 *
 * Uso:
 *   @Convert(converter = PiiColumnConverter.class)
 *   @Column(name = "cpf_criptografado")
 *   private String cpf;
 *
 * O valor é criptografado ao escrever no DB e descriptografado ao ler.
 */
@Converter(autoApply = false)
public class PiiColumnConverter implements AttributeConverter<String, String> {

    private static PiiEncryptor encryptor;

    public static void setEncryptor(PiiEncryptor instance) {
        encryptor = instance;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        if (attribute == null || encryptor == null) return attribute;
        return encryptor.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        if (dbData == null || encryptor == null) return dbData;
        return encryptor.decrypt(dbData);
    }
}
