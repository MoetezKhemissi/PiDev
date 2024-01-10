

package com.example.Project.services.impl;

import com.example.Project.entities.Account;
import com.example.Project.entities.Asset;
import com.example.Project.entities.Transaction;
import com.example.Project.entities.TransactionStats;
import com.example.Project.enums.TypeTransaction;
import com.example.Project.repositories.TransactionRepository;
import com.example.Project.repositories.TransactionStatsRepository;
import com.example.Project.services.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class KafkaSubscriber {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private AccountAssetService accountAssetService;

    @Autowired
    private OrderService orderService;
    private static final Logger logger = LoggerFactory.getLogger(Logger.class);

    public static final Helper parserHerlper = new Helper();
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionStatsRepository statsRepository;

    @KafkaListener(topics = "settled-order")
    public void logSettledOrders(String message) throws Exception {

        logger.info("Received Transaction : " + message);
      Map<String,String> transactionInfo = new HashMap<>();
        transactionInfo = Helper.extractOrderInfo(message);
        Transaction transaction= new Transaction();
        System.out.println(transactionInfo.values());
         transaction.setAccount(accountService.getById(Long.parseLong(transactionInfo.get("userId"))));
        transaction.setTransactionDate(Calendar.getInstance().getTime());
        Float Amount = Float.parseFloat(transactionInfo.get("price")) *Float.parseFloat(transactionInfo.get("volume"));
        transaction.setTransactionAmount(Amount);
        TypeTransaction typeTransaction =
                transactionInfo.get("transactionType").equals("bid") ? TypeTransaction.Buy :
                        transactionInfo.get("transactionType").equals("ask") ? TypeTransaction.Sell :
                                null;


        transaction.setTypeTransaction(typeTransaction);
        Long assetId=Long.parseLong(transactionInfo.get("assetId"));
        Long accountId = Long.parseLong(transactionInfo.get("userId"));
        Double volume = Double.valueOf(transactionInfo.get("volume"));
        Asset requiredAsset = assetService.getAssetById(assetId);
        transaction.setAsset(requiredAsset);

        Account account=accountService.getById(accountId);
        if (typeTransaction==TypeTransaction.Buy){
            TransactionStats stats = new TransactionStats();
            accountAssetService.updateAccountAsset(account,assetId,volume);
            stats.setAsset(requiredAsset);
            stats.setLastPrice(Double. parseDouble(transactionInfo.get("price")));
            stats.setTimestamp(new Date());
            statsRepository.save(stats);
        }
        else if(typeTransaction==TypeTransaction.Sell) {

            account.setBalance(account.getBalance() + Amount);
            accountService.saveAccount(account);
        }

        transactionRepository.save(transaction);

    }


}