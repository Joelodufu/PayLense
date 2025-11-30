package com.paylense.application.service;

import com.paylense.domain.entity.User;
import com.paylense.domain.entity.Wallet;
import com.paylense.infrastructure.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Wallet createWallet(User user) {
        if (walletRepository.findByUserId(user.getId()).isPresent()) {
            throw new RuntimeException("User already has a wallet");
        }

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        // Use phone number as wallet number
        wallet.setWalletNumber(user.getPhoneNumber());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency("NGN"); // Default currency
        
        return walletRepository.save(wallet);
    }
    
    public Wallet getWalletByUser(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user id: " + userId));
    }
}