package com.fcr.trade.orders.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class OrderCacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("orderInfo");
        cacheManager.setAllowNullValues(false); //can happen if you get a value from a @Cachable that returns null
        cacheManager.setCaffeine(caffeineCacheBuilder());
        cacheManager.setCaffeineSpec(caffeineSpec());
        return cacheManager;
    }

    CaffeineSpec caffeineSpec() {
        return CaffeineSpec.parse
                ("initialCapacity=100,maximumSize=500,expireAfterAccess=5m,recordStats");
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(150)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .weakKeys()
                .recordStats();
    }

}
