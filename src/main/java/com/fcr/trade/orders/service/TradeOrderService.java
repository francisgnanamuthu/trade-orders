package com.fcr.trade.orders.service;

import com.fcr.trade.orders.model.GetOrderResponse;
import com.fcr.trade.orders.model.Order;
import com.fcr.trade.orders.model.OrderResponse;

public interface TradeOrderService {

    OrderResponse createOrder(Order order);

    GetOrderResponse getSummary();

    GetOrderResponse getOrderByFund(String fund);

    GetOrderResponse getOrderBySecurity(String security);

}
