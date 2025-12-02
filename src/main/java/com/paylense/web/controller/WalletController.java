package com.paylense.web.controller;

import com.paylense.domain.entity.Wallet;
import com.paylense.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
@Tag(name = "Wallet Controller", description = "APIs for managing wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get wallet by ID", description = "Fetches wallet details by wallet ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Wallet found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "Wallet not found for the given ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Wallet> getWalletById(
            @Parameter(description = "ID of the wallet to be fetched", required = true)
            @PathVariable Long id) {
        return walletService.getWalletById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}