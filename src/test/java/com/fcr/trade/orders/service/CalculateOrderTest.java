package com.fcr.trade.orders.service;

import com.fcr.trade.orders.cache.TradeOrderCache;
import com.fcr.trade.orders.model.OrderDto;
import com.fcr.trade.orders.model.OrderResponseDto;
import com.fcr.trade.orders.repository.OrderRepository;
import com.fcr.trade.orders.scheduler.OrderReadScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;


class CalculateOrderTest {
    List<OrderDto> orderDtoList;
    private OrderReadScheduler readGenerateScheduler;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private TradeOrderCache orderCache;
    private OrderResponseDto responseDto;

    @BeforeEach
    void setUp() {
        orderDtoList = new LinkedList<>();
        tradeOrderLst();
        readGenerateScheduler = new OrderReadScheduler(orderRepository, orderCache);
        responseDto = new OrderResponseDto();

    }

    @Test
    void calcaulateDuplicateOrder() {
        responseDto = new OrderResponseDto();
        readGenerateScheduler.calcaulateDuplicateOrder(responseDto, orderDtoList);
        assertThat(responseDto.getCombinableOrder()).isEqualTo("2(Buy+AAPL+MAG),2(Sell+T+F2)");
    }

    @Test
    void calculateTotalQuantity() {
        responseDto = new OrderResponseDto();
        readGenerateScheduler.calculateTotalQuantity(responseDto, orderDtoList);
        assertThat(responseDto.getTotalQuantity()).isEqualTo(21000L);
    }

    @Test
    public void calculateTotalNumberOfOrder() {
        readGenerateScheduler.calculateTotalPrice(responseDto, orderDtoList);
        assertThat(responseDto.getTotalNumberOfOrders()).isEqualTo(10);
    }

    @Test
    public void calculateAveragePrice() {
        readGenerateScheduler.calculateTotalPrice(responseDto, orderDtoList);
        assertThat(responseDto.getAveragePrice()).isEqualTo(BigDecimal.valueOf(183L));
    }

    @Test
    void calculatebySpecificSummary() {
        ConcurrentHashMap<String, OrderResponseDto> orderMap = new ConcurrentHashMap<>();
        readGenerateScheduler.calculatebySpecificSummary(orderDtoList, orderMap);
        assertThat(orderMap.size()).isEqualTo(12);
    }

    @Test
    void calcaulateDuplicateOrderbyKey() {
        responseDto = new OrderResponseDto();
        readGenerateScheduler.calcaulateDuplicateOrderbyKey(orderDtoList, "AAPL", responseDto);
        assertThat(responseDto.getCombinableOrder()).isEqualTo("2(Buy+AAPL+MAG)");
    }


    private List<OrderDto> tradeOrderLst() {
        List<String> ordrLst = new LinkedList<>();
        ordrLst.add("1,Buy,AAPL,MAG,10000,100");
        ordrLst.add("2,Buy,GOOG,CONT,1000,700");
        ordrLst.add("3,Buy,VAN,FP1,1000,10");
        ordrLst.add("4,Buy,AAPL,MAG,2000,100");
        ordrLst.add("5,Sell,T,F2,1000,30");
        ordrLst.add("6,Buy,VZ,CANA,1000,50");
        ordrLst.add("7,Buy,GOOG,CANA,1000,700");
        ordrLst.add("8,Sell,VAN,FBSC,1000,10");
        ordrLst.add("9,Buy,AAPL,FBIO,2000,100");
        ordrLst.add("15,Sell,T,F2,1000,30");
        ordrLst.forEach(order -> {
                    String[] orderSplit = StringUtils.commaDelimitedListToStringArray(order);
                    if (order != null && orderSplit.length == 6) {
                        orderDtoList.add(new OrderDto(orderSplit[0], orderSplit[1], orderSplit[2], orderSplit[3], Long.parseLong(orderSplit[4]), new BigDecimal(orderSplit[5])));
                    }
                }
        );
        return orderDtoList;
    }
}