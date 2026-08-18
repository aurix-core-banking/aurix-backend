package com.aurix.platform.shared.entity;

import jakarta.persistence.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

/**
 * API Key com hash SHA-256, rotação, revogação.
 * Armazenada em DB — NUNCA em plaintext no YAML.
 *
 * Fluxo:
 * 1. Admin gera key via endpoint
 * 2. Key retornada APENAS na criação (plaintext)
 * 3. DB armazena SHA-256 hash + prefixo (para lookup)
 * 4. Gateway valida: hash SHA-256 da key recebida → compara com armazenado
 */
@Entity
@Table(name = "api_keys")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 16)
    private String prefixo; // primeiros 8 chars — para lookup rápido

    @Column(nullable = false, length = 64)
    private String keyHash; // SHA-256 da API key

    @Column(nullable = false, length = 100)
    private String nome; // nome descritivo (ex: "Parceiro X - Produção")

    @Column(nullable = false, length = 50)
    private String tenantId;

    @Column(nullable = false, length = 30)
    private String plano; // free, sandbox, starter, growth, enterprise

    @Column(nullable = false)
    private boolean ativo;

    @Column(nullable = false)
    private LocalDateTime dataCriacao;

    private LocalDateTime dataExpiracao;

    private LocalDateTime dataUltimoUso;

    private LocalDateTime dataRotacao;

    private Integer usoTotal;

    @Column(nullable = false)
    private Integer rateLimitRpm;

    // ═══ Geração de API Key ═══

    /**
     * Gera nova API key.
     * Retornar APENAS esta string — depois não pode ser recuperada.
     */
    public static String gerarKey() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    /**
     * Hash SHA-256 da API key para armazenamento seguro.
     */
    public static String hashKey(String key) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(key.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar hash SHA-256", e);
        }
    }

    /**
     * Extrai prefixo da key (primeiros 16 chars) para lookup no DB.
     */
    public static String extrairPrefixo(String key) {
        return key.length() > 16 ? key.substring(0, 16) : key;
    }

    // ═══ Lifecycle ═══

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
        this.ativo = true;
        this.usoTotal = 0;
        if (this.rateLimitRpm == null) {
            this.rateLimitRpm = switch (this.plano) {
                case "enterprise" -> 1000;
                case "growth" -> 300;
                case "starter" -> 60;
                case "sandbox" -> 30;
                default -> 10;
            };
        }
    }

    public void registrarUso() {
        this.dataUltimoUso = LocalDateTime.now();
        this.usoTotal = (this.usoTotal == null ? 0 : this.usoTotal) + 1;
    }

    public void rotacionar(String novaKey) {
        this.keyHash = hashKey(novaKey);
        this.prefixo = extrairPrefixo(novaKey);
        this.dataRotacao = LocalDateTime.now();
    }

    public void revogar() {
        this.ativo = false;
    }

    public boolean isExpirado() {
        return dataExpiracao != null && dataExpiracao.isBefore(LocalDateTime.now());
    }

    // ═══ Getters/Setters ═══

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPrefixo() { return prefixo; }
    public void setPrefixo(String prefixo) { this.prefixo = prefixo; }
    public String getKeyHash() { return keyHash; }
    public void setKeyHash(String keyHash) { this.keyHash = keyHash; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getPlano() { return plano; }
    public void setPlano(String plano) { this.plano = plano; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public LocalDateTime getDataExpiracao() { return dataExpiracao; }
    public void setDataExpiracao(LocalDateTime dataExpiracao) { this.dataExpiracao = dataExpiracao; }
    public LocalDateTime getDataUltimoUso() { return dataUltimoUso; }
    public LocalDateTime getDataRotacao() { return dataRotacao; }
    public Integer getUsoTotal() { return usoTotal; }
    public Integer getRateLimitRpm() { return rateLimitRpm; }
    public void setRateLimitRpm(Integer rateLimitRpm) { this.rateLimitRpm = rateLimitRpm; }
}
