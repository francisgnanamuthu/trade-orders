package com.fcr.trade.orders.api;

import com.fcr.trade.orders.model.GetOrderResponse;
import com.fcr.trade.orders.model.Order;
import com.fcr.trade.orders.model.OrderResponse;
import com.fcr.trade.orders.service.TradeOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class TradeOrderAPI implements com.fcr.trade.orders.api.TradeApi {
    private TradeOrderService orderService;

    public TradeOrderAPI(TradeOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody final Order order) {
        OrderResponse response = this.orderService.createOrder(order);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GetOrderResponse> getOrderByFund(@PathVariable String fund) {
        return null;
    }

    @Override
    public ResponseEntity<GetOrderResponse> getOrderBySecurity(@PathVariable String security) {
        return null;
    }

    @Override
    public ResponseEntity<GetOrderResponse> getSummary() {
        return null;
    }
}
