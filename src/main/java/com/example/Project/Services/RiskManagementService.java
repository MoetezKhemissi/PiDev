package com.example.Project.Services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class RiskManagementService implements IRiskManagementService{
    @Autowired
    AlphaVantageService alphaVantageService;
    public List<Double> runMonteCarloSimulation(double[] historicalPrices, int numberOfSimulations) {
        List<Double> simulatedPrices = new ArrayList<>();

        // Calculate daily returns
        if (historicalPrices.length < 2) {
            throw new IllegalArgumentException("Insufficient historical data to calculate daily returns");
        }
        double[] dailyReturns = new double[historicalPrices.length - 1];

        for (int i = 1; i < historicalPrices.length; i++) {
            dailyReturns[i - 1] = (historicalPrices[i] - historicalPrices[i - 1]) / historicalPrices[i - 1];
        }

        // Calculate mean and standard deviation of returns
        double meanReturn = calculateMean(dailyReturns);
        double stdDevReturn = calculateStandardDeviation(dailyReturns, meanReturn);

        Random random = new Random();

        // Simulate future prices
        for (int i = 0; i < numberOfSimulations; i++) {
            double lastPrice = historicalPrices[historicalPrices.length - 1];
            for (int j = 0; j < dailyReturns.length; j++) {
                lastPrice *= 1 + (meanReturn + stdDevReturn * random.nextGaussian());
            }
            simulatedPrices.add(lastPrice);
        }

        return simulatedPrices;
    }


    private double calculateMean(double[] returns) {
        double sum = 0;
        for (double r : returns) {
            sum += r;
        }
        return sum / returns.length;
    }

    private double calculateStandardDeviation(double[] returns, double mean) {
        double sumSquaredDiff = 0;
        for (double r : returns) {
            sumSquaredDiff += Math.pow(r - mean, 2);
        }
        return Math.sqrt(sumSquaredDiff / (returns.length - 1));
    }
    public List<Double> runRiskManagementAnalysis(String symbol, String interval, int numberOfSimulations) {
        String historicalData = alphaVantageService.getHistoricalData(symbol, interval);
        System.out.println(historicalData);

        // Parse historical data and extract prices
        double[] historicalPrices = parseHistoricalData(historicalData);
        // Run Monte Carlo simulation
        return runMonteCarloSimulation(historicalPrices, numberOfSimulations);
    }

    // Existing Monte Carlo methods...

    private double[] parseHistoricalData(String historicalData) {
        List<Double> prices = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(historicalData);

            // Assuming the data is in "Time Series (15min)" format
            JsonNode timeSeriesNode = rootNode.get("Time Series (15min)");

            // Loop through the time series and extract prices
            if (timeSeriesNode != null) {
                for (Iterator<Map.Entry<String, JsonNode>> it = timeSeriesNode.fields(); it.hasNext(); ) {
                    Map.Entry<String, JsonNode> entry = it.next();
                    JsonNode priceNode = entry.getValue().get("1. open");
                    if (priceNode != null) {
                        double price = priceNode.asDouble();
                        prices.add(price);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Convert the list of prices to an array
        double[] historicalPrices = new double[prices.size()];
        for (int i = 0; i < prices.size(); i++) {
            historicalPrices[i] = prices.get(i);
        }

        return historicalPrices;
    }
}



