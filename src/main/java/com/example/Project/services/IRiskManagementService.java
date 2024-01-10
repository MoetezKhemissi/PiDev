package com.example.Project.Services;

import java.util.List;

public interface IRiskManagementService {
    public List<Double> runRiskManagementAnalysis(String symbol, String interval, int numberOfSimulations);
}
