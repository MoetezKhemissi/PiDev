package com.example.pi5eme.services;

import com.example.pi5eme.Entities.Account;
import com.example.pi5eme.Entities.Credit;
import com.example.pi5eme.Entities.CreditAnalysis;
import com.example.pi5eme.repositories.AccountRepository;
import com.example.pi5eme.repositories.CreditAnalysisRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class CreditAnalysisService implements ICreditAnalysisService {
    @Autowired
    CreditAnalysisRepository  creditAnalysisRepository;
    @Autowired
    AccountRepository accountRepository;
@Autowired
    TransactionService transactionService;

@Override
public CreditAnalysis createsimple(CreditAnalysis creditAnalysis){
    return creditAnalysisRepository.save(creditAnalysis);
}
@Override
    public CreditAnalysis createCreditAnalysis(CreditAnalysis creditAnalysis, Integer account_id){
   Account account=accountRepository.findById(account_id).orElseThrow(() -> new EntityNotFoundException("Account not found with id: " + account_id));
   CreditAnalysis cr=creditAnalysisRepository.findCreditAnalysisByAccount_AccountId(account_id);
   if (cr==null){
       creditAnalysis.setTotalCreditLimit(0f);
       creditAnalysis.setMvScore(0f);
       creditAnalysis.setDebtToIncomeRatio(0f);
       creditAnalysis.setInterestRate(0.0);
   creditAnalysis.setAccount(account);
    account.setCreditAnalysis(creditAnalysis);
       creditAnalysisRepository.save(creditAnalysis);
    accountRepository.save(account); }
      return  creditAnalysis ;
    }
    @Override
    public CreditAnalysis updateCreditAnalysis(Integer creditAnalysis_id){
       CreditAnalysis creditAnalysis=creditAnalysisRepository.findById(creditAnalysis_id).orElse(null);
      Float score_mv =CalculateScoreMV(creditAnalysis.getAccount().getAccountId());
       creditAnalysis.setMvScore(score_mv);
       creditAnalysis.setDebtToIncomeRatio(CalculateRatio(creditAnalysis));
       creditAnalysis.setTotalCreditLimit(500000f*creditAnalysis.getMvScore()*creditAnalysis.getDebtToIncomeRatio());

    return  creditAnalysisRepository.save(creditAnalysis);
    }
    @Override
    public Float CalculateScoreMV(Integer account_id){
    Account account= accountRepository.findById(account_id).orElse(null);

        Float sc_bal=0.0f;
        Float sc_income=0.0f;
        Float sc_outcome=0.0f;
    if (account.getBalance()>=100000){sc_bal=0.4f;
    } else { sc_bal=account.getBalance()*0.000004f;}

    if(transactionService.sumByToAccountId(account_id)!=null){
    if (transactionService.sumByToAccountId(account_id)>1000000){ sc_income=0.35f;}else {
        sc_income=(transactionService.sumByToAccountId(account_id) / 1000000) *0.35f;

    } }
 if(transactionService.sumByFromAccountId(account_id)!=null){
        if (transactionService.sumByFromAccountId(account_id)>500000){ sc_outcome=0.25f;}else {
            sc_outcome=(transactionService.sumByFromAccountId(account_id) / 500000) *0.25f;

        } }
        Float Scoremv= sc_bal+sc_income+sc_outcome;


       return Scoremv;

    }
    @Override
    public Float CalculateRatio(CreditAnalysis creditAnalysis){
        Set<Credit> credits = creditAnalysis.getCredits();
        if (credits.isEmpty()){
            return 1f;
        }
        Float ratio=0f;
        Integer nb=credits.size();
        for (Credit credit : credits){
             ratio+=credit.getRatio();
        }
    return ratio/nb;
    }
    @Override
    public List<CreditAnalysis> findAllCreditAnalysis(){
    return creditAnalysisRepository.findAll();
    }
    @Override
    public  CreditAnalysis findbyaccount(Integer account_id){
    return creditAnalysisRepository.findCreditAnalysisByAccount_AccountId(account_id);
    }
}
