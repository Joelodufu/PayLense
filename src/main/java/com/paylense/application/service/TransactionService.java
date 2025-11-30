package com.paylense.application.service;

import com.paylense.application.dto.TransferRequest;
import com.paylense.domain.entity.*;
import com.paylense.infrastructure.repository.TransactionRepository;
import com.paylense.infrastructure.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transaction transferFunds(User sender, TransferRequest request) {
        // 1. Validate Amount
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be positive");
        }

        // 2. Fetch Wallets
        Wallet senderWallet = walletRepository.findByUserId(sender.getId())
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        Wallet receiverWallet = walletRepository.findByWalletNumber(request.getReceiverPhoneNumber())
                .orElseThrow(() -> new RuntimeException("Receiver wallet not found"));

        if (senderWallet.getId().equals(receiverWallet.getId())) {
            throw new RuntimeException("Cannot transfer to self");
        }

        // 3. Check Balance
        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // 4. Perform Transfer (Optimistic Locking will handle concurrency here)
        senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmount()));
        receiverWallet.setBalance(receiverWallet.getBalance().add(request.getAmount()));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        // 5. Record Transaction
        Transaction transaction = new Transaction();
        transaction.setSenderWallet(senderWallet);
        transaction.setReceiverWallet(receiverWallet);
        transaction.setAmount(request.getAmount());
        transaction.setCurrency("NGN");
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setReference(generateReference());
        transaction.setDescription(request.getDescription());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(User user) {
        Wallet userWallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        return transactionRepository.findBySenderWalletOrReceiverWallet(userWallet, userWallet);
    }

    private String generateReference() {
        return "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}
