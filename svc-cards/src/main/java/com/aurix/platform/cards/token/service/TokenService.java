package com.aurix.platform.cards.token.service;

import com.aurix.platform.cards.entity.Cartao;
import com.aurix.platform.cards.repository.CartaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional
public class TokenService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TokenService.class);
    private final CartaoRepository cartaoRepository;

    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 128;
    private static final AtomicLong TOKEN_COUNTER = new AtomicLong(System.currentTimeMillis());
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final Map<String, TokenVaultEntry> vault = new ConcurrentHashMap<>();

    @java.lang.SuppressWarnings("all")
    public TokenService(final CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public Map<String, Object> criarToken(Long cartaoId, String merchantId, String descricao) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado: " + cartaoId));
        if (cartao.getStatus() != Cartao.StatusCartao.ATIVO) {
            throw new RuntimeException("Cartão não está ativo para tokenização");
        }

        for (TokenVaultEntry entry : vault.values()) {
            if (entry.getCartaoId().equals(cartaoId)
                    && entry.getMerchantId().equals(merchantId)
                    && entry.getStatus() == TokenStatus.ATIVO) {
                throw new RuntimeException("Já existe token ativo para este cartão e merchant");
            }
        }

        String tokenFormatado = "tok_" + gerarTokenHex();
        String panEncriptado = encriptarPAN(cartao.getNumeroCartao());

        TokenVaultEntry entry = new TokenVaultEntry();
        entry.setTokenId(generarIdToken());
        entry.setTokenFormatado(tokenFormatado);
        entry.setCartaoId(cartaoId);
        entry.setMerchantId(merchantId);
        entry.setPanEncriptado(panEncriptado);
        entry.setUltimos4Digitos(cartao.getNumeroCartao().substring(12));
        entry.setBandeira(cartao.getBandeira().name());
        entry.setDescricao(descricao);
        entry.setStatus(TokenStatus.ATIVO);
        entry.setDataCriacao(LocalDateTime.now());
        entry.setDataExpiracao(LocalDateTime.now().plusMonths(24));
        vault.put(entry.getTokenId(), entry);

        log.info("Token criado: token={}, cartaoId={}, merchantId={}", tokenFormatado, cartaoId, merchantId);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("tokenId", entry.getTokenId());
        resultado.put("token", tokenFormatado);
        resultado.put("ultimos4Digitos", entry.getUltimos4Digitos());
        resultado.put("bandeira", entry.getBandeira());
        resultado.put("merchantId", merchantId);
        resultado.put("status", entry.getStatus());
        resultado.put("dataExpiracao", entry.getDataExpiracao());
        return resultado;
    }

    public List<Map<String, Object>> listarTokens(Long cartaoId) {
        List<Map<String, Object>> tokens = new ArrayList<>();
        for (TokenVaultEntry entry : vault.values()) {
            if (entry.getCartaoId().equals(cartaoId)) {
                Map<String, Object> info = new HashMap<>();
                info.put("tokenId", entry.getTokenId());
                info.put("token", entry.getTokenFormatado());
                info.put("merchantId", entry.getMerchantId());
                info.put("ultimos4Digitos", entry.getUltimos4Digitos());
                info.put("bandeira", entry.getBandeira());
                info.put("descricao", entry.getDescricao());
                info.put("status", entry.getStatus().name());
                info.put("dataCriacao", entry.getDataCriacao());
                info.put("dataExpiracao", entry.getDataExpiracao());
                tokens.add(info);
            }
        }
        return tokens;
    }

    public Map<String, Object> revogarToken(Long tokenId) {
        TokenVaultEntry entry = vault.get(String.valueOf(tokenId));
        if (entry == null) {
            throw new RuntimeException("Token não encontrado: " + tokenId);
        }
        if (entry.getStatus() != TokenStatus.ATIVO) {
            throw new RuntimeException("Token não está ativo");
        }
        entry.setStatus(TokenStatus.REVOGADO);
        entry.setDataRevogacao(LocalDateTime.now());

        log.info("Token revogado: tokenId={}, merchantId={}", tokenId, entry.getMerchantId());

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("tokenId", entry.getTokenId());
        resultado.put("token", entry.getTokenFormatado());
        resultado.put("status", entry.getStatus().name());
        resultado.put("dataRevogacao", entry.getDataRevogacao());
        return resultado;
    }

    private String encriptarPAN(String pan) {
        try {
            byte[] iv = new byte[GCM_IV_LENGTH];
            SECURE_RANDOM.nextBytes(iv);
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            SecretKey chave = keyGen.generateKey();
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, chave, spec);
            byte[] textoEncriptado = cipher.doFinal(pan.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            ByteBuffer buffer = ByteBuffer.allocate(iv.length + textoEncriptado.length);
            buffer.put(iv);
            buffer.put(textoEncriptado);
            return Base64.getEncoder().encodeToString(buffer.array());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao encriptar PAN: " + e.getMessage(), e);
        }
    }

    private String gerarTokenHex() {
        byte[] bytes = new byte[16];
        SECURE_RANDOM.nextBytes(bytes);
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    private Long generarIdToken() {
        return TOKEN_COUNTER.incrementAndGet();
    }

    public enum TokenStatus {
        ATIVO, INATIVO, EXPIRADO, REVOGADO
    }

    public static class TokenVaultEntry {
        private Long tokenId;
        private String tokenFormatado;
        private Long cartaoId;
        private String merchantId;
        private String panEncriptado;
        private String ultimos4Digitos;
        private String bandeira;
        private String descricao;
        private TokenStatus status;
        private LocalDateTime dataCriacao;
        private LocalDateTime dataExpiracao;
        private LocalDateTime dataRevogacao;

        public Long getTokenId() { return tokenId; }
        public void setTokenId(Long tokenId) { this.tokenId = tokenId; }
        public String getTokenFormatado() { return tokenFormatado; }
        public void setTokenFormatado(String tokenFormatado) { this.tokenFormatado = tokenFormatado; }
        public Long getCartaoId() { return cartaoId; }
        public void setCartaoId(Long cartaoId) { this.cartaoId = cartaoId; }
        public String getMerchantId() { return merchantId; }
        public void setMerchantId(String merchantId) { this.merchantId = merchantId; }
        public String getPanEncriptado() { return panEncriptado; }
        public void setPanEncriptado(String panEncriptado) { this.panEncriptado = panEncriptado; }
        public String getUltimos4Digitos() { return ultimos4Digitos; }
        public void setUltimos4Digitos(String ultimos4Digitos) { this.ultimos4Digitos = ultimos4Digitos; }
        public String getBandeira() { return bandeira; }
        public void setBandeira(String bandeira) { this.bandeira = bandeira; }
        public String getDescricao() { return descricao; }
        public void setDescricao(String descricao) { this.descricao = descricao; }
        public TokenStatus getStatus() { return status; }
        public void setStatus(TokenStatus status) { this.status = status; }
        public LocalDateTime getDataCriacao() { return dataCriacao; }
        public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
        public LocalDateTime getDataExpiracao() { return dataExpiracao; }
        public void setDataExpiracao(LocalDateTime dataExpiracao) { this.dataExpiracao = dataExpiracao; }
        public LocalDateTime getDataRevogacao() { return dataRevogacao; }
        public void setDataRevogacao(LocalDateTime dataRevogacao) { this.dataRevogacao = dataRevogacao; }
    }
}
