package com.fcr.trade.orders.cache;

import com.fcr.trade.orders.model.OrderResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

import java.util.Map;

@EnableCaching
@Slf4j
@Component
public class TradeOrderCacheImpl implements TradeOrderCache {

    @Cacheable("orderInfo")
    public Map<String, OrderResponseDto> SummaryCache() {
        log.error("Map Not Exist");
        return null;
    }

    @Override
    @CachePut("orderInfo")
    public void save() {

    }

    @Override
    public OrderResponseDto retrieve(String cacheKey) {
        return null;
    }

    @Override
    public void invalidate(String cacheKey) {

    }
}
