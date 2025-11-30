package com.paylense.application.service;

import com.paylense.domain.entity.User;
import com.paylense.domain.entity.Wallet;
import com.paylense.infrastructure.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

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
        wallet.setWalletNumber(generateWalletNumber());
        wallet.setBalance(BigDecimal.ZERO);
        wallet.setCurrency("USD"); // Default currency
        
        return walletRepository.save(wallet);
    }

    private String generateWalletNumber() {
        // Simple generation strategy for now
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
    
    public Wallet getWalletByUser(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found for user id: " + userId));
    }
}