package com.paylense.service;

import com.paylense.domain.entity.Wallet;
import com.paylense.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Optional<Wallet> getWalletById(Long id) {
        return walletRepository.findById(id);
    }

    public Optional<Wallet> getWalletByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }
    
    public Optional<Wallet> getWalletByNumber(String walletNumber) {
        return walletRepository.findByWalletNumber(walletNumber);
    }
}