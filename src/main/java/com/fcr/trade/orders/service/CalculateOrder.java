package com.fcr.trade.orders.service;

import com.fcr.trade.orders.model.OrderDto;
import com.fcr.trade.orders.model.OrderResponseDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CalculateOrder {

    public static final RoundingMode HALF_UP = RoundingMode.HALF_UP;

    public String calcaulateDuplicateOrder(OrderResponseDto responseDto, List<OrderDto> dtoList) {
        StringBuilder orderBuilder = new StringBuilder();
        Map<String, Long> duplicateCount = dtoList.stream().collect(Collectors.groupingBy(c -> getFormatedOrdrString(c), Collectors.counting()));
        duplicateCount.entrySet().stream().filter(entry -> entry.getValue() > 1).forEach(entry -> {
            orderBuilder.append(entry.getValue());
            orderBuilder.append("(");
            orderBuilder.append(entry.getKey());
            orderBuilder.append(")");
            orderBuilder.append(",");
        });
        if (orderBuilder.length() > 1) orderBuilder.deleteCharAt(orderBuilder.length() - 1);
        responseDto.setCombinableOrder(orderBuilder.toString());
        return orderBuilder.toString();

    }

    private String getFormatedOrdrString(OrderDto dto) {
        StringBuilder orderBuilder = new StringBuilder();
        orderBuilder.append(dto.getSide());
        orderBuilder.append("+");
        orderBuilder.append(dto.getSecurity());
        orderBuilder.append("+");
        orderBuilder.append(dto.getFundName());
        return orderBuilder.toString();
    }

    public void calculateTotalQuantity(OrderResponseDto responseDto, List<OrderDto> dtoList) {
        Long totalQuantity = dtoList.stream().mapToLong(dto -> dto.getQuantity()).sum();
        responseDto.setTotalQuantity(totalQuantity);
    }

    public void calculateTotalPrice(OrderResponseDto responseDto, List<OrderDto> priceList) {
        BigDecimal totalprice = priceList.stream().map(dto -> dto.getPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
        Long count = Long.valueOf(priceList.size());
        responseDto.setAveragePrice(totalprice.divide(BigDecimal.valueOf(count), HALF_UP));
        responseDto.setTotalNumberOfOrders(count);
    }

    public void calculatebySpecificSummary(List<OrderDto> dtoList, Map<String, OrderResponseDto> responseDtoMap) {
        for (Iterator<OrderDto> iterator = dtoList.iterator(); iterator.hasNext(); ) {
            OrderDto dto = iterator.next();
            String security = dto.getSecurity().trim();
            calculateByOrderKey(dtoList, responseDtoMap, dto, security);
            String fund = dto.getFundName().trim();
            calculateByOrderKey(dtoList, responseDtoMap, dto, fund);
        }
    }

    private void calculateByOrderKey(List<OrderDto> dtoList, Map<String, OrderResponseDto> responseDtoMap, OrderDto dto, String key) {
        if (responseDtoMap.containsKey(key)) {
            OrderResponseDto responseDto = responseDtoMap.get(key);
            responseDto = addExisitingOrderByKey(dto, responseDto);
            calcaulateDuplicateOrderbyKey(dtoList, key, responseDto);
            responseDtoMap.put(key, responseDto);
        } else {
            OrderResponseDto responseDto = addNewOrderByKey(dto, new OrderResponseDto());
            calcaulateDuplicateOrderbyKey(dtoList, key, responseDto);
            responseDtoMap.put(key, responseDto);
        }
    }

    private OrderResponseDto addNewOrderByKey(OrderDto dto, OrderResponseDto responseDto) {
        responseDto.setAveragePrice(dto.getPrice());
        responseDto.setTotalQuantity(dto.getQuantity());
        responseDto.setTotalNumberOfOrders(1L);
        return responseDto;
    }

    public String calcaulateDuplicateOrderbyKey(List<OrderDto> dtoList, String key, OrderResponseDto responseDto) {
        StringBuilder orderBuilder = new StringBuilder();
        Map<String, Long> duplicateCount = dtoList.stream().collect(Collectors.groupingBy(c -> getFormatedOrdrString(c), Collectors.counting()));
        duplicateCount.entrySet().stream().filter(entry -> (entry.getValue() > 1 && entry.getKey().contains(key))).forEach(entry -> {
            orderBuilder.append(entry.getValue());
            orderBuilder.append("(");
            orderBuilder.append(entry.getKey());
            orderBuilder.append(")");
            orderBuilder.append(",");
        });
        if (orderBuilder.length() > 1) orderBuilder.deleteCharAt(orderBuilder.length() - 1);
        responseDto.setCombinableOrder(orderBuilder.toString());
        return orderBuilder.toString();

    }


    private OrderResponseDto addExisitingOrderByKey(OrderDto dto, OrderResponseDto responseDto) {
        BigDecimal totalprice = responseDto.getAveragePrice().add(dto.getPrice());
        BigDecimal totalNumberOfOrder = BigDecimal.valueOf(responseDto.getTotalNumberOfOrders() + 1);
        Long totalQuantity = responseDto.getTotalQuantity() + dto.getQuantity();
        responseDto.setAveragePrice(totalprice.divide(totalNumberOfOrder, HALF_UP));
        responseDto.setTotalQuantity(totalQuantity);
        responseDto.setTotalNumberOfOrders(totalNumberOfOrder.longValue());
        return responseDto;
    }
}
