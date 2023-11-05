package com.example.Project.services.impl;


import com.example.Project.transientEntities.Limit;
import com.example.Project.transientEntities.Order;
import com.example.Project.transientEntities.OrderBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    private final OrderBook orderBook;

    @Autowired
    public OrderService(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public void addOrder(Order order) {
        orderBook.addOrder(order);
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }
    public List<Limit> getAskLimits() {
        // Retrieve the ask limits from your OrderBook
        return new ArrayList<>(orderBook.getAskQueue());
    }

    public List<Limit> getBidLimits() {
        // Retrieve the bid limits from your OrderBook
        return new ArrayList<>(orderBook.getBidQueue());
    }
}