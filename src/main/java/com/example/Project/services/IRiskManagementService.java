package com.example.Project.services;

import java.util.List;

public interface IRiskManagementService {
    public List<Double> runRiskManagementAnalysis(String symbol, String interval, int numberOfSimulations);
}
