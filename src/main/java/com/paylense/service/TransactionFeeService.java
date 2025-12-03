package com.paylense.service;

import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Service to calculate transaction fees based on different fee structures
 * (fixed, percentage, or tiered) for the PayLense payment gateway.
 */
public class TransactionFeeService {

    public enum FeeType {
        FIXED,
        PERCENTAGE,
        TIERED
    }

    /**
     * Calculate transaction fee based on fee type and parameters.
     *
     * @param amount The transaction amount
     * @param feeType The type of fee structure
     * @param feeValue The fee value (fixed amount or percentage as decimal, e.g. 0.02 for 2%)
     * @param tieredFees The tiered fee map where key is the upper bound of the tier and value is the fee for that tier
     *                   (only used if feeType is TIERED)
     * @return The calculated fee
     */
    public BigDecimal calculateFee(BigDecimal amount, FeeType feeType, BigDecimal feeValue, NavigableMap<BigDecimal, BigDecimal> tieredFees) {
        switch (feeType) {
            case FIXED:
                return feeValue;
            case PERCENTAGE:
                return amount.multiply(feeValue);
            case TIERED:
                if (tieredFees == null || tieredFees.isEmpty()) {
                    throw new IllegalArgumentException("Tiered fees map must be provided for TIERED fee type");
                }
                // Find the tier for the amount
                BigDecimal fee = BigDecimal.ZERO;
                for (var entry : tieredFees.entrySet()) {
                    if (amount.compareTo(entry.getKey()) <= 0) {
                        fee = entry.getValue();
                        break;
                    }
                }
                return fee;
            default:
                throw new IllegalArgumentException("Unsupported fee type");
        }
    }

}
