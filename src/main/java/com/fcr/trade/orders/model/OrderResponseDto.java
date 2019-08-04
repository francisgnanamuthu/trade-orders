package com.fcr.trade.orders.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class OrderResponseDto {
    private String type;
    private Integer totalNumberOfOrders;
    private Long totalQuantity;
    private BigDecimal averagePrice;
    private String combinableOrder;
}
