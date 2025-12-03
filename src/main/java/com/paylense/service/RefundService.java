package com.paylense.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service to handle payment refunds for PayLense payment gateway.
 * Supports partial refunds, full refunds, and refund status tracking.
 */
public class RefundService {

    // In-memory store for refund statuses keyed by refund ID
    private Map<String, String> refundStatusMap = new HashMap<>();

    /**
     * Initiates a full refund for a given payment ID.
     *
     * @param paymentId The ID of the payment to refund.
     * @return The refund ID generated for tracking.
     */
    public String refundFull(String paymentId) {
        // Simulate refund processing logic
        String refundId = generateRefundId();
        refundStatusMap.put(refundId, "FULL_REFUND_INITIATED");
        // Here you would integrate with PayLense API to process full refund
        // For example: payLenseApi.refund(paymentId, fullAmount);
        refundStatusMap.put(refundId, "FULL_REFUND_COMPLETED");
        return refundId;
    }

    /**
     * Initiates a partial refund for a given payment ID and amount.
     *
     * @param paymentId The ID of the payment to refund.
     * @param amount The amount to refund partially.
     * @return The refund ID generated for tracking.
     */
    public String refundPartial(String paymentId, double amount) {
        // Simulate refund processing logic
        String refundId = generateRefundId();
        refundStatusMap.put(refundId, "PARTIAL_REFUND_INITIATED");
        // Here you would integrate with PayLense API to process partial refund
        // For example: payLenseApi.refund(paymentId, amount);
        refundStatusMap.put(refundId, "PARTIAL_REFUND_COMPLETED");
        return refundId;
    }

    /**
     * Gets the current status of a refund by refund ID.
     *
     * @param refundId The refund ID to check status for.
     * @return The current status of the refund.
     */
    public String getRefundStatus(String refundId) {
        return refundStatusMap.getOrDefault(refundId, "REFUND_ID_NOT_FOUND");
    }

    // Utility method to generate a unique refund ID
    private String generateRefundId() {
        return UUID.randomUUID().toString();
    }
}
