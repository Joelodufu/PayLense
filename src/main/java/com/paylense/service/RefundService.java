package com.paylense.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RefundService {

    public enum RefundStatus {
        PENDING,
        COMPLETED,
        FAILED
    }

    public static class RefundTransaction {
        private String transactionId;
        private String paymentId;
        private double amount;
        private RefundStatus status;

        public RefundTransaction(String paymentId, double amount) {
            this.transactionId = UUID.randomUUID().toString();
            this.paymentId = paymentId;
            this.amount = amount;
            this.status = RefundStatus.PENDING;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public String getPaymentId() {
            return paymentId;
        }

        public double getAmount() {
            return amount;
        }

        public RefundStatus getStatus() {
            return status;
        }

        public void setStatus(RefundStatus status) {
            this.status = status;
        }
    }

    private Map<String, RefundTransaction> refundTransactions = new HashMap<>();

    // Process a refund for a given payment ID and amount
    public RefundTransaction processRefund(String paymentId, double amount) {
        if (!validateRefundEligibility(paymentId, amount)) {
            throw new IllegalArgumentException("Refund not eligible for payment ID: " + paymentId);
        }

        RefundTransaction refundTransaction = new RefundTransaction(paymentId, amount);
        refundTransactions.put(refundTransaction.getTransactionId(), refundTransaction);

        // Simulate refund processing logic
        boolean refundSuccess = executeRefund(paymentId, amount);

        if (refundSuccess) {
            refundTransaction.setStatus(RefundStatus.COMPLETED);
        } else {
            refundTransaction.setStatus(RefundStatus.FAILED);
        }

        return refundTransaction;
    }

    // Validate if a refund is eligible for the given payment ID and amount
    public boolean validateRefundEligibility(String paymentId, double amount) {
        // Placeholder for actual validation logic
        // For example, check if payment exists, if amount is within refundable limits, etc.
        if (paymentId == null || paymentId.isEmpty() || amount <= 0) {
            return false;
        }
        // Additional validation can be added here
        return true;
    }

    // Simulate the execution of the refund with the payment gateway
    private boolean executeRefund(String paymentId, double amount) {
        // Placeholder for integration with payment gateway refund API
        // For now, we simulate success
        return true;
    }

    // Get refund transaction by transaction ID
    public RefundTransaction getRefundTransaction(String transactionId) {
        return refundTransactions.get(transactionId);
    }

}