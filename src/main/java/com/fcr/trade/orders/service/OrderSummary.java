package com.fcr.trade.orders.service;

import com.fcr.trade.orders.model.OrderResponseDto;
import com.fcr.trade.orders.repository.OrderRepository;
import com.fcr.trade.orders.repository.SaveOrderInMemory;
import com.google.common.collect.Table;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderSummary {
    public static final String SUMMARY = "Summary";
    private OrderRepository orderRepository;

    public OrderSummary(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private void getOrderSummary() {
        Table<String, String, String> orderDO = orderRepository.orderSummary();
        calculateOrderSummary(orderDO, new ConcurrentHashMap<>());
    }

    public void calculateOrderSummary(Table<String, String, String> orderDO, ConcurrentHashMap<String, OrderResponseDto> orderMap) {
        OrderResponseDto responseDto = new OrderResponseDto();
        Map<String, String> priceMap = orderDO.column(SaveOrderInMemory.PRICE);
        CalculateTotalPrice(responseDto, priceMap);
        CalculateTotalQuantity(responseDto, orderDO);
        orderMap.put(SUMMARY, responseDto);
    }

    private void CalculateTotalQuantity(OrderResponseDto responseDto, Table<String, String, String> orderDO) {
        Map<String, String> quantityMap = orderDO.column(SaveOrderInMemory.QUANTITY);
        Long totalQuantity = quantityMap.entrySet().stream().mapToLong(entry -> Long.valueOf(entry.getValue())).sum();
        responseDto.setTotalQuantity(totalQuantity);
    }

    private void CalculateTotalPrice(OrderResponseDto responseDto, Map<String, String> priceMap) {
        BigDecimal totalprice = priceMap.entrySet().stream().map(entry -> new BigDecimal(entry.getValue())).reduce(BigDecimal.ZERO, BigDecimal::add);
        Integer count = priceMap.entrySet().size();
        responseDto.setAveragePrice(totalprice.divide(BigDecimal.valueOf(count)));
        responseDto.setTotalNumberOfOrders(count);
    }

}
