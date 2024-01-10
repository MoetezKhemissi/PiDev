package com.example.Project.dto;

import java.util.Date;

public class TransactionStatsDTO {
    private Long id;
    private Double lastPrice;
    private Date timestamp;
    private Long assetId;

    public TransactionStatsDTO(Long id, Double lastPrice, Date timestamp, Long assetId) {
        this.id = id;
        this.lastPrice = lastPrice;
        this.timestamp = timestamp;
        this.assetId = assetId;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Long getAssetId() {
        return assetId;
    }

    // Setters (if needed for your application logic)
    public void setId(Long id) {
        this.id = id;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }
}
