package com.example.pi5eme.services;

import com.example.pi5eme.Entities.CreditPayment;

public interface ICreditPaymentService {
    CreditPayment createCreditPayment(Integer credit_id);

    CreditPayment fixingdate(Integer creditPayment_id);

    CreditPayment delayPayment(Integer creditPayment_id);

    CreditPayment payment(Integer creditPayment_id);
}
