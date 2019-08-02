package com.fcr.trade.orders.api;

import com.fcr.trade.orders.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TradeOrderAPI implements com.fcr.trade.orders.api.TradeApi {

    @Override
    public ResponseEntity<Void> createOrder(@Valid @RequestBody final List<Order> order) {
        return null;
    }
}
