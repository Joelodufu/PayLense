package com.paylense.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.paylense.web.model.Transaction;
import com.paylense.web.service.TransactionService;
import com.paylense.web.security.UserPrincipal;
import java.util.List;

@Tag(name = "Transaction Controller", description = "APIs for managing transactions")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Operation(summary = "Get all transactions", description = "Retrieve a list of all transactions")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    public List<Transaction> getAllTransactions(
        @Parameter(description = "Authenticated user principal")
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return transactionService.getAllTransactions(userPrincipal);
    }

    @Operation(summary = "Get transaction by ID", description = "Retrieve a single transaction by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved transaction"),
        @ApiResponse(responseCode = "404", description = "Transaction not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/{id}")
    public Transaction getTransactionById(
        @Parameter(description = "ID of the transaction to retrieve", required = true)
        @PathVariable Long id,
        @Parameter(description = "Authenticated user principal")
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return transactionService.getTransactionById(id, userPrincipal);
    }

    @Operation(summary = "Create a new transaction", description = "Create a new transaction with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Transaction created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping
    public Transaction createTransaction(
        @Parameter(description = "Transaction details to create", required = true)
        @RequestBody Transaction transaction,
        @Parameter(description = "Authenticated user principal")
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return transactionService.createTransaction(transaction, userPrincipal);
    }

    @Operation(summary = "Update an existing transaction", description = "Update the details of an existing transaction")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transaction updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Transaction not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PutMapping("/{id}")
    public Transaction updateTransaction(
        @Parameter(description = "ID of the transaction to update", required = true)
        @PathVariable Long id,
        @Parameter(description = "Updated transaction details", required = true)
        @RequestBody Transaction transaction,
        @Parameter(description = "Authenticated user principal")
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return transactionService.updateTransaction(id, transaction, userPrincipal);
    }

    @Operation(summary = "Delete a transaction", description = "Delete a transaction by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Transaction deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Transaction not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @DeleteMapping("/{id}")
    public void deleteTransaction(
        @Parameter(description = "ID of the transaction to delete", required = true)
        @PathVariable Long id,
        @Parameter(description = "Authenticated user principal")
        @AuthenticationPrincipal UserPrincipal userPrincipal) {
        transactionService.deleteTransaction(id, userPrincipal);
    }
}
