package com.fcr.trade.orders.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;


public class TradeOrderCacheConfig {

    public CacheManager cacheManager(final Ticker ticker) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("orderInfo");
        cacheManager.setCaffeine(caffeineCacheBuilder(ticker));
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder(final Ticker ticker) {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500).ticker(ticker);

    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }

}
