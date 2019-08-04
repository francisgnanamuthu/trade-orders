package com.fcr.trade.orders.service;

import com.fcr.trade.orders.cache.TradeOrderCache;
import com.fcr.trade.orders.mapper.OrderMapper;
import com.fcr.trade.orders.model.GetOrderResponse;
import com.fcr.trade.orders.model.Order;
import com.fcr.trade.orders.model.OrderDto;
import com.fcr.trade.orders.model.OrderResponse;
import com.fcr.trade.orders.model.OrderResponseDto;
import com.fcr.trade.orders.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TradeOrderServiceImpl implements TradeOrderService {
    private OrderRepository orderRepository;
    private TradeOrderCache orderCache;

    public TradeOrderServiceImpl(OrderRepository orderRepository, TradeOrderCache orderCache) {
        this.orderRepository = orderRepository;
        this.orderCache = orderCache;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    public OrderResponse createOrder(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(processOrder(order));
        return response;
    }

    @Override
    public GetOrderResponse getSummary() {
        OrderResponseDto responseDto = orderCache.retrieve("Summary");
        GetOrderResponse getOrderResponse = new GetOrderResponse();
        OrderMapper.mapGetOrderResponse(responseDto, getOrderResponse);
        return getOrderResponse;
    }

    @Override
    public GetOrderResponse getOrderByFund(String fund) {
        OrderResponseDto responseDto = orderCache.retrieve(fund);
        GetOrderResponse getOrderResponse = new GetOrderResponse();
        OrderMapper.mapGetOrderResponse(responseDto, getOrderResponse);
        return getOrderResponse;
    }

    @Override
    public GetOrderResponse getOrderBySecurity(String security) {
        OrderResponseDto responseDto = orderCache.retrieve(security);
        GetOrderResponse getOrderResponse = new GetOrderResponse();
        OrderMapper.mapGetOrderResponse(responseDto, getOrderResponse);
        return getOrderResponse;
    }

    private String processOrder(Order order) {
        OrderDto dto = new OrderDto();
        String uuid = generateId();
        OrderMapper.mapOrderToOrderDto(order, dto, uuid);
        this.orderRepository.saveOrder(dto);
        return uuid;
    }
}
