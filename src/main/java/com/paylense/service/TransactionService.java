package com.paylense.service;

import com.paylense.domain.Transaction;
import com.paylense.domain.Transaction.TransactionStatus;
import com.paylense.domain.Transaction.TransactionType;
import com.paylense.domain.Wallet;
import com.paylense.dto.TransferRequest;
import com.paylense.dto.TransactionResponse;
import com.paylense.repository.TransactionRepository;
import com.paylense.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;

    public TransactionService(TransactionRepository transactionRepository, WalletRepository walletRepository) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public TransactionResponse transferFunds(String senderEmail, TransferRequest request) {
        // 1. Validate Sender Wallet
        Wallet senderWallet = walletRepository.findByUserEmail(senderEmail)
                .orElseThrow(() -> new RuntimeException("Sender wallet not found"));

        // 2. Validate Recipient Wallet
        Wallet recipientWallet = walletRepository.findByWalletNumber(request.getRecipientWalletNumber())
                .orElseThrow(() -> new RuntimeException("Recipient wallet not found"));

        if (senderWallet.getId().equals(recipientWallet.getId())) {
            throw new RuntimeException("Cannot transfer to self");
        }

        // 3. Check Balance
        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        // 4. Perform Atomic Update (Optimistic Locking handles concurrency)
        senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmount()));
        recipientWallet.setBalance(recipientWallet.getBalance().add(request.getAmount()));

        walletRepository.save(senderWallet);
        walletRepository.save(recipientWallet);

        // 5. Record Transaction
        Transaction transaction = new Transaction(
                request.getAmount(),
                senderWallet.getCurrency(),
                TransactionType.TRANSFER,
                TransactionStatus.SUCCESS,
                senderWallet.getWalletNumber(),
                recipientWallet.getWalletNumber(),
                UUID.randomUUID().toString()
        );

        Transaction savedTransaction = transactionRepository.save(transaction);
        return new TransactionResponse(savedTransaction);
    }

    public List<TransactionResponse> getTransactionHistory(String userEmail) {
        Wallet userWallet = walletRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        
        return transactionRepository.findBySourceWalletIdOrDestinationWalletIdOrderByTimestampDesc(
                userWallet.getWalletNumber(), userWallet.getWalletNumber())
                .stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());
    }
}
