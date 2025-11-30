package com.paylense.infrastructure.repository;

import com.paylense.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByWalletNumber(String walletNumber);
    Optional<Wallet> findByUserId(Long userId);
}