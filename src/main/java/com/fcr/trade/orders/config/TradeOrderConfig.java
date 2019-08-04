package com.fcr.trade.orders.config;

import com.fcr.trade.orders.repository.SaveOrderInMemory;
import com.fcr.trade.orders.model.OrderDto;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class TradeOrderConfig {
    private SaveOrderInMemory saveOrderInMemory;

    public TradeOrderConfig(SaveOrderInMemory saveOrderInMemory) {
        this.saveOrderInMemory = saveOrderInMemory;
    }

    @PostConstruct
    public void init() {
        List<String> tradeLst = tradeOrderLst();
        tradeLst.forEach(order -> {
                    String[] orderSplit = StringUtils.commaDelimitedListToStringArray(order);
                    if (order != null && orderSplit.length == 6) {
                        saveOrderInMemory.saveOrder(new OrderDto(orderSplit[0], orderSplit[1], orderSplit[2], orderSplit[3], Long.parseLong(orderSplit[4]), new BigDecimal(orderSplit[5])));
                    }
                }
        );
    }

    @Bean(name = "trade.order.properties")
    @ConfigurationProperties(prefix = "trade.order")
    public List<String> tradeOrderLst() {
        return new ArrayList<String>();
    }
}
