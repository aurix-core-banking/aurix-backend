package com.aurix.platform.shared.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Collection;

@Configuration
public class CacheConfig {

    private static final Logger log = LoggerFactory.getLogger(CacheConfig.class);

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public CacheManager cacheManager(
            org.springframework.beans.factory.ObjectProvider<RedisConnectionFactory> connectionFactoryProvider) {
        RedisConnectionFactory connectionFactory = connectionFactoryProvider.getIfAvailable();
        if (connectionFactory == null) {
            log.info("RedisConnectionFactory not available, using local cache only");
            return new ConcurrentMapCacheManager();
        }
        try {
            var config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .disableCachingNullValues()
                .serializeKeysWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer()));
            var redis = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
            return new ResilientCacheManager(redis, new ConcurrentMapCacheManager());
        } catch (Exception e) {
            log.warn("Redis unavailable at startup, using local cache only: {}", e.getMessage());
            return new ConcurrentMapCacheManager();
        }
    }

    @Bean
    @ConditionalOnMissingBean
    @org.springframework.boot.autoconfigure.condition.ConditionalOnBean(RedisConnectionFactory.class)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    public static class ResilientCacheManager implements CacheManager {
        private final CacheManager primary;
        private final CacheManager fallback;

        public ResilientCacheManager(CacheManager primary, CacheManager fallback) {
            this.primary = primary;
            this.fallback = fallback;
        }

        @Override
        public Cache getCache(String name) {
            try {
                return primary.getCache(name);
            } catch (Exception e) {
                log.warn("Redis cache '{}' unavailable, fallback to local: {}", name, e.getMessage());
                return fallback.getCache(name);
            }
        }

        @Override
        public Collection<String> getCacheNames() {
            try {
                return primary.getCacheNames();
            } catch (Exception e) {
                return fallback.getCacheNames();
            }
        }
    }
}
