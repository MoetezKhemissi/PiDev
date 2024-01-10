package com.example.Project.repositories;

import com.example.Project.entities.TransactionStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionStatsRepository extends JpaRepository<TransactionStats, Long> {
}