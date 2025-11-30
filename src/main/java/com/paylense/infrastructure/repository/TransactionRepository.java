package com.paylense.infrastructure.repository;

import com.paylense.domain.entity.Transaction;
import com.paylense.domain.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderWalletOrReceiverWallet(Wallet senderWallet, Wallet receiverWallet);
    List<Transaction> findBySenderWallet(Wallet senderWallet);
    List<Transaction> findByReceiverWallet(Wallet receiverWallet);
}