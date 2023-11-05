package com.example.Project.transientEntities;

import java.util.concurrent.atomic.AtomicLong;

public class Order {
    private static final AtomicLong uniqueIdGenerator = new AtomicLong(0);
    private final long orderId;
    private int price;
    private int volume;
    private boolean isBuy; // True for bid False for ask


    public Order(int price, int volume, boolean isBuy) {
        this.orderId = uniqueIdGenerator.incrementAndGet();
        this.price = price;
        this.volume = volume;
        this.isBuy = isBuy;
    }
    public long getOrderId() {
        return orderId;
    }

    public int getPrice() {
        return price;
    }

    public int getVolume() {
        return volume;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void reduceVolume(int volume) {
        this.volume -= volume;
    }
}