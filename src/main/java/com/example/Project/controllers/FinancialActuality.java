package com.example.Project.controllers;


import com.example.Project.services.impl.AlphaVantageService;
import com.example.Project.transientEntities.StockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class FinancialActuality {

    private final AlphaVantageService alphaVantageService;

    @Autowired
    public FinancialActuality(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }
   @GetMapping("/api/stock/intraday")
   public Object getIntradayData(
           @RequestParam String symbol,
           @RequestParam String interval,
           @RequestParam String dataType) {
       String outputSize = "full";
       String function = "TIME_SERIES_INTRADAY";
       String url = alphaVantageService.buildUrl(function, symbol, interval, outputSize, dataType);

       //get the JSON response as a string from api request
       String jsonResponse = alphaVantageService.fetchData(url);

           // json to StockData object
           ObjectMapper objectMapper = new ObjectMapper();
           try {
               StockData stockData = objectMapper.readValue(jsonResponse, StockData.class);
               // CSV from the StockData object and save it to resources/csv
               String csvData = stockDataToCSV(stockData);
               saveStockDataToCSV(csvData,symbol);
               return csvData;
           } catch (Exception e) {
               e.printStackTrace();
               return "Error converting to CSV: " + e.getMessage();
           }
       }


    //method to convert StockData to CSV format
    private String stockDataToCSV(StockData stockData) {
        StringBuilder csv = new StringBuilder();

        // stock data headers
        csv.append("Symbol").append(",");
        csv.append("Open").append(",");
        csv.append("High").append(",");
        csv.append("Low").append(",");
        csv.append("Close").append("\n");

        // access MetaData
        StockData.MetaData metaData = stockData.getMetaData();
        if (metaData != null) {
            String symbol = metaData.getSymbol();
            if (symbol != null) {
                // access Time Series Data
                Map<String, StockData.TimeStampData> timeSeriesData = stockData.getTimeSeriesData();
                if (timeSeriesData != null) {
                    for (Map.Entry<String, StockData.TimeStampData> entry : timeSeriesData.entrySet()) {
                        StockData.TimeStampData timeStampData = entry.getValue();
                        if (timeStampData != null) {
                            // Append metadata property symbol
                            csv.append(symbol).append(",");
                            // Append TimeStampData properties
                            csv.append(timeStampData.getOpen()).append(",");
                            csv.append(timeStampData.getHigh()).append(",");
                            csv.append(timeStampData.getLow()).append(",");
                            csv.append(timeStampData.getClose()).append("\n");
                        }
                    }
                }
            }
        }

        return csv.toString();
    }


    private void saveStockDataToCSV(String csvData,String symbol) {

        String projectPath = "src/main/resources/csv/";

        //file name with the current date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(new Date());

        String fileName = "stock_data_" + currentDate + "_" +symbol +".csv";

        // project path and file name to create the file path
        String filePath = projectPath + fileName;

        // write the CSV content to the file
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(csvData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/api/stock/daily")
    public String getDailyData(
            @RequestParam String symbol,
            @RequestParam String outputSize,
            @RequestParam String dataType) {
        String function = "TIME_SERIES_DAILY";
        String url = alphaVantageService.buildUrl(function, symbol, null, outputSize, dataType);
        return alphaVantageService.fetchData(url);
    }

    @GetMapping("/api/stock/weekly")
    public String getWeeklyData(
            @RequestParam String symbol,
            @RequestParam String outputSize,
            @RequestParam String dataType) {
        String function = "TIME_SERIES_WEEKLY";
        String url = alphaVantageService.buildUrl(function, symbol, null, outputSize, dataType);
        return alphaVantageService.fetchData(url);
    }

    @GetMapping("/api/stock/monthly")
    public String getMonthlyData(
            @RequestParam String symbol,
            @RequestParam String outputSize,
            @RequestParam String dataType) {
        String function = "TIME_SERIES_MONTHLY";
        String url = alphaVantageService.buildUrl(function, symbol, null, outputSize, dataType);
        return alphaVantageService.fetchData(url);
    }

    @GetMapping("/api/stock/quote")
    public String getQuoteData(
            @RequestParam String symbol,
            @RequestParam String dataType) {
        String function = "GLOBAL_QUOTE";
        String interval = "1min";
        String outputSize = "compact";
        String url = alphaVantageService.buildUrl(function, symbol, interval, outputSize, dataType);
        return alphaVantageService.fetchData(url);
    }
    @GetMapping("/api/stock/symbolSearch")
    public String symbolSearch(
            @RequestParam String keywords,
            @RequestParam String dataType) {
        String function = "SYMBOL_SEARCH";
        String url = alphaVantageService.buildUrlSearch(function, keywords, dataType);
        return alphaVantageService.fetchData(url);
    }
    @GetMapping("/api/market/status")
    public String getMarketStatus() {
        String function = "MARKET_STATUS";
        String url = alphaVantageService.buildUrl(function, null, null, null, null);
        return alphaVantageService.fetchData(url);
    }
    @GetMapping("/api/news/sentiment")
    public String getNewsSentiment(
            @RequestParam String tickers,
            @RequestParam(required = false) String topics,
            @RequestParam(required = false) String time_from,
            @RequestParam(required = false) String time_to,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer limit) {
        String function = "NEWS_SENTIMENT";

        String url = alphaVantageService.buildNewsSentimentUrl(function, tickers, topics, time_from, time_to, sort, limit);

        return alphaVantageService.fetchData(url);
    }
}



