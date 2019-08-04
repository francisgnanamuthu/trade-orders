package com.fcr.trade.orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String orderId;
    private String side;
    private String security;
    private String fundName;
    private Long quantity;
    private BigDecimal price;
}
