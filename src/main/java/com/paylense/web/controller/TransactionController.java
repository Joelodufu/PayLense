package com.paylense.web.controller;

import com.paylense.application.dto.TransferRequest;
import com.paylense.application.service.TransactionService;
import com.paylense.domain.entity.Transaction;
import com.paylense.domain.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@AuthenticationPrincipal User user, @RequestBody TransferRequest request) {
        try {
            Transaction transaction = transactionService.transferFunds(user, request);
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<Transaction>> getHistory(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(transactionService.getTransactionHistory(user));
    }
}