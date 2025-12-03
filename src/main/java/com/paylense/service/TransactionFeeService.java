package com.paylense.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Service to handle fee calculation and management for payment gateway operations.
 */
public class TransactionFeeService {

    // Example fee rate for transfer (e.g., 2.5%)
    private static final BigDecimal TRANSFER_FEE_RATE = new BigDecimal("0.025");

    // Simulated fee accounts storage (accountId -> balance)
    private Map<String, BigDecimal> feeAccounts = new HashMap<>();

    /**
     * Calculate the transfer fee based on the amount.
     *
     * @param amount The amount to transfer
     * @return The calculated fee
     */
    public BigDecimal calculateTransferFee(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        return amount.multiply(TRANSFER_FEE_RATE).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Process fee deduction from a given amount.
     *
     * @param amount The original amount
     * @return The amount after deducting the fee
     */
    public BigDecimal processFeeDeduction(BigDecimal amount) {
        BigDecimal fee = calculateTransferFee(amount);
        return amount.subtract(fee).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Add fee amount to a fee account.
     *
     * @param accountId The fee account identifier
     * @param feeAmount The fee amount to add
     */
    public void addFeeToAccount(String accountId, BigDecimal feeAmount) {
        if (accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("Account ID must not be null or empty");
        }
        if (feeAmount == null || feeAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Fee amount must not be negative");
        }
        feeAccounts.put(accountId, feeAccounts.getOrDefault(accountId, BigDecimal.ZERO).add(feeAmount));
    }

    /**
     * Get the balance of a fee account.
     *
     * @param accountId The fee account identifier
     * @return The balance of the fee account
     */
    public BigDecimal getFeeAccountBalance(String accountId) {
        if (accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("Account ID must not be null or empty");
        }
        return feeAccounts.getOrDefault(accountId, BigDecimal.ZERO);
    }

    /**
     * Clear the fee account balance (for example, after payout).
     *
     * @param accountId The fee account identifier
     */
    public void clearFeeAccount(String accountId) {
        if (accountId == null || accountId.isEmpty()) {
            throw new IllegalArgumentException("Account ID must not be null or empty");
        }
        feeAccounts.remove(accountId);
    }
}
