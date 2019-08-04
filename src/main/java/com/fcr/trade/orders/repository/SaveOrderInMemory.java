package com.fcr.trade.orders.repository;

import com.fcr.trade.orders.model.OrderDto;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class SaveOrderInMemory implements OrderRepository {

    public static final String SIDE = "side";
    public static final String SECURITY = "security ";
    public static final String FUND_NAME = "fund name";
    public static final String QUANTITY = "quantity";
    public static final String PRICE = "price";
    private static List<OrderDto> dtoLinkedList;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void saveOrder(OrderDto dto) {
        lock.writeLock().lock();
        try {
            if (null == dtoLinkedList) {
                dtoLinkedList = new LinkedList<>();
            }
            dtoLinkedList.add(dto);
        } finally {
            lock.writeLock().unlock();
        }
    }

   @Override
    public List<OrderDto> orderSummaryLst() {
        lock.readLock().lock();
        try {
            return dtoLinkedList;
        } finally {
            lock.readLock().unlock();
        }
    }
}
