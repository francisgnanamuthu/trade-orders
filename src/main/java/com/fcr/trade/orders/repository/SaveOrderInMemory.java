package com.fcr.trade.orders.repository;

import com.fcr.trade.orders.model.OrderDto;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class SaveOrderInMemory implements OrderRepository {

    public static final String SIDE = "side";
    public static final String SECURITY = "security ";
    public static final String FUND_NAME = "fund name";
    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    private static Table<String, String, String> orderRequest;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void saveOrder(OrderDto dto) {
        lock.writeLock().lock();
        try {
            if (null == orderRequest) {
                orderRequest = HashBasedTable.create();
            }
            mapOrderTable(orderRequest, dto);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Table<String, String, String> orderSummary() {
        lock.readLock().lock();
        try {
            return orderRequest;
        } finally {
            lock.readLock().unlock();
        }
    }

    private Table<String, String, String> mapOrderTable(Table<String, String, String> ordertable, OrderDto dto) {
        String orderId = dto.getOrderId();
        ordertable.put(orderId, SIDE, dto.getSide());
        ordertable.put(orderId, SECURITY, dto.getSecurity());
        ordertable.put(orderId, FUND_NAME, dto.getFundName());
        ordertable.put(orderId, QUANTITY, dto.getQuantity().toString());
        ordertable.put(orderId, PRICE, dto.getPrice().toString());
        return ordertable;
    }
}
