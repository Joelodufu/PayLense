package com.paylense.web;

import com.paylense.dto.TransactionResponse;
import com.paylense.dto.TransferRequest;
import com.paylense.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<TransactionResponse> transferFunds(@RequestBody TransferRequest request, Authentication authentication) {
        String senderEmail = authentication.getName();
        return ResponseEntity.ok(transactionService.transferFunds(senderEmail, request));
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionResponse>> getHistory(Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(transactionService.getTransactionHistory(userEmail));
    }
}