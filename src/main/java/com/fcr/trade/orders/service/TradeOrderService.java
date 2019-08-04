package com.fcr.trade.orders.service;

import com.fcr.trade.orders.mapper.OrderMapper;
import com.fcr.trade.orders.model.Order;
import com.fcr.trade.orders.model.OrderDto;
import com.fcr.trade.orders.model.OrderResponse;
import com.fcr.trade.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TradeOrderService {
    private OrderRepository orderRepository;

    public TradeOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public OrderResponse createOrder(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(processOrder(order));
        return response;

    }

    private String processOrder(Order order) {
        OrderDto dto = new OrderDto();
        String uuid = generateId();
        OrderMapper.mapOrderToOrderDto(order, dto, uuid);
        this.orderRepository.saveOrder(dto);
        return uuid;
    }
}
