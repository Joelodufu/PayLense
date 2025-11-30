package com.paylense.repository;

import com.paylense.domain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderWalletWalletNumberOrReceiverWalletWalletNumberOrderByCreatedAtDesc(String senderWalletNumber, String receiverWalletNumber);
}