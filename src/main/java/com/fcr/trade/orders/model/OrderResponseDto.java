package com.fcr.trade.orders.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderResponseDto {
    private Long totalNumberOfOrders;
    private Long totalQuantity;
    private BigDecimal averagePrice;
    private String combinableOrder;
}
