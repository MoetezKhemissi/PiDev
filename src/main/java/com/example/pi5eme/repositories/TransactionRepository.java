package com.example.pi5eme.repositories;

import com.example.pi5eme.Entities.Transaction;
import com.example.pi5eme.Entities.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

  

    @Query("SELECT SUM(t.transactionAmount) FROM Transaction t WHERE t.toAccount.accountId = :toAccountId")
    Float sumByToAccountId(Integer toAccountId);

    @Query("SELECT SUM(t.transactionAmount) FROM Transaction t WHERE t.fromAccount.accountId = :toAccountId")
    Float sumByFromAccountId(Integer toAccountId);
}
