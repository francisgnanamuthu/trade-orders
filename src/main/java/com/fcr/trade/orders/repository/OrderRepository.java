package com.fcr.trade.orders.repository;

import com.fcr.trade.orders.model.OrderDto;
import com.google.common.collect.Table;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {

    void saveOrder(OrderDto dto);

    Table<String, String, String> orderSummary();
}
