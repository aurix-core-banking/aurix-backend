package com.aurix.platform.banking.core.resilience;

import com.aurix.platform.banking.BankingApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BankingApplication.class)
@ActiveProfiles("test")
class CacheResilienceTest {

    @Autowired
    private CacheManager cacheManager;

    @Test
    void cacheManagerIsConcurrentMapWhenRedisUnavailable() {
        assertThat(cacheManager).isInstanceOf(ConcurrentMapCacheManager.class);
    }

    @Test
    void cacheGetPutEvictWorksWithoutRedis() {
        Cache cache = cacheManager.getCache("test-cache");
        assertThat(cache).isNotNull();

        cache.put("key1", "value1");
        Cache.ValueWrapper result = cache.get("key1");
        assertThat(result).isNotNull();
        assertThat(result.get()).isEqualTo("value1");

        cache.evict("key1");
        assertThat(cache.get("key1")).isNull();
    }

    @Test
    void cacheNamesAreAccessible() {
        Collection<String> names = cacheManager.getCacheNames();
        assertThat(names).isNotNull();
    }
}
