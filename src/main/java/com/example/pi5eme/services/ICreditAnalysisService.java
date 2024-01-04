package com.example.pi5eme.services;

import com.example.pi5eme.Entities.CreditAnalysis;

import java.util.List;

public interface ICreditAnalysisService {


    CreditAnalysis createsimple(CreditAnalysis creditAnalysis);

    CreditAnalysis createCreditAnalysis(CreditAnalysis creditAnalysis, Integer account_id);

    CreditAnalysis updateCreditAnalysis(Integer creditAnalysis_id);

    Float CalculateScoreMV(Integer account_id);

    Float CalculateRatio(CreditAnalysis creditAnalysis);



    List<CreditAnalysis> findAllCreditAnalysis();

    CreditAnalysis findbyaccount(Integer account_id);
}
