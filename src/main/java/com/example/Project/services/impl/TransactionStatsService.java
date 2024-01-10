package com.example.Project.services.impl;

import com.example.Project.dto.TransactionStatsDTO;
import com.example.Project.entities.TransactionStats;
import com.example.Project.repositories.TransactionRepository;
import com.example.Project.repositories.TransactionStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionStatsService {
    @Autowired
    private TransactionStatsRepository statsRepository;

    public Map<Long, Map<String, List<Map<String, Object>>>> calculateVolumeIntervals() {
        List<TransactionStats> allStats = statsRepository.findAll();
        Map<Long, List<TransactionStats>> statsByAsset = allStats.stream()
                .collect(Collectors.groupingBy(stats -> stats.getAsset().getAssetId()));

        Map<Long, Map<String, List<Map<String, Object>>>> assetIntervalsVolume = new HashMap<>();

        for (Map.Entry<Long, List<TransactionStats>> entry : statsByAsset.entrySet()) {
            Long assetId = entry.getKey();
            List<TransactionStats> statsList = entry.getValue();

            Map<String, List<Map<String, Object>>> intervalVolumes = new HashMap<>();
            intervalVolumes.put("volume15Sec", calculateIntervalVolumes(statsList, 15));
            intervalVolumes.put("volume30Sec", calculateIntervalVolumes(statsList, 30));
            intervalVolumes.put("volume1Min", calculateIntervalVolumes(statsList, 60));

            assetIntervalsVolume.put(assetId, intervalVolumes);
        }

        return assetIntervalsVolume;
    }

    private List<Map<String, Object>> calculateIntervalVolumes(List<TransactionStats> statsList, int secondsInterval) {
        List<Map<String, Object>> intervalVolumes = new ArrayList<>();
        if (statsList.isEmpty()) {
            return intervalVolumes;
        }

        Date firstTimestamp = statsList.get(0).getTimestamp();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstTimestamp);

        while (calendar.getTime().before(new Date())) {
            Date intervalStart = calendar.getTime();
            calendar.add(Calendar.SECOND, secondsInterval);
            Date intervalEnd = calendar.getTime();

            double volume = statsList.stream()
                    .filter(stats -> stats.getTimestamp().after(intervalStart) && stats.getTimestamp().before(intervalEnd))
                    .mapToDouble(TransactionStats::getLastPrice) // Assuming LastPrice represents volume
                    .sum();

            Map<String, Object> intervalData = new HashMap<>();
            intervalData.put("timestamp", intervalStart);
            intervalData.put("volume", volume);

            intervalVolumes.add(intervalData);
        }

        return intervalVolumes;
    }
    public List<TransactionStatsDTO> getAllTransactionStats() {
        return statsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TransactionStatsDTO convertToDTO(TransactionStats stats) {
        return new TransactionStatsDTO(
                stats.getId(),
                stats.getLastPrice(),
                stats.getTimestamp(),
                stats.getAsset().getAssetId());
    }
}
