package com.example.pi5eme.services;

import com.example.pi5eme.Entities.Account;
import com.example.pi5eme.Entities.Credit;
import com.example.pi5eme.Entities.CreditAnalysis;
import com.example.pi5eme.Entities.CreditPayment;
import com.example.pi5eme.repositories.AccountRepository;
import com.example.pi5eme.repositories.CreditPaymentRepository;
import com.example.pi5eme.repositories.CreditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@AllArgsConstructor
@Service
public class CreditPaymentService implements ICreditPaymentService{
    @Autowired
    private CreditPaymentRepository creditPaymentRepository;
    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public CreditPayment createCreditPayment( Integer credit_id){
        CreditPayment creditPayment=new CreditPayment();
        Credit credit=creditRepository.findById(credit_id).orElse(null);
        creditPayment.setCredit(credit);

        creditPayment.setAddition(0f);
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(credit.getStart_d());
        calendar.add(Calendar.MONTH, credit.getPay_every());
        creditPayment.setStart_d(calendar.getTime());
        calendar.add(Calendar.MONTH, credit.getPay_every());
        creditPayment.setEnd_d(calendar.getTime());
        creditPayment.setAmount(credit.getNext_bill());
        creditPayment.setCheckDate(creditPayment.getStart_d());
        return creditPaymentRepository.save(creditPayment);
    }

    @Override
  //  @Scheduled(cron = "0 0 10 * * ?")
    public CreditPayment fixingdate(Integer creditPayment_id){
        CreditPayment creditPayment=creditPaymentRepository.findById(creditPayment_id).orElse(null);
        Credit credit=creditPayment.getCredit();
        Date date= new Date();
        if (date.after(creditPayment.getEnd_d())){
            creditPayment.setStart_d(creditPayment.getEnd_d());
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(creditPayment.getStart_d());
            calendar.add(Calendar.MONTH, credit.getPay_every());
            creditPayment.setEnd_d(calendar.getTime());
        }

        
        return creditPaymentRepository.save(creditPayment);
    }
     @Override
    public CreditPayment delayPayment(Integer creditPayment_id){
        CreditPayment creditPayment=creditPaymentRepository.findById(creditPayment_id).orElse(null);
        Credit credit=creditPayment.getCredit();
        Date date= new Date();
        LocalDate checkDate = creditPayment.getCheckDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long daysBetween = ChronoUnit.DAYS.between(checkDate, currentDate);
        if (daysBetween==7){
            creditPayment.setAddition((creditPayment.getAddition()+creditPayment.getAmount()*0.1f));
            creditPayment.setCheckDate(date);
            if (credit.getRatio()<=0.5f){
                credit.setRatio(0f);
            }else{
            credit.setRatio(credit.getRatio()-0.15f);}
        }

        return creditPaymentRepository.save(creditPayment);
    }

    @Override
    public CreditPayment payment(Integer creditPayment_id){
       CreditPayment creditPayment=creditPaymentRepository.findById(creditPayment_id).orElse(null);
       Credit credit=creditPayment.getCredit();
        CreditAnalysis creditAnalysis=credit.getCreditAnalysis();
        Account account=creditAnalysis.getAccount();
        if (account.getBalance()>(creditPayment.getAmount()+creditPayment.getAddition())){
        account.setBalance(account.getBalance()-creditPayment.getAmount()-creditPayment.getAddition());
        accountRepository.save(account);}
        else {
            log.error("Not enough balance for account ID: " + account.getAccountId());
            throw new IllegalStateException("Not enough balance");

        }
       credit.setRest(credit.getRest()-creditPayment.getAmount()-creditPayment.getAddition());
       credit.setFinal_payment(credit.getFinal_payment()+ creditPayment.getAmount()+creditPayment.getAddition());
       creditPayment.setStart_d(creditPayment.getEnd_d());
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(creditPayment.getStart_d());
        calendar.add(Calendar.MONTH, credit.getPay_every());
        creditPayment.setEnd_d(calendar.getTime());
        creditPayment.setAmount(credit.getNext_bill());
        creditPayment.setAddition(0f);
        creditPaymentRepository.save(creditPayment);
       creditRepository.save(credit);
          return creditPayment;
    }


}
