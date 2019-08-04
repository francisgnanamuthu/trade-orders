package com.fcr.trade.orders.scheduler;

import com.fcr.trade.orders.cache.TradeOrderCache;
import com.fcr.trade.orders.model.OrderDto;
import com.fcr.trade.orders.model.OrderResponseDto;
import com.fcr.trade.orders.repository.OrderRepository;
import com.fcr.trade.orders.service.CalculateOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class OrderReadScheduler extends CalculateOrder {
    public static final String SUMMARY = "Summary";
    private OrderRepository orderRepository;
    private TradeOrderCache tradeOrderCache;

    public OrderReadScheduler(OrderRepository orderRepository, TradeOrderCache tradeOrderCache) {
        this.orderRepository = orderRepository;
        this.tradeOrderCache = tradeOrderCache;
    }

    @Scheduled(fixedRate = 60000)
    public void readGenerateScheduler() {
        log.info(">>>>>>>>>>>>>>>> Async Scheduler Start");
        List<OrderDto> orderDO = orderRepository.orderSummaryLst();
        ConcurrentHashMap<String, OrderResponseDto> orderMap = new ConcurrentHashMap<>();
        calculateOrderSummary(orderDO, orderMap);
        calculatebySpecificSummary(orderDO, orderMap);
        for (Map.Entry<String, OrderResponseDto> stringOrderResponseDtoEntry : orderMap.entrySet()) {
            tradeOrderCache.save(stringOrderResponseDtoEntry.getKey(), stringOrderResponseDtoEntry.getValue());
        }
        log.info("<<<<<<<<<<<<<<<< Asyc Scheduler End");
    }

    /***
     * Calculate Total Order Summary
     * @param dtoList
     * @param orderMap
     * @return OrderResponseDto order Summary
     */
    private OrderResponseDto calculateOrderSummary(List<OrderDto> dtoList, ConcurrentHashMap<String, OrderResponseDto> orderMap) {
        OrderResponseDto responseDto = new OrderResponseDto();
        calculateTotalPrice(responseDto, dtoList);
        calculateTotalQuantity(responseDto, dtoList);
        calcaulateDuplicateOrder(responseDto, dtoList);
        orderMap.put(SUMMARY, responseDto);
        return responseDto;
    }
}
