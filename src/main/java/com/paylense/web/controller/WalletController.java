package com.paylense.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Wallet Controller", description = "APIs for managing wallets")
public class WalletController {

    @GetMapping("/wallet/{id}")
    @Operation(summary = "Get wallet by ID", description = "Fetches wallet details by wallet ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Wallet found and returned successfully"),
        @ApiResponse(responseCode = "404", description = "Wallet not found for the given ID"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public String getWalletById(
        @Parameter(description = "ID of the wallet to be fetched", required = true)
        @PathVariable String id) {
        // Method implementation here
        return "Wallet details for ID: " + id;
    }

}
