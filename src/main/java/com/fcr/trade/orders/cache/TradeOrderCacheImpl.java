package com.fcr.trade.orders.cache;

import com.fcr.trade.orders.model.OrderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TradeOrderCacheImpl implements TradeOrderCache {

    private org.springframework.cache.CacheManager manager;

    public TradeOrderCacheImpl(CacheManager manager) {
        this.manager = manager;
    }

    public void save(String cacheKey, OrderResponseDto orderResponseDto) {
        manager.getCache("orderInfo").put(cacheKey, orderResponseDto);
    }

    @Override
    public OrderResponseDto retrieve(String cacheKey) {
        Cache.ValueWrapper obj = manager.getCache("orderInfo").get(cacheKey);
        if (obj == null) {
            return null;
        }
        return (OrderResponseDto) obj.get();
    }

    @Override
    public void invalidate(String cacheKey) {

    }

}
