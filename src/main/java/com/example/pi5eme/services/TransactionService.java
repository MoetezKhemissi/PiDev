package com.example.pi5eme.services;

import com.example.pi5eme.repositories.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransactionService implements ITransactionService {
    @Autowired
    TransactionRepository transactionRepository;
@Override
    public Float sumByToAccountId(Integer toAccountId){
        return transactionRepository.sumByToAccountId(toAccountId);
    }
@Override
    public Float sumByFromAccountId(Integer toAccountId){
        return transactionRepository.sumByFromAccountId(toAccountId);
    }

}
