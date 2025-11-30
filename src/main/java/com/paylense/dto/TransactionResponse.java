package com.paylense.dto;

import com.paylense.domain.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String currency;
    private String type;
    private String status;
    private LocalDateTime timestamp;
    private String sourceWalletId;
    private String destinationWalletId;
    private String reference;
    private String description;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.currency = transaction.getCurrency();
        this.type = transaction.getType().name();
        this.status = transaction.getStatus().name();
        this.timestamp = transaction.getCreatedAt();
        this.sourceWalletId = transaction.getSenderWallet().getWalletNumber();
        this.destinationWalletId = transaction.getReceiverWallet().getWalletNumber();
        this.reference = transaction.getReference();
        this.description = transaction.getDescription();
    }

    // Getters
    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getSourceWalletId() { return sourceWalletId; }
    public String getDestinationWalletId() { return destinationWalletId; }
    public String getReference() { return reference; }
    public String getDescription() { return description; }
}