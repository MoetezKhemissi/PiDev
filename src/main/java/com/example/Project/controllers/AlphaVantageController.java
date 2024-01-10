package com.example.Project.controllers;

import com.example.Project.Services.AlphaVantageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AlphaVantageController {
    @Autowired
    AlphaVantageService alphaVantageService;
    @GetMapping("/historical-data/{symbol}")
    public String getHistoricalData(
            @PathVariable String symbol,
            @RequestParam String interval
    ) {
        return alphaVantageService.getHistoricalData(symbol, interval);
    }
    @GetMapping("/symbols")
    public List<String> getAllSymbols() {
        return alphaVantageService.getAllSymbols();
    }
}
