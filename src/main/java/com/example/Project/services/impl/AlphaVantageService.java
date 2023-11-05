package com.example.Project.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AlphaVantageService {
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://www.alphavantage.co";

    @Value("${api.key}")
    private String apiKey;

    public AlphaVantageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String buildUrl(String function, String symbol, String interval, String outputSize, String dataType) {
        return baseUrl + "/query?function=" + function +
                "&symbol=" + symbol +
                "&interval=" + interval +
                "&outputsize=" + outputSize +
                "&datatype=" + dataType +
                "&apikey=" + apiKey;
    }
    public String buildUrlSearch(String function, String keywords, String dataType) {
        return baseUrl + "/query?function=" + function +
                "&keywords=" + keywords +
                "&datatype=" + dataType +
                "&apikey=" + apiKey;
    }
    public String buildNewsSentimentUrl(String function, String tickers, String topics, String time_from, String time_to, String sort, Integer limit) {
        // Build URL with the provided parameters
        String url = baseUrl + "/query?function=" + function + "&apikey=" + apiKey;

        if (tickers != null) {
            url += "&tickers=" + tickers;
        }

        if (topics != null) {
            url += "&topics=" + topics;
        }

        if (time_from != null) {
            url += "&time_from=" + time_from;
        }

        if (time_to != null) {
            url += "&time_to=" + time_to;
        }

        if (sort != null) {
            url += "&sort=" + sort;
        }

        if (limit != null) {
            url += "&limit=" + limit;
        }

        return url;
    }

    public String fetchData(String url) {
        return restTemplate.getForObject(url, String.class);
    }
}
