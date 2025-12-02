package com.paylense.repository;

import com.paylense.model.Transaction;
import com.paylense.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySenderWalletWalletNumberOrReceiverWalletWalletNumberOrderByCreatedAtDesc(String senderWalletNumber, String receiverWalletNumber);

    List<Transaction> findBySenderWalletOrReceiverWallet(Wallet senderWallet, Wallet receiverWallet);

    List<Transaction> findBySenderWallet(Wallet senderWallet);

    List<Transaction> findByReceiverWallet(Wallet receiverWallet);

}
