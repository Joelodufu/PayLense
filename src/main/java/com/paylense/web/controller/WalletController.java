package com.paylense.web.controller;

import com.paylense.application.service.WalletService;
import com.paylense.domain.entity.Wallet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getWalletByUserId(@PathVariable Long userId) {
        try {
            Wallet wallet = walletService.getWalletByUser(userId);
            return ResponseEntity.ok(wallet);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}