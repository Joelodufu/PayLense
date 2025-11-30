package com.paylense.application.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransferRequest {
    private String receiverPhoneNumber; // Using phone number as wallet identifier
    private BigDecimal amount;
    private String description;
}