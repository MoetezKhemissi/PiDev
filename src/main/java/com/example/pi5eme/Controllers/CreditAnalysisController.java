package com.example.pi5eme.Controllers;

import com.example.pi5eme.Entities.CreditAnalysis;
import com.example.pi5eme.services.CreditAnalysisService;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/CreditAnalysis")
public class CreditAnalysisController {
    private CreditAnalysisService  creditAnalysisService;


    @PostMapping("/add/{account_id}")
    public CreditAnalysis createCreditAnalysis(@PathVariable("account_id") Integer account_id) {
        CreditAnalysis creditAnalysis = new CreditAnalysis();
        return creditAnalysisService.createCreditAnalysis(creditAnalysis, account_id);
    }

    @PutMapping("/update/{id}")
    CreditAnalysis updateCreditAnalysis(@PathVariable("id") Integer creditAnalysis_id){
        return creditAnalysisService.updateCreditAnalysis(creditAnalysis_id);
    }
    @GetMapping("/score/{id}")
    Float CalculateScoreMV(@PathVariable("id") Integer account_id){
        return creditAnalysisService.CalculateScoreMV(account_id);
    }
    @GetMapping("/All")
    List<CreditAnalysis> findAllCreditAnalysis(){
        return creditAnalysisService.findAllCreditAnalysis();
    }
    @GetMapping("/byaccount/{id}")
    CreditAnalysis findbyaccount(@PathVariable("id") Integer account_id){
        return creditAnalysisService.findbyaccount(account_id);
    }

}
