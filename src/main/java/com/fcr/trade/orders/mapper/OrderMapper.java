package com.fcr.trade.orders.mapper;

import com.fcr.trade.orders.model.GetOrderResponse;
import com.fcr.trade.orders.model.Order;
import com.fcr.trade.orders.model.OrderDto;
import com.fcr.trade.orders.model.OrderResponseDto;
import org.springframework.beans.BeanUtils;

public class OrderMapper {

    public static OrderDto mapOrderToOrderDto(final Order order, OrderDto dto, final String UUID) {
        BeanUtils.copyProperties(order, dto);
        dto.setOrderId(UUID);
        dto.setSide(order.getSide().toString());
        return dto;
    }

    public static GetOrderResponse mapGetOrderResponse(final OrderResponseDto responseDto, GetOrderResponse getOrderResponse) {
        BeanUtils.copyProperties(responseDto, getOrderResponse);
        return getOrderResponse;
    }
}
