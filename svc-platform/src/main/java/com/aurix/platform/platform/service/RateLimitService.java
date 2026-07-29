package com.aurix.platform.platform.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class RateLimitService {
    @java.lang.SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(RateLimitService.class);
    private final RedisTemplate<String, String> redisTemplate;
    private static final String RATE_LIMIT_PREFIX = "rate_limit:";
    private static final int DEFAULT_LIMIT = 100;
    private static final int DEFAULT_WINDOW_MINUTES = 15;

    public boolean verificarRateLimit(String clientId, String endpoint) {
        String key = RATE_LIMIT_PREFIX + clientId + ":" + endpoint;
        String countStr = redisTemplate.opsForValue().get(key);
        int count = countStr != null ? Integer.parseInt(countStr) : 0;
        if (count >= DEFAULT_LIMIT) {
            log.warn("Rate limit excedido para cliente {} no endpoint {}", clientId, endpoint);
            return false;
        }
        redisTemplate.opsForValue().increment(key);
        redisTemplate.expire(key, Duration.ofMinutes(DEFAULT_WINDOW_MINUTES));
        return true;
    }

    public int obterLimiteRestante(String clientId, String endpoint) {
        String key = RATE_LIMIT_PREFIX + clientId + ":" + endpoint;
        String countStr = redisTemplate.opsForValue().get(key);
        int count = countStr != null ? Integer.parseInt(countStr) : 0;
        return Math.max(0, DEFAULT_LIMIT - count);
    }

    @java.lang.SuppressWarnings("all")
    public RateLimitService(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
