package com.example.pi5eme.services;

import com.example.pi5eme.Entities.Credit;
import com.example.pi5eme.Entities.CreditAnalysis;
import com.example.pi5eme.Entities.CreditPayment;
import com.example.pi5eme.repositories.AccountRepository;
import com.example.pi5eme.repositories.CreditAnalysisRepository;
import com.example.pi5eme.repositories.CreditPaymentRepository;
import com.example.pi5eme.repositories.CreditRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class CreditService implements ICreditService {
    @Autowired
    CreditRepository creditRepository;
    @Autowired
    AccountRepository  accountRepository;
    @Autowired
    CreditAnalysisRepository creditAnalysisRepository;
    @Autowired
    CreditPaymentRepository creditPaymentRepository;
  @Autowired
    CreditPaymentService creditPaymentService;


 @Override
    public Credit createCredit(Credit credit,Integer analyse_id){
     CreditAnalysis creditAnalysis=creditAnalysisRepository.findById(analyse_id).orElse(null);
    CreditPayment creditPayment=creditPaymentService.createCreditPayment(credit.getId_credit());
    // CreditPayment creditPayment=new CreditPayment();
     creditPayment.setCredit(credit);
     credit.setCreditPayment(creditPayment);
     creditPaymentRepository.save(creditPayment);

     credit.setCreditAnalysis(creditAnalysis);
        //Setting rest
     credit.setRest(credit.getAmount());
//Setting start date
     credit.setStart_d(new Date());
     // Setting end date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(credit.getStart_d());
        calendar.add(Calendar.MONTH, credit.getPeriod());
        credit.setEnd_d(calendar.getTime());

        //Setting final payment

        Float finale=credit.getAmount()*credit.getPeriod()*0.002f;
        credit.setFinal_payment(credit.getAmount()+finale);
     // Setting next pay
        calendar.setTime(credit.getStart_d());
        calendar.add(Calendar.MONTH,credit.getPay_every());
        credit.setNext_pay(calendar.getTime());
     //Setting Ratio
     credit.setRatio(1.0f);
     //Setting next bill
        credit.setNext_bill(credit.getFinal_payment()/(credit.getPeriod()/credit.getPay_every()));

    return creditRepository.save(credit);
    }



}
