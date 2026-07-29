package com.aurix.platform.banking.core.resilience;

import com.aurix.platform.shared.config.CacheConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@Disabled("Requires Docker/Testcontainers — skip in CI environments without Docker")
@SpringBootTest(
    classes = RedisBackedCacheIntegrationTest.Config.class,
    properties = {
        "spring.autoconfigure.exclude[0]=org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration",
        "spring.autoconfigure.exclude[1]=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration"
    }
)
class RedisBackedCacheIntegrationTest {

    @Container
    static GenericContainer<?> redis = new GenericContainer<>("redis:7-alpine")
        .withExposedPorts(6379);

    @Autowired
    private CacheManager cacheManager;

    @Test
    void cacheManagerWrapsRedisWhenAvailable() {
        assertThat(cacheManager).isNotNull();
        assertThat(cacheManager.getCache("check")).isNotNull();
    }

    @Test
    void cacheOperationsWorkWithRedis() {
        Cache cache = cacheManager.getCache("redis-test");
        assertThat(cache).isNotNull();
        cache.put("k1", "v1");
        assertThat(cache.get("k1").get()).isEqualTo("v1");
        cache.evict("k1");
        assertThat(cache.get("k1")).isNull();
    }

    @Test
    void cacheGetAndEvictCycle() {
        Cache cache = cacheManager.getCache("cycle-test");
        assertThat(cache).isNotNull();
        cache.put("x", "y");
        var val = cache.get("x");
        assertThat(val).isNotNull();
        assertThat(val.get()).isEqualTo("y");
        cache.evict("x");
    }

    @org.springframework.context.annotation.Configuration
    @EnableAutoConfiguration
    @Import(CacheConfig.class)
    static class Config {
        @Bean
        RedisConnectionFactory redisConnectionFactory() {
            return new LettuceConnectionFactory(
                redis.getHost(), redis.getMappedPort(6379));
        }
    }
}
