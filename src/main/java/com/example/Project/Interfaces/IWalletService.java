package com.example.Project.Interfaces;

import com.example.Project.entities.Wallet;

import java.util.List;

public interface IWalletService {
    List<Wallet> retrieveAllWallets();
    Wallet addWallet(Wallet c,Long id);

    void deleteWallet(String id);

    Wallet updateWallet(Wallet c);

    Wallet retrieveWallet(String wallet_id);
    Wallet retrieveWalletFromUser(Long id);

    Wallet AddPaymentMethod(Wallet c,Long id);
}
