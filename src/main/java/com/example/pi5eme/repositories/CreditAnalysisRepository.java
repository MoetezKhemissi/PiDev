package com.example.pi5eme.repositories;

import com.example.pi5eme.Entities.CreditAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditAnalysisRepository extends JpaRepository<CreditAnalysis,Integer> {
    CreditAnalysis findCreditAnalysisByAccount_AccountId(Integer account_id);
}
