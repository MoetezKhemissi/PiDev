package com.example.Project.controllers;

import com.example.Project.dto.TransactionStatsDTO;
import com.example.Project.entities.TransactionStats;
import com.example.Project.services.impl.TransactionStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class TransactionStatsController {

    @Autowired
    private TransactionStatsService transactionStatsService;

    @GetMapping("/volumes")
    public Map<Long, Map<String, List<Map<String, Object>>>> getVolumeIntervals() {
        return transactionStatsService.calculateVolumeIntervals();
    }
    @GetMapping
    public List<TransactionStatsDTO> getAllTransactionStats() {
        return transactionStatsService.getAllTransactionStats();
    }
}