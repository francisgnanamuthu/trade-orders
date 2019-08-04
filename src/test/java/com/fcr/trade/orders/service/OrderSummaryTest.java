package com.fcr.trade.orders.service;

import com.fcr.trade.orders.model.OrderDto;
import com.fcr.trade.orders.repository.OrderRepository;
import com.fcr.trade.orders.repository.SaveOrderInMemory;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class OrderSummaryTest {
    private Table<String, String, String> orderRequest;
    private OrderRepository saveOrderInMemory;
    private OrderSummary orderSummary;

    @BeforeEach
    void setUp() {
        CreateOrderTable(tradeOrderLst());
        orderSummary = new OrderSummary(null);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void calculateOrderSummary() {
        orderSummary.calculateOrderSummary(orderRequest);
    }

    private List<String> tradeOrderLst() {
        List<String> ordrLst = new ArrayList<>();
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
        return ordrLst;
    }

    private Table<String, String, String> CreateOrderTable(List<String> tradeLst) {
        orderRequest = HashBasedTable.create();
        tradeLst.forEach(order -> {
                    String[] orderSplit = StringUtils.commaDelimitedListToStringArray(order);
                    if (order != null && orderSplit.length == 6) {
                        mapOrderTable(orderRequest, (new OrderDto(orderSplit[0], orderSplit[1], orderSplit[2], orderSplit[3], Long.parseLong(orderSplit[4]), new BigDecimal(orderSplit[5]))));
                    }
                }
        );
        return orderRequest;
    }

    private Table<String, String, String> mapOrderTable(Table<String, String, String> ordertable, OrderDto dto) {
        String orderId = dto.getOrderId();
        ordertable.put(orderId, SaveOrderInMemory.SIDE, dto.getSide());
        ordertable.put(orderId, SaveOrderInMemory.SECURITY, dto.getSecurity());
        ordertable.put(orderId, SaveOrderInMemory.FUND_NAME, dto.getFundName());
        ordertable.put(orderId, SaveOrderInMemory.QUANTITY, dto.getQuantity().toString());
        ordertable.put(orderId, SaveOrderInMemory.PRICE, dto.getPrice().toString());
        return ordertable;
    }
}