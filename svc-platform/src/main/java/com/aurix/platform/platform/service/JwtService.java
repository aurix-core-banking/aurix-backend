package com.aurix.platform.platform.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Serviço para geração e validação de JWT
 */
@Service
@SuppressWarnings({"PMD.UselessParentheses"})
public class JwtService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(JwtService.class);
    @Value("${aurix.jwt.secret}")
    private String secret;
    @Value("${aurix.jwt.expiration}")
    private Long expiration;

    /**
     * Gera token JWT para um usuário
     */
    public String generateToken(String email, Long userId, String nome, Set<String> roles, Set<String> permissions) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("nome", nome);
        claims.put("roles", roles);
        claims.put("permissions", permissions);
        return createToken(claims, email);
    }

    /**
     * Cria o token JWT
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(now).setExpiration(expiryDate).signWith(getSigningKey(), SignatureAlgorithm.HS512).compact();
    }

    /**
     * Valida o token JWT
     */
    public Boolean validateToken(String token, String email) {
        try {
            final String username = extractUsername(token);
            return (username.equals(email) && !isTokenExpired(token));
        } catch (Exception e) {
            log.error("Erro ao validar token: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Extrai o username do token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrai o ID do usuário do token
     */
    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    /**
     * Extrai as roles do token
     */
    @SuppressWarnings("unchecked")
    public Set<String> extractRoles(String token) {
        return extractClaim(token, claims -> (Set<String>) claims.get("roles"));
    }

    /**
     * Extrai as permissões do token
     */
    @SuppressWarnings("unchecked")
    public Set<String> extractPermissions(String token) {
        return extractClaim(token, claims -> (Set<String>) claims.get("permissions"));
    }

    /**
     * Extrai a data de expiração do token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrai uma claim específica do token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrai todas as claims do token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Verifica se o token expirou
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Obtém a chave de assinatura
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Obtém o tempo de expiração em milissegundos
     */
    public Long getExpirationTime() {
        return expiration;
    }
}
