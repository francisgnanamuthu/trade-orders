package com.fcr.trade.orders.repository;

import com.fcr.trade.orders.model.OrderDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository {

    void saveOrder(OrderDto dto);

    List<OrderDto> orderSummaryLst();
}
