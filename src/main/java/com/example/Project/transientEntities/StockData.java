package com.example.Project.transientEntities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockData {
    @JsonProperty("Meta Data")
    private MetaData metaData;

    @JsonProperty("Time Series (1min)")
    private Map<String, TimeStampData> timeSeriesData;

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public Map<String, TimeStampData> getTimeSeriesData() {
        return timeSeriesData;
    }

    public void setTimeSeriesData(Map<String, TimeStampData> timeSeriesData) {
        this.timeSeriesData = timeSeriesData;
    }

    public StockData() {
        // Default constructor with no arguments
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetaData {
        @JsonProperty("2. Symbol")
        private String symbol;

        // Other properties

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        // Getters and setters for other properties
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TimeStampData {
        @JsonProperty("1. open")
        private String open;

        @JsonProperty("2. high")
        private String high;

        @JsonProperty("3. low")
        private String low;

        @JsonProperty("4. close")
        private String close;

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public TimeStampData() {
            // Default constructor with no arguments
        }
    }
}
