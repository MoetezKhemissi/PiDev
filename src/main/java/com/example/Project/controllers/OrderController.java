package com.example.Project.controllers;


import com.example.Project.services.impl.OrderService;
import com.example.Project.transientEntities.Limit;
import com.example.Project.transientEntities.Order;
import com.example.Project.services.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }

    @GetMapping("/ask")
    public Queue<Limit> getAskQueue() {
        return orderService.getOrderBook().getAskQueue();
    }

    @GetMapping("/bid")
    public Queue<Limit> getBidQueue() {
        return orderService.getOrderBook().getBidQueue();
    }

    @GetMapping("/order-book/chart")
    public String orderBookChart(Model model) {
        List<Limit> askLimits = orderService.getAskLimits();
        List<Limit> bidLimits = orderService.getBidLimits();

        List<Integer> askPrices = askLimits.stream().map(Limit::getPrice).collect(Collectors.toList());
        List<Integer> bidPrices = bidLimits.stream().map(Limit::getPrice).collect(Collectors.toList());

        model.addAttribute("askPrices", askPrices);
        model.addAttribute("bidPrices", bidPrices);

        return "order-book-chart";

    }}