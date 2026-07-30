package com.aurix.platform.banking.core.resilience;

import com.aurix.platform.shared.config.CacheConfig.ResilientCacheManager;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResilientCacheManagerTest {

    private CacheManager primary;
    private CacheManager fallback;
    private CacheManager resilient;

    @BeforeEach
    void setUp() {
        fallback = new ConcurrentMapCacheManager();
    }

    @Test
    void delegatesToPrimaryWhenPrimaryWorks() {
        primary = new ConcurrentMapCacheManager();
        resilient = new ResilientCacheManager(primary, fallback);

        Cache cache = resilient.getCache("test");
        assertThat(cache).isNotNull();
        cache.put("k", "v");
        assertThat(cache.get("k").get()).isEqualTo("v");
    }

    @Test
    void fallsBackWhenPrimaryThrows() {
        primary = new CacheManager() {
            @Override
            public Cache getCache(String name) {
                throw new RuntimeException("Redis connection refused");
            }

            @Override
            public Collection<String> getCacheNames() {
                throw new RuntimeException("Redis connection refused");
            }
        };
        resilient = new ResilientCacheManager(primary, fallback);

        Cache cache = resilient.getCache("test");
        assertThat(cache).isNotNull();
        cache.put("k", "v");
        assertThat(cache.get("k").get()).isEqualTo("v");
    }

    @Test
    void fallbackHandlesGetCacheNamesWhenPrimaryFails() {
        primary = new CacheManager() {
            @Override
            public Cache getCache(String name) {
                throw new RuntimeException("Redis connection refused");
            }

            @Override
            public Collection<String> getCacheNames() {
                throw new RuntimeException("Redis connection refused");
            }
        };
        resilient = new ResilientCacheManager(primary, fallback);

        Collection<String> names = resilient.getCacheNames();
        assertThat(names).isNotNull();
    }

    @Test
    void differentCacheInstancesAreIndependent() {
        primary = new ConcurrentMapCacheManager();
        resilient = new ResilientCacheManager(primary, fallback);

        Cache cache1 = resilient.getCache("cache-a");
        Cache cache2 = resilient.getCache("cache-b");
        assertThat(cache1).isNotNull();
        assertThat(cache2).isNotNull();

        cache1.put("key", "value-a");
        assertThat(cache1.get("key").get()).isEqualTo("value-a");
        assertThat(cache2.get("key")).isNull();
    }
}
