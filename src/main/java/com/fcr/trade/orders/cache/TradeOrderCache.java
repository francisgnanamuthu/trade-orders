package com.fcr.trade.orders.cache;

import com.fcr.trade.orders.model.OrderResponseDto;

public interface TradeOrderCache {

    void save();

    OrderResponseDto retrieve(String cacheKey);

    void invalidate(String cacheKey);
}
