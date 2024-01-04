package com.example.pi5eme.services;

public interface ITransactionService {
    Float sumByToAccountId(Integer toAccountId);

    Float sumByFromAccountId(Integer toAccountId);
}
